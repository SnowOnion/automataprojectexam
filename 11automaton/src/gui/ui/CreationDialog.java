package gui.ui;

import javax.swing.JTabbedPane;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

public class CreationDialog extends Dialog{
	public final static String[] types = {"NFA","DFA","PDA"};

		
	private String name_Text;
	private String type_Text;
	private Shell parent;
	
	public CreationDialog(Shell parentShell) {
		super(parentShell);
		this.parent = parentShell;
		
		JTabbedPane pane = new JTabbedPane();
		//parent.add(pane);
	}
	
	
}
