import java.util.*;

class MaxSubArray {

	public int maxArraySum(int a[]) {
		int dummy = 0;
		int sumEnding = 0;

		for(int i = 0; i < a.length; i++){
			sumEnding = sumEnding + a[i];
			if(sumEnding < 0) {
			    sumEnding = 0;
			}
			if (dummy < sumEnding) {
			    dummy = sumEnding;
			}
		}
		return dummy;
	}

}

public class Max_Final{

	public static void main(String[] args){
		MaxSubArray msb = new MaxSubArray();
		Scanner scan = new Scanner(System.in);
		String lines = scan.nextLine(); 
		String[] split = lines.split(",");
		int[] input = new int[split.length];
		for(int i = 0; i < split.length;i++) {
			input[i] = Integer.parseInt(split[i]);
		}
		int max_sum = msb.maxArraySum(input);
		
		if(max_sum == 0) {
			System.out.println("0");
			System.out.println("Maximum sum is less than 0.");
		}

		if(max_sum > 0){
			System.out.println(max_sum);
			for(int i = 0; i < input.length;i++) {
				if(input[i] >= 0 ){
					System.out.print( input[i] + " ");
				}	
			}
		}
	}
}
