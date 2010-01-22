package edu.cn.thss.qmy.ndfa.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.cn.thss.qmy.ndfa.util.Globals;

public class FAFunctions {
	
	public static String canAccepted(FA fa, char[] chars){
		String path = "";
		FA dfa = FAFunctions.EnsureDFA(fa);
		Integer preStateKey = dfa.getStartState();
		for(int i = 0; i < chars.length; i++){
			Transition tran = dfa.getTransitionByPreAndSym(preStateKey, dfa.getSymbolKeyBySymbol(chars[i]));
			if(tran == null){
				path = "This String CANNOT be Accepted by this FA.";
				break;
			}else{
				if(i == chars.length-1){
					path += dfa.getStateByKey(preStateKey).getName() + " --" + chars[i] + "--> " + dfa.getStateByKey(tran.getNextState()).getName();
				}else{
					path += dfa.getStateByKey(preStateKey).getName() + " --" + chars[i] + "--> ";
				}
				preStateKey = tran.getNextState();
			}
		}
		if(!dfa.getFinalStates().contains(preStateKey)){
			path = "This String CANNOT be Accepted by this FA.";
		}
		return path;
	}

	public static boolean isLanguageNull(FA fa){
		FA dfa = FAFunctions.EnsureDFA(fa);
		Set<Integer> calced = new HashSet<Integer>();
		calced.add(dfa.getStartState());
		Iterator<Integer> it = calced.iterator();
		while(it.hasNext()){
			Integer size = calced.size();
			Integer pre = it.next();
			Iterator<Integer> symIt = dfa.getSymbols().keySet().iterator();
			while(symIt.hasNext()){
				Integer sym = symIt.next();
				Transition tran = dfa.getTransitionByPreAndSym(pre, sym);
				Integer next = tran.getNextState();
				if(dfa.getFinalStates().contains(next)){
					return false;
				}else if(!calced.contains(next)){
					calced.add(next);
				}
			}
			if(size.equals(calced.size())){
				return true;
			}
		}
		return true;
	}
	
	public static boolean isLanguageInfinite(FA fa){
		FA dfa = FAFunctions.EnsureDFA(fa);
		Set<Integer> calced = new HashSet<Integer>();
		calced.add(dfa.getStartState());
		Iterator<Integer> it = calced.iterator();
		while(it.hasNext()){
			Integer pre = it.next();
			Iterator<Integer> symIt = dfa.getSymbols().keySet().iterator();
			while(symIt.hasNext()){
				Integer sym = symIt.next();
				Transition tran = dfa.getTransitionByPreAndSym(pre, sym);
				Integer next = tran.getNextState();
				if(calced.contains(next)){
					return true;
				}else{
					calced.add(next);
				}
			}
		}
		return false;
	}
	
	public static FA EnsureDFA(FA fa){
		FA dfa = null;
		fa.setIsDFA();
		if(fa.getIsDFA()){
			dfa = fa;
		}else{
			dfa = FAFunctions.NFA2DFA(fa);
		}
		return dfa;
	}
	
	public static FA NFA2DFA(FA nfa){
		FA dfa = new FA();

		dfa.setSymbols(nfa.getSymbols());
		dfa.getSymbols().remove(0);
		Integer stateKeyToAdd = 0;
		dfa.addState(stateKeyToAdd, nfa.getEClose(nfa.getStartState()));
		dfa.setStartState(stateKeyToAdd);
		
		for(int i = 0; i < dfa.getStates().keySet().size(); i++){
			State curState = dfa.getStateByKey(i);
			Iterator<Integer> symbolIt = dfa.getSymbols().keySet().iterator();
			while(symbolIt.hasNext()){
				Transition transition = new Transition();
				transition.setPreState(dfa.getStateKeyByName(curState.getName()));
				Integer symbolKey = symbolIt.next();
				transition.setSymbol(symbolKey);
				State nextState = new State();
				boolean isFinalState = false;
				String stateName = "";
				Iterator<State> subStateIt = curState.getSubStates().iterator();
				while(subStateIt.hasNext()){
					State subState = subStateIt.next();
					Set<Integer> stateKeysToAdd1 = nfa.getNextStateKey(nfa.getStateKeyByName(subState.getName()), nfa.getSymbolKeyBySymbol(dfa.getSymbolByKey(symbolKey)));
					Iterator<Integer> it1 = stateKeysToAdd1.iterator();
					while(it1.hasNext()){
						State stateToAdd = nfa.getStateByKey(it1.next());
						if(stateToAdd != null && !nextState.getSubStates().contains(stateToAdd)){
							nextState.addSubStates(stateToAdd);
							stateName += stateToAdd.getName();
						}
					}
					
					nextState.setSubStates(emptyTranCheck(nextState.getSubStates(), nfa, stateName));
					
				}
				
				Iterator<State> subIt = nextState.getSubStates().iterator();
				while(subIt.hasNext()){
					State sub = subIt.next();
					if(nfa.getFinalStates().contains(nfa.getStateKeyByName(sub.getName()))){
						isFinalState = true;
					}
				}
				
				if(stateName.equals("")){
					stateName = Globals.EMPTY_STATE;
				}
				nextState.setName(stateName);
				String stateNameToAddIn = dfa.getStateNameBySubState(nextState);
				if(stateNameToAddIn == null){
					stateKeyToAdd++;
					dfa.addState(stateKeyToAdd, nextState);
					transition.setNextState(dfa.getStateKeyByName(nextState.getName()));
					if(isFinalState){
						dfa.getFinalStates().add(dfa.getStateKeyByName(nextState.getName()));
					}
				}else{
					transition.setNextState(dfa.getStateKeyByName(stateNameToAddIn));
					if(isFinalState){
						dfa.getFinalStates().add(dfa.getStateKeyByName(stateNameToAddIn));
					}
				}
				dfa.addTransition(transition);
			}
		}
		return dfa;
	}
	
	private static Set<State> emptyTranCheck(Set<State> subStates, FA nfa, String stateName){
		Set<State> states = new HashSet<State>();
		states.addAll(subStates);
		Iterator<State> subIt = subStates.iterator();
		while(subIt.hasNext()){
			State subState = subIt.next();
			Set<Integer> stateKeysToAdd2 = nfa.getNextStateKey(nfa.getStateKeyByName(subState.getName()), Globals.NIL_SYMBOL_KEY);
			Iterator<Integer> it2 = stateKeysToAdd2.iterator();
			while(it2.hasNext()){
				State stateToAdd = nfa.getStateByKey(it2.next());
				if(stateToAdd != null && !states.contains(stateToAdd)){
					states.add(stateToAdd);
					stateName += stateToAdd.getName();
				}
			}
		}
		if(states.size() != subStates.size()){
			states = emptyTranCheck(states, nfa, stateName);
		}
		return states;
	}
}
