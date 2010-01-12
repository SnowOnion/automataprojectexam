package automaton;

import java.util.ArrayList;

public class TransitionNFA extends Transition{
	
	public TransitionNFA(State from,ArrayList<String> condition,State to){
		this.fromState = from;
		this.transitionConditions = condition;
		this.toState = to;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList <String> getTransitionConditions(){
		return (ArrayList <String>)transitionConditions;
	}
	
	public void setTransitionCondition(ArrayList <String> condition){
		this.transitionConditions = condition;
	}
	
	

}
