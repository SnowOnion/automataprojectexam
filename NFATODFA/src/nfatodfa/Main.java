/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfatodfa;

//import java.io.FileInputStream;
import java.util.Vector;

/**
 *
 * @author lishouqin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static void InputNFA(Vector<EDGE> edgeGather,Vector<Integer> start, Vector<Integer> end)
{

            EDGE edge0 = new EDGE();
            EDGE edge1 = new EDGE();
            EDGE edge2  = new EDGE();
            EDGE edge3 = new EDGE();

            EDGE edge4 = new EDGE();
            EDGE edge5  = new EDGE();
            EDGE edge6 = new EDGE();

            EDGE edge7 = new EDGE();
            EDGE edge8  = new EDGE();
            EDGE edge9  = new EDGE();
            edgeGather.clear();
            start.clear();
            end.clear();
  
            edge0.start = 0;
            edge0.input = "@";
            edge0.end = 1;
            edgeGather.add(edge0);

            edge1.start = 0;
            edge1.input = "+";
            edge1.end = 1;
            edgeGather.add(edge1);

            edge9.start = 0;
            edge9.input = "-";
            edge9.end = 1;
            edgeGather.add(edge9);
            edge2.start = 1;
            edge2.input = "0123456789";
            edge2.end = 1;
            edgeGather.add(edge2);
            edge3.start = 1;
            edge3.input = "0123456789";
            edge3.end = 4;
            edgeGather.add(edge3);
            edge4.start = 1;
            edge4.input = ".";
            edge4.end = 2;
            edgeGather.add(edge4);
            edge5.start = 2;
            edge5.input = "0123456789";
            edge5.end = 3;
            edgeGather.add(edge5);
            edge6.start = 3;
            edge6.input = "0123456789";
            edge6.end = 3;
            edgeGather.add(edge6);
            edge7.start = 4;
            edge7.input = ".";
            edge7.end = 3;
            edgeGather.add(edge7);
            edge8.start = 3;
            edge8.input = "@";
            edge8.end = 5;
            edgeGather.add(edge8);

            start.addElement(0);
            end.addElement(5);


}
    static void printDFAresult(int start,Vector<EDGE> edgeGather,Vector<Integer> end )
    {
            System.out.println("=================Start==================");
            System.out.println();

            System.out.println("==================重新命名DFA的状态===================");
            System.out.println();
            System.out.println("初始状态：");
            System.out.println(start);
            System.out.println("结束状态：");
            int i=0;
            for(;i<end.size();i++)
                System.out.println(end.elementAt(i));
            System.out.println();
            System.out.println("DFA的状态转移表：");
            System.out.println();
//            for(i=0;i<DFA_input_symbol.size();i++)
//            {
//                System.out.println("       "+DFA_input_symbol.elementAt(i)+"                      ");
//            }
            int m_;
            for(i=0;i<edgeGather.size();i++)
            {
                m_ = i+1;
                System.out.println("第 "+m_+" 条边: ");
                System.out.println(edgeGather.elementAt(i).start+"   "+edgeGather.elementAt(i).input+"   "
                        +edgeGather.elementAt(i).end);
                System.out.println();
                System.out.println();
            }
            System.out.println();


            System.out.println("==================End===================");
            System.out.println();

    }
    public static void main(String[] args) {
        // TODO code application logic here
        //Vector<String> stringGather = new Vector<String>();	//test string gather
	Vector<EDGE> edge_All_state = new Vector<EDGE>();		//edge gather for both NFA and DFA
	//int startDFA =0;					//start state for DFA
	Vector<Integer>	 NFA_start = new Vector<Integer>();			//start state gather for NFA
	Vector<Integer>	 end_All_state = new Vector<Integer>();				//end state gather for both NFA and DFA
        NFA2DFA test = new NFA2DFA();
        //输入NFA
        InputNFA(edge_All_state,NFA_start,end_All_state);
        //调用转换程序的初始化函数，把NFA赋值
        test.InitNFA(edge_All_state,NFA_start,end_All_state);
        //执行NFA到DFA的转换
        test.Process_NDATODFA();
        //打印合并后状态集合后的新的DFA
        printDFAresult(test.getDFA_StartStatus(),test.getDFA_TransStatus(),test.getDFA_EndStatus());
        //输入测试字符串
        String str = new String("5.6");//英文课本上P72讲解的例子
        System.out.println("测试字符串为： "+str);
        if(test.TestInputString(str))
            System.out.println("Pass ok!");
        else
            System.out.println("Failed!");
        //add 2009.12.27 lishouqin
        //输入正则表达式
        Regular test_re = new Regular();
        String str_regular = new String("(0+1)*1(0+1)");//英文课本上P100讲解的例子
        test_re.InputRegular(str_regular);
        //输出结果
        test_re.process_re();
    }

}
