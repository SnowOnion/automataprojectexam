package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import automatondesigner.SystemConstant;
import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.TransCondition;
import cn.tsinghua.mse.automatondesigner.dataobject.TransFunction;

import com.swtdesigner.ResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Dialog_TransConManager extends Dialog {

	private Shell shell = null;
	private Dialog_TransFunction dlg = null;
	private int result = SystemConstant.DIALOG_RESULT_CANCLE;
	private Text tx_NewSymbol;
	private List list_Inputs;
	private List list_TransCondition;
	
	public Dialog_TransConManager(Shell parent, final Dialog_TransFunction dlg, int style) {
		super(parent, style);
		this.dlg = dlg;
		shell = new Shell(parent.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(450, 398);
		shell.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/Tray 126_16x16-32.png"));
		shell.setText("转移条件管理");
		Rectangle displayBounds = parent.getDisplay().getPrimaryMonitor().getBounds();
        Rectangle shellBounds = shell.getBounds();
        int x = displayBounds.x + (displayBounds.width-shellBounds.width)>>1;
        int y = displayBounds.y + (displayBounds.height - shellBounds.height)>>1;
        shell.setLocation(x, y);
        
        Group group = new Group(shell, SWT.NONE);
        group.setBounds(10, 10, 175, 334);
        group.setText("输入符号");
        group.setLayout(null);
        
        ScrolledComposite scrolledComposite = new ScrolledComposite(group, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite.setBounds(3, 20, 169, 225);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
        
        list_Inputs = new List(scrolledComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        scrolledComposite.setContent(list_Inputs);
        scrolledComposite.setMinSize(list_Inputs.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        
        tx_NewSymbol = new Text(group, SWT.BORDER);
        tx_NewSymbol.setBounds(10, 265, 155, 22);
        
        Button button = new Button(group, SWT.NONE);
        button.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		doAddInputSymb();
        	}
        });
        button.setBounds(89, 300, 76, 22);
        button.setText("增加");
        button.setToolTipText("增加输入符号");
        
        Button button_1 = new Button(shell, SWT.NONE);
        button_1.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		if (list_Inputs.getSelectionCount() != 0){
        			doAddTransContidion();
        		}
        	}
        });
        button_1.setBounds(201, 100, 41, 29);
        button_1.setText(">>");
        button_1.setToolTipText("增加");
        
        Button button_2 = new Button(shell, SWT.NONE);
        button_2.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		if (list_TransCondition.getSelectionCount() != 0){
        			doRemoveTransContidion();
        		}
        	}
        });
        button_2.setBounds(201, 153, 41, 29);
        button_2.setText("<<");
        button_2.setToolTipText("移除");
        
        Group group_1 = new Group(shell, SWT.NONE);
        group_1.setBounds(263, 10, 171, 297);
        group_1.setText("转移条件");
        group_1.setLayout(null);
        
        list_TransCondition = new List(group_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        list_TransCondition.setBounds(3, 21, 165, 273);
        
        Button button_3 = new Button(shell, SWT.NONE);
        button_3.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		dlg.setTransList(list_TransCondition.getItems());
        		shell.close();
        	}
        });
        button_3.setBounds(273, 313, 58, 22);
        button_3.setText("确定");
        
        Button button_4 = new Button(shell, SWT.NONE);
        button_4.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		shell.close();
        	}
        });
        button_4.setBounds(362, 313, 58, 22);
        button_4.setText("取消");
        
        
        
        loadInputSymbols();
        loadTransCondition();
	}
	
	protected void doRemoveTransContidion() {
		list_TransCondition.remove(list_TransCondition.getSelectionIndices());
	}

	protected void doAddTransContidion() {
		String[] conditions = list_TransCondition.getItems();
		for(String s : list_Inputs.getSelection()){
			boolean flag = false;
			for (String c : conditions){
				if (c.equals(s)){
					flag = true;
					break;
				}
			}
			if (flag == false){
				list_TransCondition.add(s);
			}
		}
	}

	protected void doAddInputSymb() {
		String newSymbol = tx_NewSymbol.getText();
		if (newSymbol.equals("")){
			MessageDialog.openError(shell, "错误", "新加入的输入符号不能为空！");
		}
		if (dlg.getAutomaton().addInputSymbol(newSymbol)){
			loadInputSymbols();
		}else {
			MessageDialog.openError(shell, "错误", "您要加入的输入符号已经存在！");
		}
	}
	
	private void loadInputSymbols(){
		list_Inputs.removeAll();
		Automaton automaton = dlg.getAutomaton();
		for (String s : automaton.getM_InputSymbols()){
			list_Inputs.add(s);
		}
	}
	
	private void loadTransCondition(){
		list_TransCondition.removeAll();
		list_TransCondition.setItems(dlg.getCurrentCoditions());
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
