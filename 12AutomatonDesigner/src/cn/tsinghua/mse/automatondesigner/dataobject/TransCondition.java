package cn.tsinghua.mse.automatondesigner.dataobject;

public class TransCondition {
	protected String m_InputSymbol;
	
	public TransCondition(String input){
		setM_InputSymbol(input);
	}

	public String getM_InputSymbol() {
		return m_InputSymbol;
	}

	public void setM_InputSymbol(String mInputSymbol) {
		m_InputSymbol = mInputSymbol;
	}

}
