package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import automatondesigner.SystemConstant;
import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.State;

import com.swtdesigner.ResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;

public class Dialog_State extends Dialog {
	private Text tx_StateName;
	private Combo cmb_StateType;
	private State state = null;
	private Shell shell = null;
	int result  = SystemConstant.DIALOG_RESULT_CANCLE;
	private Automaton automaton;
	
	public Dialog_State(Shell parent, Automaton a, State s) {
		super(parent, SWT.DIALOG_TRIM);
		state = s;
		automaton = a;
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(324, 213);
		shell.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/PropertyWindow.ico"));
		shell.setLayout(null);
		shell.setText("状态属性");
		Rectangle displayBounds = parent.getDisplay().getPrimaryMonitor().getBounds();
        Rectangle shellBounds = shell.getBounds();
        int x = displayBounds.x + (displayBounds.width-shellBounds.width)>>1;
        int y = displayBounds.y + (displayBounds.height - shellBounds.height)>>1;
        shell.setLocation(x, y);

		
		Group group = new Group(shell, SWT.NONE);
		group.setBounds(21, 10, 269, 115);
		group.setText("状态属性");
		
		tx_StateName = new Text(group, SWT.BORDER);
		tx_StateName.setBounds(85, 29, 167, 18);
		tx_StateName.setText(state.getM_Name());
		
		cmb_StateType = new Combo(group, SWT.READ_ONLY);
		cmb_StateType.setBounds(85, 68, 167, 20);
		for (int i = 0; i < SystemConstant.STATETYPES.length; i++){
			cmb_StateType.add(SystemConstant.STATETYPES[i]);
		}
		cmb_StateType.setText(SystemConstant.STATETYPES[state.getM_type()]);
		
		
		Label label = new Label(group, SWT.NONE);
		label.setBounds(13, 32, 66, 12);
		label.setText("状态名称：");
		
		Label label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(13, 71, 66, 12);
		label_1.setText("状态类型：");
		
		Button button = new Button(shell, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				doCancle();
			}
		});
		button.setBounds(212, 147, 72, 22);
		button.setText("取消");
		
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.setBounds(118, 147, 72, 22);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				doSave();
			}
		});
		button_1.setText("保存");
		
	}
	
	protected void doSave() {
		String name = tx_StateName.getText();
		if (name.equals("")){
			MessageDialog.openError(shell, "错误", "状态名称不能为空！");
			return;
		}
		if (!state.getM_Name().equals(name) && !automaton.checkStateNameUnique(name)){
			MessageDialog.openError(shell, "错误", "该名称已经被其他状态使用，请更换状态名称！");
			return;
		}
		state.setM_Name(name);
		automaton.modifyStateType(state, getbStateType());
		result = SystemConstant.DIALOG_RESULT_SAVE;
		shell.close();
	}
	
	private byte getbStateType(){
		return (byte)cmb_StateType.getSelectionIndex();
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
	
	protected void doCancle() {
		result = SystemConstant.DIALOG_RESULT_CANCLE;
		shell.close();
	}
}
