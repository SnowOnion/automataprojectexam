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
	 * 从XML文件获取图形化的自动机对象
	 * @param doc  XML文档对象
	 * @return 图形化的自动机对象
	 * @throws NoStateFoundException 定义的状态无法从状态列表中获取
	 */
	
	public abstract Graphic_MiddleAutomaton getAutomatonFromNode(Document doc)throws NoStateFoundException;
	/**
	 * 从自动机到XML文档
	 * @param automaton 图形化的自动机对象
	 * @return XML文档对象
	 */
	public abstract Document getDocumentFromAutomaton(Graphic_MiddleAutomaton automaton);
	
	/**
	 * 将文档写入文件
	 * @param document XML文档对象
	 * @param file 要写入的文件
	 */
	public abstract void writeDocumentToFile(Document document,File file);
	
}
