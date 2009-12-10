package automaton;

import edu.uci.ics.jung.graph.util.EdgeType;
import graph.AutomatonGraph;
import graph.TransitionEdge;
import util.Util;

import java.util.*;
import java.util.logging.Logger;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 11:11:36
 */
public class DFA<C extends Comparable<C>> extends FiniteAutomaton<C, DFAState> {
    private final static Logger log;

    static {
        log = Util.getLogger(DFA.class);
    }

    public DFA(String name) {
        super.setName(name);
    }

    public boolean addTransition(DFAState from, C condition, DFAState to) {
        return from.addTransition(condition, to);
    }

    @Override
    public boolean accept(List<C> symbols) {
        DFAState state = initialState;
        try {
            for (C symbol : symbols) {
                state = state.shift(symbol);
            }
            return state.isFinalState();
        } catch (UnconvertableException e) {
            //e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        //TODO: make sure this method works with loops
        // bfs search
        List<DFAState> dfaStates = new ArrayList<DFAState>();
        Set<DFAState> visitedStates = new TreeSet<DFAState>();
        dfaStates.add(initialState);
        while (!dfaStates.isEmpty()) {
            DFAState state = dfaStates.remove(0);
            visitedStates.add(state);
            if (state.isFinalState())
                return false;
            Collection<DFAState> allStates = state.getAllShiftableState();
            for (DFAState s : allStates) {
                if (dfaStates.contains(s) || visitedStates.contains(s))
                    continue;
                dfaStates.add(s);
            }
        }
        return true;
    }

    @Override
    public boolean isInfinite() {
        //TODO: finish me
        return false;
    }

    @Override
    public AutomatonGraph<C, DFAState> toJUNGraph() {
        AutomatonGraph<C, DFAState> graph
                = new AutomatonGraph<C, DFAState>();
        for (DFAState state : getStates()) {
            graph.addVertex(state);
            Set<TransitionEdge<C, DFAState>> edges = state.constructEdges();
            for (TransitionEdge<C, DFAState> edge : edges) {
                graph.addEdge(edge, edge.getFrom(), edge.getTo(), EdgeType.DIRECTED);
            }
        }
        return graph;
    }
}
