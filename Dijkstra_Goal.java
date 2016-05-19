import java.util.*;

class Dijkstra_Path {
	int[] distances;
	int[] parent;
	int[][] graph;
	String[] vertices;
	String source,destination;
	PriorityQueue<String> q;
	ArrayList<String> elements;
	int noOfVertices;

	public Dijkstra_Path(int[][] graph, String[] vertices, String source, String destination,int noOfVertices) {
		this.graph = graph;
		this.vertices = vertices;
		this.source = source;
		this.destination = destination;
		this.noOfVertices = noOfVertices;
	}

	public void getInitialized() {
		distances = new int[graph.length];
		parent = new int[graph.length];
		Arrays.fill(distances,9999);
		Arrays.fill(parent,-1);
		elements = new ArrayList<String>(Arrays.asList(vertices));
		if(source.equals("a") ) {
			System.out.println(source + "->" + destination + ":" + 9999);
		}
	}

	public void dijkstrasShortest(String source,String destination) {
		q = new PriorityQueue<String>();
		int index = elements.indexOf(source);
		distances[index] = 0;
		parent[index] = index;
		q.add(source);
		while(!(q.isEmpty())){
			String element = getMin(elements);
			q.remove(element);
			int value = elements.indexOf(element);
			for(int i = 0; i < graph[value].length; i++) {
				if(graph[value][i] != 9999) {
					int v = distances[value] + graph[value][i];
					if(v < distances[i]) {
						distances[i] = v;
						parent[i] = value;
						if(q.contains(vertices[i])){
							distances[elements.indexOf(vertices[i])] =  distances[elements.indexOf(vertices[i])];
						}else {
							q.add(vertices[i]);
						}
					}
				}
			}
		}
	}

	private String getMin(ArrayList<String> elements) {
		int min = 9999;
		String output = "";
		for(String element: q) {
			if((distances[elements.indexOf(element)]) < min){
				min = distances[elements.indexOf(element)];
				output = element;
			}
		}
		return output;
	}

	public ArrayList<String> getDestination() {
		ArrayList<String> desti = new ArrayList<String>();
		int dest = elements.indexOf(destination);
		int src = elements.indexOf(source);
		if(parent[dest] != -1) {
			while(dest != src) {
				desti.add(vertices[dest]);
				dest = parent[dest];
			}
		}
		return desti;
	}

	public String toString() {
		int dest = elements.indexOf(destination);
		int src = elements.indexOf(source);
		StringBuffer sb = new StringBuffer();
		StringBuffer rb = new StringBuffer();
		if(parent[dest] != -1) {
			rb.append(source);
			while(dest != src) {
				sb.append(vertices[dest]);
				sb.append(">-");
				dest = parent[dest];
			}
		}else {
			sb.append(vertices[0]);
			sb.append("->");
		}
		rb.append(sb.reverse());
		rb.append(':');
		rb.append(distances[elements.indexOf(destination)]);
		return rb.toString();
	}
}


public class Dijkstra_Goal {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int noOfVertices = Integer.parseInt(scan.nextLine());
		String costs = scan.nextLine();
		costs = costs.replace("(","").replace(")","");
		String[] vertices = costs.split(",");
		String[] inputs = scan.nextLine().split(",");
		String source = inputs[0];
		String destination = inputs[1];
		int[][] graph = new int[noOfVertices][noOfVertices];
		for(int i = 0; i < noOfVertices ; i++){
			String line[] = scan.nextLine().split(",");
			for(int j = 0; j < noOfVertices; j++) {
				graph[i][j] = Integer.parseInt(line[j]);
			}
		}
		Dijkstra_Path dp = new Dijkstra_Path(graph, vertices, source, destination,noOfVertices);
		System.out.println(source + "->" + source + ":" + 0);
		dp.getInitialized();
		dp.dijkstrasShortest(source,destination);
		ArrayList<String> destinationsValues = dp.getDestination();
		Collections.sort(destinationsValues);
		for(int i = 0; i < destinationsValues.size() ;i++) {
			Dijkstra_Path dp1 = new Dijkstra_Path(graph, vertices, source, destinationsValues.get(i),noOfVertices);
			dp1.getInitialized();
			dp1.dijkstrasShortest(source,destination);
			System.out.println(dp1);
		}
	}
}