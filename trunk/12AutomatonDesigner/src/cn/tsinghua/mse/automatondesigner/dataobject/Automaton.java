package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

public class Automaton {
	protected ArrayList<State> m_States;
	protected ArrayList<String> m_InputSymbols;
	protected ArrayList<TransFunction> m_transFunctions;
	protected State m_StartState;
	protected ArrayList<State> m_FinalStates;
	protected String m_Type; 
	
	public Automaton(){
		m_States = new ArrayList<State>();
		m_InputSymbols = new ArrayList<String>();
		m_transFunctions = new ArrayList<TransFunction>();
		m_StartState = null;
		m_FinalStates = new ArrayList<State>();
		m_Type = "";
	}
	
	public Automaton(ArrayList<State> states, ArrayList<String> inputs, ArrayList<TransFunction> funcs, State start, ArrayList<State> finals, String type){
		m_States = states;
		m_InputSymbols = inputs;
		m_transFunctions = funcs;
		m_StartState = start;
		m_FinalStates = finals;
		m_Type = type;
	}
	
	public String getStateType(State s){
		if (m_StartState.getM_Name().endsWith(s.getM_Name()))
			return AutomatonConst.STATE_TYPE_INITIAL;
		else if (m_FinalStates.contains(s))
			return AutomatonConst.STATE_TYPE_FINAL;
		else
			return AutomatonConst.STATE_TYPE_COMMON;
	}
	
	public void addInputSymbol(String s){
		if (!m_InputSymbols.contains(s)){
			m_InputSymbols.add(s);
		}
	}
	
	public boolean removeInputSymbol(String s){
		return m_InputSymbols.remove(s);
	}
	
	public ArrayList<State> getM_States() {
		return m_States;
	}
	public void setM_States(ArrayList<State> mStates) {
		m_States = mStates;
	}
	public ArrayList<String> getM_InputSymbols() {
		return m_InputSymbols;
	}
	public void setM_InputSymbols(ArrayList<String> mInputSymbols) {
		m_InputSymbols = mInputSymbols;
	}
	public ArrayList<TransFunction> getM_transFunctions() {
		return m_transFunctions;
	}
	public void setM_transFunctions(ArrayList<TransFunction> mTransFunctions) {
		m_transFunctions = mTransFunctions;
	}
	public State getM_StartState() {
		return m_StartState;
	}
	public void setM_StartState(State mStartState) {
		m_StartState = mStartState;
	}
	public ArrayList<State> getM_FinalStates() {
		return m_FinalStates;
	}
	public void setM_FinalStates(ArrayList<State> mFinalStates) {
		m_FinalStates = mFinalStates;
	}
	public String getM_Type() {
		return m_Type;
	}
	public void setM_Type(String mType) {
		m_Type = mType;
	}
}
