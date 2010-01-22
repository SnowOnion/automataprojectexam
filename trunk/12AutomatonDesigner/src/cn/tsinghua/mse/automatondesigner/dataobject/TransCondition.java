package cn.tsinghua.mse.automatondesigner.dataobject;

/**
 * ת��������ת������
 * ����NFA��DFA��Ϊ������ĸ��PDA��ת�������̳��Ա��ࡣ
 * @author David
 *
 */
public class TransCondition {
	protected String m_InputSymbol;
	
	public TransCondition(String input){
		setM_InputSymbol(input);
	}

	/**
	 * ��ȡ�������
	 * @return �������
	 */
	public String getM_InputSymbol() {
		return m_InputSymbol;
	}

	/**
	 * �����������
	 * @param mInputSymbol �������
	 */
	public void setM_InputSymbol(String mInputSymbol) {
		m_InputSymbol = mInputSymbol;
	}
	
	@Override
	public String toString(){
		return m_InputSymbol;
	}
}
