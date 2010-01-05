package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

/**
 * 转移函数
 * @author David
 *
 */
public class TransFunction {
	/**
	 * 开始状态
	 */
	private State m_BeginState;
	/**
	 * 结束状态
	 */
	private State m_EndState;
	/**
	 * 转移条件
	 */
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
	
	public void removeCondition(Object cond){
		if (cond instanceof String){
			for (int i = m_TransCondition.size()-1; i >= 0; i--){
				if (m_TransCondition.get(i).getM_InputSymbol().equals(cond)){
					m_TransCondition.remove(i);
				}
			}
		}
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
	
	/**
	 * 获取字符串形式的转换条件表达。
	 * @return 字符换表达
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
