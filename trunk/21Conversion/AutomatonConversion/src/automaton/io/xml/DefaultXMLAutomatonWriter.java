package automaton.io.xml;

import automaton.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocumentType;
import util.Util;
import static util.Util.*;

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
        log = getLogger(DefaultXMLAutomatonWriter.class);
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
        doc.setDocType(new DefaultDocumentType(DOCUMENT_TYPE, SYSTEM_ID));

        // add root
        Element root = doc.addElement(DOCUMENT_TYPE);
        root.addAttribute(FA_TYPE, fa.getClass().getSimpleName());
        root.addAttribute(FA_NAME, fa.getName());

        // add States
        Element XMLStates = root.addElement(STATES);
        Set<State> states = fa.getStates();
        for (State state : states) {
            Element XMLState = XMLStates.addElement(STATE);
            XMLState.addAttribute(STATE_ID, state.getStateID());
            Element XMLStateTypes = XMLState.addElement(STATE_TYPES);
            // add state Type
            Set<StateType> stateTypes = state.getStateTypes();
            for (StateType stateType : stateTypes) {
                XMLStateTypes.addElement(STATE_TYPE).
                        addAttribute(STATE_TYPE_ATTR, stateType.toString());
            }
        }

        // add Symbols
        Element XMLSymbols = root.addElement(INPUT_SYMBOLS);
        Set symbols = fa.getSymbols();
        for (Object symbol : symbols) {
            XMLSymbols.addElement(INPUT_SYMBOL).addAttribute(SYMBOL, symbol.toString());
        }

        // add Transitions
        Element XMLTransitions = root.addElement(Util.TRANSITIONS);
        for (State state : states) {
            // add nfa transitions
            if (state instanceof NFAState) {
                NFAState nfaState = (NFAState) state;
                Set<NFAState> epsilonTransitions = nfaState.getEpsilonTransitions();
                // add epsilon transitions
                if (!epsilonTransitions.isEmpty()) {
                    Element XMLTransition = XMLTransitions.addElement(TRANSITION);
                    XMLTransition.addElement(FROM_STATE).addAttribute(STATE_ID, nfaState.getStateID());
                    Element XMLToStates = XMLTransition.addElement(TO_STATES);
                    for (NFAState toState : epsilonTransitions) {
                        XMLToStates.addElement(TO_STATE).addAttribute(STATE_ID, toState.getStateID());
                    }
                }

                // add normal transitions
                for (Object symbol : symbols) {
                    Set<NFAState> toStates = nfaState.shift((Comparable) symbol);
                    if (toStates != null && !toStates.isEmpty()) {
                        Element XMLTransition = XMLTransitions.addElement(TRANSITION);
                        XMLTransition.addElement(FROM_STATE).addAttribute(STATE_ID, nfaState.getStateID());
                        XMLTransition.addElement(CONDITIONS).addElement(CONDITION).
                                addAttribute(SYMBOL, symbol.toString());
                        Element XMLToStates = XMLTransition.addElement(TO_STATES);
                        for (NFAState toState : toStates) {
                            XMLToStates.addElement(TO_STATE).addAttribute(STATE_ID, toState.getStateID());
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
                        Element XMLTransition = XMLTransitions.addElement(TRANSITION);
                        XMLTransition.addElement(FROM_STATE).addAttribute(STATE_ID, dfaState.getStateID());
                        XMLTransition.addElement(CONDITIONS).addElement(CONDITION).
                                addAttribute(Util.SYMBOL, symbol.toString());
                        XMLTransition.addElement(TO_STATES).addElement(TO_STATE).
                                addAttribute(STATE_ID, toState.getStateID());
                    }

                }
            }

        }

        return doc;
    }
}
