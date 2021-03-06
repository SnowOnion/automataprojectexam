package automata;

import java.util.*;

/**************
 * 
 * 该类抽象一个基本的自动机数据结构，包含自动机的一些公共属性和基本操作，所有其他自动机均从该自动机继承
 * 
 */
public abstract class Automata {
	private String name;
	/**
	 * 定义标识EPSILON转换的符号
	 */
	public static final char EPSILON = '^';
	/**
	 * 表示自动机的所有状态集合，并用一个Sring类型的StateId来唯一标识该状态
	 */
	private Map<String, State> states = new HashMap<String, State>();
	/**
	 * 表示自动机的输入字符集
	 */
	private Set<Character> symbols = new HashSet<Character>();
	/**
	 * 表示该自动机的所有转换函数
	 */
	private List<Transition> transfers = new LinkedList<Transition>();
	/**
	 * 初始状态
	 */
	private State startQ;
	/**
	 * 终结状态集合
	 */
	private List<State> finalStates = new LinkedList<State>();

	/**
	 * @param stateId
	 *            增加一个自动机的状态
	 */
	public void addState(String stateId) {
		states.put(stateId, new State(stateId));
	}
	public void addState(State state){
		states.put(state.getStateId(), state);
	}
	public void removeState(String stateId){
/*		Iterator<Transition> listItr = transfers.iterator();
		State curState = 
		while(listItr.hasNext()){
			Transition trans = listItr.next();
			if(trans.getBeginState()==)
		}*/
	}
	public void removeTrans(String startId, String endId){
		
	}
	public void removeSymbol(char symbol){
		
	}
	/**
	 * @param stateIds
	 *            增加多个状态
	 */
	public void addStates(List<State> stateIds) {
		for (State stateId : stateIds) {
			states.put(stateId.getStateId(), stateId);
		}
	}

	/**
	 * @param stateId
	 *            设置起始状态
	 */
	public void setStartQ(String stateId) {
		if (startQ != null)
			startQ.setStyle(State.OTHER_S);
		this.startQ = states.get(stateId);
		startQ.setStyle(State.START_S);
	}

	/**
	 * @param symbol
	 *            往输入字符集中增加一个字符
	 */
	public void addSymbol(char symbol) {
		this.symbols.add(new Character(symbol));
	}

	/**
	 * @param symbols
	 *            往输入字符集中增加多个字符
	 */
	public void addSymbols(List<Character> symbols) {
		for (Character symbol : symbols) {
			symbols.add(symbol);
		}
	}

	/**
	 * @param beginStateId
	 * @param inputChar
	 * @param endStateId
	 *            增加一个转换函数
	 */
	public void addTransfer(String beginStateId, char inputChar,
			String endStateId) {
		State beginState = states.get(beginStateId);
		State endState = states.get(endStateId);
		Transition tran = new Transition(beginState, inputChar, endState);
		beginState.addTransfer(inputChar, tran);
		transfers.add(tran);
	}
	
	public void addTransfer(Transition tran){
		State beginState=tran.getBeginState();
		char inputChar=tran.getInputChar();
		beginState.addTransfer(inputChar, tran);
		transfers.add(tran);
	}


	/**
	 * @param finalStateId
	 *            增加一个终止状态
	 */
	public void addFinalState(String finalStateId) {
		State finalState = states.get(finalStateId);
		if (finalState.getStyle() == State.START_S)
			finalState.setStyle(State.START_FINAL_S);
		else
			finalState.setStyle(State.FINAL_S);
		this.finalStates.add(finalState);
	}

	/**
	 * @param finalStateIds
	 *            增加多个终止状态
	 */
	public void addFinalStates(List<String> finalStateIds) {
		for (String finalStateId : finalStateIds) {
			State finalState = states.get(finalStateId);
			finalState.setStyle(State.FINAL_S);
			this.finalStates.add(finalState);
		}
	}
	
	public void importGraph(Set cells){
		int i=0;
		for(Object object:cells){
			if(object instanceof State){
				State state=(State)object;
				this.states.put(state.getStateId(), state);
				if(state.getStyle()==State.START_S||state.getStyle()==State.START_FINAL_S){
					this.startQ=state;
				}
				if(state.getStyle()==State.FINAL_S||state.getStyle()==State.START_FINAL_S){
					this.finalStates.add(state);
				}
			}else if(object instanceof Transition){
				Transition tran=(Transition)object;
				this.transfers.add(tran);
				this.symbols.add(tran.getInputChar());
			}
		}
		
	}
	public Collection export(){
		Set cells=new HashSet();
		for(Object object:states.values()){
			cells.add(object);
		}
		for(Object object:transfers){
			cells.add(object);
		}
		return cells;
	}

	public abstract void init();

	/**
	 * @param symbols
	 * @return 判断字符串symbols是否能被该自动机接受
	 */
	public abstract boolean acceptString(String symbols);

	/**
	 * @param symbol
	 * @return 在当前状态下输入字符symbol，如果能够接受，转换到下一个状态，否则返回false
	 */
	public abstract boolean acceptSymbol(char symbol);

	/**
	 * @param symbols
	 * @return 在当前状态下输入字符串symbols，如果能够接受，转换到相应状态，否则返回false
	 */
	public abstract boolean acceptSymbols(String symbols);

	public Map<String, State> getStates() {
		return states;
	}

	public Set<Character> getSymbols() {
		return symbols;
	}

	public List<Transition> getTransfers() {
		return transfers;
	}

	public State getStartQ() {
		return startQ;
	}

	public List<State> getFinalStates() {
		return finalStates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
