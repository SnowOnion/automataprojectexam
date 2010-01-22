package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

import org.eclipse.swt.internal.ole.win32.FUNCDESC;

import automatondesigner.SystemConstant;

/**
 * 自动机类。 可表示NFA和DFA，并可用作为自动机超类被继承。
 * 
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
	 * 自动机类型，使用字符串表示
	 */
	protected byte m_Type;
	
	/**
	 * 自动机名称
	 */
	protected String m_Name;


	/**
	 * 默认构造函数
	 */
	public Automaton() {
		m_States = new ArrayList<State>();
		m_InputSymbols = new ArrayList<String>();
		m_transFunctions = new ArrayList<TransFunction>();
		m_StartState = null;
		m_Type = AutomatonConst.AUTOMATON_TYPE_NFA;
		m_Name = "";
	}

	public Automaton(ArrayList<State> states, ArrayList<String> inputs,
			ArrayList<TransFunction> funcs, State start, byte type, String name) {
		m_States = states;
		m_InputSymbols = inputs;
		m_transFunctions = funcs;
		m_StartState = start;
		m_Type = type;
		m_Name = name;
	}

	/**
	 * 移除状态（如果存在的话）。
	 * 
	 * @param state
	 *            要移除的状态
	 */
	public void removeState(State state) {
		if ((state.getM_type() & AutomatonConst.STATE_INITIAL_TYPE) != 0) {
			m_StartState = null;
		}
		m_States.remove(state);
	}

	/**
	 * 新加状态
	 * 
	 * @param state
	 *            要添加的状态
	 * @return 如果已经存在添加失败，返回false；否则返回true。
	 */
	public boolean addState(State state) {
		if (m_States.contains(state)) {
			return false;
		}
		m_States.add(state);
		if ((state.getM_type() & AutomatonConst.STATE_INITIAL_TYPE) != 0) {
			m_StartState = state;
		}
		return true;
	}

	/**
	 * 修改自动机中某个状态的类型
	 * 
	 * @param state
	 *            要修改的状态
	 * @param type
	 *            修改后的类型
	 * @return 是否修改成功
	 */
	public boolean modifyStateType(State state, byte type) {
		if (!m_States.contains(state)) {
			return false;
		}
		if (state.getM_type() == type)
			return true;
		if ((type & AutomatonConst.STATE_INITIAL_TYPE) != 0) {
			if (m_StartState != null) {
				m_StartState
						.setM_type((byte) (m_StartState.getM_type() & AutomatonConst.STATE_FINAL_TYPE));
			}
			m_StartState = state;
		} else {
			if (m_StartState != null && m_StartState.equals(state)) {
				m_StartState = null;
			}
		}
		state.setM_type(type);
		return true;
	}

	/**
	 * 检查新的状态名称是否可以使用
	 * @param newname 要检查的状态名称
	 * @return 如果可以使用则为true，已被使用则会false
	 */
	public boolean checkStateNameUnique(String newname) {
		for (State s : m_States) {
			if (s.getM_Name().equals(newname)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 通过状态的名称获取状态
	 * @param name 要获取的状态名称
	 * @return 与名称相关的状态对象
	 */
	public State getStateByName(String name){
		for (State s : m_States) {
			if (s.getM_Name().equals(name)) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * 获取下一个序号的状态名称
	 * @param prefix 前缀
	 * @return  状态名称
	 */
	public String getNextNameIdx(String prefix) {
		int result = 0;
		for (State s : m_States) {
			String name = s.getM_Name();
			int idx = 0;
			if (name.startsWith(prefix)) {
				try {
					idx = Integer.valueOf(name.substring(prefix.length()));
				} catch (Exception e) {

				}
				if (idx >= result) {
					result = idx + 1;
				}
			}
		}
		return prefix + result;
	}
	
	/**
	 * 使开始状态放置在状态集合的第一个位置
	 */
	public void setInitStateFirst(){
		if (m_StartState != null){
			m_States.remove(m_StartState);
			m_States.add(0, m_StartState);
		}
	}
	
	/**
	 * 整理输入输入字母表符号
	 * @param needclear 整理之前是否需要清除原输入字母表
	 * @return 整理后的输入字母表的引用
	 */
	public ArrayList<String> clearUpInputSymbol(boolean needclear){
		if (needclear){
			m_InputSymbols.clear();
		}
		for (TransFunction func : m_transFunctions){
			for (TransCondition cond : func.getM_TransCondition()){
				if (!m_InputSymbols.contains(cond.getM_InputSymbol()))
					m_InputSymbols.add(cond.getM_InputSymbol());
			}
		}
		return m_InputSymbols;
	}
	
	/**
	 * 获取从某状态出发的所有转移函数，也可以从结果中提取所能到达的状态集合
	 * @param startState 转移函数的开始符号
	 * @return 以输入参数为开始符号的转移函数
	 */
	public ArrayList<TransFunction> getRelativeTrans(State startState){
		ArrayList<TransFunction> result = new ArrayList<TransFunction>();
		for(TransFunction func : m_transFunctions){
			if (func.getM_BeginState().equals(startState)){
				if (!result.contains(func))
					result.add(func);
			}
		}
		return result;
	}
	
	/**
	 * 获取从某状态出发的以某个输入符号为输入的所有转移函数所到达的状态集合
	 * @param startState 转移函数的开始符号
	 * @param input 输入符号
	 * @return 以输入参数为开始符号、输入符号所能到达的状态集合
	 */
	public ArrayList<State> getToStates(State startState, String input){
		ArrayList<State> result = new ArrayList<State>();
		for(TransFunction func : m_transFunctions){
			if (func.getM_BeginState().equals(startState)){
				for (TransCondition cond : func.getM_TransCondition()){
					if (cond.getM_InputSymbol().equals(input)){
						result.add(func.getM_EndState());
					}
				}
			}
		}
		return result;
	}

	// /**
	// * 获取传入参数状态s的类型。
	// * 由于界面部分要对开始状态也是结束状态的情况作特殊的处理，因此此处分了四类。
	// * @param s 要获取状态的类型。
	// * @return 依据AutomatonConst中定义的字符串返回的类型
	// */
	// public String getStateType(State s){
	// String type = SystemConstant.STATE_TYPE_COMMON;
	// if (m_StartState != null && m_StartState.equals(s))
	// type = SystemConstant.STATE_TYPE_INITIAL;
	// if (m_FinalStates != null && m_FinalStates.contains(s)){
	// if (!type.equals(AutomatonConst.STATE_TYPE_INITIAL))
	// type = SystemConstant.STATE_TYPE_FINAL;
	// else
	// type = SystemConstant.STATE_TYPE_INI_FINAL;
	// }
	// return type;
	// }

	/**
	 * 新增输入符号
	 * 
	 * @param s
	 *            要添加的输入符号
	 */
	public boolean addInputSymbol(String s) {
		if (!m_InputSymbols.contains(s)) {
			m_InputSymbols.add(s);
			return true;
		}
		return false;
	}

	/**
	 * 移除输入符号
	 * 
	 * @param s
	 *            要移除的输入符号
	 * @return 是否成功移除，成功返回true，失败返回false。
	 */
	public boolean removeInputSymbol(String s) {
		return m_InputSymbols.remove(s);
	}

	/**
	 * 增加转移函数
	 * 
	 * @param transFunction
	 *            要增加的转移函数对象
	 */
	public void addTransFunction(TransFunction transFunction) {
		if (!m_transFunctions.contains(transFunction))
			m_transFunctions.add(transFunction);
	}

	/**
	 * 获取终结状态的集合
	 * 
	 * @return 自动机中终结状态的集合
	 */
	public ArrayList<State> getFinalStates() {
		ArrayList<State> finalStates = new ArrayList<State>();
		for (State s : m_States) {
			if ((s.getM_type() & AutomatonConst.STATE_FINAL_TYPE) != 0)
				finalStates.add(s);
		}
		return finalStates;
	}

	/**
	 * 删除某个转移函数
	 * 
	 * @param transFunction
	 *            要删除的转移函数对象
	 * @return 删除操作是否成功
	 */
	public boolean removeTransFunction(TransFunction transFunction) {
		return m_transFunctions.remove(transFunction);
	}

	/**
	 * 获取字符串形式的自动机类型
	 * @return 字符串形式的自动机类型
	 */
	public String getStrType(){
		return SystemConstant.AUTOMATONTYPES[getM_Type()];
	}
	
	/**
	 * 根据字符串形式的类型设置自动机类型
	 * @param type 类型名称
	 */
	public void setTypeByStr(String type){
		for (byte i = 0; i < SystemConstant.AUTOMATONTYPES.length; i++){
			if (SystemConstant.AUTOMATONTYPES[i].equals(type)){
				this.setM_Type(i);
			}
		}
	}
	
	/**
	 * 获取状态集合
	 * @return 自动机的状态集合
	 */
	public ArrayList<State> getM_States() {
		return m_States;
	}

	/**
	 * 设置状态集合
	 * @param mStates 自动机的状态集合
	 */
	public void setM_States(ArrayList<State> mStates) {
		m_States = mStates;
	}

	/**
	 * 获取自动机的输入符号表
	 * @return 输入符号表
	 */
	public ArrayList<String> getM_InputSymbols() {
		return m_InputSymbols;
	}

	/**
	 * 设置自动机的输入符号表
	 * @param mInputSymbols 输入符号表
	 */
	public void setM_InputSymbols(ArrayList<String> mInputSymbols) {
		m_InputSymbols = mInputSymbols;
	}

	/**
	 * 获取自动机的转移函数列表
	 * @return 自动机的转移函数列表
	 */
	public ArrayList<TransFunction> getM_transFunctions() {
		return m_transFunctions;
	}

	/**
	 * 设置自动机的转移函数列表
	 * @param mTransFunctions 自动机的转移函数列表
	 */
	public void setM_transFunctions(ArrayList<TransFunction> mTransFunctions) {
		m_transFunctions = mTransFunctions;
	}

	/**
	 * 获得自动机的开始状态
	 * @return 自动机的开始状态
	 */
	public State getM_StartState() {
		return m_StartState;
	}

	/**
	 * 设置自动机的开始状态
	 * @param mStartState 自动机的开始状态
	 */
	public void setM_StartState(State mStartState) {
		m_StartState = mStartState;
	}

	/**
	 * 获取自动机的类型
	 * @return 自动机的类型
	 */
	public byte getM_Type() {
		return m_Type;
	}

	/**
	 * 设置自动机的类型
	 * @param mType 自动机的类型
	 */
	public void setM_Type(byte mType) {
		m_Type = mType;
	}

	/**
	 * 获取自动机名称
	 * @return 自动机名称
	 */
	public String getM_Name() {
		return m_Name;
	}

	/**
	 * 设置自动机名称
	 * @param mName 自动机名称
	 */
	public void setM_Name(String mName) {
		m_Name = mName;
	}
	
	/**
	 * 获取开始状态名称，没有开始状态则为空
	 * @return 开始状态名称
	 */
	public String getStartStateName(){
		if (m_StartState != null)
			return m_StartState.getM_Name();
		return "";
	}
}
