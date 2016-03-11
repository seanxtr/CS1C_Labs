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
      src.addToFlowAdjList(dst, cost);
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
   
   public double findMaxFlow() {
      return 0;
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
