/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfa2dfa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author admin
 */
public class Nfa2Dfa {
    
    public static NFA ConvertNfa2Dfa(NFA nfa)
    {
        NFA dfa = new NFA();
        ArrayList<String> startStatuses = GetEncloser(nfa, nfa.getStartStatus());
        dfa.setStartStatus(BuildDfaStatusName(startStatuses));
        dfa.getAllSymbols().addAll(nfa.getAllSymbols());

        HashMap<String, ArrayList<String>> searchedStatuses = new HashMap<String, ArrayList<String>>();
        HashMap<String, ArrayList<String>> toSearchStatuses = new HashMap<String, ArrayList<String>> ();
        toSearchStatuses.put(dfa.getStartStatus(), startStatuses);

        // Begin, End, Symbols
        HashMap<String, HashMap<String, ArrayList<String>>> trans = new
                HashMap<String, HashMap<String, ArrayList<String>>>();
        
        while (toSearchStatuses.size() > 0)
        {
            String beginStatus = toSearchStatuses.keySet().toArray()[0].toString();
            ArrayList<String> beginStatuses = toSearchStatuses.get(beginStatus);
            toSearchStatuses.remove(beginStatus);
            if (!searchedStatuses.containsKey(beginStatus))
            {
                searchedStatuses.put(beginStatus, beginStatuses);
            }
                    
            for (String symbol : nfa.getAllSymbols())
            {
                ArrayList<String> endStatus = GetNextStatus(nfa, beginStatuses, symbol);
                if (endStatus.size() == 0)
                {
                    continue;
                }
                
                String endStatusName = BuildDfaStatusName(endStatus);
                if (!searchedStatuses.containsKey(endStatusName))
                {
                    toSearchStatuses.put(endStatusName, endStatus);
                }
                
                String beginStatusName = BuildDfaStatusName(beginStatuses);
                AddTransaction(beginStatusName, endStatusName, trans, symbol);
            }
        }
        
        dfa.getAllStatus().addAll(searchedStatuses.keySet());
        dfa.getAllFinalStatus().addAll(GetFinalStatuses(searchedStatuses, nfa));
        for (String beginStatus : trans.keySet())
        {
            for (String endStatus : trans.get(beginStatus).keySet())
            {
                NfaTranstraction tran = new NfaTranstraction(beginStatus, endStatus);
                tran.getSymbols().addAll(trans.get(beginStatus).get(endStatus));
                dfa.getAllTransactions().add(tran);
            }
        }
        
        return dfa;
    }
    
    private static ArrayList<String> GetNextStatus(NFA nfa, ArrayList<String> beginStatus,
            String symbol)
    {
        ArrayList<String> nextStatus = new ArrayList<String>();
        for (NfaTranstraction tran : nfa.getAllTransactions())
        {
            if (beginStatus.contains(tran.getStartStatus()) &&
                    tran.getSymbols().contains(symbol) &&
                    !nextStatus.contains(tran.getEndStatus()))
            {
                nextStatus.add(tran.getEndStatus());
            }
        }
        
        return GetEncloser(nfa, nextStatus);
    }
    
    private static String BuildDfaStatusName(ArrayList<String> dfaStatus)
    {
        ArrayList<String> newStatus = new ArrayList<String>(dfaStatus);
        
        Collections.sort(dfaStatus);
        StringBuffer sb = new StringBuffer();
        for (String status : newStatus)
        {
            sb.append(status + NFA.StatusNameDelimeter);
        }
        
        return sb.toString();
    }
    
    private static ArrayList<String> GetEncloser(NFA nfa,
        String status)
    {
        ArrayList<String> statusSet = new ArrayList<String>();
        statusSet.add(status);
        return GetEncloser(nfa, statusSet, NFA.EmptySymbol);
    }

    public static ArrayList<String> GetEncloser(NFA nfa,
        ArrayList<String> statusSet)
    {
        return GetEncloser(nfa, statusSet, NFA.EmptySymbol);
    }
            
    public static ArrayList<String> GetEncloser(NFA nfa,
            ArrayList<String> statusSet, String symbol)
    {
        ArrayList<String> ecloseStatus = new ArrayList<String>();
        ecloseStatus.addAll(statusSet);

        ArrayList<String> toSearchStatus = new ArrayList<String>();
        toSearchStatus.addAll(statusSet);
        
        ArrayList<String> searchedStatus = new ArrayList<String>();
        
        while (toSearchStatus.size() > 0)
        {
            String status = toSearchStatus.get(0);
            toSearchStatus.remove(0);
            for(NfaTranstraction trans  : nfa.getAllTransactions())
            {
                if (trans.getStartStatus().equals(status) &&
                        trans.getSymbols().contains(symbol))
                {
                    if (!ecloseStatus.contains(trans.getEndStatus()))
                    {
                        ecloseStatus.add(trans.getEndStatus());
                    }
                    
                    if (!toSearchStatus.contains(trans.getEndStatus()) &&
                            !searchedStatus.contains(trans.getEndStatus()))
                    {
                        toSearchStatus.add(trans.getEndStatus());
                    }
                }
            }
            
            if (!searchedStatus.contains(status))
            {
                searchedStatus.add(status);
            }
        }
        
        return ecloseStatus;
    }

    private static void AddTransaction(String beginStatusName, String endStatusName, HashMap<String, HashMap<String, ArrayList<String>>> trans, String symbol) {
        if (!trans.containsKey(beginStatusName)) {
            trans.put(beginStatusName, new HashMap<String, ArrayList<String>>());
        }

        if (!trans.get(beginStatusName).containsKey(endStatusName)) {
            trans.get(beginStatusName).put(endStatusName, new ArrayList<String>());
        }

        if (!trans.get(beginStatusName).get(endStatusName).contains(symbol)) {
            trans.get(beginStatusName).get(endStatusName).add(symbol);
        }
    }

    private static ArrayList<String> GetFinalStatuses(
            HashMap<String, ArrayList<String>> searchedStatuses, NFA nfa) {
        ArrayList<String> finalStatuses = new ArrayList<String>();
                
        for (String status : searchedStatuses.keySet()) {
            boolean isFinalStatus = false;
            for (String nfaStatus : searchedStatuses.get(status)) {
                if (nfa.getAllFinalStatus().contains(nfaStatus)) {
                    isFinalStatus = true;
                    break;
                }
            }

            if (isFinalStatus) {
                finalStatuses.add(status);
            }
        }
        
        return finalStatuses;
    }
    
}
