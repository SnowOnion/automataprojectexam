package edu.cn.thss.qmy.ndfa.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import edu.cn.thss.qmy.ndfa.core.FA;
import edu.cn.thss.qmy.ndfa.core.FAFunctions;
import edu.cn.thss.qmy.ndfa.core.State;
import edu.cn.thss.qmy.ndfa.core.Transition;
import edu.cn.thss.qmy.ndfa.util.FAParser;

public class ConsoleMain {

	public static void main(String[] args) throws Exception {
		PrintWriter pw = new PrintWriter(System.out);
		InputStreamReader stdin = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader(stdin);
		FA fa = null;
		while(true){
			ConsoleMain.printWelcome(pw);
			String path = bf.readLine();
			fa = FAParser.NaiveParser(path);
			if(fa == null){
				System.err.println("The format of xml file is not correct.");
				ConsoleMain.printWelcome(pw);
			}else{
				ConsoleMain.printFA(fa, pw);
				ConsoleMain.printMenu(pw);
				break;
			}
		}
		while(true){
			int option = Integer.parseInt(bf.readLine().toString());
			switch(option){
			case 1: 
				FA dfa = FAFunctions.EnsureDFA(fa);
				ConsoleMain.printFA(dfa, pw);
				ConsoleMain.printMenu(pw);
				break;
			case 2:
				System.out.println("Is language null? " + FAFunctions.isLanguageNull(fa));
				System.out.println("Is language infinite? " + FAFunctions.isLanguageInfinite(fa));
				ConsoleMain.printMenu(pw);
				break;
			case 3:
				System.out.println("Input your string below: ");
				String str = bf.readLine();
				System.out.println(FAFunctions.canAccepted(fa, str.toCharArray()));
				ConsoleMain.printMenu(pw);
				break;
			case 4:
				bf.close();
				stdin.close();
				pw.close();
				System.exit(0);
				break;
			default:
				ConsoleMain.printMenu(pw);
				break;	
			}
		}
	}
	
	/**
	 * The output format:
	 * States: {name of all states}
  	 * Symbols: {symbols}
  	 * Start State: start state
  	 * Final States: {final states}
  	 * Transitions: 
  	 * pre_state --'symbol'--> next_state
  	 * 
	 * @param fa
	 */
	public static void printFA(FA fa, PrintWriter pw){
		pw.append("====================== \n");
		pw.append("Your FA is as below: \n");
		pw.append("States: {");
		HashMap states = fa.getStates();
		Iterator<Integer> stateIt = states.keySet().iterator();
		while(stateIt.hasNext()){
			Integer key = stateIt.next();
			pw.append(((State)states.get(key)).getName() + ", ");
		}
		pw.append("} \n");
		
		pw.append("Symbols: {");
		HashMap symbols = fa.getSymbols();
		Iterator<Integer> symbolIt = symbols.keySet().iterator();
		while(symbolIt.hasNext()){
			Integer key = symbolIt.next();
			pw.append((Character)symbols.get(key) + ", ");
		}
		pw.append("} \n");
		
		pw.append("Start State: ");
		pw.append(((State)states.get(fa.getStartState())).getName());
		pw.append("\n");
		
		pw.append("Final States: {");
		Iterator<Integer> finalStateIt = fa.getFinalStates().iterator();
		while(finalStateIt.hasNext()){
			Integer key = finalStateIt.next();
			pw.append(((State)states.get(key)).getName() + ", ");
		}
		pw.append("} \n");
		
		pw.append("Transitions: \n");
		Iterator<Transition> transitionIt = fa.getTransitions().iterator();
		while(transitionIt.hasNext()){
			Transition transition = transitionIt.next();
			pw.append(((State)states.get(transition.getPreState())).getName() + " --");
			pw.append((Character)symbols.get(transition.getSymbol()));
			pw.append("--> " + ((State)states.get(transition.getNextState())).getName() + "\n");
		}
		
		pw.flush();
	}
	
	public static void printWelcome(PrintWriter pw){
		pw.append("====================== \n");
		pw.append("Home_27_NDFAConverter: \n");
		pw.append("Pls input the absolute path of your FA xml file: \n");
		
		pw.flush();
	}
	
	public static void printMenu(PrintWriter pw){
		pw.append("====================== \n");
		pw.append("Home_27_NDFAConverter: \n");
		pw.append("1. Convert the FA to a DFA. \n");
		pw.append("2. Check the Language's Properties(Null/Infite). \n");
		pw.append("3. Check a String. \n");
		pw.append("4. Quit. \n");
		pw.append("====================== \n");
		
		pw.flush();
	}

}
