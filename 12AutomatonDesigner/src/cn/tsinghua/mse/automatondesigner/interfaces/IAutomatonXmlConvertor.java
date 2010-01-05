package cn.tsinghua.mse.automatondesigner.interfaces;

import java.io.File;

import org.w3c.dom.Document;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.exception.NoStateFoundException;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Graphic_MiddleAutomaton;


/****************************************************************
 * This Interface defines the operation with the XML files.
 * @author Administrator
 *
 */
public interface IAutomatonXmlConvertor {
	/**
	 * ��XML�ļ���ȡͼ�λ����Զ�������
	 * @param doc  XML�ĵ�����
	 * @return ͼ�λ����Զ�������
	 * @throws NoStateFoundException �����״̬�޷���״̬�б��л�ȡ
	 */
	
	public abstract Graphic_MiddleAutomaton getAutomatonFromNode(Document doc)throws NoStateFoundException;
	/**
	 * ���Զ�����XML�ĵ�
	 * @param automaton ͼ�λ����Զ�������
	 * @return XML�ĵ�����
	 */
	public abstract Document getDocumentFromAutomaton(Graphic_MiddleAutomaton automaton);
	
	/**
	 * ���ĵ�д���ļ�
	 * @param document XML�ĵ�����
	 * @param file Ҫд����ļ�
	 */
	public abstract void writeDocumentToFile(Document document,File file);
	
}
