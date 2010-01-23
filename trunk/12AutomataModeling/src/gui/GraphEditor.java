package gui;

import gui.editor.BasicGraphEditor;
import gui.editor.EditorMenuBar;
import gui.editor.EditorPalette;

import java.awt.Color;
import java.awt.Point;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.*;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphTransferable;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

public class GraphEditor extends BasicGraphEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4601740824088314699L;

	/**
	 * Holds the shared number formatter.
	 * 
	 * @see NumberFormat#getInstance()
	 */
	public static final NumberFormat numberFormat = NumberFormat.getInstance();

	/**
	 * Holds the URL for the icon to be used as a handle for creating new
	 * connections. This is currently unused.
	 */
	public static URL url = null;
	private CustomGraphComponent graphComponent1;
	
	private ModelAgent modelAgent;

	// GraphEditor.class.getResource("/gui/images/connector.gif");

	public GraphEditor() {
		this("自动机建模工具", new CustomGraphComponent(new CustomGraph()));
		graphComponent1=(CustomGraphComponent)this.getGraphComponent();
	}

	/**
	 * 
	 */
	public GraphEditor(String appTitle, mxGraphComponent component) {
		super(appTitle, component);
		final mxGraph graph = graphComponent.getGraph();
		((CustomGraph)graph).setPropertyPanel(this.getPropertyPanel());
		modelAgent=new ModelAgent(graph);
		this.getPropertyPanel().setModelAgent((mxGraphModel)graph.getModel(),modelAgent);
		Business.setModelAgent(modelAgent);

		// Creates the shapes palette
		EditorPalette shapesPalette = insertPalette(mxResources.get("shapes"));

		// Sets the edge template to be used for creating new edges if an edge
		// is clicked in the shape palette
		shapesPalette.addListener(mxEvent.SELECT, new mxIEventListener() {
			public void invoke(Object sender, mxEventObject evt) {
				Object tmp = evt.getProperty("transferable");

				if (tmp instanceof mxGraphTransferable) {
					mxGraphTransferable t = (mxGraphTransferable) tmp;
					Object cell = t.getCells()[0];

					if (graph.getModel().isEdge(cell)) {
						((CustomGraph) graph).setEdgeTemplate(cell);
					}
				}
			}

		});

		// Adds some template cells for dropping into the graph
		shapesPalette
				.addTemplate(
						"Ellipse",
						new ImageIcon(
								GraphEditor.class
										.getResource("/gui/images/ellipse.png")),
						"ellipse", 160, 160, "");
/*		shapesPalette
				.addTemplate(
						"Double Ellipse",
						new ImageIcon(
								GraphEditor.class
										.getResource("/gui/images/doubleellipse.png")),
						"ellipse;shape=doubleEllipse", 160, 160, "");
*/		shapesPalette
				.addEdgeTemplate(
						"Straight",
						new ImageIcon(
								GraphEditor.class
										.getResource("/gui/images/straight.png")),
						"straight", 120, 120, "");
	}

	public void setGraph1(mxGraph graph){
		graphComponent1.setGraph1(graph);
	}
	/**
	 * 
	 */
	public static class CustomGraphComponent extends mxGraphComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6833603133512882012L;

		/**
		 * 
		 * @param graph
		 */
		public CustomGraphComponent(mxGraph graph) {
			super(graph);

			// Sets switches typically used in an editor
			setPageVisible(true);
			setGridVisible(true);
			setToolTips(true);
			getConnectionHandler().setCreateTarget(true);

			// Loads the defalt stylesheet from an external file
			mxCodec codec = new mxCodec();
			Document doc = mxUtils.loadDocument(GraphEditor.class.getResource(
					"/gui/resources/default-style.xml")
					.toString());
			codec.decode(doc.getDocumentElement(), graph.getStylesheet());

			// Sets the background to white
			getViewport().setOpaque(false);
			setBackground(Color.WHITE);
		}
		public void setGraph1(mxGraph graph){
			super.setGraph(graph);

			// Sets switches typically used in an editor
			setPageVisible(true);
			setGridVisible(true);
			setToolTips(true);
			getConnectionHandler().setCreateTarget(true);

			// Loads the defalt stylesheet from an external file
			mxCodec codec = new mxCodec();
			Document doc = mxUtils.loadDocument(GraphEditor.class.getResource(
					"/gui/resources/default-style.xml")
					.toString());
			codec.decode(doc.getDocumentElement(), graph.getStylesheet());

			// Sets the background to white
			getViewport().setOpaque(false);
			setBackground(Color.WHITE);
			
		}

		/**
		 * Overrides drop behaviour to set the cell style if the target is not a
		 * valid drop target and the cells are of the same type (eg. both
		 * vertices or both edges).
		 */
		public Object[] importCells(Object[] cells, double dx, double dy,
				Object target, Point location) {
			if (target == null && cells.length == 1 && location != null) {
//				System.out.println("TTTTTTT "+location.x+" "+location.y);
				target = getCellAt(location.x, location.y);

				if (target instanceof mxICell && cells[0] instanceof mxICell) {
					mxICell targetCell = (mxICell) target;
					mxICell dropCell = (mxICell) cells[0];

					if (targetCell.isVertex() == dropCell.isVertex()
							|| targetCell.isEdge() == dropCell.isEdge()) {
						mxIGraphModel model = graph.getModel();
/*						model.setStyle(target, model.getStyle(cells[0]));
						graph.setSelectionCell(target);
*/
						JOptionPane.showMessageDialog(null, "位置重复，不能拖入");
//						System.out.println("YYYYYYYYYYYY "+location.x+" "+location.y);
						return null;
					}
				}
			}

			return super.importCells(cells, dx, dy, target, location);
		}

	}

	public void viewProperty(mxCell cell){
		this.getPropertyPanel().setCell(cell);
	}
	/**
	 * A graph that creates new edges from a given template edge.
	 */
	public static class CustomGraph extends mxGraph {
		/**
		 * Holds the edge to be used as a template for inserting new edges.
		 */
		private PropertyPanel panel;
		protected Object edgeTemplate;

		/**
		 * Custom graph that defines the alternate edge style to be used when
		 * the middle control point of edges is double clicked (flipped).
		 */
		public CustomGraph() {
			setAlternateEdgeStyle("edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical");
		}
		
		public void setPropertyPanel(PropertyPanel panel) {
			this.panel=panel;
		}

		@Override
		public void setSelectionCell(Object ob){
			super.setSelectionCell(ob);
			mxCell cell=(mxCell)ob;
			panel.setCell(cell);
		}
		/**
		 * Sets the edge template to be used to inserting edges.
		 */
		public void setEdgeTemplate(Object template) {
			edgeTemplate = template;
		}

		/**
		 * Prints out some useful information about the cell in the tooltip.
		 */
		@Override
		public String getToolTipForCell(Object cell) {
			String tip = "<html>";
			mxGeometry geo = getModel().getGeometry(cell);
			mxCellState state = getView().getState(cell);

			if (getModel().isEdge(cell)) {
				tip += "points={";

				if (geo != null) {
					List<mxPoint> points = geo.getPoints();

					if (points != null) {
						Iterator<mxPoint> it = points.iterator();

						while (it.hasNext()) {
							mxPoint point = it.next();
							tip += "[x=" + numberFormat.format(point.getX())
									+ ",y=" + numberFormat.format(point.getY())
									+ "],";
						}

						tip = tip.substring(0, tip.length() - 1);
					}
				}

				tip += "}<br>";
				tip += "absPoints={";

				if (state != null) {

					for (int i = 0; i < state.getAbsolutePointCount(); i++) {
						mxPoint point = state.getAbsolutePoint(i);
						tip += "[x=" + numberFormat.format(point.getX())
								+ ",y=" + numberFormat.format(point.getY())
								+ "],";
					}

					tip = tip.substring(0, tip.length() - 1);
				}

				tip += "}";
			} else {
				tip += "geo=[";

				if (geo != null) {
					tip += "x=" + numberFormat.format(geo.getX()) + ",y="
							+ numberFormat.format(geo.getY()) + ",width="
							+ numberFormat.format(geo.getWidth()) + ",height="
							+ numberFormat.format(geo.getHeight());
				}

				tip += "]<br>";
				tip += "state=[";

				if (state != null) {
					tip += "x=" + numberFormat.format(state.getX()) + ",y="
							+ numberFormat.format(state.getY()) + ",width="
							+ numberFormat.format(state.getWidth())
							+ ",height="
							+ numberFormat.format(state.getHeight());
				}

				tip += "]";
			}

			mxPoint trans = getView().getTranslate();

			tip += "<br>scale=" + numberFormat.format(getView().getScale())
					+ ", translate=[x=" + numberFormat.format(trans.getX())
					+ ",y=" + numberFormat.format(trans.getY()) + "]";
			tip += "</html>";

			return tip;
		}

		/**
		 * Overrides the method to use the currently selected edge template for
		 * new edges.
		 * 
		 * @param graph
		 * @param parent
		 * @param id
		 * @param value
		 * @param source
		 * @param target
		 * @param style
		 * @return
		 */
		@Override
		public Object createEdge(Object parent, String id, Object value,
				Object source, Object target, String style) {
//			System.out.println("SSSS ");
			if (edgeTemplate != null) {
				mxCell edge = (mxCell) cloneCells(new Object[] { edgeTemplate })[0];
				edge.setId(id);

				return edge;
			}

			return super.createEdge(parent, id, value, source, target, style);
		}

	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		mxConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
		GraphEditor editor = new GraphEditor();
		// BasicGraphEditor editor=new BasicGraphEditor("BesicAdtor",new
		// mxGraphComponent(new mxGraph()));
		editor.createFrame(new EditorMenuBar(editor)).setVisible(true);
		// editor.createFrame(null).setVisible(true);
	}
}
