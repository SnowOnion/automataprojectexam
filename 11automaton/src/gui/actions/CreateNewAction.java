package gui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPart;

public class CreateNewAction extends Action {
	final static public String ID = "New_File";    
	    
	//protected JDialog dlg = null;    
	
	public CreateNewAction(IWorkbenchPart part) {    
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
    }    */

}
