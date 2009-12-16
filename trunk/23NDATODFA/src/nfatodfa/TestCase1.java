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
public class TestCase1 {
static void InputNFA(Vector<EDGE> edgeGather,Vector<Integer> start, Vector<Integer> end)
{
//        try
//        {
            //FileInputStream inNFA = new FileInputStream("NFA.txt");
            EDGE edge0 = new EDGE();
            EDGE edge1 = new EDGE();
            EDGE edge2  = new EDGE();
            EDGE edge3 = new EDGE();
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
            edge0.start = 0;
            edge0.input = "a";
            edge0.end = 0;
            edgeGather.add(edge0);

            edge1.start = 0;
            edge1.input = "b";
            edge1.end = 0;
            edgeGather.add(edge1);
            edge2.start = 0;
            edge2.input = "a";
            edge2.end = 1;
            edgeGather.add(edge2);
            edge3.start = 1;
            edge3.input = "b";
            edge3.end = 2;
            edgeGather.add(edge3);
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
            end.addElement(2);
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
