package automaton;

import exception.NoStateFoundException;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import xml2.NFADomParserTwo;
import xml2.PDADomParserTwo;

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
			ndp = new NFADomParserTwo();
			pdp = new PDADomParserTwo();
		}
		return af;
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
		if (automatonType.equals("NFA") || automatonType.equals("DFA")) {
			ndp = new NFADomParserTwo();
			return ndp.getAutomatonFromNode(doc);
		}
		if (automatonType.equals("PDA")) {
			pdp = new PDADomParserTwo();
			return pdp.getAutomatonFromNode(doc);
		}
		return null;
	}

	public static Document getDocumentFromAutomaton(Automaton automaton) {
		String automatonType = automaton.getAutomatonType();
		if (automatonType.equals("NFA") || automatonType.equals("DFA")) {
			ndp = new NFADomParserTwo();
			return ndp.getDocumentFromAutomaton(automaton);
		}
		if (automatonType.equals("PDA")) {
			pdp = new PDADomParserTwo();
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
	public static void writeAutomatonToXml(Automaton automaton, File file) {
		Document document = getDocumentFromAutomaton(automaton);
		writeAutomatonToXml(document, file);
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
	private static NFADomParserTwo ndp;
	private static PDADomParserTwo pdp;
}
