package nfaAndRE;

public class REtoAutomaton {
	private String re = new String();              //regular expression
	private NFA nfa = new NFA();    //NFA
	int count = 3;                  //record the middle stateId
	
	public REtoAutomaton(String re) {
		super();
		this.re = re;
		reToAutomaton();
		
	}
	//below are settings and gettings
	public String getRe() {
		return re;
	}

	public void setRe(String re) {
		this.re = re;
	}

	public NFA getNfa() {
		return nfa;
	}

	public void setNfa(NFA nfa) {
		this.nfa = nfa;
	}
	
	//implementation of REtoNFA 
	public void reToAutomaton(){
		nfa.addState("q1");
		nfa.addState("q2");
		
		nfa.setStartQ("q1");
		nfa.addFinalState("q2");
		
		reToAutomatonPro(re, "q1", "q2");
	}
	
	//the process of transitions of REtoNFA
	public void reToAutomatonPro(String s, String beginStateId, String endStateId){
		//end condition
		if(s.length() == 1){
			nfa.addSymbol(s.charAt(0));
			nfa.addTransfer(beginStateId, s.charAt(0), endStateId);
			return ;
		}
		if(s.length() == 0){
			nfa.addTransfer(beginStateId, Automata.EPSILON, endStateId);
			return ;
		}
		
		//find the first "+" in the s, then recurse of the two part
		int index = s.indexOf('+');
		if(index > -1){
			String s1 = s.substring(0, index);
			String s2 = s.substring(index + 1, s.length());
			char[] s1Arr = s1.toCharArray();
			int leftParenthesis = 0; 
			int rightParenthesis = 0;
			for(int i = 0; i < s1.length(); i++){
				if(s1Arr[i] == '(')
					leftParenthesis++;
				else if(s1Arr[i] == ')')
					rightParenthesis++;
			}
			if(leftParenthesis == rightParenthesis){
				reToAutomatonPro(s1, beginStateId, endStateId);
				reToAutomatonPro(s2, beginStateId, endStateId);
			}			
		}
		
		//find the first "&" in the s, then recurse of the two part
		index = s.indexOf('&');
		if(index > -1){
			String s1 = s.substring(0, index);
			String s2 = s.substring(index + 1, s.length());
			char[] s1Arr = s1.toCharArray();
			int leftParenthesis = 0; 
			int rightParenthesis = 0;
			for(int i = 0; i < s1.length(); i++){
				if(s1Arr[i] == '(')
					leftParenthesis++;
				else if(s1Arr[i] == ')')
					rightParenthesis++;
			}
			if(leftParenthesis == rightParenthesis){
				String middleStateId = "q" + Integer.valueOf(count++).toString();
				nfa.addState(middleStateId);
				reToAutomatonPro(s1, beginStateId, middleStateId);
				reToAutomatonPro(s2, middleStateId, endStateId);
			}
			
		}
		
		//find the first "*" in the s, then recurse of the two part
		index = s.indexOf('*');
		if(index > -1){
			String s1 = s.substring(0, index);
			char[] s1Arr = s1.toCharArray();
			int leftParenthesis = 0; 
			int rightParenthesis = 0;
			for(int i = 0; i < s1.length(); i++){
				if(s1Arr[i] == '(')
					leftParenthesis++;
				else if(s1Arr[i] == ')')
					rightParenthesis++;
			}
			if(leftParenthesis == rightParenthesis){
				reToAutomatonPro(s1, beginStateId, beginStateId);
				nfa.addTransfer(beginStateId, Automata.EPSILON, endStateId);
			}
		}
		
		//find the first "()" in the s, then delete "()"
		index = s.indexOf('(');
		if(index > -1){
			if(index == 0){
				s = s.substring(1, s.length() - 1);
				reToAutomatonPro(s, beginStateId, endStateId);
			}
		}
	}
}
