package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

public class PushdownAutomaton extends Automaton {

	private ArrayList<String> m_StackSymbols;
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
