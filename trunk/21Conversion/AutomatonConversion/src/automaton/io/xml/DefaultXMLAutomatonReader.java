package automaton.io.xml;

import automaton.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import static util.Util.*;

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

    static {
        reader = new SAXReader();
        log = getLogger(DefaultXMLAutomatonReader.class);

    }

    @SuppressWarnings({"unchecked"})
    private DFA<String> parseDFA(Element root) {
        String name = root.attributeValue(FA_NAME);
        DFA<String> dfa = new DFA<String>(name);

        // parse states
        List<Element> xmlStates = root.element(STATES).elements(STATE);
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
        List<Element> xmlSymbols = root.element(INPUT_SYMBOLS).elements(INPUT_SYMBOL);
        for (Element xmlSymbol : xmlSymbols) {
            String symbol = xmlSymbol.attributeValue(SYMBOL);
            // add symbol
            dfa.addSymbol(symbol);
        }

        // parse transitions
        List<Element> xmlTransitions = root.element(TRANSITIONS).elements(TRANSITION);
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

    @SuppressWarnings({"unchecked"})
    private NFA<String> parseNFA(Element root) {
        String name = root.attributeValue(FA_NAME);
        NFA<String> nfa = new NFA<String>(name);
        // parse states
        List<Element> xmlStates = root.element(STATES).elements(STATE);
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
        List<Element> xmlSymbols = root.element(INPUT_SYMBOLS).elements(INPUT_SYMBOL);
        for (Element xmlSymbol : xmlSymbols) {
            String symbol = xmlSymbol.attributeValue(SYMBOL);
            // add symbol
            nfa.addSymbol(symbol);
        }

        // parse transitions
        List<Element> xmlTransitions = root.element(TRANSITIONS).elements(TRANSITION);
        for (Element xmlTransition : xmlTransitions) {
            String fromID = xmlTransition.element(FROM_STATE).attributeValue(STATE_ID);
            NFAState fromState = nfa.getStateByID(fromID);
            List<Element> xmlToStates = xmlTransition.element(TO_STATES).elements();
            Element xmlConditions = xmlTransition.element(CONDITIONS);
            if (xmlConditions == null) {
                for (Element xmlToState : xmlToStates) {
                    String toID = xmlToState.attributeValue(STATE_ID);
                    NFAState toState = nfa.getStateByID(toID);
                    nfa.addEpsilonTransition(fromState, toState);
                }
            } else {
                List<Element> conditions = xmlTransition.element(CONDITIONS).elements();
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
        }
        return nfa;
    }


    @Override
    public DFA<String> readDFA(File file) throws Exception {
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
    public DFA<String> readDFA(String content) throws Exception {
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
    public DFA<String> readDFA(InputStream in) throws Exception {
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
    public DFA<String> readDFA(URL url) throws Exception {
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
    public NFA<String> readNFA(File file) throws Exception {
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
    public NFA<String> readNFA(String content) throws Exception {
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
    public NFA<String> readNFA(InputStream in) throws Exception {
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
    public NFA<String> readNFA(URL url) throws Exception {
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
