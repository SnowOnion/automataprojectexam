/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfatodfa;

import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author lishouqin
 */
public class Regular {
//添加NFA状态
int state =1;
//初始化相关变量
String str_regular = new String();//
Vector<EDGE> NFA_EDGE = new Vector<EDGE>();
Vector <String> REInput = new Vector <String>();
//NFA对应的变量
Vector <String> NFAInput = new Vector<String>();	//NFA的输入符
Vector<Integer>	startNFA = new Vector<Integer>();	//NFA的起始状态集
Vector<Integer>	endNFA =new Vector<Integer>();		//NFA的终态集
public void InputRegular(String regular)
{
    str_regular = regular;
}
Regular()
{

}
public void GetNFA_State(Vector<Integer> stNFA,Vector<EDGE> NFA_edge,Vector<Integer> acceptNFA)
{
    stNFA = startNFA;
    NFA_edge = NFA_EDGE;
    acceptNFA = endNFA;
}
//执行正则表达式到NFA转换
void process_re()
{
    ProcessREToNFA();
    NFAInput = REInput;
    PrintOutputResult();
}
//添加NFA的转移状态
void Add_NFA_edge(char input,NFA n,Vector<EDGE> edge)
{
    EDGE e = new EDGE();
    if(n.start==0 && n.end==0)
    {
        e.start=n.start=state;
        e.input = String.valueOf(input);
        e.end=n.end=++state;
        state++;
    }
    else
    {
        e.start=n.start;
        e.input = String.valueOf(input);
        e.end=n.end;
    }
    edge.add(e);
}
//进行或连接
void  Add_NFA_OR(NFA result,NFA left,NFA right,Vector<EDGE> edge)
{
    NFA temp = new NFA();
    result.start=temp.start=state;
    temp.end=left.start;
    Add_NFA_edge('@',temp,edge);

    temp.start=state;
    temp.end=right.start;
    Add_NFA_edge('@',temp,edge);

    temp.start=left.end;
    temp.end=++state;
    Add_NFA_edge('@',temp,edge);

    temp.start=right.end;
    result.end=temp.end=state;
    Add_NFA_edge('@',temp,edge);

    state++;
}
//进行与连接
void Add_NFA_AND(NFA result,NFA left,NFA right,Vector<EDGE> edge)
{
    EDGE edge_add = new EDGE();
	result.start=left.start;
	result.end=right.end;

	for(int i=0;i<edge.size();i++)
	{
		if(edge.elementAt(i).start==right.start)
		{
			//edge.elementAt(i).start=left.end;
                    edge_add.start = left.end;
                    edge_add.input = String.valueOf('@');
                    edge_add.end = right.start;
                    edge.add(edge_add);
                    break;
		}
	}
}
//进行连接
void  NFA_Connect(NFA result,NFA op,Vector<EDGE> edge)
{
	NFA temp = new NFA();
	result.start=temp.start=state;
	temp.end=op.start;
	Add_NFA_edge('@',temp,edge);

	temp.start=op.end;
	result.end=temp.end=++state;
	Add_NFA_edge('@',temp,edge);

	temp.start=op.end;
	temp.end=op.start;
	Add_NFA_edge('@',temp,edge);

	temp.start=result.start;
	temp.end=result.end;
	Add_NFA_edge('@',temp,edge);

	state++;
}
//检查当前符号
int test_symbol(char re)
{
	if(re=='*'||re=='+')
		return 0;
	else if(re!='('&&re!=')')
		return 1;
	else
		return -1;
}
//查找字符
int _findVector(Vector<String> input,String find)
{
    int result =-1;
    Vector<String> _input = new Vector<String>();
    String find_ = new String();
    _input = input;
    find_ = find;
    result = _input.indexOf(find_);
    if (result>=0)
    {
        return result;
    }
    return result;
}
//执行正则表达式到NFA转换
void  ProcessREToNFA()
{
    //操作符堆栈，保存处理过程中遇到的操作符
    Stack<String> st = new Stack<String>();
    //NFA堆栈，保存处理过程中产生的NFA
    Stack<NFA> sNFA = new Stack<NFA>();
    //定义一些临时变量
    NFA nfa = new NFA();
    NFA temp = new NFA();
    NFA result=new NFA();				

    /*************************************************/
    for(int i=0;i<str_regular.length();i++)
    {
        //进行符号判断，然后进行相应处理
        if(str_regular.charAt(i)=='(')
        {
                st.push(new String(String.valueOf(str_regular.charAt(i))));
        }
        else if(test_symbol(str_regular.charAt(i))==1)
        {
                /************获取正则表达式输入字符************/
                if (_findVector(REInput,String.valueOf(str_regular.charAt(i)))<0)
                        REInput.add(new String(String.valueOf(str_regular.charAt(i))));
                /******************End*******************/

                nfa.start=0;
                nfa.end=0;
                Add_NFA_edge(str_regular.charAt(i),nfa,NFA_EDGE);
                NFA nfa1 = new NFA();
                nfa1.end= nfa.end;
                nfa1.start = nfa.start;
                sNFA.push(nfa1);
                if(sNFA.size()>=2)
                {
                        if(st.size()>0&&st.lastElement().equals(String.valueOf('+')))
                        {
                                st.pop();

                                temp=sNFA.lastElement();
                                sNFA.pop();
                                nfa=sNFA.lastElement();
                                sNFA.pop();

                                Add_NFA_OR(result,nfa,temp,NFA_EDGE);
                                NFA nfa2 = new NFA();
                                nfa2.start = result.start;
                                nfa2.end = result.end;
                                sNFA.push(nfa2);
                        }
                        else if(i>0&&str_regular.charAt(i-1)!='(')
                        {
                                temp=sNFA.lastElement();
                                sNFA.pop();
                                nfa=sNFA.lastElement();
                                sNFA.pop();

                                Add_NFA_AND(result,nfa,temp,NFA_EDGE);
                                NFA nfa3 = new NFA();
                                nfa3.start = result.start;
                                nfa3.end = result.end;
                                sNFA.push(nfa3);
                        }
                }
            }
            else if(test_symbol(str_regular.charAt(i))==0)
            {
                    if(str_regular.charAt(i)=='*')
                    {
                            nfa=sNFA.lastElement();
                            sNFA.pop();
                            NFA_Connect(result,nfa,NFA_EDGE);
                            NFA nfa4 = new NFA();
                            nfa4.start = result.start;
                            nfa4.end = result.end;
                            sNFA.push(nfa4);
                    }
                    else if(str_regular.charAt(i)=='+')
                    {
                            st.push(new String(String.valueOf(str_regular.charAt(i))));
                    }
            }
            else if(str_regular.charAt(i)==')')
            {
                    st.pop();
                    if(sNFA.size()>=2)
                    {
                            temp=sNFA.lastElement();
                            sNFA.pop();
                            nfa=sNFA.lastElement();
                            sNFA.pop();

                            Add_NFA_AND(result,nfa,temp,NFA_EDGE);
                            NFA nfa5 = new NFA();
                            nfa5.start = result.start;
                            nfa5.end = result.end;
                            sNFA.push(nfa5);
                    }
            }
    }
    /****************************************************************/

    /****************把转换完成的NFA的起始和终止状态写入************/
    if(sNFA.size()>0)
    {
        startNFA.add(sNFA.lastElement().start);
        endNFA.add(sNFA.lastElement().end);
    }
    /****************************************************/

}
//输出处理结果
void PrintOutputResult()
{
        int i;
        System.out.println("==================输出NFA===================");
        System.out.println();
        System.out.println("初始状态：");
        for(i=0;i<startNFA.size();i++)
            System.out.println(startNFA.elementAt(i));
        System.out.println("结束状态：");
        for(i=0;i<endNFA.size();i++)
            System.out.println(endNFA.elementAt(i));

//
        System.out.println();
        System.out.println("NFA的状态转移表：");
        System.out.println();

        int m_;
        for(i=0;i<NFA_EDGE.size();i++)
        {
            m_ = i+1;
            System.out.println("第 "+m_+" 条边: ");
            System.out.println(NFA_EDGE.elementAt(i).start+"   "+NFA_EDGE.elementAt(i).input+"   "
                    +NFA_EDGE.elementAt(i).end);
            System.out.println();
            System.out.println();
        }
        System.out.println();


        System.out.println("==================完毕===================");
        System.out.println();
    }
}
