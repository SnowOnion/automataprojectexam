package regularExpressionTool;

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
	private Map<String, List<Transition>> trans = new HashMap<String, List<Transition>>();//�޸Ĺ� 2010-1-10

	public State(){}//�޸Ĺ�
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
	public void addTransfer(String inputString, Transition tran) {//�޸Ĺ� 2010-1-10
		if (trans.containsKey(new String(inputString))) {
			List<Transition> lst = trans.get(new String(inputString));
			lst.add(tran);
		} else {
			List<Transition> lst = new LinkedList<Transition>();
			lst.add(tran);
			trans.put(new String(inputString), lst);
		}
	}

	/**
	 * @param inputChar
	 * @return
	 * ����һ�������ַ�inputChar���÷������ظ�״̬�϶�Ӧ������ת��
	 */
	public List<Transition> getTransfers(String inputString) {//�޸Ĺ�
		return trans.get(new String(inputString));
	}
	
	/**
	 * @param inputChar
	 * @return
	 * ����һ�������ַ�inputChar����������һ��ת��
	 */
	public Transition getTransfer(String inputString){//�޸Ĺ�
		List<Transition> lst=getTransfers(inputString);
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
	
	public int compareTo(Object s) {
		return this.stateId.compareTo(((State)s).stateId);
	}
	public Map<String, List<Transition>> getTrans() {
		return trans;
	}
	public void setTrans(Map<String, List<Transition>> trans) {
		this.trans = trans;
	}
}