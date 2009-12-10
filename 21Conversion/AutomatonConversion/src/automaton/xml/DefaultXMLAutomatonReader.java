package automaton.xml;

import automaton.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import util.Util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 12:45:17
 */
public class DefaultXMLAutomatonReader implements XMLAutomatonReader {
    private final static SAXReader reader;
    private final static Logger log;
    private final static String STATES_XPATH;
    private final static String SYMBOLS_XPATH;
    private final static String TRANSITIONS_XPATH;
    private final static String STATE_ID;
    private final static String FA_NAME;
    private static final String FA_TYPE;
    private static final String DFA_NAME;
    private static final String NFA_NAME;
    private static final String STATE_TYPE_ATTR;
    private static final String STATE_TYPES;
    private static final String STATE_TYPE;
    private static final String SYMBOL;
    private static final String FINAL_STATE;
    private static final String INITIAL_STATE;
    private static final String FROM_STATE;
    private static final String CONDITIONS;
    private static final String TO_STATES;

    static {
        reader = new SAXReader();
        log = Util.getLogger(DefaultXMLAutomatonReader.class);
        STATES_XPATH = "//States/State";
        SYMBOLS_XPATH = "//InputSymbols/InputSymbol";
        TRANSITIONS_XPATH = "//Transitions/Transition";
        STATE_ID = "stateID";
        FA_NAME = "name";
        FA_TYPE = "type";
        STATE_TYPE_ATTR = "type";
        DFA_NAME = "DFA";
        NFA_NAME = "NFA";
        STATE_TYPES = "StateTypes";
        STATE_TYPE = "StateType";
        SYMBOL = "symbol";
        FINAL_STATE = "FINAL";
        INITIAL_STATE = "INITIAL";
        FROM_STATE = "FromState";
        CONDITIONS = "Conditions";
        TO_STATES = "ToStates";
    }

    private DFA parseDFA(Element root) {
        String name = root.attributeValue(FA_NAME);
        DFA<String> dfa = new DFA<String>(name);

        // parse states
        List<Element> xmlStates = root.selectNodes(STATES_XPATH);
        for (Element xmlState : xmlStates) {
            String id = xmlState.attributeValue(STATE_ID);
            DFAState state = new DFAState(id, dfa);
            // parse state types
            List<Element> stateTypes = xmlState.element(STATE_TYPES).elements();
            for (Element stateType : stateTypes) {
                String type = stateType.attributeValue(STATE_TYPE_ATTR);
                if (type.equals(FINAL_STATE))
                    state.addStateType(StateType.FINAL);
                else if (type.equals(INITIAL_STATE))
                    state.addStateType(StateType.INITIAL);
            }
            // add state
            dfa.addState(state);
        }

        // parse symbols
        List<Element> xmlSymbols = root.selectNodes(SYMBOLS_XPATH);
        for (Element xmlSymbol : xmlSymbols) {
            String symbol = xmlSymbol.attributeValue(SYMBOL);
            // add symbol
            dfa.addSymbol(symbol);
        }

        // parse transitions
        List<Element> xmlTransitions = root.selectNodes(TRANSITIONS_XPATH);
        for (Element xmlTransition : xmlTransitions) {
            String fromID = xmlTransition.element(FROM_STATE).attributeValue(STATE_ID);
            DFAState fromState = dfa.getStateByID(fromID);
            List<Element> conditions = xmlTransition.element(CONDITIONS).elements();
            List<Element> xmlToStates = xmlTransition.element(TO_STATES).elements();
            for (Element xmlToState : xmlToStates) {
                String toID = xmlToState.attributeValue(STATE_ID);
                DFAState toState = dfa.getStateByID(toID);
                for (Element condition : conditions) {
                    String condSymbol = condition.attributeValue(SYMBOL);
                    // add transition
                    dfa.addTransition(fromState, condSymbol, toState);
                }
            }
        }
        return dfa;
    }

    private NFA parseNFA(Element root) {
        //TODO: change the DFAState to NFAState
        String name = root.attributeValue(FA_NAME);
        NFA<String> nfa = new NFA<String>(name);
        // parse states
        List<Element> xmlStates = root.selectNodes(STATES_XPATH);
        for (Element xmlState : xmlStates) {
            String id = xmlState.attributeValue(STATE_ID);
            NFAState state = new NFAState(id, nfa);
            // parse state types
            List<Element> stateTypes = xmlState.element(STATE_TYPES).elements();
            for (Element stateType : stateTypes) {
                String type = stateType.attributeValue(STATE_TYPE_ATTR);
                if (type.equals(FINAL_STATE))
                    state.addStateType(StateType.FINAL);
                else if (type.equals(INITIAL_STATE))
                    state.addStateType(StateType.INITIAL);
            }
            // add state
            nfa.addState(state);
        }

        // parse symbols
        List<Element> xmlSymbols = root.selectNodes(SYMBOLS_XPATH);
        for (Element xmlSymbol : xmlSymbols) {
            String symbol = xmlSymbol.attributeValue(SYMBOL);
            // add symbol
            nfa.addSymbol(symbol);
        }

        // parse transitions
        List<Element> xmlTransitions = root.selectNodes(TRANSITIONS_XPATH);
        for (Element xmlTransition : xmlTransitions) {
            String fromID = xmlTransition.element(FROM_STATE).attributeValue(STATE_ID);
            NFAState fromState = nfa.getStateByID(fromID);
            List<Element> conditions = xmlTransition.element(CONDITIONS).elements();
            List<Element> xmlToStates = xmlTransition.element(TO_STATES).elements();
            for (Element xmlToState : xmlToStates) {
                String toID = xmlToState.attributeValue(STATE_ID);
                NFAState toState = nfa.getStateByID(toID);
                for (Element condition : conditions) {
                    String condSymbol = condition.attributeValue(SYMBOL);
                    // add transition
                    nfa.addTransition(fromState, condSymbol, toState);
                }
            }
        }
        return nfa;
    }


    @Override
    public DFA readDFA(File file) throws Exception {
        try {
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(DFA_NAME)) {
                return parseDFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a DFA";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + file + "\"", e);
            throw e;
        }
    }

    @Override
    public DFA readDFA(String content) throws Exception {
        try {
            Document doc = reader.read(content);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(DFA_NAME)) {
                return parseDFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a DFA";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + content + "\"", e);
            throw e;
        }
    }

    @Override
    public DFA readDFA(InputStream in) throws Exception {
        try {
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(DFA_NAME)) {
                return parseDFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a DFA";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + in + "\"", e);
            throw e;
        }
    }

    @Override
    public DFA readDFA(URL url) throws Exception {
        try {
            Document doc = reader.read(url);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(DFA_NAME)) {
                return parseDFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a DFA";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + url + "\"", e);
            throw e;
        }
    }

    @Override
    public FiniteAutomaton readFiniteAutomaton(File file) throws Exception {
        try {
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(DFA_NAME)) {
                return parseDFA(root);
            } else if (type.equals(NFA_NAME)) {
                return parseNFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a FiniteAutomaton";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + file + "\"", e);
            throw e;
        }
    }

    @Override
    public FiniteAutomaton readFiniteAutomaton(String content) throws Exception {
        try {
            Document doc = reader.read(content);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(DFA_NAME)) {
                return parseDFA(root);
            } else if (type.equals(NFA_NAME)) {
                return parseNFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a FiniteAutomaton";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + content + "\"", e);
            throw e;
        }
    }

    @Override
    public FiniteAutomaton readFiniteAutomaton(InputStream in) throws Exception {
        try {
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(DFA_NAME)) {
                return parseDFA(root);
            } else if (type.equals(NFA_NAME)) {
                return parseNFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a FiniteAutomaton";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + in + "\"", e);
            throw e;
        }
    }

    @Override
    public FiniteAutomaton readFiniteAutomaton(URL url) throws Exception {
        try {
            Document doc = reader.read(url);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(DFA_NAME)) {
                return parseDFA(root);
            } else if (type.equals(NFA_NAME)) {
                return parseNFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a FiniteAutomaton";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + url + "\"", e);
            throw e;
        }
    }

    @Override
    public NFA readNFA(File file) throws Exception {
        try {
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(NFA_NAME)) {
                return parseNFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a NFA";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + file + "\"", e);
            throw e;
        }
    }

    @Override
    public NFA readNFA(String content) throws Exception {
        try {
            Document doc = reader.read(content);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(NFA_NAME)) {
                return parseNFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a NFA";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + content + "\"", e);
            throw e;
        }
    }

    @Override
    public NFA readNFA(InputStream in) throws Exception {
        try {
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(NFA_NAME)) {
                return parseNFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a NFA";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + in + "\"", e);
            throw e;
        }
    }

    @Override
    public NFA readNFA(URL url) throws Exception {
        try {
            Document doc = reader.read(url);
            Element root = doc.getRootElement();
            String type = root.attributeValue(FA_TYPE);
            if (type.equals(NFA_NAME)) {
                return parseNFA(root);
            } else {
                String message = "cannot convert a \"" + type + "\" to a NFA";
                log.log(Level.SEVERE, message, this);
                throw new UnconvertableException(message);
            }
        } catch (DocumentException e) {
            log.log(Level.SEVERE, "exception occurs while reading \"" + url + "\"", e);
            throw e;
        }
    }
}
