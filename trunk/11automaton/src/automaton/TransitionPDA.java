package automaton;

import gui.help.AutomatonException;

import java.util.ArrayList;

public class TransitionPDA extends Transition {
	public TransitionPDA(State from,
			ArrayList<TransitionPDACondition> condition, State to) {
		this.fromState = from;
		this.transitionConditions = condition;
		this.toState = to;
	}

	public TransitionPDA() {
		super();
		transitionConditions = new ArrayList<TransitionPDACondition>();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TransitionPDACondition> getTransitionConditions() {
		return (ArrayList<TransitionPDACondition>) transitionConditions;
	}

	public void setTransitionConditions(
			ArrayList<TransitionPDACondition> transitionConditions) {
		this.transitionConditions = transitionConditions;
	}

	@Override
	public void setConditionsFromRawString(Automaton automaton, String condstr)
			throws AutomatonException {
		String[] transitions = condstr.split(";");
		for (String transitionstr : transitions) {
			addCondition(automaton, transitionstr);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addCondition(Automaton automaton, String transitionstr)
			throws AutomatonException {
		int pos1 = transitionstr.indexOf(',');
		int pos2 = transitionstr.indexOf('/');
		String cond = transitionstr.substring(0, pos1);
		String oldStackSymbol = transitionstr.substring(pos1 + 1, pos2);
		String newStackSymbols = transitionstr.substring(pos2 + 1,
				transitionstr.length());
		if (!this.checkCondition(cond))
			throw new AutomatonException("Wrong condition format!");
		if (!automaton.hasInputSymbol(cond))
			automaton.addInputSymbol(cond);

		AutomatonPDA pda = (AutomatonPDA) automaton;

		if (!pda.hasStackSymbol(oldStackSymbol))
			pda.addStackSymbol(oldStackSymbol);

		ArrayList<String> toStackSymbols = new ArrayList<String>();
		for (int i = 0; i < newStackSymbols.length(); i++) {
			String symbol = newStackSymbols.substring(i, i + 1);
			if (!pda.hasStackSymbol(symbol))
				pda.addStackSymbol(symbol);
			toStackSymbols.add(symbol);
		}
		transitionConditions.add(new TransitionPDACondition(cond,
				oldStackSymbol, toStackSymbols));
	}

	@Override
	protected boolean checkCondition(String cond) {
		return cond.equals("empty") || cond.length() == 1;
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		if(transitionConditions.isEmpty()){
			sb.append("Transisiton is empty");
		}else{
			for(Object condition:transitionConditions){
				sb.append((TransitionPDACondition) condition);
			}
		}
		return sb.toString();
	}
}
