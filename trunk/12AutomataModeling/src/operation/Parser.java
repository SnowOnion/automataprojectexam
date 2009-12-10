package operation;

import java.util.*;

import org.w3c.dom.*;

import automata.*;

public class Parser {
	public static List<Automata> DOMToAutomatas(Document doc){
		Element AutomatasE=doc.getDocumentElement();
		
		NodeList Automatas=AutomatasE.getChildNodes();
		int len=Automatas.getLength();
		List<Automata> lst=new LinkedList<Automata>();
		for(int i=0;i<len;i++){
			Node AutomataN=Automatas.item(i);
			Automata automata=DomToAuto(AutomataN);
			lst.add(automata);
		}

		return lst;
	}
	private static Automata DomToAuto(Node AutoN){
		String label=AutoN.getNodeName();
		if(label.equals("DFA")||label.equals("NFA"))
			return DOMToFA(AutoN);
		else
			return DOMToPDA(AutoN);
	}
	
	private static Automata DOMToPDA(Node autoN) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static Automata DOMToFA(Node AutoN){
//		Utils.viewElement(DFA);
		Automata fa;;
		
		if(AutoN.getNodeName().equals("DFA"))
			fa=new DFA();
		else
			fa=new NFA();
		
		fa.setName(AutoN.getAttributes().item(0).getNodeValue());
		
		Node States=AutoN.getFirstChild();
		Node InputSymbols=States.getNextSibling();
		Node TranFuncs=InputSymbols.getNextSibling();
		Node StartQ=TranFuncs.getNextSibling();
		Node FinalStates=StartQ.getNextSibling();
		
		NodeList stateList=States.getChildNodes();
		for(int i=0;i<stateList.getLength();i++){
			Node node=stateList.item(i);
			fa.addState(node.getTextContent());
		}
		
		NodeList symbols=InputSymbols.getChildNodes();
		for(int i=0;i<symbols.getLength();i++){
			Node node=symbols.item(i);
			fa.addSymbol(node.getTextContent().charAt(0));
		}
		
		NodeList tranList=TranFuncs.getChildNodes();
		for(int i=0;i<tranList.getLength();i++){
			Node node=tranList.item(i);
			Node beginStateN=node.getFirstChild();
			Node symbolN=beginStateN.getNextSibling();
			Node endStateN=symbolN.getNextSibling();
			
			fa.addTransfer(beginStateN.getTextContent(), symbolN.getTextContent().charAt(0), 
					endStateN.getTextContent());			
		}
		
		Node startN=StartQ.getFirstChild();
		fa.setStartQ(startN.getTextContent());
		
		NodeList finalList=FinalStates.getChildNodes();
		for(int i=0;i<finalList.getLength();i++){
			Node node=finalList.item(i);
			fa.addFinalState(node.getTextContent());
		}
		
		return fa;
	}
}