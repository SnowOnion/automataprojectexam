package xml;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
public class DFADomParser implements AutomatonXmlInterface {
	public DFADomParser(){
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.newDocument();
			
		}catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}
	}
	/***************************************************
	 * Read the Automaton from a document, which is a defined XML 
	 * file.
	 */
	@Override
	public Automaton getAutomatonFromNode(Document doc) {
		// TODO Auto-generated method stub
		Element root = doc.getDocumentElement();
		AutomatonDFA dfa = new AutomatonDFA();
		NodeList nodes = root.getChildNodes();
		ArrayList <Element> elements = new ArrayList<Element>();
		int childIndex = 0;
		Node child = nodes.item(childIndex);
		// This while loop is used to clear the blank characters
		// and get the element list
		while(true){
			if(child instanceof Element){
				elements.add((Element)nodes.item(childIndex));
			}
			childIndex++;
			if(childIndex==nodes.getLength()){
				break;
			}
			child = nodes.item(childIndex);
		}
		Element automatonName=elements.get(0);
		dfa.setAutomatonName(automatonName.getTextContent());
		System.out.println(automatonName.getNodeName()+"\t"+automatonName.getTextContent());
		
		Element stateElements = elements.get(1);
		ArrayList <State> states = new ArrayList<State>();
		NodeList ses = stateElements.getElementsByTagName("State");
		for(int i = 0;i<ses.getLength();i++){
			State newState = getStateFromNode(ses.item(i));
			states.add(newState);
			System.out.println(newState.getStateId()+","+newState.getStateType());
		}
		dfa.setStates(states);
		System.out.println(stateElements.getNodeName());
		System.out.println(ses.getLength());
		
		Element inputSymbols = elements.get(2);
		ArrayList <String> inputSymbolSet = new ArrayList<String>();
		NodeList iss = inputSymbols.getElementsByTagName("InputSymbol");
		for(int i = 0;i<iss.getLength();i++){
			inputSymbolSet.add(iss.item(i).getTextContent());
		}
		dfa.setInputSymbolSet(inputSymbolSet);
		System.out.println(inputSymbols.getNodeName());
		
		
		Element transitionElements = elements.get(3);
		ArrayList<Transition> transitions = new ArrayList <Transition>();
		NodeList tes = transitionElements.getElementsByTagName("DFATransition");
		for(int i = 0;i<tes.getLength();i++){
			TransitionDFA newTransition = getTransitionFromNode(tes.item(i));
			transitions.add(newTransition);
		}
		dfa.setTransitions(transitions);

		System.out.println(transitionElements.getNodeName());
		return dfa;
	}
	@Override
	public State getStateFromNode(Node node) {
		// TODO Auto-generated method stub
		Element state = (Element) node;
		String stateName = state.getElementsByTagName("StateId").item(0).getTextContent();
		String stateType = state.getElementsByTagName("StateType").item(0).getTextContent();
		return new State(stateName,stateType);
	}
	@Override
	public TransitionDFA getTransitionFromNode(Node node) {
		// TODO Auto-generated method stub
		Element newTransition = (Element) node;
		Node fromStateNode=((Element)newTransition.getElementsByTagName("DFATransitionFromState").item(0)).getElementsByTagName("State").item(0);
		State fromState = getStateFromNode(fromStateNode);
		ArrayList <String> conditions = new ArrayList<String>();
		NodeList conditionNodes = ((Element) newTransition.getElementsByTagName("NFAConditions").item(0)).getElementsByTagName("NFACondition");
		for(int i = 0;i<conditionNodes.getLength();i++){
			Node nfaCondition = conditionNodes.item(i);
			conditions.add(nfaCondition.getTextContent());
		}
		Node toStateNode = ((Element)newTransition.getElementsByTagName("DFATransitionToState").item(0)).getElementsByTagName("State").item(0);
		State toState = getStateFromNode(toStateNode);
		return new TransitionDFA(fromState,conditions,toState);
	}

	/******************************************************
	 * Get a document from a automaton, and the document is used
	 * to transformed into a file saved on the disk.
	 */
	
	@Override
	public Element getElementFromAutomaton(Automaton automaton) {
		Element root = doc.createElement("DFA");
			
		Element automatonName = doc.createElement("DFAName");
		automatonName.setTextContent(automaton.getAutomatonName());
		
		Element statesElement = doc.createElement("DFAStates");
		ArrayList <State> states = automaton.getStates(); 
		for(int i = 0;i<states.size();i++){
			Element stateElement = getElementFromState(states.get(i));
			statesElement.appendChild(stateElement);
		}
		Element inputSymbolsElement = doc.createElement("DFAInputSymbols");
		ArrayList <String> inputSymbols = automaton.getInputSymbolSet();
		for(int i = 0;i<inputSymbols.size();i++){
			Element inputSymbolElement = doc.createElement("InputSymbol");
			inputSymbolElement.setTextContent(inputSymbols.get(i));
			inputSymbolsElement.appendChild(inputSymbolElement);
		}
			
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
			
			return root;
	}
	@Override
	public Element getElementFromState(State state) {
		Element stateElement = doc.createElement("State");
			
		Element stateId = doc.createElement("StateId");
		stateId.setTextContent(state.getStateId());
		Element stateType = doc.createElement("StateType");
		stateType.setTextContent(state.getStateType());
		
		stateElement.appendChild(stateId);
		stateElement.appendChild(stateType);
		return stateElement;
	}
	@Override
	public Element getElementFromTransition(Transition transition) {
		Element transitionElement = doc.createElement("DFATransition");
		Element fromState = doc.createElement("DFATransitionFromState");
		fromState.appendChild(getElementFromState(transition.getFromState()));
		Element conditions = doc.createElement("DFAConditions");
		ArrayList <String> tempConditions = ((TransitionDFA) transition).getTransitionConditions();
		for(int i = 0;i<tempConditions.size();i++){
			Element newCondition = doc.createElement("DFACondition");
			newCondition.setTextContent(tempConditions.get(i));
			conditions.appendChild(newCondition);
		}
		Element toState = doc.createElement("DFATransitionToState");
		toState.appendChild(getElementFromState(transition.getToState()));
		
		transitionElement.appendChild(fromState);
		transitionElement.appendChild(conditions);
		transitionElement.appendChild(toState);
		return transitionElement;
	}	


	private Document doc;


}
