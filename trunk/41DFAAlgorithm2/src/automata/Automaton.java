package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public abstract class Automaton {
//------Used by construction procedure------------------------------------------
	/**
	 * 添加状态。
	 * @param label 状态名
	 */
	public void addState(String label) {
		states.add(new State(label));
		label2num.put(label, size);
		++size;
	}
	
	/**
	 * 添加或设置初始状态。
	 * @param label 状态名
	 */
	public void setStartState(String label) {
		if (!label2num.containsKey(label))
			addState(label);
		startState = stateOfLabel(label);
		modified = true;
	}
	
	/**
	 * 添加或设置结束状态。
	 * @param label 状态名
	 */
	public void setFinalState(String label) {
		if (!label2num.containsKey(label))
			addState(label);
		finalStates.add(stateOfLabel(label));
		modified = true;
	}
	
	/**
	 * 添加迁移。
	 * @param from 源状态对象
	 * @param cond 迁移条件
	 * @param to 目的状态对象
	 */
	public abstract void addTransition(State from, HashSet<String> cond, State to);
	
//------Utilities, internal-----------------------------------------------------
	/**
	 * 返回状态名所对应的状态对象。
	 * @param label 状态名
	 * @return 状态对象
	 */
	protected State stateOfLabel(String label) {
		return states.get(label2num.get(label));
	}
	/**
	 * 返回指定状态的名称。
	 * @param num 状态标号
	 * @return 状态名
	 */
	protected State stateOfNum(int num) {
		return states.get(num);
	}
	/**
	 * 返回指定状态的标号。
	 * @param num 状态名称
	 * @return 状态标号
	 */
	protected int numOfState(State s) {
		return label2num.get(s.label());
	}
	
//------Used by algorithms, internal--------------------------------------------
	/**
	 * 删除状态。
	 * @param stateNum 状态标号
	 */
	protected void removeState(int stateNum) {
		removeState(states.get(stateNum));
	}
	/**
	 * 删除状态。
	 * @param stateNum 状态对象
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
	 * 自动机所含状态数
	 */
	protected int size = 0;
	/**
	 * 所有状态集合
	 */
	protected ArrayList<State> states = new ArrayList<State>();
	/**
	 * 状态名->标号映射
	 */
	protected HashMap<String, Integer> label2num = new HashMap<String, Integer>();
	/**
	 * 初始状态
	 */
	protected State startState;
	/**
	 * 终结状态集合
	 */
	protected HashSet<State> finalStates = new HashSet<State>();
	/**
	 * 字母表
	 */
	protected HashSet<String> alphabet = new HashSet<String>();
	/**
	 * 自动机是否在上次化简后被修改
	 */
	protected boolean modified = false;
}
