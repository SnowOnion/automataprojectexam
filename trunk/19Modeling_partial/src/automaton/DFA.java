package automaton;

import util.Util;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 11:11:36
 */
@SuppressWarnings({"unchecked", "unused"})
public class DFA<C extends Comparable<C>> extends FiniteAutomaton<C, DFAState>
{
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
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    private boolean dfsVisit(DFAState state,
                             Set<DFAState> visitedStates,
                             Set<DFAState> visitingStates,
                             Set<DFAState> loopStartStates) {
        return false;
    }

    @Override
    public String toString() {
        String content = super.toString();
        content = content.replaceFirst
                (getClass().getSuperclass().getSimpleName(), getClass().getSimpleName());
        return content;
    }
}
