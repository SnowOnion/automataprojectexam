package automaton;

import java.util.HashSet;
import java.util.List;


public class Transition {
	protected State fromState;
	protected State toState;
	@SuppressWarnings("unchecked")
	protected List transitionConditions;
	protected HashSet <Nail> nails;
	
	public Transition(){

	}
	public Transition(Transition transition){
		this.fromState = transition.getFromState();
		this.toState = transition.getToState();
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
	
	/******************************************************
	 * The location operation for GUI
	 * 
	 */
	public boolean addNail(Nail nail){
		return nails.add(nail);
	}
	public boolean removeNail(Nail nail){
		return nails.remove(nail);
	}
	public HashSet<Nail> getNails() {
		return nails;
	}
	public void setNails(HashSet<Nail> nails) {
		this.nails = nails;
	}
}
