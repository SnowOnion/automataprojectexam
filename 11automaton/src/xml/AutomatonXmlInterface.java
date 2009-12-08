package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import automaton.Automaton;
import automaton.State;
import automaton.Transition;
/****************************************************************
 * This Interface defines the operation with the XML files.
 * @author Administrator
 *
 */
public interface AutomatonXmlInterface {
	Automaton getAutomatonFromNode(Document doc);
	State getStateFromNode(Node node);
	Transition getTransitionFromNode(Node node);
	
	Element getElementFromAutomaton(Automaton automaton);
	Element getElementFromState(State state);
	Element getElementFromTransition(Transition transition);
}
