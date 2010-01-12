package automaton;

import java.util.ArrayList;

public class TransitionPDA extends Transition{
	public TransitionPDA(State from,ArrayList<TransitionPDACondition> condition,State to){
		this.fromState = from;
		this.transitionConditions = condition;
		this.toState = to;
	}
	@SuppressWarnings("unchecked")
	public ArrayList <TransitionPDACondition> getTransitionConditions(){
		return (ArrayList<TransitionPDACondition>) transitionConditions;
	}
	public void setTransitionConditions(ArrayList <TransitionPDACondition> transitionConditions){
		this.transitionConditions = transitionConditions;
	}
}
