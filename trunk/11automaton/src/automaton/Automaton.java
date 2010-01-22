package automaton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Automaton {
	protected String automatonName;
	protected String automatonType;
	protected ArrayList<String> inputSymbolSet;
	protected State initialState;
	protected HashMap<String, State> states;
	protected ArrayList<Transition> transitions;

	public String toString() {
		String text = "";
		text += "Type:" + automatonType + "\n";
		text += "Symbols:";
		for (String symbol : inputSymbolSet)
			text += symbol + " ";
		text += "\n";
		text += "States:" + states.keySet();
		if(initialState!=null){
			text += "Initial State:" + initialState.getStateId() + "\n";
		}else{
			text += "Initial State: null \n";
		}
		text += "Transitions:";
		for (Transition transition : transitions){
			text += transition.getFromState().getStateId() + " to "
			+ transition.getToState().getStateId() + ";";
			text += "nail number:"+transition.getNails().size();
		}
			
		return text;
	}

	public Automaton() {
		initialState = null;
		inputSymbolSet = new ArrayList<String>();
		states = new HashMap<String, State>();
		transitions = new ArrayList<Transition>();
	}

	/***************************************************
	 * Operation on Automaton State
	 */
	public boolean addState(State state) {
		if (states.containsKey(state.getStateId())) {
			return false;
		}
		states.put(state.getStateId(), state);
		return true;
	}

	public boolean removeState(State state) {
		if (states.containsKey(state.getStateId())) {
			states.remove(state.getStateId());
			return true;
		}
		return false;
	}

	public boolean setState(State state) {
		if (states.containsKey(state.getStateId())) {
			states.put(state.getStateId(), state);
			return true;
		}
		return false;
	}

	/***************************************************
	 * Operation on Automaton Transitions
	 */
	public int getTransitionIndex(Transition transition) {
		for (int i = 0; i < transitions.size(); i++) {
			if (transition.equals(transitions.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public boolean addTransition(Transition transition) {
		int index = getTransitionIndex(transition);
		if (index != -1) {
			return false;
		}
		transitions.add(transition);
		return true;
	}

	public boolean removeTransition(Transition transition) {
		int index = getTransitionIndex(transition);
		if (index != -1) {
			transitions.remove(transition);
			return true;
		}
		return false;
	}

	public boolean setTransition(Transition transition) {
		int index = getTransitionIndex(transition);
		if (index == -1) {
			return false;
		}
		transitions.set(index, transition);
		return true;
	}

	/***************************************************
	 * Get Method For Basic Attributes
	 * 
	 * @return
	 */
	public State getInitialState() {
		return initialState;
	}

	public String getAutomatonName() {
		return automatonName;
	}

	public String getAutomatonType() {
		return automatonType;
	}

	public ArrayList<String> getInputSymbolSet() {
		return inputSymbolSet;
	}

	public HashMap<String, State> getStates() {
		return states;
	}

	public ArrayList<Transition> getTransitions() {
		return transitions;
	}

	/**************************************************
	 * retrun the list of accepted states
	 * 
	 * @return
	 */
	public HashMap<String, State> getAcceptedStates() {
		Collection<State> tempStates = states.values();
		Iterator<State> iterator = tempStates.iterator();
		HashMap<String, State> acceptedStates = new HashMap<String, State>();
		while (iterator.hasNext()) {
			State temp = iterator.next();
			if (temp.stateType == AutomatonConstant.STATETYPE_ACCEPTED) {
				acceptedStates.put(temp.getStateId(), temp);
			}
		}
		return acceptedStates;
	}

	/***************************************************
	 * Set Method For Basic Attributes
	 * 
	 * @return
	 */
	public void setAutomatonName(String automatonName) {
		this.automatonName = automatonName;
	}

	public void setAutomatonType(String automatonType) {
		this.automatonType = automatonType;
	}

	public void setInputSymbolSet(ArrayList<String> inputSymbolSet) {
		this.inputSymbolSet = inputSymbolSet;
	}

	public void setStates(HashMap<String, State> states) {
		this.states = states;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}

	// public String toString() {
	// StringBuilder builder = new StringBuilder();
	// builder.append("automaton name:" + automatonName + "\n");
	// builder.append("automaton Type:" + automatonType + "\n");
	// builder.append("inputSymbolSet:");
	// if (inputSymbolSet.size() == 0) {
	// builder.append("no elements\n");
	// } else {
	// for (int i = 0; i < inputSymbolSet.size(); i++) {
	// builder.append(inputSymbolSet.get(i) + ",");
	// }
	// builder.deleteCharAt(builder.length() - 1);
	// builder.append("\n");
	// }
	// builder.append("state number:" + states.size() + "\n");
	// builder.append("transition number:" + transitions.size() + "\n");
	// return builder.toString();
	// }

	public boolean hasInputSymbol(String cond) {
		return inputSymbolSet.contains(cond);
	}

	public void addInputSymbol(String cond) {
		inputSymbolSet.add(cond);
	}

	public State getState(String state_id) {
		return states.get(state_id);
	}
}
