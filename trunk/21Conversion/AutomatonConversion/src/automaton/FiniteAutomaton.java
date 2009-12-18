package automaton;

import graph.AutomatonGraph;
import util.Util;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 9:21:02
 */
public abstract class FiniteAutomaton<C extends Comparable<C>, S extends State> {
    private final static Logger log;

    static {
        log = Util.getLogger(FiniteAutomaton.class);
    }

    protected String name;
    protected Set<C> symbols;
    protected Set<S> states;
    protected Set<S> finalStates;
    protected S initialState;
    protected Map<String, S> stateMap;

    {
        symbols = new TreeSet<C>();
        states = new TreeSet<S>();
        finalStates = new TreeSet<S>();
        stateMap = new HashMap<String, S>();
    }

    public boolean addSymbol(C symbol) {
        return symbols.add(symbol);
    }

    public boolean addState(S state) {
        boolean result = true;
        if (state.isInitialSate()) {
            if (initialState != null) {
                log.log(Level.WARNING, "duplicate initial states found", this);
                result = false;
            }
            initialState = state;
        }
        if (state.isFinalState()) {
            finalStates.add(state);
        }
        states.add(state);
        stateMap.put(state.getStateID(), state);
        return result;
    }

    protected boolean removeState(S state) {
        boolean result = true;
        if (state.isInitialSate()) {
            log.log(Level.WARNING, "no initial state for the current automaton", this);
            initialState = null;
            result = false;
        }
        if (finalStates.contains(state)) {
            finalStates.remove(state);
            if (finalStates.isEmpty())
                log.log(Level.INFO, "no final state for the current automaton", this);
        }
        states.remove(state);
        stateMap.remove(state.getStateID());
        return result;
    }

    public S getStateByID(String id) {
        if (!stateMap.containsKey(id)) {
            log.log(Level.WARNING, "stateID \"" + id + "\" not found", this);
            return null;
        }
        return stateMap.get(id);
    }

    /**
     * an empty implmentation of the accept function
     *
     * @param symbols a list of input symbols
     * @return true if the input symbols is accepted by the automaton, false otherwise
     */
    public abstract boolean accept(List<C> symbols);

    public Set<S> getStates() {
        return states;
    }

    public Set<S> getFinalStates() {
        return finalStates;
    }

    public Set<C> getSymbols() {
        return symbols;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public S getInitialState() {
        return initialState;
    }

    public void setInitialState(S initialState) {
        this.initialState = initialState;
    }

    @Override
    public String toString() {
        return "FiniteAutomaton{" +
                "name='" + name + '\'' +
                ", symbols=" + symbols +
                ", states=" + states +
                ", finalStates=" + finalStates +
                ", initialState=" + initialState +
                '}';
    }

    public abstract boolean isEmpty();

    public abstract boolean isInfinite();

    public abstract AutomatonGraph<C, S> toJUNGraph(C epsilon);
}
