package cn.tsinghua.mse.automatondesigner.exporttoxml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.exception.NoStateFoundException;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Graphic_MiddleAutomaton;

public class AutomatonParserFactory {

	/**
	 * 根据自动机类型获取XML格式化的工具
	 * @param aType 自动机类型
	 * @return XML文档转换工具
	 */
	public static DomBaseParser getParser(byte aType) {
		DomBaseParser parser = null;
		if (aType == AutomatonConst.AUTOMATON_TYPE_PDA) {
			parser = new PDADomParser();
		} else if (aType == AutomatonConst.AUTOMATON_TYPE_DFA
				|| aType == AutomatonConst.AUTOMATON_TYPE_NFA) {
			parser = new NFADomParser();
		}
		return parser;
	}

	/**
	 * 依据文件获取自动机的图像化显示对象
	 * @param file 文件
	 * @return 自动机的图像化显示对象
	 * @throws ParserConfigurationException XML文件格式错误异常
	 * @throws SAXException XML文件格式错误异常
	 * @throws IOException 文件读写错误异常
	 * @throws NoStateFoundException 系统定义的状态不明异常
	 */
	public static Graphic_MiddleAutomaton getParser(File file)
			throws ParserConfigurationException, SAXException, IOException,
			NoStateFoundException {
		NFADomParser test = new NFADomParser();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		DomBaseParser parser = getParser(test.getAutomatonType(doc));
		if (parser != null)
			return parser.getAutomatonFromNode(doc);
		else
			return null;
	}
}
