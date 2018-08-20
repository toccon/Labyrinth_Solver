import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Traversals {
	public Graph graph;
	public int numNodes;
	public int DEFAULT = 5;
	public Queue<Node> Q;
	public Stack<Node> nodeStack;

	public Traversals() {
		this.graph = new Graph(DEFAULT);
		this.numNodes = DEFAULT;
		nodeStack = new Stack<Node>();
	}
	public Traversals(int n){
		this.graph = new Graph(n);
		this.numNodes = n;
		nodeStack = new Stack<Node>();
	}
	public void DFS(Node u){
		u.setMark(true);
		Iterator<Edge> iter = graph.incidentEdges(u);
		while(iter.hasNext()){
			Edge edge = iter.next();
			if(!edge.getType().equals("discovery") && !edge.getType().equals("back")){
				if(edge.secondEndpoint().getMark() == false){
					edge.setType("discovery");
					
					System.out.println(edge.firstEndpoint().getName());
					System.out.println(edge.secondEndpoint().getName());
					System.out.println();
					
					DFS(edge.secondEndpoint);
				}
				else{
					edge.setType("back");
				}
			}
		}
	}
	public void BFS(Node s){
		Q = new LinkedList<Node>();
		Q.add(s);
		s.setMark(true);
		while(Q.isEmpty() == false){
			Node u = Q.remove();
			System.out.println(u.getName()); // do whatever needs to be done to each node
			Iterator<Edge> iter = graph.incidentEdges(u);
			while(iter.hasNext()){
				Edge edge = iter.next();
				if(!edge.getType().equals("discovery") && !edge.getType().equals("cross")){
					if(edge.secondEndpoint().getMark() == false){
						edge.setType("discovery");
						edge.secondEndpoint().setMark(true);
						Q.add(edge.secondEndpoint());
					}
					else{
						edge.setType("cross");
					}
				}
			}
		}
		
	}
    public static void main (String[] args) {
    	Traversals nick = new Traversals();
		nick.graph.insertEdge(nick.graph.getNode(0), nick.graph.getNode(1), "unlabelled");
		nick.graph.insertEdge(nick.graph.getNode(0), nick.graph.getNode(2), "unlabelled");
		nick.graph.insertEdge(nick.graph.getNode(1), nick.graph.getNode(2), "unlabelled");
		nick.graph.insertEdge(nick.graph.getNode(1), nick.graph.getNode(3), "unlabelled");
		nick.graph.insertEdge(nick.graph.getNode(1), nick.graph.getNode(4), "unlabelled");
		nick.graph.insertEdge(nick.graph.getNode(2), nick.graph.getNode(3), "unlabelled");
		nick.graph.insertEdge(nick.graph.getNode(3), nick.graph.getNode(4), "unlabelled");
		
		nick.BFS(nick.graph.getNode(0));
    }
}
