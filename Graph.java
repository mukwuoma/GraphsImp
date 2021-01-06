package graphs;

import java.util.*;
import java.util.Map.Entry;

/**
 * Implements a graph. There are two maps: one map for adjacency properties
 * (adjancencyMap) and one map (dataMap) to keep track of the data associated
 * with a vertex.
 * 
 * 
 * @param <E>
 */
public class Graph<E> {
	/* You must use the following maps in your implementation */
	private HashMap<String, HashMap<String, Integer>> adjacencyMap = new HashMap<>();
	private HashMap<String, E> dataMap = new HashMap<>();

	public void addVertex(String vertexName, E data) {
		if (adjacencyMap.containsKey(vertexName)) {
			throw new IllegalArgumentException("Vertex already exists");

		}
		adjacencyMap.put(vertexName, new HashMap<String, Integer>());
		dataMap.put(vertexName, data);
	}

	public void addDirectedEdge(String startVertexName, String endVertexName, int cost) {

		if (!adjacencyMap.containsKey(startVertexName) || !adjacencyMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("Vertex Does Not Exist!");
		} else {
			adjacencyMap.get(startVertexName).put(endVertexName, cost);
		}

	}

	@Override
	public String toString() {
		TreeMap<String, HashMap<String, Integer>> mapData = new TreeMap<>(adjacencyMap);
		String answer = "";
		answer += "Vertices: " + mapData.keySet().toString();
		answer += "\nEdges: ";

		for (String key : mapData.keySet()) {
			answer += "\nVertex(" + key + ")--->" + mapData.get(key).toString();
		}

		return answer;

	}

	public Map<String, Integer> getAdjacentVertices(String vertexName) {
		Map<String, Integer> adjVertices = new HashMap<>();
		for (String name : adjacencyMap.keySet()) {
			if (adjacencyMap.get(vertexName).containsKey(name)) {
				adjVertices.put(name, adjacencyMap.get(vertexName).get(name));
			}
		}
		return adjVertices;
	}

	public int getCost(String startVertexName, String endVertexName) {
		if (!adjacencyMap.containsKey(startVertexName) || !adjacencyMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		return adjacencyMap.get(startVertexName).get(endVertexName);
	}

	public Set<String> getVertices() {
		return adjacencyMap.keySet();
	}

	public E getData(String vertex) {

		if (!dataMap.containsKey(vertex)) {
			throw new IllegalArgumentException("Vertex not found!");
		}
		return dataMap.get(vertex);
	}

	public void doDepthFirstSearch(String startVertexName, CallBack<E> callback) {
		if (!adjacencyMap.containsKey(startVertexName)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		Stack<String> discovered = new Stack<>();
		TreeSet<String> visited = new TreeSet<>();
		discovered.add(startVertexName);
		while (!discovered.isEmpty()) {
			String node = discovered.pop();
			if (!visited.contains(node)) {
				visited.add(node);
				callback.processVertex(node, dataMap.get(node));
				for (String successor : adjacencyMap.get(node).keySet()) {
					if (!visited.contains(successor)) {
						discovered.add(successor);
					}
				}
			}
		}

	}

	public void doBreadthFirstSearch(String startVertexName, CallBack<E> callback) {
		if (!adjacencyMap.containsKey(startVertexName)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		Deque<String> discovered = new ArrayDeque<>();
		Set<String> visited = new TreeSet<>();
		discovered.add(startVertexName);
		while (!discovered.isEmpty()) {
			String node = discovered.poll();
			if (!visited.contains(node)) {
				visited.add(node);
				callback.processVertex(node, dataMap.get(node));
				for (String successor : adjacencyMap.get(node).keySet()) {
					if (!visited.contains(successor)) {
						discovered.add(successor);
					}
				}
			}
		}

	}

	public int doDijkstras(String startVertexName, String endVertexName, ArrayList<String> shortestPath) {
		if (!adjacencyMap.containsKey(startVertexName) || !adjacencyMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("Vertex Does Not Exist!");
		}
		Queue<String> queue = new ArrayDeque<>();
		HashSet<String> set = new HashSet<String>();
		Map<String, TreeMap<String, Integer>> map = new HashMap<>();
		for (String vertex : adjacencyMap.keySet()) {
			map.put(vertex, new TreeMap<>());
		}
		map.get(startVertexName).put(startVertexName, 0);
		queue.add(startVertexName);
		String pre = startVertexName;
		String entry = findMin(map, set);
		while (!queue.isEmpty()) {
			set.add(entry);
			for (String node : getAdjacentVertices(entry).keySet()) {
				for (String ans : map.get(entry).keySet()) {
					pre = ans;
				}
				if (!set.contains(node)) {
					Integer cost = getCost(queue.element(), node);
					Integer otherCost = map.get(queue.element()).get(pre);
					for (String keys : map.get(node).keySet()) {
						pre = keys;
					}
					if (map.get(node).isEmpty()) {
						map.get(node).put(queue.element(), cost + otherCost);
					} else if (cost + otherCost < map.get(node).get(pre)) {
						map.get(node).remove(pre);
						map.get(node).put(queue.element(), cost + otherCost);
					}
				}
			}
			queue.poll();
			entry = findMin(map, set);
			if (entry != null) {
				queue.add(entry);
			}
		}
		String vertex = endVertexName;

		while (!map.get(vertex).isEmpty()) {
			shortestPath.add(0, vertex);
			String predecessor = map.get(vertex).firstKey();
			if (vertex.equals(predecessor)) {
				break;
			} else {
				vertex = predecessor;
			}
		}
		if (map.get(endVertexName).isEmpty()) {
			shortestPath.add("None");
			return -1;
		} else {
			return map.get(endVertexName).firstEntry().getValue();
		}
	}

	private String findMin(Map<String, TreeMap<String, Integer>> map, Set<String> set) {

		Entry<String, Integer> min = null;
		String result = null;
		for (String keys : map.keySet())
			if (!set.contains(keys)) {
				for (Entry<String, Integer> entry : map.get(keys).entrySet()) {
					if (min == null || min.getValue() > entry.getValue()) {
						min = entry;
						result = keys;
					}
				}
			}
		return result;

	}

}