package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

import automatondesigner.SystemConstant;

/**
 * 下推自动机类，继承自Automaton类
 * 
 * @author David
 * 
 */
public class PushdownAutomaton extends Automaton {

	/**
	 * 堆栈符号
	 */
	private ArrayList<String> m_StackSymbols;
	/**
	 * 堆栈初始符号
	 */
	private String m_InitStackSymbol;

	public PushdownAutomaton() {
		super();
		m_StackSymbols = new ArrayList<String>();
		m_InitStackSymbol = "";
		this.setM_Type(AutomatonConst.AUTOMATON_TYPE_PDA);
	}

	
	public PushdownAutomaton(ArrayList<State> states, ArrayList<String> inputs,
			ArrayList<TransFunction> funcs, State start,
			ArrayList<String> stackSymbols, String initStack, String name) {
		super(states, inputs, funcs, start, AutomatonConst.AUTOMATON_TYPE_PDA, name);
		this.setM_StackSymbols(stackSymbols);
		this.setM_InitStackSymbol(initStack);
	}

	/**
	 * 整理堆栈符号表
	 * @param needclear 整理之前是否需要清除原堆栈字母表
	 * @return 整理后的堆栈符号表的引用
	 */
	public ArrayList<String> clearUpStackSymbols(boolean needclear){
		if (needclear)
			m_StackSymbols.clear();
		for (TransFunction func : m_transFunctions){
			for (TransCondition cond : func.getM_TransCondition()){
				PDATransCondition pdacond = (PDATransCondition)cond;
				if (!m_StackSymbols.contains(pdacond.getM_OldStackSymbol()));{
					m_StackSymbols.add(pdacond.getM_OldStackSymbol());
				}
				for (String s : pdacond.getM_NewStackSymbol()){
					if (!m_StackSymbols.contains(s));{
						m_StackSymbols.add(s);
					}
				}
			}
		}
		return m_StackSymbols;
	}

	/**
	 * 获取堆栈符号表
	 * @return 堆栈符号表
	 */
	public ArrayList<String> getM_StackSymbols() {
		return m_StackSymbols;
	}

	/**
	 * 设置堆栈符号表
	 * @param mStackSymbols 堆栈符号表
	 */
	public void setM_StackSymbols(ArrayList<String> mStackSymbols) {
		m_StackSymbols = mStackSymbols;
	}

	/**
	 * 获取堆栈初始符号
	 * @return 堆栈初始符号
	 */
	public String getM_InitStackSymbol() {
		return m_InitStackSymbol;
	}

	/**
	 * 设置堆栈初始符号
	 * @param mInitStackSymbol 堆栈初始符号
	 */
	public void setM_InitStackSymbol(String mInitStackSymbol) {
		m_InitStackSymbol = mInitStackSymbol;
	}
}
