package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

public class TransFunction {
	private State m_BeginState;
	private State m_EndState;
	private ArrayList<TransCondition> m_TransCondition;

	public TransFunction(State bState, State eState, ArrayList<TransCondition> inputs){
		setM_BeginState(bState);
		setM_EndState(eState);
		setM_TransCondition(inputs);
	}
	
	public void addInputSymbol(TransCondition input){
		if (!m_TransCondition.contains(input)){
			m_TransCondition.add(input);
		}
	}
	public boolean removeInputSymbol(TransCondition input){
		if (m_TransCondition.contains(input)){
			m_TransCondition.remove(input);
			return true;
		}
		return false;
	}
	
	public State getM_BeginState() {
		return m_BeginState;
	}
	public void setM_BeginState(State mBeginState) {
		m_BeginState = mBeginState;
	}
	public State getM_EndState() {
		return m_EndState;
	}
	public void setM_EndState(State mEndState) {
		m_EndState = mEndState;
	}	
	public ArrayList<TransCondition> getM_TransCondition() {
		return m_TransCondition;
	}

	public void setM_TransCondition(ArrayList<TransCondition> mTransCondition) {
		m_TransCondition = mTransCondition;
	}
}
