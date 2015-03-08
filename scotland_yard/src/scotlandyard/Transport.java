package scotlandyard;

public abstract class Transport {

    private String type;
    
    public Transport(String t)
    {
        type = t;
    }
    
    public String getTransportType() {return type;}
    
    public abstract int get_NodeFrom();
    
    public abstract int get_NodeTo();
}
