package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.SWT;

import cn.tsinghua.mse.automatondesigner.graphicsobj.Canvas_Automaton;

import com.swtdesigner.ResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Menu_KneePointRightClick extends Composite {

	Menu menu;
	public Menu_KneePointRightClick(final Canvas_Automaton parent, int style, final Point p) {
		super(parent, style);
		menu = new Menu(this);
		menu.setLocation(new Point(0, 0));
		setMenu(menu);
		
		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.doDelete();
			}
		});
		menuItem.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/delete16.gif"));
		menuItem.setText("ɾ���۵�");
		
		MenuItem menuItem_1 = new MenuItem(menu, SWT.NONE);
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.showLocationShell(p, parent.getStorePolyline());
			}
		});
		menuItem_1.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/thumb.png"));
		menuItem_1.setText("����λ��");
	}

	public void showMenu(int x, int y){
		menu.setLocation(x, y);
		menu.setVisible(true);
	}
}
