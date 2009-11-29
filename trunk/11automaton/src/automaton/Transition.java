package automaton;

public class Transition {
	protected State fromState;
	protected State toState;
	public Transition(){
		
	}
	public State getFromState(){
		return fromState;
	}
	public State getToState(){
		return this.toState;
	}
}
