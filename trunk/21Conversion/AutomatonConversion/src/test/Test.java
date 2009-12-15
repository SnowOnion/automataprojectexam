package test;

import automaton.DFA;
import automaton.NFA;
import automaton.io.xml.DefaultXMLAutomatonReader;
import util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 12:01:03
 */
@SuppressWarnings({"unchecked"})
public class Test {
    public static void main(String[] args) throws Exception {
        String path = Util.getHome() + "\\AutomatonConversion\\src\\data\\";
        String dfaFile = path + "aComplexDFA.xml";
        DefaultXMLAutomatonReader reader = new DefaultXMLAutomatonReader();
        DFA dfa = reader.readDFA(new File(dfaFile));
        System.out.println("dfa = " + dfa.toString());
        List<String> input = new ArrayList<String>();
        input.add("Neg");
        input.add("s3");
        input.add("Dot");
        input.add("s1");
        System.out.println("dfa.accept(Neg s3 Dot s1) = " + dfa.accept(input));
        System.out.println("dfa.isEmpty() = " + dfa.isEmpty());
        System.out.println("dfa.isInfinite() = " + dfa.isInfinite());


        String nfaFile = path + "aComplexNFA.xml";
        NFA nfa = reader.readNFA(new File(nfaFile));
        System.out.println("nfa = " + nfa);
        System.out.println("nfa.toDFA() = " + nfa.toDFA());
        input.clear();
        input.add("a");
        input.add("b");
        input.add("c");

        System.out.println("nfa.accept(abc) = " + nfa.accept(input));

        input.clear();
        input.add("b");
        input.add("b");
        input.add("c");
        System.out.println("nfa.accept(bbc) = " + nfa.accept(input));

        System.out.println("nfa.isEmpty() = " + nfa.isEmpty());
        System.out.println("nfa.isInfinite() = " + nfa.isInfinite());
    }
}
