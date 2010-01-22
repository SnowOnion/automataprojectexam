package gui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;

public class CreateNewAction extends Action {
	private final IWorkbenchWindow window;
	public final static String ID = "New_File";    
	    
	//protected JDialog dlg = null;    
	
	/*public CreateNewAction() {    
        super();    
        this.setId(ID);    
    }    
	    
    /*public void run() {      
        if(dlg==null) dlg  = new MappingDialog((Shell)null);    
        if(dlg.open()!=Window.OK) {    
            return;    
        }    
        MappingTextObjectCommand c = new MappingTextObjectCommand();    
        c.path = dlg.path;    
        c.model = (TextObjectModel)((TextObjectModelPart)s.getFirstElement()).getModel();    
        this.execute(c);    
    }     */
	
	public CreateNewAction (IWorkbenchWindow window){
		this.window = window;
		this.setId(ID);
		setText("&New File");
		setToolTipText("create the new automata");
	}
	
	public void run() {
		System.out.println("go into run");
		NewEntityModelWizard wizard = new NewEntityModelWizard(window); 
		wizard.init(window.getWorkbench(), null); 
		WizardDialog dialog = new WizardDialog(window.getShell(), wizard); 
		System.out.println("go into run2");
		dialog.create();
		dialog.open();
	}
	
}
