/**
 * @(#) DFAToReg.java 1.60 12/26,2009
 * 
 * Copyright reserved @author zilf
 */

package permissionzh.automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import automaton.*;

/**
 * Translate DFA to Regular Expression
 * 
 * @author zilf ghh
 * @version 1.0
 */
public final class DFAToReg {
	/** Define static initial state_id to assure the unique of state of NFA */
	private static int initialStateID = 0;

	/**
	 * Set InitialStateID
	 * 
	 * @param initialStateID
	 */
	public static void setInitialStateID(int initialStateID) {
		DFAToReg.initialStateID = initialStateID;
	}

	/**
	 * Get InitialStateID
	 * 
	 * @return initialStateID
	 */
	public static int getInitialStateID() {
		return initialStateID;
	}

	/**
	 * Translate DFA to Regular expression
	 * 
	 * @param automatonFA
	 * @return out_Reg
	 */
	public String automatonToReg(AutomatonDFA automatonDFA) {
		preprocessAutomaton(automatonDFA);

		/** just remain 2 states: start,accepted */
		{
			Iterator<Entry<String, State>> iter = automatonDFA.getStates()
					.entrySet().iterator();
			State temp = null;

			while (iter.hasNext()) {
				temp = iter.next().getValue();
				if (temp.getStateType() != AutomatonConstant.STATETYPE_ACCEPTED
						&& temp != automatonDFA.getInitialState()) {
					eliminateState(temp, automatonDFA);
					iter.remove();
				}
			}
		}

		/** transform the 2-state DFA to regular expression */
		StringBuilder reg = new StringBuilder("");

		Iterator<Transition> iter = automatonDFA.getTransitions().iterator();
		TransitionDFA transition = null;
		while (iter.hasNext()) {
			transition = (TransitionDFA) iter.next();
			if (((String) transition.getTransitionConditions().get(0)).length() > 1) {
				reg.append("(");
				reg.append((String) transition.getTransitionConditions().get(0));
				reg.append(")");
			} else {
				reg.append(transition.getTransitionConditions().get(0));
			}
			reg.append("+");
		}
		if (reg.length() > 1) {
			reg.delete(reg.length() - 1, reg.length());
		}

		return reg.toString();
	}

	/**
	 * Eliminate state
	 * 
	 * @param temp
	 * @param automatonFA
	 */
	private void eliminateState(State temp, AutomatonDFA automatonDFA) {
		ArrayList<TransitionDFA> transitionsToState = new ArrayList<TransitionDFA>();
		ArrayList<TransitionDFA> transitionsFromState = new ArrayList<TransitionDFA>();
		ArrayList<TransitionDFA> loopTransition = new ArrayList<TransitionDFA>();

		Iterator<Transition> iter = automatonDFA.getTransitions().iterator();

		TransitionDFA transition = null;
		while (iter.hasNext()) {
			transition = (TransitionDFA) iter.next();
			if (transition.getFromState() == temp && transition.getToState() == temp) {
				loopTransition.add(transition);
				iter.remove();
			} else if (transition.getToState() == temp) {
				transitionsToState.add(transition);
				iter.remove();
			} else if (transition.getFromState() == temp) {
				transitionsFromState.add(transition);
				iter.remove();
			}
		}

		/** union transition between the same 2 states */
		{
			processTransitionToStateList(transitionsToState);
			processTransitionFromStateList(transitionsFromState);
			processLoopTransitionList(loopTransition);
		}

		/** based on rule of eliminate-state to eliminate state */

		StringBuilder strLoop = new StringBuilder("");
		if (loopTransition.size() > 0) {
			if (((String) loopTransition.get(0).getTransitionConditions().get(0))
					.length() > 1) {
				strLoop.append("(");
				strLoop
						.append((String) loopTransition.get(0).getTransitionConditions()
								.get(0));
				strLoop.append(")");
				strLoop.append("*");
			} else {
				strLoop
						.append((String) loopTransition.get(0).getTransitionConditions()
								.get(0));
				strLoop.append("*");
			}
		}

		StringBuilder strTransconditions = new StringBuilder("");
		StringBuilder strPrev = new StringBuilder("");
		StringBuilder strPost = new StringBuilder("");

		TransitionDFA newTransition = null;
		TransitionDFA tempTransition1 = null;
		TransitionDFA tempTransition2 = null;

		ArrayList<String> transCondition = null;

		Iterator<TransitionDFA> iter1 = transitionsToState.iterator();
		Iterator<TransitionDFA> iter2 = null;

		while (iter1.hasNext()) {
			tempTransition1 = iter1.next();

			if (((String) tempTransition1.getTransitionConditions().get(0)).length() > 1) {
				strPrev.append("(");
				strPrev.append((String) tempTransition1.getTransitionConditions()
						.get(0));
				strPrev.append(")");
			} else {
				strPrev.append((String) tempTransition1.getTransitionConditions()
						.get(0));
			}

			if (strLoop.length() > 0) {
				strPrev.append(strLoop.toString());
			}

			iter2 = transitionsFromState.iterator();
			while (iter2.hasNext()) {
				tempTransition2 = iter2.next();

				if (((String) tempTransition2.getTransitionConditions().get(0))
						.length() > 1) {
					strPost.append("(");
					strPost
							.append((String) tempTransition2.getTransitionConditions()
									.get(0));
					strPost.append(")");
				} else {
					strPost
							.append((String) tempTransition2.getTransitionConditions()
									.get(0));
				}
				strTransconditions.append(strPrev.toString());
				strTransconditions.append(strPost.toString());
				transCondition = new ArrayList<String>();
				transCondition.add(strTransconditions.toString());
				newTransition = new TransitionDFA(tempTransition1.getFromState(),
						transCondition, tempTransition2.getToState());

				automatonDFA.addTransition(newTransition);
				strTransconditions.delete(0, strTransconditions.length());
				strPost.delete(0, strPost.length());
			}
			strPrev.delete(0, strPrev.length());
		}

		/**
		 * this statement can't be executed,or a Exception will be thrown
		 * 
		 * automatonDFA.removeState(temp);
		 */
	}

	/**
	 * Union loop on single state
	 * 
	 * @param loopTransition
	 */
	private void processLoopTransitionList(
			ArrayList<TransitionDFA> loopTransition) {
		if (loopTransition.size() == 0) {
			return;
		}

		TransitionDFA transition = loopTransition.get(0);
		Iterator<TransitionDFA> iter = loopTransition.iterator();
		iter.next();

		ArrayList<String> list = null;
		while (iter.hasNext()) {
			list = iter.next().getTransitionConditions();
			for (int i = 0; i < list.size(); i++) {
				transition.getTransitionConditions().add(list.get(i));
			}
			iter.remove();
		}

		StringBuilder strBuilder = new StringBuilder("");
		list = transition.getTransitionConditions();

		for (int i = 0; i < list.size() - 1; i++) {
			strBuilder.append(list.get(i));
			strBuilder.append("+");
		}
		strBuilder.append(list.get(list.size() - 1));

		transition.getTransitionConditions().clear();
		transition.getTransitionConditions().add(strBuilder.toString());
	}

	/**
	 * Union the transition between two same state
	 * 
	 * @param transitionsToState
	 */
	private void processTransitionToStateList(
			ArrayList<TransitionDFA> transitionsToState) {
		if (transitionsToState.size() == 0) {
			return;
		}

		ArrayList<String> list = null;
		for (int i = 0; i < transitionsToState.size() - 1; i++) {
			for (int j = i + 1; j < transitionsToState.size(); j++) {
				if (transitionsToState.get(j).getFromState() == transitionsToState
						.get(i).getFromState()) {
					list = transitionsToState.get(j).getTransitionConditions();
					for (int k = 0; k < list.size(); k++) {
						transitionsToState.get(i).getTransitionConditions().add(list
								.get(k));
					}

					transitionsToState.remove(j);
				}
			}
		}

		StringBuilder strBuilder = new StringBuilder("");
		for (int i = 0; i < transitionsToState.size(); i++) {
			list = transitionsToState.get(i).getTransitionConditions();

			for (int j = 0; j < list.size(); j++) {
				if (!list.get(j).equals("ε")) {
					strBuilder.append(list.get(j));
					strBuilder.append("+");
				}
			}
			if (strBuilder.length() > 1) {
				strBuilder.delete(strBuilder.length() - 1, strBuilder.length());
			}

			transitionsToState.get(i).getTransitionConditions().clear();
			transitionsToState.get(i).getTransitionConditions().add(strBuilder
					.toString());

			strBuilder.delete(0, strBuilder.length());
		}
	}

	/**
	 * Union the transition between two same state
	 * 
	 * @param transitionsFromState
	 */
	private void processTransitionFromStateList(
			ArrayList<TransitionDFA> transitionsFromState) {
		if (transitionsFromState.size() == 0) {
			return;
		}

		ArrayList<String> list = null;
		for (int i = 0; i < transitionsFromState.size() - 1; i++) {
			for (int j = i + 1; j < transitionsFromState.size(); j++) {
				if (transitionsFromState.get(j).getToState() == transitionsFromState
						.get(i).getToState()) {
					list = transitionsFromState.get(j).getTransitionConditions();
					for (int k = 0; k < list.size(); k++) {
						transitionsFromState.get(i).getTransitionConditions()
								.add(list.get(k));
					}

					transitionsFromState.remove(j);
				}
			}
		}

		StringBuilder strBuilder = new StringBuilder("");
		for (int i = 0; i < transitionsFromState.size(); i++) {
			list = transitionsFromState.get(i).getTransitionConditions();

			for (int j = 0; j < list.size(); j++) {
				if (!list.get(j).equals("ε")) {
					strBuilder.append(list.get(j));
					strBuilder.append("+");
				}
			}
			if (strBuilder.length() > 1) {
				strBuilder.delete(strBuilder.length() - 1, strBuilder.length());
			}

			transitionsFromState.get(i).getTransitionConditions().clear();
			transitionsFromState.get(i).getTransitionConditions().add(strBuilder
					.toString());

			strBuilder.delete(0, strBuilder.length());
		}
	}

	/**
	 * Preprocess automaton to transform
	 * 
	 * @param automatonFA
	 */
	private void preprocessAutomaton(AutomatonDFA automatonDFA) {
		setInitialStateID(0);
		adjustmentAutomaton(automatonDFA);

		/**
		 * add an only initial state and add ε-transaction to other initial
		 * states to it
		 */
		{
			State initialState = new State(String
					.valueOf(DFAToReg.initialStateID++));
			automatonDFA.addState(initialState);

			ArrayList<String> transCondition = new ArrayList<String>();
			transCondition.add("ε");

			Transition transition = new TransitionDFA(initialState,
					transCondition, automatonDFA.getInitialState());

			automatonDFA.addTransition(transition);
			automatonDFA.getInitialState().setStateType(
					AutomatonConstant.STATETYPE_NORMAL);

			automatonDFA.setInitialState(initialState);
		}

		/**
		 * add an only accepted state and add ε-transaction from other accepted
		 * state to it
		 */
		{
			State acceptedState = new State(String
					.valueOf(DFAToReg.initialStateID++));
			automatonDFA.addState(acceptedState);

			Iterator<Entry<String, State>> iter = automatonDFA
					.getAcceptedStates().entrySet().iterator();

			ArrayList<String> transCondition = new ArrayList<String>();
			transCondition.add("ε");
			State temp = null;
			Transition transition = null;

			while (iter.hasNext()) {
				temp = iter.next().getValue();
				temp.setStateType(AutomatonConstant.STATETYPE_NORMAL);
				transition = new TransitionDFA(temp, transCondition,
						acceptedState);
				automatonDFA.addTransition(transition);
			}
			acceptedState.setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
		}
	}

	/**
	 * Adjustment automatonFA and make sure all state_id is unique and denoted
	 * by integer
	 * 
	 * @param automatonFA
	 */
	private void adjustmentAutomaton(AutomatonDFA automatonDFA) {
		Iterator<Entry<String, State>> iter = automatonDFA.getStates().entrySet()
				.iterator();
		HashMap<String, State> tempStates = new HashMap<String, State>();
		State temp = null;

		while (iter.hasNext()) {
			temp = iter.next().getValue();
			temp.setStateId(String.valueOf(DFAToReg.initialStateID++));
			tempStates.put(temp.getStateId(), temp);
		}

		automatonDFA.setStates(tempStates);
	}
	
	/**
	 * Show the information of input DFA
	 * 
	 * @param automatonDFA
	 */
	void printInputDFA(AutomatonDFA automatonDFA) {
		int sizeOfInputAlphabet = 0;
		int numberOfStates = 0;

		ArrayList<String> inputAlphabet = automatonDFA.getInputSymbolSet();
		ArrayList<Transition> transition = automatonDFA.getTransitions();
		HashMap<String, State> states = automatonDFA.getStates();

		StringBuilder[][] transTable = null;

		sizeOfInputAlphabet = inputAlphabet.size();
		numberOfStates = states.size();

		transTable = new StringBuilder[numberOfStates + 1][sizeOfInputAlphabet + 1];
		transTable[0][0] = new StringBuilder("");
		
		for (int i = 1; i < sizeOfInputAlphabet + 1; i++) {
				transTable[0][i] = new StringBuilder(inputAlphabet.get(i - 1));
		}

		Iterator<Entry<String, State>> iter1 = states.entrySet().iterator();
		State temp = null;
		int k = 1;
		while (iter1.hasNext()) {
			temp = iter1.next().getValue();
			transTable[k][0] = new StringBuilder("");
			if (temp == automatonDFA.getInitialState()) {
				transTable[k][0].append("→");
			}
			if (temp.getStateType() == AutomatonConstant.STATETYPE_ACCEPTED) {
				transTable[k][0].append("*");
			}

			transTable[k][0].append(temp.getStateId());

			k++;
		}

		for (int i = 1; i < numberOfStates + 1; i++) {
			for (int j = 1; j < sizeOfInputAlphabet + 1; j++) {
				transTable[i][j] = new StringBuilder("");
			}
		}

		Iterator<Transition> iter2 = transition.iterator();
		Transition trans = null;
		StringBuilder strTemp = new StringBuilder("");

		while (iter2.hasNext()) {
			trans = iter2.next();
			if (trans.getFromState() == automatonDFA.getInitialState()) {
				strTemp.append("→");
			}
			if (trans.getFromState().getStateType() == AutomatonConstant.STATETYPE_ACCEPTED) {
				strTemp.append("*");
			}
			strTemp.append(trans.getFromState().getStateId());

			for (int i = 1; i < numberOfStates + 1; i++) {
				if (strTemp.toString().equals(transTable[i][0].toString())) {
					for (int j = 1; j < sizeOfInputAlphabet + 1; j++) {
						if (((TransitionDFA)trans).getTransitionConditions()
								.contains(transTable[0][j].toString())) {
							transTable[i][j].append(trans.getToState().getStateId());
							//break;
						}
					}
					break;
				}
			}
			strTemp.delete(0, strTemp.length());
		}

		System.out.println("待转换的DFA的信息如下 :\n");
		System.out.println(automatonDFA.toString());

		System.out.println("DFA的状态转移表为(每个数字表示一个状态):\n");
		for (int i = 0; i < numberOfStates + 1; i++) {
			for (int j = 0; j < sizeOfInputAlphabet + 1; j++) {
				System.out.print(transTable[i][j].toString());
				System.out.print('\t');
			}
			System.out.println();
			if (i == 0) {
				for (int l = 0; l < sizeOfInputAlphabet + 1; l++) {
					System.out.print("=======");
				}
				System.out.print("======");
				System.out.println();
			}
		}
		System.out.println();
	}

	/**
	 * Main for test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String reg;
		DFAToReg dfaToReg = new DFAToReg();

		AutomatonDFA automatonDFA = new AutomatonDFA();
		automatonDFA.setAutomatonName("InputDFA");
		
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		automatonDFA.setTransitions(transitions);
		
		/** 字母表要根据用到的字母来进行填充 */
		
		
		ArrayList<String> inputSymbolSet = new ArrayList<String>();
		
		inputSymbolSet.add("a");
		inputSymbolSet.add("b");
		
		automatonDFA.setInputSymbolSet(inputSymbolSet);
		
		State state0 = new State("0");
		State state1 = new State("1");

		ArrayList<String> transCondition0 = new ArrayList<String>();
		ArrayList<String> transCondition1 = new ArrayList<String>();

		
		transCondition0.add("a");
		transCondition1.add("b");

		TransitionDFA transitionDFA00 = new TransitionDFA(state0,
				transCondition0, state0);
		TransitionDFA transitionDFA01 = new TransitionDFA(state0,
				transCondition1, state1);
		TransitionDFA transitionDFA10 = new TransitionDFA(state1,
				transCondition0, state0);
		TransitionDFA transitionDFA11 = new TransitionDFA(state1,
				transCondition1, state1);

		automatonDFA.addState(state0);
		automatonDFA.addState(state1);
		state1.setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
		automatonDFA.setInitialState(state0);

		automatonDFA.addTransition(transitionDFA00);
		automatonDFA.addTransition(transitionDFA01);
		automatonDFA.addTransition(transitionDFA10);
		automatonDFA.addTransition(transitionDFA11);
		
		
		/*
		ArrayList<String> inputSymbolSet = new ArrayList<String>();
		
		inputSymbolSet.add("a");
		inputSymbolSet.add("b");
		
		automatonDFA.setInputSymbolSet(inputSymbolSet);
		
		State state0 = new State("0");
		State state1 = new State("1");
		State state2 = new State("2");
		State state3 = new State("3");
		State state4 = new State("4");

		ArrayList<String> transCondition0 = new ArrayList<String>();
		ArrayList<String> transCondition1 = new ArrayList<String>();
		
		transCondition0.add("a");
		transCondition1.add("b");

		TransitionDFA transitionDFA00 = new TransitionDFA(state0,
				transCondition0, state1);
		TransitionDFA transitionDFA01 = new TransitionDFA(state0,
				transCondition1, state2);
		
		TransitionDFA transitionDFA10 = new TransitionDFA(state1,
				transCondition0, state1);
		TransitionDFA transitionDFA11 = new TransitionDFA(state1,
				transCondition1, state3);
		
		TransitionDFA transitionDFA20 = new TransitionDFA(state2,
				transCondition0, state1);
		TransitionDFA transitionDFA21 = new TransitionDFA(state2,
				transCondition1, state2);
		
		TransitionDFA transitionDFA30 = new TransitionDFA(state3,
				transCondition0, state1);
		TransitionDFA transitionDFA31 = new TransitionDFA(state3,
				transCondition1, state4);
		
		TransitionDFA transitionDFA40 = new TransitionDFA(state4,
				transCondition0, state1);
		TransitionDFA transitionDFA41 = new TransitionDFA(state4,
				transCondition1, state2);
		

		automatonDFA.addState(state0);
		automatonDFA.addState(state1);
		automatonDFA.addState(state2);
		automatonDFA.addState(state3);
		automatonDFA.addState(state4);
		
		state4.setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
		automatonDFA.setInitialState(state0);

		automatonDFA.addTransition(transitionDFA00);
		automatonDFA.addTransition(transitionDFA01);
		automatonDFA.addTransition(transitionDFA10);
		automatonDFA.addTransition(transitionDFA11);	
		automatonDFA.addTransition(transitionDFA20);
		automatonDFA.addTransition(transitionDFA21);	
		automatonDFA.addTransition(transitionDFA30);
		automatonDFA.addTransition(transitionDFA31);	
		automatonDFA.addTransition(transitionDFA40);
		automatonDFA.addTransition(transitionDFA41);
		*/
		
		/*
		ArrayList<String> inputSymbolSet = new ArrayList<String>();
		
		inputSymbolSet.add("a");
		inputSymbolSet.add("b");
		
		automatonDFA.setInputSymbolSet(inputSymbolSet);
		
		State state0 = new State("0");
		State state1 = new State("1");

		ArrayList<String> transCondition0 = new ArrayList<String>();
		ArrayList<String> transCondition1 = new ArrayList<String>();

		transCondition0.add("a");
		transCondition1.add("b");

		TransitionDFA transitionDFA00 = new TransitionDFA(state0,
				transCondition0, state1);
		TransitionDFA transitionDFA01 = new TransitionDFA(state0,
				transCondition1, state0);
		TransitionDFA transitionDFA10 = new TransitionDFA(state1,
				transCondition0, state1);
		TransitionDFA transitionDFA11 = new TransitionDFA(state1,
				transCondition1, state1);

		automatonDFA.addState(state0);
		automatonDFA.addState(state1);
		state1.setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
		automatonDFA.setInitialState(state0);

		automatonDFA.addTransition(transitionDFA00);
		automatonDFA.addTransition(transitionDFA01);
		automatonDFA.addTransition(transitionDFA10);
		automatonDFA.addTransition(transitionDFA11);
		*/
		
		/*		
		ArrayList<String> inputSymbolSet = new ArrayList<String>();
		
		inputSymbolSet.add("0");
		inputSymbolSet.add("1");
		
		automatonDFA.setInputSymbolSet(inputSymbolSet);
		
		State state1 = new State("0");
		State state2 = new State("1");

		ArrayList<String> transCondition1 = new ArrayList<String>();
		ArrayList<String> transCondition2 = new ArrayList<String>();
		ArrayList<String> transCondition3 = new ArrayList<String>();
		
		transCondition1.add("0");
		transCondition2.add("1");
		transCondition3.add("0");
		transCondition3.add("1");

		TransitionDFA transitionDFA1 = new TransitionDFA(state1,
				transCondition2, state1);
		TransitionDFA transitionDFA2 = new TransitionDFA(state1,
				transCondition1, state2);
		TransitionDFA transitionDFA3 = new TransitionDFA(state2,
				transCondition3, state2);

		automatonDFA.addState(state1);
		automatonDFA.addState(state2);
		state2.setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
		automatonDFA.setInitialState(state1);

		automatonDFA.addTransition(transitionDFA1);
		automatonDFA.addTransition(transitionDFA2);
		automatonDFA.addTransition(transitionDFA3);
		*/
		
		dfaToReg.printInputDFA(automatonDFA);
			
		reg = dfaToReg.automatonToReg(automatonDFA);
		System.out.print("转换成的未化简正则表达式为：");
		System.out.println(reg);
	}

}
