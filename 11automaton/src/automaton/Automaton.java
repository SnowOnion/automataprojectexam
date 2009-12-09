package automaton;

import java.util.ArrayList;

public class Automaton {
	protected String automatonName;
	protected String automatonType;
	protected ArrayList<String> inputSymbolSet;
	protected State initialState;
	protected ArrayList<State> states;
	protected ArrayList<Transition> transitions;
	
	public Automaton() {
		
	}
	/***************************************************
	 * Operation on Automaton State
	 */
	public int getStateIndex(State state) {
		for(int i = 0;i<states.size();i++){
			if(state.equals(states.get(i))){
				return i;
			}
		}
		return -1;
	}
	public boolean addState(State state) {
		int index = getStateIndex(state);
		if (index != -1) {
			return false;
		}
		states.add(state);
		return true;
	}
	public boolean removeState(State state) {
		int index = getStateIndex(state);
		if (index != -1) {
			states.remove(state);
			return true;
		}
		return false;
	}
	public boolean setState(State state){
		int index = getStateIndex(state);
		if(index==-1){
			return false;
		}
		states.set(index, state);
		return true;
	}
	/***************************************************
	 * Operation on Automaton Transitions
	 */
	public int getTransitionIndex(Transition transition){
		for(int i = 0;i<transitions.size();i++){
			if(transition.equals(transitions.get(i))){
				return i;
			}
		}
		return -1;
	}
	public boolean addTransition(Transition transition){
		int index = getTransitionIndex(transition);
		if(index != -1){
			return false;
		}
		transitions.add(transition);
		return true;
	}
	public boolean removeTransition(Transition transition){
		int index = getTransitionIndex(transition);
		if(index != -1){
			transitions.remove(transition);
			return true;
		}
		return false;
	}
	public boolean setTransition(Transition transition){
		int index = getTransitionIndex(transition);
		if(index == -1){
			return false;
		}
		transitions.set(index, transition);
		return true;
	}
	
	/***************************************************
	 * Get Method For Basic Attributes
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
	public ArrayList <State> getStates() {
		return states;
	}
	public ArrayList<Transition> getTransitions(){
		return transitions;
	}

	/***************************************************
	 * Set Method For Basic Attributes
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
	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}
	public void setTransitions(ArrayList<Transition> transitions){
		this.transitions = transitions;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("automaton name:"+automatonName+"\n");
		builder.append("automaton Type:"+automatonType+"\n");
		builder.append("inputSymbolSet:");
		if(inputSymbolSet.size()==0){
			builder.append("no elements\n");
		}else{
			for(int i = 0;i<inputSymbolSet.size();i++){
				builder.append(inputSymbolSet.get(i)+",");
			}
			builder.deleteCharAt(builder.length()-1);
			builder.append("\n");
		}
		builder.append("state number:"+states.size()+"\n");
		builder.append("transition number:"+transitions.size()+"\n");
		return builder.toString();
	}
}
