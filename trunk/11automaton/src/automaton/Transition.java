package automaton;

import gui.help.AutomatonException;
import gui.help.AutomatonType;

import java.util.HashSet;
import java.util.List;

public abstract class Transition {
	protected State fromState;
	protected State toState;
	@SuppressWarnings("unchecked")
	protected List transitionConditions;
	protected HashSet<Nail> nails;

	public Transition() {
		nails = new HashSet<Nail>();
	}

	public Transition(Transition transition) {
		this.fromState = transition.getFromState();
		this.toState = transition.getToState();
	}

	public State getFromState() {
		return fromState;
	}

	public State getToState() {
		return this.toState;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transition:" + fromState + "to "+toState+"\n");
		builder.append("Condition number:"
				+ transitionConditions.size() + "\n");
		builder.append("nail number:"+nails.size()+"\n");
		return builder.toString();
	}

	public static Transition getTransitionOfType(AutomatonType type) {
		Transition transition = null;
		if (type == AutomatonType.NFA) {
			transition = new TransitionNFA();
		} else if (type == AutomatonType.DFA)
			transition = new TransitionDFA();
		else
			transition = new TransitionPDA();
		return transition;
	}

	public abstract void setConditionsFromRawString(Automaton automaton,
			String condstr) throws AutomatonException;

	protected abstract boolean checkCondition(String cond);

	public abstract void addCondition(Automaton automata, String cond)
			throws AutomatonException;

	/******************************************************
	 * The location operation for GUI
	 * 
	 */
	public boolean addNail(Nail nail) {
		return nails.add(nail);
	}

	public boolean removeNail(Nail nail) {
		return nails.remove(nail);
	}

	public HashSet<Nail> getNails() {
		return nails;
	}

	public void setNails(HashSet<Nail> nails) {
		this.nails = nails;
	}

	public void setFromState(State fromState) {
		this.fromState = fromState;
	}

	public void setToState(State toState) {
		this.toState = toState;
	}
}
