package edu.cn.thss.qmy.ndfa.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.cn.thss.qmy.ndfa.core.FA;
import edu.cn.thss.qmy.ndfa.core.State;
import edu.cn.thss.qmy.ndfa.core.Transition;

/**
 * @author Mengyue
 * Read a xml file, parser it to FA structure.
 */
public class FAParser {

	/**
	 * A Naive method via DOM.
	 * @param filePath
	 * @return
	 */
	public static FA NaiveParser(String filePath){
		
		FA fa = new FA();
		Document doc = null;
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder bulider = factory.newDocumentBuilder();
			doc = bulider.parse(filePath);
		} catch (Exception e) {
			return null;
		}
		
		NodeList faList = doc.getElementsByTagName(Globals.AUTOMATA_TAG);
		Element faNode = null;
		if(faList.getLength() > 0){
			faNode = (Element)faList.item(0).getFirstChild();
			fa.setName(faNode.getAttribute(Globals.NAME_ATTR));
		}else{
			return null;
		}
		
		NodeList statesList = faNode.getElementsByTagName(Globals.STATES_TAG);
		NodeList stateList = null;
		if(statesList.getLength() > 0){
			Element statesNode = (Element)statesList.item(0);
			stateList = statesNode.getElementsByTagName(Globals.STATE_TAG);
		}else{
			return null;
		}		
		for(int i = 0; i < stateList.getLength(); i++){
			Element stateNode = (Element)stateList.item(i);
			State state = new State();
			state.setName(stateNode.getTextContent());
			fa.addState(i, state);
		}
		
		NodeList symbolsList = faNode.getElementsByTagName(Globals.SYMBOLS_TAG);
		NodeList symbolList = null;
		if(symbolsList.getLength() > 0){
			Element symbolsNode = (Element)symbolsList.item(0);
			symbolList = symbolsNode.getElementsByTagName(Globals.SYMBOL_TAG);
		}else{
			return null;
		}
		fa.addSymbol(0, Globals.NIL_SYMBOL);
		for(int i = 0; i < symbolList.getLength(); i++){
			Element symbolNode = (Element)symbolList.item(i);
			Character symbol = symbolNode.getTextContent().charAt(0);
			fa.addSymbol(i+1, symbol);
		}
		
		NodeList transitionsList = faNode.getElementsByTagName(Globals.TRANSITIONS_TAG);
		NodeList transitionList = null;
		if(transitionsList.getLength() > 0){
			Element transitionsNode = (Element)transitionsList.item(0);
			transitionList = transitionsNode.getElementsByTagName(Globals.TRANSITION_TAG);
		}else{
			return null;
		}		
		for(int i = 0; i < transitionList.getLength(); i++){
			Element transitionNode = (Element)transitionList.item(i);
			Transition transition = new Transition();
			NodeList tStateList = transitionNode.getElementsByTagName(Globals.STATE_TAG);
			String preState = tStateList.item(0).getTextContent();
			String nextState = tStateList.item(1).getTextContent();
			NodeList tSymbolList = transitionNode.getElementsByTagName(Globals.SYMBOL_TAG);
			Character symbol = tSymbolList.item(0).getTextContent().charAt(0);
			transition.setPreState(fa.getStateKeyByName(preState));
			transition.setNextState(fa.getStateKeyByName(nextState));
			transition.setSymbol(fa.getSymbolKeyBySymbol(symbol));
			fa.addTransition(transition);
		}
		
		NodeList startStatesList = faNode.getElementsByTagName(Globals.START_STATE_TAG);
		NodeList startStateList = null;
		if(startStatesList.getLength() > 0){
			Element startStatesNode = (Element)startStatesList.item(0);
			startStateList = startStatesNode.getElementsByTagName(Globals.STATE_TAG);
			if(startStateList.getLength() > 0){
				String startState = startStateList.item(0).getTextContent();
				fa.setStartState(fa.getStateKeyByName(startState));
			}
		}else{
			return null;
		}
		
		NodeList finalStatesList = faNode.getElementsByTagName(Globals.FINAL_STATES_TAG);
		NodeList finalStateList = null;
		if(finalStatesList.getLength() > 0){
			Element startStatesNode = (Element)finalStatesList.item(0);
			finalStateList = startStatesNode.getElementsByTagName(Globals.STATE_TAG);
			for(int i = 0; i < finalStateList.getLength(); i++){
				Element finalStateNode = (Element)finalStateList.item(i);
				String finalState = finalStateNode.getTextContent();
				fa.addFinalState(fa.getStateKeyByName(finalState));
			}
		}else{
			return null;
		}
		
		return fa;
	}
}
