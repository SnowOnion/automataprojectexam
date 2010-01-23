package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import automata.State;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PropertyPanel extends JPanel {
	private JPanel editor;
	private ModelAgent modelAgent;
	private Map<mxCell,Object> graphMap;
	private mxGraphModel model;

	public PropertyPanel() {
		editor = new AutomataPropertyEditor();
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new BorderLayout());
		
		this.add(editor);
	}

	public void setModelAgent(mxGraphModel model,ModelAgent modelAgent) {
		this.model=model;
		this.modelAgent = modelAgent;
		graphMap=modelAgent.getGraphMap();
	}

	public void setCell(mxCell cell) {
		this.remove(editor);
		if (cell == null)
			editor = new AutomataPropertyEditor();
		else if (cell.isVertex()) {
			editor = new VertexPropertyEditor(cell);
		} else if (cell.isEdge()) {
			editor = new EdgePropertyEditor(cell);
		}
		this.add(editor);
	}

	class VertexPropertyEditor extends JPanel {
		protected mxCell cell;
		private mxState state;
		
		private int lastValue1=0;
		private int lastValue2=0;

		private JTextField idField = new JTextField();
		private JButton idButton = new JButton("OK");
		private JComboBox startBox = new JComboBox(new String[] { "No", "Yes" });
		private JComboBox finalBox = new JComboBox(new String[] { "No", "Yes" });

		public VertexPropertyEditor(final mxCell cell) {
			this.cell = cell;
			state = (mxState)graphMap.get(cell) ;

			idField.setPreferredSize(new Dimension(70, 30));
			idField.setText(state.getStateId());
			JLabel label1 = new JLabel("Id:");
			label1.setPreferredSize(new Dimension(50, 30));
			idButton.setPreferredSize(new Dimension(30, 30));
			JPanel p1 = new JPanel();
			// p1.setLayout(new BorderLayout());
			p1.add(label1);
			p1.add(idField);
			p1.add(idButton);
			p1.setBorder(BorderFactory.createLineBorder(Color.white));

			startBox.setPreferredSize(new Dimension(110, 30));
			JLabel label2 = new JLabel("Set Start:");
			label2.setPreferredSize(new Dimension(50, 30));
			JPanel p2 = new JPanel();
			// p2.setLayout(new BorderLayout());
			p2.add(label2);
			p2.add(startBox);
			p2.setBorder(BorderFactory.createLineBorder(Color.white));

			finalBox.setPreferredSize(new Dimension(110, 30));
			JLabel label3 = new JLabel("Set Final:");
			label3.setPreferredSize(new Dimension(50, 30));
			JPanel p3 = new JPanel();
			// p3.setLayout(new BorderLayout());
			p3.add(label3);
			p3.add(finalBox);
			p3.setBorder(BorderFactory.createLineBorder(Color.white));

			this.setLayout(new GridLayout(6, 1));
			this.add(p1);
			this.add(p2);
			this.add(p3);
			
			idField.setText(state.getStateId());
			if(state.getStyle()==State.START_S||state.getStyle()==State.START_FINAL_S)
				startBox.setSelectedIndex(1);
			if(state.getStyle()==State.FINAL_S||state.getStyle()==State.START_FINAL_S)
				finalBox.setSelectedIndex(1);

			idButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					state.setStateId(idField.getText());
					model.setValue(cell,idField.getText());
				}
			});
			startBox.addPropertyChangeListener(new PropertyChangeListener(){
				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					if(startBox.getSelectedIndex()!=lastValue1){
//						System.out.println(startBox.getSelectedIndex());	
						if(lastValue1==0&&(modelAgent.getStartState()!=null&&modelAgent.getStartState()!=state)){
//							JOptionPane.showMessageDialog(null, "初始状态必须唯一！");
							JOptionPane.showConfirmDialog(VertexPropertyEditor.this,"是否确定更改初始状态？","确定更改",JOptionPane.OK_CANCEL_OPTION);
						}else{
							if(lastValue1==1){
								modelAgent.setStartState(null);
							}else{
								modelAgent.setStartState(state);
							}
							lastValue1=startBox.getSelectedIndex();
							refresh();
						}
					}
				}		
			});
			finalBox.addPropertyChangeListener(new PropertyChangeListener(){
				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					if(finalBox.getSelectedIndex()!=lastValue2){	
						lastValue2=finalBox.getSelectedIndex();
						refresh();					
					}
				}		
			});

		}
		private void refresh(){
			if(lastValue1==0&&lastValue2==0){
				model.setStyle(cell,"ellipse");
				state.setStyle(State.OTHER_S);
			}
			else if(lastValue1==0&&lastValue2==1){
				model.setStyle(cell,"ellipse;shape=doubleEllipse");
				state.setStyle(State.FINAL_S);
			}
			else if(lastValue1==1&&lastValue2==0){
				model.setStyle(cell,"ellipse;fillColor=blue");
				state.setStyle(State.START_S);
			}
			else{
				model.setStyle(cell,"ellipse;shape=doubleEllipse;fillColor=blue");
				state.setStyle(State.START_FINAL_S);
			}
		}
	}

	class EdgePropertyEditor extends JPanel {
		protected mxCell cell;
		private mxTransition tran;
		
		private JTextField symbolField = new JTextField();
		private JButton idButton = new JButton("OK");
		
		public EdgePropertyEditor(final mxCell cell) {
			this.cell = cell;
			tran=(mxTransition)graphMap.get(cell);
			
			symbolField.setPreferredSize(new Dimension(70, 30));
			symbolField.setText(tran.getInputChar()+"");
			JLabel label1 = new JLabel("Symbol:");
			label1.setPreferredSize(new Dimension(50, 30));
			idButton.setPreferredSize(new Dimension(30, 30));
			JPanel p1 = new JPanel();
			// p1.setLayout(new BorderLayout());
			p1.add(label1);
			p1.add(symbolField);
			p1.add(idButton);
			p1.setBorder(BorderFactory.createLineBorder(Color.white));

			this.setLayout(new GridLayout(6, 1));
			this.add(p1);
			
			idButton.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					String text=symbolField.getText();
					if(text.length()>1){
						
					}else{
						model.setValue(cell,text);
						tran.setInputChar(mxTransition.stringToChar(text));
					}
				}
			});
		}
	}

	class AutomataPropertyEditor extends JPanel {
		protected mxCell cell;
		private JComboBox automataType = new JComboBox(new String[] { "DFA",
				"NFA", "PDA" });
		private JButton symbolSetButton = new JButton("Manage");

		public AutomataPropertyEditor() {
			automataType.setPreferredSize(new Dimension(110, 30));
			JLabel label1 = new JLabel("Type:");
			label1.setPreferredSize(new Dimension(50, 30));
			JPanel p1 = new JPanel();
			// p1.setLayout(new BorderLayout());
			p1.add(label1);
			p1.add(automataType);
			p1.setBorder(BorderFactory.createLineBorder(Color.white));

			symbolSetButton.setPreferredSize(new Dimension(110, 30));
			JLabel label2 = new JLabel("Symbols:");
			label2.setPreferredSize(new Dimension(50, 30));
			JPanel p2 = new JPanel();
			// p2.setLayout(new BorderLayout());
			p2.add(label2);
			p2.add(symbolSetButton);
			p2.setBorder(BorderFactory.createLineBorder(Color.white));
			this.setLayout(new GridLayout(6, 1));
			this.add(p1);
			this.add(p2);

			symbolSetButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JDialog dialog = new SymbolDialog();
					dialog.setVisible(true);
				}
			});
		}

		private class SymbolDialog extends JDialog {
			private JTable table;

			public SymbolDialog() {
				table = new JTable(new Object[][] { { "aa", "bb" },
						{ "ww", "ee" } }, new Object[] { "a", "b" });
				this.add(table);
				this.setSize(500, 300);
			}
		}
	}
}
