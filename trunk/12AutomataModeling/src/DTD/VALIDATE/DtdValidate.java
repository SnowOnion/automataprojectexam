/**
 * 
 */
package DTD.VALIDATE;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.omg.CORBA.portable.InputStream;
import org.xml.sax.InputSource;

/**
 * @author beyondboy
 * 
 */
public class DtdValidate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			DocumentBuilder builder = dbf.newDocumentBuilder();
			builder.parse(new InputSource("NFA1.xml"));
			System.out.println("Succed!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
