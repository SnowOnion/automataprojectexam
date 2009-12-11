/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfa2dfa;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NFA nfa = new NFA();
        TestNfa2Dfa.InitializeTestNfa(nfa);

        // Test ininitalize succesfully.
        System.out.println(nfa.toString());
        
        // Test get enclose method
        ArrayList<String> statusSet = new ArrayList<String>();
        statusSet.add("q0");
        ArrayList<String> q0Encloser = Nfa2Dfa.GetEncloser(nfa, statusSet);
        System.out.print("Enclose status of q0 are: ");
        for (String status : q0Encloser)
        {
            System.out.print(status + ";");
        }
        
        System.out.println();
        System.out.println("=======================");
        
        NFA dfa = Nfa2Dfa.ConvertNfa2Dfa(nfa);
        System.out.println(dfa.toString());
        
    }
}
