package automaton;

import java.util.ArrayList;

/**
 * NFA = (Q,I,T,q,F) Q: a finite set of states I: a finite set of input
 * symbols(including empty input) T: the transition functions q: the initial
 * State F: a set of final states
 * 
 * @author William Ma
 * 
 */
public class AutomatonNFA extends Automaton {
	public AutomatonNFA(){
		
	}
	//Given a initial State to build a DFA
	public AutomatonNFA(State state) {
		states = new ArrayList<State>();
		automatonType = AutomatonConstant.AUTOMATONTYPES[2];
		ArrayList<State> temp = new ArrayList<State>();
		temp = state.getToStates();
		states.add(state);
		while (!temp.isEmpty()) {
			State s = temp.get(0);
			states.add(s);
			ArrayList<State> lists = s.getToStates();
			for (int i = 0; i < lists.size(); i++) {
				if (!states.contains(lists.get(i))) {
					temp.add(lists.get(i));
				}
			}
		}
	}


	

}
