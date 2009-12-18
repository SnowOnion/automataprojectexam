package automaton.io.xml;

import automaton.DFA;
import automaton.FiniteAutomaton;
import automaton.NFA;
import automaton.State;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Create by: huangcd Date: 2009-12-10 Time: 12:42:29
 */
public interface XMLAutomatonReader<C extends Comparable<C>, S extends State>
{
    public DFA<C> readDFA(File file) throws Exception;

    public DFA<C> readDFA(String content) throws Exception;

    public DFA<C> readDFA(InputStream in) throws Exception;

    public DFA<C> readDFA(URL url) throws Exception;

    public FiniteAutomaton<C, S> readFiniteAutomaton(File file)
            throws Exception;

    public FiniteAutomaton<C, S> readFiniteAutomaton(String content)
            throws Exception;

    public FiniteAutomaton<C, S> readFiniteAutomaton(InputStream in)
            throws Exception;

    public FiniteAutomaton<C, S> readFiniteAutomaton(URL url) throws Exception;

    public NFA<C> readNFA(File file) throws Exception;

    public NFA<C> readNFA(String content) throws Exception;

    public NFA<C> readNFA(InputStream in) throws Exception;

    public NFA<C> readNFA(URL url) throws Exception;
}
