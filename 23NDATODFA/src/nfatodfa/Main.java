/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfatodfa;

//import java.io.FileInputStream;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static void InputNFA(Vector<EDGE> edgeGather,Vector<Integer> start, Vector<Integer> end)
{
//        try
//        {
            //FileInputStream inNFA = new FileInputStream("NFA.txt");
            EDGE edge0 = new EDGE();
            EDGE edge1 = new EDGE();
            EDGE edge2  = new EDGE();
            EDGE edge3 = new EDGE();

            EDGE edge4 = new EDGE();
            EDGE edge5  = new EDGE();
            EDGE edge6 = new EDGE();

            EDGE edge7 = new EDGE();
            EDGE edge8  = new EDGE();
            EDGE edge9 = new EDGE();
            EDGE edge10  = new EDGE();
            EDGE edge11 = new EDGE();

            EDGE edge12 = new EDGE();


            int edgeNum =0;
            int startNum= 0;
            int endNum = 0;
            //int temp = 0;
            edgeGather.clear();
            start.clear();
            end.clear();
            byte[] cha = new byte[1];
            //inNFA.read(cha);
            //edgeNum = (int)cha;
//	13
//	0 @ 1
//	0 @ 7
//	1 @ 2
//	1 @ 4
//	2 a 3
//	4 b 5
//	3 @ 6
//	5 @ 6
//	6 @ 7
//	6 @ 1
//	7 a 8
//	8 b 9
//	9 b 10
//	1
//	0
//	1
//	10
            edge0.start = 0;
            edge0.input = "@";
            edge0.end = 1;
            edgeGather.add(edge0);

            edge1.start = 0;
            edge1.input = "@";
            edge1.end = 7;
            edgeGather.add(edge1);
            edge2.start = 1;
            edge2.input = "@";
            edge2.end = 2;
            edgeGather.add(edge2);
            edge3.start = 1;
            edge3.input = "@";
            edge3.end = 4;
            edgeGather.add(edge3);
            edge4.start = 2;
            edge4.input = "a";
            edge4.end = 3;
            edgeGather.add(edge4);
            edge5.start = 4;
            edge5.input = "b";
            edge5.end = 5;
            edgeGather.add(edge5);
            edge6.start = 3;
            edge6.input = "@";
            edge6.end = 6;
            edgeGather.add(edge6);
            edge7.start = 5;
            edge7.input = "@";
            edge7.end = 6;
            edgeGather.add(edge7);
            edge8.start = 6;
            edge8.input = "@";
            edge8.end = 7;
            edgeGather.add(edge8);

            edge9.start = 6;
            edge9.input = "@";
            edge9.end = 1;
            edgeGather.add(edge9);
            edge10.start = 7;
            edge10.input = "a";
            edge10.end = 8;
            edgeGather.add(edge10);
            edge11.start = 8;
            edge11.input = "b";
            edge11.end = 9;
            edgeGather.add(edge11);
            edge12.start = 9;
            edge12.input = "b";
            edge12.end = 10;
            edgeGather.add(edge12);
//            for(int i=0;i<edgeNum;i++)
//            {
//                    inNFA>>edinNFA>ge.start>>edge.input>>edge.end;
//                    edgeGather.push_back(edge);
//            }
            startNum =1;
            start.addElement(0);
//            for(i=0;i<startNum;i++)
//            {
//                    inNFA>>temp;
//                    start.push_back(temp);
//            }
            endNum =1;
            end.addElement(10);
//            for(i=0;i<endNum;i++)
//            {
//                    inNFA>>temp;
//                    end.push_back(temp);
//            }
        //}
//        catch(Exception e)
//        {
//            System.out.println(e);
//        }

}
    public static void main(String[] args) {
        // TODO code application logic here
        //Vector<String> stringGather = new Vector<String>();	//test string gather
	Vector<EDGE> edge_All_state = new Vector<EDGE>();		//edge gather for both NFA and DFA
	//int startDFA =0;					//start state for DFA
	Vector<Integer>	 NFA_start = new Vector<Integer>();			//start state gather for NFA
	Vector<Integer>	 end_All_state = new Vector<Integer>();				//end state gather for both NFA and DFA
        NFA2DFA test = new NFA2DFA();
        InputNFA(edge_All_state,NFA_start,end_All_state);
        test.InitNFA(edge_All_state,NFA_start,end_All_state);
        test.Process_NDATODFA();
        String str = new String("abba");
        System.out.println("测试字符串为： "+str);
        if(test.TestInputString(str))
            System.out.println("Pass ok!");
        else
            System.out.println("Failed!");
    }

}
