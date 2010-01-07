package gui.actions;

import automatonmodeling.Application;
import gui.ui.DiagramEditor;
import gui.ui.DiagramEditorInput;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class DiagramAction extends Action implements ISelectionListener {
	
	private final IWorkbenchWindow window;
	public final static String ID = "gef.step.diagram";
	private IStructuredSelection select;
	
	
	public DiagramAction(IWorkbenchWindow window){
		this.window = window;
		setId(ID);
		setText("&Diagram");
		setToolTipText("Draw the GEF diagram");
//		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID,"icons/online.gif"));
		window.getSelectionService().addSelectionListener(this);
	}
	
	public void dispose(){
		window.getSelectionService().removeSelectionListener(this);
	}
	public void run(){
		String path= openFileDialog();
		if(path != null){
			IEditorInput input = new DiagramEditorInput(new Path(path));
			IWorkbenchPage page = window.getActivePage();
			try{
				page.openEditor(input,DiagramEditor.ID,true);
			}catch(PartInitException e){
				e.printStackTrace();
			}
		}
	}
	private String openFileDialog(){
		FileDialog dialog = new FileDialog(window.getShell(),SWT.OPEN);
		dialog.setText("get нд╪Ч");
		dialog.setFilterExtensions(new String[]{"*.diagram"});
		return dialog.open();
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		
	}
	
	

}
