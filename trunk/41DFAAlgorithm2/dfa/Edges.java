package dfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


public class Edges {
//------Interface---------------------------------------------------------------
	public boolean containsTransition(char letter, String to) {
		if (!edges.containsKey(letter))
			return false;
		if (edges.get(letter).contains(to))
			return true;
		return false;
	}
	
	public boolean containsInput(char letter) {
		return edges.containsKey(letter);
	}
	
	public void addEdge(char letter, int to) {
		if (edges.containsKey(letter))
			edges.get(letter).add(to);
		else {
			LinkedList<Integer> targets = new LinkedList<Integer>();
			targets.add(to);
			edges.put(letter, targets);
		}
	}
	
	public void removeEdge(char letter, Integer to) {
		if (edges.containsKey(letter))
			edges.get(letter).remove(to);
		if (edges.get(letter).isEmpty())
			edges.remove(letter);
	}
	
	public void removeEdge(Integer to) {
		Iterator<LinkedList<Integer>> it = edges.values().iterator();
		LinkedList<Integer> l;
		while (it.hasNext()) {
			l = it.next();
			l.remove(to);
			if (l.isEmpty())
				it.remove();
		}
	}
	
	public void alterTarget(int src, int dest) {
		Iterator<LinkedList<Integer>> it = edges.values().iterator();
		LinkedList<Integer> l;
		
		while (it.hasNext()) {
			l = it.next();
			for (int i = 0; i < l.size(); ++i) {
				if (l.get(i) == src)
					l.set(i, dest);
			}
		}
	}
	
	public LinkedList<Integer> edgesWithLetter(char letter) {
		return edges.get(letter);
	}
	
	public Set<Character> lettersAccepted() {
		return edges.keySet();
	}
	
	public boolean isEmpty() {
		return edges.isEmpty();
	}
	
	public HashSet<Integer> adjacentStates() {
		HashSet<Integer> s = new HashSet<Integer>();
		Iterator<Character> letterIter = edges.keySet().iterator();
		while (letterIter.hasNext()) {
			Iterator<Integer> stateIter = edges.get(letterIter.next()).iterator();
			while (stateIter.hasNext())
				s.add(stateIter.next());
		}
		return s;
	}
	
	public void stateRemoved(int state) {
		Iterator<LinkedList<Integer>> it = edges.values().iterator();
		LinkedList<Integer> l;
		
		while (it.hasNext()) {
			l = it.next();
			for (int i = 0; i < l.size(); ++i) {
				int r = l.get(i);
				if (r > state)
					l.set(i, r-1);
			}
		}
	}
	
//------Data member-------------------------------------------------------------
	private HashMap<Character, LinkedList<Integer>> edges = new HashMap<Character, LinkedList<Integer>>();
}
