package xml;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import automaton.Automaton;
import automaton.AutomatonConstant;
import automaton.AutomatonNFA;
import automaton.State;
import automaton.Transition;
import automaton.TransitionDFA;
import automaton.TransitionNFA;

public class NFADomParser extends DomParserParent {

	public NFADomParser(){
		super();
		automaton = new AutomatonNFA();
		//automaton.setAutomatonType("NFA");
		automaton.setAutomatonType(AutomatonConstant.AUTOMATONTYPES[1]);
	}
	/***************************************************
	 * Read the Automaton from a document, which is a defined XML 
	 * file.
	 */
	public Automaton getAutomatonFromNode(Document doc) {
		// TODO Auto-generated method stub
		Element root = doc.getDocumentElement();
		ArrayList <Element> elements = getAllElementsFromRoot(root);
		
		
		Element automatonName = elements.get(0);
		automaton.setAutomatonName(automatonName.getTextContent());
		
		Element stateElements = elements.get(1);
		automaton.setStates(getAllStatesFromNode(stateElements));
		
		Element inputSymbols = elements.get(2);
		automaton.setInputSymbolSet(getAllInputSymbols(inputSymbols));
		
		Element transitionElements = elements.get(3);
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		NodeList tes = transitionElements.getElementsByTagName("NFATransition");
		for(int i = 0;i<tes.getLength();i++){
			TransitionNFA newTransition = getTransitionFromNode(tes.item(i));
			transitions.add(newTransition);
		}
		automaton.setTransitions(transitions);
		return automaton;
	}
	private TransitionNFA getTransitionFromNode(Node node) {
		// TODO Auto-generated method stub
		Element newTransition = (Element) node;
		Node fromStateNode =((Element) newTransition.getElementsByTagName("FromState").item(0)).getElementsByTagName("State").item(0);
		State fromState = getStateFromNode(fromStateNode);
		ArrayList <String> conditions = new ArrayList<String>();
		NodeList conditionNodes = ((Element) newTransition.getElementsByTagName("NFAConditions").item(0)).getElementsByTagName("NFACondition");
		for(int i = 0;i<conditionNodes.getLength();i++){
			Node nfaCondition = conditionNodes.item(i);
			conditions.add(nfaCondition.getTextContent());
		}
		
		Node toStateNode = ((Element) newTransition.getElementsByTagName("ToState").item(0)).getElementsByTagName("State").item(0);
		State toState = getStateFromNode(toStateNode);
		return  new TransitionNFA(fromState,conditions,toState);
	}

	
	@Override
	public Document getDocumentFromAutomaton(Automaton automaton) {
		Element root = doc.createElement("NFA");
		Element automatonName = getElementFromAutomatonName(automaton);
		Element statesElement = getElementFromAllStates(automaton);
		
		Element inputSymbolsElement = getElementFromAllInputSymbols(automaton);
		
		Element transitionsElement = doc.createElement("NFATransitions");
		ArrayList <Transition> transitions = automaton.getTransitions();
		for(int i = 0;i<transitions.size();i++){
			Element transitionElement = getElementFromTransition(transitions.get(i));
			transitionsElement.appendChild(transitionElement);
		}
		root.appendChild(automatonName);
		root.appendChild(statesElement);
		root.appendChild(inputSymbolsElement);
		root.appendChild(transitionsElement);
		doc.appendChild(root);
		return doc;
	}
	private Element getElementFromTransition(Transition transition) {
		Element transitionElement = doc.createElement("NFATransition");
		Element fromState = doc.createElement("FromState");
		fromState.appendChild(getElementFromState(transition.getFromState()));
		Element conditions = doc.createElement("NFAConditions");
		ArrayList <String> tempConditions = ((TransitionDFA) transition).getTransitionConditions();
		for(int i = 0;i<tempConditions.size();i++){
			Element newCondition = doc.createElement("NFACondition");
			newCondition.setTextContent(tempConditions.get(i));
			conditions.appendChild(newCondition);
		}
		Element toState = doc.createElement("ToState");
		toState.appendChild(getElementFromState(transition.getToState()));
		
		transitionElement.appendChild(fromState);
		transitionElement.appendChild(conditions);
		transitionElement.appendChild(toState);
		return transitionElement;
	}
	private Document doc;
}
