package scotlandyard;

import java.util.ArrayList;
import java.util.Random;

public class Mr_X extends Player{
    
    private static int subway_tokens = 0;
    private static int taxi_tokens = 0;
    private static int bus_tokens = 0;
    private static int black_tokens = 5;
    private static int current_node;
    private static Mr_X instance;
    
    private Mr_X(){
        
    }
    
    // we make Mr_X into a singleton class
    // basically a class that can be instantiated only once.
    // We don't want multiple X's running around
    public static Mr_X get_MrX(){
        if(instance == null){
            instance = new Mr_X();
        }
        return instance;
    }
    
    public void increase_tokens(String s){
        if(s.equals("taxi")) taxi_tokens ++;
        else if(s.equals("subway")) subway_tokens ++;
        else if(s.equals("bus")) bus_tokens ++;
        else throw new IllegalArgumentException("Not a valid argument for increase_tokens");
    }
    
    public int tokens(String s){
        if(s.equals("taxi")) return taxi_tokens;
        else if(s.equals("subway")) return subway_tokens;
        else if(s.equals("bus")) return bus_tokens;
        else if(s.equals("ferry")) return black_tokens;
        else throw new IllegalArgumentException("Not a valid means of transportation");
    }
    
    public int taxi_tokens(){
        return taxi_tokens;
    }
    
    public int bus_tokens(){
        return bus_tokens;
    }
    
    public int subway_tokens(){
        return subway_tokens;
    }
    
    public int black_tokens(){
        return black_tokens;
    }
    
    public int number_of_tokens(){
        return subway_tokens + taxi_tokens + bus_tokens + black_tokens;
    }
    
    public void set_current_position(int x){
        current_node = x;
    }
    
    public int get_current_position(){
        return current_node;
    }
    
    // move at a random position given a list of possible positions
    public void move(ArrayList<Integer> pos, String s){

        Random rand = new Random();
        int randNum = rand.nextInt(pos.size());
        current_node = pos.get(randNum);
        
        if(s.equals("taxi")) {
            if(taxi_tokens > 0) taxi_tokens --;
            else black_tokens --;
        }
        else if(s.equals("subway")) {
            if(subway_tokens > 0) subway_tokens --;
            else black_tokens --;
        }
        else if(s.equals("bus")) {
            if(bus_tokens > 0) bus_tokens --;
            else black_tokens --;
        }
        else if(s.equals("ferry")) black_tokens --;
        else throw new IllegalArgumentException("Not a valid argument for move function");
    }
    
    public Object clone() throws CloneNotSupportedException{
        
        throw new CloneNotSupportedException();
        // in case someone tries to clone the object
        // this is made to ensure that a singleton is really a singleton 
    }
    
    
}
