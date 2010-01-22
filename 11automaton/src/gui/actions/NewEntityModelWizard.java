package gui.actions;

import gui.ui.DiagramEditor;
import gui.ui.DiagramEditorInput;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

public class NewEntityModelWizard extends Wizard implements IWizard {
	 private IWizard currentWizard = null;
	 private IStructuredSelection selection;
	 private Composite pageContainer;
	 private IWorkbench workbench;
	 private String path;
	 private String name;
	 private String type;
	 private IWorkbenchWindow window;
	 
	 public void setPath(String newPath) {
		 this.path = newPath;
	 }
	 
	 public void setName(String name) {
		 this.name = name;
	 }
	 
	 public void setType(String type){
		 this.type = type;
	 }
	 
	 public NewEntityModelWizard(IWorkbenchWindow window) {
		 setWindowTitle("new create file");
		 this.window = window;
	 }
	 
	 public void addPages() {
		 super.addPage(new NewObjectWizardPage());
		 //addPage(new AddressWizardPage());
	 }
	 
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		name = ((NewObjectWizardPage)this.getPage("#NewObectWizardPage")).text.getText();
		if(path != null){
			IEditorInput input = new DiagramEditorInput(new Path(path));
			IWorkbenchPage page = window.getActivePage();
			try{
				page.openEditor(input,DiagramEditor.ID,true);
				((DiagramEditor)page.getActiveEditor()).setContentsType(type);
				((DiagramEditor)page.getActiveEditor()).setName(name);
			}catch(PartInitException e){
				e.printStackTrace();
			}
			return true;
		}
		else return false;
	}
	
    public String getWindowTitle() {
        return "create new automata";
    }
    
    public boolean needsPreviousAndNextButtons() {
    	return false;
    }
    
    public boolean needsProgressMonitor() {
    	return false;
    }
    
    public IWizardPage getNextPage(IWizardPage page) {
    	if (page.getName().equals(NewObjectWizardPage.NEW_OBJECT_WIZARD_PAGE_NAME)) {
    		if (currentWizard == null) {
    			return null;
    		}
    		else {
    			return currentWizard.getStartingPage();
    		}
    	}else {
    		return page.getNextPage();
    	}
    }
    
    public IWizardPage getPreviousPage(IWizardPage page) {
    	if (page.getName().equals(NewObjectWizardPage.NEW_OBJECT_WIZARD_PAGE_NAME))
    		return null;
    	
    	IWizardPage previous = page.getPreviousPage();
    	if (previous == null) {
    		return getPage(NewObjectWizardPage.NEW_OBJECT_WIZARD_PAGE_NAME);
    	} else {
    		return previous;
    	}
    }
    
   public void setCurrentWizard(IWizard wizard) {
    	System.out.println("set current wizard");
    	this.currentWizard = wizard;
    	if (wizard != null) {
    		wizard.addPages();
    		wizard.createPageControls(pageContainer);
    	}
    	getContainer().updateButtons();
    }
    
    public void createPageControls(Composite pageContainer) {
    	System.out.println("create page controls");
    	super.createPageControls(pageContainer);
    	this.pageContainer = pageContainer;
    }
    
    public boolean canFinish() {
    	//return currentWizard != null && currentWizard.canFinish();
    	return true;
    }
    
    public void dispose() {
    	this.currentWizard = null;
    	this.pageContainer = null;
    	super.dispose();
    }
    
    public IWizardPage getPage(String name) {
    	if (name.equals(NewObjectWizardPage.NEW_OBJECT_WIZARD_PAGE_NAME)) {
    		return super.getPage(name);
    	} else if (this.currentWizard != null){
    		return currentWizard.getPage(name);
    	} else return null;
    }
    
    public int getPageCount() {
    	if (currentWizard != null) {
    		return currentWizard.getPageCount()+super.getPageCount();
    	}
    	return super.getPageCount();
    }
    
    public IWizardPage[] getPages() {
    	System.out.println(getPageCount());
    	if (getPageCount() == 1)
    		return super.getPages();
    	else {
    		IWizardPage[] pages = new IWizardPage[getPageCount()];
    		IWizardPage[] myPages = super.getPages();
    		System.arraycopy(myPages, 0, pages, 0, myPages.length);
    		IWizardPage[] dynamicPages = currentWizard.getPages();
    		System.arraycopy(dynamicPages, 0, pages, 1, dynamicPages.length);
    		return pages;
    	}
    }

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		this.workbench = workbench;
		//currentWizard = this;
	}
	
	public IWorkbench getWorkbench(){
		return this.workbench;
	}

}
