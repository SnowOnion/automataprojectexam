package automata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import util.Pair;

/**
 * DFA类
 * @see automata.Automaton
 * @author Leaf
 *
 */
public class DFA extends Automaton {
//------Implement of superclass interface---------------------------------------
	
	/**
	 * 为DFA添加迁移。
	 * @param from 源状态名
	 * @param cond 迁移条件
	 * @param to 目的状态名
	 * @throws Exception 状态不存在
	 */
	public void addTransition(String from, HashSet<Character> cond, String to) throws Exception {
		if (label2num.get(from) == null || label2num.get(to) == null)
			throw new Exception("State not exist");
		addTransition(stateOfLabel(from), cond, stateOfLabel(to));
	}
	/**
	 * 为DFA添加迁移。
	 * @param from 源状态对象
	 * @param cond 迁移条件
	 * @param to 目的状态对象
	 */
	@Override
	public void addTransition(State from, HashSet<Character> cond, State to) {
		Iterator<Character> cit = cond.iterator();
		while (cit.hasNext()) {
			if (from.acceptsCond(cit.next()))
				return;
		}
		// update the alphabet
		cit = cond.iterator();
		while (cit.hasNext()) {
			alphabet.add(cit.next());
		}
		// connect 'from' and 'to'
		Transition trans = new Transition(from, to, cond);
		from.addOutTransition(trans);
		to.addInTransition(trans);
		
		modified = true;
	}
	
	/** 
	 * 生成字符形式的 DFA 的迁移表格
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = new String();
		s += "\t\t";
		Iterator<Character> cit = alphabet.iterator();
		while (cit.hasNext())
			s += cit.next() + "\t";
		s += "\n";
		Iterator<String> sit = label2num.keySet().iterator();
		while (sit.hasNext()) {
			String label = sit.next();
			if (stateOfLabel(label) == startState)
				s += "->\t" + label + "\t";
			else
				s += "\t" + label + "\t";
			cit = alphabet.iterator();
			State state;
			while (cit.hasNext()) {
				state = nextStateOf(stateOfLabel(label), cit.next());
				if (state != null)
					s += state.label();
				s += "\t";
			}
			s += "\n";
		}
		return s;
	}

//------DFA algorithms----------------------------------------------------------
	/**
	 * 化简DFA。使用填表算法。
	 */
	public void minimize() {
		if (!modified)
			return;
		// Remove some of unreachable states
		Iterator<State> sit = states.iterator();
		while (sit.hasNext()) {
			State s = sit.next();
			if (s != startState && s.fromStates().isEmpty())
				removeState(s);
		}
		// Table used in merging
		table = new char[size][size];
		// Queue used for filling table
		queue = new LinkedList<Pair<Integer, Integer>>();
		// Deal with final states - first part of table-filling algorithm, linear
		Iterator<State> finalsIter = finalStates.iterator();
		while (finalsIter.hasNext()) {
			int i = numOfState(finalsIter.next());
			for (int j = 0; j < size; ++j) {
				if (!finalStates.contains(j)) {
					if (i < j)
						table[i][j] = 'X';
					else
						table[j][i] = 'X';
					queue.add(new Pair<Integer, Integer>(i, j));
				}
			}
		}
		// Fill the table
		fillTableBFS();
		// Merge equivalent states
		for (int i = 0; i < size-1; ++i) {
			mergeAll(i);
		}
		modified = false;
	}
	
	/**
	 * 判断本DFA所表达的语言是否被另一个DFA所包含。<br>
	 * 主要思想：判断是否所有能够使本DFA接受的串都能被另一个DFA所接受。<br>
	 * 使用BFS算法。
	 * @param t 另一个DFA
	 * @return true：本DFA的语言包含于t； false：本DFA的语言不包含于t。
	 */
	public boolean includedIn(DFA t) {
		minimize();
		t.minimize();
		
		markDeadStates();
		int[] map1 = new int[size];
		for (int i = 0; i < size; ++i)
			map1[i] = -1;
		map1[numOfState(startState)] = t.numOfState(t.startState);
		queue.add(new Pair<Integer, Integer>(numOfState(startState), t.numOfState(t.startState)));
		while (!queue.isEmpty()) {
			Pair<Integer, Integer> p = queue.remove();
			Iterator<Character> letterIter = lettersAcceptedByState(p.key()).iterator();
			int s1, s2;
			char letter;
			while (letterIter.hasNext()) {
				letter = letterIter.next();
				s1 = nextStateOf(p.key(), letter);
				if (map[s1] == -1)	// dead state
					continue;
				s2 = t.nextStateOf(p.value(), letter);
				if (map1[s1] != -1) {	// already visited
					if (map1[s1] != s2)
						return false;
				} else {
					if (finalStates.contains(s1) && !t.finalStates.contains(s2))
						return false;
					map1[s1] = s2;
					queue.add(new Pair<Integer, Integer>(s1, s2));
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断本DFA是否与另一DFA等价。
	 * @param t 另一个DFA
	 * @return true：两个DFA等价；false：两个DFA不等价。
	 */
	public boolean equivalentTo(DFA t) {
		return includedIn(t) && t.includedIn(this);
	}
	
//------Utilities used by algorithms--------------------------------------------
	/**
	 * 合并所有与状态 i 等价的状态。
	 * @param i 状态标号
	 */
	private void mergeAll(int i) {
		if (i == size - 1)
			return;
		// Merge all states equivalent to i recursively
		for (int j = i+1; j < size; ++j) {
			if (table[i][j] != 'X') {
				mergeAll(j);
				merge(i, j);
				table[i][j] = 0;
			}
		}
	}
	
	/**
	 * 将两个状态 state1 state2 合并为 state1。
	 * @param state1
	 * @param state2
	 * 
	 */
	private void merge(int state1, int state2) {
		Iterator<State> sit = stateOfNum(state2).fromStates().iterator();
		while (sit.hasNext())
			sit.next().outTransitions().alterToState(stateOfNum(state2), stateOfNum(state1));
		
		removeState(stateOfNum(state2));
	}

	// Second part of table-filling algorithm, O(n^2)
	/**
	 * 为等价类表填入值。
	 * 这是填表算法的第二部分。（第一部分在minimize()函数中）
	 */
	private void fillTableBFS() {
		while (!queue.isEmpty()) {
			Pair<Integer, Integer> statePair = queue.remove();
			InTransitions trans1 = stateOfNum(statePair.key()).inTransitions();
			InTransitions trans2 = stateOfNum(statePair.value()).inTransitions();
			Iterator<Character> letterIter = trans1.conditions().iterator();
			while (letterIter.hasNext()) {
				Character letter = letterIter.next();
				Iterator<State> stateIter1 = trans1.fromStatesByCond(letter).iterator();
				if (trans2.fromStatesByCond(letter).isEmpty())
					continue;
				Iterator<State> stateIter2 = trans2.fromStatesByCond(letter).iterator();
				while (stateIter1.hasNext()) {	// reverse paths - may be multiple
					int i = numOfState(stateIter1.next());
					stateIter2 = trans2.fromStatesByCond(letter).iterator();
					while (stateIter2.hasNext()) {
						int j = numOfState(stateIter2.next());
						if (i > j) {
							int temp = i;
							i = j;
							j = temp;
						}
						if (table[i][j] != 'X') {
							table[i][j] = 'X';
							queue.add(new Pair<Integer, Integer>(i, j));
						}
					}
				}
			}
		}
	}

	/**
	 * 标记无效状态。<br>
	 * 调用此函数的递归版本。
	 */
	private void markDeadStates() {
		map = new int[size];
		markDeadStates(startState);
	}
	/**
	 * 递归标记从 state 能到达的所有无效状态。使用DFS算法。<br>
	 * 被此函数的非递归版本所调用，不单独使用。
	 * @param state
	 */
	private void markDeadStates(State state) {
		int mark;
		int currNum = numOfState(state);
		map[currNum] = -1;	// 0 white; -1 grey/dead; -2 live
		Iterator<State> sit = state.toStates().iterator();
		while (sit.hasNext()) {
			// ASSERTION: each letter has one and only one corresponding target
			State nextState = sit.next();
			int nextNum = numOfState(nextState);
			if (map[nextNum] == 0)
				markDeadStates(nextState);
			mark = map[nextNum];
			if (mark < map[currNum])
				map[currNum] = mark;
		}
		if (finalStates.contains(state))
			map[currNum] = -2;
	}
	
	/**
	 * 返回从状态 state 通过迁移条件 letter 能够到达的下一状态。
	 * @param state 当前状态名
	 * @param letter 迁移条件
	 * @return 下一状态
	 */
	private State nextStateOf(State state, char letter) {
		OutTransitions e = state.outTransitions();
		HashSet<State> s = e.toStatesByCond(letter);
		if (s.isEmpty())
			return null;
		else
			return s.iterator().next();
	}
	/**
	 * 返回从状态 state 通过迁移条件 letter 能够到达的下一状态。
	 * @param state 当前状态标号
	 * @param letter 迁移条件
	 * @return 下一状态
	 */
	private int nextStateOf(int state, char letter) {
		OutTransitions e = stateOfNum(state).outTransitions();
		HashSet<State> s = e.toStatesByCond(letter);
		if (s.isEmpty())
			return -1;
		else
			return numOfState(s.iterator().next());
	}
	
	/**
	 * 返回所有被某状态接受的迁移条件（字符）。
	 * @param stateNum 状态标号
	 * @return 迁移条件集合
	 */
	private Set<Character> lettersAcceptedByState(int stateNum) {
		return stateOfNum(stateNum).outTransitions().conditions();
	}
	
//------Data member-------------------------------------------------------------
	/**
	 * DFA化简表
	 */
	private char[][] table = null;
	/**
	 * 语言包含判断算法所用队列
	 */
	private LinkedList<Pair<Integer, Integer>> queue = null;
	/**
	 * 无效状态标记数组
	 */
	private int[] map = null;
}

