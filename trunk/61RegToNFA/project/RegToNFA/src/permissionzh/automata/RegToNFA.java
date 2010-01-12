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

		/** 开始字符验证（不能为并和闭包运算符） */
		if (regLength > 0) {
			if (regBytes[0] == '+' || regBytes[0] == '*' || regBytes[0] == '.'
					|| regBytes[0] == '|') {
				System.out.println("正则表达式开始符号错误！");
				return false;
			}
		}

		for (int i = 0; i < regLength; i++) {
			b = regBytes[i];
			/** 验证约束（括号配对、不应该相连的运算符） */
			{
				/** 括号配对 */
				{
					if (b == '(') {
						if (count >= 0)
							count++;
						else {
							System.out.println("括号不匹配，右括号比左括号多！");
							return false;
						}
					}
					if (b == ')') {
						if (count > 0)
							count--;
						else {
							System.out.println("括号不匹配，左括号比左括号多！");
							return false;
						}
					}
				}
				/** 相连运算符的判断 */
				{
					if (preOperator != '@') {
						if (preOperator == '(') {
							if (b == '+' || b == '|' || b == '*' || b == '.') {
								System.out.println("左括号和运算符非法连接！");
								return false;
							}
						}
						if (preOperator == '+' || preOperator == '|') {
							if (b == '+' || b == '|' || b == ')' || b == '*'
									|| b == '.') {
								System.out.println("并运算符和其他运算符非法连接！");
								return false;
							}
						}
						if (preOperator == '*') {
							if (b == '*') {
								System.out.println("无意义的闭包运算符连接！");
								return false;
							}
						}
						if (preOperator == '.') {
							if (b == '+' || b == '|' || b == ')' || b == '*'
									|| b == '.') {
								System.out.println("连接运算符和其他运算符非法连接！");
								return false;
							}
						}
					}
				}
			}
			/** 验证输入字符的合法性 */
			{
				/** b 为 0-9 或者 A-Z 或者 a-z 中的字符 */
				if ((48 <= b && b <= 57) || (65 <= b && b <= 90)
						|| (97 <= b && b <= 122))
					preOperator = '@';
				/** b 为 合法定义的运算符 */
				else if (b == '*' || b == '|' || b == '+' || b == '('
						|| b == ')' || b == '.')
					preOperator = b;
				else {
					System.out.println("正则表达式含有非法字符！");
					return false;
				}
			}
			/** 验证输入的并运算符的一致性 */
			if (b == '+') {
				if (flag2 != 0) {
					System.out.println("正则表达式 中并运算符不一致！");
					return false;
				}
				flag1 = 1;
			}
			if (b == '|') {
				if (flag1 != 0) {
					System.out.println("正则表达式 中并运算符不一致！");
					return false;
				}
				flag2 = 1;
			}
		}
		/** 结束字符验证（不能为并和连接运算符） */
		if (b == '+' || b == '|' || b == '.') {
			System.out.println("正则表达式 中结尾字符不合法！");
			return false;
		}
		/** 括号配对 */
		if (count != 0) {
			System.out.println("正则表达式中括号不配对！");
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

		/** 开始字符验证（不能为并和闭包运算符） */
		if (regLength > 0) {
			if (regBytes[0] == '+' || regBytes[0] == '*' || regBytes[0] == '|') {
				System.out.println("正则表达式开始符号错误！");
				return false;
			}
		}

		for (int i = 0; i < regLength; i++) {
			b = regBytes[i];
			/** 验证约束（括号配对、不应该相连的运算符） */
			{
				/** 括号配对 */
				{
					if (b == '(') {
						if (count >= 0)
							count++;
						else {
							System.out.println("括号不匹配，右括号比左括号多！");
							return false;
						}
					}
					if (b == ')') {
						if (count > 0)
							count--;
						else {
							System.out.println("括号不匹配，左括号比左括号多！");
							return false;
						}
					}
				}
				/** 相连运算符的判断 */
				{
					if (preOperator != '@') {
						if (preOperator == '(') {
							if (b == '+' || b == '|' || b == '*') {
								System.out.println("左括号和运算符非法连接！");
								return false;
							}
						}
						if (preOperator == '+' || preOperator == '|') {
							if (b == '+' || b == '|' || b == ')' || b == '*') {
								System.out.println("并运算符和其他运算符非法连接！");
								return false;
							}
						}
						/*
						 * this restriction is just used to make regular
						 * expression easy to understand,but not necessary.
						 * 
						 * if (preOperator == '*') { if (b == '*') {
						 * System.out.println("无意义的闭包运算符连接！"); return false; } }
						 */
					}
				}
			}
			/** 验证输入字符的合法性 */
			{
				/** b 为 0-9 或者 A-Z 或者 a-z 中的字符 */
				if ((48 <= b && b <= 57) || (65 <= b && b <= 90)
						|| (97 <= b && b <= 122)) {
					preOperator = '@';
				}
				/** b 为 合法定义的运算符 */
				else if (b == '*' || b == '|' || b == '+' || b == '('
						|| b == ')') {
					preOperator = b;
				}
				/** b 为 合法定义的通配符 */
				else if (b == '.') {
					preOperator = '@';
				} else {
					System.out.println("正则表达式含有非法字符！");
					return false;
				}
			}
			/** 验证输入的并运算符的一致性 */
			if (b == '+') {
				if (flag2 != 0) {
					System.out.println("正则表达式 中并运算符不一致！");
					return false;
				}
				flag1 = 1;
			}
			if (b == '|') {
				if (flag1 != 0) {
					System.out.println("正则表达式 中并运算符不一致！");
					return false;
				}
				flag2 = 1;
			}
		}
		/** 结束字符验证（不能为并运算符） */
		if (b == '+' || b == '|') {
			System.out.println("正则表达式 中结尾字符不合法！");
			return false;
		}
		/** 括号配对 */
		if (count != 0) {
			System.out.println("正则表达式中括号不配对！");
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

		/** 运算符栈 */
		Stack<Byte> operator = new Stack<Byte>();
		/** 自动机栈 */
		Stack<AutomatonNFA> automatonNFA = new Stack<AutomatonNFA>();

		/** 运算符优先级，栈内：priority1 ；栈外：priority2 */
		int priority1, priority2;
		/** 栈内运算符 */
		byte operatorInStack;

		/** 保存临时的NFA */
		AutomatonNFA temp, temp1, temp2;
		byte[] regBytes = in_reg.getBytes();

		/** 保存字母表 */
		ArrayList<String> inputSymbolSet = new ArrayList<String>();

		/** 指示上个输入字符的类型，0：运算符；1：字母 */
		int flag = 0;

		/** 压入符号栈栈底符号 */
		operator.push((byte) '$');

		/** 通过 for 循环将正则表达式转换为 NFA */
		for (int i = 0; i < regBytes.length; i++) {
			switch (regBytes[i]) {
			/** 遇到左括号，且自动机栈不空，压入连接符 */
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
			/** 遇到闭包运算符，直接将自动机栈顶元素弹出，进行运算, 再将其压入自动机栈即可 */
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

		/** 对符号栈中的运算符出栈 ,进行运算 */
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

		/** 标示找到结束状态 */
		int flag = 0;

		/** 空转移条件 */
		ArrayList<String> transCondition = new ArrayList<String>();
		transCondition.add("ε");

		/** 复制temp1的所有状态 */
		Iterator<Entry<String, State>> iter = temp1.getStates().entrySet()
				.iterator();
		Entry<String, State> entry = null;
		while (iter.hasNext()) {
			entry = iter.next();
			temp.addState(entry.getValue());

			/** 添加结束状态空转移 */
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
		/** 复制temp1的所有转移 */
		for (int i = 0; i < temp1.getTransitions().size(); i++) {
			temp.addTransition(temp1.getTransitions().get(i));
		}

		flag = 0;

		/** 复制temp2的所有状态 */
		iter = temp2.getStates().entrySet().iterator();
		while (iter.hasNext()) {
			entry = iter.next();
			temp.addState(entry.getValue());

			/** 添加结束状态空转移 */
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
		/** 复制temp2的所有转移 */
		for (int i = 0; i < temp2.getTransitions().size(); i++) {
			temp.addTransition(temp2.getTransitions().get(i));
		}

		/** 添加开始状态空转移 */
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

		/** 标示找到结束状态 */
		int flag = 0;

		/** 空转移条件 */
		ArrayList<String> transCondition = new ArrayList<String>();
		transCondition.add("ε");

		/** 复制temp1的所有状态 */
		Iterator<Entry<String, State>> iter = temp1.getStates().entrySet()
				.iterator();
		Entry<String, State> entry = null;
		while (iter.hasNext()) {
			entry = iter.next();
			temp.addState(entry.getValue());

			/** 添加结束状态空转移 */
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
		/** 复制temp1的所有转移 */
		for (int i = 0; i < temp1.getTransitions().size(); i++) {
			temp.addTransition(temp1.getTransitions().get(i));
		}

		/** 复制temp2的所有状态 */
		iter = temp2.getStates().entrySet().iterator();
		while (iter.hasNext()) {
			entry = iter.next();
			temp.addState(entry.getValue());
		}
		/** 复制temp2的所有转移 */
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

		/** 标示找到结束状态 */
		int flag = 0;

		/** 空转移条件 */
		ArrayList<String> transCondition = new ArrayList<String>();
		transCondition.add("ε");

		/** 复制temp1的所有状态 */
		Iterator<Entry<String, State>> iter = temp1.getStates().entrySet()
				.iterator();
		Entry<String, State> entry = null;
		while (iter.hasNext()) {
			entry = iter.next();
			temp.addState(entry.getValue());

			/** 添加结束状态空转移 */
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
		/** 复制temp1的所有转移 */
		for (int i = 0; i < temp1.getTransitions().size(); i++) {
			temp.addTransition(temp1.getTransitions().get(i));
		}

		/** 添加开始状态空转移 */
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
				transTable[0][i] = new StringBuilder("ε");
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
				transTable[k][0].append("→");
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
				strTemp.append("→");
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
					transTable[i][j] = new StringBuilder("Ø");
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

		System.out.println("NFA的信息如下 :");
		System.out.println(automatonNFA.toString());

		System.out.println("NFA的状态转移表为(每个数字表示一个状态):");
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

		System.out.println("******************* 注意   ******************");
		System.out.println("*******************************************");
		System.out.println("正则表达式支持的符号集合：A-Z a-z 0-9");
		System.out.println("正则表达式支持的运算符集合：+ | * ()");
		System.out.println("正则表达式支持的通配符集合：.");
		System.out.println("不要在正则表达式中同时使用并运算符：+ |");
		System.out.println("通配符(.)只支持对字母表中任意字符的通配");
		System.out.println("*******************************************");
		System.out.println("请输入正则表达式：");
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
				System.out.println("正则表达式成功转换为NFA...\n");
				regToNFA.printModifiedNFA(automatonNFA);
			} else {
				System.out.println("转换失败...\n");
			}

			showTips();
		}
	}
}
