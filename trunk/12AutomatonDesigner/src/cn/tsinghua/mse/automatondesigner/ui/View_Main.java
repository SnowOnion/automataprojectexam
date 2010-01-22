package cn.tsinghua.mse.automatondesigner.ui;

import java.io.File;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.internal.decorators.FullTextDecoratorRunnable;
import org.eclipse.ui.part.ViewPart;

import automatondesigner.SystemConstant;
import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Canvas_Automaton;
import cn.tsinghua.mse.automatondesigner.interfaces.ICanvasContainer;
import cn.tsinghua.mse.automatondesigner.tools.CommonTool;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * 图形化显示自动机的主界面
 * 
 * @author David
 * 
 */
public class View_Main extends ViewPart implements ICanvasContainer {

	public static final String ID = "cn.tsinghua.mse.automatondesigner.ui.View_Main"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	public static int INSTANCENUM = 0;

	private Automaton m_Automaton = null;
	private boolean isDirty = false;
	private Canvas_Automaton canvas;
	private IWorkbenchWindow mainWindow = null;
	private String AutomatonPrefix = SystemConstant.PREFIX_STATE_NAME;

	public String getAutomatonPrefix() {
		return AutomatonPrefix;
	}

	public void setAutomatonPrefix(String automatonPrefix) {
		AutomatonPrefix = automatonPrefix;
		if (canvas != null){
			canvas.setPrefix_StateName(AutomatonPrefix);
		}
	}

	public IWorkbenchWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(IWorkbenchWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	/**
	 * 新建文件时使用的构造函数
	 */
	public View_Main() {
		super();
		INSTANCENUM++;
		isDirty = false;
		m_Automaton = new Automaton();
	}

	/**
	 * 打开已有文件时使用的构造函数
	 * 
	 * @param automaton 已经构造好的自动机
	 */
	public View_Main(Automaton automaton) {
		isDirty = false;
		m_Automaton = automaton;
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		ScrolledForm scrolledForm = toolkit.createScrolledForm(container);
		toolkit.paintBordersFor(scrolledForm);

		canvas = new Canvas_Automaton(scrolledForm.getBody(), SWT.DOUBLE_BUFFERED, this);
		canvas.setSize(1500, 1500);
		toolkit.adapt(canvas);
		toolkit.paintBordersFor(canvas);
		canvas.setPrefix_StateName(AutomatonPrefix);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				canvas.paint(e.gc);
			}
		});
		createActions();
		initializeToolBar();
		initializeMenu();
		//this.setPartName("未命名" + INSTANCENUM);
	}
	
	public void setTitle(String title){
		setPartName(title);
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	public Automaton getM_Automaton() {
		return m_Automaton;
	}

	public void setM_Automaton(Automaton mAutomaton) {
		m_Automaton = mAutomaton;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	public boolean doDelete() {
		boolean result = canvas.doDelete();
		if (result){
			setDirty(true);
		}
		return result;
	}

	public boolean doAlign(byte direction) {
		boolean result = canvas.doAlign(direction);
		if (result){
			setDirty(true);
		}
		return result;
	}
	
	public boolean isContainsCiricle(){
		if (canvas.getNumofSelectedCircle() == 0){
			return false;
		}
		return true;
	}

	@Override
	public void saveAsImage() {
		if (!OS.IsWin32s){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误", "本操作仅支持Win32系统！");
		}
		FileDialog dlg = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
		dlg.setFileName(this.getTitle()+".jpg");
		dlg.setFilterNames(new String[]{"Image Files"});
		dlg.setFilterExtensions(new String[]{"*.jpg"});
		String fileName = dlg.open();
		if (fileName == null){
			return;
		}
		File file = new File(fileName);
		if (file.exists()){
			if(!MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "已存在", "该文件已经存在，是否覆盖？"))
				return;
		}
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData []{CommonTool.makeShotImage(canvas).getImageData()};
		loader.save(fileName, SWT.IMAGE_JPEG);
		setDirty(false);
	}

	@Override
	public Canvas_Automaton getCanvas() {
		return canvas;
	}

	@Override
	public void doRedo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doUndo() {
		// TODO Auto-generated method stub
		
	}
}
