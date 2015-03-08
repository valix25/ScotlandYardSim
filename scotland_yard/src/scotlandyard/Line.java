package scotlandyard;

import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
//import javax.swing.JLabel;
//import java.util.Random;
//import javax.swing.JFrame;
import javax.swing.JPanel;

public class Line extends JPanel {

    private static final long serialVersionUID = 1L;
    private static ArrayList<Taxi> taxi_lines;
    private static ArrayList<Ferry> ferry_lines;
    private static ArrayList<Bus> bus_lines;
    private static ArrayList<Subway> subway_lines;
    private static ArrayList<Node> nodes;
    private static ArrayList<Detective> detectives;
    private static Mr_X MrX;
    private static String status = "Starting positions";
    private static final int UPDATE_RATE = 30;
 //   private static final double SPEED = 0.25;
    
    private static ArrayList<Integer> helperFindTaxiNodes(int x, ArrayList<Taxi> t_routes){
        
        ArrayList<Integer> l = new ArrayList<Integer>();
        for(Taxi t:t_routes)
            if (x == t.get_NodeFrom()) l.add(t.get_NodeTo());
        return l;
        
    }
    
    private static ArrayList<Integer> helperFindBusNodes(int x, ArrayList<Bus> b_routes){
        
        ArrayList<Integer> l = new ArrayList<Integer>();
        for(Bus b:b_routes)
            if (x == b.get_NodeFrom()) l.add(b.get_NodeTo());
        return l;
        
    }
    
   private static ArrayList<Integer> helperFindFerryNodes(int x, ArrayList<Ferry> f_routes){
        
        ArrayList<Integer> l = new ArrayList<Integer>();
        for(Ferry f:f_routes)
            if (x == f.get_NodeFrom()) l.add(f.get_NodeTo());
        return l;
        
    }
   
   private static ArrayList<Integer> helperFindSubwayNodes(int x, ArrayList<Subway> s_routes){
       
       ArrayList<Integer> l = new ArrayList<Integer>();
       for(Subway s:s_routes)
           if (x == s.get_NodeFrom()) l.add(s.get_NodeTo());
       return l;
       
   }
 
    private static ArrayList<Integer> findNextNodes(int x, String s){
        
        if(s.equals("taxi")) return helperFindTaxiNodes(x,taxi_lines);
        else if(s.equals("bus")) return helperFindBusNodes(x,bus_lines);
        else if(s.equals("ferry")) return helperFindFerryNodes(x,ferry_lines);
        else if(s.equals("subway")) return helperFindSubwayNodes(x,subway_lines);
        else  return new ArrayList<Integer>();
        
    }
    
    // same game_random_simulation as the one in the main function only that it
    // has some modifications
    private void game_random_simulation(){
        
        Thread gameThreadSim = new Thread(){
            public void run(){

        status = "Game in progress...";
        ArrayList<Integer> possible_pos = new ArrayList<Integer>();
        boolean status_game = true;
        String winner = "no one at the moment";
        
        // the trans array basically remembers all the possible transportation methods
        String[] trans = new String[5];
        trans[0] = "subway"; trans[1] = "bus"; trans[2] = "taxi"; trans[3] = "ferry";
        Random randy = new Random();
        boolean[] stuck = new boolean[detectives.size()+1];
        for(int j=0; j < detectives.size(); j++)
           stuck[j] = false;
        while(status_game){
            
            for(int i = 0; i < detectives.size(); i++){
                int randN = randy.nextInt(3);
                possible_pos.clear();
                if(detectives.get(i).tokens(trans[randN]) != 0 && stuck[i] == false){
                   possible_pos = findNextNodes(detectives.get(i).get_current_position(),trans[randN]);
                }
                int count = 0;

                while(possible_pos.isEmpty() && stuck[i] == false){
                    // this while loop checks if a detective is stuck in a position or not 
                    // a detective can get stuck either from getting stuck in a position
                    // from which it doesn't have a token or his tokens finished
                    // if it is possible to move it gives a list with possible moves
                    if(count == 2) break;
                    else{
                        if(randN == 2){
                            randN = 0;
                            if(detectives.get(i).tokens(trans[randN]) != 0){
                            possible_pos = findNextNodes(detectives.get(i).get_current_position(),trans[randN]);
                            }
                            count ++;
                        } else {
                            randN ++;
                            if(detectives.get(i).tokens(trans[randN]) != 0){
                            possible_pos = findNextNodes(detectives.get(i).get_current_position(),trans[randN]);
                            }
                            count ++;
                        }
                        
                    }   
                }
                
                //equivalent with saying that there is a way to move
                if(!possible_pos.isEmpty()){
                    Detective d_partial = detectives.get(i);
                    System.out.print("Detective "+i+" goes from "+d_partial.get_current_position()+" -- "+trans[randN]+" -- ");
                    detectives.get(i).move(possible_pos, trans[randN]);
                    repaint();
                    try{
                        Thread.sleep(10000 / UPDATE_RATE);
                    } catch (InterruptedException ex) {}
                    System.out.print(detectives.get(i).get_current_position()+", tokens left: ");
                    System.out.print("taxi-"+d_partial.taxi_tokens()+" bus-"+d_partial.bus_tokens());
                    System.out.println(" subway-"+d_partial.subway_tokens());
                    // increase MrX tokens if a detective moves
                    MrX.increase_tokens(trans[randN]);
                }
                
                if(possible_pos.isEmpty()){
                    stuck[i] = true;
                    System.out.println("Detective "+i+" is stuck");
                }
                
            }
            
            boolean check_stuck = true;
            for(int k=1; k < detectives.size();k++){
                if (stuck[k-1] != stuck[k] || stuck[k] == false || stuck[k-1] == false) check_stuck = false;
            }
            
            boolean win = check_if_detectives_caught_X();
            if(win) {
                winner = "detectives";
                status = "Detectives won,Mr.X was caught.";
                repaint();
                status_game = false;
            } else if(check_end_tokens(detectives.size()) == false) {
                winner = "Mr.X";
                status = "Mr.X won,no tokens left for detectives.";
                repaint();
                status_game = false;
            } else if(check_stuck == true){
                winner = "Mr.X";
                status = "Mr.X won,detectives are stuck.";
                repaint();
                status_game = false;
            } else {
                
                // let's move X
                int randN2 = randy.nextInt(4); 
                if(MrX.tokens(trans[randN2]) > 0 || MrX.black_tokens() > 0 ){
                    //System.out.println("Position X: "+MrX.get_current_position()+", trans: "+trans[randN2]);
                    possible_pos = findNextNodes(MrX.get_current_position(),trans[randN2]);
                 }
                while(possible_pos.isEmpty()){
                    randN2 = randy.nextInt(4);
                    if(MrX.tokens(trans[randN2]) > 0 || MrX.tokens(trans[3]) > 0 ){
                        //System.out.println("Position X: "+MrX.get_current_position()+", trans: "+trans[randN2]);
                        possible_pos = findNextNodes(MrX.get_current_position(),trans[randN2]);
                     }
                }
                // X won't get stuck so no need to check for that
                System.out.print("Mr.X goes from "+MrX.get_current_position() + " -- "+ trans[randN2]+ " -- ");
                MrX.move(possible_pos, trans[randN2]);
                repaint();
                try{
                    Thread.sleep(10000 / UPDATE_RATE);
                } catch (InterruptedException ex) {}
                System.out.print(" "+MrX.get_current_position()+", tokens left: ");
                System.out.print("taxi-"+MrX.taxi_tokens()+" bus-"+MrX.bus_tokens()+" subway-"+MrX.subway_tokens());
                System.out.println(" black-"+MrX.black_tokens());
                System.out.println();
            }
            
            // X might also run into a detective when he moves
            win = check_if_detectives_caught_X();
            if(win){
                winner = "detectives";
                status = "Detectives won,Mr.X has stumbled upon a detective.";
                repaint();
                status_game = false;
            }
            
        }
        
        System.out.println("Winner of the game: "+winner);
        }
        };
        gameThreadSim.start();
    }
    
public Line(ArrayList<Taxi> taxi_r, ArrayList<Ferry> ferry_l, ArrayList<Bus> bus_l,
        ArrayList<Subway> sub_l, ArrayList<Node> node_r, ArrayList<Detective> det_l, Mr_X mrX){
    
    taxi_lines = taxi_r;
    ferry_lines = ferry_l;
    bus_lines = bus_l;
    subway_lines = sub_l;
    nodes = node_r;
    detectives = det_l;
    MrX = mrX;
    repaint();

}

public void start_game(){
    game_random_simulation();
}

private boolean check_if_detectives_caught_X(){
    
    for(int i=0; i < detectives.size();i++){
        if(detectives.get(i).get_current_position() == MrX.get_current_position()) return true;
    }
    return false;
}

// checks if at least one of the detectives reached 0 in number of tokens
private boolean check_end_tokens(int no_det){

    for(int j = 0;j < no_det;j++){
        if (detectives.get(j).number_of_tokens() == 0) return false;
    }
    return true;
}
    

public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();

    g2d.setColor(Color.red);

    // draw the taxi,ferry,subway,bus lines
    for (Taxi t:taxi_lines){
        g2d.setColor(Color.black);
        int from = t.get_NodeFrom();
        int to = t.get_NodeTo();
        int x1,y1,x2,y2;
        x1 = 20;y1 = 20; x2 = 50; y2 = 50;
        for(Node n:nodes){
           if(n.get_number() == from) {
               x1 = n.get_X();
               y1 = n.get_Y();
           } 
           if(n.get_number() == to) {
               x2 = n.get_X();
               y2 = n.get_Y();
           } 
        }
        
        g2d.drawLine(x1*7, y1*7, x2*7, y2*7);
        g2d.fillOval(x1*7-4, y1*7-4, 8, 8);
        g2d.fillOval(x2*7-4, y2*7-4, 8, 8);
    }
    
    for(Ferry f:ferry_lines){
        g2d.setColor(Color.blue);
        int from = f.get_NodeFrom();
        int to = f.get_NodeTo();
        int x1,y1,x2,y2;
        x1 = 20;y1 = 20; x2 = 50; y2 = 50;
        for(Node n:nodes){
           if(n.get_number() == from) {
               x1 = n.get_X();
               y1 = n.get_Y();
           } 
           if(n.get_number() == to) {
               x2 = n.get_X();
               y2 = n.get_Y();
           } 
        }
        g2d.drawLine(x1*7, y1*7, x2*7, y2*7);
        
    }
    
    for(Bus b:bus_lines){
        g2d.setColor(Color.yellow);
        int from = b.get_NodeFrom();
        int to = b.get_NodeTo();
        int x1,y1,x2,y2;
        x1 = 20;y1 = 20; x2 = 50; y2 = 50;
        for(Node n:nodes){
           if(n.get_number() == from) {
               x1 = n.get_X();
               y1 = n.get_Y();
           } 
           if(n.get_number() == to) {
               x2 = n.get_X();
               y2 = n.get_Y();
           } 
        }
        g2d.drawLine(x1*7, y1*7, x2*7, y2*7);
        
    }
    
    for(Subway s:subway_lines){
        g2d.setColor(Color.green);
        int from = s.get_NodeFrom();
        int to = s.get_NodeTo();
        int x1,y1,x2,y2;
        x1 = 20;y1 = 20; x2 = 50; y2 = 50;
        for(Node n:nodes){
           if(n.get_number() == from) {
               x1 = n.get_X();
               y1 = n.get_Y();
           } 
           if(n.get_number() == to) {
               x2 = n.get_X();
               y2 = n.get_Y();
           } 
        }
        g2d.drawLine(x1*7, y1*7, x2*7, y2*7);
        
    }
    
    // draw left labels
    g2d.setColor(Color.black);
    g2d.drawString("taxi", 720, 50);
    g2d.fillRect(770, 45, 30, 5);
    
    g2d.setColor(Color.black);
    g2d.drawString("ferry", 720, 70);
    g2d.setColor(Color.blue);
    g2d.fillRect(770, 65, 30, 5);
    
    g2d.setColor(Color.black);
    g2d.drawString("bus", 720, 90);
    g2d.setColor(Color.yellow);
    g2d.fillRect(770, 85, 30, 5);
    
    g2d.setColor(Color.black);
    g2d.drawString("subway", 720, 110);
    g2d.setColor(Color.green);
    g2d.fillRect(770, 105, 30, 5);
    
    // draw the detectives and MrX
    for(Detective d:detectives){
        d.set_det_X(0);
        d.set_det_X(0);
        int cur_node = d.get_current_position();
        for(Node n:nodes){
            if(cur_node == n.get_number()){
                d.set_det_X(n.get_X());
                d.set_det_Y(n.get_Y());
                break;
            }
        }
        g2d.setColor(Color.cyan);
        g2d.fillOval((int)7*d.get_det_X()-10, (int)7*d.get_det_Y()-10, 20, 20);
    }
    
    int cur_pos = MrX.get_current_position();
    int x = 10,y=10;
    for(Node n:nodes){
        if(cur_pos == n.get_number()){
            x = n.get_X();
            y = n.get_Y();
            break;
        }
    }
    g2d.setColor(Color.red);
    g2d.fillOval((int)7*x-10,(int)7*y-10, 20, 20);
    
    g2d.setColor(Color.black);
    g2d.drawString("Detectives", 720, 130);
    g2d.setColor(Color.cyan);
    g2d.fillOval(790, 120, 10, 10);
    
    g2d.setColor(Color.black);
    g2d.drawString("Mr.X", 720, 150);
    g2d.setColor(Color.red);
    g2d.fillOval(770, 140, 10, 10);
    
    String[] splits = status.split(",");
    g2d.drawString("Status: ",720,640);
    if(splits.length == 1)
        g2d.drawString(splits[0],720,660);
    else {
    g2d.drawString(splits[0]+",",720,660);
    g2d.drawString(splits[1],720,680);
    }
    
    g2d.dispose(); 
  }

}

