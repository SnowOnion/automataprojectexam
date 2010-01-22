package Automata;

/**
 * ���ඨ���Զ�����״̬��ת�������ݽṹ
 * @author beyondboy ����  �޸� 2010-1-10
 * @�޸�����
 *  ��inputChar��ΪinputString,������char��Ϊstring�ȵ�
 */
public class Transition implements Comparable<Object>{
	/**
	 * �����ݽṹ��ʾ�������ַ�ΪinputCharʱ��״̬beginStateת����״̬endState
	 */
	private State beginState;
	private String inputString;
	private State endState;
	
	/**
	 * @param beginState
	 * @param inputChar
	 * @param targetState
	 * �ù��췽������һ��ת��
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
		int level2=this.inputString.compareTo(f.inputString);//�޸Ĺ�  2010-1-10
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
