package automaton.io.xml;

import automaton.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocumentType;
import util.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create by: huangcd
 * Date: 2009-12-15
 * Time: 20:47:50
 */
@SuppressWarnings({"unchecked"})
public class DefaultXMLAutomatonWriter implements XMLAutomatonWriter {
    private static final Logger log;

    static {
        log = Util.getLogger(DefaultXMLAutomatonWriter.class);
    }

    public boolean write(FiniteAutomaton fa, File file) {
        try {
            Document doc = createDocument(fa);
            String str = doc.asXML();
            FileWriter writer = new FileWriter(file);
            writer.write(str);
            writer.flush();
            return true;
        } catch (IOException e) {
            log.log(Level.SEVERE, "error occurs:", e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean writeDFA(DFA dfa, File file) {
        return write(dfa, file);
    }

    @Override
    public boolean writeNFA(NFA nfa, File file) {
        return write(nfa, file);
    }

    private Document createDocument(FiniteAutomaton fa) {
        Document doc = DocumentHelper.createDocument();
        doc.setDocType(new DefaultDocumentType("FiniteAutomaton", "FA.dtd"));

        // add root
        Element root = doc.addElement("FiniteAutomaton");
        root.addAttribute("type", fa.getClass().getSimpleName());
        root.addAttribute("name", fa.getName());

        // add States
        Element XMLStates = root.addElement("States");
        Set<State> states = fa.getStates();
        for (State state : states) {
            Element XMLState = XMLStates.addElement("State");
            XMLState.addAttribute("stateID", state.getStateID());
            Element XMLStateTypes = XMLState.addElement("StateTypes");
            // add state Type
            Set<StateType> stateTypes = state.getStateTypes();
            for (StateType stateType : stateTypes) {
                XMLStateTypes.addElement("StateType").addAttribute("type", stateType.toString());
            }
        }

        // add Symbols
        Element XMLSymbols = root.addElement("InputSymbols");
        Set symbols = fa.getSymbols();
        for (Object symbol : symbols) {
            XMLSymbols.addElement("InputSymbol").addAttribute("symbol", symbol.toString());
        }

        // add Transitions
        Element XMLTransitions = root.addElement("Transitions");
        for (State state : states) {
            // add nfa transitions
            if (state instanceof NFAState) {
                NFAState nfaState = (NFAState) state;
                Set<NFAState> epsilonTransitions = nfaState.getEpsilonTransition();
                // add epsilon transitions
                if (!epsilonTransitions.isEmpty()) {
                    Element XMLTransition = XMLTransitions.addElement("Transition");
                    XMLTransition.addElement("FromState").addAttribute("stateID", nfaState.getStateID());
                    Element XMLToStates = XMLTransition.addElement("ToStates");
                    for (NFAState toState : epsilonTransitions) {
                        XMLToStates.addElement("ToState").addAttribute("stateID", toState.getStateID());
                    }
                }

                // add normal transitions
                for (Object symbol : symbols) {
                    Set<NFAState> toStates = nfaState.shift((Comparable) symbol);
                    if (toStates != null && !toStates.isEmpty()) {
                        Element XMLTransition = XMLTransitions.addElement("Transition");
                        XMLTransition.addElement("FromState").addAttribute("stateID", nfaState.getStateID());
                        XMLTransition.addElement("Conditions").addElement("Condition").
                                addAttribute("symbol", symbol.toString());
                        Element XMLToStates = XMLTransition.addElement("ToStates");
                        for (NFAState toState : toStates) {
                            XMLToStates.addElement("ToState").addAttribute("stateID", toState.getStateID());
                        }
                    }
                }
            }

            // add dfa transitions
            else if (state instanceof DFAState) {
                DFAState dfaState = (DFAState) state;
                for (Object symbol : symbols) {
                    DFAState toState = dfaState.shift((Comparable) symbol);
                    if (toState != null) {
                        Element XMLTransition = XMLTransitions.addElement("Transition");
                        XMLTransition.addElement("FromState").addAttribute("stateID", dfaState.getStateID());
                        XMLTransition.addElement("Conditions").addElement("Condition").
                                addAttribute("symbol", symbol.toString());
                        XMLTransition.addElement("ToStates").addElement("ToState").
                                addAttribute("stateID", toState.getStateID());
                    }

                }
            }

        }

        return doc;
    }
}
