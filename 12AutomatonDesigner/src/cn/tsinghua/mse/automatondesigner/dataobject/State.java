package cn.tsinghua.mse.automatondesigner.dataobject;

/**
 * The state of the automaton.
 * @author David
 *
 */
public class State {
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
