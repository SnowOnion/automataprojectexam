package xml;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import automaton.Automaton;
import automaton.AutomatonConstant;
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
		automaton = new AutomatonDFA();
		//automaton.setAutomatonType("DFA");
		automaton.setAutomatonType(AutomatonConstant.AUTOMATONTYPES[0]);
	}
	/***************************************************
	 * Read the Automaton from a document, which is a defined XML 
	 * file.
	 */
	@Override
	public Automaton getAutomatonFromNode(Document document) {
		// TODO Auto-generated method stub
		Element root = document.getDocumentElement();
		ArrayList <Element> elements = getAllElementsFromRoot(root);
	
		Element automatonName=elements.get(0);
		automaton.setAutomatonName(automatonName.getTextContent());
		//System.out.println(automatonName.getNodeName()+"\t"+automatonName.getTextContent());
		
		Element stateElements = elements.get(1);
		automaton.setStates(getAllStatesFromNode(stateElements));
		
		//System.out.println(stateElements.getNodeName());
		//System.out.println(ses.getLength());
		
		Element inputSymbols = elements.get(2);
		automaton.setInputSymbolSet(getAllInputSymbols(inputSymbols));
		//System.out.println(inputSymbols.getNodeName());
		
		
		Element transitionElements = elements.get(3);
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
	
	private TransitionDFA getTransitionFromNode(Node node) {
		Element newTransition = (Element) node;
		Node fromStateNode=((Element)newTransition.getElementsByTagName("FromState").item(0)).getElementsByTagName("State").item(0);
		State fromState = getStateFromNode(fromStateNode);
		ArrayList <String> conditions = new ArrayList<String>();
		NodeList conditionNodes = ((Element) newTransition.getElementsByTagName("DFAConditions").item(0)).getElementsByTagName("DFACondition");
		for(int i = 0;i<conditionNodes.getLength();i++){
			Node nfaCondition = conditionNodes.item(i);
			conditions.add(nfaCondition.getTextContent());
		}
		Node toStateNode = ((Element)newTransition.getElementsByTagName("ToState").item(0)).getElementsByTagName("State").item(0);
		State toState = getStateFromNode(toStateNode);
		return new TransitionDFA(fromState,conditions,toState);
	}

	/******************************************************
	 * Get a document from a automaton, and the document is used
	 * to transformed into a file saved on the disk.
	 */
	
	@Override
	public Document getDocumentFromAutomaton(Automaton automaton) {
		Element root = doc.createElement("DFA");
			
		Element automatonName = getElementFromAutomatonName(automaton);
		Element statesElement = getElementFromAllStates(automaton);
		
		Element inputSymbolsElement = getElementFromAllInputSymbols(automaton);
			
		Element transitionsElement = doc.createElement("DFATransitions");
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
		Element transitionElement = doc.createElement("DFATransition");
		Element fromState = doc.createElement("FromState");
		fromState.appendChild(getElementFromState(transition.getFromState()));
		Element conditions = doc.createElement("DFAConditions");
		ArrayList <String> tempConditions = ((TransitionDFA) transition).getTransitionConditions();
		for(int i = 0;i<tempConditions.size();i++){
			Element newCondition = doc.createElement("DFACondition");
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
	
}
