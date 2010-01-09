package nfaAndRE;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import javax.print.attribute.standard.Finishings;

public class RE {
	//store the symbols of RE, for examples a,b,0,1
	private Set<Character> symbols = new HashSet<Character>();
	//store the operators of RE, for examples +,*,(,)
	private Set<Character> operators = new HashSet<Character>();
	//receive the RE string
	private String re = new String(); 
	/**
	 * 在程序中处理RE时，先将传进来RE的String变成程序可以识别的RE，
	 * 具体来说就是传进来的RE中没有连接符，change过程就是在RE中加上连接符
	 * connectOperatorsIndex用来记录RE中需要加入连接符的下标
	 */
	private Set<Integer> connectOperatorsIndex = new TreeSet<Integer>();
	//the RE string that add connectoperations
	private String reAfterProcess = new String();   
	//REtoAutomaton class
	private REtoAutomaton reToAutomaton ;
	//NFA that RE match to 
	private NFA nfa;       


	//constructor
	public RE(String re) {
		super();
		this.re = re;
		
		char[] reArr = re.toCharArray();
		for(int i = 0; i < re.length(); i++){
			if(reArr[i] == '(' || reArr[i] == ')' || reArr[i] == '*' 
				|| reArr[i] == '+' || reArr[i] == '&')
				operators.add(Character.valueOf(reArr[i]));
			else
				symbols.add(Character.valueOf(reArr[i]));
		}
		
		reAfterProcess = changeToFormalFormat(re);
	
		reArr = reAfterProcess.toCharArray();
		for(int i = 0; i < reAfterProcess.length(); i++){
			if(reArr[i] == '(' || reArr[i] == ')' || reArr[i] == '*' 
				|| reArr[i] == '+' || reArr[i] == '&')
				operators.add(Character.valueOf(reArr[i]));
			else
				symbols.add(Character.valueOf(reArr[i]));
		}
		
		reToAutomaton = new REtoAutomaton(reAfterProcess);
		nfa = reToAutomaton.getNfa();
	}
	
	public RE union(RE re){
		return (new RE("(" + this.re + ")+" + "(" + re.re + ")"));
	}
	
	public RE connect(RE re){
		return (new RE("(" + this.re + ")&" + "(" + re.re + ")"));
	}
	
	public RE star(){
		return (new RE("(" + this.re + ")*"));
	}
	

	public boolean acceptString(String s){
		return nfa.acceptString(s);
	} 
	
	//add connectOperators to the String from the constructor
	public String changeToFormalFormat(String s){
		//find  the indexes of connectOperators added 
		changeProcess(s);
		//add connectOperators to String s
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		for(Integer inter: connectOperatorsIndex){
			sb.append(s.substring(lastIndex, inter));
			sb.append("&");
			lastIndex = inter;
		}
		sb.append(s.substring(lastIndex, s.length()));
		return sb.toString();
	}
	
	//implementions of finding  the indexes of connectOperators added 
	public void changeProcess(String s){
		//add the connectOperators
		char[] reArr = re.toCharArray();
		for(int i = 0; i < s.length() - 1; i++){
			if(symbols.contains(Character.valueOf(reArr[i]))
					&& symbols.contains(Character.valueOf(reArr[i + 1])))
				connectOperatorsIndex.add(Integer.valueOf(i + 1));
			else if(Character.valueOf(reArr[i]) == '*' && 
					symbols.contains(Character.valueOf(reArr[i + 1])))
				connectOperatorsIndex.add(Integer.valueOf(i + 1));
			else if(Character.valueOf(reArr[i]) == ')' && 
					Character.valueOf(reArr[i + 1]) == '(')
				connectOperatorsIndex.add(Integer.valueOf(i + 1));
			else if(symbols.contains(Character.valueOf(reArr[i]))
					&& Character.valueOf(reArr[i + 1]) == '(')
				connectOperatorsIndex.add(Integer.valueOf(i + 1));
			else if(symbols.contains(Character.valueOf(reArr[i + 1]))
					&& Character.valueOf(reArr[i]) == ')')
				connectOperatorsIndex.add(Integer.valueOf(i + 1));
		}
		
	} 
	
	//find the match right-parenthesis of the left-parenthesis in s
	//the parameter index is the index of the left-parenthesis in s
	public int findOppositeParenthesis(String s, int index){
		Stack<Character> stack = new Stack<Character>();
		stack.add(s.charAt(index));
		for(int i = index + 1; i < s.length(); i++){
			char ch = s.charAt(i);
			if(ch == '(')
				stack.push('(');
			else if(ch == ')'){
				if(stack.size() == 1)
					return i;
				else
					stack.pop();
			}
		}
		return -1;
	}
	
	//Below is the settings and gettings of the member variables
	public NFA getNfa() {
		return nfa;
	}
	public void setNfa(NFA nfa) {
		this.nfa = nfa;
	}
	public String getReAfterProcess() {
		return changeToFormalFormat(re);
	}
	public void setReAfterProcess(String reAfterProcess) {
		this.reAfterProcess = reAfterProcess;
	}
	public Set<Character> getSymbols() {
		return symbols;
	}
	public void setSymbols(Set<Character> symbols) {
		this.symbols = symbols;
	}
	public Set<Character> getOperators() {
		return operators;
	}
	public void setOperators(Set<Character> operators) {
		this.operators = operators;
	}
	public String getRe() {
		return re;
	}
	public void setRe(String re) {
		this.re = re;
	}
	public Set<Integer> getConnectOperatorsIndex() {
		return connectOperatorsIndex;
	}
	public void setConnectOperatorsIndex(Set<Integer> connectOperatorsIndex) {
		this.connectOperatorsIndex = connectOperatorsIndex;
	}
}
