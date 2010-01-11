package edu.cn.thss.qmy.ndfa.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.cn.thss.qmy.ndfa.core.FA;
import edu.cn.thss.qmy.ndfa.core.FAFunctions;
import edu.cn.thss.qmy.ndfa.ui.ConsoleMain;
import edu.cn.thss.qmy.ndfa.util.FAParser;

public class FAFunctionTest {
	
	private FA fa = null;
	
	private void init() {
		String filePath = "E:\\Workspaces\\test\\Home_27_NDFAConverter\\Example.xml";
		fa = FAParser.NaiveParser(filePath);
	}
	
	public FAFunctionTest() {
		init();
	}

	@Test
	public void testIsLanguageNull() {
		assertEquals(false, FAFunctions.isLanguageNull(fa));
	}

	@Test
	public void testIsLanguageInfinite() {
		assertEquals(true, FAFunctions.isLanguageInfinite(fa));
	}

	@Test
	public void testNFA2DFA() {
		FA dfa = FAFunctions.NFA2DFA(fa);
//		ConsoleMain.printFA(dfa);
	}
	
	@Test
	public void testEnsureDFA() {
		FA dfa = FAFunctions.EnsureDFA(fa);
//		ConsoleMain.printFA(dfa);
		FA dfa2 = FAFunctions.EnsureDFA(dfa);
//		ConsoleMain.printFA(dfa2);
	}
	
	@Test
	public void testCanAccepted() {
		String test = "babb";
		String str = FAFunctions.canAccepted(fa, test.toCharArray());
//		assertEquals("This String CANNOT be Accepted by this FA.", str);
		System.out.println(str);
	}

}
