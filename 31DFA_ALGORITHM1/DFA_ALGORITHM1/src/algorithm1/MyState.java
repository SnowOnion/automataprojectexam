/**
 * @Title: MyState.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 上午10:52:11
 * @Version: V1.0
 */
package algorithm1;


public class MyState {
	/**
	 * 可分配ID的标记
	 */
	private static long ID=1;
	/**
	 * 状态名字
	 */
	private String StateName;
	/**
	 * 状态ID，在系统中每个状态都有唯一ID，判断状态是否相同的ID
	 */
	private  long StateID;
	/**
	 * 构造函数
	 * @param name 状态名
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
	 * 获取当前可分配的ID
	 * @return 当前可分配ID
	 */
	private long AssignID()
	{
		ID++;
		return ID-1;
	}
	/**
	 * 通过ID判断两个状态是否是同一个状态
	 * @param op
	 * @return 相同返回true 不同返回false
	 */
	public boolean equal(MyState op)
	{
		if(this.StateID==op.getStateID())
			return true;
		return false;
	}
	/**
	 * 返回状态字符串
	 */
	public String toString()
	{
		return StateName;
	}
	/**
	 * 返回一个状态相同ID不同的状态
	 * 属于不同的自动机
	 */
	public MyState clone()
	{
		return new MyState(this.StateName);
	}

}
