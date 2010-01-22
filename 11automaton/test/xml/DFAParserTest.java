package xml;

import java.io.File;
import org.w3c.dom.Document;
import automaton.Automaton;
import automaton.AutomatonFactory;

public class DFAParserTest {
	public static void main(String []args)throws Exception{
		testDFADomParserWrite();
	}
	@SuppressWarnings("static-access")
	public static void testDFADomParserWrite() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata/DFA1.xml");
		AutomatonFactory af = AutomatonFactory.getInstance();
		Automaton a = af.getAutomatonFromXml(file);
		System.out.println(a);
		DFADomParser daf = new DFADomParser();
		Document mydoc = daf.getDocumentFromAutomaton(a);
		File f = new File("D:/DFA1.xml");
		daf.writeDocumentToFile(mydoc,f);
	}
	@SuppressWarnings("static-access")
	public static void testDFADomParserReader() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata/DFA1.xml");
		AutomatonFactory af = AutomatonFactory.getInstance();
		Automaton a = af.getAutomatonFromXml(file);
		System.out.println(a);
	}

}
