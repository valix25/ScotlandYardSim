package scotlandyard;

public class Bus extends Transport{
    
    private final int from, to;
    //private static String type;
    
    
    public Bus(int x, int y){
        
        super("bus");
      //  type = "taxi";
        from = x;
        to = y;
    }
    
    public int get_NodeFrom(){
        return from;
    }
    
    public int get_NodeTo(){
        return to;
    }
    

}
