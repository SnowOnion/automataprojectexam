/**
 * @Title: MyAutomaton.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 上午11:09:47
 * @Version: V1.0
 */
package algorithm1;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Collection;

import automaton.*;
import java.util.HashMap;;;

public class MyAutomaton {
	/**
	 * 输入字符集合
	 */
	private ArrayList<String> InputSymbols;
	/**
	 * 状态以及状态转移函数集合
	 */
	private ArrayList<StateTransition> StateTrans;
	/**
	 * 开始状态
	 */
	private MyState StartState;
	/**
	 * 终止状态集合
	 */
	private ArrayList<MyState> FinalStates;
	/**
	 * 构造函数
	 * 11自动机表达形式转换 
	 */
	public MyAutomaton(Automaton auto)
	{
		this.InputSymbols=ArrayOp.StringArrayClone(auto.getInputSymbolSet());
		String inistate=auto.getInitialState().getStateId();
		int inputsize=this.InputSymbols.size();
		Collection<State> cstates=auto.getStates().values();
		State states[]=new State[cstates.size()];
		cstates.toArray(states);
		int statesize=states.length;
		ArrayList<StateTransition> mystates=new ArrayList<StateTransition>();
		MyState startstate=null;
		ArrayList<MyState> finalstate=new ArrayList<MyState>();
		ArrayList<String> statename=new ArrayList<String>();
		for(int i=0;i<statesize;i++)
		{
			String stateid=states[i].getStateId();
			MyState tempstate=new MyState(stateid);
			if(states[i].getStateType()==AutomatonConstant.STATETYPE_ACCEPTED)
			{
				finalstate.add(tempstate);
			}
			if(inistate.equals(stateid))
			{
				startstate=tempstate;
			}
			statename.add(stateid);
			StateTransition tempst=new StateTransition(tempstate,inputsize);
			mystates.add(tempst);
		}
		ArrayList<automaton.Transition> oldtrans=auto.getTransitions();
		int oldtranssize=oldtrans.size();
		for(int i=0;i<oldtranssize;i++)
		{
			automaton.TransitionNFA tempts=(automaton.TransitionNFA )oldtrans.get(i);
			State fromstate=tempts.getFromState();
			State tostate=tempts.getToState();
			ArrayList<String> conditions=tempts.getTransitionConditions();
			int consize=conditions.size();
			int fromid=ArrayOp.FindString(statename, fromstate.getStateId());
			int toid=ArrayOp.FindString(statename, tostate.getStateId());
			MyState estate=mystates.get(toid).getBeginState();
			StateTransition bstate=mystates.get(fromid);
			for(int c=0;c<consize;c++)
			{
				String str=conditions.get(c);
				int offset;
				if(str.equals("ε"))
					offset=inputsize;
				else
					offset=ArrayOp.FindString(this.InputSymbols, str);
				bstate.AddStateTransition(offset, estate);
			}
		}
		this.StateTrans=mystates;
		this.StartState=startstate;
		this.FinalStates=finalstate;
		
	}
	public Automaton ConvertToAutomaton()
	{
		long firstid=this.StateTrans.get(0).getBeginState().getStateID();
		Automaton auto=new Automaton();
		auto.setInputSymbolSet(this.InputSymbols);
		ArrayList<State> states=new ArrayList<State>();
		int statesize=this.StateTrans.size();
		int inputsize=this.InputSymbols.size();
		for(int i=0;i<statesize;i++)
		{
			State tempstate=new State(""+this.StateTrans.get(i).getBeginState().getStateID());
			auto.addState(tempstate);
			states.add(tempstate);
		}
		
		ArrayList<automaton.Transition> newtrans=new ArrayList<automaton.Transition>();
		//状态转移函数
		for(int i=0;i<statesize;i++)
		{
			StateTransition st=this.StateTrans.get(i);
			State bstate=states.get(i);
			for(int j=0;j<inputsize+1;j++)
			{
				algorithm1.Transition ts=st.GetTransition(j);
				int tsize=ts.GetSize();
				for(int k=0;k<tsize;k++)
				{
					MyState estate=ts.GetState(k);
					int offset=(int)(estate.getStateID()-firstid);
					State endstate=states.get(offset);
					String con="";
					if(j==inputsize)
					{
						con="ε";
					}
					else
					{
						con=this.InputSymbols.get(j);
					}
					ArrayList<String> condition=new ArrayList<String>();
					automaton.Transition oldts=new automaton.TransitionNFA(bstate,condition,endstate);
					newtrans.add(oldts);
				}
			}		
		}
		auto.setTransitions(newtrans);
		//初始状态
		int offset=(int)(this.StartState.getStateID()-firstid);
		State inistate=states.get(offset);
		auto.setInitialState(inistate);
		//终止状态
		int fsize=this.FinalStates.size();
		for(int i=0;i<fsize;i++)
		{
			offset=(int)(this.FinalStates.get(i).getStateID()-firstid);
			states.get(offset).setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
		}
		return auto;
	}
	/**
	 * 构造函数
	 * @param inputs
	 * @param st
	 * @param state
	 * @param finalstate
	 */
	public MyAutomaton(ArrayList<String> inputs,ArrayList<StateTransition> st,MyState state,ArrayList<MyState> finalstate)
	{
		this.InputSymbols=inputs;
		this.StateTrans=st;
		this.StartState=state;
		this.FinalStates=finalstate;
	}
	/**
	 * 判断该自动机是否为DFA
	 * 判断方式：1.转移函数的转移条件为空
	 *         2.相同转移条件可以到达两个以上的状态
	 *         3.是否对每个输入字符都定义了转移函数	
	 * @return 
	 */
	public boolean ISDFA()
	{
		int size=StateTrans.size();
		for(int i=0;i<size;i++)
		{
			StateTransition st=StateTrans.get(i);
			ArrayList<Transition> funcs=st.getEndStates();
			Transition tempts=funcs.get(funcs.size()-1);
			if(tempts.GetSize()!=0)   //某个状态有条件为空的转移函数
				return false;
			int fsize=funcs.size();
			for(int j=0;j<fsize-1;j++)
			{
				tempts=funcs.get(j);
				if(tempts.GetSize()!=1)
					return false;     //某个状态在某个条件下没有转移函数 或者有多个转移函数
			}
		}
		return true;
	}
	/**
	 * 计算DFA的补集
	 * @return
	 */
	public MyAutomaton Complementation()
	{
		if(!this.ISDFA())
		{
			System.out.println(Error.getMsg(1));
			return null;
		}
		//构造输入字符
		ArrayList<String> inputs=ArrayOp.StringArrayClone(this.InputSymbols);
		//构造状态和状态转移函数
		ArrayList<StateTransition> statetrans=new ArrayList<StateTransition>();
		int stsize=this.StateTrans.size();
		for(int i=0;i<stsize;i++)
		{
			StateTransition st=StateTrans.get(i);
			MyState bstate=st.getBeginState().clone();
			StateTransition newst=new StateTransition(bstate,inputs.size());
			statetrans.add(newst);
		}
		for(int i=0;i<stsize;i++)
		{
			StateTransition oldst=StateTrans.get(i);
			StateTransition newst=statetrans.get(i);
		    int inputsize=inputs.size();
		    for(int j=0;j<inputsize;j++)
		    {
		    	Transition tempts=oldst.getEndStates().get(j);
		    	int tssize=tempts.GetSize();
		    	for(int k=0;k<tssize;k++)
		    	{
		    		long firstid=StateTrans.get(0).getBeginState().getStateID();
		    		long stateid=tempts.GetState(k).getStateID(); //原状态的ID
		    		int offset=(int)(stateid-firstid);
		    		MyState estate=statetrans.get(offset).getBeginState();
		    		newst.AddStateTransition(j, estate);
		    	}
		    }
		}
		//构造初始状态
		long firstid=StateTrans.get(0).getBeginState().getStateID();
		long startid=StartState.getStateID();
		int offset=(int)(startid-firstid);
		MyState newState=statetrans.get(offset).getBeginState();
		//构造终止状态
		int finalsize=FinalStates.size();
		boolean isfinal[]=new boolean[StateTrans.size()];		
		for(int i=0;i<finalsize;i++)
		{
			long finalid=FinalStates.get(i).getStateID();
			offset=(int)(finalid-firstid);
			isfinal[offset]=true;
		}
		ArrayList<MyState> finalstate=new ArrayList<MyState>();
		for(int i=0;i<StateTrans.size();i++)
		{
			if(isfinal[i]==false)
				finalstate.add(statetrans.get(i).getBeginState());
		}
		//构造补集自动机
		return new MyAutomaton(inputs,statetrans,newState,finalstate);
	}
	/**
	 * 计算两个自动机的交集
	 * @param op
	 * @return
	 */
	public MyAutomaton Intersection(MyAutomaton op)
	{
		if(this==op)
		{
			return this;
		}
		ArrayList<String> inputs=new ArrayList<String>();
		ArrayList<SymbolIndex> indexes=new ArrayList<SymbolIndex>();
		int inputsize=InputSymbols.size();
		//构造输入字符，两个DFA输入字符的交集
		for(int i=0;i<inputsize;i++)
		{
			String str=InputSymbols.get(i);
			int cursor=ArrayOp.FindString(op.InputSymbols, str);
			if(cursor!=-1)
			{
				inputs.add(str);
				SymbolIndex sindex=new SymbolIndex(i,cursor,inputs.size()-1);
				indexes.add(sindex);
			}
		}
		//构造状态 状态转移函数
		ArrayList<StateTransition> statetrans=new ArrayList<StateTransition>();
		int statesize1=this.StateTrans.size();
		int statesize2=op.StateTrans.size();
		for(int i=0;i<statesize1;i++)
		{
			MyState state1=this.StateTrans.get(i).getBeginState();
			for(int j=0;j<statesize2;j++)
			{
				MyState state2=op.StateTrans.get(j).getBeginState();
				MyState state=new MyState(state1.getStateName()+state2.getStateName());
				StateTransition st=new StateTransition(state,inputs.size());
				statetrans.add(st);
			}
		}
		//添加状态转移函数
		StateTransition st1;
		StateTransition st2;
		int index1;
		int index2;
		SymbolIndex si;
		Transition ts1;
		Transition ts2;
		int offset1;
		int offset2;
		long firstid1=StateTrans.get(0).getBeginState().getStateID();
		long firstid2=op.StateTrans.get(0).getBeginState().getStateID();
		int estateindex;
		for(int i=0;i<statesize1;i++)
		{
			st1=StateTrans.get(i);
			for(int j=0;j<statesize2;j++)
			{
				st2=op.StateTrans.get(j);
				for(int k=0;k<inputs.size();k++)
				{
					si=indexes.get(k);
					index1=si.Index1;
					index2=si.Index2;
					ts1=st1.GetTransition(index1);
					ts2=st2.GetTransition(index2);
					offset1=(int)(ts1.GetState(0).getStateID()-firstid1);
					
					offset2=(int)(ts2.GetState(0).getStateID()-firstid2);
					estateindex=offset1*(statesize2)+offset2;
					
					MyState estate=statetrans.get(estateindex).getBeginState();
					StateTransition st=statetrans.get(i*(statesize2)+j);
					st.AddStateTransition(k, estate);
				}
			}
		}
		//构造初始状态
		offset1=(int)(this.StartState.getStateID()-firstid1);
		offset2=(int)(op.StartState.getStateID()-firstid2);
		int offset=offset1*(statesize2)+offset2;
		MyState startstate=statetrans.get(offset).getBeginState();
		//构造终止状态集合
		ArrayList<MyState> finals=new ArrayList<MyState>();
		int fsize1=this.FinalStates.size();
		int fsize2=op.FinalStates.size();
		for(int i=0;i<fsize1;i++)
		{
			offset1=(int)(this.FinalStates.get(i).getStateID()-firstid1);
			for(int j=0;j<fsize2;j++)
			{
				offset2=(int)(op.FinalStates.get(j).getStateID()-firstid2);
				offset=offset1*statesize2+offset2;
				finals.add(statetrans.get(offset).getBeginState());
			}
		}
		return new MyAutomaton(inputs,statetrans,startstate,finals);
	}
	/**
	 * 复制状态列表 保证每个自动机的状态的id都不同
	 * @param newtrans 
	 * @param oldtrans
	 */
	public void RenameState(ArrayList<StateTransition> newtrans,ArrayList<StateTransition> oldtrans,int inputsize,int convert[])
	{
		int offset=newtrans.size();
		//添加状态
		int size=oldtrans.size();
		MyState tempstate;
		StateTransition tempst;
		for(int i=0;i<size;i++)
		{
			tempstate=oldtrans.get(i).getBeginState().clone();
			tempst=new StateTransition(tempstate,inputsize);
			newtrans.add(tempst);
		}
		//添加状态转移
		long firstid=oldtrans.get(0).getBeginState().getStateID();
		Transition tempts;
		int index,cursor;
		for(int i=0;i<size;i++)
		{
			tempst=oldtrans.get(i);
			int consize=tempst.EndStates.size();
			for(int j=0;j<consize;j++)
			{
				tempts=tempst.EndStates.get(j);
				index=convert[j];
				int esize=tempts.GetSize();
				for(int k=0;k<esize;k++)
				{
					tempstate=tempts.GetState(k);
					cursor=(int)(tempstate.getStateID()-firstid);
					cursor+=offset;
					tempstate=newtrans.get(cursor).getBeginState();
					newtrans.get(offset+i).AddStateTransition(index, tempstate);
				}
			}
		}
		
	}
	/**
	 * 计算并集时  添加新的状态转移函数
	 * @param trans
	 * @param offset
	 * @param auto
	 */
	public void AddStartFinal(ArrayList<StateTransition> trans,int offset,MyAutomaton auto)
	{
		//添加初始状态转移
		int con=trans.get(0).getEndStates().size()-1;
		long firstid=auto.StateTrans.get(0).getBeginState().getStateID();
		StateTransition startstate=trans.get(0);
		int index=(int)(auto.StartState.getStateID()-firstid);
		index+=offset;
		MyState tempstate=trans.get(index).getBeginState();
		trans.get(0).AddStateTransition(con, tempstate);
		int finalsize=auto.FinalStates.size();
		for(int i=0;i<finalsize;i++)
		{
			index=(int)(auto.FinalStates.get(i).getStateID()-firstid);
			index+=offset;
			trans.get(index).AddStateTransition(con, trans.get(1).getBeginState());
		}
	}
	/**
	 * 计算两个自动机的并集
	 * @param op
	 * @return
	 */
	public MyAutomaton Union(MyAutomaton op)
	{
		if(this==op)
		{
			return this;
		}
		//输入字母表
		ArrayList<String> inputs=new ArrayList<String>();
		int input1[]=new int[this.InputSymbols.size()+1];  //输入字符下标的变化
		int input2[]=new int[op.InputSymbols.size()+1];      //输入字符下标的变化
		int inputsize1=this.InputSymbols.size();
		int inputsize2=op.InputSymbols.size();
		int index;
		for(int i=0;i<inputsize1;i++)
		{
			String str=this.InputSymbols.get(i);
			index=ArrayOp.AddString(inputs, str);
			input1[i]=index;
		}
		for(int i=0;i<inputsize2;i++)
		{
			String str=op.InputSymbols.get(i);
			index=ArrayOp.AddString(inputs, str);
			input2[i]=index;
		}
		input1[inputsize1]=inputs.size();
		input2[inputsize2]=inputs.size();
		//构造初始状态
		MyState startstate=new MyState("start");
		//构造终止状态
		MyState finalstate=new MyState("final");
		//构造状态
		ArrayList<StateTransition> statetrans=new ArrayList<StateTransition>();
		StateTransition tempst=new StateTransition(startstate,inputs.size());
		statetrans.add(tempst);
		tempst=new StateTransition(finalstate,inputs.size());
		statetrans.add(tempst);
		int statesize1=this.StateTrans.size();
		int statesize2=op.StateTrans.size();
		RenameState(statetrans,this.StateTrans,inputs.size(),input1);
		RenameState(statetrans,op.StateTrans,inputs.size(),input2);
		AddStartFinal(statetrans,2,this);
		AddStartFinal(statetrans,2+statesize1,op);
		ArrayList<MyState> finalstates=new ArrayList<MyState>();
		finalstates.add(finalstate);
		return new MyAutomaton(inputs,statetrans,startstate,finalstates);
		
	}
	/**
	 * 初始化不等价关系表  状态转移表
	 * F*(S-F)置为不等价 其他都置为未知
	 * @param equtable
	 * @param size   状态数
	 */
	private boolean Initial(boolean equtable[][],int transitiontable[][],int size)
	{
		long firstid=this.StateTrans.get(0).getBeginState().getStateID();
		//初始化转移表	
		boolean isfail=false;
		int statesitze=this.StateTrans.size();
		for(int i=0;i<size;i++)
		{
			StateTransition tempst=this.StateTrans.get(i);
			if(tempst.GetTransSize(this.InputSymbols.size())!=0)
				return false;
			for(int j=0;j<this.InputSymbols.size();j++)
			{
				Transition tempts=tempst.GetTransition(j);
				if(tempts.GetSize()>1)
					return false;
				if(tempts.GetSize()==0)
				{
					isfail=true;
					transitiontable[i][j]=size;
				}
				else
				{
					MyState tempstate=tempts.GetState(0);
					int index=(int)(tempstate.getStateID()-firstid);
					transitiontable[i][j]=index;
				}
					
			}
		}
		//需要虚拟失败状态
		if(isfail)
		{
			size++;   //下标为size-1的状态为失败状态
			for(int i=0;i<this.InputSymbols.size();i++)
				transitiontable[size-1][i]=size-1;  //失败状态的转移函数表  任何条件都转移到自身
			
		}
		//首先所有置为未知
		for(int i=0;i<size-1;i++)
		{			
			for(int j=0;j<size-1;j++)
				equtable[i][j]=false;
		}
		//F*(S-F)置为不等价		
		int finalsize=this.FinalStates.size();
		for(int k=0;k<finalsize;k++)
		{
			long stateid=this.FinalStates.get(k).getStateID();
			int index=(int)(stateid-firstid);
			for(int i=0;i<index;i++)
			{
				equtable[index-1][i]=true;
			}
			for(int i=index+1;i>index&&i<size;i++)
			{
				equtable[i-1][index]=true;
			}
		}	
		return true;
	}
	
	private boolean GetLable(boolean equtable[][],int i,int j)
	{
		if(i>j)
		{
			i--;
			return equtable[i][j];
		}
		if(i<j)
		{
			j--;
			return equtable[j][i];
		}
		return false;
	}
	/**
	 * 递归标记
	 * @param statesize
	 * @param DecideList
	 * @param unequtable
	 * @param i
	 * @param j
	 */
	private void RecurSetLable(int statesize,DecidePair DecideList[],boolean unequtable[][],int i,int j)
	{
		if(this.GetLable(unequtable, i, j)==false)
		{
			this.SetLable(unequtable, i, j);
			int min=-1,max=-1;
			if(i<j)
			{
				min=i;
				max=j;
			}
			else if((j<i))
			{
				min=j;
				max=i;
			}
			
			int index=((2*statesize-1-min)*min)/2+max-min-1;
			
			int dsize=DecideList[index].GetSize();
			for(int k=0;k<dsize;k++)
			{
				StatePair temp=DecideList[index].GetPair(k);
				RecurSetLable(statesize,DecideList,unequtable,temp.Index1,temp.Index2);
			}
		}
	}
	private void SetLable(boolean unequtable[][],int i,int j)
	{
		if(i>j)
		{
			i--;
			unequtable[i][j]=true;
		}
		if(i<j)
		{
			j--;
			unequtable[j][i]=true;
		}
	}
	private void AddDecidePair(DecidePair DecideList[],StatePair pair1,StatePair decidepair,int statesize)
	{
		int i=pair1.Index1;
		int j=pair1.Index2;
		int min=-1,max=-1;
		if(i<j)
		{
			min=i;
			max=j;
		}
		else if((j<i))
		{
			min=j;
			max=i;
		}
		
		int index=((2*statesize-1-min)*min)/2+max-min-1;		
		DecideList[index].Add(decidepair);
	}
    /**
     * 为状态划分等价类
     * @param unequtable 不等价表
     * @param size 状态的数目
     */
	private int PartitionKind(boolean unequtable[][],int statekind[],int size)
	{
		int kind=0;//当前可分配的状态
		
		
		//从每个状态开始寻找与其等价的状态
		for(int i=0;i<size;i++)
		{
			//状态i还没有被划分，则划分包含i的等价类
			if(statekind[i]==-1)
			{
				//为i所在等价类标记为kind
				statekind[i]=kind;
				kind++;
				//广度优先搜索 
				Queue<Integer> stateq=new LinkedList<Integer>();
				stateq.add(new Integer(i));
				while(stateq.size()!=0)
				{
					int index=(Integer)stateq.remove();
					for(int s=0;s<size;s++)
					{
						//index和所有其他状态是否等价
						if(index!=s)
						{
							//index和s等价且s没有被标记等价类别
							if(statekind[s]==-1&&this.GetLable(unequtable, index, s)==false)
							{
								statekind[s]=statekind[index];  //标记相同的类别
								stateq.add(new Integer(s));     
							}
						}
					}
				}
			}
		}
		return kind;
	}
	public MyAutomaton MinimalDFA()
	{
		int statesize=this.StateTrans.size();
		int TransTable[][]=new int[statesize+1][this.InputSymbols.size()];
		boolean UnequTable[][]=new boolean[statesize][statesize];
		boolean re=this.Initial(UnequTable, TransTable, statesize);
		
		//不符合DFA定义
		if(re==false)
		{
			System.out.println(Error.getMsg(1));
			return null;
		}
		//需要虚拟失败状态 
		if(TransTable[statesize][0]==statesize)
			statesize++;
		int inputsize=this.InputSymbols.size();
		DecidePair DecideList[]=new DecidePair[statesize*(statesize-1)/2];
		for(int i=0;i<statesize*(statesize-1)/2;i++)
			DecideList[i]=new DecidePair();
		for(int i=0;i<statesize;i++)
		{			
			for(int j=0;j<statesize;j++)
			{
				boolean lable=this.GetLable(UnequTable, i, j);
				//i!=j并且i和j状态没有标记为不等价
				if(i!=j&&lable==false)
				{
					int k;
					ArrayList<StatePair> pairs=new ArrayList<StatePair>();
					for(k=0;k<inputsize;k++)
					{					
						int mem1=TransTable[i][k];
						int mem2=TransTable[j][k];
						if(this.GetLable(UnequTable, mem1, mem2))
						{
							//递归标记
							this.RecurSetLable(statesize, DecideList, UnequTable, i, j);
							break;
						}
						else if(mem1==mem2||(i==mem1&&j==mem2)||(j==mem1&&i==mem2))
						{
							
						}
						else
						{
							StatePair temppair=new StatePair(mem1,mem2);
							pairs.add(temppair);
						}
					}
					if(k==inputsize)
					{
						StatePair decidepair=new StatePair(i,j);
						int psize=pairs.size();
						for(int s=0;s<psize;s++)
						{
							StatePair temppair=pairs.get(s);
							this.AddDecidePair(DecideList, temppair, decidepair, statesize);
						}
					}
				}
			}
		}
		
		int statekind[]=new int[this.StateTrans.size()];  //每个状态所属的类别
		for(int i=0;i<this.StateTrans.size();i++)
		{
			statekind[i]=-1;
		}
		int kindsize=this.PartitionKind(UnequTable, statekind, this.StateTrans.size());
		int minstate[]=new int[kindsize];
		for(int i=0;i<kindsize;i++)
		{
			minstate[i]=-1;
		}
		for(int i=0;i<this.StateTrans.size();i++)
		{
			if(minstate[statekind[i]]==-1)
				minstate[statekind[i]]=i;
		}		
		//创建新的状态状态改名
		ArrayList<StateTransition> newstate=new ArrayList<StateTransition>();
		for(int i=0;i<kindsize;i++)
		{
			int index=minstate[i];
			MyState tempstate=new MyState(this.StateTrans.get(index).getBeginState().toString());
			StateTransition tempst=new StateTransition(tempstate,inputsize);
			newstate.add(tempst);
		}
		statesize=this.StateTrans.size();
		for(int i=0;i<kindsize;i++)   //最小化后的第i个状态
		{
			StateTransition tempst=newstate.get(i);
			int index=minstate[i];    //index为最小化前的状态下标
			for(int j=0;j<inputsize;j++)
			{
				if(TransTable[index][j]!=statesize)
				{
					int cursor=statekind[TransTable[index][j]];   //最小化后转移以后的状态下标
					MyState tempstate=newstate.get(cursor).getBeginState();
					tempst.AddStateTransition(j, tempstate);
				}
			}
		}
		ArrayList<String> inputs=ArrayOp.StringArrayClone(this.InputSymbols);
		long firstid=this.StateTrans.get(0).getBeginState().getStateID();
		int startindex=(int)(this.StartState.getStateID()-firstid);
		int newstartindex=statekind[startindex];
		MyState newstart=newstate.get(newstartindex).getBeginState();
		int fsize=this.FinalStates.size();
		boolean isfinal[]=new boolean[kindsize];
		ArrayList<MyState> finalstate=new ArrayList<MyState>();
		for(int i=0;i<fsize;i++)
		{
			int finalid=(int)(this.FinalStates.get(i).getStateID()-firstid);
			int finalkind=statekind[finalid];
			if(isfinal[finalid]==false)
			{
				isfinal[finalkind]=true;
				finalstate.add(newstate.get(finalkind).getBeginState());
			}
		}
		return new MyAutomaton(inputs,newstate,newstart,finalstate);
	}
	/**
	 * 去除DFA的不可到达状态
	 * @return 返回一个新的自动机不包含不可达状态
	 */
	public MyAutomaton DeleteUnreach()
	{
		int statesize=this.StateTrans.size();
		boolean judge[]=new boolean[statesize];
		Queue<Integer> queue=new LinkedList<Integer>();
		long firstid=this.StateTrans.get(0).getBeginState().getStateID();
		int cursor=(int)(this.StartState.getStateID()-firstid);
		queue.add(cursor);
		judge[cursor]=true;
		int inputsize=this.InputSymbols.size();
		while(queue.size()!=0)
		{
			cursor=(Integer)queue.remove();
			StateTransition tempst=this.StateTrans.get(cursor);
			for(int i=0;i<inputsize;i++)
			{
				int tsize=tempst.GetTransSize(i);
				Transition tempts=tempst.GetTransition(i);
				for(int j=0;j<tsize;j++)
				{
					cursor=(int)(tempts.GetState(j).getStateID()-firstid);
					if(judge[cursor]==false)
					{
						queue.add(cursor);
						judge[cursor]=true;
					}
				}
			}
			
		}
		int k;
		for(k=0;k<statesize;k++)
		{
		     if(judge[k]==false)
		    	 break;
		}
		if(k==statesize)   //全部可以到达
		{
			return this;
		}
		else
		{
			int id=0;
			int newindex[]=new int[statesize];			
			ArrayList<String> inputs=ArrayOp.StringArrayClone(this.InputSymbols);
			ArrayList<StateTransition> trans=new ArrayList<StateTransition>();
			for(int i=0;i<statesize;i++)   //复制状态
			{
				if(judge[i])    //状态未被删除
				{
					MyState tempstate=new MyState(this.StateTrans.get(i).getBeginState().getStateName());
					StateTransition tempst=new StateTransition(tempstate,inputs.size());
					trans.add(tempst);
					newindex[i]=id;
					id++;
				}
			}
			for(int i=0;i<statesize;i++) //复制转移函数
			{
				if(judge[i])
				{
					StateTransition oldstate=this.StateTrans.get(i);
					StateTransition newstate=trans.get(newindex[i]);
					for(int j=0;j<inputsize;j++)   //第j个转移条件
					{
						Transition tempts=oldstate.GetTransition(j);
						int tsize=tempts.GetSize();
						for(int s=0;s<tsize;s++)
						{
							cursor=(int)(tempts.GetState(s).getStateID()-firstid);
							if(judge[cursor])
							{
								MyState estate=trans.get(newindex[cursor]).getBeginState();
								newstate.AddStateTransition(j, estate);
							}
						}
					}
				}
			}
			//初始状态
			cursor=(int)(this.StartState.getStateID()-firstid);
			
			MyState startstate=trans.get(newindex[cursor]).getBeginState();
			//终止状态
			ArrayList<MyState> finals=new ArrayList<MyState>();
			int fsize=this.FinalStates.size();
			for(int i=0;i<fsize;i++)
			{
				cursor=(int)(this.FinalStates.get(i).getStateID()-firstid);
				if(judge[cursor])
				{
					MyState fstate=trans.get(newindex[cursor]).getBeginState();
					finals.add(fstate);
				}
			}
			return new MyAutomaton(inputs,trans,startstate,finals);
		} 
	}
	/**
	 * 图的转置
	 * @param reachtable
	 */
	private void FillReachTable(boolean reachtable[][])
	{
		
		long firstid = this.StateTrans.get(0).getBeginState().getStateID();
		// 初始化转移表
		int statesize = this.StateTrans.size();
		boolean temp[][]=new boolean[statesize][statesize];
		for (int i = 0; i < statesize; i++) {
			StateTransition tempst = this.StateTrans.get(i);
			for (int j = 0; j < this.InputSymbols.size(); j++) {
				Transition tempts = tempst.GetTransition(j);
				int tsize = tempts.GetSize();
				for (int k = 0; k < tsize; k++) {
					MyState tempstate = tempts.GetState(k);
					int index = (int) (tempstate.getStateID() - firstid);
					temp[i][index] = true;
				}
			}
		}
		for(int i=0;i<statesize;i++)
			for(int j=0;j<statesize;j++)
			{
				if(temp[i][j])
					reachtable[j][i]=true;
			}
	}
	public MyAutomaton DeleteUnaccept()
	{
		int inputsize=this.InputSymbols.size();
		long firstid = this.StateTrans.get(0).getBeginState().getStateID();
		int statesize=this.StateTrans.size();
		boolean reachtable[][]=new boolean[statesize][statesize];  
		this.FillReachTable(reachtable);
		boolean judge[]=new boolean[statesize];
		Queue<Integer> queue=new LinkedList<Integer>();
		int fsize=this.FinalStates.size();
		int cursor;
		for(int i=0;i<fsize;i++)
		{
			cursor=(int)(this.FinalStates.get(i).getStateID()-firstid);
			judge[cursor]=true;
			queue.add(cursor);
		}
		while(queue.size()!=0)
		{
			cursor=(Integer)queue.remove();
            for(int i=0;i<statesize;i++)
            {
            	if(reachtable[cursor][i])     //i可以到达cursor
            	{
            		if(judge[i]==false)
            		{
            			judge[i]=true;
            			queue.add(i);
            		}
            	}
            }
		}
		int k;
		for(k=0;k<statesize;k++)
		{
		     if(judge[k]==false)
		    	 break;
		}
		if(k==statesize)   //全部可以到达
		{
			return this;
		}
		else
		{
			int id=0;
			int newindex[]=new int[statesize];			
			ArrayList<String> inputs=ArrayOp.StringArrayClone(this.InputSymbols);
			ArrayList<StateTransition> trans=new ArrayList<StateTransition>();
			for(int i=0;i<statesize;i++)   //复制状态
			{
				if(judge[i])    //状态未被删除
				{
					MyState tempstate=new MyState(this.StateTrans.get(i).getBeginState().getStateName());
					StateTransition tempst=new StateTransition(tempstate,inputs.size());
					trans.add(tempst);
					newindex[i]=id;
					id++;
				}
			}
			for(int i=0;i<statesize;i++) //复制转移函数
			{
				if(judge[i])
				{
					StateTransition oldstate=this.StateTrans.get(i);
					StateTransition newstate=trans.get(newindex[i]);
					for(int j=0;j<inputsize;j++)   //第j个转移条件
					{
						Transition tempts=oldstate.GetTransition(j);
						int tsize=tempts.GetSize();
						for(int s=0;s<tsize;s++)
						{
							cursor=(int)(tempts.GetState(s).getStateID()-firstid);
							if(judge[cursor])
							{
								MyState estate=trans.get(newindex[cursor]).getBeginState();
								newstate.AddStateTransition(j, estate);
							}
						}
					}
				}
			}
			//初始状态
			cursor=(int)(this.StartState.getStateID()-firstid);
			
				MyState startstate=trans.get(newindex[cursor]).getBeginState();
				
			//终止状态
			ArrayList<MyState> finals=new ArrayList<MyState>();
			
			for(int i=0;i<fsize;i++)
			{
				cursor=(int)(this.FinalStates.get(i).getStateID()-firstid);
				if(judge[cursor])
				{
					MyState fstate=trans.get(newindex[cursor]).getBeginState();
					finals.add(fstate);
				}
			}
			return new MyAutomaton(inputs,trans,startstate,finals);
		} 
	}
	public String[] GetTableNames()
	{
		String con[]=new String[this.InputSymbols.size()+2];
		int inputsize=this.InputSymbols.size();
		for(int i=0;i<inputsize;i++)
		{
			con[i+1]=this.InputSymbols.get(i);
		}
		con[0]="";
		con[inputsize+1]="ε";
		return con;
	}
	public String[][] GetTableContent()
	{
		int inputsize=this.InputSymbols.size();
		int statesize=this.StateTrans.size();
		String con[][]=new String[statesize][inputsize+2];
		for(int i=0;i<statesize;i++)
		{
			con[i]=this.StateTrans.get(i).GetRow();
		}
		String Ini="—>";
		String fin="*";
		long firstid=this.StateTrans.get(0).getBeginState().getStateID();
		int offset;
		if(this.StartState!=null)
		{
	     offset=(int)(this.StartState.getStateID()-firstid);
		con[offset][0]=Ini+con[offset][0];
		}
		int fsize=this.FinalStates.size();
		for(int i=0;i<fsize;i++)
		{
			offset=(int)(this.FinalStates.get(i).getStateID()-firstid);
			con[offset][0]=fin+con[offset][0];			
		}
		return con;
	}
	/**
	 * 打印自动机结构
	 */
	public void Display()
	{
		System.out.println("-------------------------------------------------------------------------------------------");
		System.out.print("States Set：{");
		int size=StateTrans.size();
		int i;
		if(size>=1)
			System.out.print(StateTrans.get(0).getBeginState().toString());
		for(i=1;i<size;i++)
		{
			System.out.print(","+StateTrans.get(i).getBeginState().toString());
		}
		System.out.println("}");
		System.out.print("Input Symbols:{");
		size=InputSymbols.size();
		if(size>=1)
			System.out.print(InputSymbols.get(0).toString());
		for(i=1;i<size;i++)
		{
			System.out.print(","+InputSymbols.get(i));
		}
		System.out.println("}");
		System.out.println("Start State:"+StartState.toString());
		System.out.print("Final States:{");
		size=FinalStates.size();
		if(size>=1)
			System.out.print(FinalStates.get(0).toString());
		for(i=1;i<size;i++)
		{
			System.out.print(","+FinalStates.get(i));
		}
		System.out.println("}");
	    size=StateTrans.size();
	    System.out.println("Transiton Functions:");
	    for(i=0;i<size;i++)
	    {
	    	System.out.print(StateTrans.get(i).toString(this.InputSymbols));
	    }
		System.out.println("-------------------------------------------------------------------------------------------");
		
	}

}
