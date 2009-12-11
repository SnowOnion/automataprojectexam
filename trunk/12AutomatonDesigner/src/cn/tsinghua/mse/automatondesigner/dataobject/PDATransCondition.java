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

	public String getM_OldStackSymbol() {
		return m_OldStackSymbol;
	}

	public void setM_OldStackSymbol(String mOldStackSymbol) {
		m_OldStackSymbol = mOldStackSymbol;
	}

	public ArrayList<String> getM_NewStackSymbol() {
		return m_NewStackSymbol;
	}

	public void setM_NewStackSymbol(ArrayList<String> mNewStackSymbol) {
		m_NewStackSymbol = mNewStackSymbol;
	}
	
	@Override
	public String toString(){
		String result = m_InputSymbol + ", " + m_OldStackSymbol + "/";
		for (String s : m_NewStackSymbol){
			result += s;
		}
		return result;
	}
	
	public static void main(String[] args) {
		ArrayList a = new ArrayList<String>();
		a.add("Z0");
		a.add("A");
		PDATransCondition b = new PDATransCondition("a", "Z0", a);
		System.out.println(b.toString());
	}
}
