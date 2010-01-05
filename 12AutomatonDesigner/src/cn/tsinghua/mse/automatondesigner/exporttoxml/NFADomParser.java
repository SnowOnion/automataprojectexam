package cn.tsinghua.mse.automatondesigner.exporttoxml;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.swt.graphics.Point;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.TransCondition;
import cn.tsinghua.mse.automatondesigner.dataobject.TransFunction;
import cn.tsinghua.mse.automatondesigner.exception.NoStateFoundException;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Circle_State;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Graphic_MiddleAutomaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Polyline_Trans;

public class NFADomParser extends DomBaseParser {
	public NFADomParser() {
		super();
		this.doc = super.doc;
		gAutomaton = new Graphic_MiddleAutomaton(new Automaton(),
				new ArrayList<Polyline_Trans>(), new ArrayList<Circle_State>());
	}

	/**
	 * 从XML文件获取图形化自动机信息
	 */
	public Graphic_MiddleAutomaton getAutomatonFromNode(Document document)
			throws NoStateFoundException {
		initBasicAutomatonFromNode(document);

		Element transitionElements = elements.get(2);
		NodeList tes = transitionElements.getElementsByTagName("NFATransition");
		for (int i = 0; i < tes.getLength(); i++) {
			Polyline_Trans newTransition = getTransitionFromNode(tes.item(i));
			gAutomaton.getpTrans().add(newTransition);
			gAutomaton.getAutomaton().addTransFunction(newTransition.getTransFunc());
		}
		return gAutomaton;
	}

	private Polyline_Trans getTransitionFromNode(Node node)
			throws NoStateFoundException {
		Polyline_Trans pTrans = new Polyline_Trans();
		Element newTransition = (Element) node;
		Element fromStateNode = (Element) newTransition.getElementsByTagName(
				"FromState").item(0);
		Circle_State beginState = gAutomaton.getCStateByName(fromStateNode.getTextContent());
		if (beginState != null) {
			pTrans.setBeginCircle(beginState);
		} else {
			throw new NoStateFoundException(
					"begin state was not found in state list");
		}
		ArrayList<TransCondition> conditions = new ArrayList<TransCondition>();
		NodeList conditionNodes = ((Element) newTransition
				.getElementsByTagName("NFAConditions").item(0))
				.getElementsByTagName("NFACondition");
		for (int i = 0; i < conditionNodes.getLength(); i++) {
			Node nfaCondition = conditionNodes.item(i);
			conditions.add(new TransCondition(nfaCondition.getTextContent()));
		}
		Element toStateNode = (Element) newTransition.getElementsByTagName(
				"ToState").item(0);
		Circle_State endState = gAutomaton.getCStateByName(toStateNode.getTextContent());
		if (endState != null) {
			pTrans.setEndCircle(endState);
		} else {
			throw new NoStateFoundException(
					"end state was not found in state list");
		}

		Element polylineElement = (Element)newTransition.getElementsByTagName("Polyline").item(0);
		NodeList nailsElement = polylineElement.getElementsByTagName("Nail");
		ArrayList<Point> nails = getTransitionNailsFromNodeList(nailsElement);

		TransFunction func = new TransFunction(beginState.getM_State(), endState.getM_State(), conditions);
		pTrans.setPolyLine(nails);
		pTrans.setTransFunc(func);
		return pTrans;
	}

	@Override
	public Document getDocumentFromAutomaton(Graphic_MiddleAutomaton gAutomaton) {
		Element root = doc.createElement("NFA");

		initBasicRootElementFromAutomaton(root, gAutomaton);

		Element transitionsElement = doc.createElement("Transitions");
		ArrayList<Polyline_Trans> transitions = gAutomaton.getpTrans();
		for (int i = 0; i < transitions.size(); i++) {
			Element transitionElement = getElementFromTransition(transitions
					.get(i));
			transitionsElement.appendChild(transitionElement);
		}
		root.appendChild(transitionsElement);
		doc.appendChild(root);
		return doc;
	}

	private Element getElementFromTransition(Polyline_Trans transition) {
		Element transitionElement = doc.createElement("NFATransition");
		Element fromState = doc.createElement("FromState");
		fromState.setTextContent(transition.getBeginCircle().getM_State().getM_Name());
		Element conditions = doc.createElement("NFAConditions");
		ArrayList<TransCondition> tempConditions = transition.getTransFunc().getM_TransCondition();
		for (int i = 0; i < tempConditions.size(); i++) {
			Element newCondition = doc.createElement("NFACondition");
			newCondition.setTextContent(tempConditions.get(i).getM_InputSymbol());
			conditions.appendChild(newCondition);
		}
		Element toState = doc.createElement("ToState");
		toState.setTextContent(transition.getEndCircle().getM_State().getM_Name());
		Element polyline = getDocumentFromPolyLine(transition.getPolyLine());

		transitionElement.appendChild(fromState);
		transitionElement.appendChild(conditions);
		transitionElement.appendChild(toState);
		transitionElement.appendChild(polyline);
		return transitionElement;
	}

	private Document doc;
}
