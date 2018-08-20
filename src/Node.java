// This class represent a node in the graph
public class Node {
	int name;
	boolean mark;

	/*
	 * Creates a node with the given name. The name of a node is an integer
	 * value between 0 and n-1, where n is the number of vertices in the graph
	 * to which the node belongs
	 */
	public Node(int name) {
		this.name = name;
	}

	/*
	 * marks the node with the specified value
	 */
	public void setMark(boolean mark) {
		this.mark = mark;
	}

	/*
	 * returns the value with which the node has been marked
	 */
	public boolean getMark() {
		return this.mark;
	}

	/*
	 * returns the name of the node
	 */
	public int getName() {
		return this.name;
	}

}
