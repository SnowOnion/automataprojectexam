package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

/**
 * 自动机类。
 * 可表示NFA和DFA，并可用作为自动机超类被继承。
 * @author David
 *
 */
public class Automaton {
	/**
	 * 自动机的状态集合，所有的状态都会在此集合中，包括开始状态、普通状态和接受状态
	 */
	protected ArrayList<State> m_States;
	/**
	 * 输入字母表
	 */
	protected ArrayList<String> m_InputSymbols;
	/**
	 * 转移函数
	 */
	protected ArrayList<TransFunction> m_transFunctions;
	/**
	 * 开始状态
	 */
	protected State m_StartState;
	/**
	 * 终结状态
	 */
	protected ArrayList<State> m_FinalStates;
	/**
	 * 自动机类型，使用字符串表示
	 */
	protected String m_Type; 
	
	/**
	 * 默认构造函数
	 */
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
	
	/**
	 * 移除状态，会将状态从终结状态集合、状态集合和开始状态中移除（如果存在的话）。
	 * @param state 要移除的状态
	 */
	public void removeState(State state){
		if (m_FinalStates != null && m_FinalStates.size() != 0)
			m_FinalStates.remove(state);
		if (m_States != null && m_States.size() != 0)
			m_States.remove(state);
		if (m_StartState != null && m_StartState.equals(state))
			m_StartState = null;
	}
	
	/**
	 * 新加状态，由参数stateType标记类型
	 * @param state	要添加的状态
	 * @param stateType	新加状态类型
	 * @return 如果已经存在添加失败，返回false；否则返回true。
	 */
	public boolean addState(State state, String stateType){
		if (m_States.contains(state)){
			return false;
		}
		m_States.add(state);
		if (stateType.equals(AutomatonConst.STATE_TYPE_INI_FINAL)){
			m_FinalStates.add(state);
			m_StartState = state;
		}
		else if (stateType.equals(AutomatonConst.STATE_TYPE_INITIAL)){
			m_StartState = state;
		}
		else if (stateType.equals(AutomatonConst.STATE_TYPE_FINAL)){
			m_FinalStates.add(state);
		}
		return true;
	}
	
	/**
	 * 获取传入参数状态s的类型。
	 * 由于界面部分要对开始状态也是结束状态的情况作特殊的处理，因此此处分了四类。
	 * @param s 要获取状态的类型。
	 * @return	依据AutomatonConst中定义的字符串返回的类型
	 */
	public String getStateType(State s){
		String type = AutomatonConst.STATE_TYPE_COMMON;
		if (m_StartState != null && m_StartState.equals(s))
			type = AutomatonConst.STATE_TYPE_INITIAL;
		if (m_FinalStates != null && m_FinalStates.contains(s)){
			if (!type.equals(AutomatonConst.STATE_TYPE_INITIAL))
				type = AutomatonConst.STATE_TYPE_FINAL;
			else
				type = AutomatonConst.STATE_TYPE_INI_FINAL;
		}
		return type;
	}
	
	/**
	 * 新增输入符号
	 * @param s 要添加的输入符号
	 */
	public void addInputSymbol(String s){
		if (!m_InputSymbols.contains(s)){
			m_InputSymbols.add(s);
		}
	}
	
	/**
	 * 移除输入符号
	 * @param s 要移除的输入符号
	 * @return 是否成功移除，成功返回true，失败返回false。
	 */
	public boolean removeInputSymbol(String s){
		return m_InputSymbols.remove(s);
	}
	
	/**
	 * 增加转移函数
	 * @param transFunction 要增加的转移函数对象
	 */
	public void addTransFunction(TransFunction transFunction){
		if(!m_transFunctions.contains(transFunction))
			m_transFunctions.add(transFunction);
	}
	
	/**
	 * 删除某个转移函数
	 * @param transFunction 要删除的转移函数对象
	 * @return
	 */
	public boolean removeTransFunction(TransFunction transFunction){
		return m_transFunctions.remove(transFunction);
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
