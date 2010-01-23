package automata;

import java.util.HashSet;
import java.util.Iterator;

public class InTransitions extends Transitions {
	// Remove an exist transition
	/* (non-Javadoc)
	 * @see automata.Transitions#removeTransition(automata.Transition)
	 */
	@Override
	public void removeTransition(Transition s) {
		super.removeTransition(s);
		s.fromState().outTransitions().cleanUp();
	}
	
	// Remove transitions go from 'from' to current state by accepting 'c'.
	public void removeTransitionFrom(String c, State from) {
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			if (t.containsCond(c) && t.fromState() == from) {
				t.dispose();
				it.remove();
			}
		}
	}
	
	// Alter transitions from 'src', making them connect to 'dest'.
	public void alterFromState(State src, State dest) {
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			if (t.fromState() == src)
				t.setFromState(dest);
		}
	}
	
	// Return all states that can reach current state.
	public HashSet<State> fromStates() {
		HashSet<State> s = new HashSet<State>();
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			s.add(t.fromState());
		}
		return s;
	}
	
	// Return all states which can reach current state
	// (which this instance belongs to) by accepting 'c'
	public HashSet<State> fromStatesByCond(String c) {
		HashSet<State> s = new HashSet<State>();
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			if (t.containsCond(c))
				s.add(t.from);
		}
		return s;
	}
}
