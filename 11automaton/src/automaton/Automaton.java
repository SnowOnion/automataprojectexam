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

	public State getInitialState() {
		return initialState;
	}

	public String getAutomatonName() {
		return automatonName;
	}

	public void setAutomatonName(String automatonName) {
		this.automatonName = automatonName;
	}

	public String getAutomatonType() {
		return automatonType;
	}

	public void setAutomatonType(String automatonType) {
		this.automatonType = automatonType;
	}

	public ArrayList<String> getInputSymbolSet() {
		return inputSymbolSet;
	}

	public void setInputSymbolSet(ArrayList<String> inputSymbolSet) {
		this.inputSymbolSet = inputSymbolSet;
	}

	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}
	
	public ArrayList<Transition> getTransitions(){
		return transitions;
	}
	public void setTransitions(ArrayList<Transition> transitions){
		this.transitions = transitions;
	}

}
