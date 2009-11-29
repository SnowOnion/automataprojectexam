package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import automaton.Automaton;
import automaton.State;
import automaton.Transition;
import automaton.TransitionPDACondition;

public class PDADomParser implements AutomatonXmlInterface {

	@Override
	public Automaton getAutomatonFromNode(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State getStateFromNode(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transition getTransitionFromNode(Node node) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*******************************************************
	 * This method will be used in getTransitionFromNode(Node node) 
	 * which make the code structure more easy to understand
	 * @param node
	 * @return
	 */
	private TransitionPDACondition getTransitionPDAConditionFromNode(Node node){
		return null;
	}

}
