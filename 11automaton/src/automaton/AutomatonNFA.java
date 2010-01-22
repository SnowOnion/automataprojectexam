package automaton;

/**
 * NFA = (Q,I,T,q,F) Q: a finite set of states I: a finite set of input
 * symbols(including empty input) T: the transition functions q: the initial
 * State F: a set of final states
 * 
 * @author William Ma
 * 
 */
public class AutomatonNFA extends Automaton {
	public AutomatonNFA() {
		super();
		setAutomatonType(AutomatonConstant.AUTOMATONTYPES[2]);
	}

}
