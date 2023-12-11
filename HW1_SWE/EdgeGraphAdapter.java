import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    public EdgeGraphAdapter(Graph g) { this.g = g; }

    public boolean addEdge(Edge e) {
      if (hasEdge(e)) {
        return false;
      }
      else {
        if (!hasNode(e.getSrc())){
          g.addNode(e.getSrc());
        }
        if (!hasNode(e.getDst())){
          g.addNode(e.getDst());
        }
        g.addEdge(e.getSrc(), e.getDst());
      }
      return true;
    }

    public boolean hasNode(String n) {
      return g.hasNode(n);
    }

    public boolean hasEdge(Edge e) {
      return g.hasEdge(e.getSrc(), e.getDst());
    }

    public boolean removeEdge(Edge e) {
	    if (!hasEdge(e)) {
        return false;
      }
      else {
      
        g.removeEdge(e.getSrc(), e.getDst());


        if (g.succ(e.getSrc()).isEmpty() && g.pred(e.getSrc()).isEmpty()){
          g.removeNode(e.getSrc());
        }
        if (g.succ(e.getDst()).isEmpty() && g.pred(e.getDst()).isEmpty()){
          g.removeNode(e.getDst());
        }
      }
      return true;
    }

    public List<Edge> outEdges(String n) {
      List<Edge> edgeList = new LinkedList<Edge>();
      
      for (int i = 0; i < g.succ(n).size(); i ++) {
        edgeList.add(new Edge(n, g.succ(n).get(i)));
      }

      return edgeList;
    }

    public List<Edge> inEdges(String n) {
      List<Edge> edgeList = new LinkedList<Edge>();
      
      for (int i = 0; i < g.pred(n).size(); i ++) {
        edgeList.add(new Edge(g.pred(n).get(i), n));
      }
      return edgeList;
    }

    public List<Edge> edges() {
      List<Edge> edgeList = new LinkedList<Edge>();
      for (int i = 0; i < g.nodes().size() ; i ++){
          String n1 = g.nodes().get(i);
          for (int j = 0; j < outEdges(n1).size(); j ++) {
              edgeList.add(outEdges(n1).get(j));
          }
      }
      return edgeList;
      }
    

    public EdgeGraph union(EdgeGraph g) {
      EdgeGraph unionGraph = this;

      List <Edge>  edgeSet = g.edges();
      if (edgeSet.size() == 0) {
            return unionGraph;
      }

	    for (int i = 0; i < g.edges().size(); i ++){ //go through the nodes of g
            Edge edge = g.edges().get(i);
            if (!unionGraph.hasEdge(edge)) {       //if output graph doesn't include the current node
                unionGraph.addEdge(edge);          //add the node to the output graph
            }
      }
      return unionGraph;
    }

    public boolean hasPath(List<Edge> e) {
	    for (int i = 0 ; i < e.size() - 1; i ++) {
        if (e.get(i).getDst() != e.get(i+1).getSrc()) {
            throw new BadPath();
        }
      }
      for (int j = 0 ; j < e.size(); j ++) {
          if(!hasEdge(e.get(j))) {
            return false;
          }
      }
       return true;
   
    }

}
