package test;

import java.io.File;

import automaton.Automaton;

import xml.AutomatonFactory;

public class AutomatonFactoryTest {
	public static void main(String []args)throws Exception{
		File file = new File("D:/EclipseWorkspace/automaton/xmldata/DFA1.xml");
		AutomatonFactory af = AutomatonFactory.getInstance();
		Automaton a = af.getAutomatonFromXml(file);
	}
}
