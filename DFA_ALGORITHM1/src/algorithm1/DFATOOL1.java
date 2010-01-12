/**
 * @Title: DFATOOL1.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2010-1-2 ����07:19:08
 * @Version: V1.0
 */
package algorithm1;

import automaton.*;
public class DFATOOL1 {
	/**
	 * �ж��Ƿ�DFA����NFA
	 * @param auto
	 * @return trueΪDFA falseΪNFA
	 */
    public static boolean DFAORNFA(Automaton auto)
    {
    	MyAutomaton automaton=new MyAutomaton(auto);
    	return automaton.ISDFA();
    }
    /**
     * ������DFA�Ľ���
     * @param op1
     * @param op2
     * @return
     */
    public static Automaton INTERSECTION(Automaton op1,Automaton op2)
    {
    	MyAutomaton automaton1=new MyAutomaton(op1);
    	MyAutomaton automaton2=new MyAutomaton(op2);
    	MyAutomaton result=automaton1.Intersection(automaton2);
    	return result.ConvertToAutomaton();
    }
    /**
     * ������DFA�Ĳ���
     * @param op1
     * @param op2
     * @return
     */
    public static Automaton UNION(Automaton op1,Automaton op2)
    {
    	MyAutomaton automaton1=new MyAutomaton(op1);
    	MyAutomaton automaton2=new MyAutomaton(op2);
    	MyAutomaton result=automaton1.Union(automaton2);
    	return result.ConvertToAutomaton();
    }
    /**
     * ����DFA�Ĳ���
     * @param auto
     * @return
     */
    public static Automaton Complementation(Automaton auto)
    {
    	MyAutomaton automaton=new MyAutomaton(auto);
    	return automaton.Complementation().ConvertToAutomaton();
    }
    /**
     * ��С��DFA
     * @param auto
     * @return
     */
    public static Automaton MINIMAL(Automaton auto)
    {
    	MyAutomaton automaton=new MyAutomaton(auto);
    	return automaton.MinimalDFA().ConvertToAutomaton();
    }
    /**
     * DFA�������ɽ���״̬
     * @param auto
     * @return
     */
    public  static Automaton DELETEUNACC(Automaton auto)
    {
    	MyAutomaton automaton=new MyAutomaton(auto);
    	return automaton.DeleteUnaccept().ConvertToAutomaton();
    }
    /**
     * ΪDFA�������ɽ���״̬
     * @param auto
     * @return
     */
    public static Automaton DELETEUNREACH(Automaton auto)
    {
    	MyAutomaton automaton=new MyAutomaton(auto);
    	return automaton.DeleteUnreach().ConvertToAutomaton();
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
