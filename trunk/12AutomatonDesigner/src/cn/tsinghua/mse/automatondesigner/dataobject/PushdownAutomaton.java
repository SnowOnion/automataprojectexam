package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

/**
 * 下推自动机类，继承自Automaton类
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
	}

	public PushdownAutomaton(ArrayList<State> states, ArrayList<String> inputs,
			ArrayList<TransFunction> funcs, State start,
			ArrayList<State> finals, String type,
			ArrayList<String> stackSymbols, String initStack) {
		super(states, inputs, funcs, start, finals, AutomatonConst.AUTOMATON_TYPE_PDA);
		this.setM_StackSymbols(stackSymbols);
		this.setM_InitStackSymbol(initStack);
	}

	public ArrayList<String> getM_StackSymbols() {
		return m_StackSymbols;
	}

	public void setM_StackSymbols(ArrayList<String> mStackSymbols) {
		m_StackSymbols = mStackSymbols;
	}

	public String getM_InitStackSymbol() {
		return m_InitStackSymbol;
	}

	public void setM_InitStackSymbol(String mInitStackSymbol) {
		m_InitStackSymbol = mInitStackSymbol;
	}
}
