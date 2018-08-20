// This class represents an edge in the graph
public class Edge {
	Node firstEndpoint;
	Node secondEndpoint;
	String type;

	/*
	 * The first two parameters are the end points of the edge. The last
	 * parameter is the type of the edge, which for this project can be either
	 * “corridor”, “wall”, ”thickWall”, or ”metalWall”
	 */
	public Edge(Node u, Node v, String type) {
		this.firstEndpoint = u;
		this.secondEndpoint = v;
		this.type = type;
	}
	/*
	 * returns first end point of the edge
	 */
	public Node firstEndpoint(){
		return this.firstEndpoint;
	}
	/*
	 * returns second end point of the edge
	 */
	public Node secondEndpoint(){
		return this.secondEndpoint;
	}
	/*
	 * returns the type of edge
	 */
	public String getType(){
		return this.type;
	}
	/*
	 * sets the type of the edge to the specified value
	 */
	public void setType(String type){
		this.type = type;
	}

}
