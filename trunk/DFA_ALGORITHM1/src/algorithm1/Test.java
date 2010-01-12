/**
 * @Title: Test.java
 * @Description: just for test
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-29 ÉÏÎç01:54:47
 * @Version: V1.0
 */
package algorithm1;

import java.util.ArrayList;


public class Test {
	public static void main(String argvs[])
	{
//		ArrayList<String> inputs=new ArrayList<String>();
//		inputs.add("0");
//		inputs.add("1");
//		ArrayList<StateTransition> trans=new ArrayList<StateTransition>();
//		MyState states[]=new MyState[8];
//		states[0]=new MyState("A");
//		states[1]=new MyState("B");
//		states[2]=new MyState("C");
//		states[3]=new MyState("D");
//		states[4]=new MyState("E");
//		states[5]=new MyState("F");
//		states[6]=new MyState("G");
//		states[7]=new MyState("H");
//		for(int i=0;i<states.length;i++)
//		{
//			trans.add(new StateTransition(states[i],inputs.size()));
//		}
//		trans.get(0).AddStateTransition(0, states[1]);
//		trans.get(0).AddStateTransition(1, states[5]);
//		trans.get(1).AddStateTransition(0, states[6]);
//		trans.get(1).AddStateTransition(1, states[2]);
//		trans.get(2).AddStateTransition(0, states[0]);
//		trans.get(2).AddStateTransition(1, states[2]);
//		trans.get(3).AddStateTransition(0, states[2]);
//		trans.get(3).AddStateTransition(1, states[6]);
//		
//		trans.get(4).AddStateTransition(0, states[4]);
//		trans.get(4).AddStateTransition(1, states[5]);
//		trans.get(5).AddStateTransition(0, states[2]);
//		trans.get(5).AddStateTransition(1, states[6]);
//		trans.get(6).AddStateTransition(0, states[6]);
//		trans.get(6).AddStateTransition(1, states[4]);
//		trans.get(7).AddStateTransition(0, states[6]); 
//		trans.get(7).AddStateTransition(1, states[2]);
//		MyState inistate=states[0];
//		ArrayList<MyState> finals=new ArrayList<MyState>();
//		finals.add(states[2]);
//		MyAutomaton auto=new MyAutomaton(inputs,trans,inistate,finals);
//		auto.Display();
//		System.out.println(auto.ISDFA());
//		MyAutomaton a=auto.MinimalDFA();
//		a.Display();
//		auto.Complementation().Display();
//		auto.DeleteUnreach().Display();
		ArrayList<String> inputs=new ArrayList<String>();
		inputs.add("0");
		inputs.add("1");
		ArrayList<StateTransition> trans1=new ArrayList<StateTransition>();
		ArrayList<StateTransition> trans2=new ArrayList<StateTransition>();
		MyState states[]=new MyState[4];
		states[0]=new MyState("p");
		states[1]=new MyState("q");
		states[2]=new MyState("r");
		states[3]=new MyState("s");
		for (int i = 0; i < 2; i++) 
		{
			trans1.add(new StateTransition(states[i], inputs.size()));
		}
		for (int i = 2; i < 4; i++) 
		{
			trans2.add(new StateTransition(states[i], inputs.size()));
		}
		trans1.get(0).AddStateTransition(0, states[1]);
		trans1.get(0).AddStateTransition(1, states[0]);
		trans1.get(1).AddStateTransition(0, states[1]);
		trans1.get(1).AddStateTransition(1, states[1]);
		trans2.get(0).AddStateTransition(0, states[2]);
		trans2.get(0).AddStateTransition(1, states[3]);
		trans2.get(1).AddStateTransition(0, states[3]);
		trans2.get(1).AddStateTransition(1, states[3]);
		MyState inistate1=states[0];
		ArrayList<MyState> finals1=new ArrayList<MyState>();
		finals1.add(states[1]);
		MyAutomaton auto1=new MyAutomaton(inputs,trans1,inistate1,finals1);
		MyState inistate2=states[2];
		ArrayList<MyState> finals2=new ArrayList<MyState>();
		finals2.add(states[3]);
		MyAutomaton auto2=new MyAutomaton(inputs,trans2,inistate2,finals2);
		auto1.Union(auto2).Display();
	}

}
