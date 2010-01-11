package edu.cn.thss.qmy.ndfa.core;

public class Transition {

	private Integer preState;
	private Integer symbol;
	private Integer nextState;
	
	public Transition() {
		
	}
	
	public Integer getPreState() {
		return preState;
	}
	public void setPreState(Integer preState) {
		this.preState = preState;
	}
	public Integer getSymbol() {
		return symbol;
	}
	public void setSymbol(Integer symbol) {
		this.symbol = symbol;
	}
	public Integer getNextState() {
		return nextState;
	}
	public void setNextState(Integer nextState) {
		this.nextState = nextState;
	}
	
//	public int compareTo(Object o2) {
////		Transition t1 = (Transition)o1;
//		Transition t2 = (Transition)o2;
//		return this.getPreState().compareTo(t2.getPreState());
//	}
}
