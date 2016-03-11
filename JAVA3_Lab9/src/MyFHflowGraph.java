
public class MyFHflowGraph<E> {

   E startVert, endVert;

   public void setStartVert(E start) {
      
   }
   
   public void setEndVert(E end) {
      
   }
   
   public void addEdge(E start, E end, double value) {
      
   }
   
   public void showResAdjTable() {
      
   }
   
   public void showFlowAdjTable() {
      
   }
   
   public double findMaxFlow() {
      double maxflow = 0;
      return maxflow;
   }
}

/*public boolean dijkstra(E x)
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
   FHflowVertex<E> start, stop, vert;
   Stack< FHflowVertex<E> > pathStack = new Stack< FHflowVertex<E> >();

   start = getVertexWithThisData(x1);
   stop = getVertexWithThisData(x2);
   if (start == null || stop == null)
      return false;

   // perhaps add argument opting to skip if pre-computed
   dijkstra(x1);
   
   if (stop.dist == FHflowVertex.INFINITY)
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
}*/
