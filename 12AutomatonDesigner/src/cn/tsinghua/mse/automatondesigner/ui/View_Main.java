package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.internal.decorators.FullTextDecoratorRunnable;
import org.eclipse.ui.part.ViewPart;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * 图形化显示自动机的主界面
 * 
 * @author David
 * 
 */
public class View_Main extends ViewPart {

	public static final String ID = "cn.tsinghua.mse.automatondesigner.ui.View_Main"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	public static int INSTANCENUM = 0;

	private Automaton m_Automaton = null;
	private boolean isDirty = false;
	private Canvas_Automaton canvas;
	private IWorkbenchWindow mainWindow = null;

	public IWorkbenchWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(IWorkbenchWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	/**
	 * 新建文件时使用的构造函数
	 * 
	 * @wbp.parser.constructor
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
	 * @param automaton
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

		canvas = new Canvas_Automaton(scrolledForm.getBody(), SWT.BORDER, this);
		canvas.setSize(1000, 1000);
		toolkit.adapt(canvas);
		toolkit.paintBordersFor(canvas);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				canvas.paint(e.gc);
			}
		});
		createActions();
		initializeToolBar();
		initializeMenu();
		this.setPartName("未命名" + INSTANCENUM);
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

	public void doDelete() {
		canvas.doDelete();
	}
}
