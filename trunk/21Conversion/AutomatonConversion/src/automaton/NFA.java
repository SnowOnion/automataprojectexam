package automaton;

import edu.uci.ics.jung.graph.util.EdgeType;
import graph.AutomatonGraph;
import graph.TransitionEdge;
import util.Util;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 11:43:31
 */
@SuppressWarnings({"unchecked"})
public class NFA<C extends Comparable<C>> extends FiniteAutomaton<C, NFAState> {
    private final static Logger log;

    static {
        log = Util.getLogger(NFA.class);
    }

    public NFA(String name) {
        super.setName(name);
    }


    public boolean addEpsilonTransition(NFAState from, NFAState to) {
        return from.addEpsilonTransition(to);
    }


    public boolean addTransition(NFAState from, C condition, NFAState to) {
        return from.addTransition(condition, to);
    }

    @Override
    public boolean accept(List<C> symbols) {
        return toDFA().accept(symbols);
    }

    @Override
    public boolean isEmpty() {
        return toDFA().isEmpty();
    }

    @Override
    public boolean isInfinite() {
        return toDFA().isInfinite();
    }

    private DFAState constructDFAState(String name, TreeSet<NFAState> closure,
                                       boolean isInitialState, DFA owner) {
        Set<StateType> stateTypes = new TreeSet<StateType>();
        stateTypes.add(StateType.COMMON);
        if (isInitialState) {
            stateTypes.add(StateType.INITIAL);
        }
        for (NFAState state : closure) {
            if (state.isFinalState()) {
                stateTypes.add(StateType.FINAL);
            }
        }

        return new DFAState(name, stateTypes, owner);
    }

    private TreeSet<NFAState> shift(TreeSet<NFAState> states, C symbol) {
        TreeSet<NFAState> result = new TreeSet<NFAState>();
        for (NFAState state : states) {
            Set<NFAState> shiftSet;
//            try {
            shiftSet = state.shift(symbol);
            if (shiftSet == null)
                continue;
//            } catch (UnconvertableException e) {
//                continue;
//            }
            for (NFAState s : shiftSet) {
                result.addAll(s.getEpsilonClosure());
            }
        }
        return result;
    }

    private String getNFAStateSetName(TreeSet<NFAState> states) {
        StringBuilder buffer = new StringBuilder();
        for (NFAState state : states) {
            buffer.append(state.getStateID()).append(",");
        }
        if (buffer.length() > 0)
            buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }

    public DFA toDFA() {
        log.log(Level.INFO, "try to convert the current NFA to a DFA", this);

        String dfaName = "DFA[" + this.getName() + "]";
        DFA dfa = new DFA(dfaName);

        // add symbols
        for (C symbol : getSymbols()) {
            dfa.addSymbol(symbol);
        }

        Map<DFAState, TreeSet<NFAState>> stateMap = new HashMap<DFAState, TreeSet<NFAState>>();
        Map<String, DFAState> dfaStateMap = new HashMap<String, DFAState>();

        // add initial state
        TreeSet<NFAState> initialNFAStates = getInitialState().getEpsilonClosure();
        String initialStateName = getNFAStateSetName(initialNFAStates);
        DFAState initialState = constructDFAState(initialStateName,
                initialNFAStates, true, dfa);
        dfa.addState(initialState);
        stateMap.put(initialState, initialNFAStates);
        dfaStateMap.put(initialStateName, initialState);

        Stack<DFAState> stateStack = new Stack<DFAState>();
        stateStack.add(initialState);
        while (!stateStack.isEmpty()) {
            DFAState fromState = stateStack.pop();
            TreeSet<NFAState> fromNFAStates = stateMap.get(fromState);
            for (C symbol : getSymbols()) {
                TreeSet<NFAState> toNFAStates = shift(fromNFAStates, symbol);
                DFAState toState;
                String toName = getNFAStateSetName(toNFAStates);
                if (dfaStateMap.containsKey(toName)) {
                    toState = (DFAState) dfa.getStateByID(toName);
                    dfa.addTransition(fromState, symbol, toState);
                } else {
                    String toStateName = getNFAStateSetName(toNFAStates);
                    toState = constructDFAState(toStateName, toNFAStates, false, dfa);
                    dfa.addState(toState);
                    stateMap.put(toState, toNFAStates);
                    dfaStateMap.put(toStateName, toState);
                    dfa.addTransition(fromState, symbol, toState);
                    stateStack.push(toState);
                }
            }
        }
        return dfa;
    }

    @Override
    public AutomatonGraph<C, NFAState> toJUNGraph(C epsilon) {
        AutomatonGraph<C, NFAState> graph
                = new AutomatonGraph<C, NFAState>();
        for (NFAState state : getStates()) {
            graph.addVertex(state);
            Set<TransitionEdge<C, NFAState>> edges = state.constructEdges(epsilon);
            for (TransitionEdge<C, NFAState> edge : edges) {
                graph.addEdge(edge, edge.getFrom(), edge.getTo(), EdgeType.DIRECTED);
            }
        }
        return graph;

    }

    @Override
    public String toString() {
        String content = super.toString();
        content = content.replaceFirst
                (getClass().getSuperclass().getSimpleName(), getClass().getSimpleName());
        return content;
    }
}
