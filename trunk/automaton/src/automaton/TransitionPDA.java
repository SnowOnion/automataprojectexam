package automaton;

import java.util.ArrayList;

public class TransitionPDA extends Transition{
	private ArrayList <TransitionPDACondition> transitionConditions;
	public TransitionPDA(State from,ArrayList<TransitionPDACondition> condition,State to){
		this.fromState = from;
		this.transitionConditions = condition;
		this.toState = to;
	}
	public ArrayList <TransitionPDACondition> getTransitionConditions(){
		return transitionConditions;
	}
	public void setTransitionConditions(ArrayList <TransitionPDACondition> transitionConditions){
		this.transitionConditions = transitionConditions;
	}
}
