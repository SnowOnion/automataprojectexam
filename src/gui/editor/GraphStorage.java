package gui.editor;

import java.util.*;
import com.mxgraph.view.mxGraph;

public class GraphStorage {
	private static List<mxGraph> graphs=new LinkedList<mxGraph>();
	
	public static void addGraph(mxGraph g){
		graphs.add(g);
	}
	public static mxGraph getFirstGraph(){
		if(graphs.isEmpty())
			return null;
		return graphs.remove(0);
	}
	
}
