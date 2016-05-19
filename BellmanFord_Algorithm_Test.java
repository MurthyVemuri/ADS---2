import java.util.*;

class BellmanFord_Algorithm {
	int[] parent;
	String[] vertices;
	boolean flag = false;
	int[] distance;
	int[][] graph;
	int olderValue = -1;
	int iteration;

	public BellmanFord_Algorithm(int[][] graph, String[] vertices) {
		this.graph = graph;
		this.vertices = vertices;
		distance = new int[graph.length];
		parent = new int[graph.length];
		Arrays.fill(distance,99999);
		Arrays.fill(parent,-1);
	}

	public void setValue(int j) {
		iteration = j;
	}

	public void printValue() {
		System.out.println(iteration);
	}

	public void getShortestPath() {
		parent[0] = 0;
		distance[0] = 0;
		boolean iterativeFlag = false;
		for(int k = 1; k < graph.length - 1; k++) {
			iterativeFlag = false;
			for(int i = 0; i < graph.length; i++) 
				for(int j = 0; j < graph[i].length; j++) 
					if(graph[i][j] != 99999) 
						if(distance[i] + graph[i][j] < distance[j] ) {
							distance[j] = distance[i] + graph[i][j];
							iterativeFlag = true;
							olderValue = distance[j];
							parent[j] = i;
						}
						if(!iterativeFlag) {
								setValue(k);
								break;
							}
		}
		if(iterativeFlag) {
    		for(int i = 0; i < graph.length; i++){
    			for(int j = 0; j < graph.length; j++) {
    				int sum = distance[i] + graph[i][j];
    				setValue(9999);
    				if(sum < distance[j]) {
    					flag = true;
    				}
    			}
    		}
	    }
	}

	public int getVertexIndex(String string) {
		for(int i = 0; i < vertices.length; i++) {
			if(vertices[i].equals(string)) {
				return i;
			}
		}
		return -1;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(!flag){
			for(int i = 0; i < vertices.length; i++) {
				StringBuffer out = new StringBuffer();
				int dummy = parent[i];
				out.append(vertices[i]);
				if(dummy != 0) {
					out.append(">-");
					while(dummy != 0) {
						out.append(vertices[dummy]);
						dummy = parent[getVertexIndex(vertices[dummy])];
						out.append(">-");
					}
					sb.append(vertices[0]);
				} else {
					sb.append(vertices[0]);
					sb.append("->");
				}
				sb.append(out.reverse());
				sb.append(":");
				sb.append(distance[i]);
				sb.append('\n');
			}
		}else {
			sb.append("The graph has negative cycles.");
			sb.append('\n');
		}
		return sb.toString();
	}
}

public class BellmanFord_Algorithm_Test {
	public static void main(String[] args) {
		String[] vertices;
		int[][] graph;
		Scanner scan = new Scanner(System.in);
		int noOfVertices = Integer.parseInt(scan.nextLine());
		graph = new int[noOfVertices][noOfVertices];
		String line = scan.nextLine();
		vertices = line.replace("(","").replace(")","").split(",");
		String source = scan.nextLine();

		for(int j = 0; j < noOfVertices; j++) {
			String lines = scan.nextLine();
			String[] costs = lines.split(",");
			for(int k = 0; k < costs.length; k++) {
				int costValue = Integer.parseInt(costs[k]);
				graph[j][k] = costValue;
			}
		}
		BellmanFord_Algorithm bf = new BellmanFord_Algorithm(graph,vertices);
		bf.getShortestPath();
		System.out.print(bf);
		bf.printValue();
	}
}