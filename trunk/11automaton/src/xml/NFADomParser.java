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
import automaton.AutomatonNFA;
import automaton.State;
import automaton.Transition;
import automaton.TransitionNFA;

public class NFADomParser implements AutomatonXmlInterface {

	public NFADomParser(){
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.newDocument();
			
		}catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}
	}
	
	@Override
	public Automaton getAutomatonFromNode(Document doc) {
		// TODO Auto-generated method stub
		Element root = doc.getDocumentElement();
		AutomatonNFA nfa = new AutomatonNFA();
		NodeList nodes = root.getChildNodes();
		ArrayList<Element> elements = new ArrayList <Element>();
		int childIndex = 0;
		Node child = nodes.item(childIndex);
		while(true){
			if(child instanceof Element){
				elements.add((Element)nodes.item(childIndex));
			}
			childIndex++;
			if(childIndex == nodes.getLength()){
				break;
			}
			child = nodes.item(childIndex);
		}
		Element automatonName = elements.get(0);
		nfa.setAutomatonName(automatonName.getTextContent());
		
		Element stateElements = elements.get(1);
		ArrayList <State> states = new ArrayList <State>();
		NodeList ses = stateElements.getElementsByTagName("State");
		for(int i = 0;i<ses.getLength();i++){
			State newState = getStateFromNode(ses.item(i));
			states.add(newState);
		}
		nfa.setStates(states);
		
		Element inputSymbols = elements.get(2);
		ArrayList <String> inputSymbolSet = new ArrayList<String>();
		NodeList iss = inputSymbols.getElementsByTagName("InputSymbol");
		for(int i = 0;i<iss.getLength();i++){
			inputSymbolSet.add(iss.item(i).getTextContent());
		}
		nfa.setInputSymbolSet(inputSymbolSet);
		
		Element transitionElements = elements.get(3);
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		NodeList tes = transitionElements.getElementsByTagName("NFATransition");
		for(int i = 0;i<tes.getLength();i++){
			TransitionNFA newTransition = getTransitionFromNode(tes.item(i));
			transitions.add(newTransition);
		}
		nfa.setTransitions(transitions);
		return nfa;
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
	public TransitionNFA getTransitionFromNode(Node node) {
		// TODO Auto-generated method stub
		Element newTransition = (Element) node;
		Node fromStateNode =((Element) newTransition.getElementsByTagName("NFATransitionFromState").item(0)).getElementsByTagName("State").item(0);
		State fromState = getStateFromNode(fromStateNode);
		ArrayList <String> conditions = new ArrayList<String>();
		NodeList conditionNodes = ((Element) newTransition.getElementsByTagName("NFAConditions").item(0)).getElementsByTagName("NFACondition");
		for(int i = 0;i<conditionNodes.getLength();i++){
			Node nfaCondition = conditionNodes.item(i);
			conditions.add(nfaCondition.getTextContent());
		}
		
		Node toStateNode = ((Element) newTransition.getElementsByTagName("NFATransitionToState").item(0)).getElementsByTagName("State").item(0);
		State toState = getStateFromNode(toStateNode);
		return  new TransitionNFA(fromState,conditions,toState);
	}

	
	@Override
	public Element getElementFromAutomaton(Automaton automaton) {
		Element root = doc.createElement("NFA");
		Element automatonName = doc.createElement("NFAName");
		
		Element statesElement = doc.createElement("NFAStates");
		
		Element inputSymbolsElement = doc.createElement("NFAInputSymbols"); 
		ArrayList <String> inputSymbols = automaton.getInputSymbolSet();
		for(int i = 0;i<inputSymbols.size();i++){
			Element inputSymbolElement = doc.createElement("InputSymbol");
			inputSymbolElement.setTextContent(inputSymbols.get(i));
			inputSymbolsElement.appendChild(inputSymbolElement);
		}
		
		Element transitionsElement = doc.createElement("NFATransitions");
		return root;
	}

	@Override
	public Element getElementFromState(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getElementFromTransition(Transition transition) {
		// TODO Auto-generated method stub
		return null;
	}
	private Document doc;
}
