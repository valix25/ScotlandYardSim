package scotlandyard;

public class Ferry extends Transport{

    
    private final int from, to;
    //private static String type;
    
    
    public Ferry(int x, int y){
        
        super("ferry");
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
