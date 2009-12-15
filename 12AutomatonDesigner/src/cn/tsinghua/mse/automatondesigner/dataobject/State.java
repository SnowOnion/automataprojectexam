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
	private byte m_type = AutomatonConst.STATE_COMMON_TYPE;

	public State(){
		m_Name = "";
		m_type = AutomatonConst.STATE_COMMON_TYPE;
	}
	
	public State(String n, byte type) {
		this.m_Name = n;
		this.m_type = type;
	}

	public String getM_Name() {
		return m_Name;
	}

	public void setM_Name(String mName) {
		m_Name = mName;
	}
	
	public byte getM_type() {
		return m_type;
	}

	public void setM_type(byte mType) {
		m_type = mType;
	}
}
