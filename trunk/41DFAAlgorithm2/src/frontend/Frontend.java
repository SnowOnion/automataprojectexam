package frontend;

import java.io.File;

import automata.AutomatonFactory;
import automata.DFA;

public class Frontend {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Input 2 XML files that represent DFA.");
			return;
		}
		File file = new File(args[0]);
		DFA a = AutomatonFactory.getDFAFromXML(file);
		System.out.println("DFA1:");
		System.out.println(a.toString());
		
		file = new File(args[1]);
		DFA b = AutomatonFactory.getDFAFromXML(file);
		System.out.println("DFA2:");
		System.out.println(b.toString());
		
		System.out.println("DFA1 is equivalent to DFA2? " + a.equivalentTo(b));
		System.out.println("DFA1 is included in DFA2? " + a.includedIn(b));
		
		System.out.println("\nMinimized DFA1:");
		System.out.println(a.toString());
		System.out.println("Minimized DFA2:");
		System.out.println(b.toString());
	}

}
