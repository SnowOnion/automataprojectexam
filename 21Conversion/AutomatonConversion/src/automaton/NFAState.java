package automaton;

import util.Util;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 14:16:03
 */
@SuppressWarnings({"JavaDoc", "unchecked"})
public class NFAState<C extends Comparable<C>> implements State {
    private static Logger log;
    private FiniteAutomaton owner;
    private String stateID;
    private Set<StateType> stateTypes;
    private Map<C, Set<NFAState>> transitionMap;

    private Set<NFAState> epsilonTransition;

    static {
        log = Util.getLogger(NFAState.class);
    }

    {
        stateTypes = new TreeSet<StateType>();
        stateTypes.add(StateType.COMMON);
        transitionMap = new TreeMap<C, Set<NFAState>>();
        epsilonTransition = new TreeSet<NFAState>();
    }

    public NFAState(String stateID, FiniteAutomaton owner) {
        this.stateID = stateID;
        this.owner = owner;
    }

    protected NFAState(String stateID, Set<StateType> types, FiniteAutomaton owner) {
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
    public boolean addTransition(C symbol, NFAState state) {
        if (!transitionMap.containsKey(symbol)) {
            transitionMap.put(symbol, new TreeSet<NFAState>());
        }
        transitionMap.get(symbol).add(state);
        return true;
    }

    public Collection<NFAState> getAllShiftableStates() {
        Collection<NFAState> result = new HashSet<NFAState>();
        for (Collection<NFAState> col : transitionMap.values())
            result.addAll(col);
        return result;
    }

    public Set<NFAState> shift(C symbol) {
        if (!transitionMap.containsKey(symbol)) {
            return null;
//            log.log(Level.SEVERE, "the symbol \"" + symbol + "\" is not miss", this);
//            throw new UnconvertableException("the symbol \"" + symbol + "\" is not miss");
        }
        return transitionMap.get(symbol);
    }

    @Override
    public boolean addStateType(StateType type) {
        return stateTypes.add(type);
    }

    @Override
    public Set<StateType> getStateTypes() {
        if (stateTypes == null || stateTypes.isEmpty())
            log.log(Level.WARNING, "null or empty state types found", this);
        return stateTypes;
    }

    public boolean addEpsilonTransition(NFAState state) {
        return epsilonTransition.add(state);
    }

    public Set<NFAState> getEpsilonTransition() {
        return epsilonTransition;
    }

    public TreeSet<NFAState> getEpsilonClosure() {
        TreeSet<NFAState> result = new TreeSet<NFAState>(epsilonTransition);
        log.log(Level.INFO, "calculate epsilon closure, result is " + result, this);
        result.add(this);
        return result;
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
        //TODO: test me
        owner.removeState(this);
        return owner.addState(this);
    }

    @Override
    public int compareTo(State o) {
        return this.hashCode() - o.hashCode();
    }

    @Override
    public String toString() {
        return "NFAState{" +
                "stateID='" + stateID + '\'' +
                ", stateTypes=" + stateTypes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NFAState nfaState = (NFAState) o;

        return !(owner != null ? !owner.equals(nfaState.owner) : nfaState.owner != null)
                && !(stateID != null ? !stateID.equals(nfaState.stateID) : nfaState.stateID != null);

    }

    @Override
    public int hashCode() {
        int result = owner != null ? owner.hashCode() : 0;
        result = 31 * result + (stateID != null ? stateID.hashCode() : 0);
        return result;
    }
}
