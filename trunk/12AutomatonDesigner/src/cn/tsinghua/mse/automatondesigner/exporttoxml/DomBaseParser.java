package cn.tsinghua.mse.automatondesigner.exporttoxml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.swt.graphics.Point;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import automatondesigner.SystemConstant;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.State;
import cn.tsinghua.mse.automatondesigner.exception.NoStateFoundException;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Circle_State;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Graphic_MiddleAutomaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Polyline_Trans;
import cn.tsinghua.mse.automatondesigner.interfaces.IAutomatonXmlConvertor;

public abstract class DomBaseParser implements IAutomatonXmlConvertor {
	protected Document doc;
	protected Graphic_MiddleAutomaton gAutomaton;
	protected ArrayList<Element> elements;

	public DomBaseParser() {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = builder.newDocument();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
	}


	protected void initBasicAutomatonFromNode(Document document)
			throws NoStateFoundException {
		Element root = document.getDocumentElement();
		gAutomaton.getAutomaton().setM_Name(root.getAttribute("name"));
		gAutomaton.getAutomaton().setTypeByStr(root.getAttribute("type"));

		elements = getAllElementsFromRoot(root);

		Element stateElements = elements.get(0);
		gAutomaton.setcStates(getAllStatesFromNode(stateElements));
		for (Circle_State c : gAutomaton.getcStates()){
			gAutomaton.getAutomaton().addState(c.getM_State());
		}
		
		State initialState = gAutomaton.getAutomaton().getStateByName(root.getAttribute("initialState"));
		gAutomaton.getAutomaton().setM_StartState(initialState);

		Element inputSymbols = elements.get(1);
		gAutomaton.getAutomaton().setM_InputSymbols(getAllInputSymbols(inputSymbols));

	}
	
	public byte getAutomatonType(Document document){
		byte type = -1;
		Element root = document.getDocumentElement();
		String strType = root.getAttribute("type");
		if (strType.equals(SystemConstant.AUTOMATON_TYPE_PDA)){
			type =  AutomatonConst.AUTOMATON_TYPE_PDA;
		}
		else if (strType.equals(SystemConstant.AUTOMATON_TYPE_DFA)){
			type =  AutomatonConst.AUTOMATON_TYPE_DFA;
		}
		else if (strType.equals(SystemConstant.AUTOMATON_TYPE_NFA)){
			type =  AutomatonConst.AUTOMATON_TYPE_NFA;
		}
		return type;
	}

	protected Circle_State getStateFromNode(Node node) {
		State s = new State();
		Element state = (Element) node;
		s.setM_Name(state.getElementsByTagName("StateId").item(0)
				.getTextContent());
		s.setM_type(Integer.valueOf(state.getElementsByTagName("StateType").item(0)
				.getTextContent()).byteValue());
		Element nailElement = (Element) state.getElementsByTagName("Nail")
				.item(0);
		String nailX = nailElement.getAttribute("x");
		String nailY = nailElement.getAttribute("y");
		Point nail = new Point(Integer.valueOf(nailX), Integer.valueOf(nailY));
		return new Circle_State(s, nail);
	}

	protected ArrayList<Point> getTransitionNailsFromNodeList(
			NodeList nailsElement) {
		ArrayList<Point> nails = new ArrayList<Point>();
		if (nailsElement.getLength() > 0) {
			for (int i = 0; i < nailsElement.getLength(); i++) {
				Element nailTemp = (Element) nailsElement.item(i);
				nails.add(new Point(
						Integer.valueOf(nailTemp.getAttribute("x")), Integer
								.valueOf(nailTemp.getAttribute("y"))));
			}
		}
		return nails;
	}
	
	protected Element getDocumentFromPolyLine(ArrayList<Point> polyline){
		Element polylineElement = doc.createElement("Polyline");
		for (Point ptn : polyline) {
			Element pointElement = doc.createElement("Nail");
			pointElement.setAttribute("x", String.valueOf(ptn.x));
			pointElement.setAttribute("y", String.valueOf(ptn.y));
			polylineElement.appendChild(pointElement);
		}
		return polylineElement;
	}


	protected ArrayList<Element> getAllElementsFromRoot(Element root) {
		NodeList nodes = root.getChildNodes();
		ArrayList<Element> elements = new ArrayList<Element>();
		int childIndex = 0;
		Node child = nodes.item(childIndex);
		// This while loop is used to clear the blank characters
		// and get the element list
		while (true) {
			if (child instanceof Element) {
				elements.add((Element) nodes.item(childIndex));
			}
			childIndex++;
			if (childIndex == nodes.getLength()) {
				break;
			}
			child = nodes.item(childIndex);
		}
		return elements;
	}

	protected ArrayList<Circle_State> getAllStatesFromNode(Element stateElements) {
		ArrayList<Circle_State> states = new ArrayList<Circle_State>();
		NodeList ses = stateElements.getElementsByTagName("State");
		for (int i = 0; i < ses.getLength(); i++) {
			Circle_State newState = getStateFromNode(ses.item(i));
			states.add(newState);
		}
		return states;
	}

	/**
	 * 获取所有的输入符号
	 * @param inputSymbols
	 * @return
	 */
	protected ArrayList<String> getAllInputSymbols(Element inputSymbols) {
		ArrayList<String> inputSymbolSet = new ArrayList<String>();
		NodeList iss = inputSymbols.getElementsByTagName("InputSymbol");
		for (int i = 0; i < iss.getLength(); i++) {
			inputSymbolSet.add(iss.item(i).getTextContent());
		}
		return inputSymbolSet;
	}

	protected void initBasicRootElementFromAutomaton(Element root,
			Graphic_MiddleAutomaton gAutomaton) {
		Element statesElement = getElementFromAllStates(gAutomaton);
		Element inputSymbolsElement = getElementFromAllInputSymbols(gAutomaton.getAutomaton());

		root.setAttribute("initialState", gAutomaton.getAutomaton().getStartStateName());
		root.setAttribute("name", gAutomaton.getAutomaton().getM_Name());
		root.setAttribute("type", gAutomaton.getAutomaton().getStrType());

		root.appendChild(statesElement);
		root.appendChild(inputSymbolsElement);
	}

	protected Element getElementFromAllStates(Graphic_MiddleAutomaton automaton) {
		Element statesElement = doc.createElement("States");
		ArrayList<Circle_State> cStates = automaton.getcStates();
		for (Circle_State s : cStates) {
			Element stateElement = getElementFromState(s);
			statesElement.appendChild(stateElement);
		}
		return statesElement;
	}

	protected Element getElementFromAllInputSymbols(Automaton automaton) {
		Element inputSymbolsElement = doc.createElement("InputSymbols");
		ArrayList<String> inputSymbols = automaton.getM_InputSymbols();
		for (int i = 0; i < inputSymbols.size(); i++) {
			Element inputSymbolElement = doc.createElement("InputSymbol");
			inputSymbolElement.setTextContent(inputSymbols.get(i));
			inputSymbolsElement.appendChild(inputSymbolElement);
		}
		return inputSymbolsElement;
	}

	protected Element getElementFromState(Circle_State state) {
		Element stateElement = doc.createElement("State");
		Element stateId = doc.createElement("StateId");
		stateId.setTextContent(state.getM_State().getM_Name());
		Element stateType = doc.createElement("StateType");
		stateType.setTextContent(String.valueOf(state.getM_State().getM_type()));
		Element location = doc.createElement("Nail");
		location.setAttribute("x", String.valueOf(state.getCentre().x));
		location.setAttribute("y", String.valueOf(state.getCentre().y));
		
		stateElement.appendChild(stateId);
		stateElement.appendChild(stateType);
		stateElement.appendChild(location);
		return stateElement;
	}

	public void writeDocumentToFile(Document document, File file) {
		try {
			TransformerFactory transfactory = TransformerFactory.newInstance();
			Transformer transformer = transfactory.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty("encoding", "UTF-8");
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		}

	}
}
