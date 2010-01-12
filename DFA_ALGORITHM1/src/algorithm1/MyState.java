/**
 * @Title: MyState.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 ����10:52:11
 * @Version: V1.0
 */
package algorithm1;


public class MyState {
	/**
	 * �ɷ���ID�ı��
	 */
	private static long ID=1;
	/**
	 * ״̬����
	 */
	private String StateName;
	/**
	 * ״̬ID����ϵͳ��ÿ��״̬����ΨһID���ж�״̬�Ƿ���ͬ��ID
	 */
	private  long StateID;
	/**
	 * ���캯��
	 * @param name ״̬��
	 */
	public MyState(String name)
	{
		StateName=name;
		StateID=this.AssignID();
	}
	/**
	 * getter and setter
	 * @return
	 */
	public String getStateName() {
		return StateName;
	}

	public void setStateName(String stateName) {
		StateName = stateName;
	}

	public long getStateID() {
		return StateID;
	}

	public void setStateID(long stateID) {
		StateID = stateID;
	}

	/**
	 * ��ȡ��ǰ�ɷ����ID
	 * @return ��ǰ�ɷ���ID
	 */
	private long AssignID()
	{
		ID++;
		return ID-1;
	}
	/**
	 * ͨ��ID�ж�����״̬�Ƿ���ͬһ��״̬
	 * @param op
	 * @return ��ͬ����true ��ͬ����false
	 */
	public boolean equal(MyState op)
	{
		if(this.StateID==op.getStateID())
			return true;
		return false;
	}
	/**
	 * ����״̬�ַ���
	 */
	public String toString()
	{
		return StateName;
	}
	/**
	 * ����һ��״̬��ͬID��ͬ��״̬
	 * ���ڲ�ͬ���Զ���
	 */
	public MyState clone()
	{
		return new MyState(this.StateName);
	}

}
