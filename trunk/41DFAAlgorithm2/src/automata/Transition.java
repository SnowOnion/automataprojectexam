package automata;

import java.util.HashSet;

public class Transition {
//------------------------------------------------------------------------------
	// Constructors
	public Transition(State from, State to, HashSet<String> cond) {
		this.from = from;
		this.to = to;
		this.conditions = cond;
	}
	public Transition(State from, State to) {
		this.from = from;
		this.to = to;
	}
	@Override
	public Object clone() {
		Transition c = new Transition(this.from, this.to, this.conditions);
		return c;
	}
	// Claim that this transition is no longer in use, perhaps for that 
	// the state associated to this transition has been deleted.
	public void dispose() {
		valid = false;
	}
	
	// Returns whether this transition is still functional.
	public boolean isValid() {
		return valid;
	}
	
	public boolean containsCond(String c) {
		return conditions.contains(c);
	}
	// Getters & Setters
	public State fromState() {
		return from;
	}
	public void setFromState(State s) {
		from = s;
	}
	public State toState() {
		return to;
	}
	public void setToState(State s) {
		to = s;
	}
	
	public HashSet<String> conditions() {
		return conditions;
	}
//------------------------------------------------------------------------------
	protected State from;
	protected State to;
	protected HashSet<String> conditions = new HashSet<String>();
	protected boolean valid = true;
}
