package automaton;

import exception.NoStateFoundException;
import gui.help.AutomatonType;
import gui.model.AbstractConnectionModel;
import gui.model.AbstractModel;
import gui.model.ContentsModel;
import gui.model.StateModel;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.draw2d.Bendpoint;
import org.w3c.dom.Document;

import xml.DFADomParser;
import xml.NFADomParser;
import xml.PDADomParser;

/**************************************************************
 * \ This class used Factory Design pattern to implements the creation of the
 * Automaton with the document parsed by XML.
 * 
 * @author Administrator
 * 
 */
public class AutomatonFactory {

	public static AutomatonFactory getInstance() {
		if (af == null) {
			af = new AutomatonFactory();
			ddp = new DFADomParser();
			ndp = new NFADomParser();
			pdp = new PDADomParser();
		}
		return af;
	}

	/*********************************************************************
	 * To be implmented.......... This method will create an Automaton Class
	 * according to the parameter Model
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Automaton getAutomatonFromModel(AbstractModel model)
			throws Exception {
		Automaton automaton = null;
		ContentsModel contents = null;
		if (model instanceof ContentsModel)
			contents = (ContentsModel) model;
		else
			throw new Exception();

		AutomatonType type = contents.getType();
		automaton = getAutomaton(type);

		automaton.setAutomatonType(type.toString());
		for (Object stateobj : contents.getChildren()) {
			StateModel stateModel = (StateModel) stateobj;
			State state = new State(stateModel.getText());
			automaton.addState(state);
			if (stateModel.isInitial())
				automaton.setInitialState(state);
			if (stateModel.isAccept())
				state.setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
		}

		for (AbstractConnectionModel conn : contents.getConnections()) {
			Transition transition = Transition.getTransitionOfType(type);
			transition.setConditionsFromRawString(automaton, conn
					.getCondition());

			List<Bendpoint> bendpoints = conn.getBendpoints();
			for (Bendpoint bendpoint : bendpoints) {
				transition.addNail(new Nail(bendpoint.getLocation()));
			}
		}

		return automaton;
	}

	public static Automaton getAutomaton(AutomatonType type) {
		Automaton automaton = null;
		if (type == AutomatonType.NFA) {
			automaton = new AutomatonNFA();
		} else if (type == AutomatonType.DFA)
			automaton = new AutomatonDFA();
		else
			automaton = new AutomatonPDA();
		return automaton;
	}

	/*******************************************************************
	 * \ Get an Automaton Class from the XML file. The returned object is the
	 * super class of automatons, you should cast into the type you want.
	 * 
	 * @param file
	 *            The parameter file should be the right format according to our
	 *            design. We suppose every body knows the format of the XML and
	 *            will not try this method on a illegal XML file or a XML
	 *            without info. More check on the parameter file will be added
	 *            if necessary in the future version.
	 * @return automaton
	 * @throws Exception
	 * 
	 */
	public static Automaton getAutomatonFromXml(File file) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		return getAutomatonFromDocument(doc);
	}

	/*****************************************************************
	 * You may also get an automaton from a document. The Document Class is the
	 * org.w3c.dom.Document, which records the information of the Automaton. It
	 * is a tree that contains a list of elements.
	 * 
	 * @param doc
	 *            The type is document and more request is similar as it is said
	 *            in method getAutomatonFromXml(File file). We suppose the user
	 *            knows the tree structure of the Document.
	 * @return
	 ***********************************************************/
	public static Automaton getAutomatonFromDocument(Document doc)
			throws NoStateFoundException {
		String automatonType = doc.getDocumentElement().getNodeName();
		if (automatonType.equals("DFA")) {
			return ddp.getAutomatonFromNode(doc);
		}
		if (automatonType.equals("NFA")) {
			return ndp.getAutomatonFromNode(doc);
		}
		if (automatonType.equals("PDA")) {
			return pdp.getAutomatonFromNode(doc);
		}
		return null;
	}

	public static Document getDocumentFromAutomaton(Automaton automaton) {
		String automatonType = automaton.getAutomatonType();
		if (automatonType.equals("DFA")) {
			return ddp.getDocumentFromAutomaton(automaton);
		}
		if (automatonType.equals("NFA")) {
			return ndp.getDocumentFromAutomaton(automaton);
		}
		if (automatonType.equals("PDA")) {
			return pdp.getDocumentFromAutomaton(automaton);
		}
		return null;
	}

	/*****************************************************************
	 * Write the Automaton to a XML file.
	 * 
	 * @param automaton
	 * @param file
	 * @return
	 */
	public static void writeAutomatonToXml(Automaton automaton,File file){
		Document document = getDocumentFromAutomaton(automaton);
		writeAutomatonToXml(document,file);
	}
	private static void writeAutomatonToXml(Document document, File file) {
		try {
			TransformerFactory transfactory = TransformerFactory.newInstance();
			Transformer transformer = transfactory.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty("encoding", "UTF-8");
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		}
	}

	private static AutomatonFactory af;
	private static DFADomParser ddp;
	private static NFADomParser ndp;
	private static PDADomParser pdp;
}
