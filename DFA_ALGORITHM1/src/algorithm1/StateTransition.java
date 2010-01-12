/**
 * @Title: StateTransition.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 上午10:53:10
 * @Version: V1.0
 */
package algorithm1;
import java.util.ArrayList;

public class StateTransition {
	/**
	 * 状态
	 */
	private MyState BeginState;
	/**
	 * 状态BeginState的所有转移函数
	 */
	ArrayList<Transition> EndStates;
	/**
	 * 构造函数
	 * 初始化状态，同时初始化状态的每个转移条件对应的转移函数为空
	 * @param state 状态
	 * @param size 转移条件数
	 */
	public StateTransition(MyState state,int size)
	{
		BeginState=state;
		EndStates=new ArrayList<Transition>();
		for(int i=0;i<=size;i++)
		{
			Transition tempts=new Transition();
			EndStates.add(tempts);
		}
	}
	/**
	 * getter and setter
	 * @return
	 */
	public MyState getBeginState() {
		return BeginState;
	}

	public void setBeginState(MyState beginState) {
		BeginState = beginState;
	}

	public ArrayList<Transition> getEndStates() {
		return EndStates;
	}

	public void setEndStates(ArrayList<Transition> endStates) {
		EndStates = endStates;
	}

	/**
	 * 添加状态转移函数
	 * @param index 转移条件下标
	 * @param state 转移到的状态
	 */
	public void AddStateTransition(int index,MyState state)
	{
		Transition tempts=EndStates.get(index);
		tempts.AddTransition(state);
	}
	/**
	 * 获取下标为index的条件下，转移函数size
	 * @param index 条件下标
	 * @return 转移函数数目
	 */
	public int GetTransSize(int index)
	{
		Transition tempts=EndStates.get(index);
		return tempts.GetSize();
	}
	/**
	 * 将转移函数以字符串形式返回
	 * inputs 输入字符集合
	 */
	public String toString(ArrayList<String> inputs)
	{
		String result="";
		int size=inputs.size()+1;
		for(int i=0;i<size;i++)
		{
			String str;
			if (i == inputs.size())
				str = "ε";
			else
				str = inputs.get(i);
			Transition ts=EndStates.get(i);
			int tsize=ts.GetSize();
			for(int j=0;j<tsize;j++)
			{
				MyState estate=ts.GetState(j);
				String transtring="("+BeginState.toString()+","+str+")=";
				transtring+=estate.toString()+"\n";
				result+=transtring;
			}
		}
		return result;
	}
	public String[] GetRow()
	{
		int size=this.EndStates.size()-1;
		String temp[]=new String[size+2];
		size++;
		temp[0]=this.BeginState.getStateName();
		for(int i=0;i<size;i++)
		{			
			temp[i+1]=this.EndStates.get(i).toString();
		}
		return temp;
	}
	/**
	 * 返回转移条件为index的转移函数
	 * @param index 转移条件下标
	 * @return
	 */
	public Transition GetTransition(int index)
	{
		return EndStates.get(index);
	}


}
