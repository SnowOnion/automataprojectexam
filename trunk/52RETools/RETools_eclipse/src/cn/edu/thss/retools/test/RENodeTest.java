package cn.edu.thss.retools.test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import cn.edu.thss.retools.AnalysisStack;
import cn.edu.thss.retools.InputSequence;
import cn.edu.thss.retools.REFormatException;
import cn.edu.thss.retools.RENode;

public class RENodeTest {

	@Test
	public void testMatch() {
		Set<Character> inputs = new HashSet<Character>();
		char a, c;
		a = 'g';
		inputs.add(Character.valueOf(a));
		c = 'g';
		
		Assert.assertTrue(inputs.contains(Character.valueOf(c)));		
	}

	@Test
	public void testTransform() throws REFormatException, IOException {
		//fail("Not yet implemented");
//		String rep = "I(ve.?ry)+happy";
//		String rep = "[521]*";
//		String rep = "(Good)?";
//		String rep = "(12|bc|TP|Mm)*";
//		String rep = "I(very)?happy";
		String rep = "Ln(1c)to";
//		String rep = "10*[12]|ab+[bc]";
//		String rep = "I[^a-zA-Z0-8]c";
//		String rep = "10*[^0-9a-z]|ab+[a-z]?";
//		String rep = "A[521]b";
//		String rep = "(Good)+";//A[^521]+b";
//		String rep = "he\\.lo.m ";
//		String rep = "10*[^0-9a-z]";
		AnalysisStack stack = new AnalysisStack();
		InputSequence input = InputSequence.CreatePatternInput(rep);//new InputSequence(rep);
//		stack.PushRoot(new ENode());
		stack.PushRoot(RENode.CreateRoot());
		
		do{
			RENode cur = stack.Top();
			cur.Transform(stack, input, 0);
		} while(!stack.IsEmpty());
		
	//	stack.root.Transform(stack, input);
//		System.out.println(stack.root.PrintNode());
		
//		stack.root.PrintNode();
		
		System.out.println(stack.root.PrintNodeWithAttr(0));
//		stack.root.CutDuplication();
		stack.root.Formalize();
		System.out.println("After format");
		System.out.println(stack.root.PrintNodeWithAttr(0));
		
	//	Automata auto = new Automata(stack.root);
		
	//	System.out.println(auto.PrintNode());
	}

}
