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

	/**
	 * ��ȡ״̬����
	 * @return ״̬����
	 */
	public String getM_Name() {
		return m_Name;
	}

	/**
	 * ����״̬����
	 * @param mName ״̬����
	 */
	public void setM_Name(String mName) {
		m_Name = mName;
	}
	
	/**
	 * ��ȡ״̬����
	 * @return ״̬����
	 */
	public byte getM_type() {
		return m_type;
	}

	/**
	 * ����״̬����
	 * @param mType ״̬����
	 */
	public void setM_type(byte mType) {
		m_type = mType;
	}
	
	@Override
	public String toString() {
		return m_Name;
	}
	
//	public String getStrType(){
//		return AutomatonConst.STATE_TYPES[getM_type()];
//	}
//	
//	public void setTypeByStr(String stateType){
//		for (byte i = 0; i < AutomatonConst.STATE_TYPES.length; i++){
//			if (stateType.equals(AutomatonConst.STATE_TYPES[i])){
//				this.setM_type(i);
//			}
//		}
//		this.setM_type(AutomatonConst.STATE_COMMON_TYPE);
//	}
}
