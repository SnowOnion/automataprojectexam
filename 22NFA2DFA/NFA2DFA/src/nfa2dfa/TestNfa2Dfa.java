/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfa2dfa;

/**
 *
 * @author admin
 */
public class TestNfa2Dfa {
    public static void InitializeTestNfa(NFA nfa)
    {
        nfa.setStartStatus("q0");
        nfa.getAllFinalStatus().add("q5");
        nfa.getAllStatus().add("q0");
        nfa.getAllStatus().add("q1");
        nfa.getAllStatus().add("q2");
        nfa.getAllStatus().add("q3");
        nfa.getAllStatus().add("q4");
        nfa.getAllStatus().add("q5");

        nfa.getAllSymbols().add("+");
        nfa.getAllSymbols().add("-");
        nfa.getAllSymbols().add(".");
        for (int i = 0; i < 10; i++)
        {
            nfa.getAllSymbols().add(String.valueOf(i));
        }
        
        nfa.getAllTransactions().add(new NfaTranstraction("q0", "q1",
            new String[]{NFA.EmptySymbol, "+", "-"}));
        nfa.getAllTransactions().add(new NfaTranstraction("q1", "q1",
            new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
        nfa.getAllTransactions().add(new NfaTranstraction("q1", "q2",
            "."));
        nfa.getAllTransactions().add(new NfaTranstraction("q2", "q3",
            new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
        nfa.getAllTransactions().add(new NfaTranstraction("q3", "q3",
            new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
        nfa.getAllTransactions().add(new NfaTranstraction("q3", "q5",
            NFA.EmptySymbol));
        nfa.getAllTransactions().add(new NfaTranstraction("q1", "q4",
            new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
        nfa.getAllTransactions().add(new NfaTranstraction("q4", "q3",
            "."));
    }

}
