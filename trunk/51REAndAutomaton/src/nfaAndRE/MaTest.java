package nfaAndRE;

public class MaTest {
	public static void main(String args[]){
		String re = "a*b(c+d)";
		RE regu = new RE(re);
		String[] inputs=new String[]{
				"abd", "abc", "aaaaaabc"
			};
		for(int i=0; i<inputs.length; i++){
			System.out.println(regu.acceptString(inputs[i]));
		}
		
		for(Transfer transfer: regu.getNfa().getTransfers()){
			System.out.print(transfer.getBeginState().getStateId() + "-->");
			System.out.print(transfer.getInputChar() + "-->");
			System.out.print(transfer.getEndState().getStateId() + "\n");
		}
		System.out.println();
	}
}
