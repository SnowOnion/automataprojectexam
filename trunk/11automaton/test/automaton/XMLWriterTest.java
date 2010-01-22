package automaton;

import java.io.File;

public class XMLWriterTest {
	public static void main(String []args){
		Automaton auto= AutomatonInstance.constructAutomaton();
		Automaton s1 = AutomatonInstance.constructAutomatonS1();
		Automaton s2 = AutomatonInstance.constructAutomatonS2();
		AutomatonFactory autoFact = AutomatonFactory.getInstance();
		File out = new File("C:/P155min.xml");
		autoFact.writeAutomatonToXml(auto, out);
		autoFact.writeAutomatonToXml(s1, new File("C:/P158A.xml"));
		autoFact.writeAutomatonToXml(s2, new File("C:/P158B.xml"));

	}
}
