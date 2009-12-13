package automata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Transitions {
//------------------------------------------------------------------------------
	// Add a new transition.
	public void addTransition(Transition s) {
		trans.add(s);
		conditions.addAll(s.conditions());
	}
	
	// Remove an exist transition, and mark it as invalid.
	public void removeTransition(Transition s) {
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			if (t == s) {
				t.dispose();
				it.remove();
			}
		}
		updateConditions();
	}
	
	// Return whether there exist a transition accepting 'c'.
	public boolean containsCond(char c) {
		return conditions.contains(c);
	}
	
	public HashSet<Character> conditions() {
		return conditions;
	}
	
	// Return whether this set of transitions is empty.
	public boolean isEmpty() {
		return trans.isEmpty();
	}
	
	
	// Remove all invalid transitions.
	public void cleanUp() {
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			if (!t.isValid()) {
				it.remove();
			}
		}
		updateConditions();
	}
	
	// Dispose all transitions within, probably for that the state owning these
	// transitions has been deleted.
	public void dispose() {
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			t.dispose();
		}
	}
	
	// Update the conditions when some transition is removed
	protected void updateConditions() {
		Iterator<Transition> t = trans.iterator();
		while (t.hasNext()) {
			conditions.addAll(t.next().conditions());
		}
	}
//------------------------------------------------------------------------------	
	protected LinkedList<Transition> trans = new LinkedList<Transition>();
	protected HashSet<Character> conditions = new HashSet<Character>();
}
