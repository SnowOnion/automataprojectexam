package graph;

import automaton.State;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 16:09:04
 */
public class AutomatonGraph<C extends Comparable<C>, S extends State> extends
        DirectedSparseMultigraph<S, TransitionEdge<C, S>> {
}
