package cn.tsinghua.mse.automatondesigner.dataobject;

import java.util.ArrayList;

import automatondesigner.SystemConstant;

/**
 * �����Զ����࣬�̳���Automaton��
 * 
 * @author David
 * 
 */
public class PushdownAutomaton extends Automaton {

	/**
	 * ��ջ����
	 */
	private ArrayList<String> m_StackSymbols;
	/**
	 * ��ջ��ʼ����
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
	 * �����ջ���ű�
	 * @param needclear ����֮ǰ�Ƿ���Ҫ���ԭ��ջ��ĸ��
	 * @return �����Ķ�ջ���ű������
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
	 * ��ȡ��ջ���ű�
	 * @return ��ջ���ű�
	 */
	public ArrayList<String> getM_StackSymbols() {
		return m_StackSymbols;
	}

	/**
	 * ���ö�ջ���ű�
	 * @param mStackSymbols ��ջ���ű�
	 */
	public void setM_StackSymbols(ArrayList<String> mStackSymbols) {
		m_StackSymbols = mStackSymbols;
	}

	/**
	 * ��ȡ��ջ��ʼ����
	 * @return ��ջ��ʼ����
	 */
	public String getM_InitStackSymbol() {
		return m_InitStackSymbol;
	}

	/**
	 * ���ö�ջ��ʼ����
	 * @param mInitStackSymbol ��ջ��ʼ����
	 */
	public void setM_InitStackSymbol(String mInitStackSymbol) {
		m_InitStackSymbol = mInitStackSymbol;
	}
}
