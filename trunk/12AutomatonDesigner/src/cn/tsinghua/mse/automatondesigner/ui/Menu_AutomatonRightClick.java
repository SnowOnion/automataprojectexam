package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import automatondesigner.SystemConstant;
import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.PushdownAutomaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Canvas_Automaton;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.SWT;
import com.swtdesigner.ResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Menu_AutomatonRightClick extends Composite {
	Menu menu;

	public Menu_AutomatonRightClick(final Canvas_Automaton parent, int style) {
		super(parent, style);
		menu = new Menu(this);
		menu.setLocation(new Point(0, 0));
		setMenu(menu);

		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dialog_SymbolsManager dlg = new Dialog_SymbolsManager(parent
						.getShell(), parent.getM_mainView().getM_Automaton(),
						parent.getM_mainView().getM_Automaton()
								.getM_InputSymbols(),
						Dialog_SymbolsManager.DIALOG_INPUTYMBOLS);
				int result = dlg.open();
				if (result == SystemConstant.DIALOG_RESULT_SAVE){
					parent.getM_mainView().setDirty(true);
				}
			}
		});
		menuItem.setImage(ResourceManager.getPluginImage("AutomatonDesigner",
				"icons/inputs.png"));
		menuItem.setText("\u8F93\u5165\u7B26\u53F7\u7BA1\u7406");

		if (parent.getM_mainView().getM_Automaton().getM_Type() == AutomatonConst.AUTOMATON_TYPE_PDA) {
			MenuItem menuItem_1 = new MenuItem(menu, SWT.NONE);
			menuItem_1.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Dialog_SymbolsManager dlg = new Dialog_SymbolsManager(
							parent.getShell(), parent.getM_mainView()
									.getM_Automaton(), ((PushdownAutomaton)parent.getM_mainView()
									.getM_Automaton()).getM_StackSymbols(),
							Dialog_SymbolsManager.DIALOG_STACKYMBOLS);
					int result = dlg.open();
					if (result == SystemConstant.DIALOG_RESULT_SAVE){
						parent.getM_mainView().setDirty(true);
					}
				}
			});
			menuItem_1.setImage(ResourceManager.getPluginImage(
					"AutomatonDesigner", "icons/stacks.png"));
			menuItem_1.setText("\u5806\u6808\u7B26\u53F7\u7BA1\u7406");
		}
	}

	public void showMenu(int x, int y) {
		menu.setLocation(x, y);
		menu.setVisible(true);
	}
}
