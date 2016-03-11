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
   
   
   public void addEdge(E source, E dest, double cost)
   {
      FHflowVertex<E> src, dst;

      // put both source and dest into vertex list(s) if not already there
      src = addToVertexSet(source);
      dst = addToVertexSet(dest);

      // add dest to source's adjacency list
      src.addToAdjList(dst, cost);
   }
   
   public void addEdge(E source, E dest, int cost)
   {
      addEdge(source, dest, (double)cost);
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
   
   
   public void showAdjTable()
   {
      Iterator< FHvertex<E> > iter;

      System.out.println( "------------------------ ");
      for (iter = vertexSet.iterator(); iter.hasNext(); )
         (iter.next()).showAdjList();
      System.out.println();
   }
   
   public HashSet< FHvertex<E> > getVertSet() 
   { 
      return (HashSet< FHvertex<E> > )vertexSet.clone(); 
   }

   /**
    * Method to remove all vertexes
    */
   public void clear()
   {
      vertexSet.clear();
   }
   
   public void setGraph( ArrayList< FHedge<E> > edges )
   {
      int k, numEdges;
      numEdges = edges.size();

      clear();
      for (k = 0; k < numEdges; k++)
         addEdge( edges.get(k).source.data,  
            edges.get(k).dest.data,  edges.get(k).cost);
   }

   // algorithms
   public boolean dijkstra(E x)
   {
      FHvertex<E> w, s, v;
      Pair<FHvertex<E>, Double> edge;
      Iterator< FHvertex<E> > iter;
      Iterator< Pair<FHvertex<E>, Double> > edgeIter;
      Double costVW;
      Deque< FHvertex<E> > partiallyProcessedVerts
       = new LinkedList< FHvertex<E> >();

      s = getVertexWithThisData(x);
      if (s == null)
         return false;

      // initialize the vertex list and place the starting vert in p_p_v queue
      for (iter = vertexSet.iterator(); iter.hasNext(); )
         iter.next().dist = FHvertex.INFINITY;
      
      s.dist = 0;
      partiallyProcessedVerts.addLast(s);

      // outer dijkstra loop
      while( !partiallyProcessedVerts.isEmpty() )
      {
         v = partiallyProcessedVerts.removeFirst(); 
         
         // inner dijkstra loop: for each vert adj to v, lower its dist 
         // to s if you can
         for (edgeIter = v.adjList.iterator(); edgeIter.hasNext(); )
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
            }
         }
       }
      return true;
   }
   
   // applies dijkstra, print path - could skip dijkstra()
   public boolean showShortestPath(E x1, E x2)
   {
      FHvertex<E> start, stop, vert;
      Stack< FHvertex<E> > pathStack = new Stack< FHvertex<E> >();

      start = getVertexWithThisData(x1);
      stop = getVertexWithThisData(x2);
      if (start == null || stop == null)
         return false;

      // perhaps add argument opting to skip if pre-computed
      dijkstra(x1);
      
      if (stop.dist == FHvertex.INFINITY)
      {
         System.out.println("No path exists.");
         return false;
      }

      for (vert = stop; vert != start; vert = vert.nextInPath)
         pathStack.push(vert);
      pathStack.push(vert);

      System.out.println( "Cost of shortest path from " + x1 
            + " to " + x2 + ": " + stop.dist );
      while (true)
      {
         vert = pathStack.pop();
         if ( pathStack.empty() )
         {
            System.out.println( vert.data );
            break;
         }
         System.out.print( vert.data + " -> ");
      }
      return true;
   }

   // applies dijkstra, prints distances - could skip dijkstra()
   public boolean showDistancesTo(E x)
   {
      
      Iterator< FHvertex<E> > iter;
      FHvertex<E> vert;

      if (!dijkstra(x))
         return false;
 
      for (iter = vertexSet.iterator(); iter.hasNext(); )
      {
         vert = iter.next();
         System.out.println( vert.data + " " + vert.dist); 
      }
      System.out.println();
      return true;
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
