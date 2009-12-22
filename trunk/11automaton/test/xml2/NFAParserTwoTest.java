package xml2;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import automaton.Automaton;

public class NFAParserTwoTest {
	public static void main(String []args)throws Exception{
		testFA_NFAWriter();
	}
	public static void testFA_NFAReader() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata2/NFA1_Two.xml");
		NFADomParserTwo test = new NFADomParserTwo();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		Automaton a = test.getAutomatonFromNode(doc);
		System.out.println(a);
	}
	public static void testFA_NFAWriter() throws Exception {
		File file = new File("D:/EclipseWorkspace/automaton/xmldata2/NFA1_Two.xml");
		NFADomParserTwo test = new NFADomParserTwo();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		Automaton a = test.getAutomatonFromNode(doc);
		
		File toFile = new File("D:/toFile.xml");
		Document document = test.getDocumentFromAutomaton(a);
		test.writeDocumentToFile(document, toFile);
	}
}
