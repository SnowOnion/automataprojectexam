package cn.tsinghua.mse.automatondesigner.ui;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.swtdesigner.ResourceManager;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.PDATransCondition;
import cn.tsinghua.mse.automatondesigner.dataobject.PushdownAutomaton;

import automatondesigner.SystemConstant;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Dialog_SymbolsManager extends Dialog {
	public static final byte DIALOG_INPUTYMBOLS = 0;
	public static final byte DIALOG_STACKYMBOLS = 1;
	
	private Shell shell = null;
	int result  = SystemConstant.DIALOG_RESULT_CANCLE;
	private Automaton automaton;
	private ArrayList<String> ListtoManage;
	private byte dlg_Type;
	private Text text;
	private List list;
	
	
	public Dialog_SymbolsManager(Shell parent, Automaton a, ArrayList<String> array, byte type) {
		super(parent, SWT.DIALOG_TRIM);
		ListtoManage = array;
		automaton = a;
		dlg_Type = type;
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(282, 402);
		shell.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/PropertyWindow.ico"));
		shell.setLayout(null);
		if (dlg_Type == DIALOG_INPUTYMBOLS)
			shell.setText("输入字母表管理");
		else if (dlg_Type == DIALOG_STACKYMBOLS)
			shell.setText("堆栈字母表管理");
			
		Rectangle displayBounds = parent.getDisplay().getPrimaryMonitor().getBounds();
        Rectangle shellBounds = shell.getBounds();
        int x = displayBounds.x + (displayBounds.width-shellBounds.width)>>1;
        int y = displayBounds.y + (displayBounds.height - shellBounds.height)>>1;
        shell.setLocation(x, y);
        
        Group group = new Group(shell, SWT.NONE);
        group.setText("\u5B57\u6BCD\u8868");
        group.setBounds(10, 10, 250, 251);
        group.setLayout(null);
        
        ScrolledComposite scrolledComposite = new ScrolledComposite(group, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite.setBounds(3, 20, 244, 228);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
        
        list = new List(scrolledComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        scrolledComposite.setContent(list);
        scrolledComposite.setMinSize(list.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        
        Button button = new Button(shell, SWT.NONE);
        button.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		if (list.getSelectionCount() == 0){
        			MessageDialog.openError(shell, "错误", "请选中要删除的字母！");
        			return;
        		}
        		for (int i : list.getSelectionIndices()){
        			ListtoManage.remove(list.getItem(i));
        		}
        		for (String s : list.getSelection()){
        			list.remove(s);
        		}
        		result = SystemConstant.DIALOG_RESULT_SAVE;
        	}
        });
        button.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/delete16.gif"));
        button.setBounds(33, 267, 98, 22);
        button.setText("\u5220\u9664");
        
        Button button_1 = new Button(shell, SWT.NONE);
        button_1.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		if (!MessageDialog.openConfirm(shell, "确认", "系统将删除没有在转移函数中使用过的符号，并且无法恢复，是否继续？")){
        			return;
        		}
        		if (dlg_Type == DIALOG_INPUTYMBOLS){
        			ListtoManage = automaton.clearUpInputSymbol(true);
        			loadList();
        		}
        		else if (dlg_Type == DIALOG_STACKYMBOLS) {
        			ListtoManage = ((PushdownAutomaton)automaton).clearUpStackSymbols(true);
        			loadList();
        		}
        		result = SystemConstant.DIALOG_RESULT_SAVE;
        	}
        });
        button_1.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/Tray 123_16x16-8.png"));
        button_1.setToolTipText("\u6E05\u7406\u65E0\u7528\u7B26\u53F7");
        button_1.setBounds(149, 267, 98, 22);
        button_1.setText("\u7B26\u53F7\u6574\u7406");
        
        Group group_1 = new Group(shell, SWT.NONE);
        group_1.setText("\u6DFB\u52A0\u7B26\u53F7");
        group_1.setBounds(10, 305, 250, 55);
        
        Button button_2 = new Button(group_1, SWT.NONE);
        button_2.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		if (text.getText().isEmpty()){
        			MessageDialog.openError(shell, "错误", "新加入的符号不能为空！");
        			return;
        		}
        		if (ListtoManage.contains(text.getText())){
        			MessageDialog.openError(shell, "错误", "新加入的符号已经存在！");
        			return;
        		}
        		ListtoManage.add(text.getText());
        		list.add(text.getText());
        		text.setText("");
        		result = SystemConstant.DIALOG_RESULT_SAVE;
        	}
        });
        button_2.setToolTipText("\u6DFB\u52A0\u7B26\u53F7");
        button_2.setBounds(168, 21, 72, 22);
        button_2.setText("\u6DFB\u52A0");
        
        text = new Text(group_1, SWT.BORDER);
        text.setBounds(10, 23, 140, 18);
        
        loadList();
	}
	
	private void loadList() {
		if (ListtoManage != null){
			list.removeAll();
			for (String s : ListtoManage){
				list.add(s);
			}
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
