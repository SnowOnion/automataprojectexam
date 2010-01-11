package edu.cn.thss.qmy.ndfa.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.cn.thss.qmy.ndfa.core.FA;
import edu.cn.thss.qmy.ndfa.core.FAFunctions;
import edu.cn.thss.qmy.ndfa.core.State;
import edu.cn.thss.qmy.ndfa.util.FAParser;

public class FATest {
	
	private FA fa = null;
	
	private void init() {
		String filePath = "E:\\Workspaces\\test\\Home_27_NDFAConverter\\Example.xml";
		fa = FAParser.NaiveParser(filePath);
	}

	@Test
	public void testSetIsDFA() {
		init();
		fa.setIsDFA();
		assertEquals(false, fa.getIsDFA());
		FA dfa = FAFunctions.NFA2DFA(fa);
		dfa.setIsDFA();
		assertEquals(true, dfa.getIsDFA());
	}

//	@Test
//	public void testGetEClosure() {
//		init();
////		State state = fa.getEClosure(fa.getStartState());
////		assertEquals(3, state.getSubStates().size());
//		State state = fa.getEClosure(fa.getStateKeyByName("q"));
//		assertEquals(1, state.getSubStates().size());
//	}
	
	@Test
	public void testGetEClose() {
		init();
//		State state = fa.getEClose(fa.getStartState());
//		assertEquals(3, state.getSubStates().size());
//		State state = fa.getEClose(fa.getStateKeyByName("q"));
//		assertEquals(1, state.getSubStates().size());
		assertEquals(3, fa.getEClose().size());
	}

}
