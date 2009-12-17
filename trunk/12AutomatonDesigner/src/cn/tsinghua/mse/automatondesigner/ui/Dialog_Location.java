package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import automatondesigner.SystemConstant;

import com.swtdesigner.ResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class Dialog_Location extends Dialog {
	private Text tx_X;
	private Text tx_Y;
	private Point point = null;
	private Shell shell = null;
	private int result = SystemConstant.DIALOG_RESULT_CANCLE;	//返回值，取消返回1，成功修改返回2。

	public Dialog_Location(Shell parent, Point p) {
		super(parent);
		shell = new Shell(parent,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		point = p;
		shell.setSize(258, 202);
		shell.setImage(ResourceManager.getPluginImage("AutomatonDesigner",
				"icons/thumb.png"));
		shell.setText("XY坐标");
		Rectangle displayBounds = parent.getDisplay().getPrimaryMonitor().getBounds();
        Rectangle shellBounds = shell.getBounds();
        int x = displayBounds.x + (displayBounds.width-shellBounds.width)>>1;
        int y = displayBounds.y + (displayBounds.height - shellBounds.height)>>1;
        shell.setLocation(x, y);
		

		Group group = new Group(shell, SWT.NONE);
		group.setBounds(10, 10, 223, 99);
		group.setText("XY坐标值");

		Label label = new Label(group, SWT.NONE);
		label.setBounds(32, 25, 54, 12);
		label.setText("X轴坐标：");

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(32, 63, 54, 12);
		label_1.setText("Y轴坐标：");

		tx_X = new Text(group, SWT.BORDER);
		tx_X.setBounds(92, 22, 106, 18);
		tx_X.setText(String.valueOf(point.x));

		tx_Y = new Text(group, SWT.BORDER);
		tx_Y.setBounds(92, 60, 106, 18);
		tx_Y.setText(String.valueOf(point.y));

		Button button = new Button(shell, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				doCancle();
			}
		});
		button.setBounds(140, 125, 72, 22);
		button.setText("取消");

		Button button_1 = new Button(shell, SWT.NONE);
		button_1.setBounds(44, 125, 72, 22);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				doSave();
			}
		});
		button_1.setText("保存");
	}

	protected void doSave() {
		String strX = tx_X.getText();
		String strY = tx_Y.getText();
		int x = 0;
		int y = 0;
		if (strX == null || strX.equals("") || strY == null || strX.equals("")){
			MessageDialog.openError(shell, "错误", "X或Y坐标均不能为空！");
			return;
		}
		try{
			x = Integer.valueOf(strX);
			y = Integer.valueOf(strY);
		}catch(Exception e){
			MessageDialog.openError(shell, "错误", "X和Y坐标必须都为正整数！");
			return;
		}
		if(x < 0 || x > 1000 || y > 1000 || y < 0){
			MessageDialog.openError(shell, "错误", "X和Y坐标在画布范围内(0~1000)！");
			return;
		}
		point.x = x;
		point.y = y;
		result = SystemConstant.DIALOG_RESULT_SAVE;
		this.shell.close();
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
		result = SystemConstant.DIALOG_RESULT_CANCLE;;
		this.shell.close();
	}

}
