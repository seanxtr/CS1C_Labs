// ------------ Class Definition of FHflowGraph ---------------
import java.util.*;

// --- Internal class FHvertex --------------------------------
class FHflowVertex <E>
{
   // class variables and methods
   public static Stack<Integer> keyStack = new Stack<Integer>();
   public static final int KEY_ON_DATA = 0, KEY_ON_DIST = 1;
   public static int keyType = KEY_ON_DATA;
   public static final double INFINITY = Double.MAX_VALUE;
   
   // instance variables
   public HashSet< Pair<FHflowVertex <E>, Double> > flowAdjList, resAdjList;
   public E data;
   public double dist;
   public FHflowVertex <E> nextInPath;
   
   /**
    * Default constructor
    */
   public FHflowVertex () { this(null); }

   /**
    * 1-parameter constructor
    * @param x       data value
    */
   public FHflowVertex ( E x )
   {
      // initialize value
      data = x;
      dist = INFINITY;
      nextInPath = null;
      
      // initialize hashsets
      flowAdjList = new HashSet< Pair<FHflowVertex <E>, Double> >();
      resAdjList = new HashSet< Pair<FHflowVertex <E>, Double> >();
   }
   
   /**
    * Add successor vertex to current vertex in flow graph
    * @param neighbor       successor vertex
    * @param cost           value
    */
   public void addToFlowAdjList(FHflowVertex <E> neighbor, double cost)
   {
      flowAdjList.add( new Pair<FHflowVertex <E>, Double> (neighbor, cost) );
   }
   
   /**
    * Output all vertex in flow graph for inspection
    */
   public void showFlowAdjList()
   {
      Iterator< Pair<FHflowVertex<E>, Double> > iter ;
      Pair<FHflowVertex<E>, Double> pair;

      System.out.print( "Flow adj List for " + data + ": ");
      for (iter = flowAdjList.iterator(); iter.hasNext(); )
      {
         pair = iter.next();
         System.out.print( pair.first.data + "(" 
            + String.format("%3.1f", pair.second)
            + ") " );
      }
      System.out.println();
   }
   
   /**
    * Add successor vertex to current vertex in residual graph
    * @param neighbor       successor vertex
    * @param cost           value
    */
   public void addToResAdjList(FHflowVertex <E> neighbor, double cost)
   {
      resAdjList.add( new Pair<FHflowVertex <E>, Double> (neighbor, cost) );
   }
   
   /**
    * Output all vertex in residual graph for inspection
    */
   public void showResAdjList()
   {
      Iterator< Pair<FHflowVertex<E>, Double> > iter ;
      Pair<FHflowVertex<E>, Double> pair;

      System.out.print( "Residual adj List for " + data + ": ");
      for (iter = resAdjList.iterator(); iter.hasNext(); )
      {
         pair = iter.next();
         System.out.print( pair.first.data + "(" 
            + String.format("%3.1f", pair.second)
            + ") " );
      }
      System.out.println();
   }
   
   public boolean equals(Object rhs)
   {
      FHflowVertex <E> other = (FHflowVertex <E>)rhs;
      switch (keyType)
      {
      case KEY_ON_DIST:
         return (dist == other.dist);
      case KEY_ON_DATA:
         return (data.equals(other.data));
      default:
         return (data.equals(other.data));
      } 
   }
   public int hashCode()
   {
      switch (keyType)
      {
      case KEY_ON_DIST:
         Double d = dist;
         return (d.hashCode());
      case KEY_ON_DATA:
         return (data.hashCode());
      default:
         return (data.hashCode());
      }  
   }
   public static boolean setKeyType( int whichType )
   {
      switch (whichType)
      {
      case KEY_ON_DATA:
      case KEY_ON_DIST:
         keyType = whichType;
         return true;
      default:
         return false;
      }
   }
   public static void pushKeyType() { keyStack.push(keyType); }
   public static void popKeyType() { keyType = keyStack.pop(); };
}

//-------------------------------------------------------------------------
public class FHflowGraph<E>
{
   protected HashSet< FHflowVertex<E> > vertexSet;
   protected FHflowVertex<E> startVert, endVert;
   
   /**
    * Default constructor
    */
   public FHflowGraph ()
   {
      vertexSet = new HashSet< FHflowVertex<E> >();
   }
   
   /**
    * Setter for start vertex
    * @param start       data value
    * @return            true if the value exists
    */
   public boolean setStartVert(E start) {
      startVert = getVertexWithThisData(start);
      return startVert != null;
   }
   
   /**
    * Setter for end vertex
    * @param start       data value
    * @return            true if the value exists
    */
   public boolean setEndVert(E end) {
      endVert = getVertexWithThisData(end);
      return endVert != null;
   }
   
   /**
    * Method to output Residual graph for inspection
    */
   public void showResAdjTable() {
      Iterator<FHflowVertex<E>> iter;

      System.out.println( "------------------------ ");
      for (iter = vertexSet.iterator(); iter.hasNext(); )
         (iter.next()).showResAdjList();
      System.out.println();
   }
   
   /**
    * Method to output flow graph for inspection
    */
   public void showFlowAdjTable() {
      Iterator<FHflowVertex<E>> iter;

      System.out.println( "------------------------ ");
      for (iter = vertexSet.iterator(); iter.hasNext(); )
         (iter.next()).showFlowAdjList();
      System.out.println();
   }
   
   /**
    * Method to add edge to the graph
    * @param source       start value
    * @param dest         end value
    * @param cost         cost value of the edge
    */
   public void addEdge(E source, E dest, double cost)
   {
      FHflowVertex<E> src, dst;

      // put both source and dest into vertex list(s) if not already there
      src = addToVertexSet(source);
      dst = addToVertexSet(dest);

      // add forward and backward edge to residual graph
      src.addToResAdjList(dst, cost);
      dst.addToResAdjList(src, 0);
      
      // add forward edge to flow graph
      src.addToFlowAdjList(dst, 0);
   }
   
   /**
    * adds vertex with x in it, and always returns ref to it
    * @param x        data value
    * @return         the vertex object has the value
    */
   public FHflowVertex<E> addToVertexSet(E x)
   {
      FHflowVertex<E> retVal, vert;
      boolean successfulInsertion;
      Iterator< FHflowVertex<E> > iter;

      // save sort key for client
      FHflowVertex.pushKeyType();
      FHflowVertex.setKeyType(FHflowVertex.KEY_ON_DATA);

      // build and insert vertex into master list
      retVal = new FHflowVertex<E>(x);
      successfulInsertion = vertexSet.add(retVal);
      
      if ( successfulInsertion )
      {
         FHflowVertex.popKeyType();  // restore client sort key
         return retVal;
      }

      // the vertex was already in the set, so get its ref
      for (iter = vertexSet.iterator(); iter.hasNext(); )
      {
         vert = iter.next();
         if (vert.equals(retVal))
         {
            FHflowVertex.popKeyType();  // restore client sort key
            return vert;
         }
      }

      FHflowVertex.popKeyType();  // restore client sort key
      return null;   // should never happen
   }
   
   /**
    * Method contains main algorithm to find max flow
    * @return         max flow
    */
   public double findMaxFlow() {
      double minCost, maxFlow = 0;
      boolean run = true;
      
      // generate the flow graph
      while (run) {
         run = establishNextFlowPath();
         if (!run) { break; }
         
         minCost = getLimitingFlowOnResPath();
         if (minCost <= 0) { break; }
         
         run = adjustPathByCost(minCost);
         if (!run) { break; }
      }
      
      // calculating max flow
      maxFlow = getMaxFlow();
      return maxFlow;
   }
   
   /**
    * helper method to probe back to calculate max flow
    * @return        max flow
    */
   private double getMaxFlow() {
      double maxflow = 0;
      FHflowVertex<E> vert;
      
      if (startVert == null || endVert == null)
         return -1;

      // in case no path was found
      if (endVert.dist == FHflowVertex.INFINITY)
         return -2;

      for (vert = endVert; vert != startVert; vert = vert.nextInPath)
         maxflow += vert.dist;
      return maxflow;
   }
   
   private boolean establishNextFlowPath() {
      FHflowVertex<E> w, s, v;
      Pair<FHflowVertex<E>, Double> edge;
      Iterator< FHflowVertex<E> > iter;
      Iterator< Pair<FHflowVertex<E>, Double> > edgeIter;
      Double costVW;
      Deque< FHflowVertex<E> > partiallyProcessedVerts
         = new LinkedList< FHflowVertex<E> >();

      s = startVert;
      if (s == null)
         return false;

      // initialize the vertex list and place the starting vert in p_p_v queue
      for (iter = vertexSet.iterator(); iter.hasNext(); )
         iter.next().dist = FHflowVertex.INFINITY;
      
      s.dist = 0;
      partiallyProcessedVerts.addLast(s);

      // outer loop
      while( !partiallyProcessedVerts.isEmpty() )
      {
         v = partiallyProcessedVerts.removeFirst(); 
         
         // inner dijkstra loop: for each vert adj to v, lower its dist 
         // to s if you can
         for (edgeIter = v.resAdjList.iterator(); edgeIter.hasNext(); )
         {
            edge = edgeIter.next();
            w = edge.first;
            costVW = edge.second;
            if ( v.dist + costVW < w.dist )
            {
               w.dist = v.dist + costVW;
               w.nextInPath = v; 
               
               // w now has improved distance, so add w to PPV queue
               partiallyProcessedVerts.addLast(w); 
               
               if (w.equals(endVert))
            	   return true;
            }
         }
      }
      return false;
   }
   
   private double getLimitingFlowOnResPath() {
      double minflow = FHflowVertex.INFINITY;
      double temp;
      FHflowVertex<E> vert;
      
      if (startVert == null || endVert == null)
         return 0;
      
      for (vert = endVert; vert != startVert; vert = vert.nextInPath) {
         temp = getCostOfResEdge(vert.nextInPath, vert);
         if (temp < minflow)
            minflow = temp;
      }
      return minflow;
   }
   
   /**
    * helper method to modify the costs of edge in both graph
    * @param cost         cost value
    * @return             
    */
   private boolean adjustPathByCost(double cost) {
      double maxflow = 0;
      FHflowVertex<E> vert;
      
      if (startVert == null || endVert == null)
         return false;
      
      for (vert = endVert; vert != startVert; vert = vert.nextInPath) {
         addCostToResEdge(vert.nextInPath, vert, cost*-1);
         addCostToResEdge(vert, vert.nextInPath, cost);
         addCostToFlowEdge(vert.nextInPath, vert, cost);
      }
      return true;
   }
   
   /**
    * Helper method to get cost value of edge from src to dst
    * @param src          source vertex
    * @param dst          destination vertex
    * @return             cost value
    */
   private double getCostOfResEdge(FHflowVertex<E> src, FHflowVertex<E> dst) {
      Iterator<Pair<FHflowVertex<E>, Double>> iter;
      Pair<FHflowVertex<E>, Double> pair;

      for (iter = src.resAdjList.iterator(); iter.hasNext();) {
         pair = iter.next();
         if (dst.equals(pair.first)) {
            return pair.second;
         }
      }
      return 0;
   }
   
   /**
    * Helper method to find dst in src's residuel list and add cost to it
    * @param src           source vertex
    * @param dst           destination vertex
    * @param cost          cost
    * @return              boolean indicate if dst is found
    */
   private boolean addCostToResEdge(FHflowVertex<E> src, 
      FHflowVertex<E> dst, double cost) {
      Iterator<Pair<FHflowVertex<E>, Double>> iter;
      Pair<FHflowVertex<E>, Double> pair;
      boolean found = false;
      
      for (iter = src.resAdjList.iterator(); iter.hasNext();) {
         pair = iter.next();
         if (dst.equals(pair.first)){
            found = true;
            pair.second += cost;
            break;
         }
      }
      return found;
   }
   
   /**
    * Helper method to find dst in src's flow list and modify its cost
    * @param src           source vertex
    * @param dst           destination vertex
    * @param cost          cost
    * @return              boolean indicate if dst is found
    */
   private boolean addCostToFlowEdge(FHflowVertex<E> src, 
      FHflowVertex<E> dst, double cost) {
      Iterator<Pair<FHflowVertex<E>, Double>> iter;
      Pair<FHflowVertex<E>, Double> pair;
      boolean found = false;
      
      for (iter = src.flowAdjList.iterator(); iter.hasNext();) {
         pair = iter.next();
         if (dst.equals(pair.first)) {
            found = true;
            pair.second += cost;
            break;
         }
      }
      
      if (!found) {
         for (iter = dst.flowAdjList.iterator(); iter.hasNext();) {
             pair = iter.next();
             if (src.equals(pair.first)) {
                found = true;
                pair.second -= cost;
                break;
             }
          }
      }
      return found;
   }
   
   /**
    * Method to remove all vertexes
    */
   public void clear()
   {
      vertexSet.clear();
   }
   
   /**
    * Method to find vertex with given data value
    * @param x           data value
    * @return            the vertex object
    */
   protected FHflowVertex<E> getVertexWithThisData(E x)
   {
      FHflowVertex<E> searchVert, vert;
      Iterator< FHflowVertex<E> > iter;

      // save sort key for client
      FHflowVertex.pushKeyType();
      FHflowVertex.setKeyType(FHflowVertex.KEY_ON_DATA);

      // build vertex with data = x for the search
      searchVert = new FHflowVertex<E>(x);


      // the vertex was already in the set, so get its ref
      for (iter = vertexSet.iterator(); iter.hasNext(); )
      {
         vert = iter.next();
         if (vert.equals(searchVert))
         {
            FHflowVertex.popKeyType();
            return vert;
         }
      }
      
      FHflowVertex.popKeyType();
      return null;   // not found
   }
}
