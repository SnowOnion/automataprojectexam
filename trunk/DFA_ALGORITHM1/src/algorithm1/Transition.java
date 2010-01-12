/**
 * @Title: Transition.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 上午10:49:00
 * @Version: V1.0
 */
package algorithm1;
import java.util.ArrayList;


public class Transition {
	/**
	 * 状态经一个输入字符后可转移到的状态集合
	 */
	private ArrayList<MyState> States;
	/**
	 * 构造函数
	 */
	public Transition()
	{
		States=new ArrayList<MyState>();
	}	
	/**
	 * 添加状态转移函数结果状态
	 * @param end
	 */
	public void AddTransition(MyState end)
	{
		States.add(end);
	}
	/**
	 * 返回转移函数数目
	 * @return
	 */
    public int GetSize()
    {
    	return States.size();
    }
    public MyState GetState(int index)
    {
    	return States.get(index);
    }
    public String toString()
    {
    	String str="";
    	int size=this.States.size();
    	if(size!=0)
    	{
    		str="{";
    
    	for(int i=0;i<size;i++)
    	{
    		str+=this.States.get(i).getStateName();
    		if(i!=size-1)
    			str+=",";
    		else
    			str+="}";
    	}
    	}
    	return str;
    }
}
