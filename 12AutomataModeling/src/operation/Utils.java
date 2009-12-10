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
			// ��DOM����ת��ΪDOMSource����󣬸ö������Ϊת���ɱ�ı����ʽ����Ϣ������
			DOMSource source = new DOMSource(document);
			/*
			 * ���һ��StreamResult����󣬸ö�����DOM�ĵ�ת���ɵ�������ʽ���ĵ���������
			 * ������XML�ļ����ı��ļ���HTML�ļ�������Ϊһ��XML�ļ���
			 */
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			StreamResult result = new StreamResult(out);
			// ����API����DOM�ĵ�ת����XML�ļ���
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
			// ��DOM����ת��ΪDOMSource����󣬸ö������Ϊת���ɱ�ı����ʽ����Ϣ������
			DOMSource source = new DOMSource(document);
			/*
			 * ���һ��StreamResult����󣬸ö�����DOM�ĵ�ת���ɵ�������ʽ���ĵ���������
			 * ������XML�ļ����ı��ļ���HTML�ļ�������Ϊһ��XML�ļ���
			 */
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			StreamResult result = new StreamResult(out);
			// ����API����DOM�ĵ�ת����XML�ļ���
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
