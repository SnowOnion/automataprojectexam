package automaton;

import java.util.ArrayList;

/**********************************************************
 * PDA = (Q,E,S,T,q,Z,F) Q: a finite set of states E: a finite set of input
 * symbols(including empty input) S: a finite set of Stack Symbols T: the
 * transition functions q: the initial State Z: the first symbol in Stack F: a
 * set of final states
 * 
 * @author Administrator
 * 
 */
public class AutomatonPDA extends Automaton {
	private ArrayList<String> stackSymbols;
	private String initialStackSymbol;

	public AutomatonPDA() {
		super();
		setAutomatonType(AutomatonConstant.AUTOMATONTYPES[3]);
		stackSymbols = new ArrayList<String>();
		initialStackSymbol = null;
	}

	public boolean addStackSymbol(String symbol) {
		for (String sb : stackSymbols)
			if (sb.equals(symbol))
				return false;
		stackSymbols.add(symbol);
		return true;
	}

	public String getInitialStackSymbol() {
		return initialStackSymbol;
	}

	public void setInitialStackSymbol(String initialStackSymbol) {
		this.initialStackSymbol = initialStackSymbol;
	}

	public ArrayList<String> getStackSymbols() {
		return stackSymbols;
	}

	public void setStackSymbols(ArrayList<String> stackSymbols) {
		this.stackSymbols = stackSymbols;
	}

	public boolean hasStackSymbol(String stackSymbol) {
		return stackSymbols.indexOf(stackSymbol) != -1;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type:" + automatonType + "\n");
		sb.append("Symbols:");
		for (String symbol : inputSymbolSet){
			sb.append(symbol+" ");
		}
		sb.append("\n");
		
		sb.append("States:" + states.keySet());
		if(initialState!=null){
			sb.append("Initial State:" + initialState.getStateId() + "\n");
		}else{
			sb.append("Initial State: null \n");
		}
		sb.append("Transitions:");
		for (Transition transition : transitions){
			sb.append((TransitionPDA)transition+";");
			

		}
		return sb.toString();
	}
}
