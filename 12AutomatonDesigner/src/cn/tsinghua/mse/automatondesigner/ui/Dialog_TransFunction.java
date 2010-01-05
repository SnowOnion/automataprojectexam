package cn.tsinghua.mse.automatondesigner.ui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.DLLVERSIONINFO;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import automatondesigner.SystemConstant;
import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.TransCondition;
import cn.tsinghua.mse.automatondesigner.dataobject.TransFunction;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Canvas_Automaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Circle_State;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Polyline_Trans;

import com.swtdesigner.ResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.custom.ScrolledComposite;
import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;


public class Dialog_TransFunction extends Dialog {

	private Shell shell = null;
	public Shell getShell() {
		return shell;
	}

	private Canvas_Automaton canvas = null;
	private Polyline_Trans polyline = null;
	private int result = SystemConstant.DIALOG_RESULT_CANCLE;
	
	private Combo cmb_BeginState;
	private Combo cmb_EndState;
	
	private List list_TransCondition;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	public Automaton getAutomaton(){
		return canvas.getM_mainView().getM_Automaton();
	}
	
	public TransFunction getTransfunction(){
		return polyline.getTransFunc();
	}
	
	public Dialog_TransFunction(Shell parent, Canvas_Automaton c, Polyline_Trans p) {
		super(parent);
		canvas = c;
		polyline = p;
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(313, 343);
		shell.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/PropertyWindow.ico"));
		shell.setText("转移函数");
		Rectangle displayBounds = parent.getDisplay().getPrimaryMonitor().getBounds();
        Rectangle shellBounds = shell.getBounds();
        int x = displayBounds.x + (displayBounds.width-shellBounds.width)>>1;
        int y = displayBounds.y + (displayBounds.height - shellBounds.height)>>1;
        shell.setLocation(x, y);
        
        Group group = new Group(shell, SWT.NONE);
        group.setBounds(10, 10, 287, 258);
        group.setText("转移函数属性");
        
        Label label = new Label(group, SWT.NONE);
        label.setBounds(22, 28, 65, 12);
        label.setText("起始状态：");
        
        Label label_1 = new Label(group, SWT.NONE);
        label_1.setBounds(22, 68, 54, 12);
        label_1.setText("结束状态：");
        
        cmb_BeginState = new Combo(group, SWT.READ_ONLY);
        cmb_BeginState.setBounds(93, 25, 170, 20);
        cmb_BeginState.setItems(getAllStateNames());
        cmb_BeginState.setText(polyline.getBeginCircle().getM_State().getM_Name());
        
        cmb_EndState = new Combo(group, SWT.READ_ONLY);
        cmb_EndState.setBounds(93, 65, 170, 20);
        cmb_EndState.setItems(getAllStateNames());
        cmb_EndState.setText(polyline.getEndCircle().getM_State().getM_Name());
        
        list_TransCondition = new List(group, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        list_TransCondition.setLocation(93, 110);
        list_TransCondition.setSize(170, 100);
        list_TransCondition.setFont(SWTResourceManager.getFont("宋体", 9, SWT.NORMAL));
        list_TransCondition.setItems(getAllTransCondition());
        
        Label label_2 = new Label(group, SWT.NONE);
        label_2.setBounds(22, 114, 54, 12);
        label_2.setText("转移条件：");
        
        Button button_3 = new Button(group, SWT.NONE);
        button_3.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/modify.png"));
        button_3.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		doShowTransManager();
        	}
        });
        button_3.setBounds(179, 221, 84, 22);
        button_3.setText("修改");
        
        Button button = new Button(shell, SWT.NONE);
        button.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/save16.gif"));
        button.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseUp(MouseEvent e) {
        		doSave();
        	}
        });
        button.setBounds(56, 274, 79, 22);
        button.setText("保存");
        
        Button button_1 = new Button(shell, SWT.NONE);
        button_1.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/close.png"));
        button_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseUp(MouseEvent e) {
        		doCancle();
        	}
        });
        button_1.setBounds(173, 274, 79, 22);
        button_1.setText("取消");
	}
	
	protected void doShowTransManager() {
		if (getAutomaton().getM_Type() == AutomatonConst.AUTOMATON_TYPE_PDA){
			Dialog_PDATransConManager dlg = new Dialog_PDATransConManager(shell, this, SWT.NONE);
			dlg.open();
			list_TransCondition.removeAll();
			for (TransCondition tc : getTransfunction().getM_TransCondition()){
				list_TransCondition.add(tc.toString());
			}
		}else{
			Dialog_TransConManager dlg = new Dialog_TransConManager(shell, this, SWT.NONE);
			dlg.open();
		}
	}

	private String[] getAllStateNames(){
		Automaton automaton = canvas.getM_mainView().getM_Automaton();
		String[] names = new String[automaton.getM_States().size()];
		for (int i = 0; i < automaton.getM_States().size(); i++){
			names[i] = automaton.getM_States().get(i).getM_Name();
		}
		return names;
	}
	
	public String[] getCurrentCoditions(){
		return list_TransCondition.getItems();
	}
	
	public String[] getAllTransCondition(){
		Automaton automaton = canvas.getM_mainView().getM_Automaton();
		String[] conditions = new String[getTransfunction().getM_TransCondition().size()];
		for(int i = 0; i < getTransfunction().getM_TransCondition().size(); i++){
			conditions[i] = getTransfunction().getM_TransCondition().get(i).toString();
		}
		return conditions;
	}
	
	public void setTransList(String[] conditions){
		list_TransCondition.removeAll();
		list_TransCondition.setItems(conditions);
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
		this.shell.close();
	}
	
	protected void doSave() {
		Circle_State begin = canvas.getCirclebyName(cmb_BeginState.getText());
		Circle_State end = canvas.getCirclebyName(cmb_EndState.getText());
		polyline.setBeginCircle(begin);
		polyline.setEndCircle(end);
		polyline.getTransFunc().setM_BeginState(begin.getM_State());
		polyline.getTransFunc().setM_EndState(end.getM_State());
		if (getAutomaton().getM_Type() == AutomatonConst.AUTOMATON_TYPE_PDA){
			
		}else{
			polyline.getTransFunc().getM_TransCondition().clear();
			for (String s :list_TransCondition.getItems()){
				polyline.getTransFunc().getM_TransCondition().add(new TransCondition(s));
			}
		}
		result = SystemConstant.DIALOG_RESULT_SAVE;
		this.shell.close();
	}
}
