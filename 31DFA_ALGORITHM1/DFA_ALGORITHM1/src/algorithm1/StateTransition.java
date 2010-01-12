/**
 * @Title: StateTransition.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 ����10:53:10
 * @Version: V1.0
 */
package algorithm1;
import java.util.ArrayList;

public class StateTransition {
	/**
	 * ״̬
	 */
	private MyState BeginState;
	/**
	 * ״̬BeginState������ת�ƺ���
	 */
	ArrayList<Transition> EndStates;
	/**
	 * ���캯��
	 * ��ʼ��״̬��ͬʱ��ʼ��״̬��ÿ��ת��������Ӧ��ת�ƺ���Ϊ��
	 * @param state ״̬
	 * @param size ת��������
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
	 * ���״̬ת�ƺ���
	 * @param index ת�������±�
	 * @param state ת�Ƶ���״̬
	 */
	public void AddStateTransition(int index,MyState state)
	{
		Transition tempts=EndStates.get(index);
		tempts.AddTransition(state);
	}
	/**
	 * ��ȡ�±�Ϊindex�������£�ת�ƺ���size
	 * @param index �����±�
	 * @return ת�ƺ�����Ŀ
	 */
	public int GetTransSize(int index)
	{
		Transition tempts=EndStates.get(index);
		return tempts.GetSize();
	}
	/**
	 * ��ת�ƺ������ַ�����ʽ����
	 * inputs �����ַ�����
	 */
	public String toString(ArrayList<String> inputs)
	{
		String result="";
		int size=inputs.size()+1;
		for(int i=0;i<size;i++)
		{
			String str;
			if (i == inputs.size())
				str = "��";
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
	 * ����ת������Ϊindex��ת�ƺ���
	 * @param index ת�������±�
	 * @return
	 */
	public Transition GetTransition(int index)
	{
		return EndStates.get(index);
	}


}
