package gui;

import javax.swing.JFrame;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;

public class Port extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -464235672367772404L;

	final int PORT_DIAMETER = 20;

	final int PORT_RADIUS = PORT_DIAMETER / 2;

	public Port()
	{
		super("Hello, World!");

		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try
		{
			mxCell v1 = (mxCell) graph.insertVertex(parent, null, "Hello", 20,
					20, 100, 100, "");
			v1.setConnectable(false);
			mxGeometry geo = graph.getModel().getGeometry(v1);
			// The size of the rectangle when the minus sign is clicked
			geo.setAlternateBounds(new mxRectangle(20, 20, 100, 50));

			mxGeometry geo1 = new mxGeometry(0, 0.5, PORT_DIAMETER,
					PORT_DIAMETER);
			// Because the origin is at upper left corner, need to translate to
			// position the center of port correctly
			geo1.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
			geo1.setRelative(true);

			mxCell port1 = new mxCell(null, geo1,
					"shape=ellipse;perimter=ellipsePerimeter");
			port1.setVertex(true);

			mxGeometry geo2 = new mxGeometry(1.0, 0.5, PORT_DIAMETER,
					PORT_DIAMETER);
			geo2.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
			geo2.setRelative(true);

			mxCell port2 = new mxCell(null, geo2,
					"shape=ellipse;perimter=ellipsePerimeter");
			port2.setVertex(true);

			graph.addCell(port1, v1);
			graph.addCell(port2, v1);

			graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}

	public static void main(String[] args)
	{
		Port frame = new Port();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}

}
