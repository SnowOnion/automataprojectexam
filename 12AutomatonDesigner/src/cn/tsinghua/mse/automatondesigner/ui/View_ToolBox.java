package cn.tsinghua.mse.automatondesigner.ui;

import java.awt.Font;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 工具箱视图的类
 * @author David
 *
 */
public class View_ToolBox extends ViewPart {

	public static final String ID = "cn.tsinghua.mse.automatondesigner.ui.View_ToolBox"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	public View_ToolBox() {
		
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		toolkit.adapt(scrolledComposite);
		toolkit.paintBordersFor(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		ToolBar toolBar = new ToolBar(scrolledComposite, SWT.BORDER | SWT.FLAT | SWT.RIGHT | SWT.VERTICAL);
		toolBar.setFont(SWTResourceManager.getFont("宋体", 12, SWT.NORMAL));
		toolkit.adapt(toolBar);
		toolkit.paintBordersFor(toolBar);
		
		
		
		ToolItem ti_Selection = new ToolItem(toolBar, SWT.RADIO);
		ti_Selection.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/selected.gif"));
		ti_Selection.setSelection(true);
		ti_Selection.setText("  选择    ");
		
		ToolItem ti_State = new ToolItem(toolBar, SWT.RADIO);
		ti_State.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/state2.gif"));
		ti_State.setText("  状态    ");
		
		ToolItem ti_Transform = new ToolItem(toolBar, SWT.RADIO);
		ti_Transform.setImage(ResourceManager.getPluginImage("AutomatonDesigner", "icons/transform.gif"));
		ti_Transform.setText("  转移    ");
		
		scrolledComposite.setContent(toolBar);
		scrolledComposite.setMinSize(toolBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		createActions();
		initializeToolBar();
		initializeMenu();
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
		//IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		//IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
