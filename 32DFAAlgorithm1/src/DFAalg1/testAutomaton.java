package DFAalg1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;


import Exception.NotDFAException;
import Exception.NotThesameCharsetException;
import automaton.*;

public class testAutomaton {
	public static void main(String args[]) throws NotDFAException, NotThesameCharsetException{
//		Automaton auto = new AutomatonDFA();
//
//		State a = new State("A",AutomatonConstant.STATETYPE_ACCEPTED);
//		State b = new State("B",AutomatonConstant.STATETYPE_NORMAL);
//		State c = new State("C",AutomatonConstant.STATETYPE_ACCEPTED);
//		State d = new State("D",AutomatonConstant.STATETYPE_ACCEPTED);
//		State e = new State("E",AutomatonConstant.STATETYPE_NORMAL);
//		
//		String automatonName;
//		String automatonType;
//		ArrayList<String> inputSymbolSet = new ArrayList<String>();
//		State initialState;
//		HashMap<String,State> states = new HashMap<String,State>();
//		ArrayList<Transition> transitions = new ArrayList<Transition>();
//		
//		states.put("A", a);
//		states.put("B", b);
//		states.put("C", c);
//		states.put("D", d);
//		states.put("E", e);
//		
//		inputSymbolSet.add("0");
//		inputSymbolSet.add("1");
//		
//		ArrayList<String> condition0 = new ArrayList<String>();
//		ArrayList<String> condition1 = new ArrayList<String>();
//		ArrayList<String> condition01 = new ArrayList<String>();
//		condition0.add("0");
//		condition1.add("1");
//		condition01.add("0");
//		condition01.add("1");
//		transitions.add(new TransitionDFA(a,condition0,a));
//		transitions.add(new TransitionDFA(a,condition1,b));
//		transitions.add(new TransitionDFA(b,condition0,a));
//		transitions.add(new TransitionDFA(b,condition1,b));
//		
//		
//		transitions.add(new TransitionDFA(c,condition0,d));
//		
//		transitions.add(new TransitionDFA(c,condition1,e));
//		
//		transitions.add(new TransitionDFA(d,condition0,d));
//		transitions.add(new TransitionDFA(d,condition1,e));
//		transitions.add(new TransitionDFA(e,condition0,c));
//		transitions.add(new TransitionDFA(e,condition1,e));
//		
//		auto.setAutomatonName("test");
//		auto.setAutomatonType("DFA");
//		auto.setInitialState(c);
//		auto.setInputSymbolSet(inputSymbolSet);
//		auto.setStates(states);
//		auto.setTransitions(transitions);
		
		Automaton auto= constructAutomaton();
		Automaton s1 = constructAutomatonS1();
		Automaton s2 = constructAutomatonS2();
		DFAOpInterface testAlg= new DFAAlgorithm1();
		System.out.println(testAlg.getAutomatonType(auto));
		testAlg.minOP(auto);
		Automaton a = testAlg.intersectionOP(s1, s2);
		Automaton b = testAlg.minOP(a);
		System.out.println("fu");
	}
	
	static Automaton constructAutomatonS1(){
		Automaton auto = new AutomatonDFA();
		String automatonName;
		String automatonType;
		ArrayList<String> inputSymbolSet = new ArrayList<String>();
		State initialState;
		HashMap<String,State> states = new HashMap<String,State>();
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		
		State a = new State("A",AutomatonConstant.STATETYPE_ACCEPTED);
		State b = new State("B",AutomatonConstant.STATETYPE_NORMAL);
		
		states.put("A", a);
		states.put("B", b);
		
		inputSymbolSet.add("0");
		inputSymbolSet.add("1");
		
		ArrayList<String> condition0 = new ArrayList<String>();
		ArrayList<String> condition1 = new ArrayList<String>();
		ArrayList<String> condition01 = new ArrayList<String>();
		condition0.add("0");
		condition1.add("1");
		condition01.add("0");
		condition01.add("1");
		
		transitions.add(new TransitionDFA(a,condition0,a));
		transitions.add(new TransitionDFA(a,condition1,b));
		
		transitions.add(new TransitionDFA(b,condition0,a));
		transitions.add(new TransitionDFA(b,condition1,b));
		
		auto.setAutomatonName("s2");
		auto.setAutomatonType("DFA");
		auto.setInitialState(a);
		auto.setInputSymbolSet(inputSymbolSet);
		auto.setStates(states);
		auto.setTransitions(transitions);
		return auto;
	}
	
	static Automaton constructAutomatonS2(){
		Automaton auto = new AutomatonDFA();
		String automatonName;
		String automatonType;
		ArrayList<String> inputSymbolSet = new ArrayList<String>();
		State initialState;
		HashMap<String,State> states = new HashMap<String,State>();
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		
		State c = new State("C",AutomatonConstant.STATETYPE_ACCEPTED);
		State d = new State("D",AutomatonConstant.STATETYPE_ACCEPTED);
		State e = new State("E",AutomatonConstant.STATETYPE_NORMAL);
		
		states.put("C", c);
		states.put("D", d);
		states.put("E", e);
		
		inputSymbolSet.add("0");
		inputSymbolSet.add("1");
		
		ArrayList<String> condition0 = new ArrayList<String>();
		ArrayList<String> condition1 = new ArrayList<String>();
		ArrayList<String> condition01 = new ArrayList<String>();
		condition0.add("0");
		condition1.add("1");
		condition01.add("0");
		condition01.add("1");
		
		transitions.add(new TransitionDFA(c,condition0,d));
		transitions.add(new TransitionDFA(c,condition1,e));
		
		transitions.add(new TransitionDFA(d,condition0,d));
		transitions.add(new TransitionDFA(d,condition1,e));
		
		transitions.add(new TransitionDFA(e,condition0,c));
		transitions.add(new TransitionDFA(e,condition1,e));
		
		auto.setAutomatonName("s1");
		auto.setAutomatonType("DFA");
		auto.setInitialState(c);
		auto.setInputSymbolSet(inputSymbolSet);
		auto.setStates(states);
		auto.setTransitions(transitions);
		
		return auto;
	}
	
	static Automaton constructAutomaton(){
		Automaton auto = new AutomatonDFA();

		State a = new State("A",AutomatonConstant.STATETYPE_NORMAL);
		State b = new State("B",AutomatonConstant.STATETYPE_NORMAL);
		State c = new State("C",AutomatonConstant.STATETYPE_ACCEPTED);
		State d = new State("D",AutomatonConstant.STATETYPE_NORMAL);
		State e = new State("E",AutomatonConstant.STATETYPE_NORMAL);
		State f = new State("F",AutomatonConstant.STATETYPE_NORMAL);
		State g = new State("G",AutomatonConstant.STATETYPE_NORMAL);
		State h = new State("H",AutomatonConstant.STATETYPE_NORMAL);
		
		String automatonName;
		String automatonType;
		ArrayList<String> inputSymbolSet = new ArrayList<String>();
		State initialState;
		HashMap<String,State> states = new HashMap<String,State>();
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		
		states.put("A", a);
		states.put("B", b);
		states.put("C", c);
		states.put("D", d);
		states.put("E", e);
		states.put("F", f);
		states.put("G", g);
		states.put("H", h);
		
		inputSymbolSet.add("0");
		inputSymbolSet.add("1");
		
		ArrayList<String> condition0 = new ArrayList<String>();
		ArrayList<String> condition1 = new ArrayList<String>();
		ArrayList<String> condition01 = new ArrayList<String>();
		condition0.add("0");
		condition1.add("1");
		condition01.add("0");
		condition01.add("1");
		
		transitions.add(new TransitionDFA(a,condition0,b));
		transitions.add(new TransitionDFA(a,condition1,f));
		
		transitions.add(new TransitionDFA(b,condition1,c));
		transitions.add(new TransitionDFA(b,condition0,g));
		
		
		transitions.add(new TransitionDFA(c,condition1,c));
		transitions.add(new TransitionDFA(c,condition0,a));
		
		transitions.add(new TransitionDFA(d,condition0,c));
		transitions.add(new TransitionDFA(d,condition1,g));
		
		transitions.add(new TransitionDFA(e,condition0,h));
		transitions.add(new TransitionDFA(e,condition1,f));

		transitions.add(new TransitionDFA(f,condition0,c));
		transitions.add(new TransitionDFA(f,condition1,g));

		transitions.add(new TransitionDFA(g,condition0,g));
		transitions.add(new TransitionDFA(g,condition1,e));

		transitions.add(new TransitionDFA(h,condition0,g));
		transitions.add(new TransitionDFA(h,condition1,c));
		
		auto.setAutomatonName("test");
		auto.setAutomatonType("DFA");
		auto.setInitialState(a);
		auto.setInputSymbolSet(inputSymbolSet);
		auto.setStates(states);
		auto.setTransitions(transitions);
		
		return auto;
	}
}
