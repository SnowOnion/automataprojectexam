package DFAalg1;

import Exception.NotDFAException;
import Exception.NotThesameCharsetException;

import automaton.*;

public class DFAAlgorithm1 implements DFAOpInterface {

	DFAminAlg minAlg;
	DFAOperation dfaOP;
	TestFAType testType;
	
	public DFAAlgorithm1(){
		minAlg = new DFAminAlg();
		dfaOP = new DFAOperation();
		testType = new TestFAType();
	}
	
	/**
	 * test the automaton type
	 * @param automaton
	 * @return automaton type
	 */
	public  DFAalg1.DFAalg1Const.automatonType getAutomatonType(Automaton automaton) {
		return testType.getAutomatonType(automaton);
	}
	/**
	 * minimal algorithm
	 * @param s
	 * @return the minimal of a automaton
	 * @throws NotDFAException 
	 */
	public Automaton minOP(Automaton s)throws NotDFAException {
		// TODO Auto-generated method stub
		return minAlg.minOP(s);
	}
	/**
	 * DFA intersection operation
	 * @param s1 one automaton
	 * @param s2 another automaton
	 * @return a automaton which is the intersection of s1 and s2
	 * @throws NotDFAException one of the input automaton is not a DFA
	 * @throws NotThesameCharsetException the character sets of the two DFA are not the same
	 */
	public Automaton intersectionOP(Automaton s1, Automaton s2) throws NotDFAException, NotThesameCharsetException{
		// TODO Auto-generated method stub
		return dfaOP.intersectionOP(s1, s2);
	}
	/**
	 * complement of a DFA
	 * @param s one automaton
	 * @return a automaton which is the complement of source automaton
	 * @throws NotDFAException 
	 */
	public Automaton notOP(Automaton s)throws NotDFAException {
		// TODO Auto-generated method stub
		
		return dfaOP.notOP(s);
	}
	/**
	 * DFA union operation
	 * @param s1 one automaton
	 * @param s2 another automaton
	 * @return a automaton which is the union of s1 and s2
	 * @throws NotDFAException 
	 * @throws NotThesameCharsetException 
	 */
	public Automaton unionOP(Automaton s1, Automaton s2) throws NotDFAException, NotThesameCharsetException{
		// TODO Auto-generated method stub
		return dfaOP.unionOP(s1, s2);
	}
}
