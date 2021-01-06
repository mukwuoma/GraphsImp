package tests;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import graphs.Graph;
import junit.framework.TestCase;

/**
 * Please put your own test cases into this file.
*/
public class StudentTests extends TestCase {
	
	
	public void testDijkstras() {
		Graph<Double> graph = createGraph();
		String startVertex = "ST";
		String answer = runDijkstras(graph, startVertex);
		System.out.println(answer);
	}
	
		private Graph<Double> createGraph() {
			Graph<Double> graph = new Graph<Double>();

			/* Adding vertices to the graph */
			String[] vertices = new String[] {"A", "ST"};
			for (int i = 0; i < vertices.length; i++) {
				graph.addVertex(vertices[i], new Double(i + 1000.50));
			}
				
			/* Adding directed edges */
			graph.addDirectedEdge("ST", "A", 6);
//			graph.addDirectedEdge("ST", "B", 61);
//			graph.addDirectedEdge("A", "C", 2);
//			graph.addDirectedEdge("B", "A", 4);
//			graph.addDirectedEdge("B", "D", 3);
//			graph.addDirectedEdge("C", "D", 5);
//			graph.addDirectedEdge("D", "C", 7);
//			graph.addDirectedEdge("M", "ST", 6);

	//	h.addElement(6);
		//graph;System.out.println(h.toString());
			return graph;
		}
		
		private static String runDijkstras(Graph<Double> graph, String startVertex) {
			ArrayList<String> shortestPath = new ArrayList<String>();
			StringBuffer results = new StringBuffer();
			
			Set<String> vertices = graph.getVertices();
			TreeSet<String> sortedVertices = new TreeSet<String>(vertices);
			for (String endVertex : sortedVertices) {
				int shortestPathCost = graph.doDijkstras(startVertex, endVertex,
						shortestPath);
				results.append("Shortest path cost between " + startVertex + " " + endVertex
						+ ": " + shortestPathCost);
				results.append(", Path: " + shortestPath + "\n");
				shortestPath.clear();
			}
			
			return results.toString();
		}
}