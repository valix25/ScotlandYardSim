package scotlandyard;

public class Node {

    private final int number;
    private final int coord_x;
    private final int coord_y;
    
    class Pair{
        
        private final int x;
        private final int y;
        
        public Pair(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        
        public int obtain_x() {return x;}
        public int obtain_y() {return y;}
    }
    
    public Node(int n, int x, int y)
    {
        number = n;
        Pair p = new Pair(x,y);
        coord_x = p.obtain_x();
        coord_y = p.obtain_y();
    }
    
    public int get_number() {return number;}
    public int get_X() {return coord_x;}
    public int get_Y() {return coord_y;}
    public int getX_givenN(int n){return coord_x;}
    public int getY_givenN(int n){return coord_y;}
}
