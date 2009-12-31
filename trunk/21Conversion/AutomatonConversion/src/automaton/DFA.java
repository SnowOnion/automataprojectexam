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
 * Time: 11:11:36
 */
@SuppressWarnings({"unchecked"})
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
        for (C symbol : symbols) {
            state = state.shift(symbol);
            if (state == null)
                return false;
        }
        return state.isFinalState();
    }

    @Override
    public boolean isEmpty() {
        // bfs search
        List<DFAState> dfaStates = new ArrayList<DFAState>();
        Set<DFAState> visitedStates = new HashSet<DFAState>();
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
        log.log(Level.INFO, "this DFA is empty", this);
        return true;
    }

    @Override
    public boolean isInfinite() {
        //dfs search
        Set<DFAState> visitedStates = new HashSet<DFAState>();
        Set<DFAState> visitingStates = new HashSet<DFAState>();
        Set<DFAState> loopStartStates = new HashSet<DFAState>();
        return dfsVisit(getInitialState(), visitedStates, visitingStates, loopStartStates);
    }

    private boolean dfsVisit(DFAState state,
                             Set<DFAState> visitedStates,
                             Set<DFAState> visitingStates,
                             Set<DFAState> loopStartStates) {
        visitingStates.add(state);


        Collection<DFAState> states = state.getAllShiftableState();
        for (DFAState toState : states) {
            if (visitingStates.contains(toState)) {
                loopStartStates.add(toState);

            } else if (visitedStates.contains(toState)) {

            } else {
                if (dfsVisit(toState, visitedStates, visitingStates, loopStartStates))
                    return true;
            }
        }
        // find a loop to a final state
        if (state.isFinalState() && !loopStartStates.isEmpty())
            return true;
        // change state;
        if (loopStartStates.contains(state)) {
            loopStartStates.remove(state);
        }
        visitingStates.remove(state);
        visitedStates.add(state);
        return false;
    }

    @Override
    public AutomatonGraph<C, DFAState> toJUNGraph(C epsilon) {
        AutomatonGraph<C, DFAState> graph
                = new AutomatonGraph<C, DFAState>();
        for (DFAState state : getStates()) {
            //graph.addVertex(state);
            Set<TransitionEdge<C, DFAState>> edges = state.constructEdges();
            for (TransitionEdge<C, DFAState> edge : edges) {
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
