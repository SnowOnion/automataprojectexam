package DFAalg1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import automaton.Automaton;
import automaton.State;
import automaton.Transition;

public class TestFAType {
	/**
	 * test the automaton type
	 * @param automaton Automaton 
	 * @return automaton type
	 */
	public  DFAalg1.DFAalg1Const.automatonType getAutomatonType(Automaton automaton) {
		// TODO Auto-generated method stub
		ArrayList<String> symbols = automaton.getInputSymbolSet();
		HashMap<String, State> states = automaton.getStates();
		ArrayList<Transition> transitions	= automaton.getTransitions();

		if(symbols == null || states == null || transitions == null){
			return DFAalg1Const.automatonType.NFA;
		}
		//�����Ƿ�ת�Ƶı���Ϊsymbol.size*states.size
		int transitionEdges = 0;
		Iterator transNum = transitions.iterator() ;
		while(transNum.hasNext()){
			transitionEdges += ((Transition)transNum.next()).getTransitionConditions().size();
		}
		if(transitionEdges != symbols.size()*states.size()){
			return DFAalg1Const.automatonType.NFA;
		}
		//����ÿ���ַ����Ƿ�һ������һ��˵��ÿһ��ת����ȷ����
		//ͬʱ����������ܹ���symbol.size*states.size����˵����һ��ȷ�����Զ���
		Set<OneTrans> testUnique = new TreeSet<OneTrans>();
		transNum = transitions.iterator() ;
		while(transNum.hasNext()){
			Transition tempTrans = (Transition)transNum.next();
			List conditions = tempTrans.getTransitionConditions();
			for(int i = 0 ; i < conditions.size();i++){
				OneTrans temp = new OneTrans(tempTrans.getFromState().getStateId(),(String)conditions.get(i));
				if(((String)conditions.get(i)).toLowerCase().equals("empty")){
					return DFAalg1Const.automatonType.NFA;
				}
				if(testUnique.add(temp)==false){
					return DFAalg1Const.automatonType.NFA;
				}
			}		
		}
		return DFAalg1Const.automatonType.DFA;
	}
	/**
	 * ���������������ϵĵȼ���
	 * @author yyf
	 *
	 */
	class OneTrans implements Comparable{

		String fromStateID;
		String transCondition;
		
		public OneTrans(String state, String trans){
			fromStateID = state;
			transCondition = trans;
		}
		
		public int compareTo(Object arg0) {
			// TODO Auto-generated method stub
			OneTrans a = (OneTrans)arg0;
			if(a.fromStateID.equals(fromStateID)){
				if(a.transCondition.equals(transCondition)){
					return 0;
				}else{
					return transCondition.compareTo(a.transCondition);
				}
			}else{
				return fromStateID.compareTo(a.fromStateID);
			}
		}
	}
}
