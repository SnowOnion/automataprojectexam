package cn.tsinghua.mse.automatondesigner.dataobject;

/**
 * 自动机中的状态。默认以名称区分，但是允许重名。
 * 在AutomatonDesigner系统中一个状态只维护一个对象，其他都以引用形式获得，
 * 因此可以允许重名，但最终保存成自动机的时候需要去除重名。
 * @author David
 *
 */

public class State {
	/**
	 * 状态名称
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
