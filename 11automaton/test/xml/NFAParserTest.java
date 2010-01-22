package xml;

import java.io.File;
import org.w3c.dom.Document;
import automaton.Automaton;
import automaton.AutomatonFactory;

public class NFAParserTest {
	public static void main(String []args)throws Exception{
		testNFADomParserWrite();
	}
	
	/*********************************************************
	 * Test Write Operation
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static void testNFADomParserWrite() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata/NFA1.xml");
		AutomatonFactory af = AutomatonFactory.getInstance();
		Automaton a = af.getAutomatonFromXml(file);
		System.out.println(a);
		
		NFADomParser nfa = new NFADomParser();
		Document mydoc = nfa.getDocumentFromAutomaton(a);
		File f = new File("D:/NFA1.xml");
		nfa.writeDocumentToFile(mydoc,f);
	}
	
	@SuppressWarnings("static-access")
	public static void testNFADomParserReader() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata/NFA1.xml");
		AutomatonFactory af = AutomatonFactory.getInstance();
		Automaton a = af.getAutomatonFromXml(file);
		System.out.println(a);
	}
}
