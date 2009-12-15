package graph;

import automaton.FiniteAutomaton;
import automaton.State;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationModel;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import org.apache.commons.collections15.Transformer;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Create by: huangcd
 * Date: 2009-12-10
 * Time: 16:12:09
 */
public class AutomatonViewer<C extends Comparable<C>, S extends State>
        extends VisualizationViewer<S, TransitionEdge<C, S>> {
    private Transformer<TransitionEdge<C, S>, String> edgeLabel;
    private Transformer<S, String> vertexLabel;
    private Transformer<S, Shape> vertexShape;
    private Transformer<S, Paint> vertexFillPaint;

    public AutomatonViewer(Layout<S, TransitionEdge<C, S>> layout) {
        super(layout);
        init();
    }

    public AutomatonViewer(Layout<S, TransitionEdge<C, S>> layout, Dimension dimension) {
        super(layout, dimension);
        init();
    }

    public AutomatonViewer(VisualizationModel<S, TransitionEdge<C, S>> model) {
        super(model);
        init();
    }

    public AutomatonViewer(VisualizationModel<S, TransitionEdge<C, S>> model, Dimension dimension) {
        super(model, dimension);
        init();
    }

    public static <C extends Comparable<C>, S extends State> AutomatonViewer<C, S> createAutomatonViewer(FiniteAutomaton<C, S> automaton) {
        AutomatonGraph<C, S> graph = automaton.toJUNGraph();
        Layout<S, TransitionEdge<C, S>> layout = new FRLayout<S, TransitionEdge<C, S>>(graph);// Layout(graph);
        return new AutomatonViewer<C, S>(layout, new Dimension(600, 400));
    }

    private void init() {
        RenderContext<S, TransitionEdge<C, S>> context = this.getRenderContext();
        final double size = 20;

        edgeLabel = new Transformer<TransitionEdge<C, S>, String>() {
            @Override
            public String transform(TransitionEdge<C, S> edge) {
                return edge.toString();
            }
        };
        vertexLabel = new Transformer<S, String>() {
            @Override
            public String transform(S s) {
                return s.getStateID();
            }
        };
        vertexFillPaint = new Transformer<S, Paint>() {
            @Override
            public Paint transform(S s) {
                Paint result = Color.BLUE;
                return result;
            }
        };
        vertexShape = new Transformer<S, Shape>() {
            @Override
            public Shape transform(S s) {
                Shape result = new Ellipse2D.Double(-size, -size, 2 * size, 2 * size);
                return result;
            }
        };

        context.setEdgeLabelTransformer(edgeLabel);
        context.setVertexLabelTransformer(vertexLabel);
        context.setVertexFillPaintTransformer(vertexFillPaint);
        context.setVertexShapeTransformer(vertexShape);
    }
}
