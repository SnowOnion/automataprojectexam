package edu.cn.thss.qmy.ndfa.test;

import static org.junit.Assert.*;

import java.io.PrintWriter;

import org.junit.Test;

import edu.cn.thss.qmy.ndfa.core.FA;
import edu.cn.thss.qmy.ndfa.ui.ConsoleMain;
import edu.cn.thss.qmy.ndfa.util.FAParser;

public class FAPaserTest {

	@Test
	public void testNaiveParser() {
		String filePath = "E:\\Workspaces\\test\\Home_27_NDFAConverter\\Example.xml";
		FA fa = FAParser.NaiveParser(filePath);
		ConsoleMain.printFA(fa, new PrintWriter(System.out));
		assertNotNull(fa);
	}

}
