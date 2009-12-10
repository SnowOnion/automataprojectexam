package dfa;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class DFA extends Automaton {
//------Implement of superclass interface---------------------------------------
	@Override
	public void addTransition(String from, char letter, String to) throws Exception {
		addTransition(state2num.get(from), letter, state2num.get(to));
	}
	public void addTransition(Integer from, char letter, Integer to) throws Exception {
		if (from != null && to != null && from < size && to < size) {
			Edges edges = transitions.get(from);
			if (edges.containsInput(letter))
				return;
			else
				edges.addEdge(letter, to);
			edges = reverseTransitions.get(to);
			edges.addEdge(letter, from);
			alphabet.add(letter);
		} else {
			throw new Exception("State not exists");
		}
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
		Iterator<String> sit = state2num.keySet().iterator();
		while (sit.hasNext()) {
			String state = sit.next();
			if (state2num.get(state) == startState)
				s += "->\t" + state + "\t";
			else
				s += "\t" + state + "\t";
			cit = alphabet.iterator();
			int num;
			while (cit.hasNext()) {
				num = nextStateOf(state2num.get(state), cit.next());
				if (num != -1)
					s += num2state.get(num);
				s += "\t";
			}
			s += "\n";
		}
		return s;
	}
	/*
	@Override
	public Object clone() {
		DFA t = new DFA();
		t.alphabet = (TreeSet<Character>) alphabet.clone();
		t.state2num = (HashMap<String, Integer>) state2num.clone();
		t.num2state = (ArrayList<String>) num2state.clone();
		t.transitions = (HashMap<Integer, Edges>) transitions.clone();
		t.reverseTransitions = (HashMap<Integer, Edges>) reverseTransitions.clone();
		t.size = size;
		t.startState = startState;
		t.finalStates = (HashSet<Integer>) finalStates.clone();
		return t;
	}
	*/
//------DFA algorithms----------------------------------------------------------
	public void minimize() {
		if (!modified)
			return;
		// Remove unreachable states
		for (int state = 0; state < size; ++state) {
			if (state != startState && reverseTransitions.get(state).isEmpty())
				removeStateCascade(state);
		}
		// Table used in merging
		table = new char[size][size];
		// Queue used for filling table
		queue = new LinkedList<Pair<Integer, Integer>>();
		// Manage final states - first part of table-filling algorithm, linear
		Iterator<Integer> finalsIter = finalStates.iterator();
		while (finalsIter.hasNext()) {
			int i = finalsIter.next();
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
		if (size > t.size)
			return false;
		
		markDeadStates();
		int[] map1 = new int[size];
		int[] map2 = new int[t.size];
		int count = 0;
		map1[startState] = map2[t.startState] = count++;
		
		queue.add(new Pair<Integer, Integer>(startState, t.startState));
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
				s2 = nextStateOf(p.value(), letter);
				if (map1[s1] != 0) {	// already visited
					if (map1[s1] != map2[s2])
						return false;
				} else {
					map1[s1] = map2[s2] = count++;
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
		Edges edges = reverseTransitions.get(state2);
		Iterator<Integer> stateIter = edges.adjacentStates().iterator();
		while (stateIter.hasNext())
			transitions.get(stateIter.next()).alterTarget(state2, state1);
		
		removeStateCascade(state2);
	}

	// Second part of table-filling algorithm, O(n^2)
	private void fillTableBFS() {
		while (!queue.isEmpty()) {
			Pair<Integer, Integer> statePair = queue.remove();
			Edges edges1 = reverseTransitions.get(statePair.key());
			Edges edges2 = reverseTransitions.get(statePair.value());
			Iterator<Character> letterIter = edges1.lettersAccepted().iterator();
			while (letterIter.hasNext()) {
				Character letter = letterIter.next();
				Iterator<Integer> stateIter1 = edges1.edgesWithLetter(letter).listIterator();
				if (edges2.edgesWithLetter(letter) == null)
					continue;
				Iterator<Integer> stateIter2 = edges2.edgesWithLetter(letter).listIterator();
				while (stateIter1.hasNext()) {	// reverse paths - may be multiple
					int i = stateIter1.next();
					while (stateIter2.hasNext()) {
						int j = stateIter2.next();
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
	private void markDeadStates(int state) {
		int mark;
		map[state] = -1;	// 0 white; -1 grey/dead; -2 live
		Edges e = transitions.get(state);
		Iterator<Character> letterIter = e.lettersAccepted().iterator();
		while (letterIter.hasNext()) {
			// ASSERTION: each letter has one and only one corresponding target
			int nextState = nextStateOf(state, letterIter.next());
			if (map[nextState] == 0)
				markDeadStates(nextState);
			mark = map[nextState];
			if (mark < map[state])
				map[state] = mark;
		}
		if (finalStates.contains(state))
			map[state] = -2;
	}
	
	private int nextStateOf(int state, char letter) {
		Edges e = transitions.get(state);
		LinkedList<Integer> l = e.edgesWithLetter(letter);
		if (l.isEmpty())
			return -1;
		else
			return l.getFirst();
	}
	
	private Set<Character> lettersAcceptedByState(int state) {
		return transitions.get(state).lettersAccepted();
	}
	
//------Data member-------------------------------------------------------------
	private char[][] table = null;
	private LinkedList<Pair<Integer, Integer>> queue = null;
	private int[] map = null;
}
