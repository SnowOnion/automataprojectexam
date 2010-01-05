package cn.tsinghua.mse.automatondesigner.exporttoxml;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.swt.graphics.Point;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.PDATransCondition;
import cn.tsinghua.mse.automatondesigner.dataobject.PushdownAutomaton;
import cn.tsinghua.mse.automatondesigner.dataobject.TransCondition;
import cn.tsinghua.mse.automatondesigner.dataobject.TransFunction;
import cn.tsinghua.mse.automatondesigner.exception.NoStateFoundException;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Circle_State;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Graphic_MiddleAutomaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Polyline_Trans;

public class PDADomParser extends DomBaseParser {
	public PDADomParser() {
		super();
		this.doc = super.doc;
		gAutomaton = new Graphic_MiddleAutomaton(new PushdownAutomaton(),
				new ArrayList<Polyline_Trans>(), new ArrayList<Circle_State>());
	}

	@Override
	public Graphic_MiddleAutomaton getAutomatonFromNode(Document document)
			throws NoStateFoundException {
		initBasicAutomatonFromNode(document);

		Element stackSymbolElements = elements.get(2);
		((PushdownAutomaton) gAutomaton.getAutomaton())
				.setM_StackSymbols(getAllStackSymbolsFromNode(stackSymbolElements));

		Element initialStackSymbolElement = elements.get(3);
		String initalStack = initialStackSymbolElement.getTextContent();
		((PushdownAutomaton) gAutomaton.getAutomaton())
				.setM_InitStackSymbol(initalStack);

		Element transitionElements = elements.get(4);
		NodeList tes = transitionElements.getElementsByTagName("PDATransition");
		for (int i = 0; i < tes.getLength(); i++) {
			Polyline_Trans newTransition = getTransitionFromNode(tes.item(i));
			gAutomaton.getAutomaton().addTransFunction(newTransition.getTransFunc());
			gAutomaton.getpTrans().add(newTransition);
		}
		return gAutomaton;
	}

	private ArrayList<String> getAllStackSymbolsFromNode(Element stackSymbols) {
		ArrayList<String> stackSymbolSet = new ArrayList<String>();
		NodeList iss = stackSymbols.getElementsByTagName("PDAStackSymbol");
		for (int i = 0; i < iss.getLength(); i++) {
			stackSymbolSet.add(iss.item(i).getTextContent());
		}
		return stackSymbolSet;
	}

	private Polyline_Trans getTransitionFromNode(Node node)
			throws NoStateFoundException {
		Polyline_Trans pTrans = new Polyline_Trans();
		Element newTransition = (Element) node;
		Element fromStateNode = (Element) newTransition.getElementsByTagName(
				"FromState").item(0);
		String fromStateId = fromStateNode.getTextContent();
		Circle_State beginState = gAutomaton.getCStateByName(fromStateNode
				.getTextContent());
		if (beginState != null) {
			pTrans.setBeginCircle(beginState);
		} else {
			throw new NoStateFoundException(
					"begin state was not found in state list");
		}
		ArrayList<TransCondition> conditions = new ArrayList<TransCondition>();
		NodeList conditionNodes = ((Element) newTransition
				.getElementsByTagName("PDAConditions").item(0))
				.getElementsByTagName("PDACondition");
		for (int i = 0; i < conditionNodes.getLength(); i++) {
			Node pdaCondition = conditionNodes.item(i);
			conditions.add(getTransitionPDAConditionFromNode(pdaCondition));
		}
		Element toStateNode = (Element) newTransition.getElementsByTagName(
				"ToState").item(0);
		Circle_State endState = gAutomaton.getCStateByName(toStateNode
				.getTextContent());
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

	/**
	 * 从XML文件节点获取PDA的转移条件
	 * @param node XML文件的节点
	 * @return PDA的转移条件对象
	 */
	private PDATransCondition getTransitionPDAConditionFromNode(Node node) {
		Element newPDACondition = (Element) node;
		String conditionSymbol = ((Element) newPDACondition
				.getElementsByTagName("PDAConditionSymbol").item(0))
				.getTextContent();
		String fromStackSymbol = ((Element) newPDACondition
				.getElementsByTagName("PDAFromStackSymbol").item(0))
				.getTextContent();
		ArrayList<String> toStackSymbols = new ArrayList<String>();
		NodeList toStackNodes = ((Element) newPDACondition
				.getElementsByTagName("PDAToStackSymbol").item(0))
				.getElementsByTagName("PDAStackSymbol");
		for (int i = 0; i < toStackNodes.getLength(); i++) {
			Node stackTemp = toStackNodes.item(i);
			toStackSymbols.add(stackTemp.getTextContent());
		}
		return new PDATransCondition(conditionSymbol, fromStackSymbol,
				toStackSymbols);
	}

	
	@Override
	public Document getDocumentFromAutomaton(Graphic_MiddleAutomaton gAutomaton) {
		Element root = doc.createElement("PDA");

		initBasicRootElementFromAutomaton(root, gAutomaton);

		Element stackSymbolsElement = getElementFromAllStackSymbols((PushdownAutomaton)gAutomaton.getAutomaton());
		Element initialStackSymbol = doc.createElement("InitialStackSymbol");
		initialStackSymbol.setTextContent(((PushdownAutomaton) gAutomaton.getAutomaton())
				.getM_InitStackSymbol());

		Element transitionsElement = doc.createElement("Transitions");
		ArrayList<Polyline_Trans> transitions = gAutomaton.getpTrans();
		for (int i = 0; i < transitions.size(); i++) {
			Element transitionElement = getElementFromTransition(transitions
					.get(i));
			transitionsElement.appendChild(transitionElement);
		}
		root.appendChild(stackSymbolsElement);
		root.appendChild(initialStackSymbol);
		root.appendChild(transitionsElement);

		doc.appendChild(root);
		return doc;
	}

	private Element getElementFromAllStackSymbols(PushdownAutomaton automaton) {
		Element stackSymbolsElement = doc.createElement("PDAStackSymbols");
		ArrayList<String> stackSymbols = automaton.getM_StackSymbols();
		for (int i = 0; i < stackSymbols.size(); i++) {
			Element stackSymbolElement = doc.createElement("PDAStackSymbol");
			stackSymbolElement.setTextContent(stackSymbols.get(i));
			stackSymbolsElement.appendChild(stackSymbolElement);
		}
		return stackSymbolsElement;
	}

	private Element getElementFromTransition(Polyline_Trans transition) {
		Element transitionElement = doc.createElement("PDATransition");
		Element fromState = doc.createElement("FromState");
		fromState.setTextContent(transition.getBeginCircle().getM_State().getM_Name());

		Element conditions = doc.createElement("PDAConditions");
		ArrayList<TransCondition> tempConditions = transition.getTransFunc().getM_TransCondition();
		for (int i = 0; i < tempConditions.size(); i++) {
			Element newCondition = getElementFromTransitionPDACondition((PDATransCondition) tempConditions
					.get(i));
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

	private Element getElementFromTransitionPDACondition(
			PDATransCondition pdaCondition) {
		Element pdaConditionElement = doc.createElement("PDACondition");

		Element conditionSymbol = doc.createElement("PDAConditionSymbol");
		conditionSymbol.setTextContent(pdaCondition.getM_InputSymbol());

		Element fromStackElement = doc.createElement("PDAFromStackSymbol");
		fromStackElement.setTextContent(pdaCondition.getM_OldStackSymbol());
		Element toStacksElement = doc.createElement("PDAToStackSymbol");
		ArrayList<String> toStacks = pdaCondition.getM_NewStackSymbol();
		for (int i = 0; i < toStacks.size(); i++) {
			Element tempStack = doc.createElement("PDAStackSymbol");
			tempStack.setTextContent(toStacks.get(i));
			toStacksElement.appendChild(tempStack);
		}
		pdaConditionElement.appendChild(conditionSymbol);
		pdaConditionElement.appendChild(fromStackElement);
		pdaConditionElement.appendChild(toStacksElement);
		return pdaConditionElement;
	}

}
