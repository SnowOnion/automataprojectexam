package automata;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**************************************************************
 * \ This class used Factory Design pattern to implements the creation of the
 * Automaton with the document parsed by XML.
 * 
 * @author Administrator
 * 
 */
public class AutomatonFactory {

	public static DFA getDFAFromXML(File file) throws java.io.FileNotFoundException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);
			return getDFAFromDocument(doc);
		} catch (java.io.FileNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static DFA getDFAFromDocument(Document doc) {
		DFA dfa = new DFA();
		Element root = doc.getDocumentElement();
		NodeList nodes = root.getChildNodes();
		ArrayList<Element> elements = new ArrayList<Element>();
		// clear the blank characters
		// and get the element list
		for (int i = 0; i < nodes.getLength(); ++i) {
			Node child = nodes.item(i);
			if (child instanceof Element)
				elements.add((Element) nodes.item(i));
		}
		// Start state
		dfa.setStartState(getStartStateName(root));
		// Intermediate states
		NodeList states = getCommonStateNodes(root);
		if (states != null) {
			for (int i = 0; i < states.getLength(); ++i) {
				Element t = (Element) states.item(i);
				dfa.addState(getStateName(t));
			}
		}
		// Final states
		states = getFinalStateNodes(root);
		for (int i = 0; i < states.getLength(); ++i) {
			Element t = (Element) states.item(i);
			dfa.setFinalState(getStateName(t));
		}
		// Transitions
		NodeList transitions = getTransitionNodes(root);
		for (int i = 0; i < transitions.getLength(); i++) {
			Element transition = (Element) transitions.item(i);
			String from = getTransitionSource(transition);
			String to = getTransitionDestination(transition);
			NodeList conditions = getConditionList(transition);
			HashSet<String> cond = new HashSet<String>();
			for (int j = 0; j < conditions.getLength(); ++j) {
				String c = getCondition(conditions.item(j));
				cond.add(c);
			}
			try {
				dfa.addTransition(from, cond, to);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return dfa;
	}

	private static String getStartStateName(Element root) {
		return root.getElementsByTagName("StartState").item(0).getTextContent();
	}
	private static NodeList getCommonStateNodes(Element root) {
		Element e = (Element) root.getElementsByTagName("CommonStates").item(0);
		if (e == null) return null;
		return e.getElementsByTagName("State");
	}
	private static NodeList getFinalStateNodes(Element root) {
		Element e = (Element) root.getElementsByTagName("FinalStates").item(0);
		return e.getElementsByTagName("State");
	}
	private static String getStateName(Node e) {
		return e.getTextContent();
	}
	
	private static NodeList getTransitionNodes(Element root) {
		Element e = (Element) root.getElementsByTagName("Transitions").item(0);
		return e.getElementsByTagName("Transition");
	}
	private static String getTransitionSource(Element node) {
		return getStateName(node.getElementsByTagName("From").item(0));
	}
	private static String getTransitionDestination(Element node) {
		return getStateName(node.getElementsByTagName("To").item(0));
	}
	private static NodeList getConditionList(Element transition) {
		Element e = (Element) transition.getElementsByTagName("Conditions").item(0);
		return e.getElementsByTagName("Condition");
	}
	private static String getCondition(Node node) {
		Element e = (Element) node;
		return e.getTextContent();
	}
}
