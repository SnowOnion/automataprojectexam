package cn.tsinghua.mse.automatondesigner.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import cn.tsinghua.mse.automatondesigner.ui.EditorInputer_Automaton;
import cn.tsinghua.mse.automatondesigner.ui.Editor_Main;

public class AutomatonNewWizard extends Wizard implements INewWizard {
	private NewAutomatonWizardPage page;

	public AutomatonNewWizard() {
		super();
		page = new NewAutomatonWizardPage("Create Automaton");
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		doFinish();
		return true;

	}

	private void doFinish() {

	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

}
