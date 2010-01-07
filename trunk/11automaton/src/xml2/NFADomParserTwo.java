package xml2;

import java.util.ArrayList;
import java.util.HashSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import automaton.Automaton;
import automaton.AutomatonNFA;
import automaton.Nail;
import automaton.State;
import automaton.Transition;
import automaton.TransitionNFA;

import exception.NoStateFoundException;

public class NFADomParserTwo extends DomParserParentTwo{
	public NFADomParserTwo(){
		super();
		this.doc = super.doc;
		automaton = new AutomatonNFA();
	}
	/***************************************************
	 * Read the Automaton from a document, which is a defined XML 
	 * file.
	 */
	public Automaton getAutomatonFromNode(Document document) throws NoStateFoundException {
		initBasicAutomatonFromNode(document);
		
		Element transitionElements = elements.get(2);
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		NodeList tes = transitionElements.getElementsByTagName("NFATransition");
		for(int i = 0;i<tes.getLength();i++){
			Transition newTransition = getTransitionFromNode(tes.item(i));
			transitions.add(newTransition);
		}
		automaton.setTransitions(transitions);
		return automaton;
	}
	private Transition getTransitionFromNode(Node node) throws NoStateFoundException{
		Element newTransition = (Element) node;
		Element fromStateNode =(Element) newTransition.getElementsByTagName("FromState").item(0);
		String fromStateId = fromStateNode.getTextContent();
		State fromState = null;
		if(automaton.getStates().containsKey(fromStateId)){
			fromState = automaton.getStates().get(fromStateId);
		}else{
			throw new NoStateFoundException("fromState was not found in state list of the DFA");
		}
		ArrayList <String> conditions = new ArrayList<String>();
		NodeList conditionNodes = ((Element) newTransition.getElementsByTagName("NFAConditions").item(0)).getElementsByTagName("NFACondition");
		for(int i = 0;i<conditionNodes.getLength();i++){
			Node nfaCondition = conditionNodes.item(i);
			conditions.add(nfaCondition.getTextContent());
		}
		Element toStateNode = (Element) newTransition.getElementsByTagName("ToState").item(0);
		String toStateId = toStateNode.getTextContent();
		State toState = null;
		if(automaton.getStates().containsKey(toStateId)){
			toState = automaton.getStates().get(toStateId);
		}else{
			throw new NoStateFoundException("toState was not found in state list of the DFA");
		}
		NodeList nailsElement = newTransition.getElementsByTagName("Nails");
		HashSet <Nail> nails = getTransitionNailsFromNodeList(nailsElement);
		
		TransitionNFA result = new TransitionNFA(fromState,conditions,toState);
		result.setNails(nails);
		return  result;
	}
	
	@Override
	public Document getDocumentFromAutomaton(Automaton automaton) {
		Element root = doc.createElement("NFA");
		
		initBasicRootElementFromAutomaton(root,automaton);
		
		Element transitionsElement = doc.createElement("Transitions");
		ArrayList <Transition> transitions = automaton.getTransitions();
		for(int i = 0;i<transitions.size();i++){
			Element transitionElement = getElementFromTransition(transitions.get(i));
			transitionsElement.appendChild(transitionElement);
		}
		root.appendChild(transitionsElement);
		doc.appendChild(root);
		return doc;
	}
	private Element getElementFromTransition(Transition transition) {
		Element transitionElement = doc.createElement("NFATransition");
		Element fromState = doc.createElement("FromState");
		fromState.setTextContent(transition.getFromState().getStateId());
		Element conditions = doc.createElement("NFAConditions");
		ArrayList <String> tempConditions = ((TransitionNFA) transition).getTransitionConditions();
		for(int i = 0;i<tempConditions.size();i++){
			Element newCondition = doc.createElement("NFACondition");
			newCondition.setTextContent(tempConditions.get(i));
			conditions.appendChild(newCondition);
		}
		Element toState = doc.createElement("ToState");
		toState.setTextContent(transition.getToState().getStateId());
		
		transitionElement.appendChild(fromState);
		transitionElement.appendChild(conditions);
		transitionElement.appendChild(toState);
		return transitionElement;
	}
	private Document doc;
}
