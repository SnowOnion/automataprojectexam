package cn.tsinghua.mse.automatondesigner.main;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ExternalActionManager;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import automatondesigner.Activator;
import automatondesigner.SystemConstant;
import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.PushdownAutomaton;
import cn.tsinghua.mse.automatondesigner.exporttoxml.AutomatonParserFactory;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Graphic_MiddleAutomaton;
import cn.tsinghua.mse.automatondesigner.interfaces.ICanvasContainer;
import cn.tsinghua.mse.automatondesigner.main.action.ActNewFile;
import cn.tsinghua.mse.automatondesigner.ui.Dialog_SymbolsManager;
import cn.tsinghua.mse.automatondesigner.ui.EditorInputer_Automaton;
import cn.tsinghua.mse.automatondesigner.ui.Editor_Main;
import cn.tsinghua.mse.automatondesigner.ui.Menu_AutomatonRightClick;
import cn.tsinghua.mse.automatondesigner.ui.View_Property;
import cn.tsinghua.mse.automatondesigner.ui.View_ToolBox;

import org.eclipse.jface.action.Separator;
import com.swtdesigner.ResourceManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private ActNewFile newFileACT;
	public static IWorkbenchWindow mainWindow = null;
	private IAction saveAction;
	private IAction aboutAction;
	private IAction helpContentsAction;
	private IAction closeAction;
	private IAction closeAllAction;
	private Action openToolBoxAction;
	private Action openPropertyAction;
	private Action deleteAction;
	private Action leftAlignAction;
	private Action rightAlignAction;
	private Action topAlignAction;
	private Action bottomAlignAction;
	private IAction quitAction;
	private IAction saveAsAction;
	private Action saveAsImageAction;
	private Action openAction;
	private IAction saveAllAction;
	private Action InputManageAction;
	private Action StackManageAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
		ExternalActionManager.getInstance().setCallback(null);
	}

	protected void makeActions(IWorkbenchWindow window) {
		mainWindow = window;
		newFileACT = new ActNewFile(window);
		newFileACT.setAccelerator(SWT.CTRL | 'N');
		newFileACT.setImageDescriptor(Activator
				.getImageDescriptor("icons/new16.gif"));
		newFileACT.setText("\u65B0\u5EFA...");
		newFileACT.setId("cn.tsinghua.mse.automatondesigner.newfileaction");
		register(newFileACT);

		saveAction = ActionFactory.SAVE.create(window);
		saveAction.setToolTipText(" \u4FDD\u5B58");
		saveAction.setText("����");
		register(saveAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		aboutAction.setImageDescriptor(ResourceManager
				.getPluginImageDescriptor("AutomatonDesigner",
						"icons/file16.png"));
		aboutAction.setText("����...");
		register(aboutAction);

		helpContentsAction = ActionFactory.HELP_CONTENTS.create(window);
		helpContentsAction.setText("����");
		register(helpContentsAction);

		closeAction = ActionFactory.CLOSE.create(window);
		closeAction.setToolTipText("\u5173\u95ED");
		closeAction.setText("\u5173\u95ED");
		register(closeAction);

		closeAllAction = ActionFactory.CLOSE_ALL.create(window);
		closeAllAction.setToolTipText("\u5168\u90E8\u5173\u95ED");
		closeAllAction.setText("\u5168\u90E8\u5173\u95ED");
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

		deleteAction = new Action("ɾ��") {
			public void run() {
				doDelete();
			}
		};
		deleteAction.setId("cn.tsinghua.mse.automatondesigner.deleteAction");
		deleteAction.setImageDescriptor(Activator
				.getImageDescriptor("icons/delete16.gif"));
		register(deleteAction);

		leftAlignAction = new Action("�������") {
			public void run() {
				doAlign(SystemConstant.ALIGN_LEFT);
			}
		};
		leftAlignAction
				.setId("cn.tsinghua.mse.automatondesigner.leftAlignAction");
		leftAlignAction.setImageDescriptor(ResourceManager
				.getPluginImageDescriptor("AutomatonDesigner",
						"icons/left_align.png"));
		register(leftAlignAction);

		rightAlignAction = new Action("���Ҷ���") {
			public void run() {
				doAlign(SystemConstant.ALIGN_RIGHT);
			}
		};
		rightAlignAction
				.setId("cn.tsinghua.mse.automatondesigner.rightAlignAction");
		rightAlignAction.setImageDescriptor(ResourceManager
				.getPluginImageDescriptor("AutomatonDesigner",
						"icons/right_align.png"));
		register(rightAlignAction);
		topAlignAction = new Action("���϶���") {
			public void run() {
				doAlign(SystemConstant.ALIGN_TOP);
			}
		};
		topAlignAction
				.setId("cn.tsinghua.mse.automatondesigner.topAlignAction");
		topAlignAction.setImageDescriptor(ResourceManager
				.getPluginImageDescriptor("AutomatonDesigner",
						"icons/top_align.png"));
		register(topAlignAction);

		bottomAlignAction = new Action("���¶���") {
			public void run() {
				doAlign(SystemConstant.ALIGN_BOTTOM);
			}
		};
		bottomAlignAction
				.setId("cn.tsinghua.mse.automatondesigner.bottomAlignAction");
		bottomAlignAction.setImageDescriptor(ResourceManager
				.getPluginImageDescriptor("AutomatonDesigner",
						"icons/bottom_align.png"));
		register(bottomAlignAction);

		quitAction = ActionFactory.QUIT.create(window);
		quitAction.setToolTipText("\u9000\u51FA\u7CFB\u7EDF");
		quitAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				"AutomatonDesigner", "icons/exit.png"));
		quitAction.setText("�˳�");
		register(quitAction);

		saveAsAction = ActionFactory.SAVE_AS.create(window);
		saveAsAction.setToolTipText("\u53E6\u5B58\u4E3A");
		saveAsAction.setText("\u53E6\u5B58\u4E3A...");
		saveAsAction.setId("cn.tsinghua.mse.automatondesigner.saveAsAction");
		register(saveAsAction);

		saveAsImageAction = new Action("\u5BFC\u51FA\u5230\u56FE\u7247...") {
			public void run() {
				doSaveAsImage();
			}
		};
		saveAsImageAction
				.setId("cn.tsinghua.mse.automatondesigner.saveAsImageAction");
		saveAsImageAction.setImageDescriptor(ResourceManager
				.getPluginImageDescriptor("AutomatonDesigner",
						"icons/saveworkspace.png"));
		saveAsImageAction.setToolTipText("\u5BFC\u51FA\u5230\u56FE\u7247");
		register(saveAsImageAction);

		openAction = new Action("\u6253\u5F00...") {
			public void run() {
				doOpen();
			}
		};
		openAction.setId("cn.tsinghua.mse.automatondesigner.openAction");
		openAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				"AutomatonDesigner", "icons/open16.gif"));
		register(openAction);

		saveAllAction = ActionFactory.SAVE_ALL.create(window);
		saveAllAction.setId("cn.tsinghua.mse.automatondesigner.saveAllAction");
		saveAllAction.setToolTipText("\u5168\u90E8\u4FDD\u5B58");
		saveAllAction.setText("\u5168\u90E8\u4FDD\u5B58");
		register(saveAllAction);
		InputManageAction = new Action("\u8F93\u5165\u7B26\u53F7\u7BA1\u7406") {
			public void run() {
				IWorkbenchPart currentView = mainWindow.getActivePage()
						.getActivePart();
				if (currentView != null
						&& currentView instanceof ICanvasContainer) {
					ICanvasContainer canvasContext = (ICanvasContainer) currentView;
					Dialog_SymbolsManager dlg = new Dialog_SymbolsManager(
							Display.getCurrent().getActiveShell(),
							canvasContext.getM_Automaton(), canvasContext
									.getM_Automaton().getM_InputSymbols(),
							Dialog_SymbolsManager.DIALOG_INPUTYMBOLS);
					dlg.open();
				} else {
					MessageDialog.openError(mainWindow.getShell(), "����",
							"��ѡ��ĳ���Զ���ģ���ٽ��б�������");
					return;
				}
			}
		};
		InputManageAction
				.setId("cn.tsinghua.mse.automatondesigner.InputManageAction");
		InputManageAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor("AutomatonDesigner", "icons/inputs.png"));
		register(InputManageAction);

		StackManageAction = new Action("\u5806\u6808\u7B26\u53F7\u7BA1\u7406") {
			public void run() {
				IWorkbenchPart currentView = mainWindow.getActivePage()
						.getActivePart();
				if (currentView != null
						&& currentView instanceof ICanvasContainer) {
					ICanvasContainer canvasContext = (ICanvasContainer) currentView;
					if (canvasContext.getM_Automaton().getM_Type() == AutomatonConst.AUTOMATON_TYPE_PDA){
						Dialog_SymbolsManager dlg = new Dialog_SymbolsManager(
								Display.getCurrent().getActiveShell(),
								canvasContext.getM_Automaton(), ((PushdownAutomaton)canvasContext
										.getM_Automaton()).getM_StackSymbols(),
								Dialog_SymbolsManager.DIALOG_STACKYMBOLS);
						dlg.open();
					}
					else{
						MessageDialog.openError(mainWindow.getShell(), "����",
						"ֻ�������Զ����ſ��Խ��ж�ջ���Ź���");
				return;
					}
				} else {
					MessageDialog.openError(mainWindow.getShell(), "����",
							"��ѡ��ĳ���Զ���ģ���ٽ��б�������");
					return;
				}
			}
		};
		StackManageAction
				.setId("cn.tsinghua.mse.automatondesigner.StackManageAction");
		StackManageAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor("AutomatonDesigner", "icons/stacks.png"));
		register(StackManageAction);

	}

	protected void doOpen() {
		FileDialog dlg = new FileDialog(Display.getCurrent().getActiveShell(),
				SWT.OPEN);
		dlg.setFilterNames(new String[] { "XML File" });
		dlg.setFilterExtensions(new String[] { "*.xml" });
		String filePathAndName = dlg.open();
		if (filePathAndName == null) {
			return;
		}
		File file = new File(filePathAndName);
		if (!file.exists()) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(),
					"����", "�ļ������ڣ�");
			return;
		}
		try {
			Graphic_MiddleAutomaton gAutomaton = AutomatonParserFactory
					.getParser(file);
			Editor_Main tempView = (Editor_Main) mainWindow.getActivePage()
					.openEditor(
							new EditorInputer_Automaton(gAutomaton
									.getAutomaton().getM_Name()),
							Editor_Main.ID, true);
			tempView.setMainWindow(mainWindow);
			tempView.setM_Automaton(gAutomaton.getAutomaton());
			tempView.setCanvasProperties(gAutomaton.getcStates(), gAutomaton
					.getpTrans());
			tempView.setFilePathAndName(filePathAndName);
			tempView.setDirty(false);
		} catch (Exception e) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(),
					"����", "�ļ���ʽ������ļ������ڵ��¶�ȡʧ�ܣ�");
			return;
		}
	}

	protected void doSaveAsImage() {
		IWorkbenchPart currentView = mainWindow.getActivePage().getActivePart();
		if (currentView != null && currentView instanceof ICanvasContainer) {
			((ICanvasContainer) currentView).saveAsImage();
		} else {
			MessageDialog.openError(mainWindow.getShell(), "����",
					"��ѡ�пɹ��������Զ���ģ���ٽ��б�������");
		}
	}

	protected void doDelete() {
		IWorkbenchPart currentView = mainWindow.getActivePage().getActivePart();
		if (currentView != null && currentView instanceof ICanvasContainer) {
			if (!((ICanvasContainer) currentView).doDelete()) {
				MessageDialog.openError(mainWindow.getShell(), "ɾ��ʧ�ܣ�",
						"û�пɹ�ɾ���������ѡ�У�");
			}
		} else {
			MessageDialog.openWarning(mainWindow.getShell(), "ɾ��ʧ�ܣ�",
					"����ѡ��ĳ���Զ����ı༭��ͼ��ִ�д˲�����");
		}
	}

	private void openView(String id) {
		try {
			mainWindow.getActivePage().showView(id);
		} catch (PartInitException e) {
			MessageDialog.openError(mainWindow.getShell(), "����", "����ͼʧ�ܣ�");
		}
	}

	private void doAlign(byte direction) {
		IWorkbenchPart currentView = mainWindow.getActivePage().getActivePart();
		if (currentView != null && currentView instanceof ICanvasContainer) {
			if (!((ICanvasContainer) currentView).doAlign(direction)) {
				MessageDialog.openError(mainWindow.getShell(), "����ʧ�ܣ�",
						"����Ҫ������״̬��ѡ�в���ִ�д˲�����");
			}
		} else {
			MessageDialog.openWarning(mainWindow.getShell(), "����ʧ�ܣ�",
					"����ѡ��ĳ���Զ����ı༭��ͼ��ִ�д˲�����");
		}
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenuanager = new MenuManager("�ļ�",
				"cn.tsinghua.mse.automatondesigner.filemenu");
		fileMenuanager.setVisible(true);
		fileMenuanager.add(newFileACT);
		fileMenuanager.add(openAction);
		fileMenuanager.add(new Separator());
		fileMenuanager.add(saveAction);
		fileMenuanager.add(saveAllAction);
		fileMenuanager.add(saveAsAction);
		fileMenuanager.add(new Separator());
		fileMenuanager.add(saveAsImageAction);
		fileMenuanager.add(new Separator());
		fileMenuanager.add(closeAction);
		fileMenuanager.add(closeAllAction);
		fileMenuanager.add(new Separator());
		fileMenuanager.add(quitAction);
		menuBar.add(fileMenuanager);

		MenuManager editMenuManager = new MenuManager("�༭",
				"cn.tsinghua.mse.automatondesigner.editmenu");
		editMenuManager.setVisible(true);
		menuBar.add(editMenuManager);
		editMenuManager.add(deleteAction);
		editMenuManager.add(new Separator());
		editMenuManager.add(leftAlignAction);
		editMenuManager.add(rightAlignAction);
		editMenuManager.add(topAlignAction);
		editMenuManager.add(bottomAlignAction);

		MenuManager viewMenuManager = new MenuManager("����",
				"cn.tsinghua.mse.automatondesigner.viewmenu");
		viewMenuManager.setVisible(true);
		menuBar.add(viewMenuManager);
		viewMenuManager.add(InputManageAction);
		viewMenuManager.add(StackManageAction);
		viewMenuManager.add(new Separator());

		MenuManager menuManager = new MenuManager("����ͼ");
		viewMenuManager.add(menuManager);
		menuManager.add(openToolBoxAction);

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
		toolbar.add(openAction);
		toolbar.add(new Separator());
		toolbar.add(saveAction);
		toolbar.add(saveAllAction);
		toolbar.add(new Separator());
		toolbar.add(deleteAction);
		toolbar.add(new Separator());
		toolbar.add(leftAlignAction);
		toolbar.add(rightAlignAction);
		toolbar.add(topAlignAction);
		toolbar.add(bottomAlignAction);
	}
}
