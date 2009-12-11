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
