package scotlandyard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class test_movement extends JPanel{
    
    private static final long serialVersionUID = 1L;
    private static final double SPEED = 0.25;
    private static int x1 = 10, y1 = 60;
    private static int x2 = 230, y2 = 400;
    private static double x ,y;
    private static final int UPDATE_RATE = 60; // Number of refresh per second
    
    public test_movement(){
        
        Thread gameThread = new Thread(){
            
            public void run(){
                
                x = x1; y = y1;
                while(x < x2 && y < y2){
                    double angle = Math.atan2(y2 - y1, x2 - x1);
                    x += SPEED * Math.cos(angle);
                    y += SPEED * Math.sin(angle);
                    
                    // Refresh the display
                    repaint(); // Callback paintComponent()
                    
                    // Delay for timing control and give other threads a chance
                    try{
                        Thread.sleep(100 / UPDATE_RATE);
                    } catch (InterruptedException ex) {}
                }
                
            }
            
        };
        gameThread.start(); // Callback run()
        
        
    }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillOval(x1-4, y1-4, 8, 8);
        g2d.fillOval(x2-4, y2-4, 8, 8);
        
        //Draw ball
        g2d.setColor(Color.blue);
        g2d.fillOval((int)x - 4, (int)y - 4, 8, 8);

       // double x = x1, y = y1;
        /*
        while(x < x2 && y < y2) {
           x += SPEED * Math.cos(angle);
           y += SPEED * Math.sin(angle);
           g2d.setColor(Color.blue);
           g2d.fillOval((int)x - 4, (int)y - 4, 8, 8);

        }*/
        /*
        for(int t = 0; t <= 50; t++) {
            double x = lerp(x1, x2, t / 50.0);
            double y = lerp(y1, y2, t / 50.0);
            g2d.setColor(Color.blue);
            g2d.fillOval((int)x - 4, (int)y - 4, 8, 8);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
         }*/
      }
      /*
      private static double lerp(int x1, int x2, double t) {
         return x1 + (x2 - x1) * t;
      }*/
    
    public static void main(String[] args) {
        // run GUI in the Event Dispatcher Thread (EDT) instead of the main thread
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            
          public void run(){
            JFrame frame = new JFrame("Scotlang Yard");
            frame.setBounds(100, 100, 820, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        
            test_movement t = new test_movement();
            frame.add(t);
          }
        });

    }
}