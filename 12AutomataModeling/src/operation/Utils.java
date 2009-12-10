package operation;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Utils {

	public static void viewDom(Document document){
		try {
			TransformerFactory tfactory = TransformerFactory.newInstance();
			Transformer transformer = tfactory.newTransformer();
			// 将DOM对象转化为DOMSource类对象，该对象表现为转化成别的表达形式的信息容器。
			DOMSource source = new DOMSource(document);
			/*
			 * 获得一个StreamResult类对象，该对象是DOM文档转化成的其他形式的文档的容器，
			 * 可以是XML文件，文本文件，HTML文件。这里为一个XML文件。
			 */
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			StreamResult result = new StreamResult(out);
			// 调用API，将DOM文档转化成XML文件。
			transformer.transform(source, result);
			byte[] bytes=out.toByteArray();
			String xmlStr=new String(bytes);
			System.out.println(xmlStr);
		}catch(Exception ex){
			
		}
		
	}
	public static void StoreDOM(Document document,String filename){
		try {
			TransformerFactory tfactory = TransformerFactory.newInstance();
			Transformer transformer = tfactory.newTransformer();
			// 将DOM对象转化为DOMSource类对象，该对象表现为转化成别的表达形式的信息容器。
			DOMSource source = new DOMSource(document);
			/*
			 * 获得一个StreamResult类对象，该对象是DOM文档转化成的其他形式的文档的容器，
			 * 可以是XML文件，文本文件，HTML文件。这里为一个XML文件。
			 */
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			StreamResult result = new StreamResult(out);
			// 调用API，将DOM文档转化成XML文件。
			transformer.transform(source, result);
			byte[] bytes=out.toByteArray();
			String xmlStr=new String(bytes);
			int pos=xmlStr.indexOf('>');
			System.out.println(pos);
			
			String newXmlStr=xmlStr.substring(0, pos+1)+
				"<!DOCTYPE Automatas SYSTEM \"auto1.dtd\">"+
				xmlStr.substring(pos+1);
//			System.out.println(newXmlStr);
			BufferedWriter writer=new BufferedWriter(new FileWriter(filename));
			writer.write(newXmlStr);
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void viewElement(Element el) {
		System.out.println(el.getTextContent());	
	}

}
