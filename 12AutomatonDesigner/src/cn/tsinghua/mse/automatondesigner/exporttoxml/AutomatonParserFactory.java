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
	 * �����Զ������ͻ�ȡXML��ʽ���Ĺ���
	 * @param aType �Զ�������
	 * @return XML�ĵ�ת������
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
	 * �����ļ���ȡ�Զ�����ͼ����ʾ����
	 * @param file �ļ�
	 * @return �Զ�����ͼ����ʾ����
	 * @throws ParserConfigurationException XML�ļ���ʽ�����쳣
	 * @throws SAXException XML�ļ���ʽ�����쳣
	 * @throws IOException �ļ���д�����쳣
	 * @throws NoStateFoundException ϵͳ�����״̬�����쳣
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
