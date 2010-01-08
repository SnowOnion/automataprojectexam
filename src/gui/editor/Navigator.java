package gui.editor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;

import com.mxgraph.view.mxGraph;

public class Navigator extends JPanel {
	private BasicGraphEditor editor;
	Set<Item> items = new LinkedHashSet<Item>();

	public Navigator(BasicGraphEditor editor) {
		this.editor=editor;
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new GridLayout(10, 1));
	}
	public void addItem(String modelName, mxGraph graph) {
		Item newItem = new Item(modelName, graph);
		items.add(newItem);
		this.add(newItem);
	}

	public void removeItem(String modelName) {
		Iterator<Item> iter = items.iterator();
		while (iter.hasNext()) {
			if (iter.next().getModelName().equals(modelName)) {
				iter.remove();
				return;
			}
		}
	}

	private class Item extends JLabel {
		private String modelName;
		private mxGraph graph;

		public Item(String modelName, mxGraph graph) {
			super(modelName);
			this.modelName = modelName;
			this.graph = graph;
			this.setPreferredSize(new Dimension((int) (Navigator.this
					.getWidth() * 0.8), 100));
			this.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					editor.getGraphComponent().setGraph(getGraph());
				}

			});
		}

		public String getModelName() {
			return modelName;
		}

		public mxGraph getGraph() {
			return graph;
		}
	}
}
