package graph;

import automaton.FiniteAutomaton;
import automaton.State;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.picking.ShapePickSupport;
import static edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position.CNTR;
import edu.uci.ics.jung.visualization.util.Animator;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;

/**
 * Create by: huangcd
 * Date: 2009-12-22
 * Time: 10:24:42
 */
@SuppressWarnings({"unchecked"})
public class FAViewer<S extends State> {
    private JPanel panel;
    private JPanel controlPanel;
    private JComboBox operactionMode;
    private JCheckBox edgeBox;
    private JCheckBox verticesBox;
    private JPanel graphViewer;
    private JComboBox layoutsBox;

    private JComponent _createViewer(FiniteAutomaton<String, S> automaton, String epsilon) {

        AutomatonGraph<String, S> graph = automaton.toJUNGraph(epsilon);
        Layout<S, TransitionEdge<String, S>> layout =
                new CircleLayout<S, TransitionEdge<String, S>>(graph);
        VisualizationViewer<S, TransitionEdge<String, S>> viewer = new FAVisualizationViewer<S>(layout, new Dimension(600, 400));
        graphViewer.add(new GraphZoomScrollPane(viewer), BorderLayout.CENTER);
        return $$$getRootComponent$$$();
    }

    public static <S extends State> JComponent
    createViewer(FiniteAutomaton<String, S> automaton, String epsilon) {
        return new FAViewer<S>()._createViewer(automaton, epsilon);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel.add(controlPanel, BorderLayout.SOUTH);
        controlPanel.setBorder(BorderFactory.createTitledBorder("control"));
        layoutsBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("CircleLayout");
        defaultComboBoxModel1.addElement("KKLayout");
        defaultComboBoxModel1.addElement("SpringLayout2");
        defaultComboBoxModel1.addElement("StaticLayout");
        defaultComboBoxModel1.addElement("FRLayout");
        defaultComboBoxModel1.addElement("FRLayout2");
        defaultComboBoxModel1.addElement("ISOMLayout");
        layoutsBox.setModel(defaultComboBoxModel1);
        controlPanel.add(layoutsBox);
        operactionMode = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("TRANSFORMING");
        defaultComboBoxModel2.addElement("PICKING");
        operactionMode.setModel(defaultComboBoxModel2);
        controlPanel.add(operactionMode);
        edgeBox = new JCheckBox();
        edgeBox.setSelected(true);
        edgeBox.setText("Show Edges");
        edgeBox.setToolTipText("display edges label");
        controlPanel.add(edgeBox);
        verticesBox = new JCheckBox();
        verticesBox.setSelected(true);
        verticesBox.setText("Show Vertices");
        verticesBox.setToolTipText("display vertices label");
        controlPanel.add(verticesBox);
        graphViewer = new JPanel();
        graphViewer.setLayout(new BorderLayout(0, 0));
        panel.add(graphViewer, BorderLayout.CENTER);
        graphViewer.setBorder(BorderFactory.createTitledBorder("AutomatonViewer"));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

    {
    }

    private class FAVisualizationViewer<S extends State>
            extends VisualizationViewer<S, TransitionEdge<String, S>> {
        private FAVisualizationViewer<S> viewer;
        private Graph graph;
        private boolean showEdgeLabel;
        private boolean showVertexLabel;
        private double vertexSize;
        private Font font;

        public FAVisualizationViewer(Layout<S, TransitionEdge<String, S>> layout,
                                     Dimension dimension) {
            super(layout, dimension);
            graph = layout.getGraph();
            init();
        }

        private void init() {
            font = new Font("Comic Sans MS", Font.PLAIN, 15);
            viewer = this;
            showEdgeLabel = true;
            showVertexLabel = true;
            vertexSize = 15;
            setEdgeFunction();
            setVertexFunction();
            setControlFunction();
            setLayoutFunction();
        }

        // set vertex function
        private void setVertexFunction() {
            verticesBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    showVertexLabel = verticesBox.isSelected();
                    viewer.repaint();
                }
            });

            setVertexToolTipTransformer(
                    new Transformer<S, String>() {
                        @Override
                        public String transform(S s) {
                            return s.toString();
                        }
                    });

            getRenderContext().setVertexLabelTransformer(
                    new Transformer<S, String>() {
                        @Override
                        public String transform(S s) {
                            return showVertexLabel ? s.getStateID() : "";
                        }
                    });

            getRenderContext().setVertexFontTransformer(
                    new Transformer<S, Font>() {
                        @Override
                        public Font transform(S s) {
                            return font;
                        }
                    });

            getRenderContext().setVertexFillPaintTransformer(
                    new Transformer<S, Paint>() {
                        @Override
                        public Paint transform(S s) {
                            if (s.isFinalState())
                                return new Color(160, 160, 255);
                            else if (s.isInitialSate())
                                return new Color(100, 100, 255);
                            return new Color(204, 204, 255);
                        }
                    });

            getRenderContext().setVertexShapeTransformer(
                    new Transformer<S, Shape>() {
                        @Override
                        public Shape transform(S s) {
                            double x = -vertexSize;
                            double y = -vertexSize;
                            double w = 2 * vertexSize;
                            double h = 2 * vertexSize;
                            return new Ellipse2D.Double(x, y, w, h);
                        }
                    });

            getRenderer().getVertexLabelRenderer().setPosition(CNTR);
        }

        // set edge function
        private void setEdgeFunction() {
            edgeBox.addItemListener(
                    new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            showEdgeLabel = edgeBox.isSelected();
                            viewer.repaint();
                        }
                    });

            getRenderContext().setEdgeLabelTransformer
                    (new Transformer<TransitionEdge<String, S>, String>() {
                        @Override
                        public String transform(TransitionEdge<String, S> edge) {
                            return showEdgeLabel ? edge.toString() : "";
                        }
                    });

            getRenderContext().setEdgeFontTransformer(
                    new Transformer<TransitionEdge<String, S>, Font>() {
                        @Override
                        public Font transform(TransitionEdge<String, S> edge) {
                            return font;
                        }
                    });

            setEdgeToolTipTransformer(
                    new Transformer<TransitionEdge<String, S>, String>() {
                        @Override
                        public String transform(TransitionEdge<String, S> edge) {
                            return edge.toString();
                        }
                    });
        }

        private void setLayoutFunction() {
            layoutsBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    String layoutName = layoutsBox.getSelectedItem().toString();
                    String fullName = "edu.uci.ics.jung.algorithms.layout." + layoutName;
                    try {
                        Layout layout = (Layout) Class.forName(fullName).
                                getConstructor(Graph.class).newInstance(graph);
                        layout.setInitializer(viewer.getGraphLayout());
                        layout.setSize(viewer.getSize());
                        LayoutTransition transition = new LayoutTransition(viewer, viewer.getGraphLayout(), layout);
                        Animator animator = new Animator(transition);
                        animator.start();
                        viewer.getRenderContext().getMultiLayerTransformer().setToIdentity();
                        viewer.repaint();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }

        private void setControlFunction() {
            setPickSupport(new ShapePickSupport<S, TransitionEdge<String, S>>(this));
            //setToolTipText(graph.toString());
            final ModalGraphMouse gm = new DefaultModalGraphMouse<S, TransitionEdge<String, S>>();
            setGraphMouse(gm);
            operactionMode.addItemListener(new ItemListener() {
                ModalGraphMouse.Mode picking = ModalGraphMouse.Mode.PICKING;
                ModalGraphMouse.Mode transforming = ModalGraphMouse.Mode.TRANSFORMING;
                ModalGraphMouse.Mode annotating = ModalGraphMouse.Mode.ANNOTATING;

                @Override
                public void itemStateChanged(ItemEvent e) {
                    String mode = operactionMode.getSelectedItem().toString();
                    if ("PICKING".equals(mode)) {
                        gm.setMode(picking);
                    } else if ("TRANSFORMING".equals(mode)) {
                        gm.setMode(transforming);
                    } else if ("ANNOTATING".equals(mode)) {
                        gm.setMode(annotating);
                    }
                }
            });
        }
    }

}
