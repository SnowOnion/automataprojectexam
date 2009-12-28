package DFAalg1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import Exception.NotDFAException;
import automaton.Automaton;
import automaton.AutomatonDFA;
import automaton.Nail;
import automaton.State;
import automaton.Transition;
import automaton.TransitionDFA;

public class DFAminAlg {
	/**
	 * minimal algorithm
	 * @param s Automaton
	 * @return Automaton the minimal of a automaton
	 * @throws NotDFAException
	 */
	public Automaton minOP(Automaton s)throws NotDFAException {
		// TODO Auto-generated method stub
		TestFAType temp = new TestFAType();
		if(DFAalg1.DFAalg1Const.automatonType.NFA == temp.getAutomatonType(s)){
			throw new NotDFAException("computing minimal of a DFA: the automaton is not a DFA");
		}
		ArrayList<String> oldSymbols = s.getInputSymbolSet();
		HashMap<String, State> oldStates = s.getStates();
		ArrayList<Transition> oldTransitions	= s.getTransitions();
		
		//HashMap<String,HashMap<String,State>> 第一个String表示了转移开始的状态，第二个String表示了转移的条件，得到的state表示转移到的状态。
		//用于快速索引到转移指向的状态
		HashMap<String,HashMap<String,State>> fromToTrans = new HashMap<String,HashMap<String, State>>();
		//HashMap<String,HashMap<String,State>> 第一个String表示了转移结束的状态，第二个String表示了转移的条件，得到的state表示转移到的状态。
		//用于快速索引到转移指向的状态
		HashMap<String,HashMap<String,ArrayList<State>>> toFromTrans = new HashMap<String,HashMap<String,ArrayList<State>>>();
		//构造fromToTrans和toFromTrans 用来快速的查找到状态转移到得状态
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
			
			//构造toFromTrans
			HashMap<String,ArrayList<State>> tempToFrom; 
			if(toFromTrans.get(oldTran.getToState().getStateId()) == null){
				tempToFrom = new HashMap<String,ArrayList<State>>();
				toFromTrans.put(oldTran.getToState().getStateId(), tempToFrom);
			}
			tempToFrom = toFromTrans.get(oldTran.getToState().getStateId());
			//构造fromToTrans和toFromTrans
			for(int j = 0 ; j < oldCondition.size(); j++){
				//构造fromToTrans
				tempFromTo.put(oldCondition.get(j), oldTran.getToState());
				
				//构造toFromTrans
				if(tempToFrom.get(oldCondition.get(j))== null){
					ArrayList<State> states = new ArrayList<State>();
					states.add(oldTran.getFromState());
					tempToFrom.put(oldCondition.get(j), states);
				}else{
					ArrayList<State> states = tempToFrom.get(oldCondition.get(j));
					states.add(oldTran.getFromState());
				}
			}
		}
		
		HashMap<String, State> eliminatedStates = eliminateUselessStates(fromToTrans,s.getInitialState());
		State[] sortedStates = new State[eliminatedStates.size()];
		int i = 0;
		for(Iterator itr = eliminatedStates.keySet().iterator(); itr.hasNext();i++){ 
			String key = (String) itr.next(); 
			State value = (State) oldStates.get(key);
			sortedStates[i] = value;
		}
		byte[][] fillingTable = tableFillingAlg(eliminatedStates,sortedStates,s,toFromTrans);
		Automaton minAuto = constructNewMinAutomaton(eliminatedStates,sortedStates, fillingTable, s,fromToTrans);
		return minAuto;
	}

	/**
	 * 消除不可达状态
	 * @param fromToTrans HashMap<String,HashMap<String,State>> 通过出发的状态ID和转移条件快速索引到到达状态
	 * @param initialState State 开始状态
	 * @return HashMap<String,State> 消除了不可达状态的状态集合
	 */
	private HashMap<String,State> eliminateUselessStates(HashMap<String,HashMap<String,State>> fromToTrans,State initialState){
		HashMap<String,State> eliminatedStates = new HashMap<String,State>();
		//采用BFS的搜索方式消除不可达状态
		java.util.Queue<State> bfsQuene = new LinkedList<State>();
		State newInit = new State(initialState.getStateId(), initialState.getStateType(), new Nail(initialState.getStateNail().getNailX(),initialState.getStateNail().getNailY()));
		bfsQuene.offer(newInit);
		while(bfsQuene.peek() != null){
			State usedState = bfsQuene.poll();
			HashMap<String,State> oneTransision = fromToTrans.get(usedState.getStateId());
			for(Iterator itr = oneTransision.keySet().iterator(); itr.hasNext();){ 
				String key = (String) itr.next(); 
				State value = (State) oneTransision.get(key);
				if(eliminatedStates.get(value.getStateId())==null){
					State st = new State(value.getStateId(), value.getStateType(), new Nail(value.getStateNail().getNailX(),value.getStateNail().getNailY()));
					eliminatedStates.put(value.getStateId(), st);
					bfsQuene.offer(st);
				}
			} 
		} 
		return eliminatedStates;
	}
	
	/**
	 * 最小化的填表算法 
	 * @param states HashMap<String,State> 所有的状态集合
	 * @param sortedStates State[] 排序后的状态集合
	 * @param a Automaton 最小化的自动机
	 * @param toFromTrans HashMap<String,HashMap<String,ArrayList<State>>> 可以通过到达状态ID和字符集字符索引到出发状态
	 * @return 填表算法运算后的表格
	 */
	private byte[][] tableFillingAlg(HashMap<String,State> states,		//所有的状态集合
										State[] sortedStates,			//排序后的状态集合
										Automaton a,					//最小化的自动机
										HashMap<String,HashMap<String,ArrayList<State>>> toFromTrans		
																		//可以通过到达状态ID和字符集字符索引到出发状态
										){
		byte[][] table = new byte[states.size()-1][states.size()-1];
		
		//初始化table所有元素为0
		for (int  i= 0;  i< states.size()-1; i++) {
			for (int j = 0; j <= i; j++) {
				table[i][j]= 0;
			}
		}
		HashMap<String,State> acceptStates = a.getAcceptedStates();
		//建立通过状态的id和数值之间的联系，快速的双向的索引位置
		HashMap<String,Integer> idMapIndex = new HashMap<String,Integer>();
		for(int i = 0 ; i < sortedStates.length; i++){
			idMapIndex.put(sortedStates[i].getStateId(),i);
		}
		
		//建立初始的表格状态,比较是否为结束状态或非结束状态
		Queue<Coordinate> coor = new LinkedList<Coordinate>();	//建立刚区分的两种状态的队列
		boolean changed = false;
		for(int i = 0 ; i < states.size() - 1 ; i++){
			boolean isAcceptStateA = false;
			if(acceptStates.containsKey(sortedStates[i+1].getStateId()) == true){
				isAcceptStateA = true;
			}
			for(int j = 0 ; j <=i ; j++){
				boolean isAcceptStateB = false;
				if(acceptStates.containsKey(sortedStates[j].getStateId()) == true ){
					isAcceptStateB = true;
				}
				if((isAcceptStateA&&!isAcceptStateB) || (!isAcceptStateA&&isAcceptStateB)){
					table[i][j] = 1;
					coor.offer(new Coordinate(i+1,j));
					changed = true;
				}
			}
		}
		
		//通过测试两个不同的状态在同一个转移字符的转移情况下得到填写表格
		ArrayList<String> inputSymbolSet = a.getInputSymbolSet();
		while(coor.peek() != null){
			Coordinate coo = coor.poll();
			String oneState = sortedStates[coo.x].getStateId();
			String anotherState = sortedStates[coo.y].getStateId();
			System.out.println("dis:"+oneState+"  another:"+anotherState);
			HashMap<String,ArrayList<State>> oneStateMap = toFromTrans.get(oneState);
			HashMap<String,ArrayList<State>> anotherStateMap = toFromTrans.get(anotherState);
			for(int i = 0 ; i < inputSymbolSet.size(); i++){
				String symbol = inputSymbolSet.get(i);
				ArrayList<State> oneFromState = oneStateMap.get(symbol);
				ArrayList<State> anotherFromState = anotherStateMap.get(symbol);
				if(oneFromState == null || anotherFromState == null){
					continue;
				}
				for(int ioneFromState = 0 ; ioneFromState < oneFromState.size() ; ioneFromState++){
					State tempStateA = oneFromState.get(ioneFromState);
					if(idMapIndex.get(tempStateA.getStateId()) == null){
						continue;
					}
					for(int j = 0 ; j < anotherFromState.size() ; j++){
						State tempStateB = anotherFromState.get(j);
						if(idMapIndex.get(tempStateB.getStateId()) == null){
							continue;
						}
						
						int tableIndexI = 0 , tableIndexJ = 0;
						//获得两个转移在表table中的正确的下标位置
						if(idMapIndex.get(tempStateA.getStateId())==idMapIndex.get(tempStateB.getStateId())){
							continue;
						}else if(idMapIndex.get(tempStateA.getStateId())>idMapIndex.get(tempStateB.getStateId())){
							tableIndexI = idMapIndex.get(tempStateA.getStateId());
							tableIndexJ = idMapIndex.get(tempStateB.getStateId());
						}else{
							tableIndexI = idMapIndex.get(tempStateB.getStateId());
							tableIndexJ = idMapIndex.get(tempStateA.getStateId());
						}
						
						if(table[tableIndexI-1][tableIndexJ] != 1){
							table[tableIndexI-1][tableIndexJ] = 1;
							Coordinate tempCoo = new Coordinate(tableIndexI,tableIndexJ);
							coor.offer(tempCoo);
						}
					}
				}
				
			}
			
		}
		return table;
	}

	/**
	 * 创建最小化的自动机
	 * @param eliminatedStates HashMap<String, State> 消除了不可达状态剩余的状态
	 * @param sortedStates State[] 排过序的状态数组
	 * @param table byte[][] 经过填表法计算后的表格
	 * @param s Automaton //需要进行计算的自动机
	 * @param fromToTrans HashMap<String, HashMap<String, State>> //可以快速通过出发状态ID，字符集字符索引到到达状态ID
	 * @return Automaton 最小化的自动机
	 */
	private Automaton constructNewMinAutomaton(HashMap<String, State> eliminatedStates, 		//消除了不可达状态剩余的状态
			State[] sortedStates, 							//排过序的状态数组
			byte[][] table, 								//经过填表法计算后的表格
			Automaton s, 									//需要进行计算的自动机
			HashMap<String, HashMap<String, State>> fromToTrans
															//可以快速通过出发状态ID，字符集字符索引到到达状态ID
			){
			Automaton minAuto = new AutomatonDFA();
			minAuto.setAutomatonName(s.getAutomatonName());
			minAuto.setAutomatonType(s.getAutomatonType());
			//合并等价状态
			HashMap<String,Integer> unionSameSetState = new HashMap<String,Integer>();
			for(int i = 0 ; i < sortedStates.length; i++){
				unionSameSetState.put(sortedStates[i].getStateId(),i);
			}

			for(int i = 0 ; i < sortedStates.length-1; i++ ){
				for (int j = 0; j <= i; j++) {
					if(table[i][j] != 1){
						sortedStates[i+1] = sortedStates[j];
					}
				}
			}


			//建立所有的状态，只包含有等价的状态集合
			HashMap<String, State> newStates = new HashMap<String, State>();
			for(int i = 0 ; i < sortedStates.length; i++){
				newStates.put(sortedStates[i].getStateId(), sortedStates[i]);
			}
			minAuto.setStates(newStates);

			//设置初始状态
			minAuto.setInitialState(sortedStates[unionSameSetState.get(s.getInitialState().getStateId())]);

			//建立所有的symbol集合
			ArrayList<String> newSymbols = new ArrayList<String>();
			ArrayList<String> oldSymbols = s.getInputSymbolSet();
			for(int i = 0 ; i < oldSymbols.size(); i++){
				newSymbols.add((String)oldSymbols.get(i));
			}
			minAuto.setInputSymbolSet(newSymbols);


			//建立所有的转移
			ArrayList<Transition> newTransitions = new ArrayList<Transition>();
			//用于索引到从fromState 到 toState中的conditions
			HashMap<String,HashMap<String,ArrayList<String>>> newFromToConditions = new HashMap<String,HashMap<String,ArrayList<String>>>();
			for(Iterator<String> itr = newStates.keySet().iterator(); itr.hasNext();){ 
				String fromStateID = (String) itr.next(); 
				State fromState = (State) newStates.get(fromStateID);
				HashMap<String,State> transition = fromToTrans.get(fromStateID);
				for(int i = 0 ; i < newSymbols.size() ; i++){
					String symbol = newSymbols.get(i);
					State toState = sortedStates[unionSameSetState.get(transition.get(symbol).getStateId())];
					/*索引condition*/
					ArrayList<String> conditions;
					if(newFromToConditions.get(fromStateID)!= null){
						if(newFromToConditions.get(fromStateID).get(toState.getStateId()) != null){
							conditions = newFromToConditions.get(fromStateID).get(toState.getStateId());
							conditions.add(symbol);
						}else{
							conditions = new ArrayList<String>();
							conditions.add(symbol);
							newTransitions.add(new TransitionDFA(fromState,conditions,toState));
							newFromToConditions.get(fromStateID).put(toState.getStateId(), conditions);
						}
					}else{
						conditions = new ArrayList<String>();
						conditions.add(symbol);
						newTransitions.add(new TransitionDFA(fromState,conditions,toState));
						HashMap<String,ArrayList<String>> toIndexConditions = new HashMap<String,ArrayList<String>>();
						toIndexConditions.put(toState.getStateId(), conditions);
						newFromToConditions.put(fromStateID,toIndexConditions);
					}
				}
			} 
			minAuto.setTransitions(newTransitions);

			return minAuto;
	}
}
