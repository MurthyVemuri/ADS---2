import java.util.*;

class Kruskal {

	ArrayList<Edge> edges;
	ArrayList<Edge> costs;
	int graph[][];
	String[] vertices;
	
	Kruskal(int graph[][], String vertices[]) {
		this.graph = graph;
		this.vertices = vertices;
		costs = new ArrayList<Edge>();
	}

	public void initialization() {
		edges = new ArrayList<Edge>();
		for(int i = 0; i < graph.length; i++) {
			for(int j = 0; j < graph.length; j++) {
				if(graph[i][j] != 0) {
					Edge forward = new Edge(vertices[i], vertices[j], graph[i][j]);
					Edge backward = new Edge(vertices[j], vertices[i], graph[i][j]);
					if(!edges.contains(forward) && ! edges.contains(backward)){
						edges.add(forward);
					}
				}
			}
		}
		edges.sort(new Comparator<Edge>() {
			public int compare(Edge e1, Edge e2) {
				if(e1.getWeight() == e2.getWeight()) {
					int compareSource = e1.getSrc().compareTo(e2.getSrc());
					if(compareSource == 0){
						return e2.getDest().compareTo(e1.getDest());
					}
					return compareSource;
				}
				return e1.getWeight() - e2.getWeight();
			}
		});
	}

	public void startKrushkal() {
		int distance = 0;
		UnionFind unionFind = new UnionFind(graph.length, vertices);
		unionFind.initializationUnion();
		for (int i = 0; i < edges.size(); i++) {
			if(unionFind.union(edges.get(i))) {
				costs.add(edges.get(i));
			}
		}
		for (int i = 0; i < costs.size(); i++) {
			distance = distance + costs.get(i).getWeight();
			System.out.println(costs.get(i));
		}
		System.out.println(distance);
	}
}

class Edge implements Comparable<Edge> {

	String source;
	String destination;
	int weight;
	
	Edge(String source, String destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public String getDest() {
		return destination;
	}

	public String getSrc() {
		return source;
	}

	public int getWeight() {
		return weight;
	}

	public int compareTo(Edge e) {
		if(this.getWeight() == e.getWeight()) {
			int compareSource = this.getSrc().compareTo(e.getSrc());
			if(compareSource == 0){
				return e.getDest().compareTo(this.getDest());
			}
			return compareSource;
		}
		return e.getWeight() - this.getWeight();
	}

	public boolean equals(Object object) {
		if(this == object) 
			return true;
		if(object instanceof Edge) {
			Edge temp = (Edge) object;
			return this.getWeight() == temp.getWeight() && this.getSrc().equals(temp.getSrc()) &&
			this.getDest().equals(temp.getDest());
		}
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(this.getSrc());
		sb.append(',');
		sb.append(this.getDest());
		//sb.append(this.getWeight());
		sb.append(')');
		return sb.toString();
	}
}

class UnionFind {

	int[] rank;
	int[] parent;
	String[] vertices;
	int graphLength;

	public UnionFind(int graphLength, String[] vertices) {
		this.graphLength = graphLength;
		parent = new int[graphLength];
		rank = new int[graphLength];
		this.vertices = vertices;
	}

	public void initializationUnion() {
		Arrays.fill(rank, 0);
		for (int i = 0; i < graphLength; i++) {
			parent[i] = i;
		}
	}

	public int find(int s) {
		if(parent[s] == s) 
			return s;
		else {
			int lDash = find(parent[s]);
			parent[s] = lDash;
			return lDash;
		}
	}

	public void link(int i, int j) {
		if(rank[i] < rank[j]) 
			parent[i] = j;
		else {
			parent[j] = i;
			if(rank[i] == rank[j]) {
				rank[i]++;
			}
		}
	}

	public boolean union(Edge e) {
		int i = indexOf(e.getSrc());
		int j = indexOf(e.getDest());
		if(find(i) != find(j)) {
			link(find(i), find(j));
			return true;
		}
		return false;
	}

	private int indexOf(String source) {
		for (int i = 0; i < vertices.length; i++) {
			if(vertices[i].equals(source)) 
				return i;
		}
		return -1;
	}
}


public class Kruskals {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int noOfVertices = Integer.parseInt(scan.nextLine());
		String vertices = scan.nextLine();

		vertices = vertices.replace("(","").replace(")","");
		String[] stringVertices = vertices.split(",");

		int graph[][] = new int[noOfVertices][noOfVertices];

		for(int i = 0; i < noOfVertices;i++) {
			String line[] = scan.nextLine().split(" ");
			for (int j = 0; j < noOfVertices; j++) {
				graph[i][j] = Integer.parseInt(line[j]);
			}
		}

		Kruskal krushkal = new Kruskal(graph, stringVertices);
		krushkal.initialization();
		krushkal.startKrushkal();
	}
}