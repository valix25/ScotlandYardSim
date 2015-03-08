package scotlandyard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class InitializePOS extends JPanel{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static ArrayList<Detective> dl;
    private static ArrayList<Node> nodes;
    private static Mr_X mrx;
    
    public InitializePOS(ArrayList<Detective> det_l, Mr_X Mrx, ArrayList<Node> nodl){
        mrx = Mrx;
        dl = det_l;
        nodes = nodl;
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d_new = (Graphics2D) g.create();

        g2d_new.setColor(Color.red);
        
        for(Detective d:dl){
            int x=10,y=10;
            int cur_node = d.get_current_position();
            for(Node n:nodes){
                if(cur_node == n.get_number()){
                    x = n.get_X();
                    y = n.get_Y();
                    break;
                }
            }
            g2d_new.setColor(Color.cyan);
            g2d_new.fillOval(7*x-6, 7*y-6, 12, 12);
        }
        
        int cur_pos = mrx.get_current_position();
        int x = 10,y=10;
        for(Node n:nodes){
            if(cur_pos == n.get_number()){
                x = n.get_X();
                y = n.get_Y();
                break;
            }
        }
        g2d_new.setColor(Color.red);
        g2d_new.fillOval(7*x-6, 7*y-6, 12, 12);
    }
    
    
    
}
