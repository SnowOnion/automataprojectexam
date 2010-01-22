package gui.actions;

import gui.help.AutomatonException;
import gui.model.ContentsModel;
import gui.ui.DiagramEditor;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

import automaton.Automaton;
import automaton.AutomatonFactory;
import automatonmodeling.ModelTransformer;

public class SaveModelAction extends Action implements ISelectionListener {
	private final IWorkbenchWindow window;
	public final static String ID = "gef.step.savediagram";
	private IStructuredSelection select;

	public SaveModelAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setText("&Save Model");
		setToolTipText("Save the automaton model");
		// setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID,"icons/online.gif"));
		window.getSelectionService().addSelectionListener(this);
	}

	public void dispose() {
		window.getSelectionService().removeSelectionListener(this);
	}

	public void run() {
		IWorkbenchPage page = window.getActivePage();
		DiagramEditor editor = (DiagramEditor) (page.getActiveEditor());
		ContentsModel contents = editor.getContents();
		try {
			Automaton automaton = ModelTransformer
					.getAutomatonFromModel(contents);
			System.out.println("Automaton: \n"+automaton);
			String filename = "";
			if (!editor.getFilepath().contains(".xml"))
				filename = editor.getFilepath()+"/"+editor.getName()+".xml";
			else
				filename = editor.getFilepath();
			AutomatonFactory.writeAutomatonToXml(automaton, new File(filename));
			MessageDialog.openInformation(window.getShell(), "Success","Model Saved successfully!");
		} catch (AutomatonException ae) {
			ae.printStackTrace();
			MessageDialog.openInformation(window.getShell(), "Error", ae
					.getExcepmsg());
		}
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {

	}
}
