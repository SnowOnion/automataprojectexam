package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

import automatondesigner.SystemConstant;

/**
 * �Զ����ࡣ �ɱ�ʾNFA��DFA����������Ϊ�Զ������౻�̳С�
 * 
 * @author David
 * 
 */
public class Automaton {
	/**
	 * �Զ�����״̬���ϣ����е�״̬�����ڴ˼����У�������ʼ״̬����ͨ״̬�ͽ���״̬
	 */
	protected ArrayList<State> m_States;
	/**
	 * ������ĸ��
	 */
	protected ArrayList<String> m_InputSymbols;
	/**
	 * ת�ƺ���
	 */
	protected ArrayList<TransFunction> m_transFunctions;
	/**
	 * ��ʼ״̬
	 */
	protected State m_StartState;
	/**
	 * �Զ������ͣ�ʹ���ַ�����ʾ
	 */
	protected byte m_Type;
	
	/**
	 * �Զ�������
	 */
	protected String m_Name;


	/**
	 * Ĭ�Ϲ��캯��
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
	 * �Ƴ�״̬��������ڵĻ�����
	 * 
	 * @param state
	 *            Ҫ�Ƴ���״̬
	 */
	public void removeState(State state) {
		if ((state.getM_type() & AutomatonConst.STATE_INITIAL_TYPE) != 0) {
			m_StartState = null;
		}
		m_States.remove(state);
	}

	/**
	 * �¼�״̬
	 * 
	 * @param state
	 *            Ҫ��ӵ�״̬
	 * @return ����Ѿ��������ʧ�ܣ�����false�����򷵻�true��
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
	 * �޸��Զ�����ĳ��״̬������
	 * 
	 * @param state
	 *            Ҫ�޸ĵ�״̬
	 * @param type
	 *            �޸ĺ������
	 * @return �Ƿ��޸ĳɹ�
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

	public boolean checkStateNameUnique(String newname) {
		for (State s : m_States) {
			if (s.getM_Name().equals(newname)) {
				return false;
			}
		}
		return true;
	}

	public State getStateByName(String name){
		for (State s : m_States) {
			if (s.getM_Name().equals(name)) {
				return s;
			}
		}
		return null;
	}
	
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
	 * ��������������ĸ�����
	 * @param needclear ����֮ǰ�Ƿ���Ҫ���ԭ������ĸ��
	 * @return ������������ĸ�������
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

	// /**
	// * ��ȡ�������״̬s�����͡�
	// * ���ڽ��沿��Ҫ�Կ�ʼ״̬Ҳ�ǽ���״̬�����������Ĵ�����˴˴��������ࡣ
	// * @param s Ҫ��ȡ״̬�����͡�
	// * @return ����AutomatonConst�ж�����ַ������ص�����
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
	 * �����������
	 * 
	 * @param s
	 *            Ҫ��ӵ��������
	 */
	public boolean addInputSymbol(String s) {
		if (!m_InputSymbols.contains(s)) {
			m_InputSymbols.add(s);
			return true;
		}
		return false;
	}

	/**
	 * �Ƴ��������
	 * 
	 * @param s
	 *            Ҫ�Ƴ����������
	 * @return �Ƿ�ɹ��Ƴ����ɹ�����true��ʧ�ܷ���false��
	 */
	public boolean removeInputSymbol(String s) {
		return m_InputSymbols.remove(s);
	}

	/**
	 * ����ת�ƺ���
	 * 
	 * @param transFunction
	 *            Ҫ���ӵ�ת�ƺ�������
	 */
	public void addTransFunction(TransFunction transFunction) {
		if (!m_transFunctions.contains(transFunction))
			m_transFunctions.add(transFunction);
	}

	/**
	 * ��ȡ�ս�״̬�ļ���
	 * 
	 * @return �Զ������ս�״̬�ļ���
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
	 * ɾ��ĳ��ת�ƺ���
	 * 
	 * @param transFunction
	 *            Ҫɾ����ת�ƺ�������
	 * @return ɾ�������Ƿ�ɹ�
	 */
	public boolean removeTransFunction(TransFunction transFunction) {
		return m_transFunctions.remove(transFunction);
	}

	public String getStrType(){
		return SystemConstant.AUTOMATONTYPES[getM_Type()];
	}
	
	public void setTypeByStr(String type){
		for (byte i = 0; i < SystemConstant.AUTOMATONTYPES.length; i++){
			if (SystemConstant.AUTOMATONTYPES[i].equals(type)){
				this.setM_Type(i);
			}
		}
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

	public byte getM_Type() {
		return m_Type;
	}

	public void setM_Type(byte mType) {
		m_Type = mType;
	}

	public String getM_Name() {
		return m_Name;
	}

	public void setM_Name(String mName) {
		m_Name = mName;
	}
	
	public String getStartStateName(){
		if (m_StartState != null)
			return m_StartState.getM_Name();
		return "";
	}
}
