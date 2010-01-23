package automata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class OutTransitions extends Transitions {
	// Remove an exist transition
	@Override
	public void removeTransition(Transition s) {
		super.removeTransition(s);
		s.toState().inTransitions().cleanUp();
	}
	
	// Remove transitions that can reach 'to' by accepting 'c'.
	public void removeTransitionTo(char c, State to) {
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			if (t.containsCond(c) && t.toState() == to) {
				t.dispose();
				it.remove();
			}
		}
	}
	
	// Alter transitions to 'src', making them connect to 'dest'.
	public void alterToState(State src, State dest) {
		Transition t;
		LinkedList<Transition> temp = new LinkedList<Transition>();
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			if (t.toState() == src) {
				temp.add(t);
			}
		}
		// The following part is necessary, since removeTransiton()
		// would mark the transition as invalid. 
		// Avoiding concurrent modification exceptions is also in concern.
		it = temp.iterator();
		while (it.hasNext()) {
			t = it.next();
			removeTransition(t);
			t = (Transition)t.clone();
			t.setToState(dest);
			addTransition(t);
			dest.addInTransition(t);
		}
	}
	
	// Return all reachable states.
	public HashSet<State> toStates() {
		HashSet<State> s = new HashSet<State>();
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			s.add(t.toState());
		}
		return s;
	}
	
	// Return all states reached by accepting 'c'
	public HashSet<State> toStatesByCond(char c) {
		HashSet<State> s = new HashSet<State>();
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			if (t.containsCond(c))
				s.add(t.to);
		}
		return s;
	}
}
