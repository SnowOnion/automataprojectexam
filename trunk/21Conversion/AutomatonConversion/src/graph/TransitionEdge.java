package graph;

import automaton.State;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 15:46:46
 */
public class TransitionEdge<C extends Comparable<C>, S extends State> {
    private static Map<String, TransitionEdge> map;

    static {
        map = new HashMap<String, TransitionEdge>();
    }

    private S from;
    private S to;
    private Set<C> symbols;
    //private C symbol;

    private TransitionEdge(S form, S to, C symbol) {
        this.from = form;
        this.to = to;
        this.symbols = new TreeSet<C>();
        symbols.add(symbol);
    }

    public static <C extends Comparable<C>, S extends State>
    TransitionEdge<C, S> createEdge(S from, S to, C symbol) {
        String key = from.getStateID() + "->>" + to.getStateID();
        if (map.containsKey(key)) {
            TransitionEdge<C, S> edge = map.get(key);
            edge.symbols.add(symbol);
            return edge;
        } else {
            TransitionEdge<C, S> edge = new TransitionEdge<C, S>(from, to, symbol);
            map.put(key, edge);
            return edge;
        }
    }

    public S getFrom() {
        return from;
    }

    public void setFrom(S from) {
        this.from = from;
    }

    public S getTo() {
        return to;
    }

    public void setTo(S to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "" + symbols;
    }
}
