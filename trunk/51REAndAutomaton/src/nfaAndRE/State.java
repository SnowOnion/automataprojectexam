package nfaAndRE;

import java.util.*;
/**************
 * 
 * ���ඨ���ʾ�Զ�����״̬���ݽṹ������һЩ��ص����ԺͲ���
 *
 */
public class State implements Comparable{
	public static final int START_S=1;
	public static final int FINAL_S=2;
	public static final int OTHER_S=3;
	
	/**
	 * ��ʶһ��״̬
	 */
	private String stateId;
	/**
	 * ������ʾ״̬���ͣ�START_S��ʾ��ʼ״̬��FINAL_S��ʾ��̬��OTHER_S��ʾ����״̬
	 */
	private int style=OTHER_S;
	/**
	 * ��״̬�ϵ�����ת��
	 */
	private Map<Character, List<Transfer>> trans = new HashMap<Character, List<Transfer>>();

	/**
	 * @param state
	 * �����ʶstateId�������״̬
	 */
	public State(String stateId){
		this.stateId=stateId;
	}
	/**
	 * @param inputChar
	 * @param tran
	 * �ڸ�״̬�����һ��ת��
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
	 * ����һ�������ַ�inputChar���÷������ظ�״̬�϶�Ӧ������ת��
	 */
	public List<Transfer> getTransfers(char inputChar) {
		return trans.get(new Character(inputChar));
	}
	
	/**
	 * @param inputChar
	 * @return
	 * ����һ�������ַ�inputChar����������һ��ת��
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
