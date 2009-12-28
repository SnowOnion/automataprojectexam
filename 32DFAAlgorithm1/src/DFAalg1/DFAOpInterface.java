package DFAalg1;

import Exception.NotDFAException;
import Exception.NotThesameCharsetException;
import automaton.Automaton;

/**
 * DFA algorthm one
 * @author yyf
 *
 */
public interface DFAOpInterface {
	
	/**
	 * test the automaton type
	 * @param automaton
	 * @return automaton type
	 */
	public DFAalg1.DFAalg1Const.automatonType getAutomatonType(Automaton automaton);
	
	/**
	 * DFA union operation
	 * @param s1 one automaton
	 * @param s2 another automaton
	 * @return a automaton which is the union of s1 and s2
	 * @throws NotDFAException 
	 * @throws NotThesameCharsetException 
	 */
	public Automaton unionOP(Automaton s1,  Automaton s2) throws NotDFAException, NotThesameCharsetException;
	/**
	 * DFA intersection operation
	 * @param s1 one automaton
	 * @param s2 another automaton
	 * @return a automaton which is the intersection of s1 and s2
	 * @throws NotDFAException one of the input automaton is not a DFA
	 * @throws NotThesameCharsetException the character sets of the two DFA are not the same
	 */
	public Automaton intersectionOP(Automaton s1, Automaton s2)throws NotDFAException, NotThesameCharsetException;
	
	/**
	 * complement of a DFA
	 * @param s one automaton
	 * @return a automaton which is the complement of source automaton
	 * @throws NotDFAException 
	 */
	public Automaton notOP(Automaton s)throws NotDFAException;
	
	/**
	 * minimal algorithm
	 * @param s
	 * @return the minimal of a automaton
	 * @throws NotDFAException 
	 */
	public Automaton minOP(Automaton s)throws NotDFAException;
	
}
