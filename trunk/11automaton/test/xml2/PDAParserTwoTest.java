package xml2;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import automaton.Automaton;

public class PDAParserTwoTest {
	public static void main(String []args)throws Exception{
		testNFADomParserWrite();
	}
	
	/*********************************************************
	 * Test Write Operation
	 * @throws Exception
	 */
	public static void testNFADomParserWrite() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata2/PDA1_Two.xml");
		PDADomParserTwo test = new PDADomParserTwo();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		Automaton a = test.getAutomatonFromNode(doc);
		
		File toFile = new File("D:/toFilePDA.xml");
		Document document = test.getDocumentFromAutomaton(a);
		test.writeDocumentToFile(document, toFile);
	}
	
	public static void testPDADomParserReader() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata2/PDA1_Two.xml");
		PDADomParserTwo test = new PDADomParserTwo();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		Automaton a = test.getAutomatonFromNode(doc);
		System.out.println(a);
	}
	
}

