/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfa2dfa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author admin
 */
public class NFA {
    
    public static String EmptySymbol = "~";
    public static String StatusNameDelimeter = "";
    
    private ArrayList<String> _allStatus = new ArrayList<String>();
    private ArrayList<String> _allSymbols = new ArrayList<String>();
    private String _startStatus;
    private ArrayList<String> _allFinalStatus = new ArrayList<String>();
    private ArrayList<NfaTranstraction> _allTransactions = new ArrayList<NfaTranstraction>();
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Start Status:" + getStartStatus() + "\n");

        sb.append("Final Status:");
        for (String symbol : getAllFinalStatus())
        {
            sb.append(symbol + ",");
        }
        
        sb.append("\n");
        
        sb.append("All Status:");
        for (String symbol : getAllStatus())
        {
            sb.append(symbol + ",");
        }
        
        sb.append("\n");
        
        sb.append("Symbols:");
        for (String symbol : getAllSymbols())
        {
            sb.append(symbol + ";");
        }
        
        sb.append("\n");
        
        sb.append("Transactions:");
        
        sb.append("\n");
        for (NfaTranstraction nfaNode : getAllTransactions())
        {
            sb.append(nfaNode.toString());
            sb.append("\n");
        }
        
        sb.append("\n");
  
        return sb.toString();
    }

    public ArrayList<String> getAllStatus() {
        return _allStatus;
    }

    public ArrayList<String> getAllSymbols() {
        return _allSymbols;
    }

    public String getStartStatus() {
        return _startStatus;
    }

    public void setStartStatus(String startStatus) {
        this._startStatus = startStatus;
    }

    public ArrayList<String> getAllFinalStatus() {
        return _allFinalStatus;
    }

    public ArrayList<NfaTranstraction> getAllTransactions() {
        return _allTransactions;
    }
    
}
