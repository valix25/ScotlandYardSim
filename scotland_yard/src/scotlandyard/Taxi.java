package scotlandyard;

public class Taxi extends Transport{
    
    private final int from, to;
    //private static String type;
    
    
    public Taxi(int x, int y){
        
        super("taxi");
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
