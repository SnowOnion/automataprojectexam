package automaton;

import graph.TransitionEdge;
import util.Util;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 9:48:09
 */
@SuppressWarnings({"JavaDoc", "unchecked"})
public class DFAState<C extends Comparable<C>> implements State {
    private static final Logger log;
    private final FiniteAutomaton owner;
    private final String stateID;
    private Set<StateType> stateTypes;
    private Map<C, DFAState> transitionMap;

    static {
        log = Util.getLogger(DFAState.class);
    }

    {
        stateTypes = new TreeSet<StateType>();
        stateTypes.add(StateType.COMMON);
        transitionMap = new TreeMap<C, DFAState>();
    }

    public DFAState(String stateID, FiniteAutomaton owner) {
        this.stateID = stateID;
        this.owner = owner;
    }

    DFAState(String stateID, Set<StateType> types, FiniteAutomaton owner) {
        this.stateID = stateID;
        this.stateTypes.addAll(types);
        this.owner = owner;
    }

    /**
     * add a transition to the transition map, if the specify symbol is already in the
     * map, return false directly; otherwise add the transition to the map and return true.
     *
     * @param symbol
     * @param state
     * @return
     */
    public boolean addTransition(C symbol, DFAState state) {
        if (transitionMap.containsKey(symbol)) {
            log.log(Level.SEVERE, "duplicate transition found. {symbol: "
                    + symbol + ", stateID" + state.getStateID() + "}", this);
            return false;
        }
        transitionMap.put(symbol, state);
        return true;
    }

    public Collection<DFAState> getAllShiftableState() {
        return new TreeSet<DFAState>(transitionMap.values());
    }

    public DFAState shift(C symbol) {
        if (!transitionMap.containsKey(symbol)) {
            return null;
//            String message = "the symbol \"" + symbol + "\" is not accept by the current " + this;
//            log.log(Level.INFO, message, this);
//            throw new UnconvertableException(message);
        }
        return transitionMap.get(symbol);
    }

    @Override
    public boolean addStateType(StateType type) {
        return stateTypes.add(type);
    }

    public Set<TransitionEdge<C, DFAState>> constructEdges() {
        Set<TransitionEdge<C, DFAState>> result = new HashSet<TransitionEdge<C, DFAState>>();
        for (Map.Entry<C, DFAState> entry : transitionMap.entrySet()) {
            TransitionEdge<C, DFAState> edge = TransitionEdge.createEdge
                    (this, entry.getValue(), entry.getKey());
            result.add(edge);
        }
        return result;
    }

    @Override
    public Set<StateType> getStateTypes() {
        if (stateTypes == null || stateTypes.isEmpty())
            log.log(Level.WARNING, "null or empty state types found", this);
        return stateTypes;
    }

    @Override
    public String getStateID() {
        return stateID;
    }

    @Override
    public boolean isFinalState() {
        return stateTypes.contains(StateType.FINAL);
    }

    @Override
    public boolean isInitialSate() {
        return stateTypes.contains(StateType.INITIAL);
    }

    @Override
    public FiniteAutomaton getOwner() {
        return owner;
    }

    @Override
    public boolean updateOwner() {
        //TODO: sample me
        owner.removeState(this);
        return owner.addState(this);
    }

    @Override
    public int compareTo(State o) {
        return this.hashCode() - o.hashCode();
    }

    @Override
    public String toString() {
        return "DFAState{" +
                "stateID='" + stateID + '\'' +
                ", stateTypes=" + stateTypes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DFAState dfaState = (DFAState) o;

        return !(owner != null ? !owner.equals(dfaState.owner) : dfaState.owner != null)
                && !(stateID != null ? !stateID.equals(dfaState.stateID) : dfaState.stateID != null);

    }

    @Override
    public int hashCode() {
        int result = owner != null ? owner.hashCode() : 0;
        result = 31 * result + (stateID != null ? stateID.hashCode() : 0);
        return result;
    }
}
