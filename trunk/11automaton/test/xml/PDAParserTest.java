package xml;

import java.io.File;

import org.w3c.dom.Document;

import automaton.Automaton;
import automaton.AutomatonFactory;

public class PDAParserTest {
	public static void main(String []args)throws Exception{
		testNFADomParserWrite();
	}
	
	/*********************************************************
	 * Test Write Operation
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static void testNFADomParserWrite() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata/PDA1.xml");
		AutomatonFactory af = AutomatonFactory.getInstance();
		Automaton a = af.getAutomatonFromXml(file);
		System.out.println(a);
		
		PDADomParser pda = new PDADomParser();
		Document mydoc = pda.getDocumentFromAutomaton(a);
		File f = new File("D:/PDA1.xml");
		pda.writeDocumentToFile(mydoc,f);
	}
	
	@SuppressWarnings("static-access")
	public static void testPDADomParserReader() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata/PDA1.xml");
		AutomatonFactory af = AutomatonFactory.getInstance();
		Automaton a = af.getAutomatonFromXml(file);
		System.out.println(a);
	}
	
}
