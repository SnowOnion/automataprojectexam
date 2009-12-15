package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.SWT;

import automatondesigner.SystemConstant;
import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import org.eclipse.swt.graphics.Point;
import com.swtdesigner.ResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Menu_StateRightClick extends Composite {
	Menu menu;
	public Menu_StateRightClick(final Canvas_Automaton parent, int style, final Circle_State state) {
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
		
		byte stateType = parent.getSelectedStateType();
		MenuItem menuItem_1 = new MenuItem(menu_1, SWT.RADIO);
		if (stateType==AutomatonConst.STATE_COMMON_TYPE){
			menuItem_1.setSelection(true);
		}
		else {
			menuItem_1.setSelection(false);
		}
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.setSelectedStateType(AutomatonConst.STATE_COMMON_TYPE);				
			}
		});
		//menuItem_1.setSelection(true);
		menuItem_1.setText(SystemConstant.STATE_TYPE_COMMON);
		
		MenuItem menuItem_2 = new MenuItem(menu_1, SWT.RADIO);
		if (stateType == AutomatonConst.STATE_INITIAL_TYPE){
			menuItem_2.setSelection(true);
		}
		else {
			menuItem_2.setSelection(false);
		}
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.setSelectedStateType(AutomatonConst.STATE_INITIAL_TYPE);
			}
		});
		menuItem_2.setText(SystemConstant.STATE_TYPE_INITIAL);
		
		MenuItem menuItem_3 = new MenuItem(menu_1, SWT.RADIO);
		if (stateType == AutomatonConst.STATE_FINAL_TYPE){
			menuItem_3.setSelection(true);
		}
		else {
			menuItem_3.setSelection(false);
		}
		menuItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.setSelectedStateType(AutomatonConst.STATE_FINAL_TYPE);
			}
		});
		menuItem_3.setText(SystemConstant.STATE_TYPE_FINAL);
		
		MenuItem menuItem_4 = new MenuItem(menu_1, SWT.RADIO);
		if (stateType == (AutomatonConst.STATE_FINAL_TYPE | AutomatonConst.STATE_INITIAL_TYPE)){
			menuItem_4.setSelection(true);
		}
		else {
			menuItem_4.setSelection(false);
		}
		menuItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.setSelectedStateType((byte) (AutomatonConst.STATE_FINAL_TYPE | AutomatonConst.STATE_INITIAL_TYPE));
			}
		});
		menuItem_4.setText(SystemConstant.STATE_TYPE_INI_FINAL);
		
		MenuItem menuItem_7 = new MenuItem(menu, SWT.NONE);
		menuItem_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.showLocationShell(state.getCentre(), state);
			}
		});
		menuItem_7.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/thumb.png"));
		menuItem_7.setText("×ø±êÎ»ÖÃ");
		
		MenuItem menuItem_6 = new MenuItem(menu, SWT.NONE);
		menuItem_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.showStateProperty(state);
			}
		});
		menuItem_6.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/file16.png"));
		menuItem_6.setText("ÊôÐÔ");
		
	}

	public void showMenu(int x, int y){
		menu.setLocation(x, y);
		menu.setVisible(true);
	}
}
