package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

/**
 * �����Զ���ת�Ƶ�ת������
 * @author David
 *
 */
public class PDATransCondition extends TransCondition {

	/**
	 * ת��ǰ��ջ�����š�
	 */
	private String m_OldStackSymbol;
	/**
	 * ת�ƺ��ջ�����ţ����մ�ջ����ʼ��˳�����С�
	 */
	private ArrayList<String> m_NewStackSymbol;

	/**
	 * ���캯��
	 * @param input
	 * @param oldStackTop
	 * @param newStackTops
	 */
	public PDATransCondition(String input, String oldStackTop, ArrayList<String> newStackTops) {
		super(input);
		setM_OldStackSymbol(oldStackTop);
		setM_NewStackSymbol(newStackTops);
	}

	/**
	 * ��ȡת��ǰ��ջ�����š�
	 * @return ת��ǰ��ջ������
	 */
	public String getM_OldStackSymbol() {
		return m_OldStackSymbol;
	}

	/**
	 * ����ת��ǰ��ջ������
	 * @param mOldStackSymbol ת��ǰ��ջ������
	 */
	public void setM_OldStackSymbol(String mOldStackSymbol) {
		m_OldStackSymbol = mOldStackSymbol;
	}

	/**
	 * ��ȡ��ջ�����ż���
	 * @return ��ջ�����ż���
	 */
	public ArrayList<String> getM_NewStackSymbol() {
		return m_NewStackSymbol;
	}

	/**
	 * ������ջ�����ż���
	 * @param mNewStackSymbol ��ջ�����ż���
	 */
	public void setM_NewStackSymbol(ArrayList<String> mNewStackSymbol) {
		m_NewStackSymbol = mNewStackSymbol;
	}
	
	@Override
	public String toString(){
		String result = m_InputSymbol + ", " + m_OldStackSymbol + "/";
		int i = 0;
		for (String s : m_NewStackSymbol){
			if (0 != i){
				result += ",";
			}
			result += s;
			i++;
		}
		return result;
	}
}
