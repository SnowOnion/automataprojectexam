package DFAalg1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Document;

import xml.*;
import Exception.NotDFAException;
import Exception.NotThesameCharsetException;
import automaton.*;

public class testAutomaton {
	/**
	 * @param args
	 * @throws NotDFAException
	 * @throws NotThesameCharsetException
	 */
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String args[]) throws Exception{		
//		Automaton auto= constructAutomaton();
//		Automaton s1 = constructAutomatonS1();
		Automaton s2 = constructAutomatonS2();
		AutomatonFactory autoFact = AutomatonFactory.getInstance();
		autoFact.writeAutomatonToXml(s2, new File("P158B.xml"));
//		File out = new File("P155min.xml");
//		autoFact.writeAutomatonToXml(auto, out);
//		autoFact = AutomatonFactory.getInstance();
//		autoFact.writeAutomatonToXml(s1, new File("P158A.xml"));
//		autoFact.writeAutomatonToXml(s2, new File("P158B.xml"));
//		autoFact.writeAutomatonToXml(constructAutomatonS3(), new File("PPTexmp.xml"));
		
////		System.out.print(s1.toString());
//		DFAOpInterface testAlg= new DFAAlgorithm1();
//		System.out.println(testAlg.getAutomatonType(auto));
//		testAlg.minOP(auto);
//		Automaton a = testAlg.intersectionOP(s1, s2);
//		Automaton b = testAlg.minOP(a);
//		
//		autoFact.writeAutomatonToXml(testAlg.minOP(constructAutomatonS3()),new File("PPTexmpMin.xml"));
//		Automaton S3 = autoFact.getAutomatonFromXml(new File("PPTexmp.xml"));
//		S3 = testAlg.minOP(S3);
//		autoFact.writeAutomatonToXml(S3, new File("PPTReadMin.xml"));
//		Automaton testNFA = autoFact.getAutomatonFromXml(new File("NFA1.xml"));
//		System.out.println(testAlg.getAutomatonType(testNFA));
//		testNFA = autoFact.getAutomatonFromXml(new File("TemplateNFA.xml"));
//		System.out.println(testAlg.getAutomatonType(testNFA));
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
	
	static Automaton constructAutomatonS3(){
		Automaton auto = new AutomatonDFA();
		String automatonName;
		String automatonType;
		ArrayList<String> inputSymbolSet = new ArrayList<String>();
		State initialState;
		HashMap<String,State> states = new HashMap<String,State>();
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		
		State a_1 = new State("1",AutomatonConstant.STATETYPE_NORMAL);
		State a_2 = new State("2",AutomatonConstant.STATETYPE_NORMAL);
		State a_3 = new State("3",AutomatonConstant.STATETYPE_NORMAL);
		State a_4 = new State("4",AutomatonConstant.STATETYPE_NORMAL);
		State a_5 = new State("5",AutomatonConstant.STATETYPE_ACCEPTED);
		State a_6 = new State("6",AutomatonConstant.STATETYPE_ACCEPTED);
		State a_7 = new State("7",AutomatonConstant.STATETYPE_ACCEPTED);
		
		states.put("1", a_1);
		states.put("2", a_2);
		states.put("3", a_3);
		states.put("4", a_4);
		states.put("5", a_5);
		states.put("6", a_6);
		states.put("7", a_7);
		
		inputSymbolSet.add("a");
		inputSymbolSet.add("b");
		
		ArrayList<String> conditionA = new ArrayList<String>();
		ArrayList<String> conditionB = new ArrayList<String>();
		ArrayList<String> conditionAB = new ArrayList<String>();
		conditionA.add("a");
		conditionB.add("b");
		conditionAB.add("a");
		conditionAB.add("b");
		
		transitions.add(new TransitionDFA(a_1,conditionA,a_6));
		transitions.add(new TransitionDFA(a_1,conditionB,a_3));
		
		transitions.add(new TransitionDFA(a_2,conditionA,a_7));
		transitions.add(new TransitionDFA(a_2,conditionB,a_3));
		
		transitions.add(new TransitionDFA(a_3,conditionA,a_1));
		transitions.add(new TransitionDFA(a_3,conditionB,a_5));
		
		transitions.add(new TransitionDFA(a_4,conditionA,a_4));
		transitions.add(new TransitionDFA(a_4,conditionB,a_6));
		
		transitions.add(new TransitionDFA(a_5,conditionA,a_7));
		transitions.add(new TransitionDFA(a_5,conditionB,a_3));
		
		transitions.add(new TransitionDFA(a_6,conditionA,a_4));
		transitions.add(new TransitionDFA(a_6,conditionB,a_1));
		
		transitions.add(new TransitionDFA(a_7,conditionA,a_4));
		transitions.add(new TransitionDFA(a_7,conditionB,a_2));
		
		auto.setAutomatonName("pptExp");
		auto.setAutomatonType("DFA");
		auto.setInitialState(a_1);
		auto.setInputSymbolSet(inputSymbolSet);
		auto.setStates(states);
		auto.setTransitions(transitions);
		return auto;
	}
	
}
