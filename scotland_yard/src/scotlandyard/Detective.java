package scotlandyard;

import java.util.ArrayList;
import java.util.Random;

public class Detective extends Player{

    private int subway_tokens;
    private int taxi_tokens;
    private int bus_tokens;
    private int current_node;
    private int det_X;
    private int det_Y;
    public Detective(){
        
        subway_tokens = 4;
        taxi_tokens = 10;
        bus_tokens = 8;
    }
    
    public int tokens(String s){
        if(s.equals("taxi")) return taxi_tokens;
        else if(s.equals("subway")) return subway_tokens;
        else if(s.equals("bus")) return bus_tokens;
        else throw new IllegalArgumentException("Not a valid means of transportation");
    }
    
    public void set_det_X(int x){ 
        det_X = x;
        }
    public int get_det_X(){
        return det_X;
    }
    
    public void set_det_Y(int y){ 
        det_Y = y;
        }
    public int get_det_Y(){
        return det_Y;
    }
    
    public int number_of_tokens(){
        
        return subway_tokens + taxi_tokens + bus_tokens;
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
        
        if(s.equals("taxi")) taxi_tokens --;
        else if(s.equals("subway")) subway_tokens --;
        else if(s.equals("bus")) bus_tokens --;
    }
    
}
