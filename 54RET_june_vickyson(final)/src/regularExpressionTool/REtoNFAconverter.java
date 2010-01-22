package regularExpressionTool;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Automata.Automata;
import Automata.NFA;
import Automata.State;
import Automata.Transition;
import GUI.MainForm;

/**
 * 正则表达式到NFA的转换器
 * @author 刘军娥、卫松  2009-12-26
 * 
 */
public class REtoNFAconverter {
	private RE re=new RE();//解析好的正则表达式
	private String s=new String();//待检测的字符串
	
	/**
	 * 构造函数
	 * @param re 正则表达式
	 * @param s  待检测的字符串
	 */
	public REtoNFAconverter(RE re,String s){
		this.setRe(re);
		this.setS(s);	
	}
	
	public boolean check(){
		return false;
	}

	public void setRe(RE re) {
		this.re = re;
	}

	public RE getRe() {
		return re;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getS() {
		return s;
	}
	
	
	/**
	 *  将正则表达式转换为NFA
	 * @param re 待转换的正则表达式
	 * @return 转换好的NFA
	 */
	public NFA convertToNFA(RE re) {
		NFA nfa=new NFA();
		State startState=new State();
		State endState=new State();
		startState.setStyle(State.START_S);
		endState.setStyle(State.FINAL_S);
		REtoNFAconverter rtnc=new REtoNFAconverter(re, re.getReString());
		if(rtnc.convert(re, nfa, 0, startState, endState)){
			/*NFA的初始状态*/
			nfa.setStartQ(startState);
			
			/*NFA的接受状态*/
			List<State> finalStates = new LinkedList<State>();
			finalStates.add(0, endState);
			nfa.setFinalStates(finalStates);
			
			/*NFA的输入符号集*/
			Set<Character> symbols = new HashSet<Character>();
			int t=re.getReString().length();
			for (int i = 0; i < t; i++) {
				if(re.getReString().charAt(i)!='+' && re.getReString().charAt(i)!='*' && 
						re.getReString().charAt(i)!='*' && re.getReString().charAt(i)!='(' &&
						re.getReString().charAt(i)!=')' &&re.getReString().charAt(i)!='&'){
					if(symbols.add(re.getReString().charAt(i))){
						symbols.add(new Character('ε'));
					}
				}
			}
			nfa.setSymbols(symbols);
			
			/*NFA的状态转移函数*/
			System.out.println("转换完毕！\n\n输出状态转移函数:");
			List<Transition> transfers = new LinkedList<Transition>();
			for (int i=0;i<MainForm.stateIDnumber;i++) {
				for (Character character : symbols) {
					if(nfa.getStates().get(new String("q"+i)).getTrans().get(new String(String.valueOf(character)))!=null){
						for (Transition transition : nfa.getStates().get(new String("q"+i)).getTrans().get(new String(String.valueOf(character)))) {
							if(transfers.add(transition)){
								System.out.println(transition.getBeginState().getStateId()+"\t"+transition.getInputString()+"\t"+transition.getEndState().getStateId());
							}
						}
					}

				}
			}
			
			return nfa;
		}
		else{
			return null;
		}
	}
		
	/**
	 * 将正则表达式转换为NFA
	 * @param re	待转换的正则表达式
	 * @param nfa	转换后的NFA
	 * @param index	状态名下标
	 * @param startState	第一个状态
	 * @param endState	第二个状态
	 * @return	返回是否转换成功
	 */
	public boolean convert(RE re,NFA nfa,int index,State startState,State endState) {
		/*构造NFA*/
		int stateID=index;
		startState.setStateId("q"+stateID++);
		endState.setStateId("q"+stateID++);
		MainForm.stateIDnumber=stateID;
		Transition tran=new Transition(startState,re.getReString(),endState);
		startState.addTransfer(re.getReString(), tran);
		System.out.println(startState.getStateId()+"\t"+re.getReString()+"\t"+endState.getStateId());
		nfa.addState(startState);
		nfa.addState(endState);
			
		/*递归构造NFA*/
		/*处理“+”号*/
		if(re.getOperationChar()=='+'){
			State[][] stateArray=new State[re.getRes().size()][2];
			for (int i = 0; i < re.getRes().size(); i++) {
				stateArray[i][0]=new State();
				stateArray[i][1]=new State();
			}
			for (int i = 0; i < re.getRes().size(); i++){
				if(convert(re.getRes().get(i),nfa,MainForm.stateIDnumber,stateArray[i][0],stateArray[i][1])){
					Transition tranOR1=new Transition(startState,String.valueOf(Automata.EPSILON),stateArray[i][0]);
					startState.addTransfer(String.valueOf(Automata.EPSILON), tranOR1);
					System.out.println(startState.getStateId()+"\t"+String.valueOf(Automata.EPSILON)+"\t"+stateArray[i][0].getStateId());
					Transition tranOR2=new Transition(stateArray[i][1],String.valueOf(Automata.EPSILON),endState);
					stateArray[i][1].addTransfer(String.valueOf(Automata.EPSILON), tranOR2);
					System.out.println(stateArray[i][1].getStateId()+"\t"+String.valueOf(Automata.EPSILON)+"\t"+endState.getStateId());
				}
				else{
					MainForm.stateIDnumber=0;
					return false;
				}
			}
			
		}
		/*处理“&”号*/
		else if(re.getOperationChar()=='&'){
			State[][] statesArray=new State[re.getRes().size()][2];
			for (int i = 0; i < re.getRes().size(); i++) {
				statesArray[i][0]=new State();
				statesArray[i][1]=new State();
			}
			
			for (int i = 0; i < re.getRes().size(); i++) {
				if(convert(re.getRes().get(i),nfa,MainForm.stateIDnumber,statesArray[i][0],statesArray[i][1])){
					if(i>0){
						Transition tranAND=new Transition(statesArray[i-1][1],String.valueOf(Automata.EPSILON),statesArray[i][0]);
						statesArray[i-1][1].addTransfer(String.valueOf(Automata.EPSILON), tranAND);
						System.out.println(statesArray[i-1][1].getStateId()+"\t"+String.valueOf(Automata.EPSILON)+"\t"+statesArray[i][0].getStateId());
					}
				}
				else{
					MainForm.stateIDnumber=0;
					return false;
				}
				
			}
			Transition tranFirst=new Transition(startState, String.valueOf(Automata.EPSILON), statesArray[0][0]);
			startState.addTransfer(String.valueOf(Automata.EPSILON), tranFirst);
			System.out.println(startState.getStateId()+"\t"+String.valueOf(Automata.EPSILON)+"\t"+statesArray[0][0].getStateId());
			Transition tranSecond=new Transition(statesArray[re.getRes().size()-1][1], String.valueOf(Automata.EPSILON),endState);
			statesArray[re.getRes().size()-1][1].addTransfer(String.valueOf(Automata.EPSILON), tranSecond);
			System.out.println(statesArray[re.getRes().size()-1][1].getStateId()+"\t"+String.valueOf(Automata.EPSILON)+"\t"+endState.getStateId());
		}
		/*处理“*”号*/
		else if(re.getOperationChar()=='*'){
			State startState3=new State();
			State endState3=new State();
			if(convert(re.getRes().get(0),nfa,MainForm.stateIDnumber,startState3,endState3)){
				Transition tranStar1=new Transition(endState3,String.valueOf(Automata.EPSILON),startState3);
				endState3.addTransfer(String.valueOf(Automata.EPSILON), tranStar1);
				System.out.println(endState3.getStateId()+"\t"+String.valueOf(Automata.EPSILON)+"\t"+startState3.getStateId());
				Transition tranStar2=new Transition(startState,String.valueOf(Automata.EPSILON),endState);
				startState.addTransfer(String.valueOf(Automata.EPSILON), tranStar2);
				System.out.println(startState.getStateId()+"\t"+String.valueOf(Automata.EPSILON)+"\t"+endState.getStateId());
				Transition tranStar3=new Transition(startState, String.valueOf(Automata.EPSILON), startState3);
				startState.addTransfer(String.valueOf(Automata.EPSILON), tranStar3);
				System.out.println(startState.getStateId()+"\t"+String.valueOf(Automata.EPSILON)+"\t"+startState3.getStateId());
				Transition tranStar4=new Transition(endState3, String.valueOf(Automata.EPSILON), endState);
				endState3.addTransfer(String.valueOf(Automata.EPSILON), tranStar4);
				System.out.println(endState3.getStateId()+"\t"+String.valueOf(Automata.EPSILON)+"\t"+endState.getStateId());
			}
			else{
				MainForm.stateIDnumber=0;
				return false;
			}
		}
		else{
			return true;
		}
		
		return true;
	}
}
