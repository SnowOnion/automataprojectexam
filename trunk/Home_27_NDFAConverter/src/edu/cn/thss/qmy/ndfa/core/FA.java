package edu.cn.thss.qmy.ndfa.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.cn.thss.qmy.ndfa.util.Globals;

/**
 * @author Mengyue
 * The FA Structure.
 */
public class FA {

	private String name;
	private HashMap<Integer, State> states;
	private HashMap<Integer, Character> symbols;
	private Set<Transition> transitions;
	private Integer startState;
	private Set<Integer> finalStates;
	private boolean isDFA;
	
	public FA() {
		this.name = "";
		this.states = new HashMap<Integer, State>();
		this.symbols = new HashMap<Integer, Character>();
		this.transitions = new HashSet<Transition>();
		this.startState = Globals.ILEGAL_SYMBOL;
		this.finalStates = new HashSet<Integer>();
		this.setIsDFA();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public HashMap<Integer, State> getStates() {
		return states;
	}
	public void setStates(HashMap<Integer, State> states) {
		this.states = states;
	}
	public void addState(Integer key, State state) {
		this.states.put(key, state);
	}
	public Integer getStateKeyByName(String name) {
		for(Integer i = 0; i < this.states.size(); i++){
			State state = this.states.get(i);
			if(state.getName().equals(name)){
				return i;
			}
		}
		return Globals.ILEGAL_SYMBOL;
	}
	public State getStateByKey(Integer key) {
		return this.states.get(key);
	}
	public Set<Integer> getNextStateKey(Integer preStateKey, Integer symbolKey){
		Set<Integer> nextStateKeys = new HashSet<Integer>();
		Iterator<Transition> it = this.transitions.iterator();
		while(it.hasNext()){
			Transition transition = it.next();
			if(transition.getPreState().equals(preStateKey) && transition.getSymbol().equals(symbolKey)){
				nextStateKeys.add(transition.getNextState());
			}
		}
		return nextStateKeys;
	}

	public String getStateNameBySubState(State state){
		Iterator<Integer> it = this.states.keySet().iterator();
		while(it.hasNext()){
			State curState = this.getStateByKey(it.next());
			if(curState.subStatesEquals(state)){
				return curState.getName();
			}
		}
		return null;
	}
	
	public HashMap<Integer, Character> getSymbols() {
		return symbols;
	}
	public void setSymbols(HashMap<Integer, Character> symbols) {
		this.symbols = symbols;
	}
	public void addSymbol(Integer key, Character symbol) {
		this.symbols.put(key, symbol);
	}
	public Integer getSymbolKeyBySymbol(Character name) {
		Iterator<Integer> it = this.symbols.keySet().iterator();
		while(it.hasNext()){
			Integer key = it.next();
			Character symbol = this.symbols.get(key);
			if(symbol.equals(name)){
				return key;
			}
		}
		return Globals.ILEGAL_SYMBOL;
	}
	public Character getSymbolByKey(Integer key){
		return this.symbols.get(key);
	}
	
	public Set<Transition> getTransitions() {
		return transitions;
	}
	public void setTransitions(Set<Transition> transitions) {
		this.transitions = transitions;
	}
	public void addTransition(Transition transition) {
		this.transitions.add(transition);
	}
	public Transition getTransitionByPreAndSym(Integer pre, Integer sym) {
		Iterator<Transition> it = this.transitions.iterator();
		while(it.hasNext()){
			Transition transition = it.next();
			if(transition.getPreState().equals(pre) && transition.getSymbol().equals(sym)){
				return transition;
			}
		}
		return null;
	}
	
	public Integer getStartState() {
		return startState;
	}
	public void setStartState(Integer startState) {
		this.startState = startState;
	}
	
	public Set<Integer> getFinalStates() {
		return finalStates;
	}
	public void setFinalStates(Set<Integer> finalStates) {
		this.finalStates = finalStates;
	}
	public void addFinalState(Integer finalState) {
		this.finalStates.add(finalState);
	}
	
	public boolean getIsDFA() {
		return isDFA;
	}
	public void setIsDFA() {
		Iterator<Integer> stateIt = this.states.keySet().iterator();
		while(stateIt.hasNext()){
			Integer stateKey = stateIt.next();
			Iterator<Integer> symIt = this.symbols.keySet().iterator();
			while(symIt.hasNext()){
				Integer symKey = symIt.next();
				if(this.getNextStateKey(stateKey, symKey).size() != 1){
					this.isDFA = false;
					return;
				}
			}
		}
		this.isDFA = true;
	}
	
	public HashMap<Integer, State> getEClose(){
		HashMap<Integer, State> newStates = new HashMap<Integer, State>();
		Iterator<Integer> it = this.states.keySet().iterator();
		int i = 0;
		while(it.hasNext()){
			Integer key = it.next();
			newStates.put(i, this.getEClose(key));
			i++;
		}
		return newStates;
	}
	
	public State getEClose(Integer start){
		State startState = this.getStateByKey(start);
		State state = new State();
		state.addSubStates(startState);
		String stateName = startState.getName();
		
		Iterator<Transition> transitionIt = this.transitions.iterator();
		while(transitionIt.hasNext()){
			Transition transition = transitionIt.next();
			State nextState = this.getStateByKey(transition.getNextState());
			if(startState.equals(this.getStateByKey(transition.getPreState()))
					&& transition.getSymbol().equals(0)){
				state.addSubStates(nextState);
				stateName += nextState.getName();
			}
		}
		state.setName(stateName);
		
		return state;
	}
}
