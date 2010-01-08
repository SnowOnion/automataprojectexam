/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfatodfa;
import java.util.Collections;
import java.util.Stack;
import java.util.Vector;
/**
 *
 * @author lishouqin
 */

public class NFA2DFA {

//
    	//定义NFA所用到的变量
        //----------------------------------------
    	//NFA的所有可能的输入符号集合
 	Vector<String> NFA_input_symbol = new Vector<String>();
        //NFA的初始状态集合
	Vector<Integer>	NFA_start_state_arr = new Vector<Integer>();
        //NFA的接受状态集合
	Vector<Integer>	 NFA_end_state_arr = new Vector<Integer>();
        //NFA的所有转移的集合
	Vector<EDGE> NFA_edge_state_arr= new Vector<EDGE>();		


    	//定义DFA所用到的变量
        //----------------------------------------
        //DFA的所有可能的输入符号集合
	Vector<String> DFA_input_symbol= new Vector<String>();
        //DFA的初始状态
	int DFA_start_state = 0;
        //DFA的接受状态集合
	Vector<Integer> DFA_accept_state_arr= new Vector<Integer>();
        //DFA的其他的中间状态集合
	Vector<Integer> DFA_trans_state_arr= new Vector<Integer>();
        //从NFA转换后的DFA的转移的集合
	Vector<EDGE> DFA_edge_state_arr = new Vector<EDGE>();
        //DFA中各个状态对应于NFA中的状态集合
	Vector<Vector<Integer> > DFAStateAll= new Vector<Vector<Integer>>();
        //构造函数
        NFA2DFA()
        {

        }
        //获取转换后的DFA的起始状态
        public int getDFA_StartStatus()
        {
            return DFA_start_state;
        }
        //获取转换后的DFA的接受状态集合
        public Vector<Integer>  getDFA_EndStatus()
        {
            return DFA_accept_state_arr;
        }
        //获取转换后的DFA的状态转移集合
        public 	Vector<EDGE> getDFA_TransStatus()
        {
            return DFA_edge_state_arr;
        }
	//实现从NFA到DFA的转换并打印转换后的结果
        public	void Process_NDATODFA()
        {
            DFA_input_symbol=new Vector(NFA_input_symbol);
            ConvertNFAtoDFA();
            PrintOutputResult();
        }

//////////////////////////////////////////////////////////////////////////
//	Functions.

//	Description： 测试输入的字符串，并判断能否被此DFA接受
//	Parameters: str [in] 输入需要测试的字符串
//	Return value: false not accept
//                    true accept.
	boolean TestInputString(String str)
        {
            int currentState,nextState;
            nextState=DFA_start_state;

            int i=0;
            for(;i<str.length();i++)
            {
                currentState=nextState;
                int j=0;
                for(;j<DFA_edge_state_arr.size();j++)
                    if(DFA_edge_state_arr.elementAt(j).start==currentState&&
                            DFA_edge_state_arr.elementAt(j).input.indexOf(str.charAt(i))>-1)//.charAt(0)==
                    {
                        nextState=DFA_edge_state_arr.elementAt(j).end;
                        break;
                    }
                    if(j==DFA_edge_state_arr.size())
                        break;
            }
            if(i==str.length()&&_find(DFA_accept_state_arr,nextState))
                return true;
            else
                return false;
        }
        //////////////////////////////////////////////////////////////////////////
        //	Functions.
        //	Description： 查找某个状态是否存在
        //	Parameters: enddfa [in] 状态集合
        //                  state_ [in] 状态
        //	Return value: false not found
        //                    true found.
        boolean _find(Vector<Integer> enddfa,int state_)
        {
            if((enddfa.size()>=0)&&enddfa.contains(state_))
                return true;

            return false;
        }

        //////////////////////////////////////////////////////////////////////////
        //	Functions.
        //	Description： 初始化NFA
        //	Parameters: edge [in] 边集合
        //                  start [in] 起始状态
        //                  end  [in] 结束状态
        //	Return value: none
        public int InitNFA(Vector<EDGE> edge,Vector<Integer> start,Vector<Integer> end)
        {
            Vector<EDGE> _edge = new Vector<EDGE>();
            Vector<Integer> _start = new Vector<Integer>();
            Vector<Integer> _end = new Vector<Integer>();
            _edge = edge;
            _start = start;
            _end = end;
            //对输入与输出清空
            clear();

            NFA_edge_state_arr=_edge;
            NFA_start_state_arr=_start;
            NFA_end_state_arr=_end;
            //判断输入是否为空
            if (_edge.size()<0 && start.size()<0 && end.size()<0)
            {
                System.out.println("输入的NFA为空！");
                return -1;
            }
            for(int i=0;i<_edge.size();i++)
            {
                if(NFA_input_symbol.indexOf(_edge.elementAt(i).input)<0)
                {
                    NFA_input_symbol.addElement(_edge.elementAt(i).input);
                }
            }
            return 0;
        }

        //////////////////////////////////////////////////////////////////////////
        //	Functions.
        //	Description： 	初始化所有容器变量
        //	Parameters: none
        //	Return value: none

	public void clear()
        {
            NFA_edge_state_arr.clear();
            NFA_input_symbol.clear();
            NFA_start_state_arr.clear();
            NFA_end_state_arr.clear();

            DFA_input_symbol.clear();
            DFA_accept_state_arr.clear();
            DFA_trans_state_arr.clear();
            DFA_edge_state_arr.clear();
            DFAStateAll.clear();
        }
        //////////////////////////////////////////////////////////////////////////
        //	Functions.
        //	Description： 输出最后的结果
        //	Parameters: none
        //	Return value: none

	void PrintOutputResult()
        {
            System.out.println("=================Start==================");
            System.out.println();

            System.out.println("==================转换后的DFA===================");
            System.out.println();
            System.out.println("初始状态：");
            System.out.println(DFAStateAll.elementAt(DFA_start_state));
            System.out.println("结束状态：");
            int i=0;
            for(;i<DFA_accept_state_arr.size();i++)
                System.out.println(DFAStateAll.elementAt(DFA_accept_state_arr.elementAt(i))+" ");
            System.out.println();
            System.out.println("DFA的状态转移表：");
            System.out.println();
//            for(i=0;i<DFA_input_symbol.size();i++)
//            {
//                System.out.println("       "+DFA_input_symbol.elementAt(i)+"                      ");
//            }
            int m_;
            for(i=0;i<DFA_edge_state_arr.size();i++)
            {
                m_ = i+1;
                System.out.println("第 "+m_+" 条边: ");
                System.out.println(DFAStateAll.elementAt(DFA_edge_state_arr.elementAt(i).start)+"   "+DFA_edge_state_arr.elementAt(i).input+"   "
                        +DFAStateAll.elementAt(DFA_edge_state_arr.elementAt(i).end));
                System.out.println();
                System.out.println();
            }
            System.out.println();


            System.out.println("==================End===================");
            System.out.println();
        }

        //////////////////////////////////////////////////////////////////////////
        //	Functions.
        //	Description： 查找某个状态在集合中的位置
        //	Parameters: data [in] 状态集合
        //                  toFind [in] 状态集合
        //	Return value: the position
        int FindVector(Vector<Vector<Integer> > data, Vector<Vector<Integer> > toFind)
        {
            int result = -1;
            Vector<Vector<Integer> > _toFind = new Vector<Vector<Integer> >();
            //Collections.sort(_toFind);
            Vector<Vector<Integer>> _input = new Vector<Vector<Integer>>();
            Vector<Integer> find_ = new Vector<Integer>();
            _input = data;
            _toFind = toFind;
            find_ = _toFind.firstElement();
            result = _input.indexOf(find_);
            if (result>=0)
            {
                return result;
            }
            return result;
        }
        //////////////////////////////////////////////////////////////////////////
        //	Functions.
        //	Description： 查找某个状态在集合中的位置
        //	Parameters: data [in] 状态集合
        //                  toFind [in] 状态
        //	Return value: the position
        int _findVector(Vector<Vector<Integer>> input,Vector<Integer> find)
        {
            int result =-1;
            Vector<Vector<Integer>> _input = new Vector<Vector<Integer>>();
            Vector<Integer> find_ = new Vector<Integer>();
            _input = input;
            find_ = find;
            result = _input.indexOf(find_);
            if (result>=0)
            {
                return result;
            }
            return result;
        }

        //////////////////////////////////////////////////////////////////////////
        //	Functions.
        //	Description： 实现了NFA到DFA的转换
        //	Parameters: none
        //	Return value: none
	void ConvertNFAtoDFA()
        {
            //临时变量，保存某一状态集
            Vector<Integer> DFAState = new Vector<Integer>();
            //标记已处理过的状态集
            int	processed=-1; 
            //由NFA的初始状态经过epsilon转移后得到初始状态集
            Find_epsilon_closure(NFA_start_state_arr, DFAState,NFA_edge_state_arr);

            DFAStateAll.add(DFAState);
            //得到初始状态
            DFA_start_state=DFAStateAll.size()-1;	

            /*=========================12.12 modified=========================*/
            int op= 0;
            for(;op<NFA_end_state_arr.size();op++)
            {

                    if(DFAState.indexOf(NFA_end_state_arr.get(op)) >= 0)
                            break;
            }

            if(op<NFA_end_state_arr.size())
                    DFA_accept_state_arr.add(DFAStateAll.size()-1);
            else
                    DFA_trans_state_arr.add(DFAStateAll.size()-1);
            /*=========================12.12 modified=========================*/

            /***************************************/
            Vector<Vector<Integer> > tempState = new Vector<Vector<Integer> >();
            Vector<Integer> DFAState1 = new Vector<Integer>();
            DFAState1 = DFAState;
            tempState.add(DFAState1);

            while(tempState.size()>0)
            {
                    //临时变量
                    EDGE edge_st = new EDGE();
                    processed++;
                    edge_st.start=FindVector(DFAStateAll, tempState);
                    for(int i=0;i<NFA_input_symbol.size();i++)
                    {
                        //临时变量
                        EDGE edge = new EDGE();
                        edge.start = edge_st.start;

                        //在输入为@时不去运算
                        if (NFA_input_symbol.elementAt(i).charAt(0)=='@')
                        {
                                continue;
                        }
                        Vector<Integer> DFAState2 = new Vector<Integer>();
                        Move(tempState.firstElement(),NFA_input_symbol.elementAt(i),DFAState2,NFA_edge_state_arr);
                        if(DFAState2.size()<1)
                                continue;
                        //填充转换后的DFA的边
                        edge.input=NFA_input_symbol.elementAt(i);
                        int result =0;
                        result=_findVector(DFAStateAll,DFAState2);

                        //如果所产生的状态集不在DFAStateAll中，则保存该状态集
                        if(result<0)
                        {
                            Vector<Integer> DFAState3 = new Vector<Integer>();
                            DFAState3 = DFAState2;
                                DFAStateAll.add(DFAState3);

                                /*=========================12.10=========================*/
                                int op1=0;
                                for(;op1<NFA_end_state_arr.size();op1++)
                                {
                                        if(_find(DFAState3,NFA_end_state_arr.elementAt(op1)))
                                                break;
                                }

                                if(op1<NFA_end_state_arr.size())
                                        DFA_accept_state_arr.add(DFAStateAll.size()-1);
                                else
                                        DFA_trans_state_arr.add(DFAStateAll.size()-1);
                                /*=========================12.10=========================*/

                                edge.end=DFAStateAll.size()-1;
                        }
                        else
                                edge.end=result;
                        //保存DFA的边
                        int IsFind = -1;
                        for (int edge_i=0;edge_i<DFA_edge_state_arr.size();edge_i++)
                        {

                            if((DFA_edge_state_arr.elementAt(edge_i).start==edge.start)&&(DFA_edge_state_arr.elementAt(edge_i).input.equals(edge.input))&&(DFA_edge_state_arr.elementAt(edge_i).end==edge.end))
                            {
                                    IsFind = 1;
                                    break;
                            }

                        }
                        if (IsFind == -1)
                        {
                            EDGE edge_add = new EDGE();
                            edge_add = edge;
                            DFA_edge_state_arr.add(edge_add);
                        }

                        Vector<Integer> DFAState4 = new Vector<Integer>();
                        DFAState4 = DFAState2;
                        if(DFAState4!=tempState.firstElement()&&edge.end>processed)
                                tempState.add(DFAState4);
                    }
                    tempState.remove(tempState.firstElement());
            }
            /*************************判断DFA是否为空*******************************/
            if (DFA_accept_state_arr.size()<0 &&  DFA_edge_state_arr.size()<0)
            {
                System.out.println("输入的DFA为空！");
                return ;
            }

            /*************************End*******************************/

        }
        //////////////////////////////////////////////////////////////////////////
        //	Functions.

        //	Description： 找到input集合的epsilon闭包，结果保存在集合output中
        //	Parameters: input [in] 输入集合
        //                  output[out]输出集合
        //                  edge [in] 边集合
        //	Return value: none

	public void Find_epsilon_closure(Vector<Integer> input, Vector<Integer> output,Vector<EDGE> edge)
        {
            Stack<Integer> _state = new Stack<Integer>();
            Vector<Integer> _input = new Vector<Integer>();
            Vector<EDGE> _edge = new Vector<EDGE>();
            _input = input;
            _edge = edge;
            output.clear();
            Collections.sort(_input);
            
            for(int i=0;i<_input.size();i++)
                _state.push(_input.elementAt(i));

            int temp=0;
            while(_state.size()>0)
            {
                output.add(_state.lastElement());
                temp=_state.lastElement();
                _state.pop();
                for(int j=0;j<NFA_edge_state_arr.size();j++)
                    if(_edge.elementAt(j).start==temp&&edge.elementAt(j).input.charAt(0)=='@')
                        _state.push(_edge.elementAt(j).end);
            }
            Collections.sort(output);
        }
        //////////////////////////////////////////////////////////////////////////
        //	Functions.
        //	Description： 计算集合input在输入为in时的所能达到的状态集合result
        //	Parameters: input [in] 输入集合
        //                  result[out]输出集合
        //                  edge [in] 边集合
        //                  in [in] 输入字符
        //	Return value: none
	public void Move(Vector<Integer> input,String in,Vector<Integer> result,Vector<EDGE> edge)
        {
            Vector<Integer> temp = new Vector<Integer>();
            Vector<Integer> _input = new Vector<Integer>();
            Vector<EDGE> _edge = new Vector<EDGE>();
            _input = input;
            _edge = edge;
            temp.clear();
    
            for(int i=0;i<_input.size();i++)
            {
                for(int j=0;j<_edge.size();j++)
                {
                    if(_edge.elementAt(j).start==_input.elementAt(i)&&_edge.elementAt(j).input.equals(in))
                    {
                        //如果所产生的状态集不在temp中，则保存该状态集
                        if(!_find(temp,_edge.elementAt(j).end))
                        {
                            temp.add(_edge.elementAt(j).end);
                        }
                    }
                }
            }

            Find_epsilon_closure(temp,result,_edge);
        }
	/***************************************The End***********************************/

}
