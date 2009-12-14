package nfaAndRE;

public class DFA extends Automata {

	/**
	 * ��ʾ�Զ�����ǰ����״̬
	 */
	private State curState;

	@Override
	public boolean acceptSymbols(String symbols) {
		if (symbols.equals(""))
			return true;
		else {
			char inputChar = symbols.charAt(0);
			if (acceptSymbol(inputChar)) {
				return acceptSymbols(symbols.substring(1));
			} else {
				return false;
			}
		}
	}

	@Override
	public boolean acceptSymbol(char symbol) {
		Transfer tran = curState.getTransfer(symbol);
		if (tran == null)
			return false;
		else {
			setCurState(tran.getEndState());
			return true;
		}
	}

	/**
	 * ��ʼ���Զ���
	 */
	@Override
	public void init() {
		curState = getStartQ();
	}

	/**
	 * @param symbols
	 * @return �ж��ַ���symbols�Ƿ��ܱ����Զ�������
	 */
	@Override
	public boolean acceptString(String symbols) {
		init();
		if (acceptSymbols(symbols) && curState.getStyle() == State.FINAL_S)
			return true;
		else
			return false;
	}

	public State getCurState() {
		return curState;
	}

	protected void setCurState(State curState) {
		this.curState = curState;
	}

}

