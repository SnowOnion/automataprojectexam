/**
 * @Title: TestUI.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2010-1-2 下午07:58:54
 * @Version: V1.0
 */
package algorithm1;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class TestUI extends JFrame
{

	private MyAutomaton auto1;
	private MyAutomaton auto2;
	private JTable table1;
	private JTable table2;
	private JTable table3;
	private JTable table4;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	public TestUI(MyAutomaton a1,MyAutomaton a2)
	{
		this.Initial();
		auto1=a1;
		auto2=a2;		
		button1=new JButton("判断");
		button2=new JButton("并运算");
		button3=new JButton("交运算");
		button4=new JButton("补运算");
		button5=new JButton("最小化");
		button6=new JButton("除不可接受");
		button7=new JButton("除不可达");
		Container con=this.getContentPane();
		con.add(button1);
		con.add(button2);
		con.add(button3);
		con.add(button4);
		con.add(button5);
		con.add(button6);
		con.add(button7);
		button1.setBounds(20, 10, 100, 30);
		button2.setBounds(140, 10, 100, 30);
		button3.setBounds(260, 10, 100, 30);
		button4.setBounds(380, 10, 100, 30);
		button5.setBounds(20, 50, 100, 30);
		button6.setBounds(140, 50, 100, 30);
		button7.setBounds(260, 50, 100, 30);
		JSeparator sep=new JSeparator();
		con.add(sep);
		sep.setBounds(0, 90, 500, 1);
		table1=new JTable();
		table2=new JTable();
		table3=new JTable();
		table4=new JTable();
		JScrollPane pane1=new JScrollPane(table1);
		JScrollPane pane2=new JScrollPane(table2);
		JScrollPane pane3=new JScrollPane(table3);
		JScrollPane pane4=new JScrollPane(table4);
		JLabel label1=new JLabel("输入：");
		JLabel label2=new JLabel("输出：");
		con.add(label1);
		label1.setBounds(40,100,60,30);
		con.add(pane1);
		con.add(pane2);
		con.add(pane3);
		con.add(pane4);
		pane1.setBounds(40, 140, 200, 200);
		pane2.setBounds(260, 140, 200, 200);
		JSeparator sep2=new JSeparator();
		con.add(sep2);
		sep2.setBounds(0, 350, 500, 1);
		con.add(label2);
		label2.setBounds(40,350,60,30);
		pane3.setBounds(40, 390, 200, 200);
		pane4.setBounds(260, 390, 200, 200);
		this.MakeButton();
	}
	public void MakeButton()
	{
		button1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						auto1=Construct.getminimal();
						SetTable(table1,auto1);
						auto2=Construct.getnfa();
						SetTable(table2,auto2);
						String str="";
						if(auto1.ISDFA())
						{
							str+="DFA！\n";
						}
						else
						{
							str+="NFA！\n";
						}
						if(auto2.ISDFA())
						{
							str+="DFA！\n";
						}
						else
						{
							str+="NFA！\n";
						}
						javax.swing.JOptionPane.showMessageDialog(null, str);
					}
				}
				);
		button2.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						ArrayList<MyAutomaton> list=Construct.GetTwo();
						auto1=list.get(0);
						auto2=list.get(1);
						SetTable(table1,auto1);
						SetTable(table2,auto2);
						MyAutomaton temp=auto1.Union(auto2);
						SetTable(table3,temp);
						automaton.Automaton au=temp.ConvertToAutomaton();
						System.out.println(au.toString());
					}
				}
				);
		button3.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						ArrayList<MyAutomaton> list=Construct.GetTwo();
						auto1=list.get(0);
						auto2=list.get(1);
						SetTable(table1,auto1);
						SetTable(table2,auto2);
						
						MyAutomaton temp=auto1.Intersection(auto2);
						SetTable(table3,temp);
					}
				}
				);
		button4.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						auto1=Construct.getminimal();
						SetTable(table1,auto1);
						MyAutomaton temp1=auto1.Complementation();
						SetTable(table3,temp1);
					}
				}
				);
		button5.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						auto1=Construct.getminimal();
						SetTable(table1,auto1);
						MyAutomaton temp1=auto1.MinimalDFA();
						SetTable(table3,temp1);
						
					}
				}
				);
		button6.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						auto1=Construct.getacc();
						SetTable(table1,auto1);
						MyAutomaton temp1=auto1.DeleteUnaccept();
						SetTable(table3,temp1);
						
					}
				}
				);
		button7.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						auto1=Construct.getreach();
						SetTable(table1,auto1);
						MyAutomaton temp1=auto1.DeleteUnreach();
						SetTable(table3,temp1);
						
					}
				}
				);
	}
	public void Initial()
	{
		this.setTitle("结果演示");
		this.setVisible(true);
		this.setBounds(400, 200, 500, 650);
		this.setLayout(null);
		this.addWindowListener(
				new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}
				}
				);
	}
	private void SetTable(JTable table,MyAutomaton auto)
	{
		if(auto!=null)
		{
		String names[]=auto.GetTableNames();
		String con[][]=auto.GetTableContent();
		DefaultTableModel model=new DefaultTableModel(con,names);
		table.setModel(model);
		}
	}
	public static void main(String[] args) {
		new TestUI(null,null);

	}

}
class Construct
{
	public static MyAutomaton getminimal()
	{
		ArrayList<String> inputs=new ArrayList<String>();
		inputs.add("0");
		inputs.add("1");
		ArrayList<StateTransition> trans=new ArrayList<StateTransition>();
		MyState states[]=new MyState[8];
		states[0]=new MyState("A");
		states[1]=new MyState("B");
		states[2]=new MyState("C");
		states[3]=new MyState("D");
		states[4]=new MyState("E");
		states[5]=new MyState("F");
		states[6]=new MyState("G");
		states[7]=new MyState("H");
		for(int i=0;i<states.length;i++)
		{
			trans.add(new StateTransition(states[i],inputs.size()));
		}
		trans.get(0).AddStateTransition(0, states[1]);
		trans.get(0).AddStateTransition(1, states[5]);
		trans.get(1).AddStateTransition(0, states[6]);
		trans.get(1).AddStateTransition(1, states[2]);
		trans.get(2).AddStateTransition(0, states[0]);
		trans.get(2).AddStateTransition(1, states[2]);
		trans.get(3).AddStateTransition(0, states[2]);
		trans.get(3).AddStateTransition(1, states[6]);
		
		trans.get(4).AddStateTransition(0, states[4]);
		trans.get(4).AddStateTransition(1, states[5]);
		trans.get(5).AddStateTransition(0, states[2]);
		trans.get(5).AddStateTransition(1, states[6]);
		trans.get(6).AddStateTransition(0, states[6]);
		trans.get(6).AddStateTransition(1, states[4]);
		trans.get(7).AddStateTransition(0, states[6]); 
		trans.get(7).AddStateTransition(1, states[2]);
		MyState inistate=states[0];
		ArrayList<MyState> finals=new ArrayList<MyState>();
		finals.add(states[2]);
		MyAutomaton auto=new MyAutomaton(inputs,trans,inistate,finals);
		return auto;
	}
	public static ArrayList<MyAutomaton> GetTwo()
	{
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
		ArrayList<MyAutomaton> temp=new ArrayList<MyAutomaton>();
		temp.add(auto1);
		temp.add(auto2);
		return temp;
	}
	public static MyAutomaton getacc()
	{
		ArrayList<String> inputs=new ArrayList<String>();
		inputs.add("0");
		inputs.add("1");
		ArrayList<StateTransition> trans=new ArrayList<StateTransition>();
		MyState states[]=new MyState[8];
		states[0]=new MyState("A");
		states[1]=new MyState("B");
		states[2]=new MyState("C");
		states[3]=new MyState("D");
		states[4]=new MyState("E");
		states[5]=new MyState("F");
		states[6]=new MyState("G");
		states[7]=new MyState("H");
		for(int i=0;i<states.length;i++)
		{
			trans.add(new StateTransition(states[i],inputs.size()));
		}
		trans.get(0).AddStateTransition(0, states[1]);
		trans.get(0).AddStateTransition(1, states[5]);
		trans.get(1).AddStateTransition(0, states[6]);
		trans.get(1).AddStateTransition(1, states[2]);
		trans.get(2).AddStateTransition(0, states[0]);
		trans.get(2).AddStateTransition(1, states[2]);
		trans.get(3).AddStateTransition(0, states[2]);
		trans.get(3).AddStateTransition(1, states[6]);
		
		trans.get(4).AddStateTransition(0, states[4]);
		trans.get(4).AddStateTransition(1, states[5]);
		trans.get(5).AddStateTransition(0, states[2]);
		trans.get(5).AddStateTransition(1, states[6]);
		trans.get(6).AddStateTransition(0, states[6]);
		trans.get(6).AddStateTransition(1, states[4]);
		trans.get(7).AddStateTransition(0, states[7]); 
		trans.get(7).AddStateTransition(1, states[7]);
		MyState inistate=states[0];
		ArrayList<MyState> finals=new ArrayList<MyState>();
		finals.add(states[2]);
		MyAutomaton auto=new MyAutomaton(inputs,trans,inistate,finals);
		return auto;
	}
	public static MyAutomaton getreach()
	{
		ArrayList<String> inputs=new ArrayList<String>();
		inputs.add("0");
		inputs.add("1");
		ArrayList<StateTransition> trans=new ArrayList<StateTransition>();
		MyState states[]=new MyState[8];
		states[0]=new MyState("A");
		states[1]=new MyState("B");
		states[2]=new MyState("C");
		states[3]=new MyState("D");
		states[4]=new MyState("E");
		states[5]=new MyState("F");
		states[6]=new MyState("G");
		states[7]=new MyState("H");
		for(int i=0;i<states.length;i++)
		{
			trans.add(new StateTransition(states[i],inputs.size()));
		}
		trans.get(0).AddStateTransition(0, states[1]);
		trans.get(0).AddStateTransition(1, states[5]);
		trans.get(1).AddStateTransition(0, states[6]);
		trans.get(1).AddStateTransition(1, states[2]);
		trans.get(2).AddStateTransition(0, states[0]);
		trans.get(2).AddStateTransition(1, states[2]);
		trans.get(3).AddStateTransition(0, states[2]);
		trans.get(3).AddStateTransition(1, states[6]);
		
		trans.get(4).AddStateTransition(0, states[4]);
		trans.get(4).AddStateTransition(1, states[5]);
		trans.get(5).AddStateTransition(0, states[2]);
		trans.get(5).AddStateTransition(1, states[6]);
		trans.get(6).AddStateTransition(0, states[6]);
		trans.get(6).AddStateTransition(1, states[4]);
		trans.get(7).AddStateTransition(0, states[6]); 
		trans.get(7).AddStateTransition(1, states[2]);
		MyState inistate=states[0];
		ArrayList<MyState> finals=new ArrayList<MyState>();
		finals.add(states[2]);
		MyAutomaton auto=new MyAutomaton(inputs,trans,inistate,finals);
		return auto;
	}
	public static MyAutomaton getnfa()
	{
		ArrayList<String> inputs=new ArrayList<String>();
		inputs.add("0");
		inputs.add("1");
		ArrayList<StateTransition> trans=new ArrayList<StateTransition>();
		MyState states[]=new MyState[8];
		states[0]=new MyState("A");
		states[1]=new MyState("B");
		states[2]=new MyState("C");
		states[3]=new MyState("D");
		states[4]=new MyState("E");
		states[5]=new MyState("F");
		states[6]=new MyState("G");
		states[7]=new MyState("H");
		for(int i=0;i<states.length;i++)
		{
			trans.add(new StateTransition(states[i],inputs.size()));
		}
		trans.get(0).AddStateTransition(0, states[1]);
		trans.get(0).AddStateTransition(1, states[5]);
		trans.get(1).AddStateTransition(0, states[6]);
		trans.get(1).AddStateTransition(1, states[2]);
		trans.get(2).AddStateTransition(0, states[0]);
		trans.get(2).AddStateTransition(1, states[2]);
		trans.get(3).AddStateTransition(0, states[2]);
		trans.get(3).AddStateTransition(1, states[6]);
		trans.get(3).AddStateTransition(1, states[7]);
		trans.get(4).AddStateTransition(0, states[4]);
		trans.get(4).AddStateTransition(1, states[5]);
		trans.get(5).AddStateTransition(0, states[2]);
		trans.get(5).AddStateTransition(1, states[6]);
		trans.get(6).AddStateTransition(0, states[6]);
		trans.get(6).AddStateTransition(1, states[4]);
		trans.get(7).AddStateTransition(0, states[6]); 
		trans.get(7).AddStateTransition(1, states[2]);
		MyState inistate=states[0];
		ArrayList<MyState> finals=new ArrayList<MyState>();
		finals.add(states[2]);
		MyAutomaton auto=new MyAutomaton(inputs,trans,inistate,finals);
		return auto;
	}
}
