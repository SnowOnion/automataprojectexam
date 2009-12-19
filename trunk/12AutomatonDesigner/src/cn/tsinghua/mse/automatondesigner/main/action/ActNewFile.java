package cn.tsinghua.mse.automatondesigner.main.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import cn.tsinghua.mse.automatondesigner.ui.Dialog_NewAutomaton;
import cn.tsinghua.mse.automatondesigner.ui.View_Main;

public class ActNewFile extends Action {

	IWorkbenchWindow window = null;
	public ActNewFile(IWorkbenchWindow window) {
		super();
		this.window = window;
	}
	
	public void run() {
		if(window != null) {	
			try {
				Dialog_NewAutomaton dlg = new Dialog_NewAutomaton(window.getShell(), SWT.NONE);
				dlg.open();
				View_Main tempView = (View_Main)window.getActivePage().showView(View_Main.ID, Integer.toString(View_Main.INSTANCENUM), IWorkbenchPage.VIEW_ACTIVATE);
				tempView.setMainWindow(window);
			} catch (PartInitException e) {
				MessageDialog.openError(window.getShell(), "´íÎó", "´ò¿ªÊÓÍ¼Ê§°Ü£¡");
			}
		}
	}
	
	public void dispose() {
		window = null;
	}
}
