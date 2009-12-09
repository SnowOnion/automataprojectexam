package test;

import java.io.File;

import org.w3c.dom.Document;

import xml.AutomatonFactory;
import xml.DFADomParser;
import automaton.Automaton;

public class ParserTest {
	public static void main(String []args)throws Exception{
		testPDADomParser();
	}
	@SuppressWarnings("static-access")
	public static void testPDADomParser() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata/PDA1.xml");
		AutomatonFactory af = AutomatonFactory.getInstance();
		Automaton a = af.getAutomatonFromXml(file);
		System.out.println(a);
	}
	@SuppressWarnings("static-access")
	public static void testNFADomParser() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata/NFA1.xml");
		AutomatonFactory af = AutomatonFactory.getInstance();
		Automaton a = af.getAutomatonFromXml(file);
		System.out.println(a);
	}
	@SuppressWarnings("static-access")
	public static void testDFADomParser() throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata/DFA1.xml");
		AutomatonFactory af = AutomatonFactory.getInstance();
		Automaton a = af.getAutomatonFromXml(file);
		System.out.println(a);
		DFADomParser daf = new DFADomParser();
		Document mydoc = daf.getDocumentFromAutomaton(a);
		File f = new File("D:/ms.xml");
		daf.writeDocumentToFile(mydoc,f);
	}
}
