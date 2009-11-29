package automaton;

import java.util.ArrayList;

public class TransitionNFA extends Transition{
	
	private ArrayList <String> transitionConditions;
	public TransitionNFA(State from,ArrayList<String> condition,State to){
		this.fromState = from;
		this.transitionConditions = condition;
		this.toState = to;
	}
	
	public ArrayList <String> getTransitionConditions(){
		return transitionConditions;
	}
	public void setTransitionCondition(ArrayList <String> condition){
		this.transitionConditions = condition;
	}
	

}
