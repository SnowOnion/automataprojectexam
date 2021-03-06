package automaton;

import java.io.File;

import org.w3c.dom.Document;



import exception.NoStateFoundException;
/****************************************************************
 * This Interface defines the operation with the XML files.
 * @author Administrator
 *
 */
public interface AutomatonXmlInterface {
	/**********************************************************
	 * READ OPERATION
	 * Get an automaton from a document which is generated by an
	 * XML file.
	 */
	Automaton getAutomatonFromNode(Document doc)throws NoStateFoundException;
	/**********************************************************
	 * WRITE OPERATION
	 * Get a document used to write XML file from an automaton
	 **********************************************************/
	Document getDocumentFromAutomaton(Automaton automaton);
	void writeDocumentToFile(Document document,File file);
	
}
