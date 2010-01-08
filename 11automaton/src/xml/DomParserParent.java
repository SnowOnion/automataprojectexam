package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import automaton.Automaton;
import automaton.AutomatonConstant;
import automaton.AutomatonXmlInterface;
import automaton.State;

import exception.NoStateFoundException;

public class DomParserParent implements AutomatonXmlInterface {
	protected Document doc;
	protected Automaton automaton;
	
	public DomParserParent(){
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.newDocument();
		}catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}
	}
	/**********************************************************
	 * READ OPERATION
	 * Get an automaton from a document which is generated by an
	 * XML file.
	 */
	public Automaton getAutomatonFromNode(Document document) throws NoStateFoundException{
		// TODO Auto-generated method stub
		return null;
	}
	/**********************************************************************
	 * initialize the basic content of an automaton including
	 * automatonName, state, initialState,inputSymbols
	 * @param document
	 * @throws NoStateFoundException
	 */
	protected void initBasicAutomatonFromNode(Document document) throws NoStateFoundException{
		Element root = document.getDocumentElement();
		ArrayList <Element> elements = getAllElementsFromRoot(root);
		
		Element automatonName=elements.get(0);
		automaton.setAutomatonName(automatonName.getTextContent());
		//System.out.println(automatonName.getNodeName()+"\t"+automatonName.getTextContent());
		
		Element stateElements = elements.get(1);
		automaton.setStates(getAllStatesFromNode(stateElements));
		
		Element initialStateElement = elements.get(2);
		String stateId = initialStateElement.getTextContent();
		
		State initialState = null;
		if(automaton.getStates().containsKey(stateId)){
			initialState = automaton.getStates().get(stateId);
		}else{
			throw new NoStateFoundException("Initial State was not found in state list of the DFA");
		}
		automaton.setInitialState(initialState);
		
		Element inputSymbols = elements.get(3);
		automaton.setInputSymbolSet(getAllInputSymbols(inputSymbols));
		//System.out.println(inputSymbols.getNodeName());
		
	}
	protected State getStateFromNode(Node node) {
		Element state = (Element) node;
		String stateName = state.getElementsByTagName("StateId").item(0).getTextContent();
		String stateTypeString = state.getElementsByTagName("StateType").item(0).getTextContent();
		byte stateType = AutomatonConstant.getStateTypeByteFromString(stateTypeString);
		return new State(stateName,stateType);
	}
	/*************************************************************
	 * 
	 * @param stateElements
	 * @return
	 */
	protected ArrayList<Element> getAllElementsFromRoot(Element root){
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
		return elements;
	}
	protected HashMap<String,State> getAllStatesFromNode(Element stateElements){
		HashMap<String,State> states = new HashMap<String,State>();
		NodeList ses = stateElements.getElementsByTagName("State");
		for(int i = 0;i<ses.getLength();i++){
			State newState = getStateFromNode(ses.item(i));
			states.put(newState.getStateId(),newState);
			//System.out.println(newState.getStateId()+","+newState.getStateType());
		}
		return states;
	}
	protected ArrayList<String> getAllInputSymbols(Element inputSymbols){
		ArrayList <String> inputSymbolSet = new ArrayList<String>();
		NodeList iss = inputSymbols.getElementsByTagName("InputSymbol");
		for(int i = 0;i<iss.getLength();i++){
			inputSymbolSet.add(iss.item(i).getTextContent());
		}
		return inputSymbolSet;
	}
	/**********************************************************
	 * WRITE OPERATION
	 * Get a document used to write XML file from an automaton
	 **********************************************************/
	public Document getDocumentFromAutomaton(Automaton automaton) {
		// diffrent automaton will have different implementation
		return null;
	}
	protected void initBasicRootElementFromAutomaton(Element root,Automaton automaton){
		Element automatonName = getElementFromAutomatonName(automaton);
		Element statesElement = getElementFromAllStates(automaton);
		Element initialStateElement = doc.createElement("InitialState");
		initialStateElement.setTextContent(automaton.getInitialState().getStateId());
		Element inputSymbolsElement = getElementFromAllInputSymbols(automaton);
		root.appendChild(automatonName);
		root.appendChild(statesElement);
		root.appendChild(initialStateElement);
		root.appendChild(inputSymbolsElement);
	}
	protected Element getElementFromAutomatonName(Automaton automaton){
		Element automatonName = doc.createElement("AutomatonName");
		automatonName.setTextContent(automaton.getAutomatonName());
		return automatonName;
	}
	protected Element getElementFromAllStates(Automaton automaton){
		Element statesElement = doc.createElement("AutomatonStates");
		HashMap<String,State> states = automaton.getStates();
		Collection <State> stateList = states.values();
		Iterator <State>stateIterator = stateList.iterator();
		while(stateIterator.hasNext()){
			Element stateElement = getElementFromState(stateIterator.next());
			statesElement.appendChild(stateElement);
		}
		return statesElement;
	}
	protected Element getElementFromAllInputSymbols(Automaton automaton){
		Element inputSymbolsElement = doc.createElement("AutomatonInputSymbols");
		ArrayList <String> inputSymbols = automaton.getInputSymbolSet();
		for(int i = 0;i<inputSymbols.size();i++){
			Element inputSymbolElement = doc.createElement("InputSymbol");
			inputSymbolElement.setTextContent(inputSymbols.get(i));
			inputSymbolsElement.appendChild(inputSymbolElement);
		}
		return inputSymbolsElement;
	}
	protected Element getElementFromState(State state) {
		Element stateElement = doc.createElement("State");
		Element stateId = doc.createElement("StateId");
		stateId.setTextContent(state.getStateId());
		Element stateType = doc.createElement("StateType");
		
		byte stateTypeByte = state.getStateType();
		stateType.setTextContent(AutomatonConstant.STATETYPES[stateTypeByte]);
		
		stateElement.appendChild(stateId);
		stateElement.appendChild(stateType);
		return stateElement;
	}

	
	public void writeDocumentToFile(Document document,File file){
		try{
			TransformerFactory transfactory = TransformerFactory.newInstance();
			Transformer transformer = transfactory.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty("encoding","UTF-8");
			StreamResult result = new StreamResult(file);
			transformer.transform(source,result);
		}catch(TransformerConfigurationException tce){
			tce.printStackTrace();
		}catch(TransformerException te){
			te.printStackTrace();
		}
		
	}
}
