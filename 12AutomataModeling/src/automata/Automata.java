package automata;

import java.util.*;

/**************
 * 
 * �������һ���������Զ������ݽṹ�������Զ�����һЩ�������Ժͻ������������������Զ������Ӹ��Զ����̳�
 * 
 */
public abstract class Automata {
	private String name;
	/**
	 * �����ʶEPSILONת���ķ���
	 */
	public static final char EPSILON = '^';
	/**
	 * ��ʾ�Զ���������״̬���ϣ�����һ��Sring���͵�StateId��Ψһ��ʶ��״̬
	 */
	private Map<String, State> states = new HashMap<String, State>();
	/**
	 * ��ʾ�Զ����������ַ���
	 */
	private Set<Character> symbols = new HashSet<Character>();
	/**
	 * ��ʾ���Զ���������ת������
	 */
	private List<Transition> transfers = new LinkedList<Transition>();
	/**
	 * ��ʼ״̬
	 */
	private State startQ;
	/**
	 * �ս�״̬����
	 */
	private List<State> finalStates = new LinkedList<State>();

	/**
	 * @param stateId
	 *            ����һ���Զ�����״̬
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
	 *            ���Ӷ��״̬
	 */
	public void addStates(List<State> stateIds) {
		for (State stateId : stateIds) {
			states.put(stateId.getStateId(), stateId);
		}
	}

	/**
	 * @param stateId
	 *            ������ʼ״̬
	 */
	public void setStartQ(String stateId) {
		if (startQ != null)
			startQ.setStyle(State.OTHER_S);
		this.startQ = states.get(stateId);
		startQ.setStyle(State.START_S);
	}

	/**
	 * @param symbol
	 *            �������ַ���������һ���ַ�
	 */
	public void addSymbol(char symbol) {
		this.symbols.add(new Character(symbol));
	}

	/**
	 * @param symbols
	 *            �������ַ��������Ӷ���ַ�
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
	 *            ����һ��ת������
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
	 *            ����һ����ֹ״̬
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
	 *            ���Ӷ����ֹ״̬
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
	 * @return �ж��ַ���symbols�Ƿ��ܱ����Զ�������
	 */
	public abstract boolean acceptString(String symbols);

	/**
	 * @param symbol
	 * @return �ڵ�ǰ״̬�������ַ�symbol������ܹ����ܣ�ת������һ��״̬�����򷵻�false
	 */
	public abstract boolean acceptSymbol(char symbol);

	/**
	 * @param symbols
	 * @return �ڵ�ǰ״̬�������ַ���symbols������ܹ����ܣ�ת������Ӧ״̬�����򷵻�false
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
