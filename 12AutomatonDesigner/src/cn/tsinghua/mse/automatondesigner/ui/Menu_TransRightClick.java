package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.SWT;

import cn.tsinghua.mse.automatondesigner.dataobject.TransFunction;

import com.swtdesigner.ResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Menu_TransRightClick extends Composite {
	Menu menu;
	private Point currPoint = null;
	public Menu_TransRightClick(final Canvas_Automaton parent, int style, Point p, final Polyline_Trans polyline) {

		super(parent, style);
		menu = new Menu(this);
		menu.setLocation(new Point(0, 0));
		setMenu(menu);
		
		currPoint = p;
		
		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.doDelete();
			}
		});
		menuItem.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/delete16.gif"));
		menuItem.setText("删除转移函数");
		
		MenuItem menuItem_2 = new MenuItem(menu, SWT.NONE);
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.addKneePointForPoyline(currPoint.x, currPoint.y);
			}
		});
		menuItem_2.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/insert.png"));
		menuItem_2.setText("增加折点");
		
		MenuItem menuItem_1 = new MenuItem(menu, SWT.NONE);
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.showTransFuncProperty(polyline);
			}
		});
		menuItem_1.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/file16.png"));
		menuItem_1.setText("属性");
		// TODO Auto-generated constructor stub
	}
	protected void doShowProperty() {
		// TODO Auto-generated method stub
		
	}

	public void showMenu(int x, int y){
		menu.setLocation(x, y);
		menu.setVisible(true);
	}
}
