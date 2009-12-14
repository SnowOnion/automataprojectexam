package nfaAndRE;

import java.util.*;
/**************
 * 
 * 该类定义表示自动机的状态数据结构，包括一些相关的属性和操作
 *
 */
public class State implements Comparable{
	public static final int START_S=1;
	public static final int FINAL_S=2;
	public static final int OTHER_S=3;
	
	/**
	 * 标识一个状态
	 */
	private String stateId;
	/**
	 * 用来表示状态类型，START_S表示开始状态，FINAL_S表示终态，OTHER_S表示其他状态
	 */
	private int style=OTHER_S;
	/**
	 * 该状态上的所有转换
	 */
	private Map<Character, List<Transfer>> trans = new HashMap<Character, List<Transfer>>();

	/**
	 * @param state
	 * 用其标识stateId来构造该状态
	 */
	public State(String stateId){
		this.stateId=stateId;
	}
	/**
	 * @param inputChar
	 * @param tran
	 * 在该状态上添加一个转换
	 */
	public void addTransfer(char inputChar, Transfer tran) {
		if (trans.containsKey(new Character(inputChar))) {
			List<Transfer> lst = trans.get(new Character(inputChar));
			lst.add(tran);
		} else {
			List<Transfer> lst = new LinkedList<Transfer>();
			lst.add(tran);
			trans.put(new Character(inputChar), lst);
		}
	}

	/**
	 * @param inputChar
	 * @return
	 * 对于一个输入字符inputChar，该方法返回该状态上对应的所有转换
	 */
	public List<Transfer> getTransfers(char inputChar) {
		return trans.get(new Character(inputChar));
	}
	
	/**
	 * @param inputChar
	 * @return
	 * 对于一个输入字符inputChar，返回其中一个转换
	 */
	public Transfer getTransfer(char inputChar){
		List<Transfer> lst=getTransfers(inputChar);
		if(lst!=null)
			return lst.get(0);
		else
			return null;
	}

	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
	}
	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	
	@Override
	public int compareTo(Object s) {
		return this.stateId.compareTo(((State)s).stateId);
	}
}
