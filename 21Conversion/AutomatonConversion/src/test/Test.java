package test;

import automaton.DFA;
import automaton.DFAState;
import automaton.xml.DefaultXMLAutomatonReader;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import graph.AutomatonViewer;
import graph.TransitionEdge;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 12:01:03
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String path = Util.getProjectHome() + "\\AutomatonConversion\\src\\data\\";
        String dfaFile = path + "aComplexDFA.xml";
        DefaultXMLAutomatonReader reader = new DefaultXMLAutomatonReader();
        DFA dfa = reader.readDFA(new File(dfaFile));
        System.out.println("dfa = " + dfa);
        List<String> input = new ArrayList<String>();
        input.add("Neg");
        input.add("s3");
        input.add("Dot");
        input.add("s1");
        System.out.println(dfa.accept(input));
        System.out.println(dfa.isEmpty());

        AutomatonViewer viewer = AutomatonViewer.createAutomatonViewer(dfa);

        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(viewer);
        frame.pack();
        frame.setVisible(true);
    }
}
