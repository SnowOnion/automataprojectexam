package dfa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;


public abstract class Automaton {
//------Used by construction procedure------------------------------------------
	public void addState(String label) {
		if (state2num.containsKey(label))	// Already exists
			return;
		state2num.put(label, size);
		num2state.add(label);
		transitions.add(new Edges());
		reverseTransitions.add(new Edges());
		++size;
		modified = true;
	}
	
	public void setStartState(String label) {
		if (!state2num.containsKey(label))
			addState(label);
		startState = state2num.get(label);
		modified = true;
	}
	
	public void addFinalState(String label) {
		if (!state2num.containsKey(label))
			addState(label);
		finalStates.add(state2num.get(label));
		modified = true;
	}
	
	public abstract void addTransition(String from, char letter, String to) throws Exception;
	
//------Used by other classes---------------------------------------------------
	public boolean hasTransition(String from, char letter, String to) {
		if (state2num.get(from) >= size)
			return false;
		return transitions.get(state2num.get(from)).containsTransition(letter, to);
	}
	
	public void removeStateCascade(String label) {
		removeStateCascade(state2num.get(label));
	}
	
//------Used by algorithms, internal--------------------------------------------
	protected void removeStateCascade(int state) {
		// Remove edges either derived from 'state'
		Edges edges = transitions.get(state);
		Iterator<Character> letterIter = edges.lettersAccepted().iterator();
		while (letterIter.hasNext()) {
			char c = letterIter.next();
			Iterator<Integer> stateIter = edges.edgesWithLetter(c).iterator();
			while (stateIter.hasNext())
				reverseTransitions.get(stateIter.next()).removeEdge(c, state);
		}
		transitions.remove(state);
		// Remove edges either to 'state'
		edges = reverseTransitions.get(state);
		letterIter = edges.lettersAccepted().iterator();
		while (letterIter.hasNext()) {
			char c = letterIter.next();
			Iterator<Integer> stateIter = edges.edgesWithLetter(c).iterator();
			while (stateIter.hasNext()) {
				Edges e = transitions.get(stateIter.next());
				if (e != null)
					e.removeEdge(c, state);
			}
		}
		reverseTransitions.remove(state);
		
		num2state.remove(state);
		--size;
		
		// Decrease the state no. which is larger than 'state'
		// in reverse transitions
		for (int i = 0; i <size; i++) {
			edges = transitions.get(i);
			edges.stateRemoved(state);
		}
		// in transitions
		for (int i = 0; i <size; i++) {
			edges = reverseTransitions.get(i);
			edges.stateRemoved(state);
		}
		// in state2num map
		state2num.remove(num2state.get(state));
		Iterator<String> sit = state2num.keySet().iterator();
		while (sit.hasNext()) {
			String s = sit.next();
			if (state2num.get(s) > state) {
				sit.remove();
				state2num.put(s, state-1);
			}
		}
		// in final states
		Iterator<Integer> fit = finalStates.iterator();
		while (fit.hasNext()) {
			int fstate = fit.next();
			if (fstate == state)
				fit.remove();
			else if (fstate > state) {
				fit.remove();
				finalStates.add(--fstate);
			}
		}
		// in start state
		if (startState >= state)
			--startState;
		
		modified = true;
	}
	
	protected void removeStateOnly(int num) {
		transitions.remove(num);
		reverseTransitions.remove(num);
		modified = true;
	}
	
//------Data member-------------------------------------------------------------
	protected int size = 0;	// ×´Ì¬Êý
	protected HashMap<String, Integer> state2num = new HashMap<String, Integer>();
	protected ArrayList<String> num2state = new ArrayList<String>();
	protected ArrayList<Edges> transitions = new ArrayList<Edges>();
	protected ArrayList<Edges> reverseTransitions = new ArrayList<Edges>();
	protected Integer startState = 0;
	protected HashSet<Integer> finalStates = new HashSet<Integer>();
	protected boolean modified;
	protected TreeSet<Character> alphabet = new TreeSet<Character>();
}
