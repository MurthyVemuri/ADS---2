import java.util.*;


class Link {

	int node;
	int distance;
	int parent;

	public Link(int node, int distance, int parent) {
		this.node = node;
		this.distance = distance;
		this.parent = parent;
	}

	public int getDistance() {
		return this.distance;
	}

	public int getParent() {
		return this.parent;
	}

	public int getNode() {
		return this.node;
	}
}

public class Prim {
   
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in); 
		int noOfNodes = Integer.parseInt(scan.nextLine() );
		String nodesTemp = scan.nextLine();
		String source = scan.nextLine();
		String[] nodes = nodesTemp.replace("(","").replace(")","").split(",");
		int[][] graph = new int[noOfNodes][noOfNodes];
		Prims_Algorithm pa = new Prims_Algorithm(nodes);

		for(int i = 0; i < noOfNodes; i++) {
			String[] temp = scan.nextLine().split(" ");
			for(int j = 0; j < noOfNodes; j++) {
				graph[i][j] = Integer.parseInt(temp[j]);
			}
		}
		pa.primsAlgorithm(getIndex(nodes, source), graph);
	}

	public static int getIndex(String[] nodes, String key)  {
		for(int i = 0; i < nodes.length; i++){
			if(key.equals(nodes[i])){
				return i;
			}
		}
		return -1;
	} 
}
 
class Prims_Algorithm {    

	int[] d;
	String[] nodes;
	int[] parent;
	int[][] temp;
	ArrayList<String> removedFromQueue;
	PriorityQueue<Link> Q;

	public Prims_Algorithm(String[] nodes) {
		this.nodes = nodes;
		removedFromQueue = new ArrayList<String>();
		temp = new int[nodes.length][nodes.length];
		d = new int[nodes.length];
		parent = new int[nodes.length];
	}

	private void getCompare() {
		Q = new PriorityQueue<Link>(new Comparator<Link>() {
			public int compare(Link n1, Link n2) {
				if(n1.getDistance() == n2.getDistance()) {
					if(n1.getParent() == n2.getParent()) {
						return -1;
					} else if(n1.getParent() < n2.getParent()){
						return 1;
					} else {
						return -1;
					}
				}
				if(n1.getDistance() < n2.getDistance())
					return -1;
				else{
					return 1;
				}
			}
		});
	}

	public void primsAlgorithm(int source, int[][] graph){
		Arrays.fill(d, 999);
		Arrays.fill(parent, -1);
		parent[source] = source;
		removedFromQueue.add(nodes[source]);
		d[source] = 0;
		getCompare();
		Link dummy = new Link(source, 0, source);
		Q.add(dummy);

		while(!Q.isEmpty()){

			Link u = Q.poll();

			if(!removedFromQueue.contains(nodes[u.getNode()])){
				removedFromQueue.add(nodes[u.getNode()]);
			}

			for(int v = 0; v < graph[u.getNode()].length; v++) {
				if(graph[u.getNode()][v] > 0) {
					if((graph[u.getNode()][v]) < d[v] && temp[u.getNode()][v]==0){
						d[v] = graph[u.getNode()][v];
						temp[u.getNode()][v] = 1;
						temp[v][u.getNode()] = 1;
						parent[v] = u.getNode();
						Link vNode = new Link(v, d[v], parent[v]);
						if(Q.contains(vNode)) {
							Q.remove(vNode);
						} else {
							Q.add(vNode);
						}
					}
				}
			}
		}
		printOutput(d,nodes,parent,source);
	}

	public void printOutput(int[] d,String[] nodes,int[] parent,int source) {
		StringBuffer out = new StringBuffer();
		out.append("(");
		StringBuffer outOne = new StringBuffer();
		outOne.append("(");
		int distance = 0;
		int i;

		for(i = 0 ;i < d.length-1;i++) {
			distance = distance + d[i];
			//source
			out.append(nodes[parent[Prim.getIndex(nodes, nodes[i])]]);
			out.append(",");
			//destination
			outOne.append(removedFromQueue.get(i));
			outOne.append(",");
		}

		out.append(nodes[parent[Prim.getIndex(nodes, nodes[i])]]);
		out.append(")");
		//parent vertex of graph.
		System.out.println(out.toString());
		outOne.append(removedFromQueue.get(i));
		//outOne.append(nodes[source]);
		outOne.append(")");
		//lexicographical order of vertices.
		System.out.println(outOne.toString() );
		System.out.println(distance + d[i]);
	}   
}