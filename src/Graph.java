// graph represented by adjacency list
import java.util.Iterator;
import java.util.LinkedList;

public class Graph {
	
	int V;
	LinkedList<Edge> adjList[];
	LinkedList<Node> nodeList;

	/*
	 * creates an empty graph with n nodes and no edges.
	 */
	public Graph(int n) {
		this.V = n;

		// size of the array = number of vertices
		adjList = new LinkedList[V];
		nodeList = new LinkedList();

		// names each node
		for (int i = 0; i < V; i++) {
			nodeList.add(new Node(i));
		}

		// Creates a new linked list for each index
		for (int i = 0; i < V; i++) {
			adjList[i] = new LinkedList<>();
		}
	}

	/*
	 * adds to the graph an edge connecting u and v. The type for this new edge
	 * is as indicated by the last parameters. This method throws a
	 * GraphException if either node does not exist or if there is already an
	 * edge connecting the given vertices.
	 */
	public void insertEdge(Node u, Node v, String edgeType) throws GraphException {
		// throw exception if either node does not exits in the graph
		if (!nodeList.contains(u) || !nodeList.contains(v)) {
			throw new GraphException("Node does not exist");
		} else {
			// create two new edges going back and forth between vertices
			Edge newEdge = new Edge(u, v, edgeType);
			Edge newEdge2 = new Edge(v, u, edgeType);
			// if there is already an edge connecting the given vertices throw
			// exception
			if (adjList[u.getName()].contains(newEdge) || adjList[v.getName()].contains(newEdge2)) {
				throw new GraphException("There is already an edge connecting the given vertices.");
			}
			// else add the edges under each node name in the adjacency list
			else {
				adjList[u.getName()].add(newEdge);
				adjList[v.getName()].add(newEdge2);
			}
		}
	}

	/*
	 * returns the node with the specified name. If no node with this name
	 * exists, the method should throw a GraphException
	 */
	public Node getNode(int name) {
		// vertices are named 0 - V-1 , if the name is out of that range throw
		// an exception
		if (name >= V || name < 0) {
			throw new GraphException("Node does not exist");
		}
		// otherwise return the node at the given index/name
		else {
			return nodeList.get(name);
		}
	}

	/*
	 * returns a Java Iterator storing all the edges incident on node u. It
	 * returns null if u does not have any edges incident on it.
	 */
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		// if u is not in the graph throw an exception
		if (!nodeList.contains(u)) {
			throw new GraphException("Node does not exist");
		}
		// if u is in the graph
		else {
			// if the linked list representing incident edges on u is empty,
			// return null
			if (adjList[u.getName()].size() == 0) {
				return null;
			}
			// else return an iterator of all incident edges
			else {
				return adjList[u.getName()].iterator();
			}
		}
	}

	/*
	 * returns the edge connecting nodes u and v. This method throws a
	 * GraphException if there is no edge between u and v
	 */
	public Edge getEdge(Node u, Node v) throws GraphException {
		// if u or v are not in the graph, throw exception
		if (!nodeList.contains(u) || !nodeList.contains(v)) {
			throw new GraphException("Node does not exist");
		}
		// if they are both in the graph
		else {
			// for every edge in the linked list representing edges of u, if the
			// second end point matches v return the edge
			for (Edge edge : adjList[u.getName()]) {
				if (edge.secondEndpoint().equals(v)) {
					return edge;
				}
			}
			// if all edges are checked and there are no edge between u and v
			// throw exception
			throw new GraphException("There is no edge between u and v");
		}
	}

	/*
	 *  returns true if and only if nodes u and v are adjacent.
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		// if u or v are not in the graph, throw exception
		if (!nodeList.contains(u) || !nodeList.contains(v)) {
			throw new GraphException("Node does not exist");
		}
		// if they are both in the graph
		else {
			// for every edge in the linked list representing edges of u, if the
			// second end point matches v return the edge
			for (Edge edge : adjList[u.getName()]) {
				if (edge.secondEndpoint().equals(v)) {
					return true;
				}
			}
			// if all edges are checked and there are no edge between u and v
			// return false
			return false;
		}
	}
}
