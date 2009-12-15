package automaton.io.xml;

import automaton.DFA;
import automaton.FiniteAutomaton;
import automaton.NFA;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 12:42:29
 */
public interface XMLAutomatonReader {
    public DFA readDFA(File file) throws Exception;

    public DFA readDFA(String content) throws Exception;

    public DFA readDFA(InputStream in) throws Exception;

    public DFA readDFA(URL url) throws Exception;

    public FiniteAutomaton readFiniteAutomaton(File file) throws Exception;

    public FiniteAutomaton readFiniteAutomaton(String content) throws Exception;

    public FiniteAutomaton readFiniteAutomaton(InputStream in) throws Exception;

    public FiniteAutomaton readFiniteAutomaton(URL url) throws Exception;

    public NFA readNFA(File file) throws Exception;

    public NFA readNFA(String content) throws Exception;

    public NFA readNFA(InputStream in) throws Exception;

    public NFA readNFA(URL url) throws Exception;
}
