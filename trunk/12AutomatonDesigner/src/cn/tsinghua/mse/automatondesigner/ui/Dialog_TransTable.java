package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import automatondesigner.SystemConstant;

import com.swtdesigner.ResourceManager;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.tools.CommonTool;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

public class Dialog_TransTable extends Dialog {
	private Shell shell = null;
	private int result = SystemConstant.DIALOG_RESULT_CANCLE;
	private Table table;
	
	public Dialog_TransTable(Shell parent, Automaton automaton) {
		super(parent);
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(400, 300);
		shell.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/PropertyWindow.ico"));
		shell.setText("状态转移表");
		Rectangle displayBounds = parent.getDisplay().getPrimaryMonitor().getBounds();
        Rectangle shellBounds = shell.getBounds();
        int x = displayBounds.x + (displayBounds.width-shellBounds.width)>>1;
        int y = displayBounds.y + (displayBounds.height - shellBounds.height)>>1;
        shell.setLocation(x, y);
        
        Group group = new Group(shell, SWT.NONE);
        group.setText("\u72B6\u6001\u8F6C\u79FB\u8868");
        group.setBounds(10, 10, 374, 214);
        group.setLayout(null);
        
        ScrolledComposite scrolledComposite = new ScrolledComposite(group, SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite.setBounds(3, 12, 368, 199);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
        
        table = new Table(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        scrolledComposite.setContent(table);
        scrolledComposite.setMinSize(table.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        
        Button button = new Button(shell, SWT.NONE);
        button.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		shell.close();
        	}
        });
        button.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/close.png"));
        button.setBounds(298, 236, 86, 24);
        button.setText("\u5173\u95ED");
        
        fillTable(automaton);
	}
	
	
	private void fillTable(Automaton automaton) {
		String[][] strTable = CommonTool.getTrancTable(automaton);
		if (strTable == null){
			shell.setVisible(false);
			MessageDialog.openInformation(shell, "信息", "状态转移表为空！");
			shell.close();
			return;
		}
		TableColumn[] column = new TableColumn[strTable[0].length];
		for (int i = 0; i < strTable[0].length; i++){
			column[i] = new TableColumn(table, SWT.NONE);
			//column[i].setWidth(80);
			column[i].setText(strTable[0][i]);
		}
		for (int i = 1; i < strTable.length; i++){
			TableItem item = new TableItem(table, SWT.NONE);
			int c = 0;
			for (int j = 0; j < strTable[0].length; j++){
				item.setText(c++, strTable[i][j]);
			}
		}
		for (int i = 0, n = column.length; i < n; i++) {
		      column[i].pack();
	    }
	}


	public int open() {
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}
}
