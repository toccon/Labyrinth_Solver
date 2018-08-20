
// This class represents the Labyrinth. A graph will be used to store the labyrinth and to find a solution for it.
import java.util.*;
import java.io.*;

public class Labyrinth {
	private int scale;
	private int numNodes; // Number of nodes in the labyrinth
	private int labWidth; // Width of the labyrinth (in rooms)
	private int labLength; // Length of the labyrinth (in rooms)
	private int bombsAllowed; // Number of walls allowed in solution
	private int acidBombsAllowed;
	private char[][] lab;
	private Graph graph;
	private Node entrance, exit;
	private Stack<Node> nodeStack;

	/*
	 * constructor for building a labyrinth from the contents of the input file.
	 * If the input file does not exist, this method should throw a
	 * LabyrinthException.
	 */
	public Labyrinth(String inputFile) {
		try {
			BufferedReader input;
			String line;
			char holder;
			int i, row = 0, node = 0, rows, cols;
			numNodes = 0;

			input = new BufferedReader(new FileReader(inputFile));

			// Process first four lines of the file
			scale = Integer.parseInt(input.readLine());
			labWidth = Integer.parseInt(input.readLine());
			labLength = Integer.parseInt(input.readLine());
			bombsAllowed = Integer.parseInt(input.readLine());
			acidBombsAllowed = Integer.parseInt(input.readLine());

			numNodes = labWidth * labLength;
			graph = new Graph(numNodes);
			lab = new char[2 * labLength - 1][2 * labWidth - 1];

			for (;;) {
				line = input.readLine();
				if (line == null) { // End of file
					// lab[2*labLength-1][2*labWidth-1]
					for (rows = 0; rows < 2 * labLength - 1; rows++) {
						for (cols = 0; cols < 2 * labWidth - 1; cols++) {
							// going through each character and based on the
							// symbol creating an edge or node
							// edges are “corridor”, “wall”, ”thickWall”, or
							// ”metalWall”
							holder = lab[rows][cols];
							// Nodes - Rooms
							// 'b' is entrance to the labyrinth
							if (holder == 'b') {
								entrance = graph.getNode(node);
								node++;
							}
							// 'x' is exit of the labyrinth
							else if (holder == 'x') {
								exit = graph.getNode(node);
								node++;
							}

							// '+' is a room
							else if (holder == '+')
								node++;
							// Edges - Horizontal walls
							// Checks if holder is equal to 'h', if it is, it
							// inserts an
							// edge into graph, of a "wall" type.
							else if (holder == 'h')
								graph.insertEdge(graph.getNode(node - 1), graph.getNode(node), "wall");
							// 'H' inserts a horizontal thick brick wall
							else if (holder == 'H')
								graph.insertEdge(graph.getNode(node - 1), graph.getNode(node), "thickWall");
							// 'm' inserts a horizontal metal wall
							else if (holder == 'm')
								graph.insertEdge(graph.getNode(node - 1), graph.getNode(node), "metalWall");
							// '-' horizontal corridor
							else if (holder == '-')
								graph.insertEdge(graph.getNode(node - 1), graph.getNode(node), "corridor");
							// Edge - Vertical Walls
							// 'v' vertical normal brick wall
							else if (holder == 'v')
								graph.insertEdge(graph.getNode(node - labWidth + ((cols + 1) / 2)),
										graph.getNode(node + ((cols + 1) / 2)), "wall");
							// 'V' vertical thick brick wall
							else if (holder == 'V')
								graph.insertEdge(graph.getNode(node - labWidth + ((cols + 1) / 2)),
										graph.getNode(node + ((cols + 1) / 2)), "thickWall");
							// 'M' vertical metal wall
							else if (holder == 'M')
								graph.insertEdge(graph.getNode(node - labWidth + ((cols + 1) / 2)),
										graph.getNode(node + ((cols + 1) / 2)), "metalWall");
							// '|' vertical corridor
							else if (holder == '|')
								graph.insertEdge(graph.getNode(node - labWidth + ((cols + 1) / 2)),
										graph.getNode(node + ((cols + 1) / 2)), "corridor");
						}
					}
					input.close();
					return;
				}

				/*
				 * inserting rows from text file describing labyrinth into 2d
				 * array lab
				 */
				for (i = 0; i < line.length(); ++i) {
					lab[row][i] = line.charAt(i);
				}
				++row;
			}

		} catch (IOException e) {
			System.out.println("Error reading input file: " + inputFile);
		}
	}

	/*
	 * returns a reference to the graph representing the labyrinth. Throws a
	 * Labyrinth Exception if the graph is not defined.
	 */
	public Graph getGraph() throws LabyrinthException {
		// checks if the graph is initialized, if not, throws a
		// LabyrinthException.
		if (graph == null)
			throw new LabyrinthException("The graph is not defined!");
		// else, return graph.
		return graph;
	}

	/*
	 * returns a java Iterator containing the nodes along the path from the
	 * entrance to the exit of the labyrinth, if such a path exists. If the path
	 * does not exist, this method returns the value null
	 */
	public Iterator<Node> solve() {
		// create a new stack and use helper method path to see if there is a
		// path
		// from the entrance to the exit
		nodeStack = new Stack<Node>();
		if (path(entrance, exit) == true) {
			return nodeStack.iterator();
		} else {
			return null;
		}
	}

	/*
	 * modified path algorithm finds path from starting node current to
	 * destination d
	 */
	private boolean path(Node current, Node d) {
		// mark current node and push it onto the stack
		current.setMark(true);
		nodeStack.push(current);
		// if the current node is the destination return true
		if (current.equals(d))
			return true;
		// creates an iterator which contains all incident edges on the current
		// node
		// iterate through each incident edge, calling recursively on each
		// incident
		// edge with an unmarked second end point
		Iterator<Edge> iter = graph.incidentEdges(current);
		while (iter.hasNext()) {
			Edge edge = iter.next();
			if (edge.secondEndpoint().getMark() == false) {
				// if the edge between current node and adjacent node is a wall
				if (edge.getType() == "wall") {
					// if you do not have enough required bombs, continue to
					// next iteration
					// otherwise subtract bomb supply by 1 and move on to
					// recursive call
					if (bombsAllowed == 0)
						continue;
					else
						bombsAllowed = bombsAllowed - 1;
					// if the edge between current and adjacent is a thick wall
				} else if (edge.getType().equals("thickWall")) {
					// if you do not have enough required bombs, continue to
					// next iteration
					// otherwise subtract bomb supply by 2 and move on to
					// recursive call
					if (bombsAllowed <= 1)
						continue;
					else
						bombsAllowed = bombsAllowed - 2;
					// if the edge between current and adjacent is a metal wall
				} else if (edge.getType() == "metalWall") {
					// if you do not have enough required bombs, continue to
					// next iteration
					// otherwise subtract acid bomb supply by 1 and move on to
					// recursive call
					if (acidBombsAllowed == 0)
						continue;
					else
						acidBombsAllowed = acidBombsAllowed - 1;
				}
				// recursive call only reached if second end point is unmarked
				// and
				// you have the required bombs to pass the previous three if
				// statements without
				// hitting a continue
				if (path(edge.secondEndpoint(), d) == true) {
					return true;
				}
			}
			// if the second end point is marked, move on to the next edge
			if (iter.hasNext())
				continue;
		}
		// if there are no more edges and the stack is of size 1, both paths
		// from the start
		// have been attempted and there is no solution to be found
		if (nodeStack.size() <= 1) {
			System.out.println("There is no solution");
		}
		// if there are no more edges to be checked in the current path and the
		// stack is of size
		// greater than one, check the edge connecting the node on the top of
		// the stack and the
		// node directly beneath that one before popping to see if bombs need to
		// be replenished
		Edge popEdge = graph.getEdge(nodeStack.peek(), nodeStack.get(nodeStack.size() - 2));
		// replenish bombs based on the edge of the node being popped off the
		// stack
		if (popEdge.getType() == "wall") {
			bombsAllowed = bombsAllowed + 1;
		} else if (popEdge.getType() == "thickWall") {
			bombsAllowed = bombsAllowed + 2;
		} else if (popEdge.getType() == "metalWall") {
			acidBombsAllowed = acidBombsAllowed + 1;
		}
		// unmark and pop the node off the stack, returning false as a path to
		// the destination
		// could not be found
		current.setMark(false);
		nodeStack.pop();
		return false;

	}
}
