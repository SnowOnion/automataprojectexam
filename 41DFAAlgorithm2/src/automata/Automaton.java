package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public abstract class Automaton {
//------Used by construction procedure------------------------------------------
	public void addState(String label) {
		states.add(new State(label));
		label2num.put(label, size);
		++size;
	}
	
	public void setStartState(String label) {
		if (!label2num.containsKey(label))
			addState(label);
		startState = stateOfLabel(label);
		modified = true;
	}
	
	public void setFinalState(String label) {
		if (!label2num.containsKey(label))
			addState(label);
		finalStates.add(stateOfLabel(label));
		modified = true;
	}
	
	public abstract void addTransition(State from, HashSet<Character> cond, State to);
	
//------Utilities, internal-----------------------------------------------------
	protected State stateOfLabel(String label) {
		return states.get(label2num.get(label));
	}
	protected State stateOfNum(int num) {
		return states.get(num);
	}
	protected int numOfState(State s) {
		return label2num.get(s.label());
	}
	
//------Used by algorithms, internal--------------------------------------------
	protected void removeState(int stateNum) {
		removeState(states.get(stateNum));
	}
	protected void removeState(State state) {
		Set<State> s;
		Iterator<State> it;
		State t;
		
		state.dispose();
		
		s = state.toStates();
		it = s.iterator();
		while (it.hasNext()) {
			t = it.next();
			t.cleanUpInTransitions();
		}
		s = state.fromStates();
		it = s.iterator();
		while (it.hasNext()) {
			t = it.next();
			t.cleanUpOutTransitions();
		}
		
		states.remove(state);
		--size;
		
		// reconstruct state2num map
		label2num.clear();
		for (int num = 0; num < size; ++num) {
			label2num.put(states.get(num).label(), num);
		}

		// in final states
		finalStates.remove(state);
		// in start state
		if (startState == state)
			startState = null;
		
		modified = true;
	}
	
	
//------Data member-------------------------------------------------------------
	protected int size = 0;
	protected ArrayList<State> states = new ArrayList<State>();
	protected HashMap<String, Integer> label2num = new HashMap<String, Integer>();
	protected State startState;
	protected HashSet<State> finalStates = new HashSet<State>();
	protected HashSet<Character> alphabet = new HashSet<Character>();
	protected boolean modified = false;
}
