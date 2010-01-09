package nfaAndRE;

public class Test {
	public static void printTransFun(RE regu){
		System.out.println("************转移函数**************");
		System.out.println("其中q1为开始状态，q2为结束状态");
		for(Transfer transfer: regu.getNfa().getTransfers()){
			System.out.print(transfer.getEndState().getStateId() + " = " 
					+ "δ" + "(" + transfer.getBeginState().getStateId() 
					+ " , " + transfer.getInputChar() + ")" + "\n");
		}
	}
	
	public static void testString(RE regu, String[] inputs){
		for(int i=0; i<inputs.length; i++){
			System.out.println(inputs[i] + " : " + regu.acceptString(inputs[i]));
		}
	}
	
	public static void main(String args[]){
		
		/*
		 * (0+1)+(012)
		 */String re = "(0+1)+(012)";
		RE regu = new RE(re);
		String[] inputs=new String[]{
				"010100101110000111101110001000", "1", "10", "01", "101",
				"00100", "10100", "100010000010", "00010000001"
			};
		testString(regu, inputs);
		printTransFun(regu);
		
		
		/*
		 * (0+1)*1(0+1)+(0+1)*1(0+1)(0+1)
		 * String re = "(0+1)*1(0+1)+(0+1)*1(0+1)(0+1)";
		RE regu = new RE(re);
		String[] inputs=new String[]{
				"010100101110000111101110001000", "1", "10", "01", "101",
				"00100", "10100", "100010000010", "00010000001"
			};
		testString(regu, inputs);
		printTransFun(regu);*/
		
		/*
		 * (0+10)*1*
		 * String re = "(0+10)*1*";
		RE regu = new RE(re);
		String[] inputs=new String[]{
				"010100101110000111101110001000", "1", "10", "01", "101",
				"00100", "10100", "100010000010", "00010000001"
			};
		testString(regu, inputs);
		printTransFun(regu);*/
		
		/*
		 * (0*1*)*000(0+1)*
		 * String re = "(0*1*)*000(0+1)*";
		RE regu = new RE(re);
		String[] inputs=new String[]{
				"010100101110000111101110001000", "1", "10", "01", "101",
				"00100", "10100", "100010000010", "00010000001"
			};
		testString(regu, inputs);
		printTransFun(regu);*/
		
		/*
		 * (0*1*)000(0+1)*
		 * String re = "(0*1*)000(0+1)*";
		RE regu = new RE(re);
		String[] inputs=new String[]{
				"1", "10", "01", "101", "00100", "10100",
				"1000100000", "0001000"
			};
		testString(regu, inputs);
		printTransFun(regu);*/
		
		/*
		 * a*b(c+d)
		 * String re = "a*b(c+d)";
		RE regu = new RE(re);
		String[] inputs=new String[]{
				"aab", "abc", "aaaaabcd", "aasdfaa", "abd", "aaabd"
			};
		testString(regu, inputs);
		printTransFun(regu);*/		
		
		/*
		 * (1+EPSILON)(00*1)0*测试
		 * String re = "(1+" + Automata.EPSILON + ")(00*1)0*"; 
		RE regu = new RE(re);
		
		String[] inputs=new String[]{
				"1", "10", "01", "101", "00100", "10100",
				"1000100000", "0001000"
			};
		testString(regu, inputs);
		printTransFun(regu);*/
		
		/*
		 * ************************对 "&"（连接运算) 运算的测试************************
		String re1 = "a";
		RE regu1 = new RE(re1);
		
		String re2 = "b";
		RE regu2 = new RE(re2);
		regu2 = regu2.connect(regu1);
		
		System.out.println(regu2.getReAfterProcess());
		for(Transfer transfer: regu2.getNfa().getTransfers()){
			System.out.print(transfer.getEndState().getStateId() + " = " 
					+ "δ" + "(" + transfer.getBeginState().getStateId() 
					+ " , " + transfer.getInputChar() + ")" + "\n");
		}
		System.out.println();*/
		
		/*
		 * ************************对“ +”（union运算） 运算的测试************************
		String re1 = "a";
		RE regu1 = new RE(re1);
		
		String re2 = "b";
		RE regu2 = new RE(re2);
		regu2 = regu2.union(regu1);
		
		System.out.println(regu2.getReAfterProcess());
		for(Transfer transfer: regu2.getNfa().getTransfers()){
			System.out.print(transfer.getEndState().getStateId() + " = " 
					+ "δ" + "(" + transfer.getBeginState().getStateId() 
					+ " , " + transfer.getInputChar() + ")" + "\n");
		}
		System.out.println();*/
		
		
		/*
		 * *************************对“ * ” 运算的测试************************
		 * String re = "a";
		RE regu = new RE(re);
		regu = regu.star();
		String[] inputs=new String[]{
				"aa", "a", "aaaaa", "aaaaaaaaaaaaa", "abc", "aaaaaabc"
			};
		for(int i=0; i<inputs.length; i++){
			System.out.println(inputs[i] + " : " + regu.acceptString(inputs[i]));
		}
		
		for(Transfer transfer: regu.getNfa().getTransfers()){
			System.out.print(transfer.getEndState().getStateId() + " = " 
					+ "δ" + "(" + transfer.getBeginState().getStateId() 
					+ " , " + transfer.getInputChar() + ")" + "\n");
		}
		System.out.println();*/
	}
}
