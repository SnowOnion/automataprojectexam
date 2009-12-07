package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

public class PDATransCondition extends TransCondition {

	private String m_OldStackSymbol;
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

	private ArrayList<String> m_NewStackSymbol;
	
	public PDATransCondition(String input, String oldStackTop, ArrayList<String> newStackTops) {
		super(input);
		setM_OldStackSymbol(oldStackTop);
		setM_NewStackSymbol(newStackTops);
	}

}
