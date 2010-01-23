package gui;

import gui.editor.BasicGraphEditor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import operation.AutoToXML;
import operation.Utils;

import org.w3c.dom.Document;

import automata.Automata;
import automata.DFA;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;

public class Business {
	private static ModelAgent modelAgent;

	@SuppressWarnings("serial")
	public static class CheckAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			System.out.println("ZHANG " + modelAgent.getGraphMdel());
			DFA dfa = new DFA();
			dfa.importGraph(modelAgent.getGraphMdel());
			Document document = AutoToXML.FAToXML(dfa);
			Utils.StoreDOM(document, "xml\\DFAtest.xml");
		}
	}
	public static String getAutoString(){
		String xml="";
		try{
		DFA dfa = new DFA();
		dfa.importGraph(modelAgent.getGraphMdel());
		Document document = AutoToXML.FAToXML(dfa);
		xml= Utils.DomToString(document);
		}catch(Exception e){
			e.printStackTrace();
		}
		return xml;
	}

	public static void setModelAgent(ModelAgent agent) {
		modelAgent = agent;
	}

	public static void addAutomata(Automata auto){
		modelAgent.addAutomata(auto);
	}
	
}
