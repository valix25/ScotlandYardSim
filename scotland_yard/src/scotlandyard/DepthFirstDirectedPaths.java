package scotlandyard;

import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.Queue;
import java.util.Stack;

/* *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
*
*  @author Robert Sedgewick
*  @author Kevin Wayne
*  
*  Code adapted from the source above
*/

public class DepthFirstDirectedPaths {

    private boolean[] marked; //marked[v] = true if v is reachable from s
    private boolean[] visited; // [v] = true if v is reachable from s
    private int[] edgeTo; // egdeTo[v] = last edge on path from s to v
    private final int s; //source vertex
    private ArrayList<Integer> longPath;

    
    public DepthFirstDirectedPaths(Digraph G, int s){
        
        marked = new boolean[G.V()];
        visited = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G,s);
        longPath = new ArrayList<Integer>();

    }
    
    
    private void searchPaths(Digraph G,int v, int n, String[] tr){
        
        if(n <= 0) throw new IllegalArgumentException("cannot have a path less than 0");
        if(v < 0 || v > G.V()) throw new IndexOutOfBoundsException("must have vertex between 1 and V");

        if(longPath.size() == n+1){
         boolean sw = true;   
        for(int p = 0; p < n; p++)
            if(G.getType(longPath.get(p), longPath.get(p+1)) != tr[p]
                    || G.getType(longPath.get(p+1), longPath.get(p)) !=tr[p]) sw = false;
        if(sw == true)
            printPath(longPath,tr);
        return;
        }

        for(int w:G.adj(v)){
                longPath.add(w);
                edgeTo[w] = v;
                searchPaths(G,w,n,tr);
                if(longPath.size()>1)
                  longPath.remove(longPath.size()-1);
               // System.out.println(longPath.size()+" "+longPath.get(longPath.size()-1));

        }
        
    }
    
    private void printPath(ArrayList<Integer> l, String[] trans){
        for(int j=0; j<l.size()-1;j++)
          System.out.print(l.get(j)+" - "+trans[j]+ " - ");
        System.out.println(l.get(l.size()-1));
    }
    
    public void printAllPaths(Digraph G,int v, int n, String[] t){
        longPath.add(v);
        searchPaths(G,v,n,t);
        for(int j=0;j<visited.length;j++)
            visited[j] = false;
    }
    
    private void dfs(Digraph G, int v){
        marked[v] = true;
        for (int w:G.adj(v)){
            if(!marked[w]){
                edgeTo[w] = v;
                dfs(G,w);
            }
            
        }
        
    }
    
    public boolean hasPathTo(int v){
        return marked[v];
    }
    
    public Iterable<Integer> pathTo(int v){
        
        if(!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for(int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
    
}
