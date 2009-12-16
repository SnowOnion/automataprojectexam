/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfatodfa;

import java.util.Vector;

/**
 *
 * @author lsq
 */
public class TestCase2 {
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
//            9
//0 @ 1
//0 + 1
//1 m 1
//1 m 4
//1 . 2
//2 m 3
//3 m 3
//4 . 3
//3 @ 5
//1
//0
//1
//5
            edge0.start = 0;
            edge0.input = "@";
            edge0.end = 1;
            edgeGather.add(edge0);

            edge1.start = 0;
            edge1.input = "+";
            edge1.end = 1;
            edgeGather.add(edge1);
            edge2.start = 1;
            edge2.input = "0..9";
            edge2.end = 1;
            edgeGather.add(edge2);
            edge3.start = 1;
            edge3.input = "0..9";
            edge3.end = 4;
            edgeGather.add(edge3);
            edge4.start = 1;
            edge4.input = ".";
            edge4.end = 2;
            edgeGather.add(edge4);
            edge5.start = 2;
            edge5.input = "0..9";
            edge5.end = 3;
            edgeGather.add(edge5);
            edge6.start = 3;
            edge6.input = "0..9";
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
            end.addElement(5);
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
	Vector<EDGE> edgeGather = new Vector<EDGE>();		//edge gather for both NFA and DFA
	//int startDFA =0;					//start state for DFA
	Vector<Integer>	 startNFA = new Vector<Integer>();			//start state gather for NFA
	Vector<Integer>	 end = new Vector<Integer>();				//end state gather for both NFA and DFA
        NFA2DFA test = new NFA2DFA();
        InputNFA(edgeGather,startNFA,end);
        test.InitNFA(edgeGather,startNFA,end);
        test.Process_NDATODFA();
    }
}
