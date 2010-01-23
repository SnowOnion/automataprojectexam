package automata;

import java.util.HashSet;
import java.util.Set;

public class State {
//------------------------------------------------------------------------------
	// Constructor
	public State(String name) {
		this.name = name;
	}
	
	// Add transitions
	public void addOutTransition(Transition s) {
		out.addTransition(s);
	}
	public void addInTransition(Transition s) {
		in.addTransition(s);
	}
	// Remove transitions
	public void removeOutTransition(Transition s) {
		out.removeTransition(s);
	}
	public void removeInTransition(Transition s) {
		in.removeTransition(s);
	}
	
	// Getters
	public String label() {
		return name;
	}
	public InTransitions inTransitions() {
		return in;
	}
	public OutTransitions outTransitions() {
		return out;
	}
	
	// Adapters
	public Set<State> toStates() {
		return out.toStates();
	}
	public Set<State> fromStates() {
		return in.fromStates();
	}
	public boolean acceptsCond(String c) {
		return out.containsCond(c);
	}
	public HashSet<State> toStatesByCond(String c) {
		return out.toStatesByCond(c);
	}
	public HashSet<State> fromStatesByCond(String c) {
		return in.fromStatesByCond(c);
	}
	public void cleanUpInTransitions() {
		in.cleanUp();
	}
	public void cleanUpOutTransitions() {
		out.cleanUp();
	}
	
	// Claim that this state is no longer in use.
	public void dispose() {
		in.dispose();
		out.dispose();
	}
	
//------------------------------------------------------------------------------
	protected String name;
	protected InTransitions in = new InTransitions();
	protected OutTransitions out = new OutTransitions();
}
