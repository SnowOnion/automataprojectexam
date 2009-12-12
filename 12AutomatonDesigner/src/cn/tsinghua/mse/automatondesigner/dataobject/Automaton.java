package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

/**
 * �Զ����ࡣ
 * �ɱ�ʾNFA��DFA����������Ϊ�Զ������౻�̳С�
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
	 * �ս�״̬
	 */
	protected ArrayList<State> m_FinalStates;
	/**
	 * �Զ������ͣ�ʹ���ַ�����ʾ
	 */
	protected String m_Type; 
	
	/**
	 * Ĭ�Ϲ��캯��
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
	 * �Ƴ�״̬���Ὣ״̬���ս�״̬���ϡ�״̬���ϺͿ�ʼ״̬���Ƴ���������ڵĻ�����
	 * @param state Ҫ�Ƴ���״̬
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
	 * �¼�״̬���ɲ���stateType�������
	 * @param state	Ҫ��ӵ�״̬
	 * @param stateType	�¼�״̬����
	 * @return ����Ѿ��������ʧ�ܣ�����false�����򷵻�true��
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
	 * ��ȡ�������״̬s�����͡�
	 * ���ڽ��沿��Ҫ�Կ�ʼ״̬Ҳ�ǽ���״̬�����������Ĵ�����˴˴��������ࡣ
	 * @param s Ҫ��ȡ״̬�����͡�
	 * @return	����AutomatonConst�ж�����ַ������ص�����
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
	 * �����������
	 * @param s Ҫ��ӵ��������
	 */
	public void addInputSymbol(String s){
		if (!m_InputSymbols.contains(s)){
			m_InputSymbols.add(s);
		}
	}
	
	/**
	 * �Ƴ��������
	 * @param s Ҫ�Ƴ����������
	 * @return �Ƿ�ɹ��Ƴ����ɹ�����true��ʧ�ܷ���false��
	 */
	public boolean removeInputSymbol(String s){
		return m_InputSymbols.remove(s);
	}
	
	/**
	 * ����ת�ƺ���
	 * @param transFunction Ҫ���ӵ�ת�ƺ�������
	 */
	public void addTransFunction(TransFunction transFunction){
		if(!m_transFunctions.contains(transFunction))
			m_transFunctions.add(transFunction);
	}
	
	/**
	 * ɾ��ĳ��ת�ƺ���
	 * @param transFunction Ҫɾ����ת�ƺ�������
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
