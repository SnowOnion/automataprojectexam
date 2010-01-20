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

        System.out.println();

        TestAcceptable(dfa, "1.#", false);
        TestAcceptable(dfa, "1%", false);
        TestAcceptable(dfa, "11.1", true);
        TestAcceptable(dfa, "-16.1231", true);
        TestAcceptable(dfa, "-+16.1", false);
        TestAcceptable(dfa, "^&", false);
        TestAcceptable(dfa, "1912.%", false);
        TestAcceptable(dfa, "-1912.%", false);

        System.out.println("Dfa empty Test case 1 :" + (dfa.IsEmptyLanguage() ? "Failed" : "Passed"));

        NFA emptyDfa = new NFA();
        TestNfa2Dfa.InitalizeEmptyLanguageDfa(emptyDfa);
        System.out.println("Dfa empty Test case 2 :" + (emptyDfa.IsEmptyLanguage() ? "Passed" : "Failed"));
    }

    private static void TestAcceptable(NFA dfa, String testString, boolean expected)
    {
        boolean result = dfa.IsAccept(testString);
        StringBuilder sb = new StringBuilder();
        if (result == expected)
        {
            sb.append("Pass");
        }
        else
        {
            sb.append("Failed");
        }

        sb.append(" test case for [" + testString + "]");
        if (result == expected)
        {
             sb.append(", ");
            sb.append(expected ? "accepted" : "rejected");
        }
        else
        {
            sb.append(", should be ");
            sb.append(expected ? "accepted" : "rejected");
            sb.append(" but ");
            sb.append(result ? "accepted" : "rejected");
        }

        System.out.println(sb.toString());
    }
}
