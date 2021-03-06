package automaton;

import gui.help.AutomatonException;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class TransitionNFA extends Transition {

	public TransitionNFA(State from, ArrayList<String> condition, State to) {
		this.fromState = from;
		this.transitionConditions = condition;
		this.toState = to;
	}

	public TransitionNFA() {
		super();
		transitionConditions = new ArrayList<String>();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getTransitionConditions() {
		return (ArrayList<String>) transitionConditions;
	}

	public void setTransitionCondition(ArrayList<String> condition) {
		this.transitionConditions = condition;
	}

	@Override
	public void setConditionsFromRawString(Automaton automaton, String condstr)
			throws AutomatonException {
		StringTokenizer st = new StringTokenizer(condstr, ",");
		while (st.hasMoreTokens()) {
			String cond = st.nextToken();
			if (!checkCondition(cond))
				throw new AutomatonException("Wrong format of condition "
						+ cond);
			addCondition(automaton, cond);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addCondition(Automaton automaton, String cond)
			throws AutomatonException {
		if (!checkCondition(cond))
			throw new AutomatonException("Wrong format of condition " + cond);
		transitionConditions.add(cond);
		if (!automaton.hasInputSymbol(cond))
			automaton.addInputSymbol(cond);
	}

	@Override
	protected boolean checkCondition(String cond) {
		return cond.equals("empty") || cond.length() == 1;
	}

}
