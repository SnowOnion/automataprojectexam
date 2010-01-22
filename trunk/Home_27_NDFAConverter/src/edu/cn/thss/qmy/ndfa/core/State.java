package edu.cn.thss.qmy.ndfa.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class State {
	
	private String name;
	private Set<State> subStates;
	
	public State(){
		this.name = "";
		this.subStates = new HashSet<State>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<State> getSubStates() {
		return subStates;
	}
	public void setSubStates(Set<State> subStates) {
		this.subStates = subStates;
	}
	public void addSubStates(State state) {
		this.subStates.add(state);
	}
	
	public boolean subStatesEquals(State state){
		Iterator<State> it = state.getSubStates().iterator();
		Integer size = subStates.size();
		Integer num = 0;
		while(it.hasNext()){
			State subState = it.next();
			if(subStates.contains(subState)){
				num++;
			}else{
				return false;
			}
		}
		if(num.equals(size)){
			return true;
		}
		return false;
	}
}
