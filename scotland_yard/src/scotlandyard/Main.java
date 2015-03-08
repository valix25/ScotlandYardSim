package scotlandyard;
//import java.io.BufferedInputStream;
//import java.awt.Graphics;
//import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.io.BufferedReader;
import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main extends JFrame{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static BufferedReader reader;
    private static InputStream resource;
    private static ArrayList<Node> nodes;
    private static ArrayList<Taxi> taxi_routes;
    private static ArrayList<Bus> bus_routes;
    private static ArrayList<Subway> subway_routes;
    private static ArrayList<Ferry> ferry_routes;
    private static ArrayList<Detective> detectives;
    private static Mr_X MrX = Mr_X.get_MrX();
    private static Digraph G;
    private static Digraph G2;
    private static JFrame frame = new JFrame("Scotland Yard");
    private static JMenuBar menuBar = new JMenuBar();
    
    private static JMenu fileMenu = new JMenu("File");
    
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
        
        if(s.equals("taxi")) return helperFindTaxiNodes(x,taxi_routes);
        else if(s.equals("bus")) return helperFindBusNodes(x,bus_routes);
        else if(s.equals("ferry")) return helperFindFerryNodes(x,ferry_routes);
        else if(s.equals("subway")) return helperFindSubwayNodes(x,subway_routes);
        else  return new ArrayList<Integer>();
        
    }
    
    private static void OpenFile(String map_f) throws FileNotFoundException{
        
        String filePath = new File("").getAbsolutePath();
        System.out.println(filePath);
        
        // previous tweaks
        // filePath+/src/scotlandyard/+map_f
        resource = Main.class.getResourceAsStream(map_f);
        //reader = new BufferedReader(new FileReader(filePath+"/"+map_f));
        reader = new BufferedReader(new InputStreamReader(resource));
          
    }

    // reads everything from .map file and puts the results in their 
    // respective lists
    private static void ReadFromFile() throws IOException{
        
        String line = null;
        nodes = new ArrayList<Node>();
        taxi_routes = new ArrayList<Taxi>();
        bus_routes = new ArrayList<Bus>();
        ferry_routes = new ArrayList<Ferry>();
        subway_routes = new ArrayList<Subway>();
        try {
            while((line = reader.readLine()) != null)
            {
                if(line.trim().isEmpty()) System.out.print("");
                else if(line.charAt(0) == '#') System.out.print("");
                else  {

                    String[] vals = line.split("\\s+");
                    for(int j = 0; j < vals.length; j++)
                    {
                        vals[j].trim();
                    }
                    
                    if(vals[0].equals("node")){
                        
                        try{
                        int n = Integer.parseInt(vals[1]);
                        int x = Integer.parseInt(vals[2]);
                        int y = Integer.parseInt(vals[3]);
                        
                        nodes.add(new Node(n,x,y));
                        //for(Node no:nodes)
                            //System.out.print("nodes"+no.get_number());
                        } catch (NumberFormatException e)
                        {
                         System.out.println("Cannot convert inputs to ints");   
                        }

                        
                    } else if(vals[0].equals("taxi")){
                        
                        if(vals.length < 3) System.out.println("Invalid input");
                        else {
                            try{
                            
                              int t1 = Integer.parseInt(vals[1]);
                              int t2 = Integer.parseInt(vals[2]);
                              taxi_routes.add(new Taxi(t1,t2));
                              taxi_routes.add(new Taxi(t2,t1));
                             // for(int j = 0; j<taxi_routes.size(); j++)
                                  //System.out.print(taxi_routes.get(j).get_NodeFrom()+" ");
                            } catch (NumberFormatException e){
                            
                               System.out.println("Cannot convert inputs to ints");
                            }
                        }
                    } else if(vals[0].equals("bus")){
                        
                        if(vals.length < 3) System.out.println("Invalid input");
                        else {
                            try{
                            
                              int b1 = Integer.parseInt(vals[1]);
                              int b2 = Integer.parseInt(vals[2]);
                              bus_routes.add(new Bus(b1,b2));
                              bus_routes.add(new Bus(b2,b1));
                              //for(int j = 0; j<bus_routes.size(); j++)
                                //  System.out.print(bus_routes.get(j).get_NodeFrom()+" ");
                            } catch (NumberFormatException e){
                            
                               System.out.println("Cannot convert inputs to ints");
                            }
                        }
                    } else if(vals[0].equals("ferry")){
                        
                        if(vals.length < 3) System.out.println("Invalid input");
                        else {
                            try{
                            
                              int f1 = Integer.parseInt(vals[1]);
                              int f2 = Integer.parseInt(vals[2]);
                              ferry_routes.add(new Ferry(f1,f2));
                              ferry_routes.add(new Ferry(f2,f1));
                             // for(int j = 0; j<ferry_routes.size(); j++)
                               //   System.out.print(ferry_routes.get(j).get_NodeFrom()+" ");
                            } catch (NumberFormatException e){
                            
                               System.out.println("Cannot convert inputs to ints");
                            }
                        }
                        
                    } else if(vals[0].equals("subway")){
                        if(vals.length < 3) System.out.println("Invalid input");
                        else {
                            try{
                            
                              int s1 = Integer.parseInt(vals[1]);
                              int s2 = Integer.parseInt(vals[2]);
                              subway_routes.add(new Subway(s1,s2));
                              subway_routes.add(new Subway(s2,s1));
                             // for(int j = 0; j<subway_routes.size(); j++)
                               //   System.out.print(subway_routes.get(j).get_NodeFrom()+" ");

                            } catch (NumberFormatException e){
                            
                               System.out.println("Cannot convert inputs to ints");
                            }
                        }
                    } else{
                        System.out.println("That is not a valid transportation system");
                    }

                }
            }
            

        } catch (IOException e) {
            
            e.printStackTrace();
        } finally {
            
            reader.close();
        }
        
    }    
    
    // add all pairs of transportation as edges into the graph
    private static void buildAllEdgesGraph(){
        
        int counterT = 0;
        int counterS = 0;
        int counterF = 0;
        int counterB = 0;
        for(int i=0;i<taxi_routes.size();i++){
            G2.addEdge(taxi_routes.get(i).get_NodeFrom(), taxi_routes.get(i).get_NodeTo(), "taxi");
            counterT ++;
        }
        for(int i=0;i<ferry_routes.size();i++){
            G2.addEdge(ferry_routes.get(i).get_NodeFrom(), ferry_routes.get(i).get_NodeTo(), "ferry");
            counterF ++;
        }
        for(int i=0;i<subway_routes.size();i++){
            G2.addEdge(subway_routes.get(i).get_NodeFrom(), subway_routes.get(i).get_NodeTo(), "subway");
            counterS ++;
        }
        for(int i=0;i<bus_routes.size();i++){
            G2.addEdge(bus_routes.get(i).get_NodeFrom(), bus_routes.get(i).get_NodeTo(), "bus");
            counterB ++;
        }
        System.out.println("edges taxi: "+counterT);
        System.out.println("edges ferry: "+counterF);
        System.out.println("edges subway: "+counterS);
        System.out.println("edges bus: "+counterB);
    }
    
    private static void buildEdgesGraph(int y, String[] l){
        
        Queue<Integer> q = new LinkedList<Integer>();
        
        q.add(y);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        ArrayList<Integer> n_pos = new ArrayList<Integer>();
        int i = 0;
        while(i < l.length){
            
            while(!q.isEmpty()){
                
                int v = q.poll();
                n_pos = findNextNodes(v,l[i]);
                for(int k=0; k < n_pos.size();k++){
                    
                   if(!G.hasEdge(v, n_pos.get(k)) && !G.hasEdge(n_pos.get(k), v)){
                    
                    G.addEdge(n_pos.get(k), v,l[i]);
                    G.addEdge(v, n_pos.get(k),l[i]);
                    System.out.println("("+v+","+n_pos.get(k)+")"+"("+G.getType(v, n_pos.get(k))+")");
                    temp.add(n_pos.get(k));
                   }
                }
                
            }
            
            for(int p = 0; p < temp.size(); p++){
              if(!q.contains(temp.get(p)))
                q.add(temp.get(p));
            }
            temp.clear();
            i++;
        }
    }

    
    private static void initiate_starting_positions(){
        
        Random rand = new Random();
        int randNum = rand.nextInt(nodes.size());
        MrX.set_current_position(randNum);
        System.out.println();
        System.out.println("---> Starting positions <---");
        // next we have to choose some reasonable positions for the detectives
        // according to the rules of the game at least 2 steps away from MrX
        // or in another words not overlapping with Mr_X and not in the possibility
        // of finding X at the first try meaning path > 1
        
        for(int i=0; i < detectives.size(); i++){
            // we are assuming that multiple detective might occupy the same position
            // since the problem doesn't say otherwise
            randNum = rand.nextInt(nodes.size());
            detectives.get(i).set_current_position(randNum);
            boolean check = true;
            while(check){
                int x = MrX.get_current_position();
                int y = detectives.get(i).get_current_position();
                if( x == y || G2.hasEdge(x, y)) {
                    randNum = rand.nextInt(nodes.size());
                    detectives.get(i).set_current_position(randNum);
                } else check = false;
            }           
        }
        
        System.out.println("Mr_X position: "+MrX.get_current_position());
        int count = 0;
        for(Detective i:detectives){
            System.out.println("Detective "+count+ " position: "+i.get_current_position());
            count++;
        }
        System.out.println();
        
        //Line all_lines = new Line(taxi_routes,ferry_routes,bus_routes,subway_routes,nodes,detectives,MrX);
        //frame.add(all_lines);
        //InitializePOS initpos = new InitializePOS(detectives,MrX,nodes);
        //frame.add(initpos);
       
    }
    
    private static boolean check_if_detectives_caught_X(){
        
        for(int i=0; i < detectives.size();i++){
            if(detectives.get(i).get_current_position() == MrX.get_current_position()) return true;
        }
        return false;
    }
    
    // checks if at least one of the detectives reached 0 in number of tokens
    private static boolean check_end_tokens(int no_det){

        for(int j = 0;j < no_det;j++){
            if (detectives.get(j).number_of_tokens() == 0) return false;
        }
        return true;
    }
    
    private static void game_random_simulation(int no_of_detectives){
        
        if(no_of_detectives <= 0) throw new IllegalArgumentException("Let's say we want a positive number of detectives");
        
        // build list of detectives
        detectives = new ArrayList<Detective>();
        for(int i = 0;i < no_of_detectives;i++)
            detectives.add(new Detective());
        
        // give some starting positions for the players
        initiate_starting_positions();
        
        ArrayList<Integer> possible_pos = new ArrayList<Integer>();
        boolean status_game = true;
        String winner = "no one at the moment";
        
        // the trans array basically remembers all the possible transportation methods
        String[] trans = new String[5];
        trans[0] = "subway"; trans[1] = "bus"; trans[2] = "taxi"; trans[3] = "ferry";
        Random randy = new Random();
        boolean[] stuck = new boolean[no_of_detectives+1];
        for(int j=0; j < no_of_detectives; j++)
           stuck[j] = false;
        while(status_game){
            
            for(int i = 0; i < no_of_detectives; i++){
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
            for(int k=1; k < no_of_detectives;k++){
                if (stuck[k-1] != stuck[k] || stuck[k] == false || stuck[k-1] == false) check_stuck = false;
            }
            
            boolean win = check_if_detectives_caught_X();
            if(win) {
                winner = "detectives";
                status_game = false;
            } else if(check_end_tokens(no_of_detectives) == false) {
                winner = "Mr.X";
                status_game = false;
            } else if(check_stuck == true){
                winner = "Mr.X";
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
                System.out.print(" "+MrX.get_current_position()+", tokens left: ");
                System.out.print("taxi-"+MrX.taxi_tokens()+" bus-"+MrX.bus_tokens()+" subway-"+MrX.subway_tokens());
                System.out.println(" black-"+MrX.black_tokens());
                System.out.println();
            }
            
            // X might also run into a detective when he moves
            win = check_if_detectives_caught_X();
            if(win){
                winner = "detectives";
                status_game = false;
            }
            
        }
        
        System.out.println("Winner of the game: "+winner);
    }
    
    public static void main(String[] args) throws IOException {
        
        String map_file = null;
        map_file = args[0];
       // map_file = "london.map";
        //Commented code may have been from the last assignment
        
        int start_node = 0;
        
        try{
        start_node = Integer.parseInt(args[1]);
        } catch(NumberFormatException e){
            System.out.println("Cannot convert string to integer");
        }
        
        OpenFile(map_file);
        ReadFromFile();
        // G is from the last assignment where we
        // didn't need to build the whole graph
        G = new Digraph(nodes.size()+1);
        G2 = new Digraph(nodes.size()+1);
        
        
        String[] l = new String[args.length-2];

        ArrayList<String> transportation = new ArrayList<String>();
        transportation.add("taxi");
        transportation.add("ferry");
        transportation.add("subway");
        transportation.add("bus");
        for(int j=2; j<args.length; j++)
        {
           if(transportation.contains(args[j]))
            l[j-2] = args[j];
           else throw new IllegalArgumentException(args[j] + " argument is not accepted");
        }
        
        // this is from the last assignment not much value for the purposes of this assignment
         buildEdgesGraph(start_node,l);
        
        buildAllEdgesGraph();
        // DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(G,start_node);
       
        frame.setBounds(100, 100, 1000, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        
        frame.setJMenuBar(menuBar);
        menuBar.add(fileMenu);

        Action exitAction = new
                AbstractAction("Exit") // menu item text goes here
                {

                    private static final long serialVersionUID = 1L;

                public void actionPerformed(ActionEvent event) {
                // action code goes here
                System.exit(0);
                }
                };
         /* 
         Action initializeAction = new
                AbstractAction("Initialize") // menu item text goes here
                {

                   private static final long serialVersionUID = 1L;

                  public void actionPerformed(ActionEvent event) {
                        // action code goes here
                      
                      initiate_starting_positions();
                      Line new_lines = new Line(taxi_routes,ferry_routes,bus_routes,subway_routes,nodes,detectives,MrX);
                      frame.add(new_lines);
                  }
                  };
                 
          Action startAction = new
                 AbstractAction("Start Game") // menu item text goes here
                 {

                 private static final long serialVersionUID = 1L;

                 public void actionPerformed(ActionEvent event) {
                                  // action code goes here
                     Line all_lines = new Line(taxi_routes,ferry_routes,bus_routes,subway_routes,nodes,detectives,MrX);
                     all_lines.start_game();
                     frame.add(all_lines);
                  }
                  };
                                        
        
        JMenuItem startItem = fileMenu.add(startAction);*/
        //JMenuItem initializeItem = fileMenu.add(initializeAction);
        JMenuItem exitItem = fileMenu.add(exitAction);
        //JMenuItem exitItem = fileMenu.add(exitAction);

        int no_of_detectives = 4; // change number to get different number of detectives
        detectives = new ArrayList<Detective>();
        for(int i = 0;i < no_of_detectives;i++)
            detectives.add(new Detective());
        
        // the next 2 lines draws the starting positions
        initiate_starting_positions();
        Line all_lines = new Line(taxi_routes,ferry_routes,bus_routes,subway_routes,nodes,detectives,MrX);
        // plays a game given the above starting positions
        all_lines.start_game();
        frame.add(all_lines);
        
       // dfs.printAllPaths(G, start_node, l.length, l);

    }
}
