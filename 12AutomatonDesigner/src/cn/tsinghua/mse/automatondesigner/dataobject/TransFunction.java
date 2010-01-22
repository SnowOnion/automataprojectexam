package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

/**
 * ת�ƺ���
 * @author David
 *
 */
public class TransFunction {
	/**
	 * ��ʼ״̬
	 */
	private State m_BeginState;
	/**
	 * ����״̬
	 */
	private State m_EndState;
	/**
	 * ת������
	 */
	private ArrayList<TransCondition> m_TransCondition;

	public TransFunction(State bState, State eState, ArrayList<TransCondition> inputs){
		setM_BeginState(bState);
		setM_EndState(eState);
		setM_TransCondition(inputs);
	}
	
	/**
	 * ����������
	 * @param input Ҫ��ӵ��������
	 */
	public void addInputSymbol(TransCondition input){
		if (!m_TransCondition.contains(input)){
			m_TransCondition.add(input);
		}
	}
	
	/**
	 * �Ƴ��������
	 * @param input Ҫ�Ƴ����������
	 * @return �Ƿ��Ƴ��ɹ�
	 */
	public boolean removeInputSymbol(TransCondition input){
		if (m_TransCondition.contains(input)){
			m_TransCondition.remove(input);
			return true;
		}
		return false;
	}
	
	/**
	 * �Ƴ�ĳ��ת��״̬
	 * @param cond ���Ƴ���ת��״̬
	 */
	public void removeCondition(Object cond){
		if (cond instanceof String){
			for (int i = m_TransCondition.size()-1; i >= 0; i--){
				if (m_TransCondition.get(i).getM_InputSymbol().equals(cond)){
					m_TransCondition.remove(i);
				}
			}
		}
	}
	
	/**
	 * ��ȡת�ƺ����Ŀ�ʼ״̬
	 * @return ת�ƺ����Ŀ�ʼ״̬
	 */
	public State getM_BeginState() {
		return m_BeginState;
	}
	/**
	 * ����ת�ƺ����Ŀ�ʼ״̬
	 * @param mBeginState ת�ƺ����Ŀ�ʼ״̬
	 */
	public void setM_BeginState(State mBeginState) {
		m_BeginState = mBeginState;
	}
	/**
	 * ��ȡת�ƺ����Ľ���״̬
	 * @return ת�ƺ����Ľ���״̬
	 */
	public State getM_EndState() {
		return m_EndState;
	}

	/**
	 * ����ת�ƺ����Ľ���״̬
	 * @param mEndState ת�ƺ����Ľ���״̬
	 */
	public void setM_EndState(State mEndState) {
		m_EndState = mEndState;
	}	
	
	/**
	 * ��ȡת����������
	 * @return ת����������
	 */
	public ArrayList<TransCondition> getM_TransCondition() {
		return m_TransCondition;
	}

	/**
	 * ����ת����������
	 * @param mTransCondition ת����������
	 */
	public void setM_TransCondition(ArrayList<TransCondition> mTransCondition) {
		m_TransCondition = mTransCondition;
	}
	
	/**
	 * ��ȡ�ַ�����ʽ��ת��������
	 * @return �ַ������
	 */
	public String getStrCondition(){
		String result = "";
		if(m_TransCondition != null && m_TransCondition.size() != 0){
			String division = ",";
			if (m_TransCondition.get(0) instanceof PDATransCondition){
				division = "\n";
			}
			int idx = 0;
			for(idx = 0; idx < m_TransCondition.size()-1; idx++){
				result += m_TransCondition.get(idx).toString() + division;
			}
			result += m_TransCondition.get(idx).toString();
		}
		return result;
	}
	
	/**
	 * ���ת������
	 * @param condition Ҫ��ӵ�ת������
	 * @return ����Ѿ����������ʧ�ܷ���false�����򷵻�true
	 */
	public boolean addTransCondition(TransCondition condition){
		for (TransCondition c : m_TransCondition){
			if (c.toString().equals(condition.toString())){
				return false;
			}
		}
		m_TransCondition.add(condition);
		return true;
	}
}
