/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfa2dfa;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author admin
 */
public class NfaTranstraction {
    private String _startStatus;
    private String _endStatus;
    private ArrayList<String> _symbols = new  ArrayList<String>();
    
    public NfaTranstraction(String startStatus, String endStatus)
    {
        _startStatus = startStatus;
        _endStatus = endStatus;
    }

    public NfaTranstraction(String startStatus, String endStatus, String symbol)
    {
        this(startStatus, endStatus);
        _symbols.add(symbol);
    }

    public NfaTranstraction(String startStatus, String endStatus, String[] symbols)
    {
        this(startStatus, endStatus);
        for (String s : symbols)
        {
              _symbols.add(s);  
        }
    }
        
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(_startStatus + "->" + _endStatus + ":");
        for (String s : _symbols)
        {
              sb.append(s + ";");
        }
      
        return sb.toString();
    }
    
    public String getStartStatus() {
        return _startStatus;
    }

    public void setStartStatus(String startStatus) {
        this._startStatus = startStatus;
    }

    public String getEndStatus() {
        return _endStatus;
    }

    public void setEndStatus(String endStatus) {
        this._endStatus = endStatus;
    }

    public ArrayList<String> getSymbols() {
        return _symbols;
    }
    
    
}
