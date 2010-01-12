/**
 * @(#) RegToNFA.java 1.60 12/21,2009
 * 
 * Copyright reserved @author zilf
 */

package permissionzh.automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.Map.Entry;

import automaton.*;

/**
 * Translate Regular Expression to NFA
 * 
 * @author zilf ghh
 * @version 1.0
 */
public final class RegToNFA {
	/** Define static initial state_id to assure the unique of state of NFA */
	private static int initialStateID = 0;

	/**
	 * Set InitialStateID
	 * 
	 * @param initialStateID
	 */
	public static void setInitialStateID(int initialStateID) {
		RegToNFA.initialStateID = initialStateID;
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
	 * Validate the input: regular expression
	 * 
	 * @param in_reg
	 * @return true or false
	 */
	boolean check1(String in_reg) {
		byte[] regBytes = in_reg.getBytes();
		byte b = 0;
		int count = 0;
		int flag1 = 0;
		int flag2 = 0;
		byte preOperator = '@';
		int regLength = regBytes.length;

		/** ��ʼ�ַ���֤������Ϊ���ͱհ�������� */
		if (regLength > 0) {
			if (regBytes[0] == '+' || regBytes[0] == '*' || regBytes[0] == '.'
					|| regBytes[0] == '|') {
				System.out.println("������ʽ��ʼ���Ŵ���");
				return false;
			}
		}

		for (int i = 0; i < regLength; i++) {
			b = regBytes[i];
			/** ��֤Լ����������ԡ���Ӧ��������������� */
			{
				/** ������� */
				{
					if (b == '(') {
						if (count >= 0)
							count++;
						else {
							System.out.println("���Ų�ƥ�䣬�����ű������Ŷ࣡");
							return false;
						}
					}
					if (b == ')') {
						if (count > 0)
							count--;
						else {
							System.out.println("���Ų�ƥ�䣬�����ű������Ŷ࣡");
							return false;
						}
					}
				}
				/** ������������ж� */
				{
					if (preOperator != '@') {
						if (preOperator == '(') {
							if (b == '+' || b == '|' || b == '*' || b == '.') {
								System.out.println("�����ź�������Ƿ����ӣ�");
								return false;
							}
						}
						if (preOperator == '+' || preOperator == '|') {
							if (b == '+' || b == '|' || b == ')' || b == '*'
									|| b == '.') {
								System.out.println("�������������������Ƿ����ӣ�");
								return false;
							}
						}
						if (preOperator == '*') {
							if (b == '*') {
								System.out.println("������ıհ���������ӣ�");
								return false;
							}
						}
						if (preOperator == '.') {
							if (b == '+' || b == '|' || b == ')' || b == '*'
									|| b == '.') {
								System.out.println("���������������������Ƿ����ӣ�");
								return false;
							}
						}
					}
				}
			}
			/** ��֤�����ַ��ĺϷ��� */
			{
				/** b Ϊ 0-9 ���� A-Z ���� a-z �е��ַ� */
				if ((48 <= b && b <= 57) || (65 <= b && b <= 90)
						|| (97 <= b && b <= 122))
					preOperator = '@';
				/** b Ϊ �Ϸ����������� */
				else if (b == '*' || b == '|' || b == '+' || b == '('
						|| b == ')' || b == '.')
					preOperator = b;
				else {
					System.out.println("������ʽ���зǷ��ַ���");
					return false;
				}
			}
			/** ��֤����Ĳ��������һ���� */
			if (b == '+') {
				if (flag2 != 0) {
					System.out.println("������ʽ �в��������һ�£�");
					return false;
				}
				flag1 = 1;
			}
			if (b == '|') {
				if (flag1 != 0) {
					System.out.println("������ʽ �в��������һ�£�");
					return false;
				}
				flag2 = 1;
			}
		}
		/** �����ַ���֤������Ϊ��������������� */
		if (b == '+' || b == '|' || b == '.') {
			System.out.println("������ʽ �н�β�ַ����Ϸ���");
			return false;
		}
		/** ������� */
		if (count != 0) {
			System.out.println("������ʽ�����Ų���ԣ�");
			return false;
		}
		return true;
	}

	/**
	 * Validate the input: regular expression changed from check1: dot(.) won't
	 * be used as a operator but a wildcard
	 * 
	 * @param in_reg
	 * @return true or false
	 */
	boolean check(String in_reg) {
		byte[] regBytes = in_reg.getBytes();
		byte b = 0;
		int count = 0;
		int flag1 = 0;
		int flag2 = 0;
		byte preOperator = '@';
		int regLength = regBytes.length;

		/** ��ʼ�ַ���֤������Ϊ���ͱհ�������� */
		if (regLength > 0) {
			if (regBytes[0] == '+' || regBytes[0] == '*' || regBytes[0] == '|') {
				System.out.println("������ʽ��ʼ���Ŵ���");
				return false;
			}
		}

		for (int i = 0; i < regLength; i++) {
			b = regBytes[i];
			/** ��֤Լ����������ԡ���Ӧ��������������� */
			{
				/** ������� */
				{
					if (b == '(') {
						if (count >= 0)
							count++;
						else {
							System.out.println("���Ų�ƥ�䣬�����ű������Ŷ࣡");
							return false;
						}
					}
					if (b == ')') {
						if (count > 0)
							count--;
						else {
							System.out.println("���Ų�ƥ�䣬�����ű������Ŷ࣡");
							return false;
						}
					}
				}
				/** ������������ж� */
				{
					if (preOperator != '@') {
						if (preOperator == '(') {
							if (b == '+' || b == '|' || b == '*') {
								System.out.println("�����ź�������Ƿ����ӣ�");
								return false;
							}
						}
						if (preOperator == '+' || preOperator == '|') {
							if (b == '+' || b == '|' || b == ')' || b == '*') {
								System.out.println("�������������������Ƿ����ӣ�");
								return false;
							}
						}
						/*
						 * this restriction is just used to make regular
						 * expression easy to understand,but not necessary.
						 * 
						 * if (preOperator == '*') { if (b == '*') {
						 * System.out.println("������ıհ���������ӣ�"); return false; } }
						 */
					}
				}
			}
			/** ��֤�����ַ��ĺϷ��� */
			{
				/** b Ϊ 0-9 ���� A-Z ���� a-z �е��ַ� */
				if ((48 <= b && b <= 57) || (65 <= b && b <= 90)
						|| (97 <= b && b <= 122)) {
					preOperator = '@';
				}
				/** b Ϊ �Ϸ����������� */
				else if (b == '*' || b == '|' || b == '+' || b == '('
						|| b == ')') {
					preOperator = b;
				}
				/** b Ϊ �Ϸ������ͨ��� */
				else if (b == '.') {
					preOperator = '@';
				} else {
					System.out.println("������ʽ���зǷ��ַ���");
					return false;
				}
			}
			/** ��֤����Ĳ��������һ���� */
			if (b == '+') {
				if (flag2 != 0) {
					System.out.println("������ʽ �в��������һ�£�");
					return false;
				}
				flag1 = 1;
			}
			if (b == '|') {
				if (flag1 != 0) {
					System.out.println("������ʽ �в��������һ�£�");
					return false;
				}
				flag2 = 1;
			}
		}
		/** �����ַ���֤������Ϊ��������� */
		if (b == '+' || b == '|') {
			System.out.println("������ʽ �н�β�ַ����Ϸ���");
			return false;
		}
		/** ������� */
		if (count != 0) {
			System.out.println("������ʽ�����Ų���ԣ�");
			return false;
		}
		return true;
	}

	/**
	 * Get the priority of sign of in-stack
	 * 
	 * @param sign
	 * @return priority
	 */
	int getPriority1(byte sign) {
		int priority = -1;
		switch (sign) {
		case '(':
			priority = 1;
			break;
		case '.':
			priority = 5;
			break;
		case '|':
		case '+':
			priority = 3;
			break;
		case '$':
			priority = 0;
			break;
		}
		return priority;
	}

	/**
	 * Get the priority of sign of out-stack
	 * 
	 * @param sign
	 * @return priority
	 */
	int getPriority2(byte sign) {
		int priority = -1;
		switch (sign) {
		case ')':
			priority = 1;
			break;
		case '.':
			priority = 4;
			break;
		case '|':
		case '+':
			priority = 2;
			break;
		}
		return priority;
	}

	/**
	 * Transform the checked legal regular expression into AutomatonNFA
	 * 
	 * @param in_reg
	 * @return AutomatonNFA
	 */
	AutomatonNFA regToAutomatonNFA(String in_reg) {

		setInitialStateID(0);

		if (!check(in_reg)) {
			return null;
		}

		in_reg = adjustReg(in_reg);

		/** �����ջ */
		Stack<Byte> operator = new Stack<Byte>();
		/** �Զ���ջ */
		Stack<AutomatonNFA> automatonNFA = new Stack<AutomatonNFA>();

		/** ��������ȼ���ջ�ڣ�priority1 ��ջ�⣺priority2 */
		int priority1, priority2;
		/** ջ������� */
		byte operatorInStack;

		/** ������ʱ��NFA */
		AutomatonNFA temp, temp1, temp2;
		byte[] regBytes = in_reg.getBytes();

		/** ������ĸ�� */
		ArrayList<String> inputSymbolSet = new ArrayList<String>();

		/** ָʾ�ϸ������ַ������ͣ�0���������1����ĸ */
		int flag = 0;

		/** ѹ�����ջջ�׷��� */
		operator.push((byte) '$');

		/** ͨ�� for ѭ����������ʽת��Ϊ NFA */
		for (int i = 0; i < regBytes.length; i++) {
			switch (regBytes[i]) {
			/** ���������ţ����Զ���ջ���գ�ѹ�����ӷ� */
			case '(':
				flag = 0;
				if (!automatonNFA.empty()) {
					operatorInStack = operator.peek();
					priority1 = getPriority1(operatorInStack);
					priority2 = getPriority2((byte) '.');
					if (priority2 < priority1) {
						temp2 = automatonNFA.pop();
						temp1 = automatonNFA.pop();
						temp = connectOperation(temp1, temp2);
						automatonNFA.push(temp);
					} else {
						operator.push((byte) '.');
					}
				}
				operator.push((byte) '(');
				break;
			case ')':
				while (operator.peek() != '(') {
					operatorInStack = operator.pop();
					switch (operatorInStack) {
					case '.':
						temp2 = automatonNFA.pop();
						temp1 = automatonNFA.pop();
						temp = connectOperation(temp1, temp2);
						automatonNFA.push(temp);
						break;
					case '+':
						temp2 = automatonNFA.pop();
						temp1 = automatonNFA.pop();
						temp = unionOperation(temp1, temp2);
						automatonNFA.push(temp);
						break;
					default:
						break;
					}
				}
				operator.pop();
				break;
			/** �����հ��������ֱ�ӽ��Զ���ջ��Ԫ�ص�������������, �ٽ���ѹ���Զ���ջ���� */
			case '*':
				flag = 1;
				temp1 = automatonNFA.pop();
				temp = closureOperation(temp1);
				automatonNFA.push(temp);
				break;
				
			/*
			 * regular <pre>-processed expression don't contain dot(.) signal
			 * because dot signal is used wildcard
			 */
			/*
			case '.':
				flag = 0;
				operatorInStack = operator.peek();
				priority1 = getPriority1(operatorInStack);
				priority2 = getPriority2((byte) '.');
				if (priority2 < priority1) {
					temp2 = automatonNFA.pop();
					temp1 = automatonNFA.pop();
					temp = connectOperation(temp1, temp2);
					automatonNFA.push(temp);
				} else {
					operator.push((byte) '.');
				}
				break;
			*/

			case '|':
			case '+':
				flag = 0;
				operatorInStack = operator.peek();
				priority1 = getPriority1(operatorInStack);
				priority2 = getPriority2((byte) '+');
				if (priority2 < priority1) {
					while (priority2 < priority1) {
						temp2 = automatonNFA.pop();
						temp1 = automatonNFA.pop();
						if (operatorInStack == '+') {
							temp = unionOperation(temp1, temp2);
							automatonNFA.push(temp);
							break;
						} else {
							temp = connectOperation(temp1, temp2);
							automatonNFA.push(temp);
							operator.pop();
							operatorInStack = operator.peek();
							priority1 = getPriority1(operatorInStack);
						}
					}
				} else {
					operator.push((byte) '+');
				}
				break;
			default:
				if (!inputSymbolSet.contains("" + (char) regBytes[i])) {
					inputSymbolSet.add("" + (char) regBytes[i]);
				}
				if (flag == 1) {
					operatorInStack = operator.peek();
					priority1 = getPriority1(operatorInStack);
					priority2 = getPriority2((byte) '.');
					if (priority2 < priority1) {
						temp2 = automatonNFA.pop();
						temp1 = automatonNFA.pop();
						temp = connectOperation(temp1, temp2);
						automatonNFA.push(temp);
					} else {
						operator.push((byte) '.');
					}
				}
				temp = new AutomatonNFA();
				buildMetaAutomatonNFA(temp, regBytes[i]);
				automatonNFA.push(temp);
				flag = 1;
				break;
			} // end switch
		} // end for

		/** �Է���ջ�е��������ջ ,�������� */
		while (operator.peek() != '$') {
			operatorInStack = operator.pop();
			switch (operatorInStack) {
			case '.':
				temp2 = automatonNFA.pop();
				temp1 = automatonNFA.pop();
				temp = connectOperation(temp1, temp2);
				automatonNFA.push(temp);
				break;
			case '+':
				temp2 = automatonNFA.pop();
				temp1 = automatonNFA.pop();
				temp = unionOperation(temp1, temp2);
				automatonNFA.push(temp);
				break;
			default:
				break;
			}
		}

		automatonNFA.peek().setAutomatonName("RegNFA");
		automatonNFA.peek().setInputSymbolSet(inputSymbolSet);
		return automatonNFA.pop();

	} // end function

	/***
	 * Adjust regular expression to resolve dot(.) wildcard
	 * 
	 * @param in_Reg
	 * @return adjusted_Reg
	 */
	private String adjustReg(String in_Reg) {
		StringBuilder ret_Reg = new StringBuilder("");
		StringBuilder alphabet = new StringBuilder("");
		byte[] regBytes = in_Reg.getBytes();

		/** fill the alphabet */
		for (int i = 0; i < regBytes.length; i++) {
			switch (regBytes[i]) {
			case '(':
			case ')':
			case '*':
			case '+':
			case '|':
			case '.':
				break;
			default:
				alphabet.append((char) regBytes[i]);
				break;
			}
		}

		/** adjust the regular expression */
		for (int i = 0; i < regBytes.length; i++) {
			if (regBytes[i] == '.') {
				if (alphabet.length() > 1) {
					ret_Reg.append("(");
					for (int j = 0; j < alphabet.length(); j++) {
						ret_Reg.append(alphabet.charAt(j));
						ret_Reg.append("+");
					}
					ret_Reg.delete(ret_Reg.length() - 1, ret_Reg.length());
					ret_Reg.append(")");
				} else {
					for (int j = 0; j < alphabet.length(); j++) {
						ret_Reg.append(alphabet.charAt(j));
					}
				}
			} else {
				ret_Reg.append((char) regBytes[i]);
			}
		}
		return ret_Reg.toString();
	}

	/**
	 * Create Meta-NFA(NFA of single-letter)
	 * 
	 * @param temp
	 * @param regByte
	 */
	private void buildMetaAutomatonNFA(AutomatonNFA temp, byte regByte) {
		State from = new State(String.valueOf(RegToNFA.initialStateID++));
		State to = new State(String.valueOf(RegToNFA.initialStateID++),
				AutomatonConstant.STATETYPE_ACCEPTED);
		temp.addState(from);
		temp.addState(to);
		temp.setInitialState(from);

		ArrayList<Transition> transitions = new ArrayList<Transition>();
		temp.setTransitions(transitions);

		ArrayList<String> transCondition = new ArrayList<String>();
		transCondition.add(String.valueOf((char) regByte));
		TransitionNFA transitionNFA = new TransitionNFA(from, transCondition,
				to);

		temp.addTransition(transitionNFA);
	}

	/**
	 * AutomatonNFA Union Operation
	 * 
	 * @param temp1
	 * @param temp2
	 * @return AutomatonNFA
	 */
	private AutomatonNFA unionOperation(AutomatonNFA temp1, AutomatonNFA temp2) {
		AutomatonNFA temp = new AutomatonNFA();
		State from = new State(String.valueOf(RegToNFA.initialStateID++));
		State to = new State(String.valueOf(RegToNFA.initialStateID++),
				AutomatonConstant.STATETYPE_ACCEPTED);
		temp.addState(from);
		temp.addState(to);
		temp.setInitialState(from);

		ArrayList<Transition> transitions = new ArrayList<Transition>();
		temp.setTransitions(transitions);

		/** ��ʾ�ҵ�����״̬ */
		int flag = 0;

		/** ��ת������ */
		ArrayList<String> transCondition = new ArrayList<String>();
		transCondition.add("��");

		/** ����temp1������״̬ */
		Iterator<Entry<String, State>> iter = temp1.getStates().entrySet()
				.iterator();
		Entry<String, State> entry = null;
		while (iter.hasNext()) {
			entry = iter.next();
			temp.addState(entry.getValue());

			/** ��ӽ���״̬��ת�� */
			if (flag == 0
					&& entry.getValue().getStateType() == AutomatonConstant.STATETYPE_ACCEPTED) {
				flag = 1;
				entry.getValue().setStateType(
						AutomatonConstant.STATETYPE_NORMAL);
				TransitionNFA transitionNFA = new TransitionNFA(entry
						.getValue(), transCondition, to);
				temp.addTransition(transitionNFA);
			}
		}
		/** ����temp1������ת�� */
		for (int i = 0; i < temp1.getTransitions().size(); i++) {
			temp.addTransition(temp1.getTransitions().get(i));
		}

		flag = 0;

		/** ����temp2������״̬ */
		iter = temp2.getStates().entrySet().iterator();
		while (iter.hasNext()) {
			entry = iter.next();
			temp.addState(entry.getValue());

			/** ��ӽ���״̬��ת�� */
			if (flag == 0
					&& entry.getValue().getStateType() == AutomatonConstant.STATETYPE_ACCEPTED) {
				flag = 1;
				entry.getValue().setStateType(
						AutomatonConstant.STATETYPE_NORMAL);
				TransitionNFA transitionNFA = new TransitionNFA(entry
						.getValue(), transCondition, to);
				temp.addTransition(transitionNFA);
			}
		}
		/** ����temp2������ת�� */
		for (int i = 0; i < temp2.getTransitions().size(); i++) {
			temp.addTransition(temp2.getTransitions().get(i));
		}

		/** ��ӿ�ʼ״̬��ת�� */
		TransitionNFA transitionNFA1 = new TransitionNFA(from, transCondition,
				temp1.getInitialState());
		TransitionNFA transitionNFA2 = new TransitionNFA(from, transCondition,
				temp2.getInitialState());

		temp.addTransition(transitionNFA1);
		temp.addTransition(transitionNFA2);

		return temp;
	}

	/**
	 * AutomatonNFA Connect Operation
	 * 
	 * @param temp1
	 * @param temp2
	 * @return AutomatonNFA
	 */
	private AutomatonNFA connectOperation(AutomatonNFA temp1, AutomatonNFA temp2) {
		AutomatonNFA temp = new AutomatonNFA();
		temp.setInitialState(temp1.getInitialState());

		ArrayList<Transition> transitions = new ArrayList<Transition>();
		temp.setTransitions(transitions);

		/** ��ʾ�ҵ�����״̬ */
		int flag = 0;

		/** ��ת������ */
		ArrayList<String> transCondition = new ArrayList<String>();
		transCondition.add("��");

		/** ����temp1������״̬ */
		Iterator<Entry<String, State>> iter = temp1.getStates().entrySet()
				.iterator();
		Entry<String, State> entry = null;
		while (iter.hasNext()) {
			entry = iter.next();
			temp.addState(entry.getValue());

			/** ��ӽ���״̬��ת�� */
			if (flag == 0
					&& entry.getValue().getStateType() == AutomatonConstant.STATETYPE_ACCEPTED) {
				flag = 1;
				entry.getValue().setStateType(
						AutomatonConstant.STATETYPE_NORMAL);
				TransitionNFA transitionNFA = new TransitionNFA(entry
						.getValue(), transCondition, temp2.getInitialState());
				temp.addTransition(transitionNFA);
			}
		}
		/** ����temp1������ת�� */
		for (int i = 0; i < temp1.getTransitions().size(); i++) {
			temp.addTransition(temp1.getTransitions().get(i));
		}

		/** ����temp2������״̬ */
		iter = temp2.getStates().entrySet().iterator();
		while (iter.hasNext()) {
			entry = iter.next();
			temp.addState(entry.getValue());
		}
		/** ����temp2������ת�� */
		for (int i = 0; i < temp2.getTransitions().size(); i++) {
			temp.addTransition(temp2.getTransitions().get(i));
		}

		return temp;
	}

	/**
	 * AutomatonNFA Closure Operation
	 * 
	 * @param temp1
	 * @return AutomatonNFA
	 */
	private AutomatonNFA closureOperation(AutomatonNFA temp1) {
		AutomatonNFA temp = new AutomatonNFA();
		State from = new State(String.valueOf(RegToNFA.initialStateID++));
		State to = new State(String.valueOf(RegToNFA.initialStateID++),
				AutomatonConstant.STATETYPE_ACCEPTED);
		temp.addState(from);
		temp.addState(to);
		temp.setInitialState(from);

		ArrayList<Transition> transitions = new ArrayList<Transition>();
		temp.setTransitions(transitions);

		/** ��ʾ�ҵ�����״̬ */
		int flag = 0;

		/** ��ת������ */
		ArrayList<String> transCondition = new ArrayList<String>();
		transCondition.add("��");

		/** ����temp1������״̬ */
		Iterator<Entry<String, State>> iter = temp1.getStates().entrySet()
				.iterator();
		Entry<String, State> entry = null;
		while (iter.hasNext()) {
			entry = iter.next();
			temp.addState(entry.getValue());

			/** ��ӽ���״̬��ת�� */
			if (flag == 0
					&& entry.getValue().getStateType() == AutomatonConstant.STATETYPE_ACCEPTED) {
				flag = 1;
				entry.getValue().setStateType(
						AutomatonConstant.STATETYPE_NORMAL);
				TransitionNFA transitionNFA = new TransitionNFA(entry
						.getValue(), transCondition, temp1.getInitialState());
				temp.addTransition(transitionNFA);

				transitionNFA = new TransitionNFA(entry.getValue(),
						transCondition, to);
				temp.addTransition(transitionNFA);
			}
		}
		/** ����temp1������ת�� */
		for (int i = 0; i < temp1.getTransitions().size(); i++) {
			temp.addTransition(temp1.getTransitions().get(i));
		}

		/** ��ӿ�ʼ״̬��ת�� */
		TransitionNFA transitionNFA = new TransitionNFA(from, transCondition,
				temp1.getInitialState());
		temp.addTransition(transitionNFA);

		transitionNFA = new TransitionNFA(from, transCondition, to);
		temp.addTransition(transitionNFA);

		return temp;
	}

	/**
	 * Show the information of modified NFA
	 * 
	 * @param automatonNFA
	 */
	void printModifiedNFA(AutomatonNFA automatonNFA) {
		int sizeOfInputAlphabet = 0;
		int numberOfStates = 0;

		ArrayList<String> inputAlphabet = automatonNFA.getInputSymbolSet();
		ArrayList<Transition> transition = automatonNFA.getTransitions();
		HashMap<String, State> states = automatonNFA.getStates();

		StringBuilder[][] transTable = null;

		sizeOfInputAlphabet = inputAlphabet.size();
		numberOfStates = states.size();

		transTable = new StringBuilder[numberOfStates + 1][sizeOfInputAlphabet + 2];
		transTable[0][0] = new StringBuilder("");
		for (int i = 1; i < sizeOfInputAlphabet + 2; i++) {
			if (i == 1) {
				transTable[0][i] = new StringBuilder("��");
			} else {
				transTable[0][i] = new StringBuilder(inputAlphabet.get(i - 2));
			}
		}

		Iterator<Entry<String, State>> iter1 = states.entrySet().iterator();
		State temp = null;
		int k = 1;
		while (iter1.hasNext()) {
			temp = iter1.next().getValue();
			transTable[k][0] = new StringBuilder("");
			if (temp == automatonNFA.getInitialState()) {
				transTable[k][0].append("��");
			}
			if (temp.getStateType() == AutomatonConstant.STATETYPE_ACCEPTED) {
				transTable[k][0].append("*");
			}

			transTable[k][0].append(temp.getStateId());

			k++;
		}

		for (int i = 1; i < numberOfStates + 1; i++) {
			for (int j = 1; j < sizeOfInputAlphabet + 2; j++) {
				transTable[i][j] = new StringBuilder("");
			}
		}

		Iterator<Transition> iter2 = transition.iterator();
		Transition trans = null;
		StringBuilder strTemp = new StringBuilder("");

		while (iter2.hasNext()) {
			trans = iter2.next();
			if (trans.getFromState() == automatonNFA.getInitialState()) {
				strTemp.append("��");
			}
			if (trans.getFromState().getStateType() == AutomatonConstant.STATETYPE_ACCEPTED) {
				strTemp.append("*");
			}
			strTemp.append(trans.getFromState().getStateId());

			for (int i = 1; i < numberOfStates + 1; i++) {
				if (strTemp.toString().equals(transTable[i][0].toString())) {
					for (int j = 1; j < sizeOfInputAlphabet + 2; j++) {
						if (((TransitionNFA) trans).getTransitionConditions()
								.contains(transTable[0][j].toString())) {
							transTable[i][j].append(
									trans.getToState().getStateId())
									.append(",");
							// break;
						}
					}
					break;
				}
			}
			strTemp.delete(0, strTemp.length());
		}

		for (int i = 1; i < numberOfStates + 1; i++) {
			for (int j = 1; j < sizeOfInputAlphabet + 2; j++) {
				if (transTable[i][j].length() == 0) {
					transTable[i][j] = new StringBuilder("�0�1");
				} else {
					strTemp.append("{").append(transTable[i][j].toString());
					strTemp.delete(strTemp.length() - 1, strTemp.length());
					strTemp.append("}");
					transTable[i][j].delete(0, transTable[i][j].length());
					transTable[i][j].append(strTemp.toString());

					strTemp.delete(0, strTemp.length());
				}
			}
		}

		System.out.println("NFA����Ϣ���� :");
		System.out.println(automatonNFA.toString());

		System.out.println("NFA��״̬ת�Ʊ�Ϊ(ÿ�����ֱ�ʾһ��״̬):");
		for (int i = 0; i < numberOfStates + 1; i++) {
			for (int j = 0; j < sizeOfInputAlphabet + 2; j++) {
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
		System.out.println("\n\n");
	}

	/**
	 * Show tips for users easily to use
	 * 
	 * @param
	 */
	public static void showTips() {

		System.out.println("******************* ע��   ******************");
		System.out.println("*******************************************");
		System.out.println("������ʽ֧�ֵķ��ż��ϣ�A-Z a-z 0-9");
		System.out.println("������ʽ֧�ֵ���������ϣ�+ | * ()");
		System.out.println("������ʽ֧�ֵ�ͨ������ϣ�.");
		System.out.println("��Ҫ��������ʽ��ͬʱʹ�ò��������+ |");
		System.out.println("ͨ���(.)ֻ֧�ֶ���ĸ���������ַ���ͨ��");
		System.out.println("*******************************************");
		System.out.println("������������ʽ��");
	}

	/**
	 * Main for test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String reg;

		RegToNFA regToNFA = new RegToNFA();
		AutomatonNFA automatonNFA;

		showTips();

		while (scanner.hasNext()) {
			reg = scanner.nextLine();
			if ((automatonNFA = regToNFA.regToAutomatonNFA(reg)) != null) {
				System.out.println("������ʽ�ɹ�ת��ΪNFA...\n");
				regToNFA.printModifiedNFA(automatonNFA);
			} else {
				System.out.println("ת��ʧ��...\n");
			}

			showTips();
		}
	}
}
