import java.util.*;
import java.util.Map.Entry;

class HuffmanCode_Algorithm {

	public ArrayList<String> outputData = new ArrayList<String>();
	public ArrayList<String> sortedValue = new ArrayList<String>();
	public Hashtable<Character,String> storedData = new Hashtable<Character,String>();

	public HuffmanTree buildTree(int[] charFreqs) {
		PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
		for (int i = 0; i < charFreqs.length; i++) {
			if (charFreqs[i] > 0){
				trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
			}
		}
		while (trees.size() > 1) {
			HuffmanTree a = trees.poll();
			HuffmanTree b = trees.poll();
			trees.offer(new HuffmanNode(a, b));
		}
		return trees.poll();
	}

	public void printCodes(HuffmanTree tree, StringBuffer prefix) {
		int distance = 0;
		if (tree instanceof HuffmanLeaf) {
			HuffmanLeaf leaf = (HuffmanLeaf)tree;
			storedData.put(leaf.value,prefix.toString());
			sortedValue.add(prefix.toString());
			int valueBinary = prefix.length();
			distance = distance + (leaf.frequency * valueBinary );
			outputData.add(leaf.value + "::" + leaf.frequency + "::" + prefix.toString()+ ":::" + distance);
		} else if (tree instanceof HuffmanNode) {
			HuffmanNode node = (HuffmanNode)tree;
			prefix.append('1');
			printCodes(node.right, prefix);
			prefix.deleteCharAt(prefix.length()-1);
			prefix.append('0');
			printCodes(node.left, prefix);
			prefix.deleteCharAt(prefix.length()-1);
		}
	}

	public void getOutput() {
		int distance = 0;
		for(int i = 0; i < outputData.size();i++) {
			String[] data = outputData.get(i).split(":::");
			distance += Integer.parseInt(data[1]);
		}
		Set<Character> keys = storedData.keySet();
		Collections.sort(sortedValue,new CustomComparator());
		for(int i = 0; i < sortedValue.size(); i++ ) {
			for(char key : keys){
				if(sortedValue.get(i).equals(storedData.get(key)) ){
			        System.out.println(key + ": "+sortedValue.get(i));
				}
			}
		}
		System.out.println(distance);
	}
}



abstract class HuffmanTree implements Comparable<HuffmanTree> {

	int frequency;
	
	public HuffmanTree(int frequency) { 
		this.frequency = frequency; 
	}

	public int compareTo(HuffmanTree tree) {
		return frequency - tree.frequency;
	}
}

class HuffmanLeaf extends HuffmanTree {

	char value;

	public HuffmanLeaf(int freq, char value) {
		super(freq);
		this.value = value;
	}
}

class HuffmanNode extends HuffmanTree {

	HuffmanTree left;
	HuffmanTree right;

	public HuffmanNode(HuffmanTree left, HuffmanTree right) {
		super(left.frequency + right.frequency);
		this.left = left;
		this.right = right;
	}
}


public class HuffmanCode {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int[] charFreqs = new int[256];
		int stringLength = Integer.parseInt(scan.nextLine());
		for(int i = 0; i < stringLength; i++) {
			String lines = scan.nextLine();
			String[] dataLine = lines.split(" ");
			for(int j = 0; j < dataLine.length;j++) {
				char charValue = dataLine[0].charAt(0);
				charFreqs[charValue] = Integer.parseInt(dataLine[1]);
			}
		}

		HuffmanCode_Algorithm hca = new HuffmanCode_Algorithm();
		HuffmanTree tree = hca.buildTree(charFreqs);
		hca.printCodes(tree, new StringBuffer());
		hca.getOutput();
	}
}

class CustomComparator implements Comparator<String>{
	public int compare(String s1, String s2) {
		int decimalOne = Integer.parseInt(s1,2);
		int decimalTwo = Integer.parseInt(s2,2);
		if(decimalOne == decimalTwo) {
			return s1.length() - s2.length();
		}
		return decimalOne - decimalTwo;
	}
}