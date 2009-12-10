package automaton.xml;

import automaton.DFA;
import automaton.NFA;
import automaton.FiniteAutomaton;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.dom4j.DocumentException;

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
