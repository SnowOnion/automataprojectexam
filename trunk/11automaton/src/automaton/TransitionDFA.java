package automaton;

import java.util.ArrayList;

public class TransitionDFA extends TransitionNFA {

	public TransitionDFA(State from, ArrayList<String> condition, State to) {
		super(from, condition, to);
		// TODO Auto-generated constructor stub
	}

	public TransitionDFA() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkCondition(String cond) {
		return cond.length() == 1;
	}
}
