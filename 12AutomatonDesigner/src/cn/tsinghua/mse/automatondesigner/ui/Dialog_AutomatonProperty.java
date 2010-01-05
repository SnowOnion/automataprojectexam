package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import automatondesigner.SystemConstant;
import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.PushdownAutomaton;

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

public class Dialog_AutomatonProperty extends Dialog {

	private Shell shell = null;
	private Text text_Name;
	private Text text_Prefix;
	private Automaton automaton = null;
	private byte Dialog_Style = 0;
	private String AutomatonName = "";
	private String prefixStr = SystemConstant.PREFIX_STATE_NAME;

	public String getPrefixStr() {
		return prefixStr;
	}

	public String getAutomatonName() {
		return AutomatonName;
	}

	private TabItem tabItem_BaseInfo;
	private TabItem tabItem_Inputs;
	private TabItem tabItem_States;
	private TabItem tabItem_TransFuncs;
	private Combo cmb_Type;

	public Dialog_AutomatonProperty(Shell parent, int style, Automaton auto) {
		super(parent, style);
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(372, 277);
		shell.setImage(ResourceManager.getPluginImage("AutomatonDesigner",
				"icons/new16.gif"));
		if (auto == null)
			Dialog_Style = 0;
		else
			Dialog_Style = 1;
		if (Dialog_Style == 0)
			shell.setText("新建自动机");
		else if (Dialog_Style == 1){
			shell.setText("自动机属性");
		}
		Rectangle displayBounds = parent.getDisplay().getPrimaryMonitor()
				.getBounds();
		Rectangle shellBounds = shell.getBounds();
		int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;
		int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;
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

		tabItem_BaseInfo = new TabItem(tabFolder, SWT.NONE);
		tabItem_BaseInfo.setText("基本属性");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tabItem_BaseInfo.setControl(composite_2);

		text_Name = new Text(composite_2, SWT.BORDER);
		text_Name.setBounds(115, 26, 216, 20);
		text_Name.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		Label label = new Label(composite_2, SWT.NONE);
		label.setBounds(31, 31, 78, 12);
		label.setText("\u81EA\u52A8\u673A\u540D\u79F0:");

		Label label_1 = new Label(composite_2, SWT.NONE);
		label_1.setBounds(31, 77, 75, 12);
		label_1.setText("\u81EA\u52A8\u673A\u7C7B\u578B:");

		cmb_Type = new Combo(composite_2, SWT.READ_ONLY);
		cmb_Type.setBounds(115, 74, 216, 20);
		cmb_Type.setItems(SystemConstant.AUTOMATONTYPES);

		text_Prefix = new Text(composite_2, SWT.BORDER);
		text_Prefix.setBounds(115, 125, 215, 18);
		text_Prefix.setText(SystemConstant.PREFIX_STATE_NAME);

		Label label_2 = new Label(composite_2, SWT.NONE);
		label_2.setBounds(31, 128, 84, 12);
		label_2.setText("\u72B6\u6001\u7F16\u53F7\u524D\u7F00:");
		if (Dialog_Style == 1) {
			tabItem_Inputs = new TabItem(tabFolder, SWT.NONE);
			tabItem_Inputs.setText("输入字母表");

			Composite composite_3 = new Composite(tabFolder, SWT.NONE);
			tabItem_Inputs.setControl(composite_3);

			tabItem_States = new TabItem(tabFolder, SWT.NONE);
			tabItem_States.setText("状态集合");

			Composite composite_1 = new Composite(tabFolder, SWT.NONE);
			tabItem_States.setControl(composite_1);

			List list_States = new List(composite_1, SWT.BORDER | SWT.H_SCROLL
					| SWT.V_SCROLL | SWT.MULTI);
			list_States.setBounds(40, 36, 181, 115);

			Button button_3 = new Button(composite_1, SWT.NONE);
			button_3.setImage(ResourceManager.getPluginImage(
					"AutomatonDesigner", "icons/delete.png"));
			button_3.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
				}
			});
			button_3.setBounds(248, 123, 100, 22);
			button_3.setText("删除");

			Button button_2 = new Button(composite_1, SWT.NONE);
			button_2.setImage(ResourceManager.getPluginImage(
					"AutomatonDesigner", "icons/new16.gif"));
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
			button_5.setImage(ResourceManager.getPluginImage(
					"AutomatonDesigner", "icons/modify.png"));
			button_5.setBounds(248, 80, 100, 22);
			button_5.setText("修改");

			tabItem_TransFuncs = new TabItem(tabFolder, SWT.NONE);
			tabItem_TransFuncs.setText("转移函数");

			Composite composite = new Composite(tabFolder, SWT.NONE);
			tabItem_TransFuncs.setControl(composite);

			List list_1 = new List(composite, SWT.BORDER | SWT.H_SCROLL
					| SWT.V_SCROLL | SWT.MULTI);
			list_1.setBounds(40, 37, 182, 114);

			Button button_6 = new Button(composite, SWT.NONE);
			button_6.setImage(ResourceManager.getPluginImage(
					"AutomatonDesigner", "icons/new16.gif"));
			button_6.setBounds(241, 36, 96, 22);
			button_6.setText("添加");

			Button button_7 = new Button(composite, SWT.NONE);
			button_7.setImage(ResourceManager.getPluginImage(
					"AutomatonDesigner", "icons/modify.png"));
			button_7.setBounds(241, 77, 96, 22);
			button_7.setText("修改");

			Button button_8 = new Button(composite, SWT.NONE);
			button_8.setImage(ResourceManager.getPluginImage(
					"AutomatonDesigner", "icons/delete.png"));
			button_8.setBounds(241, 121, 96, 22);
			button_8.setText("删除");

			Label label_5 = new Label(composite, SWT.NONE);
			label_5.setBounds(40, 20, 106, 12);
			label_5.setText("转换函数列表:");
		}
	}

	protected void doCreateAutomaton() {
		if(Dialog_Style == 0){
			AutomatonName = text_Name.getText();
			if (AutomatonName.equals("")){
				MessageDialog.openWarning(shell, "警告", "自动机名称不能为空！");
				return;
			}
			if (cmb_Type.getSelectionIndex() == -1){
				MessageDialog.openWarning(shell, "警告", "请选择自动机类型！");
				return;
			}
			prefixStr = text_Prefix.getText();
			if (cmb_Type.getSelectionIndex() == (int)AutomatonConst.AUTOMATON_TYPE_PDA)
				automaton = new PushdownAutomaton();
			else {
				automaton = new Automaton();
				automaton.setM_Type((byte) cmb_Type.getSelectionIndex());
			}
			automaton.setM_Name(AutomatonName);
			shell.close();
		}
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
