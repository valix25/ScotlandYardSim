package scotlandyard;

import java.util.ArrayList;

//import java.util.InputMismatchException;
//import java.util.NoSuchElementException;

/* *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  
 *  Code adapted from the source above
 */

public class Digraph {

    private final int V;
    private int E;
    private  ArrayList<Type> types;
    private ArrayList<Integer>[] adj;
    
    class Type{
         
        public int left;
        public int right;
        public String type;
        public Type(int x,int y, String s){
            left = x;
            right = y;
            type = s;
        }
        public int get_right(int x){return right;}
        public int get_left(int y){return left;}
        public String get_type(){return type;}
        
    }
    

    public Digraph(int V){
        
        if(V < 0) throw new IllegalArgumentException("Number of vertices must positive");
        
        this.V = V;
        this.E = 0;
        types = new ArrayList<Type>();
        
        // the only warning in the code
        adj = (ArrayList<Integer>[]) new ArrayList[V];
        
        for(int v=0; v < V;v++){
            
            adj[v] = new ArrayList<Integer>();
        }
        
    }
    
    public int V(){ return V;}
    
    public int E() {return E;}
    
    public void addEdge(int v, int w,String s){
        
        if(v <0 || v >= V) throw new IndexOutOfBoundsException("vertex "+v+" is not between 0 and "+ V);
        if(w <0 || w >= V) throw new IndexOutOfBoundsException("vertex "+w+" is not between 0 and "+ V);
        adj[v].add(w); 
        E++;
        types.add(new Type(v,w,s));
    }
    
    public Iterable<Integer> adj(int v){
        if(v <0 || v > V) throw new IndexOutOfBoundsException("vertex "+v+" is not between 0 and "+ V);
        return adj[v];
    }
    
    
    /**
     * Returns the reverse of the digraph.
     * @return the reverse of the digraph
     */
   /* public Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                R.addEdge(w, v,);
            }
        }
        return R;
    }*/
    
    public boolean hasEdge(int i, int j){
        if(i <0 || i >= V) throw new IndexOutOfBoundsException("vertex "+i+" is not between 0 and "+ V);
        if(j <0 || j >= V) throw new IndexOutOfBoundsException("vertex "+j+" is not between 0 and "+ V);
        return adj[i].contains(j);
        
    }
    
    public String getType(int i, int j){
        
        for(int k=0;k<types.size();k++)
        {
            Type t = types.get(k);
            if(t.left == i && t.right == j) return t.get_type();
            
        }
        return "x";
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
    
}
