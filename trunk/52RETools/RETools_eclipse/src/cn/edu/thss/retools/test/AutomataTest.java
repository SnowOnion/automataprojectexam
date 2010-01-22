/**
 * 
 */
package cn.edu.thss.retools.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cn.edu.thss.retools.AnalysisStack;
import cn.edu.thss.retools.Automata;
import cn.edu.thss.retools.InputSequence;
import cn.edu.thss.retools.RENode;
import cn.edu.thss.retools.TransitionNotMatchException;

/**
 * @author Kylin
 *
 */
public class AutomataTest {

	public String rep;
	public AnalysisStack stack;
	public InputSequence input;
	public RENode rootNode;
	public Automata auto;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
//		rep = "10*[12]|ab+[bc]?";
//		rep = "10110";
//		rep = "[521]*";
//		rep = "\t123";
//		rep = "I(very)+happy";
//		rep = "(Good)?";
//		rep = "I(ve.?ry)+happy";
//		rep = "I[^a-zA-Z0-8]c";
//		rep = "12.3|abc";
		rep = "Ln(.1c)to";
		stack = new AnalysisStack();
//		input = new InputSequence(rep);
		input = InputSequence.CreatePatternInput(rep);
		stack.PushRoot(RENode.CreateRoot());
		
		do{
			RENode cur = stack.Top();
			cur.Transform(stack, input, 1);
		} while(!stack.IsEmpty());
		
		stack.root.Formalize();
		rootNode = stack.root;
//		System.out.println(rootNode.PrintNodeWithAttr(0));
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Automata#Automata()}.
	 */
	@Test
	public void testAutomata() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Automata#Convert2NFA()}.
	 */
//	@Test
	public void testConvert2NFA() {
		System.out.println("---------Test convert2NFA");
		auto = new Automata(rootNode, InputSequence.NO_IGNORE_CASE);
		auto.Convert2NFA();
//		auto.PrintNode();
		System.out.println("---------Finish Test convert2NFA");
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Automata#Convert2DFA()}.
	 */
	@Test
	public void testConvert2DFA() {
		System.out.println("---------Test convert2DFA");
		
		auto = new Automata(rootNode, InputSequence.NO_IGNORE_CASE);
		auto.PrintNode();
		
		System.out.println("\n\n\n");
		
		try {
			Automata dfa = auto.Convert2DFA();
			dfa.PrintNode();
		} catch (TransitionNotMatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---------Finish Test convert2DFA");
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Automata#PushNodeEdge(cn.edu.thss.retools.NodeEdge)}.
	 */
	@Test
	public void testPushNodeEdge() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Automata#AddState(cn.edu.thss.retools.AbstractState)}.
	 */
	@Test
	public void testAddState() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Automata#AppendTransiton(cn.edu.thss.retools.Transition)}.
	 */
	@Test
	public void testAppendTransiton() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Automata#AddTransition(cn.edu.thss.retools.Transition)}.
	 */
	@Test
	public void testAddTransition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Automata#PrintNode()}.
	 */
//	@Test
	public void testPrintNode() {
		System.out.println("---------Test PrintNode");
		System.out.println(rootNode.PrintNodeWithAttr());		
		auto = new Automata(rootNode, InputSequence.NO_IGNORE_CASE);
		auto.PrintNode();		
		System.out.println("---------Finish Test PrintNode");
	}

}
