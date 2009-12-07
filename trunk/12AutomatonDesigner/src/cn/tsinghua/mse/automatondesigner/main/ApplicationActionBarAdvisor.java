package cn.tsinghua.mse.automatondesigner.main;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import automatondesigner.Activator;
import cn.tsinghua.mse.automatondesigner.main.action.ActNewFile;
import cn.tsinghua.mse.automatondesigner.ui.View_Property;
import cn.tsinghua.mse.automatondesigner.ui.View_ToolBox;

import org.eclipse.jface.action.Separator;
import com.swtdesigner.ResourceManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.swt.SWT;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private ActNewFile newFileACT;
	private Action exitAction;
	IWorkbenchWindow mainWindow = null;
	private IAction saveAction;
	private IAction cutAction;
	private IAction copyAction;
	private IAction aboutAction;
	private IAction helpContentsAction;
	private IAction closeAction;
	private IAction closeAllAction;
	private Action openToolBoxAction;
	private Action openPropertyAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		mainWindow = window;
		newFileACT = new ActNewFile(window);
		newFileACT.setAccelerator(SWT.CTRL | 'N');
		newFileACT.setImageDescriptor(Activator
				.getImageDescriptor("icons/new16.gif"));
		newFileACT.setText("�½�");
		newFileACT.setId("cn.tsinghua.mse.automatondesigner.newfileaction");
		register(newFileACT);

		exitAction = new Action("�˳�") {
			public void run() {
				close();
			}
		};
		exitAction.setId("cn.tsinghua.mse.automatondesigner.exitaction");
		register(exitAction);

		saveAction = ActionFactory.SAVE.create(window);
		saveAction.setText("����");
		register(saveAction);

		cutAction = ActionFactory.CUT.create(window);
		register(cutAction);

		copyAction = ActionFactory.COPY.create(window);
		register(copyAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);

		helpContentsAction = ActionFactory.HELP_CONTENTS.create(window);
		register(helpContentsAction);

		closeAction = ActionFactory.CLOSE.create(window);
		register(closeAction);

		closeAllAction = ActionFactory.CLOSE_ALL.create(window);
		register(closeAllAction);

		openToolBoxAction = new Action("������") {
			public void run() {
				openView(View_ToolBox.ID);
			}
		};
		openToolBoxAction
				.setId("cn.tsinghua.mse.automatondesigner.openToolBoxAction");
		register(openToolBoxAction);

		openPropertyAction = new Action("������") {
			public void run() {
				openView(View_Property.ID);
			}
		};
		openPropertyAction
				.setId("cn.tsinghua.mse.automatondesigner.openPropertyAction");
		register(openPropertyAction);
	}

	private void openView(String id) {
		try {
			mainWindow.getActivePage().showView(id);
		} catch (PartInitException e) {
			MessageDialog.openError(mainWindow.getShell(), "����", "����ͼʧ�ܣ�");
		}
	}

	private void close() {
		// �˴�Ҫ����Ƿ񱣴���жϺ�ѯ�ʺ���

		mainWindow.close();
		// System.exit(0);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenuanager = new MenuManager("�ļ�",
				"cn.tsinghua.mse.automatondesigner.filemenu");
		fileMenuanager.setVisible(true);
		fileMenuanager.add(newFileACT);
		fileMenuanager.add(new Separator());
		fileMenuanager.add(closeAction);
		fileMenuanager.add(closeAllAction);
		fileMenuanager.add(new Separator());
		fileMenuanager.add(saveAction);
		fileMenuanager.add(new Separator());
		fileMenuanager.add(exitAction);
		menuBar.add(fileMenuanager);

		MenuManager editMenuManager = new MenuManager("�༭",
				"cn.tsinghua.mse.automatondesigner.editmenu");
		editMenuManager.setVisible(true);
		menuBar.add(editMenuManager);
		editMenuManager.add(cutAction);
		editMenuManager.add(copyAction);

		MenuManager viewMenuManager = new MenuManager("��ͼ",
				"cn.tsinghua.mse.automatondesigner.viewmenu");
		viewMenuManager.setVisible(true);
		menuBar.add(viewMenuManager);

		MenuManager menuManager = new MenuManager("����ͼ");
		viewMenuManager.add(menuManager);
		menuManager.add(openToolBoxAction);
		menuManager.add(openPropertyAction);

		MenuManager helpMenuManager = new MenuManager("����",
				"cn.tsinghua.mse.automatondesigner.helpmenu");
		helpMenuManager.setVisible(true);
		menuBar.add(helpMenuManager);
		helpMenuManager.add(helpContentsAction);
		helpMenuManager.add(aboutAction);

	}

	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		coolBar.add(new ToolBarContributionItem(toolbar,
				"cn.tsinghua.mse.automatondesigner.toolbar"));
		toolbar.add(newFileACT);
	}
}
