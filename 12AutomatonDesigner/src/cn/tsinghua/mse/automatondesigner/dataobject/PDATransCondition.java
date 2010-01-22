package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

/**
 * 下推自动机转移的转移条件
 * @author David
 *
 */
public class PDATransCondition extends TransCondition {

	/**
	 * 转移前的栈顶符号。
	 */
	private String m_OldStackSymbol;
	/**
	 * 转移后的栈顶符号，依照从栈顶开始的顺序排列。
	 */
	private ArrayList<String> m_NewStackSymbol;

	/**
	 * 构造函数
	 * @param input
	 * @param oldStackTop
	 * @param newStackTops
	 */
	public PDATransCondition(String input, String oldStackTop, ArrayList<String> newStackTops) {
		super(input);
		setM_OldStackSymbol(oldStackTop);
		setM_NewStackSymbol(newStackTops);
	}

	/**
	 * 获取转移前的栈顶符号。
	 * @return 转移前的栈顶符号
	 */
	public String getM_OldStackSymbol() {
		return m_OldStackSymbol;
	}

	/**
	 * 设置转移前的栈顶符号
	 * @param mOldStackSymbol 转移前的栈顶符号
	 */
	public void setM_OldStackSymbol(String mOldStackSymbol) {
		m_OldStackSymbol = mOldStackSymbol;
	}

	/**
	 * 获取新栈顶符号集合
	 * @return 新栈顶符号集合
	 */
	public ArrayList<String> getM_NewStackSymbol() {
		return m_NewStackSymbol;
	}

	/**
	 * 设置新栈顶符号集合
	 * @param mNewStackSymbol 新栈顶符号集合
	 */
	public void setM_NewStackSymbol(ArrayList<String> mNewStackSymbol) {
		m_NewStackSymbol = mNewStackSymbol;
	}
	
	@Override
	public String toString(){
		String result = m_InputSymbol + ", " + m_OldStackSymbol + "/";
		int i = 0;
		for (String s : m_NewStackSymbol){
			if (0 != i){
				result += ",";
			}
			result += s;
			i++;
		}
		return result;
	}
}
