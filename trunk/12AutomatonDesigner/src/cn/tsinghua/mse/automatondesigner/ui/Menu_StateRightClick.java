package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.SWT;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import org.eclipse.swt.graphics.Point;
import com.swtdesigner.ResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Menu_StateRightClick extends Composite {
	Menu menu;
	public Menu_StateRightClick(final Canvas_Automaton parent, int style) {
		super(parent, style);
		
		menu = new Menu(this);
		menu.setLocation(new Point(0, 0));
		setMenu(menu);
		
		MenuItem menuItem_5 = new MenuItem(menu, SWT.NONE);
		menuItem_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.doDelete();
			}
		});
		menuItem_5.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/delete16.gif"));
		menuItem_5.setText("É¾³ý×´Ì¬");
		
		MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
		menuItem.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/state.gif"));
		menuItem.setText("×´Ì¬ÀàÐÍ");
		
		Menu menu_1 = new Menu(menuItem);
		menuItem.setMenu(menu_1);
		
		String stateType = parent.getSelectedStateType();
		MenuItem menuItem_1 = new MenuItem(menu_1, SWT.RADIO);
		if (stateType.equals(AutomatonConst.STATE_TYPE_COMMON)){
			menuItem_1.setSelection(true);
		}
		else {
			menuItem_1.setSelection(false);
		}
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.setSelectedStateType(AutomatonConst.STATE_TYPE_COMMON);				
			}
		});
		//menuItem_1.setSelection(true);
		menuItem_1.setText(AutomatonConst.STATE_TYPE_COMMON);
		
		MenuItem menuItem_2 = new MenuItem(menu_1, SWT.RADIO);
		if (stateType.equals(AutomatonConst.STATE_TYPE_INITIAL)){
			menuItem_2.setSelection(true);
		}
		else {
			menuItem_2.setSelection(false);
		}
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.setSelectedStateType(AutomatonConst.STATE_TYPE_INITIAL);
			}
		});
		menuItem_2.setText(AutomatonConst.STATE_TYPE_INITIAL);
		
		MenuItem menuItem_3 = new MenuItem(menu_1, SWT.RADIO);
		if (stateType.equals(AutomatonConst.STATE_TYPE_FINAL)){
			menuItem_3.setSelection(true);
		}
		else {
			menuItem_3.setSelection(false);
		}
		menuItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.setSelectedStateType(AutomatonConst.STATE_TYPE_FINAL);
			}
		});
		menuItem_3.setText(AutomatonConst.STATE_TYPE_FINAL);
		
		MenuItem menuItem_4 = new MenuItem(menu_1, SWT.RADIO);
		if (stateType.equals(AutomatonConst.STATE_TYPE_INI_FINAL)){
			menuItem_4.setSelection(true);
		}
		else {
			menuItem_4.setSelection(false);
		}
		menuItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.setSelectedStateType(AutomatonConst.STATE_TYPE_INI_FINAL);
			}
		});
		menuItem_4.setText(AutomatonConst.STATE_TYPE_INI_FINAL);
		
		
	}
	
	public void showMenu(int x, int y){
		menu.setLocation(x, y);
		menu.setVisible(true);
	}
}
