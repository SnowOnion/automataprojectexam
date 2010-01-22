package cn.tsinghua.mse.automatondesigner.ui;

import java.awt.color.CMMException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.PDATransCondition;
import cn.tsinghua.mse.automatondesigner.dataobject.PushdownAutomaton;
import cn.tsinghua.mse.automatondesigner.dataobject.TransCondition;
import cn.tsinghua.mse.automatondesigner.dataobject.TransFunction;
import cn.tsinghua.mse.automatondesigner.wizards.NewAutomatonWizardPage;

import com.swtdesigner.ResourceManager;

import automatondesigner.SystemConstant;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Dialog_PDATransConManager extends Dialog {
	public Dialog_PDATransConManager(Shell parent,
			final Dialog_TransFunction dlg, int style) {
		super(parent, style);
		this.dlg = dlg;
		shell = new Shell(parent.getShell(), SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL);
		shell.setSize(390, 410);
		shell.setImage(ResourceManager.getPluginImage("AutomatonDesigner",
				"icons/Tray 126_16x16-32.png"));
		shell.setText("转移条件管理");
		Rectangle displayBounds = parent.getDisplay().getPrimaryMonitor()
				.getBounds();
		Rectangle shellBounds = shell.getBounds();
		int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;
		int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;
		shell.setLocation(x, y);

		pda = (PushdownAutomaton) dlg.getAutomaton();

		Group group = new Group(shell, SWT.NONE);
		group.setText("\u8F6C\u79FB\u6761\u4EF6\u5217\u8868");
		group.setBounds(10, 10, 364, 196);

		ScrolledComposite scrolledComposite = new ScrolledComposite(group,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 20, 344, 169);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		list = new List(scrolledComposite, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (list.getSelectionIndex() != -1) {
					loadProperty(list.getSelectionIndex());
					isCreating = false;
				}
			}
		});
		scrolledComposite.setContent(list);
		scrolledComposite
				.setMinSize(list.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Group group_1 = new Group(shell, SWT.NONE);
		group_1.setText("\u8BE6\u7EC6\u5C5E\u6027");
		group_1.setBounds(10, 220, 364, 125);
		group_1.setLayout(null);

		Label label = new Label(group_1, SWT.NONE);
		label.setBounds(8, 21, 60, 12);
		label.setText("\u8F93\u5165\u7B26\u53F7\uFF1A");

		combo_Input = new Combo(group_1, SWT.NONE);
		combo_Input.setBounds(73, 17, 283, 20);

		Label label_1 = new Label(group_1, SWT.NONE);
		label_1.setBounds(8, 46, 60, 12);
		label_1.setText("\u65E7\u6808\u7B26\u53F7\uFF1A");

		combo_Stack = new Combo(group_1, SWT.NONE);
		combo_Stack.setBounds(73, 42, 283, 20);

		Label label_2 = new Label(group_1, SWT.NONE);
		label_2.setBounds(8, 72, 60, 12);
		label_2.setText("\u65B0\u6808\u7B26\u53F7\uFF1A");

		Composite composite_1 = new Composite(group_1, SWT.NONE);
		composite_1.setBounds(73, 67, 283, 22);
		composite_1.setLayout(null);

		text = new Text(composite_1, SWT.BORDER);
		text.setLocation(0, 0);
		text.setSize(119, 20);
		text.setEditable(false);

		combo_Stack2 = new Combo(composite_1, SWT.NONE);
		combo_Stack2.setBounds(125, 0, 102, 20);

		Button button_2 = new Button(composite_1, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				tempNewStacksTop.clear();
			}
		});
		button_2.setToolTipText("\u6E05\u9664");
		button_2.setImage(ResourceManager.getPluginImage("AutomatonDesigner",
				"icons/clearall.png"));
		button_2.setBounds(261, 0, 22, 22);

		Button button_3 = new Button(composite_1, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (combo_Stack2.getText().equals("")) {
					MessageDialog.openWarning(shell, "警告", "请先选择或者输入一个堆栈符号！");
					return;
				}
				if (combo_Stack2.getText().indexOf(",") != -1) {
					MessageDialog.openWarning(shell, "警告", "堆栈符号不能包含逗号！");
					return;
				}
				addStackSymbtoPDA(combo_Stack2.getText());
				if (!text.getText().equals("")){
					text.setText(text.getText() + ",");
				}
				text.setText(text.getText() + combo_Stack2.getText());
				tempNewStacksTop.add(combo_Stack2.getText());
			}
		});
		button_3.setToolTipText("\u6DFB\u52A0");
		button_3.setImage(ResourceManager.getPluginImage("AutomatonDesigner",
				"icons/add_cablelogic.png"));
		button_3.setBounds(233, 0, 22, 22);

		Composite composite = new Composite(group_1, SWT.NONE);
		composite.setBounds(83, 95, 251, 25);
		composite.setLayout(null);

		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doSave();
			}
		});
		button.setLocation(92, 0);
		button.setSize(71, 25);
		button.setImage(ResourceManager.getPluginImage("AutomatonDesigner",
				"icons/modify.png"));
		button.setText("\u4FDD\u5B58");

		Button button_1 = new Button(composite, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isCreating = true;
				text.setText("");
				combo_Input.setText("");
				combo_Stack.setText("");
				combo_Stack2.setText("");
				list.setSelection(-1);
				tempNewStacksTop.clear();
			}
		});
		button_1.setImage(ResourceManager.getPluginImage("AutomatonDesigner",
				"icons/new16.gif"));
		button_1.setLocation(0, 0);
		button_1.setSize(71, 25);
		button_1.setText("\u65B0\u589E");
		
		Button button_4 = new Button(composite, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list.getSelectionIndex() == -1){
					MessageDialog.openError(shell, "错误", "请先选择一个转移条件！");
					return;
				}
				dlg.getTransfunction().getM_TransCondition().remove(list.getSelectionIndex());
				initData();
			}
		});
		button_4.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/delete16.gif"));
		button_4.setBounds(180, 0, 71, 25);
		button_4.setText("\u5220\u9664");

		initData();
	}

	protected void doSave() {
		if (combo_Input.getText().equals("")){
			MessageDialog.openError(shell, "错误", "输入符号不能为空！");
			return;
		}
		if(combo_Stack.getText().equals("")){
			MessageDialog.openError(shell, "错误", "旧栈符号不能为空！");
			return;
		}
		if(text.getText().equals("")){
			MessageDialog.openError(shell, "错误", "新栈符号不能为空！");
			return;
		}
		addInputSymbol(combo_Input.getText());
		addStackSymbtoPDA(combo_Stack.getText());
		ArrayList<String> tempArray = new ArrayList<String>();
		for (String s : tempNewStacksTop){
			tempArray.add(s);
		}
		if (isCreating) {
			PDATransCondition pdatc = new PDATransCondition(combo_Input.getText(), combo_Stack.getText(), tempArray);
			if (!dlg.getTransfunction().addTransCondition(pdatc)){
				MessageDialog.openError(shell, "错误", "此转移条件已经存在！");
				return;
			}
			isCreating = false;
		} else {
			if (list.getSelectionIndex() == -1){
				return;
			}
			TransFunction func = dlg.getTransfunction();
			PDATransCondition pdatc = (PDATransCondition) func
					.getM_TransCondition().get(list.getSelectionIndex());
			pdatc.setM_InputSymbol(combo_Input.getText());
			pdatc.setM_OldStackSymbol(combo_Stack.getText());
			pdatc.setM_NewStackSymbol(tempArray);
		}
		initData();
	}

	protected void loadProperty(int idx) {
		TransFunction func = dlg.getTransfunction();
		PDATransCondition pdatc = (PDATransCondition) func
				.getM_TransCondition().get(idx);
		combo_Input.setText(pdatc.getM_InputSymbol());
		combo_Stack.setText(pdatc.getM_OldStackSymbol());
		tempNewStacksTop.clear();
		text.setText("");
		for (String s : pdatc.getM_NewStackSymbol()) {
			if (!text.getText().equals("")){
				text.setText(text.getText() + ",");
			}
			text.setText(text.getText() + s);
			tempNewStacksTop.add(s);
		}
	}

	private void loadList(){
		list.removeAll();
		TransFunction func = dlg.getTransfunction();
		for (TransCondition tc : func.getM_TransCondition()) {
			PDATransCondition pdatc = (PDATransCondition) tc;
			list.add(pdatc.toString());
		}
	}
	
	private void addStackSymbtoPDA(String s) {
		if (!pda.getM_StackSymbols().contains(s)) {
			pda.getM_StackSymbols().add(s);
		}
		loadStackSymbols();
	}
	
	private void addInputSymbol(String s){
		if (!pda.getM_InputSymbols().contains(s)) {
			pda.getM_InputSymbols().add(s);
		}
		loadInputs();
	}

	private void loadStackSymbols() {
		Automaton atm = dlg.getAutomaton();
		String temp = combo_Stack.getText();
		combo_Stack.removeAll();
		for (String s : ((PushdownAutomaton) atm).getM_StackSymbols()) {
			combo_Stack.add(s);
		}
		combo_Stack.setText(temp);
		temp = combo_Stack2.getText();
		combo_Stack2.removeAll();
		for (String s : ((PushdownAutomaton) atm).getM_StackSymbols()) {
			combo_Stack2.add(s);
		}
		combo_Stack2.setText(temp);
	}

	private void initData() {
		loadList();
		loadInputs();
		loadStackSymbols();
		if (list.getSelectionIndex() == -1){
			isCreating = true;
			text.setText("");
			combo_Input.setText("");
			combo_Stack.setText("");
			combo_Stack2.setText("");
			tempNewStacksTop.clear();
		}
	}
	
	private void loadInputs(){
		String temp = combo_Input.getText();
		Automaton atm = dlg.getAutomaton();
		combo_Input.removeAll();
		for (String s : atm.getM_InputSymbols()) {
			combo_Input.add(s);
		}
		combo_Input.setText(temp);
	}

	private Shell shell = null;
	private Dialog_TransFunction dlg = null;
	private int result = SystemConstant.DIALOG_RESULT_CANCLE;
	private Text text;
	private Combo combo_Input;
	private Combo combo_Stack;
	private List list;
	private Combo combo_Stack2;
	private PushdownAutomaton pda = null;
	private ArrayList<String> tempNewStacksTop = new ArrayList<String>();
	private boolean isCreating = false;

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
