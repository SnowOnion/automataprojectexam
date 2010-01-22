package cn.tsinghua.mse.automatondesigner.dataobject;

/**
 * 转换函数的转换条件
 * 对于NFA和DFA即为输入字母，PDA的转换函数继承自本类。
 * @author David
 *
 */
public class TransCondition {
	protected String m_InputSymbol;
	
	public TransCondition(String input){
		setM_InputSymbol(input);
	}

	/**
	 * 获取输入符号
	 * @return 输入符号
	 */
	public String getM_InputSymbol() {
		return m_InputSymbol;
	}

	/**
	 * 设置输入符号
	 * @param mInputSymbol 输入符号
	 */
	public void setM_InputSymbol(String mInputSymbol) {
		m_InputSymbol = mInputSymbol;
	}
	
	@Override
	public String toString(){
		return m_InputSymbol;
	}
}
