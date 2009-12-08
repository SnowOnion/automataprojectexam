package automaton;

import java.util.ArrayList;

/**
 * This class defines the transition condition of one PDA
 * this condition consists of three parts: 
 * 1.the transition strings
 * 2. for each string, there would be a from stack symbol
 * 3. for each string, there would be a to stack symbol
 * for example: 0,Zo/0Zo.'0' is one of the transition strings, 'Zo'is the from stack symbol, and '0Zo'is the to stack symbol
 * @author Guoxin
 *
 */

public class TransitionPDACondition {
	String conditions;
	String fromStackSymbol;
	ArrayList<String> toSymbols;
	
	public TransitionPDACondition(){
		conditions = new String();
		fromStackSymbol = "fromStackSymbol";
		toSymbols = new ArrayList<String>();
	}
	
	public TransitionPDACondition(String conditions,
			String fromStackSymbol,ArrayList<String> toSymbols){
		this.conditions = conditions;
		this.fromStackSymbol = fromStackSymbol;
		this.toSymbols = toSymbols;
	}
	
	public void setCondition(String conditions){
		this.conditions = conditions;
	}
	public void setFromStackSymbol(String fromStackSymbol){
		this.fromStackSymbol = fromStackSymbol;
	}
	
	public void setToStackSymbol(ArrayList<String> toStackSymbol){
		this.toSymbols = toStackSymbol;
	}
	
	public String getCondition(){
		return this.conditions;
	}
	
	public String getFromStackSymbol(){
		return this.fromStackSymbol;
	}
	
	public ArrayList<String> getToSymbols(){
		return this.toSymbols;
	}
}
