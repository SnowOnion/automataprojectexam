package xml;

import java.io.File;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import automaton.Automaton;
/**************************************************************\
 * This class used Factory Design pattern to implements the creation
 * of the Automaton with the document parsed by XML.
 * 
 * @author Administrator
 *
 */
public class AutomatonFactory {
	
	public static AutomatonFactory getInstance(){
		if(af == null){
			af = new AutomatonFactory();
			ddp = new DFADomParser();
			ndp = new NFADomParser();
			pdp = new PDADomParser();
		}
		return af;
	}
	/*******************************************************************\
	 * Get an Automaton Class from the XML file.
	 * The returned object is the super class of automatons, 
	 * you should cast into the type you want. 
	 * @param file 
	 * 		The parameter file should be the right format
	 * according to our design. We suppose every body knows
	 * the format of the XML and will not try this method on 
	 * a illegal XML file or a XML without info.
	 * 		More check on the parameter file will be added if
	 * necessary in the future version.
	 * @return automaton
	 * @throws Exception
	 * 		
	 */
	public static Automaton getAutomatonFromXml(File file) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		
		Element root = doc.getDocumentElement();
		System.out.println(root.getNodeName()+"\t"+root.getNodeValue());
		String rootName = root.getNodeName();
		if(rootName.equals("DFA")){
			return ddp.getAutomatonFromNode(doc);
		}
		if(rootName.equals("NFA")){
			return ndp.getAutomatonFromNode(doc);
		}
		if(rootName.equals("PDA")){
			return pdp.getAutomatonFromNode(doc);
		}
		return null;
	}

	/*****************************************************************
	 * You may also get an automaton from a document.
	 * The Document Class is the org.w3c.dom.Document, which records 
	 * the information of the Automaton.
	 * It is a tree that contains a list of elements.
	 * 
	 * @param doc
	 * 		The type is document and more request is similar as 
	 * it is said in method getAutomatonFromXml(File file). We 
	 * suppose the user knows the tree structure of the Document.
	 * @return
	 ***********************************************************/
	public static Automaton getAutomatonFromDocument(Document doc){
		String automatonType = doc.getDocumentElement().getNodeName();
		if(automatonType.equals("DFA")){
			return ddp.getAutomatonFromNode(doc);
		}
		if(automatonType.equals("NFA")){
			return ndp.getAutomatonFromNode(doc);
		}
		if(automatonType.equals("PDA")){
			return pdp.getAutomatonFromNode(doc);
		}
		return null;
	}
	
	public Element getDocumentFromAutomaton(Automaton automaton){
		String automatonType = automaton.getAutomatonType();
		if(automatonType.equals("DFA")){
			return ddp.getElementFromAutomaton(automaton);
		}
		if(automatonType.equals("NFA")){
			return ndp.getElementFromAutomaton(automaton);
		}
		if(automatonType.equals("PDA")){
			return pdp.getElementFromAutomaton(automaton);
		}
		return null;
	}
	
	/*****************************************************************
	 * Write the Automaton to a XML file.
	 * @param automaton
	 * @param file
	 * @return
	 */
	public boolean writeAutomatonToXml(Document automaton,Writer writer,String encoding) {
		// TODO Auto-generated method stub
		/*
		try{
			Source source = new DOMSource(doc);
		}
		*/
		File f = new File("test.txt");
		automaton.toString();
		return false;
	}
	
	private static AutomatonFactory af;
	private static DFADomParser ddp;
	private static NFADomParser ndp;
	private static PDADomParser pdp;
}
