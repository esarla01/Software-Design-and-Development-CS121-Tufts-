import java.util.*;

public class ListGraph implements Graph {
    private HashMap<String, LinkedList<String>> nodes = new HashMap<>();

    public boolean addNode(String n) {       
        if (nodes.containsKey(n)){
            return false;           
        } else {
            nodes.put(n,new LinkedList<String>());
        }
        return true;
    }

    public boolean addEdge(String n1, String n2) {
        if (!(nodes.keySet().contains(n1) && nodes.keySet().contains(n2)))  {
            throw new NoSuchElementException();
        }
        if (nodes.get(n1).contains(n2)){
            return false;
        } else {
            nodes.get(n1).add(n2);
        }
            return true;
    }
	    
    public boolean hasNode(String n) {
	     if (nodes.isEmpty()) {
            return false;
         }  else {
            return nodes.containsKey(n);
         }
    }

    public boolean hasEdge(String n1, String n2) {
	    if (nodes.isEmpty() || !nodes.keySet().contains(n1)){
            return false;
        } else {
            return nodes.get(n1).contains(n2);
        }
    }


    public boolean removeNode(String n) {
	    if (hasNode(n)){
            for (String key : nodes()){ 
                if (nodes.get(key).contains(n)){
                    nodes.get(key).remove(n); 
                }
            }
            nodes.remove(n);
            return true;
        } 
        return false;
    }

    public boolean removeEdge(String n1, String n2) {
        if (!(nodes.keySet().contains(n1) && nodes.keySet().contains(n2)))  {
            throw new NoSuchElementException();
        }
        if (nodes.get(n1).contains(n2)){
            nodes.get(n1).remove(n2);
            return true;
        } else {
            return false;
        }

    }

    public List<String> nodes() {
            List<String> ll = new LinkedList<String>();
            for (String n : nodes.keySet()) { 
                ll.add(n);
            }
            return ll;
    }

    public List<String> succ(String n) {
	    if (!nodes.containsKey(n)) {
            throw new NoSuchElementException();
        } else {
            return nodes.get(n);         
        }
    }

    public List<String> pred(String n) {
        List<String> ll = new LinkedList<String>();
	    if (!nodes.containsKey(n)) {
            throw new NoSuchElementException();
        } else {
            for (String key : nodes.keySet()) { // iterate over key of HashMap
                if (nodes.get(key).contains(n)){// get corresponding successor lists
                    ll.add(key);
                }
            }
        }
        return ll;  
    }

  
    public Graph union(Graph g) {
        Graph unionGraph = this;

        List <String>  nodeSet = g.nodes();
        if (nodeSet.size() == 0) {
            return unionGraph;
        }

	    for (int i = 0; i < g.nodes().size(); i ++){ //go through the nodes of g
            String node = nodeSet.get(i);
            if (!unionGraph.hasNode(node)) {       //if output graph doesn't include the current node
                unionGraph.addNode(node);          //add the node to the output graph
            }
        }
        for (int i = 0; i < g.nodes().size(); i ++) {  //go through the nodes of g 
            String node = nodeSet.get(i);
            for (int j = 0; j < g.succ(node).size(); j++) {    //go thorugh the edges of the node
                String edge = g.succ(node).get(i);
                if (!unionGraph.hasEdge(node, edge)) {  //if output graph doesn't include the current edge
                    unionGraph.addEdge(node, edge);     //map the edge to the relevant node 
                } 
            }
        }
        return unionGraph;
    }

    public Graph subGraph(Set<String> nodes) {
	    Graph g = this;
        for (String key : this.nodes.keySet()) { // iterate over key of HashMap
            if (!nodes.contains(key)) {
                g.removeNode(key);
            }
        }    
        return g;
    }

    public boolean connected(String n1, String n2) {

        if (!(nodes.keySet().contains(n1) && nodes.keySet().contains(n2)))  {
            throw new NoSuchElementException();
        }

        List<String> visited = new LinkedList<String>();

	    return connectedHelper(n1, n2, visited);
    }

    public boolean connectedHelper(String n1, String n2, List<String> visited) {

        visited.add(n1);

        if (succ(n1).contains(n2)) { //if n1 is connected to n2, return true;
            return true;
        }
        if (succ(n1).isEmpty()) {   //if node doesnt have any succesors, return false
            return false;
        }

        for (int i = 0; i < succ(n1).size(); i ++) {
            String n = succ(n1).get(i);
            if (! visited.contains(n)) {
                return connectedHelper(n, n2, visited);
            }
        }

        return false; //if all the succesors are visited, return false

    }
    
}
