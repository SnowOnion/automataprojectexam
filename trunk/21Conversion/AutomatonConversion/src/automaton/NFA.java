package automaton;

import util.Util;

import java.util.List;
import java.util.logging.Logger;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import graph.TransitionEdge;
import graph.AutomatonGraph;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 11:43:31
 */
public class NFA<C extends Comparable<C>> extends FiniteAutomaton<C, NFAState> {
    private final static Logger log;
    private boolean allowVarepsilon;
    private C varepsilon;

    static {
        log = Util.getLogger(NFA.class);
    }

    public NFA(String name)
    {
        super.setName(name);
    }

    public NFA(String name, boolean allowVarepsilon, C varepsilon) {
        super.setName(name);
        this.allowVarepsilon = allowVarepsilon;
        if (this.allowVarepsilon)
            this.varepsilon = varepsilon;            
    }

    public boolean addVarepsilonTransition(NFAState from, NFAState to) {
        //TODO: complete this method
        return from.addTransition(varepsilon, to);
    }


    public boolean addTransition(NFAState from, C condition, NFAState to) {
        return from.addTransition(condition, to);
    }

    @Override
    public boolean accept(List<C> symbols) {
        //TODO: finish me
        return super.accept(symbols);
    }

    @Override
    public boolean isEmpty() {
        //TODO: finish me
        return false;
    }

    @Override
    public boolean isInfinite() {
        //TODO: finish me
        return false;
    }

    @Override
    public AutomatonGraph<C, NFAState> toJUNGraph() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
