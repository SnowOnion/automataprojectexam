package Automata;

/**
 * 该类定义自动机中状态的转换的数据结构
 * @author beyondboy 卫松  修改 2010-1-10
 * @修改内容
 *  将inputChar改为inputString,类型由char改为string等等
 */
public class Transition implements Comparable<Object>{
	/**
	 * 该数据结构表示当输入字符为inputChar时由状态beginState转换到状态endState
	 */
	private State beginState;
	private String inputString;
	private State endState;
	
	/**
	 * @param beginState
	 * @param inputChar
	 * @param targetState
	 * 该构造方法定义一个转换
	 */
	public Transition(State beginState,String inputString,State targetState){
		this.beginState=beginState;
		this.inputString=inputString;
		this.endState=targetState;
	}
	
	public State getBeginState() {
		return beginState;
	}

	public void setBeginState(State beginState) {
		this.beginState = beginState;
	}

	public State getEndState() {
		return endState;
	}
	public void setEndState(State targetState) {
		this.endState = targetState;
	}

	public int compareTo(Object o) {
		Transition f=(Transition)o;
		int level1=this.beginState.compareTo(f.beginState);
		int level2=this.inputString.compareTo(f.inputString);//修改过  2010-1-10
		int level3=this.endState.compareTo(f.endState);
		
		if(level1!=0)
			return level1;
		else if(level2!=0)
			return level2;
		else
			return level3;
	}

	public void setInputString(String inputString) {
		this.inputString = inputString;
	}

	public String getInputString() {
		return inputString;
	}
}
