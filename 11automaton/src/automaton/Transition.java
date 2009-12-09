package automaton;

import java.util.List;

public class Transition {
	protected State fromState;
	protected State toState;
	@SuppressWarnings("unchecked")
	protected List transitionConditions;
	
	public Transition(){
		
	}
	public State getFromState(){
		return fromState;
	}
	public State getToState(){
		return this.toState;
	}
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("fromState:"+fromState+"\n");
		builder.append("transition condition number:"+transitionConditions.size()+"\n");
		builder.append("toState"+toState+"\n");
		return builder.toString();
	}
}
