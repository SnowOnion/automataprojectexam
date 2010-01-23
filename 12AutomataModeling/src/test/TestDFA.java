package test;

import java.io.*;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import operation.*;
import automata.*;
import junit.framework.TestCase;

public class TestDFA extends TestCase {
	public void AutoToXML() {
		NFA dfa = new NFA();

		dfa.addState("q1");
		dfa.addState("q2");
		dfa.addState("q3");
		dfa.addState("q4");
		dfa.addState("q5");
		dfa.addState("q6");
		dfa.addState("q7");

		dfa.addSymbol('a');
		dfa.addSymbol('b');

		dfa.setStartQ("q1");

		dfa.addFinalState("q7");
		dfa.addFinalState("q6");

		dfa.addTransfer("q1", Automata.EPSILON, "q2");
		dfa.addTransfer("q1", Automata.EPSILON, "q4");
		dfa.addTransfer("q2", Automata.EPSILON, "q3");
		dfa.addTransfer("q3", Automata.EPSILON, "q6");
		dfa.addTransfer("q5", Automata.EPSILON, "q7");
		dfa.addTransfer("q6", Automata.EPSILON, "q1");
		dfa.addTransfer("q4", 'a', "q5");
		dfa.addTransfer("q5", 'b', "q6");
		
		Document document = AutoToXML.FAToXML(dfa);
		Utils.StoreDOM(document,"xml\\NFA1.auto");
		
		System.out.println("Finished!");
	}
	public void testParser(){
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse("xml\\NFA1.auto");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		List<Automata> Automatas=Parser.DOMToAutomatas(document);
		
		int i=0;
		for(Automata dfa:Automatas){
			Document doc = AutoToXML.FAToXML(dfa);
			Utils.StoreDOM(doc,"ttt"+(i++)+".auto");			
		}
	}
	public void DFA(){
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse("NFA1.auto");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		List<Automata> Automatas=Parser.DOMToAutomatas(document);
		Automata dfa=Automatas.get(0);
		
		String[] inputs=new String[]{
		"a",
		"ababababababababababababababababababababb"
		};

/*		"11111111111111111111111111111111100000000000000000000000000000100001010101001",
		"111111111111111111111111111111",
		"a",
		"01"	
*/		for(int i=0;i<inputs.length;i++){
			dfa.init();
			System.out.println(dfa.acceptString(inputs[i]));
		}
	}
}
