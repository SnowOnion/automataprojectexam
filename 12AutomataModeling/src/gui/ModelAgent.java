package gui;

import gui.editor.BasicGraphEditor;
import gui.editor.EditorMenuBar;
import gui.editor.EditorPalette;

import automata.Automata;
import automata.State;
import automata.Transition;

import com.mxgraph.model.*;
import java.awt.Color;
import java.awt.Point;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
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

public class ModelAgent {

	private mxIGraphModel model;
	private mxGraph graph;

	private Set lastModel = new LinkedHashSet();
	private Map<String, Object> curModel;
	private Map<mxCell, Object> graphMap = new HashMap<mxCell, Object>();
	private mxState startState;
	private Change change;

	private List<Change> vertex_add;
	private List<Change> vertex_remove;
	private List<Change> vertex_id_change;

	private List<Change> vertex_edge_add;
	private List<Change> vertex_edge_remove;

	private List<Change> edge_add;
	private List<Change> edge_remove;
	private List<Change> edge_symbol_change;

	private List<Change> edge_source_add;
	private List<Change> edge_source_remove;
	private List<Change> edge_source_change;

	private List<Change> edge_target_add;
	private List<Change> edge_target_remove;
	private List<Change> edge_target_change;

	private int count = 0;

	ModelAgent(mxGraph graph) {
		this.graph = graph;
		this.model = graph.getModel();
		model.addListener(mxEvent.CHANGE, change_listaner);
	}

	public Set getGraphMdel() {
		return lastModel;
	}

	public void addAutomata(Automata auto) {
		Collection cells = auto.export();

		Object parent = graph.getDefaultParent();

		int i=0;
		Map<State,Object> states=new HashMap<State,Object>();
		for (Object object : cells) {
			if (object instanceof State) {
				State state = (State) object;

				graph.getModel().beginUpdate();

				Object cell=graph.insertVertex(parent, null, state.getStateId(), (i%4)*200, (i/4+1)*300,
						160, 160,"ellipse");
				i++;		
				graph.getModel().endUpdate();
				
				states.put(state, cell);
			} else {
				Transition tran=(Transition) object;
				State source=(State)tran.getBeginState();
				State target=(State)tran.getEndState();
				
				Object sourceVertex=states.get(source);
				Object targetVertex=states.get(target);
				char c=tran.getInputChar();
				graph.getModel().beginUpdate();
				graph.insertEdge(parent, null, c+"", sourceVertex, targetVertex,"straight");
				graph.getModel().endUpdate();
			}
		}
	}

	private mxIEventListener change_listaner = new mxIEventListener() {
		public void invoke(Object sender, mxEventObject evt) {
			mxGraphModel model1 = (mxGraphModel) model;
			curModel = model1.getCells();

			vertex_add = new LinkedList<Change>();
			vertex_remove = new LinkedList<Change>();
			vertex_id_change = new LinkedList<Change>();
			vertex_edge_add = new LinkedList<Change>();
			vertex_edge_remove = new LinkedList<Change>();
			edge_add = new LinkedList<Change>();
			edge_remove = new LinkedList<Change>();
			edge_symbol_change = new LinkedList<Change>();
			edge_source_add = new LinkedList<Change>();
			edge_source_remove = new LinkedList<Change>();
			edge_source_change = new LinkedList<Change>();
			edge_target_add = new LinkedList<Change>();
			edge_target_remove = new LinkedList<Change>();
			edge_target_change = new LinkedList<Change>();

			boolean match1 = forwardCheck();
			boolean match2 = backwardCheck();
			while (!(match1 && match2)) {
				updateModel();

				vertex_add = new LinkedList<Change>();
				vertex_remove = new LinkedList<Change>();
				vertex_id_change = new LinkedList<Change>();
				vertex_edge_add = new LinkedList<Change>();
				vertex_edge_remove = new LinkedList<Change>();
				edge_add = new LinkedList<Change>();
				edge_remove = new LinkedList<Change>();
				edge_symbol_change = new LinkedList<Change>();
				edge_source_add = new LinkedList<Change>();
				edge_source_remove = new LinkedList<Change>();
				edge_source_change = new LinkedList<Change>();
				edge_target_add = new LinkedList<Change>();
				edge_target_remove = new LinkedList<Change>();
				edge_target_change = new LinkedList<Change>();
				match1 = forwardCheck();
				match2 = backwardCheck();
			}
			/*
			 * for (String name : curModel.keySet()) { mxCell ce = (mxCell)
			 * curModel.get(name); if (ce.isEdge()) { System.out.print(name +
			 * " :Edge " + ce + ":"); System.out.println(ce.getSource() + " " +
			 * ce.getTarget()); } else if (ce.isVertex()) {
			 * System.out.print(name + " :Vertex ");
			 * System.out.print(ce.getEdgeCount() + ":"); for (int i = 0; i <
			 * ce.getEdgeCount(); i++) { System.out.print(ce.getEdgeAt(i) +
			 * ","); } System.out.println(); mxState state = new mxState(ce);
			 * ce.setValue(state); } else { System.out.print(name + " :Other");
			 * } System.out.println(" " + ce.getValue()); } if
			 * (!baseCell.isEdge()) { System.out.println(baseCell.getId());
			 * System.out.println(baseCell.getChildCount()); }
			 * System.out.println("YYYYYYYYYYYYTTTTTTTTT" +
			 * model1.getRoot().getClass());
			 */
		}
	};

	public boolean forwardCheck() {
		boolean match = true;
		for (Object object : lastModel) {
			if (object instanceof mxState) {
				mxState state = (mxState) object;
				mxCell cell = state.getCell();
				if (!curModel.containsValue(cell)) {
					System.out.println("from Model Delete Vertex:" + cell);// ///
					vertex_remove.add(new Change(Change.VERTEX_REMOVED, cell));
					match = false;
				} else {
					/*
					 * if(state.getStateId().equals(cell.getValue())){
					 * System.out.println("Vertex Id change:" + cell);// ///
					 * vertex_id_change.add(new Change(Change.VERTEX_ID_CHANGE,
					 * cell)); match=false; }
					 */
					Set<mxTransition> trans = state.getTrans();
					// 正向比较
					for (mxTransition t1 : trans) {
						mxCell temp = t1.getCell();
						int i = 0;
						for (; i < cell.getEdgeCount(); i++) {
							if (temp == cell.getEdgeAt(i)) {
								break;
							}
						}
						if (i >= cell.getEdgeCount()) {
							System.out.println("from Vertex Delete Edge:"
									+ temp);
							vertex_edge_remove.add(new Change(
									Change.VERTEX_EDGE_REMVE, cell, temp));
							match = false;
						}
					}
					// 反向比较
					for (int i = 0; i < cell.getEdgeCount(); i++) {
						mxCell edge = (mxCell) cell.getEdgeAt(i);
						Object s = graphMap.get(edge);
						boolean exsit = false;
						for (mxTransition t1 : trans) {
							if (s == t1) {
								exsit = true;
								break;
							}
						}
						if (!exsit) {
							System.out.println("from Vertex Add Edge:" + edge);
							vertex_edge_add.add(new Change(
									Change.VERTEX_EDGE_ADD, cell, edge));
							match = false;
						}
					}
				}
			} else {
				mxTransition tran = (mxTransition) object;
				mxCell cell = tran.getCell();
				if (!curModel.containsValue(cell)) {
					System.out.println("from Model Delete Edge:" + cell);// ///
					edge_remove.add(new Change(Change.EDGE_REMOVE, cell));
					match = false;
				} else {
					/*
					 * if(!cell.getValue().toString().startsWith(""+tran.getInputChar
					 * ())){ System.out.println("Edge inputchar change:" +
					 * cell);// /// edge_symbol_change.add(new
					 * Change(Change.EDGE_SYMBOL_CHANGE, cell)); match=false; }
					 */
					// 当输入串发生改变
					mxState source1 = (mxState) tran.getBeginState();
					mxState target1 = (mxState) tran.getEndState();

					mxCell source2 = (mxCell) cell.getSource();
					mxCell target2 = (mxCell) cell.getTarget();

					if ((source1 == null) && (source2 == null)) {

					} else if (source1 == null) {
						System.out.println("atach to a source Vertex:"
								+ source2);
						edge_source_add.add(new Change(Change.EDGE_SOURCE_ADD,
								cell, source2));
						match = false;
					} else if (source2 == null) {
						System.out.println("away from a source Vertex:"
								+ source2);
						edge_source_remove.add(new Change(
								Change.EDGE_SOURCE_REMOVE, cell, source1
										.getCell()));
						match = false;
					} else {
						if (source1.getCell() != source2) {
							System.out.println("source Vettex varied:"
									+ source1.getCell() + "," + source2);
							edge_source_change.add(new Change(
									Change.EDGE_SOURCE_CHANGE, cell, source1
											.getCell()));
							match = false;
						}
					}

					if ((target1 == null) && (target2 == null)) {

					} else if (target1 == null) {
						System.out.println("atach to a target Vertex:"
								+ target2);
						edge_target_add.add(new Change(Change.EDGE_TARGET_ADD,
								cell, target2));
						match = false;
					} else if (target2 == null) {
						System.out.println("away from a target Vertex:"
								+ target2);
						edge_target_remove.add(new Change(
								Change.EDGE_TARGET_REMOVE, cell, target1
										.getCell()));
						match = false;
					} else {
						if (target1.getCell() != target2) {
							System.out.println("target Vettex varied:"
									+ target1.getCell() + "," + target2);
							edge_target_change.add(new Change(
									Change.EDGE_TARGET_CHANGE, cell, target1
											.getCell()));
							match = false;
						}
					}

				}

			}
		}
		return match;
	}

	public boolean backwardCheck() {
		boolean match = true;
		Collection cellSet = curModel.values();
		for (Object object : cellSet) {
			mxCell cell = (mxCell) object;
			if (cell.isVertex()) {
				Object user = graphMap.get(cell);
				if (user == null) {
					System.out.println("add a new Vertex:" + cell);
					vertex_add.add(new Change(Change.VERTEX_ADD, cell));
					match = false;
				} else {
				}
			} else if (cell.isEdge()) {
				Object user = graphMap.get(cell);
				if (user == null) {
					System.out.println("add a new Edge:" + cell);
					edge_add.add(new Change(Change.EDGE_ADD, cell));
					match = false;
				} else {
				}
			}
		}
		return match;
	}

	private void updateModel() {
		Collection cellSet = curModel.values();
		for (Change change : vertex_edge_remove) {
			mxCell cell = change.getBaseCell();
			mxCell relatedCell = change.getRelatedCell();

			mxState state = (mxState) graphMap.get(cell);
			mxTransition tran = (mxTransition) graphMap.get(relatedCell);

			state.removeTransfer(tran);

		}
		for (Change change : edge_source_remove) {
			mxCell cell = change.getBaseCell();
			mxCell relatedCell = change.getRelatedCell();

			mxTransition tran = (mxTransition) graphMap.get(cell);
			mxState state = (mxState) graphMap.get(relatedCell);

			tran.setBeginState(null);
		}
		for (Change change : edge_target_remove) {
			mxCell cell = change.getBaseCell();
			mxCell relatedCell = change.getRelatedCell();

			mxTransition tran = (mxTransition) graphMap.get(cell);
			mxState state = (mxState) graphMap.get(relatedCell);

			tran.setEndState(null);

		}
		for (Change change : edge_remove) {
			mxCell cell = change.getBaseCell();
			mxTransition tran = (mxTransition) graphMap.get(cell);

			lastModel.remove(tran);
			graphMap.remove(cell);
		}
		for (Change change : vertex_remove) {
			mxCell cell = change.getBaseCell();
			mxState state = (mxState) graphMap.get(cell);

			lastModel.remove(state);
			graphMap.remove(cell);
		}
		for (Change change : vertex_add) {
			mxCell cell = change.getBaseCell();
			mxState state = new mxState(cell);

			lastModel.add(state);
			graphMap.put(cell, state);
		}
		for (Change change : edge_add) {
			mxCell cell = change.getBaseCell();
			mxTransition tran = new mxTransition(cell);

			lastModel.add(tran);
			graphMap.put(cell, tran);
		}
		List<Change> changes = new LinkedList();
		for (Change change : vertex_edge_add) {
			mxCell cell = change.getBaseCell();
			mxCell relatedCell = change.getRelatedCell();

			mxState state = (mxState) graphMap.get(cell);
			mxTransition tran = (mxTransition) graphMap.get(relatedCell);

			state.addTransfer(tran.getInputChar(), tran);
		}
		for (Change change : edge_source_add) {
			mxCell cell = change.getBaseCell();
			mxCell relatedCell = change.getRelatedCell();

			mxTransition tran = (mxTransition) graphMap.get(cell);
			mxState state = (mxState) graphMap.get(relatedCell);

			tran.setBeginState(state);
		}
		for (Change change : edge_source_change) {
			mxCell cell = change.getBaseCell();
			mxCell relatedCell = change.getRelatedCell();

			mxTransition tran = (mxTransition) graphMap.get(cell);
			mxState state = (mxState) graphMap.get(relatedCell);

			tran.setBeginState(state);
		}
		for (Change change : edge_target_add) {
			mxCell cell = change.getBaseCell();
			mxCell relatedCell = change.getRelatedCell();

			mxTransition tran = (mxTransition) graphMap.get(cell);
			mxState state = (mxState) graphMap.get(relatedCell);

			tran.setEndState(state);
		}
		for (Change change : edge_target_change) {
			mxCell cell = change.getBaseCell();
			mxCell relatedCell = change.getRelatedCell();

			mxTransition tran = (mxTransition) graphMap.get(cell);
			mxState state = (mxState) graphMap.get(relatedCell);

			tran.setEndState(state);
		}
		for (Change change : vertex_id_change) {
			mxCell cell = change.getBaseCell();
			mxState state = (mxState) graphMap.get(cell);
			state.setStateId(cell.getValue().toString());
		}
		for (Change change : edge_symbol_change) {
			mxCell cell = change.getBaseCell();
			mxTransition tran = (mxTransition) graphMap.get(cell);
			Object s = cell.getValue();
			if (s != null && s.toString().length() > 0)
				tran.setInputChar(s.toString().charAt(0));
		}

		/*
		 * for (Object object : cellSet) { mxCell baseCell = (mxCell) object; if
		 * (baseCell.isVertex()) { mxState state = new mxState(baseCell);
		 * state.setStateId("" + count++); lastModel.add(state);
		 * graphMap.put(baseCell, state); } else if (baseCell.isEdge()) {
		 * mxTransition tran = new mxTransition(baseCell); lastModel.add(tran);
		 * graphMap.put(baseCell, tran); } }
		 * 
		 * for (Object object : cellSet) { mxCell baseCell = (mxCell) object; if
		 * (baseCell.isVertex()) { mxState state = (mxState)
		 * graphMap.get(baseCell); for (int i = 0; i < baseCell.getEdgeCount();
		 * i++) { mxCell edge = (mxCell) baseCell.getEdgeAt(i);
		 * state.addTransfer(' ', (mxTransition) graphMap.get(edge)); } } else
		 * if (baseCell.isEdge()) { mxTransition tran = (mxTransition)
		 * graphMap.get(baseCell); mxCell source = (mxCell)
		 * baseCell.getSource(); mxCell target = (mxCell) baseCell.getTarget();
		 * if (source != null) tran.setBeginState((mxState)
		 * graphMap.get(source)); if (target != null) tran.setEndState((mxState)
		 * graphMap.get(target)); } }
		 */

		/*
		 * if (change != null) { mxCell baseCell = change.getCell(); switch
		 * (change.getType()) { case Change.VERTEX_ADD: { mxState state = new
		 * mxState(baseCell); baseCell.setValue(state); lastModel.add(state);
		 * break; } case Change.EDGE_ADD: { mxTransition tran = new
		 * mxTransition(baseCell); baseCell.setValue(tran); lastModel.add(tran);
		 * break; } case Change.VERTEX_REMOVED: {
		 * lastModel.remove(baseCell.getValue()); break; } case
		 * Change.EDGE_REMOVE: { lastModel.remove(baseCell.getValue()); break; }
		 * case Change.EDGE_SOURCE_CHANGE: { mxTransition tran = (mxTransition)
		 * baseCell.getValue(); mxCell source=(mxCell)baseCell.getSource();
		 * if(source==null){ tran.setBeginState(null); }else{
		 * tran.setBeginState((mxState)source.getValue()); } break; } case
		 * Change.EDGE_TARGET_CHANGE: { mxTransition tran = (mxTransition)
		 * baseCell.getValue(); mxCell target=(mxCell)baseCell.getTarget();
		 * if(target==null){ tran.setEndState(null); }else{
		 * tran.setEndState((mxState)target.getValue()); } break; } default: {
		 * 
		 * break; } } change = null; }
		 */
	}

	public Map<mxCell, Object> getGraphMap() {
		return graphMap;
	}

	public mxState getStartState() {
		return startState;
	}

	public void setStartState(mxState startState) {
		this.startState = startState;
	}

	public mxGraph getGraph() {
		return graph;
	}

	public void setGraph(mxGraph graph) {
		this.graph = graph;
	}

	private class Change {
		public static final int VERTEX_ADD = 1;
		public static final int VERTEX_REMOVED = 2;
		public static final int VERTEX_ID_CHANGE = 100;
		public static final int VERTEX_EDGE_ADD = 3;
		public static final int VERTEX_EDGE_REMVE = 4;

		public static final int EDGE_ADD = 5;
		public static final int EDGE_REMOVE = 6;
		public static final int EDGE_SYMBOL_CHANGE = 101;
		public static final int EDGE_SOURCE_ADD = 7;
		public static final int EDGE_SOURCE_REMOVE = 8;
		public static final int EDGE_SOURCE_CHANGE = 9;
		public static final int EDGE_TARGET_ADD = 10;
		public static final int EDGE_TARGET_REMOVE = 11;
		public static final int EDGE_TARGET_CHANGE = 12;
		// public static final int EDGE_INPUT_CHANGE = 7;

		private int type;

		private mxCell baseCell;
		private mxCell relatedCell;

		public Change(int type, mxCell cell) {
			this.type = type;
			this.baseCell = cell;
		}

		public Change(int type, mxCell baseCell, mxCell relatedCell) {
			this.type = type;
			this.baseCell = baseCell;
			this.relatedCell = relatedCell;
		}

		public int getType() {
			return type;
		}

		public mxCell getBaseCell() {
			return baseCell;
		}

		public mxCell getRelatedCell() {
			return relatedCell;
		}
	}
}
