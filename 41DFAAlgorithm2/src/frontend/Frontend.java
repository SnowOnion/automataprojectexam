package frontend;

import java.io.File;

import automata.AutomatonFactory;
import automata.DFA;

public class Frontend {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		DFA d = new DFA();
		d.addState("p");
		d.addFinalState("q");
		d.addFinalState("r");
		try {
			d.addTransition("p", 'a', "q");
			d.addTransition("p", 'b', "r");
			d.addTransition("q", 'a', "r");
			d.addTransition("q", 'b', "q");
			d.addTransition("r", 'a', "r");
			d.addTransition("r", 'b', "q");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		DFA t = new DFA();
		t.setStartState("p");
		t.addFinalState("q");
		t.addFinalState("r");
		try {
			t.addTransition("p", 'a', "q");
			t.addTransition("p", 'b', "r");
			t.addTransition("q", 'a', "r");
			t.addTransition("q", 'b', "q");
			t.addTransition("r", 'a', "q");
			t.addTransition("r", 'b', "q");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		System.out.println(d.toString());
		System.out.println(t.toString());
		d.minimize();
		System.out.println(d.toString());

		System.out.println(d.equivalentTo(t));
		*/
		File file = new File("src\\XML1.xml");
		DFA a = AutomatonFactory.getDFAFromXML(file);
		System.out.println(a.toString());
		file = new File("src\\XML2.xml");
		DFA b = AutomatonFactory.getDFAFromXML(file);
		System.out.println(b.toString());
		System.out.println(a.equivalentTo(b));
		System.out.println(a.toString());
		System.out.println(b.toString());
	}

}
