package automaton;

import util.Util;

import java.util.List;
import java.util.logging.Logger;

/**
 * Create by: huangcd Date: 2009-12-10 Time: 11:43:31
 */
@SuppressWarnings( { "unchecked", "unused" })
public class NFA<C extends Comparable<C>> extends FiniteAutomaton<C, NFAState>
{
    private final static Logger log;

    static
    {
        log = Util.getLogger(NFA.class);
    }

    public NFA(String name)
    {
        super.setName(name);
    }

    public boolean addEpsilonTransition(NFAState from, NFAState to)
    {
        return from.addEpsilonTransition(to);
    }

    public boolean addTransition(NFAState from, C condition, NFAState to)
    {
        return from.addTransition(condition, to);
    }

    @Override
    public boolean accept(List<C> symbols)
    {
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public boolean isInfinite()
    {
        return false;
    }

    @Override
    public String toString()
    {
        String content = super.toString();
        content = content.replaceFirst(getClass().getSuperclass()
                .getSimpleName(), getClass().getSimpleName());
        return content;
    }
}
