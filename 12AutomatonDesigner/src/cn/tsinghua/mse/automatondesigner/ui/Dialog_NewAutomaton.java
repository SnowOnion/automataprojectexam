package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import automatondesigner.SystemConstant;
import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;

import com.swtdesigner.ResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

public class Dialog_NewAutomaton extends Dialog {

	private Shell shell = null;
	private Text text_Name;
	private Text text_Prefix;
	private Automaton automaton = null;
	public Dialog_NewAutomaton(Shell parent, int style) {
		super(parent, style);
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(372, 277);
		shell.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/new16.gif"));
		shell.setText("新建自动机");
		Rectangle displayBounds = parent.getDisplay().getPrimaryMonitor().getBounds();
        Rectangle shellBounds = shell.getBounds();
        int x = displayBounds.x + (displayBounds.width-shellBounds.width)>>1;
        int y = displayBounds.y + (displayBounds.height - shellBounds.height)>>1;
        shell.setLocation(x, y);
        
        Button button = new Button(shell, SWT.NONE);
        button.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		doCreateAutomaton();
        	}
        });
        button.setBounds(96, 209, 72, 22);
        button.setText("确定");
        
        Button button_1 = new Button(shell, SWT.NONE);
        button_1.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		automaton = null;
        		shell.close();
        	}
        });
        button_1.setBounds(185, 209, 72, 22);
        button_1.setText("取消");
        
        TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
        tabFolder.setBounds(0, 0, 366, 197);
        
        TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
        tabItem.setText("基本属性");
        
        Composite composite_2 = new Composite(tabFolder, SWT.NONE);
        tabItem.setControl(composite_2);
        
        text_Name = new Text(composite_2, SWT.BORDER);
        text_Name.setBounds(115, 26, 216, 20);
        text_Name.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
        
        Label label = new Label(composite_2, SWT.NONE);
        label.setBounds(31, 31, 78, 12);
        label.setText("\u81EA\u52A8\u673A\u540D\u79F0:");
        
        Label label_1 = new Label(composite_2, SWT.NONE);
        label_1.setBounds(31, 77, 75, 12);
        label_1.setText("\u81EA\u52A8\u673A\u7C7B\u578B:");
        
        Combo cmb_Type = new Combo(composite_2, SWT.READ_ONLY);
        cmb_Type.setBounds(115, 74, 216, 20);
        cmb_Type.setItems(SystemConstant.AUTOMATONTYPES);
        
        text_Prefix = new Text(composite_2, SWT.BORDER);
        text_Prefix.setBounds(115, 125, 215, 18);
        text_Prefix.setText(SystemConstant.PREFIX_STATE_NAME);
        
        Label label_2 = new Label(composite_2, SWT.NONE);
        label_2.setBounds(31, 128, 84, 12);
        label_2.setText("\u72B6\u6001\u7F16\u53F7\u524D\u7F00:");
        
        TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
        tabItem_1.setText("输入字母表");
        
        Composite composite_3 = new Composite(tabFolder, SWT.NONE);
        tabItem_1.setControl(composite_3);
        
        TabItem tabItem_3 = new TabItem(tabFolder, SWT.NONE);
        tabItem_3.setText("状态");
        
        Composite composite_1 = new Composite(tabFolder, SWT.NONE);
        tabItem_3.setControl(composite_1);
        
        List list_States = new List(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        list_States.setBounds(40, 36, 181, 115);
        
        Button button_3 = new Button(composite_1, SWT.NONE);
        button_3.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/delete.png"));
        button_3.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        	}
        });
        button_3.setBounds(248, 123, 100, 22);
        button_3.setText("删除");
        
        Button button_2 = new Button(composite_1, SWT.NONE);
        button_2.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/new16.gif"));
        button_2.setBounds(248, 35, 100, 22);
        button_2.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        	}
        });
        button_2.setText("增加");
        
        Label label_4 = new Label(composite_1, SWT.NONE);
        label_4.setBounds(40, 18, 153, 12);
        label_4.setText("自动机状态列表:");
        
        Button button_5 = new Button(composite_1, SWT.NONE);
        button_5.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/modify.png"));
        button_5.setBounds(248, 80, 100, 22);
        button_5.setText("修改");
        
        TabItem tabItem_2 = new TabItem(tabFolder, SWT.NONE);
        tabItem_2.setText("转移函数");
        
        Composite composite = new Composite(tabFolder, SWT.NONE);
        tabItem_2.setControl(composite);
        
        List list_1 = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        list_1.setBounds(40, 37, 182, 114);
        
        Button button_6 = new Button(composite, SWT.NONE);
        button_6.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/new16.gif"));
        button_6.setBounds(241, 36, 96, 22);
        button_6.setText("添加");
        
        Button button_7 = new Button(composite, SWT.NONE);
        button_7.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/modify.png"));
        button_7.setBounds(241, 77, 96, 22);
        button_7.setText("修改");
        
        Button button_8 = new Button(composite, SWT.NONE);
        button_8.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/delete.png"));
        button_8.setBounds(241, 121, 96, 22);
        button_8.setText("删除");
        
        Label label_5 = new Label(composite, SWT.NONE);
        label_5.setBounds(40, 20, 106, 12);
        label_5.setText("转换函数列表:");
	}

	protected void doCreateAutomaton() {
		// TODO Auto-generated method stub
		
	}

	public Automaton open() {
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return automaton;
	}
}
