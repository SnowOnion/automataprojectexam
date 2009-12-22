package xml2;

import java.util.ArrayList;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import automaton.Automaton;
import automaton.AutomatonPDA;
import automaton.Nail;
import automaton.State;
import automaton.Transition;
import automaton.TransitionPDA;
import automaton.TransitionPDACondition;
import exception.NoStateFoundException;

public class PDADomParserTwo extends DomParserParentTwo{
	public PDADomParserTwo(){
		super();
		this.doc = super.doc;
		automaton = new AutomatonPDA();
	}
	@Override
	public Automaton getAutomatonFromNode(Document document) throws NoStateFoundException {
		initBasicAutomatonFromNode(document);
		
		Element stackSymbolElements = elements.get(2);
		((AutomatonPDA)automaton).setStackSymbols(getAllStackSymbolsFromNode(stackSymbolElements));
		
		Element initialStackSymbolElement = elements.get(3);
		String initalStack = initialStackSymbolElement.getTextContent();
		((AutomatonPDA)automaton).setInitialStackSymbol(initalStack);
		
		Element transitionElements = elements.get(4);
		ArrayList<Transition> transitions = new ArrayList <Transition>();
		NodeList tes = transitionElements.getElementsByTagName("PDATransition");
		for(int i = 0;i<tes.getLength();i++){
			TransitionPDA newTransition = getTransitionFromNode(tes.item(i));
			transitions.add(newTransition);
		}
		automaton.setTransitions(transitions);

		//System.out.println(transitionElements.getNodeName());
		return automaton;
	}
	
	private ArrayList <String> getAllStackSymbolsFromNode(Element stackSymbols){
		ArrayList <String> stackSymbolSet = new ArrayList<String>();
		NodeList iss = stackSymbols.getElementsByTagName("PDAStackSymbol");
		for(int i = 0;i<iss.getLength();i++){
			stackSymbolSet.add(iss.item(i).getTextContent());
		}
		return stackSymbolSet;
	}
	private TransitionPDA getTransitionFromNode(Node node) throws NoStateFoundException {
		Element newTransition = (Element) node;
		Element fromStateNode=(Element)newTransition.getElementsByTagName("FromState").item(0);
		String fromStateId = fromStateNode.getTextContent();
		State fromState = null;
		if(automaton.getStates().containsKey(fromStateId)){
			fromState = automaton.getStates().get(fromStateId);
				
		}else{
			throw new NoStateFoundException("fromState was not found in state list of the DFA");
		}
		ArrayList <TransitionPDACondition> conditions = new ArrayList<TransitionPDACondition>();
		NodeList conditionNodes = ((Element) newTransition.getElementsByTagName("PDAConditions").item(0)).getElementsByTagName("DFACondition");
		for(int i = 0;i<conditionNodes.getLength();i++){
			Node pdaCondition = conditionNodes.item(i);
			conditions.add(getTransitionPDAConditionFromNode(pdaCondition));
		}
		Element toStateNode = (Element)newTransition.getElementsByTagName("ToState").item(0);
		String toStateId = toStateNode.getTextContent();
		State toState = null;
		if(automaton.getStates().containsKey(toStateId)){
			toState = automaton.getStates().get(toStateId);
		}else{
			throw new NoStateFoundException("toState was not found in state list of the DFA");
		}
		NodeList nailsElement = newTransition.getElementsByTagName("Nails");
		HashSet <Nail> nails = getTransitionNailsFromNodeList(nailsElement);
		
		TransitionPDA result = new TransitionPDA(fromState,conditions,toState);
		result.setNails(nails);
		return result;
	}
	
	/*******************************************************
	 * This method will be used in getTransitionFromNode(Node node) 
	 * which make the code structure more easy to understand
	 * @param node
	 * @return
	 */
	private TransitionPDACondition getTransitionPDAConditionFromNode(Node node){
		Element newPDACondition = (Element) node;
		String conditionSymbol = ((Element)newPDACondition.getElementsByTagName("PDAConditionSymbol").item(0)).getTextContent();
		String fromStackSymbol = ((Element)newPDACondition.getElementsByTagName("PDAFromStackSymbol").item(0)).getTextContent();
		ArrayList<String> toStackSymbols = new ArrayList <String>();
		NodeList toStackNodes = ((Element)newPDACondition.getElementsByTagName("PDAToStackSymbol").item(0)).getElementsByTagName("PDAStackSymbol");
		for(int i = 0;i<toStackNodes.getLength();i++){
			Node stackTemp = toStackNodes.item(i);
			toStackSymbols.add(stackTemp.getTextContent());
		}
		return new TransitionPDACondition(conditionSymbol,fromStackSymbol,toStackSymbols);
	}

	
	/**********************************************************
	 * The methods below are used for building a Document for 
	 * an automaton. And then such document could be saved into
	 * an XML file.
	 */
	@Override
	public Document getDocumentFromAutomaton(Automaton automaton) {
		Element root = doc.createElement("PDA");
		
		initBasicRootElementFromAutomaton(root,automaton);
		
		Element stackSymbolsElement = getElementFromAllStackSymbols(automaton);
		Element initialStackSymbol = doc.createElement("InitialStackSymbol");
		initialStackSymbol.setTextContent(((AutomatonPDA)automaton).getInitialStackSymbol());
		
		Element transitionsElement = doc.createElement("Transitions");
		ArrayList <Transition> transitions = automaton.getTransitions();
		for(int i = 0;i<transitions.size();i++){
			Element transitionElement = getElementFromTransition((TransitionPDA)transitions.get(i));
			transitionsElement.appendChild(transitionElement);
		}
		root.appendChild(stackSymbolsElement);
		root.appendChild(initialStackSymbol);
		root.appendChild(transitionsElement);
			
		doc.appendChild(root);
		return doc;
	}
	private Element getElementFromAllStackSymbols(Automaton automaton){
		Element stackSymbolsElement = doc.createElement("PDAStackSymbols");
		ArrayList <String> inputSymbols = automaton.getInputSymbolSet();
		for(int i = 0;i<inputSymbols.size();i++){
			Element stackSymbolElement = doc.createElement("PDAStackSymbol");
			stackSymbolElement.setTextContent(inputSymbols.get(i));
			stackSymbolsElement.appendChild(stackSymbolElement);
		}
		return stackSymbolsElement;
	}
	private Element getElementFromTransition(TransitionPDA transition) {
		Element transitionElement = doc.createElement("PDATransition");
		Element fromState = doc.createElement("FromState");
		fromState.setTextContent(transition.getFromState().getStateId());
		
		Element conditions = doc.createElement("PDAConditions");
		ArrayList <TransitionPDACondition> tempConditions = ((TransitionPDA) transition).getTransitionConditions();
		for(int i = 0;i<tempConditions.size();i++){
			Element newCondition = getElementFromTransitionPDACondition(tempConditions.get(i));
			conditions.appendChild(newCondition);
		}
		Element toState = doc.createElement("ToState");
		toState.setTextContent(transition.getToState().getStateId());
		
		transitionElement.appendChild(fromState);
		transitionElement.appendChild(conditions);
		transitionElement.appendChild(toState);
		return transitionElement;
	}
	
	private Element getElementFromTransitionPDACondition(TransitionPDACondition pdaCondition){
		Element pdaConditionElement = doc.createElement("PDACondition");
		
		Element conditionSymbol = doc.createElement("PDAConditionSymbol");
		conditionSymbol.setTextContent(pdaCondition.getCondition());
		
		Element fromStackElement = doc.createElement("PDAFromStackSymbol");
		fromStackElement.setTextContent(pdaCondition.getFromStackSymbol());
		Element toStacksElement = doc.createElement("PDAToStackSymbol");
		ArrayList <String> toStacks = pdaCondition.getToSymbols();
		for(int i = 0;i<toStacks.size();i++){
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
