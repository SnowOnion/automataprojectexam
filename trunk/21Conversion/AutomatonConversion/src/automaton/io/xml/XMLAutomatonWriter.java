package automaton.io.xml;

import automaton.DFA;
import automaton.NFA;

import java.io.File;

/**
 * Create by: huangcd
 * Date: 2009-12-15
 * Time: 20:44:43
 */
public interface XMLAutomatonWriter {
    boolean writeDFA(DFA dfa, File file);

    boolean writeNFA(NFA nfa, File file);
}
