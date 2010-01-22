package automata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import util.Pair;

public class DFA extends Automaton {
//------Implement of superclass interface---------------------------------------
	
	public void addTransition(String from, HashSet<Character> cond, String to) throws Exception {
		if (label2num.get(from) == null || label2num.get(to) == null)
			throw new Exception("State not exist");
		addTransition(stateOfLabel(from), cond, stateOfLabel(to));
	}
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
					map1[s1] = s2;
					queue.add(new Pair<Integer, Integer>(s1, s2));
				}
			}
		}
		return true;
	}
	
	public boolean equivalentTo(DFA t) {
		return includedIn(t) && t.includedIn(this);
	}
	
//------Utilities used by algorithms--------------------------------------------
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
	
	private void merge(int state1, int state2) {
		Iterator<State> sit = stateOfNum(state2).fromStates().iterator();
		while (sit.hasNext())
			sit.next().outTransitions().alterToState(stateOfNum(state2), stateOfNum(state1));
		
		removeState(stateOfNum(state2));
	}

	// Second part of table-filling algorithm, O(n^2)
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

	private void markDeadStates() {
		map = new int[size];
		markDeadStates(startState);
	}
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
	
	private State nextStateOf(State state, char letter) {
		OutTransitions e = state.outTransitions();
		HashSet<State> s = e.toStatesByCond(letter);
		if (s.isEmpty())
			return null;
		else
			return s.iterator().next();
	}
	private int nextStateOf(int state, char letter) {
		OutTransitions e = stateOfNum(state).outTransitions();
		HashSet<State> s = e.toStatesByCond(letter);
		if (s.isEmpty())
			return -1;
		else
			return numOfState(s.iterator().next());
	}
	
	private Set<Character> lettersAcceptedByState(int stateNum) {
		return stateOfNum(stateNum).outTransitions().conditions();
	}
	
//------Data member-------------------------------------------------------------
	private char[][] table = null;
	private LinkedList<Pair<Integer, Integer>> queue = null;
	private int[] map = null;
}

