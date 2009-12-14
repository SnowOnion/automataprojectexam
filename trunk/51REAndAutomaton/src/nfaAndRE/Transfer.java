package nfaAndRE;
/**
 * @author beyondboy
 *该类定义自动机中状态的转换的数据结构
 */
public class Transfer implements Comparable<Object>{
	/**
	 * 该数据结构表示当输入字符为inputChar时由状态beginState转换到状态endState
	 */
	private State beginState;
	private char inputChar;
	private State endState;
	
	/**
	 * @param beginState
	 * @param inputChar
	 * @param targetState
	 * 该构造方法定义一个转换
	 */
	public Transfer(State beginState,char inputChar,State targetState){
		this.beginState=beginState;
		this.inputChar=inputChar;
		this.endState=targetState;
	}
	
	public State getBeginState() {
		return beginState;
	}

	public void setBeginState(State beginState) {
		this.beginState = beginState;
	}

	public char getInputChar() {
		return inputChar;
	}
	public void setInputChar(char inputChar) {
		this.inputChar = inputChar;
	}
	public State getEndState() {
		return endState;
	}
	public void setEndState(State targetState) {
		this.endState = targetState;
	}

	@Override
	public int compareTo(Object o) {
		Transfer f=(Transfer)o;
		int level1=this.beginState.compareTo(f.beginState);
		int level2=this.inputChar-f.inputChar;
		int level3=this.endState.compareTo(f.endState);
		
		if(level1!=0)
			return level1;
		else if(level2!=0)
			return level2;
		else
			return level3;
	}
}
