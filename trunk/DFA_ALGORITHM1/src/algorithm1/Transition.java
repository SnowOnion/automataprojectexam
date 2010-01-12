/**
 * @Title: Transition.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 ����10:49:00
 * @Version: V1.0
 */
package algorithm1;
import java.util.ArrayList;


public class Transition {
	/**
	 * ״̬��һ�������ַ����ת�Ƶ���״̬����
	 */
	private ArrayList<MyState> States;
	/**
	 * ���캯��
	 */
	public Transition()
	{
		States=new ArrayList<MyState>();
	}	
	/**
	 * ���״̬ת�ƺ������״̬
	 * @param end
	 */
	public void AddTransition(MyState end)
	{
		States.add(end);
	}
	/**
	 * ����ת�ƺ�����Ŀ
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
