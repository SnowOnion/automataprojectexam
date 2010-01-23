package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public abstract class Automaton {
//------Used by construction procedure------------------------------------------
	/**
	 * ���״̬��
	 * @param label ״̬��
	 */
	public void addState(String label) {
		states.add(new State(label));
		label2num.put(label, size);
		++size;
	}
	
	/**
	 * ��ӻ����ó�ʼ״̬��
	 * @param label ״̬��
	 */
	public void setStartState(String label) {
		if (!label2num.containsKey(label))
			addState(label);
		startState = stateOfLabel(label);
		modified = true;
	}
	
	/**
	 * ��ӻ����ý���״̬��
	 * @param label ״̬��
	 */
	public void setFinalState(String label) {
		if (!label2num.containsKey(label))
			addState(label);
		finalStates.add(stateOfLabel(label));
		modified = true;
	}
	
	/**
	 * ���Ǩ�ơ�
	 * @param from Դ״̬����
	 * @param cond Ǩ������
	 * @param to Ŀ��״̬����
	 */
	public abstract void addTransition(State from, HashSet<String> cond, State to);
	
//------Utilities, internal-----------------------------------------------------
	/**
	 * ����״̬������Ӧ��״̬����
	 * @param label ״̬��
	 * @return ״̬����
	 */
	protected State stateOfLabel(String label) {
		return states.get(label2num.get(label));
	}
	/**
	 * ����ָ��״̬�����ơ�
	 * @param num ״̬���
	 * @return ״̬��
	 */
	protected State stateOfNum(int num) {
		return states.get(num);
	}
	/**
	 * ����ָ��״̬�ı�š�
	 * @param num ״̬����
	 * @return ״̬���
	 */
	protected int numOfState(State s) {
		return label2num.get(s.label());
	}
	
//------Used by algorithms, internal--------------------------------------------
	/**
	 * ɾ��״̬��
	 * @param stateNum ״̬���
	 */
	protected void removeState(int stateNum) {
		removeState(states.get(stateNum));
	}
	/**
	 * ɾ��״̬��
	 * @param stateNum ״̬����
	 */
	protected void removeState(State state) {
		Set<State> s;
		Iterator<State> it;
		State t;
		
		state.dispose();
		
		s = state.toStates();
		it = s.iterator();
		while (it.hasNext()) {
			t = it.next();
			t.cleanUpInTransitions();
		}
		s = state.fromStates();
		it = s.iterator();
		while (it.hasNext()) {
			t = it.next();
			t.cleanUpOutTransitions();
		}
		
		states.remove(state);
		--size;
		
		// reconstruct state2num map
		label2num.clear();
		for (int num = 0; num < size; ++num) {
			label2num.put(states.get(num).label(), num);
		}

		// in final states
		finalStates.remove(state);
		// in start state
		if (startState == state)
			startState = null;
		
		modified = true;
	}
	
	
//------Data member-------------------------------------------------------------
	/**
	 * �Զ�������״̬��
	 */
	protected int size = 0;
	/**
	 * ����״̬����
	 */
	protected ArrayList<State> states = new ArrayList<State>();
	/**
	 * ״̬��->���ӳ��
	 */
	protected HashMap<String, Integer> label2num = new HashMap<String, Integer>();
	/**
	 * ��ʼ״̬
	 */
	protected State startState;
	/**
	 * �ս�״̬����
	 */
	protected HashSet<State> finalStates = new HashSet<State>();
	/**
	 * ��ĸ��
	 */
	protected HashSet<String> alphabet = new HashSet<String>();
	/**
	 * �Զ����Ƿ����ϴλ�����޸�
	 */
	protected boolean modified = false;
}
