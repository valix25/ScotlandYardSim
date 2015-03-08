package scotlandyard;

public class Subway extends Transport{

    
    private final int from, to;
    //private static String type;
    
    
    public Subway(int x, int y){
        
        super("subway");
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
