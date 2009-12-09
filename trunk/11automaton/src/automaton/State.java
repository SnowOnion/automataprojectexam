package automaton;

import java.util.ArrayList;

public class State {
	// state name should be unique in one Automaton
	protected String stateId;
	protected String stateType;
	
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
	
	/***************************************************
	 * Default State Type is normal
	 * @param stateId
	 */
	public State(String stateId) {
		this.stateId = stateId;
		stateType = AutomatonConstant.STATETYPES[1];
		transitions = new ArrayList<Transition>();
		toStates = new ArrayList<State>();
	}

	public State(String stateId, String stateType) {
		this.stateId = stateId;
		this.stateType = stateType;
		transitions = new ArrayList<Transition>();
		toStates = new ArrayList<State>();
	}

	public boolean setStateType(int index) {
		if (index > AutomatonConstant.STATETYPES.length - 1 || index < 0) {
			return false;
		}
		stateType = AutomatonConstant.STATETYPES[index];
		return true;
	}

	public void setStateId(String id) {
		this.stateId = id;
	}

	public String getStateId() {
		return stateId;
	}

	public String getStateType() {
		return stateType;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("(State Id:"+stateId+"\t");
		builder.append("State Type:"+stateType+")\n");
		return builder.toString();
	}
	
	

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
