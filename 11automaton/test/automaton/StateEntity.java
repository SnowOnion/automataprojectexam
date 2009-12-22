package automaton;

import java.util.ArrayList;

public class StateEntity extends State{
	public StateEntity(String stateId, byte stateType) {
		super(stateId, stateType);
		// TODO Auto-generated constructor stub
	}

	/**********************************************
	 * transitions and toStates are used to create a tree structure
	 * for the automaton modeling.
	 * If you get a Initial State, you may build a tree whose root is
	 * such state, and the relative states and transitions could be stored
	 * in the two attributes in State
	 * 
	 * In default situation, these two attributes are empty list.
	 */
	protected ArrayList<Transition> transitions;
	protected ArrayList<State> toStates;

	public ArrayList<State> getToStates() {
		toStates.clear();
		for (int i = 0; i < transitions.size(); i++) {
			State temp = transitions.get(i).getToState();
			if (!toStates.contains(temp)) {
				toStates.add(temp);
			}
		}
		return toStates;
	}

	/***************************************************
	 * 
	 * @return all the transitions which starts from this state
	 */
	public ArrayList<Transition> getTransitions() {
		return transitions;
	}

	/****************************************************
	 * 
	 * @param transition
	 * @return true if the state has the transition given by parameter
	 */
	public boolean hasTransition(Transition transition) {
		return false;
	}

	/****************************************************
	 * Requires: the state has the transition. Effects: Remove the given
	 * transition from the state's transition list.
	 * 
	 * @param transition
	 */
	public boolean removeTransition(Transition transition) {
		if (transitions.contains(transition)) {
			transitions.remove(transition);
			toStates.remove(transition.getToState());
		}
		return false;
	}

	/*****************************************************
	 * 
	 * @param transition
	 */
	public boolean addTransition(Transition transition) {
		// initial State invalid
		if (!transition.getFromState().getStateId().equals(stateId)) {
			return false;
		}
		transitions.add(transition);
		toStates.add(transition.getToState());
		return false;
	}
}
