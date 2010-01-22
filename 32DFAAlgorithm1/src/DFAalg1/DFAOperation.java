package DFAalg1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Exception.NotDFAException;
import Exception.NotThesameCharsetException;
import automaton.Automaton;
import automaton.AutomatonConstant;
import automaton.AutomatonDFA;
import automaton.Nail;
import automaton.State;
import automaton.Transition;
import automaton.TransitionDFA;

public class DFAOperation {

	/**
	 * complement of a DFA
	 * @param s Automaton one automaton
	 * @return Automaton a automaton which is the complement of source automaton
	 * @throws NotDFAException 
	 */
	public Automaton notOP(Automaton s)throws NotDFAException {
		// TODO Auto-generated method stub
		Automaton notAutomaton = new Automaton();
		TestFAType temp = new TestFAType();
		if(DFAalg1.DFAalg1Const.automatonType.NFA == temp.getAutomatonType(s)){
			throw new NotDFAException("computing complement of a DFA:the automaton is not a DFA");
		}
		
		ArrayList<String> oldSymbols = s.getInputSymbolSet();
		HashMap<String, State> oldStates = s.getStates();
		ArrayList<Transition> oldTransitions	= s.getTransitions();
		
		//设置notAutomaton的内容，采用完全的深拷贝的方法
		
		//拷贝symbols和states
		
		ArrayList<String> newSymbols = new ArrayList<String>();
		HashMap<String, State> newStates = new HashMap<String, State>();
		ArrayList<Transition> newTransitions = new ArrayList<Transition>();
		
		//深拷贝symbols
		for(int i = 0 ; i < oldSymbols.size(); i++){
			newSymbols.add((String)oldSymbols.get(i));
		}
		
		//深拷贝states
		for(Iterator itr = oldStates.keySet().iterator(); itr.hasNext();){ 
			String key = (String) itr.next(); 
			State value = (State) oldStates.get(key);
			byte newType = value.getStateType() == AutomatonConstant.STATETYPE_NORMAL?AutomatonConstant.STATETYPE_ACCEPTED:AutomatonConstant.STATETYPE_NORMAL;
			State st = new State(value.getStateId(), newType, new Nail(value.getStateNail().getNailX(),value.getStateNail().getNailY()));
			
			newStates.put(key, st);
		} 
		
		//深拷贝transition
		for(int i = 0 ; i  < oldTransitions.size(); i++){
			Transition oldTran = oldTransitions.get(i);
			ArrayList<String> condition = new ArrayList<String>();
			for(int j = 0 ; j < oldTran.getTransitionConditions().size(); j++){
				condition.add((String)oldTran.getTransitionConditions().get(j));
			}
			
			Transition tempTran = new TransitionDFA(newStates.get(oldTran.getFromState().getStateId()),condition , newStates.get(oldTran.getToState().getStateId()));
			newTransitions.add(tempTran);
		}
		
		notAutomaton.setAutomatonName(s.getAutomatonName());
		notAutomaton.setAutomatonType(s.getAutomatonType());
		notAutomaton.setInitialState(newStates.get(s.getInitialState().getStateId()));
		notAutomaton.setInputSymbolSet(newSymbols);
		notAutomaton.setStates(newStates);
		notAutomaton.setTransitions(newTransitions);
		
		return notAutomaton;
	}
	
	/**
	 * DFA intersection operation
	 * @param s1 Automaton one automaton
	 * @param s2 Automaton another automaton
	 * @return a Automaton automaton which is the intersection of s1 and s2
	 * @throws NotDFAException one of the input automaton is not a DFA
	 * @throws NotThesameCharsetException the character sets of the two DFA are not the same
	 */
	public Automaton intersectionOP(Automaton s1, Automaton s2) throws NotDFAException, NotThesameCharsetException{
		// TODO Auto-generated method stub
		
		TestFAType temp = new TestFAType();
		if(DFAalg1.DFAalg1Const.automatonType.NFA == temp.getAutomatonType(s1)){
			throw new NotDFAException("computing union of a DFA: the automaton is not a DFA");
		}
		if(DFAalg1.DFAalg1Const.automatonType.NFA == temp.getAutomatonType(s2)){
			throw new NotDFAException("computing union of a DFA: the automaton is not a DFA");
		}
		HashMap<String, HashMap<String, State>> statesMapping = new HashMap<String, HashMap<String, State>>();
		Automaton newAutomaton = constructPlusAutomaton(s1,s2,statesMapping);
		//设置结束状态
		HashMap<String,State> acceptStateS1 = s1.getAcceptedStates();
		HashMap<String,State> acceptStateS2 = s2.getAcceptedStates();
		for (Iterator<String> i = acceptStateS1.keySet().iterator(); i.hasNext();) {
			String oneStateID = (String) i.next();
			for (Iterator<String> j = acceptStateS2.keySet().iterator(); j.hasNext();) {
				String anotherStateID = (String) j.next();
				State acceptedState = statesMapping.get(oneStateID).get(anotherStateID);
				acceptedState.setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
			}
		}
		return newAutomaton;
	}
	
	
	/**
	 * DFA union operation
	 * @param s1 Automaton one automaton
	 * @param s2 Automaton another automaton
	 * @return a Automaton automaton which is the union of s1 and s2
	 * @throws NotDFAException 
	 * @throws NotThesameCharsetException 
	 */
	public Automaton unionOP(Automaton s1, Automaton s2) throws NotDFAException, NotThesameCharsetException{
		// TODO Auto-generated method stub
		TestFAType temp = new TestFAType();
		if(DFAalg1.DFAalg1Const.automatonType.NFA == temp.getAutomatonType(s1)){
			throw new NotDFAException("computing union of a DFA: the automaton is not a DFA");
		}
		if(DFAalg1.DFAalg1Const.automatonType.NFA == temp.getAutomatonType(s2)){
			throw new NotDFAException("computing union of a DFA: the automaton is not a DFA");
		}
		HashMap<String, HashMap<String, State>> statesMapping = new HashMap<String, HashMap<String, State>>();
		Automaton newAutomaton = constructPlusAutomaton(s1,s2,statesMapping);
		
		//设置结束状态
		HashMap<String,State> acceptStateS1 = s1.getAcceptedStates();
		HashMap<String,State> acceptStateS2 = s2.getAcceptedStates();
		
		for (Iterator<String> i = acceptStateS1.keySet().iterator(); i.hasNext();) {
			String oneStateID = (String) i.next();
			for (Iterator<String> j = s2.getStates().keySet().iterator(); j.hasNext();) {
				String anotherStateID = (String) j.next();
				State acceptedState = statesMapping.get(oneStateID).get(anotherStateID);
				acceptedState.setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
			}
		}
		
		for (Iterator<String> i = acceptStateS2.keySet().iterator(); i.hasNext();) {
			String anotherStateID = (String) i.next();
			for (Iterator j = s1.getStates().keySet().iterator(); j.hasNext();) {
				String oneStateID = (String) j.next();
				State acceptedState = statesMapping.get(oneStateID).get(anotherStateID);
				acceptedState.setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
			}
		}
		
		return newAutomaton;
	}
	
	/**
	 * 两个自动机的合并后生成的自动i
	 * @param s1 Automaton 一个自动机
	 * @param s2 Automaton  另外一个自动机
	 * @param statesMapping 是HashMap<String, State>>类型可以通过两个状态的ID索引到这两个状态合并后的对应状态
	 * @return <Automaton> 合并后的自动机
	 * @throws NotThesameCharsetException 两个自动机的字符集合不相同
	 */
	private Automaton constructPlusAutomaton(Automaton s1, Automaton s2, HashMap<String, HashMap<String, State>> statesMapping) throws NotThesameCharsetException{
		Automaton combineAuto = new AutomatonDFA();
		
		//获取第一个自动机的相关信息
		ArrayList<String> symbolsS1 = s1.getInputSymbolSet();
		HashMap<String, State> statesS1 = s1.getStates();
		ArrayList<Transition> transitionsS1	= s1.getTransitions();
		
		//获取第二个自动机的相关信息
		ArrayList<String> symbolsS2 = s2.getInputSymbolSet();
		HashMap<String, State> statesS2 = s2.getStates();
		ArrayList<Transition> transitionsS2	= s2.getTransitions();
		
		//比较两个自动机的字符集合是否相同
		for (int i = 0; i < symbolsS1.size(); i++) {
			boolean isFind = false;
			String symbol1 = symbolsS1.get(i);
			for (int j = 0; j < symbolsS2.size(); j++) {
				String symbol2 = symbolsS2.get(j);
				if(symbol1.equals(symbol2)){
					isFind = true;
					break;
				}
			}
			if(isFind){
				continue;
			}else{
				throw new NotThesameCharsetException("the character sets of the tow DFA are not the same");
			}	
		}
		
		combineAuto.setAutomatonName(s1.getAutomatonName() + s2.getAutomatonName());
		combineAuto.setAutomatonType(AutomatonConstant.AUTOMATONTYPES[AutomatonConstant.AUTOMATA_DFA]);
		
		//建立新的字符集合
		ArrayList<String> newSymbolsSet = new ArrayList<String>();
		for (int i = 0; i < symbolsS1.size(); i++) {
			String symbol = symbolsS1.get(i);
			newSymbolsSet.add(symbol);
		}
		combineAuto.setInputSymbolSet(newSymbolsSet);
		
		//建立新的自动机的状态集合
		HashMap<String,State> newStates = combineStates(statesS1,statesS2,statesMapping);
		combineAuto.setStates(newStates);
		
		//建立maping形式的转移HashMap<String, HashMap<String, state>>代表的转移从起始状态在字符中转移到状态
		HashMap<String, HashMap<String, State>> mappingTransitionsS1 = getMappingTransitions(transitionsS1);
		HashMap<String, HashMap<String, State>> mappingTransitionsS2 = getMappingTransitions(transitionsS2);
		
		//建立所有的转移
		ArrayList<Transition> transitions = generateTransitions(mappingTransitionsS1,mappingTransitionsS2,statesMapping,newSymbolsSet);
		combineAuto.setTransitions(transitions);
		
		//设置初始状态
		String oneInitID = s1.getInitialState().getStateId();
		String anotherInitID = s2.getInitialState().getStateId();
		
		combineAuto.setInitialState(statesMapping.get(oneInitID).get(anotherInitID));
		
		return combineAuto;
	}
	

	/**
	 * 创建两个自动机合并后的所有状态转移
	 * @param mappingTransitionsS1 一个自动机的转移，数据结构HashMap<String, HashMap<String, State>>，通过出发的状态ID和转移条件快速索引到到达状态
	 * @param mappingTransitionsS2 另一个自动机的转移，数据结构HashMap<String, HashMap<String, State>>，通过出发的状态ID和转移条件快速索引到到达状态
	 * @param statesMapping 是HashMap<String, State>>类型可以通过两个状态的ID索引到这两个状态合并后的对应状态
	 * @param newSymbolsSet 自动机的转移条件的字符集合
	 * @return 所有的转移ArrayList<Transition>
	 */
	private ArrayList<Transition> generateTransitions(
			HashMap<String, HashMap<String, State>> mappingTransitionsS1,
			HashMap<String, HashMap<String, State>> mappingTransitionsS2,
			HashMap<String, HashMap<String, State>> statesMapping, 
			ArrayList<String> newSymbolsSet
			) {
		// TODO Auto-generated method stub
		
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		HashMap<String,HashMap<String,ArrayList<String>>> newFromToConditions = new HashMap<String,HashMap<String,ArrayList<String>>>();

		for (Iterator<String> i = statesMapping.keySet().iterator(); i.hasNext();) {
			String oneStateID = (String) i.next(); 
			HashMap<String, State> anotherState =  statesMapping.get(oneStateID);
			for (Iterator<String> j = anotherState.keySet().iterator(); j.hasNext();) {
				String anotherStateID = (String)j.next();
				State fromState = anotherState.get(anotherStateID);
				for(int symbolIndex = 0 ; symbolIndex < newSymbolsSet.size(); symbolIndex++){
					String symbol = newSymbolsSet.get(symbolIndex);
					State stateQ1 = mappingTransitionsS1.get(oneStateID).get(symbol);
					State stateQ2 = mappingTransitionsS2.get(anotherStateID).get(symbol);
					
					State toState = statesMapping.get(stateQ1.getStateId()).get(stateQ2.getStateId());
					ArrayList<String> conditions;
					if(newFromToConditions.get(fromState.getStateId())!= null){
						if(newFromToConditions.get(fromState.getStateId()).get(toState.getStateId()) != null){
							conditions = newFromToConditions.get(fromState.getStateId()).get(toState.getStateId());
							conditions.add(symbol);
						}else{
							conditions = new ArrayList<String>();
							conditions.add(symbol);
							transitions.add(new TransitionDFA(fromState,conditions,toState));
							newFromToConditions.get(fromState.getStateId()).put(toState.getStateId(), conditions);
						}
					}else{
						conditions = new ArrayList<String>();
						conditions.add(symbol);
						transitions.add(new TransitionDFA(fromState,conditions,toState));
						HashMap<String,ArrayList<String>> toIndexConditions = new HashMap<String,ArrayList<String>>();
						toIndexConditions.put(toState.getStateId(), conditions);
						newFromToConditions.put(fromState.getStateId(),toIndexConditions);
					}
				}
			
			}
		}

		return transitions;
	}
	
	/**
	 * 合并两个自动机的状态，获得自动机合并后的状态集合
	 * @param statesS1 HashMap<String, State> 一个自动机的所有状态
	 * @param statesS2 HashMap<String, State> 另外一个自动机的所有状态
	 * @param statesMapping HashMap<String, HashMap<String, State>> 建立两个状态的ID索引到这两个状态合并后的对应状态
	 * @return HashMap<String, State> 自动机合并后的状态集合
	 */
	private HashMap<String, State> combineStates(
			HashMap<String, State> statesS1, HashMap<String, State> statesS2,
			HashMap<String, HashMap<String, State>> statesMapping) {
		// TODO Auto-generated method stub
		HashMap<String, State> newStates = new HashMap<String, State>();
		
		for(Iterator<String> itrS1 = statesS1.keySet().iterator(); itrS1.hasNext();){ 
			String oneStateID = (String) itrS1.next(); 
			State oneState = (State) statesS1.get(oneStateID);
			for(Iterator<String> itrS2 = statesS2.keySet().iterator(); itrS2.hasNext();){ 
				String anotherStateID = (String) itrS2.next(); 
				State anotherState = (State) statesS2.get(oneStateID);
				String newStateID = oneStateID+"_"+anotherStateID;
				while(newStates.containsKey(newStateID)){
					newStateID += "_";
				}
				State newState = new State(newStateID,AutomatonConstant.STATETYPE_NORMAL);
				newStates.put(newStateID, newState);
				
				HashMap<String, State> mapping;
				if(statesMapping.get(oneStateID) == null){
					mapping = new HashMap<String, State>(); 
					statesMapping.put(oneStateID, mapping);
				}else{
					mapping = statesMapping.get(oneStateID);
				}
				mapping.put(anotherStateID, newState);	
			}
		}
		return newStates;
	}
	/**
	 * 通过原有的转移创建可以通过出发状态ID和转移条件快速索引到达状态的转移
	 * @param oldTransitions ArrayList<Transition> 原始的转移
	 * @return HashMap<String, HashMap<String, State>> 可以通过出发状态ID（String）和转移条件（String）快速索引到达状态的转移
	 */
	private HashMap<String, HashMap<String, State>> getMappingTransitions(
			ArrayList<Transition> oldTransitions) {
		// TODO Auto-generated method stub
		HashMap<String,HashMap<String,State>> fromToTrans = new HashMap<String,HashMap<String, State>>();
		
		for(int i = 0 ; i < oldTransitions.size(); i++){
			Transition oldTran = oldTransitions.get(i);
			ArrayList<String> oldCondition = (ArrayList<String>) oldTran.getTransitionConditions();
			HashMap<String,State> tempFromTo;
			//判断是否已经存在以该起始状态的hashMap
			if(fromToTrans.get(oldTran.getFromState().getStateId()) == null){
				tempFromTo = new HashMap<String,State>();
				fromToTrans.put(oldTran.getFromState().getStateId(), tempFromTo);
			}
			tempFromTo = fromToTrans.get(oldTran.getFromState().getStateId());
	
			//构造fromToTrans和toFromTrans
			for(int j = 0 ; j < oldCondition.size(); j++){
				//构造fromToTrans
				tempFromTo.put(oldCondition.get(j), oldTran.getToState());
			}
		}
		return fromToTrans;
	}
}
