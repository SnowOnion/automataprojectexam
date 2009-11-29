package automaton;


/**********************************************************
 * DFA = (Q,I,T,q,F)
 * Q: a finite set of states
 * I: a finite set of input symbols
 * T: the transition functions
 * q: the initial State
 * F: a set of final states
 * 
 * @author Administrator
 *
 */
public class AutomatonDFA extends AutomatonNFA{
	public AutomatonDFA(){
		
	}
	public AutomatonDFA(State state) {
		super(state);
		// TODO Auto-generated constructor stub
	}
	
	/*******************************************************
	 * return true if the NFA is a DFA.
	 * @return
	 */
	public boolean isDFA(){
		//to do
		return false;
	}

	
}
