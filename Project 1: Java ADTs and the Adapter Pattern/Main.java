import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Main {
	
    // Run "java -ea Main" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)
    
	
    /**
     * Test for Add Node 
     */
    public static void test1() {
		Graph g = new ListGraph();
		assert !g.hasNode("a"); // test for empty graph
		assert g.addNode("a") ; 
		assert g.hasNode("a") ; // test for non-empty graph
		
    }



    public static void test2() {
	Graph g = new ListGraph();
	EdgeGraph eg = new EdgeGraphAdapter(g);
	Edge e = new Edge("a", "b");
	assert eg.addEdge(e);
	assert eg.hasEdge(e);
    }
    
	/**
     * Test for Add Edge
     */
    public static void test3() {
		Graph g = new ListGraph();
		try {
			g.addEdge("a", "b"); // test for non-existent nodes
		} catch (Exception e) {
			//System.out.println(e);
		}
		assert g.addNode("x");
		assert g.addNode("a"); 
		assert g.addNode("b"); 
		assert g.addNode("c"); 
		assert g.addEdge("x", "a");
		assert g.addEdge("a", "b");
		assert g.addEdge("b", "c");
		assert g.removeNode("a") ; // test for existing edge
		assert g.hasNode("x");
		assert !g.hasNode("a");
		assert g.hasNode("b");
		assert g.hasNode("c");
		assert !g.hasEdge("x", "a");
		assert !g.hasEdge("a", "c"); // test for non-existent edge
    }

	//Test Remove Node
	public static void test4() {
		Graph g = new ListGraph();
		assert !g.removeNode("a"); // test for removing non-existant node 
		assert g.addNode("a");
		assert g.addNode("b");
		assert g.addEdge("a", "b");
		assert g.removeNode("b"); // test for removing a existing node
		assert !g.hasEdge("a", "b");
		assert !g.hasNode("b");
		assert g.hasNode("a");
		
	}

	//Test Remove Edge
	public static void test5() {
		Graph g = new ListGraph();
		try {
			assert !g.removeEdge("a", "b"); // test for removing non-existant edge
		} catch (Exception e) {
			//System.out.println(e);
		}
		assert g.addNode("a");
		assert g.addNode("b");
		assert g.addNode("c");
		assert g.addEdge("a", "b"); // test for removing an existing edge


	}

	// Test if all the nodes in the graph are returned
	public static void test6() {
		Graph g = new ListGraph();
		//System.out.println(g.nodes());
		assert g.addNode("a");
		assert g.addNode("b");
		assert g.addNode("c");
		//System.out.println(g.nodes()); // test the key set for a non-empty hashmap
	}

	// Test Successor 
	public static void test7() {
		Graph g = new ListGraph();
		try {
			g.succ("a");
		} catch (Exception e) {
			System.out.println(e);
		}
		g.addNode("a");
		g.addNode("b");
		g.addNode("c");
		g.addNode("d");
		g.addNode("e");
		g.addEdge("a", "b");
		g.addEdge("a", "c");
		g.addEdge("a", "d");
		System.out.println(g.succ("a"));
	}

	//Test Predecessor
	public static void test8() {
		Graph g = new ListGraph();
		try {
			g.pred("a");
		} catch (Exception e) {
			System.out.println(e);
		}
		g.addNode("a");
		g.addNode("b");
		g.addNode("c");
		g.addNode("d");
		g.addNode("e");
		g.addEdge("a", "e");
		g.addEdge("b", "e");
		g.addEdge("c", "e");
		g.addEdge("d", "b");
		System.out.println(g.pred("e"));
	}
	
	//Test Union (only for List graph)
	public static void test9() {
		Graph g1 = new ListGraph();
		g1.addNode("a");
		g1.addNode("b");
		g1.addNode("c");
		g1.addNode("d");
		g1.addEdge("a", "b");
		g1.addEdge("a", "c");
		g1.addEdge("b", "a");
		g1.addEdge("b", "d");
		g1.addEdge("c", "d");
		g1.addEdge("d", "b");

		Graph g2 = new ListGraph();

		Graph unionGraph1 = g1.union(g2);
		System.out.println("Key: a, " + unionGraph1.succ("a"));
		System.out.println("Key: b, " + unionGraph1.succ("b"));
		System.out.println("Key: c, " + unionGraph1.succ("c"));
		System.out.println("Key: d, " + unionGraph1.succ("d"));
		try {
			System.out.println("Key: e, " + unionGraph1.succ("e"));
		} catch (Exception e) {
			System.out.println(e);
		}

		g1.addNode("b");
		g1.addNode("c");
		g1.addNode("e");
		g1.addNode("f");
		g1.addEdge("b", "b");
		g1.addEdge("b", "a");
		g1.addEdge("b", "d");
		g1.addEdge("c", "d");
		g1.addEdge("c", "a");
		g1.addEdge("c", "f");
		g1.addEdge("f", "e");
		g1.addEdge("f", "c");

		Graph unionGraph2 = g1.union(g2);
		System.out.println("Key: a, " + unionGraph2.succ("a"));
		System.out.println("Key: b, " + unionGraph2.succ("b"));
		System.out.println("Key: c, " + unionGraph2.succ("c"));
		System.out.println("Key: d, " + unionGraph2.succ("d"));
		System.out.println("Key: e, " + unionGraph2.succ("e"));
		System.out.println("Key: f, " + unionGraph2.succ("f"));
	}

	//Test Subgraph
	public static void test10() {
		Graph g1 = new ListGraph();
		g1.addNode("a");
		g1.addNode("b");
		g1.addNode("c");
		g1.addNode("d");
		g1.addEdge("a","b");
		g1.addEdge("a","d");
		g1.addEdge("b","b");
		g1.addEdge("d","b");

		Set<String> nodes = new HashSet<String>();
		nodes.add("a");
		nodes.add("b");
		nodes.add("c");
		nodes.add("e");
		
		Graph subGraph = g1.subGraph(nodes);
		System.out.println("SubGraph Key a:" + subGraph.succ("a"));
		System.out.println("SubGraph Key b:" + subGraph.succ("b"));
		System.out.println("SubGraph Key c:" + subGraph.succ("c"));
		try {
			System.out.println(subGraph.succ("d"));
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			System.out.println(subGraph.succ("e"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	//tests if pred returns the correct list of all edges to n.
// 	public static void test3(){
// 	Graph eg = new ListGraph();

// 	assert eg.addNode("a");
// 	assert eg.addNode("b");
// 	assert eg.addNode("c");
// 	assert eg.addNode("d");
// 	assert eg.addNode("e");
// 	assert eg.addNode("f");
	
// 	assert eg.addEdge("a", "b");
// 	assert eg.addEdge("a", "c");

// 	assert eg.addEdge("b", "c");
// 	assert eg.addEdge("b", "d");

// 	assert eg.addEdge("c", "d");
// 	assert eg.addEdge("c", "e");

// 	assert eg.addEdge("e", "c");
// 	assert eg.addEdge("e", "f");

// 	List<String> l1 = new List<String>();
// 	l1.add("a");
// 	l1.add("b");
// 	l1.add("e");

// 	List<String> l2 = eg.pred("c");

// 	assert l1.equals(l2);
// 	}

	public static void test11() {
		//without loop
		Graph g1 = new ListGraph();
		g1.addNode("a");
		g1.addNode("b");
		g1.addNode("c");
		g1.addNode("d");
		g1.addNode("e");

		g1.addNode("n");


		g1.addEdge("a","b");
		g1.addEdge("a","e");

		g1.addEdge("b","a");
		g1.addEdge("b","c");

		g1.addEdge("c","e");

		g1.addEdge("e","c");
		g1.addEdge("c","d");

		try {
			System.out.println(g1.connected("y", "z")); //non existant
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(g1.connected("a", "c")); //connected
		System.out.println(g1.connected("a", "e")); //connected from a different path
		System.out.println(g1.connected("a", "d")); //tests for cycles
		System.out.println(g1.connected("a", "n")); //not connected
	}



	// Test AddEdge (EDGEGRAPH)
	public static void test12() {
		Graph g = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(g);
		Edge e = new Edge("a", "b");
		assert eg.addEdge(e);
		assert eg.hasEdge(e);
	}

	// Test Remove Edge1 (EDGEGRAPH)
	public static void test13() {
		//naked nodes dissapear
		Graph g = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(g);
		Edge e = new Edge("a", "b");
		assert eg.addEdge(e);
		assert eg.removeEdge(e);
		assert !eg.hasNode("a");
		assert !eg.hasNode("b");
	}

	// Test Remove Edge2 (EDGEGRAPH)
	public static void test14() {
		//naked node dissapears and others stay
		Graph g1 = new ListGraph();
		EdgeGraph eg1 = new EdgeGraphAdapter(g1);
		Edge e1 = new Edge("a", "b");
		Edge e2 = new Edge("a", "c");
		assert eg1.addEdge(e1);
		assert eg1.addEdge(e2);

		assert eg1.removeEdge(e2);

		assert eg1.hasNode("a");
		assert eg1.hasNode("b");
		assert !eg1.hasNode("c");
	}

	// Test Remove Edge3 (EDGEGRAPH)
	public static void test15() {
		//all nodes stay
		Graph g2 = new ListGraph();
		EdgeGraph eg2 = new EdgeGraphAdapter(g2);
		Edge e3 = new Edge("a", "b");
		Edge e4 = new Edge("a", "c");
		Edge e5 = new Edge("b", "c");
		assert eg2.addEdge(e3);
		assert eg2.addEdge(e4);
		assert eg2.addEdge(e5);

		assert eg2.removeEdge(e5);

		assert eg2.hasNode("a");
		assert eg2.hasNode("b");
		assert eg2.hasNode("c");

	}

	// Test outEdge(EDGEGRAPH)
	public static void test16() {
		Graph g = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(g);
		assert eg.addEdge(new Edge("a", "b"));
		assert eg.addEdge(new Edge("a", "c"));
		assert eg.addEdge(new Edge("a", "d"));
		assert eg.addEdge(new Edge("a", "f"));
		assert eg.addEdge(new Edge("b", "d"));
		assert eg.addEdge(new Edge("c", "f"));
		assert eg.outEdges("a").size() == 4;
	}

	// Test inEdge(EDGEGRAPH)
	public static void test17() {
		Graph g = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(g);
		assert eg.addEdge(new Edge("a", "b"));
		assert eg.addEdge(new Edge("a", "c"));
		assert eg.addEdge(new Edge("c", "d"));
		assert eg.addEdge(new Edge("a", "f"));
		assert eg.addEdge(new Edge("b", "d"));
		assert eg.addEdge(new Edge("c", "f"));
		assert eg.inEdges("c").size() == 1;
	}

	// Test edges (EDGEGRAPH)
	public static void test18() {
		Graph g = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(g);
		assert eg.addEdge(new Edge("a", "b"));
		assert eg.addEdge(new Edge("a", "c"));
		assert eg.addEdge(new Edge("c", "d"));
		assert eg.addEdge(new Edge("a", "f"));
		assert eg.addEdge(new Edge("b", "d"));
		assert eg.addEdge(new Edge("c", "f"));
		assert eg.edges().size() == 6;
	}
	

	public static void test19() {
		Graph g = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(g);
		List<Edge> l = new LinkedList<Edge>();
		List<Edge> ll = new LinkedList<Edge>();
		List<Edge> lll = new LinkedList<Edge>();
		List<Edge> llll = new LinkedList<Edge>();


		Edge e1 = new Edge("a", "b");
		Edge e2 = new Edge("b", "c");
		Edge e3 = new Edge("c", "d");

		Edge e4 = new Edge("d", "e");

		l.add(e1);
		l.add(e2);
		l.add(e3);

		lll.add(e4);

		llll.add(e4);
		llll.add(e1);

		assert eg.addEdge(e1);
		assert eg.addEdge(e2);
		assert eg.addEdge(e3);


		assert eg.hasPath(l);	//test correct list
		assert eg.hasPath(ll);  //test empty list
		assert !eg.hasPath(lll);	//no path
		try {
			assert eg.hasPath(llll);	//badpath

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	

    public static void main(String[] args) {
		// test1();
		// test2();
		test3();
		// test4();
		// test5();
		// test6();
		// test7();
		// test8();
		// test9();
		// test10();
		// test11();
		// test12();
		// test13();
		// test14();
		// test15();
		// test16();
		// test17();
		// test18();
		// test19();
    }

}

