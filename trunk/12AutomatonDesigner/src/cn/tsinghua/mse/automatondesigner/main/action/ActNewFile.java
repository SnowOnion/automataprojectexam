package cn.tsinghua.mse.automatondesigner.main.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.ui.EditorInputer_Automaton;
import cn.tsinghua.mse.automatondesigner.ui.Dialog_AutomatonProperty;
import cn.tsinghua.mse.automatondesigner.ui.Editor_Main;
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
				Dialog_AutomatonProperty dlg = new Dialog_AutomatonProperty(window.getShell(), SWT.NONE, null);
				Automaton auto = dlg.open();
				if (auto == null){
					return;
				}
				Editor_Main tempView = (Editor_Main)window.getActivePage().openEditor(new EditorInputer_Automaton(dlg.getAutomatonName()), Editor_Main.ID, true);
				tempView.setMainWindow(window);
				tempView.setM_Automaton(auto);
				tempView.setAutomatonPrefix(dlg.getPrefixStr());
			} catch (Exception e) {
				MessageDialog.openError(window.getShell(), "´íÎó", "´ò¿ªÊÓÍ¼Ê§°Ü£¡");
			}
		}
	}
	
	public void dispose() {
		window = null;
	}
}
