package xml;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import exception.NoStateFoundException;
import automaton.Automaton;
import automaton.AutomatonDFA;
import automaton.State;
import automaton.Transition;
import automaton.TransitionDFA;
/**********************************************************
 * This Class Use org.w3c.dom to parse the XML files 
 * which implements the AutomatonXmlInterface.
 * 
 * not completed........
 * 
 * @author Administrator
 *
 */
public class DFADomParser extends DomParserParent {
	public DFADomParser(){
		super();
		this.doc = super.doc;
		automaton = new AutomatonDFA();
	}
	/***************************************************
	 * Read the Automaton from a document, which is a defined XML 
	 * file.
	 */
	@Override
	public Automaton getAutomatonFromNode(Document document) throws NoStateFoundException{
		Element root = document.getDocumentElement();
		ArrayList <Element> elements = getAllElementsFromRoot(root);
	
		initBasicAutomatonFromNode(document);
		
		Element transitionElements = elements.get(4);
		ArrayList<Transition> transitions = new ArrayList <Transition>();
		NodeList tes = transitionElements.getElementsByTagName("DFATransition");
		for(int i = 0;i<tes.getLength();i++){
			TransitionDFA newTransition = getTransitionFromNode(tes.item(i));
			transitions.add(newTransition);
		}
		automaton.setTransitions(transitions);

		//System.out.println(transitionElements.getNodeName());
		return automaton;
	}
	
	private TransitionDFA getTransitionFromNode(Node node) throws NoStateFoundException{
		Element newTransition = (Element) node;
		Element fromStateNode=(Element)newTransition.getElementsByTagName("FromState").item(0);
		String fromStateId = fromStateNode.getTextContent();
		State fromState = null;
		if(automaton.getStates().containsKey(fromStateId)){
			fromState = automaton.getStates().get(fromStateId);
				
		}else{
			throw new NoStateFoundException("fromState was not found in state list of the DFA");
		}
		ArrayList <String> conditions = new ArrayList<String>();
		NodeList conditionNodes = ((Element) newTransition.getElementsByTagName("DFAConditions").item(0)).getElementsByTagName("DFACondition");
		for(int i = 0;i<conditionNodes.getLength();i++){
			Node nfaCondition = conditionNodes.item(i);
			conditions.add(nfaCondition.getTextContent());
		}
		Element toStateNode = (Element)newTransition.getElementsByTagName("ToState").item(0);
		String toStateId = toStateNode.getTextContent();
		State toState = null;
		if(automaton.getStates().containsKey(toStateId)){
			toState = automaton.getStates().get(toStateId);
		}else{
			throw new NoStateFoundException("toState was not found in state list of the DFA");
		}
		return new TransitionDFA(fromState,conditions,toState);
	}

	/******************************************************
	 * Get a document from a automaton, and the document is used
	 * to transformed into a file saved on the disk.
	 */
	
	@Override
	public Document getDocumentFromAutomaton(Automaton automaton) {
		Element root = doc.createElement("DFA");
		
		initBasicRootElementFromAutomaton(root,automaton);
				
		Element transitionsElement = doc.createElement("DFATransitions");
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
		Element transitionElement = doc.createElement("DFATransition");
		Element fromState = doc.createElement("FromState");
		fromState.setTextContent(transition.getFromState().getStateId());
		Element conditions = doc.createElement("DFAConditions");
		ArrayList <String> tempConditions = ((TransitionDFA) transition).getTransitionConditions();
		for(int i = 0;i<tempConditions.size();i++){
			Element newCondition = doc.createElement("DFACondition");
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
	
}
