package automata;

import java.util.*;

public class NFA extends Automata {

	private Set<State> curStates = new TreeSet<State>();

	@Override
	public boolean acceptSymbol(char symbol) {
//		System.out.println(symbol);
		// TODO Auto-generated method stub
		Set<State> targetSet = new TreeSet<State>();
		for (State iterator : curStates) {
			List<Transition> tranList = iterator.getTransfers(symbol);
			if (tranList != null) {

				for (Transition tran : tranList) {
//					System.out.println(tran.getBeginState()+" "+tran.getInputChar()+ " "+tran.getEndState());
					
					targetSet.addAll(this.EpsilonSets(tran.getEndState()));
//					targetSet.add(tran.getEndState());
				}
			}
		}
		curStates = targetSet;
		if(curStates.isEmpty()){
			return false;			
		}
		return true;
	}

	@Override
	public boolean acceptSymbols(String symbols) {
		// TODO Auto-generated method stub
		if(symbols.equals(""))
			return true;
		else{
//			System.out.println("TTTTTTT "+symbols);
			char inputChar=symbols.charAt(0);
			if(acceptSymbol(inputChar)){
				return acceptSymbols(symbols.substring(1));
			}else{
				return false;
			}
		}
	}

	@Override
	public boolean acceptString(String symbols) {
		// TODO Auto-generated method stub
		init();
		if (acceptSymbols(symbols)){
			for(State iterator :curStates){
				if(iterator.getStyle()==State.FINAL_S)
					return true;
			}	
		}		
		return false;

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		curStates.clear();
		curStates.addAll(this.EpsilonSets(getStartQ()));
	}
	private Set<State> EpsilonSets(State curState){
		Set<State> Eset = new TreeSet<State>();
		List<State> curSet = new LinkedList<State>();
		curSet.add(curState);
		while(!curSet.isEmpty()){
			State curSt = curSet.remove(0);
			Eset.add(curSt);
			List<Transition> tranList = curSt.getTransfers(Automata.EPSILON);
			if(tranList!=null){
				for(Transition tran :tranList){
					if(!Eset.contains(tran.getEndState())){
						curSet.add(tran.getEndState());
					}
				}
			}
			
		}
		return Eset;
	}
}
