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
		
		//HashMap<String,HashMap<String,State>> ��һ��String��ʾ��ת�ƿ�ʼ��״̬���ڶ���String��ʾ��ת�Ƶ��������õ���state��ʾת�Ƶ���״̬��
		//���ڿ���������ת��ָ���״̬
		HashMap<String,HashMap<String,State>> fromToTrans = new HashMap<String,HashMap<String, State>>();
		//HashMap<String,HashMap<String,State>> ��һ��String��ʾ��ת�ƽ�����״̬���ڶ���String��ʾ��ת�Ƶ��������õ���state��ʾת�Ƶ���״̬��
		//���ڿ���������ת��ָ���״̬
		HashMap<String,HashMap<String,ArrayList<State>>> toFromTrans = new HashMap<String,HashMap<String,ArrayList<State>>>();
		//����fromToTrans��toFromTrans �������ٵĲ��ҵ�״̬ת�Ƶ���״̬
		for(int i = 0 ; i < oldTransitions.size(); i++){
			Transition oldTran = oldTransitions.get(i);
			ArrayList<String> oldCondition = (ArrayList<String>) oldTran.getTransitionConditions();
			HashMap<String,State> tempFromTo;
			//�ж��Ƿ��Ѿ������Ը���ʼ״̬��hashMap
			if(fromToTrans.get(oldTran.getFromState().getStateId()) == null){
				tempFromTo = new HashMap<String,State>();
				fromToTrans.put(oldTran.getFromState().getStateId(), tempFromTo);
			}
			tempFromTo = fromToTrans.get(oldTran.getFromState().getStateId());
			
			//����toFromTrans
			HashMap<String,ArrayList<State>> tempToFrom; 
			if(toFromTrans.get(oldTran.getToState().getStateId()) == null){
				tempToFrom = new HashMap<String,ArrayList<State>>();
				toFromTrans.put(oldTran.getToState().getStateId(), tempToFrom);
			}
			tempToFrom = toFromTrans.get(oldTran.getToState().getStateId());
			//����fromToTrans��toFromTrans
			for(int j = 0 ; j < oldCondition.size(); j++){
				//����fromToTrans
				tempFromTo.put(oldCondition.get(j), oldTran.getToState());
				
				//����toFromTrans
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
	 * �������ɴ�״̬
	 * @param fromToTrans HashMap<String,HashMap<String,State>> ͨ��������״̬ID��ת��������������������״̬
	 * @param initialState State ��ʼ״̬
	 * @return HashMap<String,State> �����˲��ɴ�״̬��״̬����
	 */
	private HashMap<String,State> eliminateUselessStates(HashMap<String,HashMap<String,State>> fromToTrans,State initialState){
		HashMap<String,State> eliminatedStates = new HashMap<String,State>();
		//����BFS��������ʽ�������ɴ�״̬
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
	 * ��С��������㷨 
	 * @param states HashMap<String,State> ���е�״̬����
	 * @param sortedStates State[] ������״̬����
	 * @param a Automaton ��С�����Զ���
	 * @param toFromTrans HashMap<String,HashMap<String,ArrayList<State>>> ����ͨ������״̬ID���ַ����ַ�����������״̬
	 * @return ����㷨�����ı��
	 */
	private byte[][] tableFillingAlg(HashMap<String,State> states,		//���е�״̬����
										State[] sortedStates,			//������״̬����
										Automaton a,					//��С�����Զ���
										HashMap<String,HashMap<String,ArrayList<State>>> toFromTrans		
																		//����ͨ������״̬ID���ַ����ַ�����������״̬
										){
		byte[][] table = new byte[states.size()-1][states.size()-1];
		
		//��ʼ��table����Ԫ��Ϊ0
		for (int  i= 0;  i< states.size()-1; i++) {
			for (int j = 0; j <= i; j++) {
				table[i][j]= 0;
			}
		}
		HashMap<String,State> acceptStates = a.getAcceptedStates();
		//����ͨ��״̬��id����ֵ֮�����ϵ�����ٵ�˫�������λ��
		HashMap<String,Integer> idMapIndex = new HashMap<String,Integer>();
		for(int i = 0 ; i < sortedStates.length; i++){
			idMapIndex.put(sortedStates[i].getStateId(),i);
		}
		
		//������ʼ�ı��״̬,�Ƚ��Ƿ�Ϊ����״̬��ǽ���״̬
		Queue<Coordinate> coor = new LinkedList<Coordinate>();	//���������ֵ�����״̬�Ķ���
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
		
		//ͨ������������ͬ��״̬��ͬһ��ת���ַ���ת������µõ���д���
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
						//�������ת���ڱ�table�е���ȷ���±�λ��
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
	 * ������С�����Զ���
	 * @param eliminatedStates HashMap<String, State> �����˲��ɴ�״̬ʣ���״̬
	 * @param sortedStates State[] �Ź����״̬����
	 * @param table byte[][] ������������ı��
	 * @param s Automaton //��Ҫ���м�����Զ���
	 * @param fromToTrans HashMap<String, HashMap<String, State>> //���Կ���ͨ������״̬ID���ַ����ַ�����������״̬ID
	 * @return Automaton ��С�����Զ���
	 */
	private Automaton constructNewMinAutomaton(HashMap<String, State> eliminatedStates, 		//�����˲��ɴ�״̬ʣ���״̬
			State[] sortedStates, 							//�Ź����״̬����
			byte[][] table, 								//������������ı��
			Automaton s, 									//��Ҫ���м�����Զ���
			HashMap<String, HashMap<String, State>> fromToTrans
															//���Կ���ͨ������״̬ID���ַ����ַ�����������״̬ID
			){
			Automaton minAuto = new AutomatonDFA();
			minAuto.setAutomatonName(s.getAutomatonName());
			minAuto.setAutomatonType(s.getAutomatonType());
			//�ϲ��ȼ�״̬
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


			//�������е�״̬��ֻ�����еȼ۵�״̬����
			HashMap<String, State> newStates = new HashMap<String, State>();
			for(int i = 0 ; i < sortedStates.length; i++){
				newStates.put(sortedStates[i].getStateId(), sortedStates[i]);
			}
			minAuto.setStates(newStates);

			//���ó�ʼ״̬
			minAuto.setInitialState(sortedStates[unionSameSetState.get(s.getInitialState().getStateId())]);

			//�������е�symbol����
			ArrayList<String> newSymbols = new ArrayList<String>();
			ArrayList<String> oldSymbols = s.getInputSymbolSet();
			for(int i = 0 ; i < oldSymbols.size(); i++){
				newSymbols.add((String)oldSymbols.get(i));
			}
			minAuto.setInputSymbolSet(newSymbols);


			//�������е�ת��
			ArrayList<Transition> newTransitions = new ArrayList<Transition>();
			//������������fromState �� toState�е�conditions
			HashMap<String,HashMap<String,ArrayList<String>>> newFromToConditions = new HashMap<String,HashMap<String,ArrayList<String>>>();
			for(Iterator<String> itr = newStates.keySet().iterator(); itr.hasNext();){ 
				String fromStateID = (String) itr.next(); 
				State fromState = (State) newStates.get(fromStateID);
				HashMap<String,State> transition = fromToTrans.get(fromStateID);
				for(int i = 0 ; i < newSymbols.size() ; i++){
					String symbol = newSymbols.get(i);
					State toState = sortedStates[unionSameSetState.get(transition.get(symbol).getStateId())];
					/*����condition*/
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
