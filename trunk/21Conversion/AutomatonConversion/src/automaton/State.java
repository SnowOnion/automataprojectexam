package automaton;

import java.util.Set;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 9:23:40
 */
public interface State extends Comparable<State> {
    /**
     * return the types of the current state
     *
     * @return
     */
    public Set<StateType> getStateTypes();

    /**
     * return a unique id identify the current state
     *
     * @return
     */
    public String getStateID();

    /**
     * check whether the current state is a final state
     *
     * @return
     */
    public boolean isFinalState();

    /**
     * check whether the current state is a initial state
     *
     * @return
     */
    public boolean isInitialSate();

    /**
     * if the type of the state has been changed, its owner must be update by invoke this method
     *
     * @return
     */
    public boolean updateOwner();
}
