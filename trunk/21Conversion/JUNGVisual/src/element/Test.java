package element;

import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.SatelliteVisualizationViewer;

import javax.swing.*;
import java.awt.*;

/**
 * Create by: huangcd
 * Date: 2009-12-8
 * Time: 19:06:01
 */
public class Test {
    public static void main(String[] args) {
        Graph<Vertex, Edge> graph = new DirectedSparseMultigraph<Vertex, Edge>();
        Vertex v1 = new Vertex("1");
        Vertex v2 = new Vertex("2");

        graph.addEdge(new Edge(v1, v2, "10", "1"), v1, v2, EdgeType.DIRECTED);
        Layout<Vertex, Edge> layout = new DAGLayout<Vertex, Edge>(graph);
        layout.setSize(new Dimension(300, 400));
        VisualizationViewer vv = new VisualizationViewer(layout);
        BasicVisualizationServer<Vertex, Edge> bvs = new SatelliteVisualizationViewer<Vertex, Edge>(vv);

        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}

class Vertex {
    String id;

    Vertex(String id) {
        this.id = id;
    }
}

class Edge {
    Vertex from;
    Vertex to;
    String weight;
    String condition;

    Edge(Vertex from, Vertex to, String weight, String condition) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.condition = condition;
    }
}
