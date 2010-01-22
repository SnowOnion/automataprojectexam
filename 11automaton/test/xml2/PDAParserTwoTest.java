package xml2;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import automaton.Automaton;
import automaton.AutomatonPDA;
import automaton.TransitionPDA;

public class PDAParserTwoTest {
	public static void main(String []args)throws Exception{
		testNFADomParserWrite();
	}
	
	/*********************************************************
	 * Test Write Operation
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static void testNFADomParserWrite() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata2/PDA1_Two.xml");
		PDADomParserTwo test = new PDADomParserTwo();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		Automaton a = test.getAutomatonFromNode(doc);
		
		File toFile = new File("C:/toFilePDA.xml");
		Document document = test.getDocumentFromAutomaton(a);
		test.writeDocumentToFile(document, toFile);
	}
	
	@SuppressWarnings("static-access")
	public static void testPDADomParserReader() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata2/PDA1_Two.xml");
		PDADomParserTwo test = new PDADomParserTwo();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		AutomatonPDA a =(AutomatonPDA) test.getAutomatonFromNode(doc);
		TransitionPDA t =(TransitionPDA) a.getTransitions().get(0);
		int s = t.getTransitionConditions().size();
		System.out.println("size"+s);
	}
	
}

