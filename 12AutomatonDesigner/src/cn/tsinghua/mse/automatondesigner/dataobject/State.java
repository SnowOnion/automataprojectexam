package cn.tsinghua.mse.automatondesigner.dataobject;

/**
 * �Զ����е�״̬��Ĭ�����������֣���������������
 * ��AutomatonDesignerϵͳ��һ��״ֻ̬ά��һ��������������������ʽ��ã�
 * ��˿������������������ձ�����Զ�����ʱ����Ҫȥ��������
 * @author David
 *
 */

public class State {
	/**
	 * ״̬����
	 */
	private String m_Name = "";

	public State(String n) {
		this.m_Name = n;
	}

	public String getM_Name() {
		return m_Name;
	}

	public void setM_Name(String mName) {
		m_Name = mName;
	}
}
