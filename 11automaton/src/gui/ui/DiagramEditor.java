package gui.ui;

import gui.help.AutomatonType;
import gui.model.AbstractConnectionModel;
import gui.model.ContentsModel;
import gui.model.StateModel;
import gui.model.TransitionModel;
import gui.parts.PartFactory;
import gui.parts.TreeEditPartFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import automaton.Automaton;
import automaton.AutomatonFactory;
import automaton.State;
import automatonmodeling.Application;
import automatonmodeling.ModelTransformer;

public class DiagramEditor extends GraphicalEditorWithPalette {
	public static final String ID = "gui.ui.DiagramEditor";
	private GraphicalViewer viewer;
	private ContentsModel contents;
	private String filepath;
	public static boolean isNewFile=true;
	private String name;

	public DiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new PartFactory());

		ScalableRootEditPart rootEditPart = new ScalableRootEditPart();
		viewer.setRootEditPart(rootEditPart);

		ZoomManager manager = rootEditPart.getZoomManager();

		double[] zoomLevels = new double[] { 0.25, 0.5, 0.75, 1.0, 1.5, 2.0,
				2.5, 3.0, 4.0, 5.0, 10.0, 20.0 };
		manager.setZoomLevels(zoomLevels);

		ArrayList zoomContributions = new ArrayList();
		zoomContributions.add(ZoomManager.FIT_ALL);
		zoomContributions.add(ZoomManager.FIT_WIDTH);
		zoomContributions.add(ZoomManager.FIT_HEIGHT);
		manager.setZoomLevelContributions(zoomContributions);

		IAction action = new ZoomInAction(manager);
		getActionRegistry().registerAction(action);

		action = new ZoomOutAction(manager);
		getActionRegistry().registerAction(action);

		KeyHandler keyHandler = new KeyHandler();
		keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
				getActionRegistry().getAction(GEFActionConstants.DELETE));
		keyHandler.put(KeyStroke.getPressed(SWT.F2, 0), getActionRegistry()
				.getAction(GEFActionConstants.DIRECT_EDIT));
		// getGraphicalViewer().setKeyHandler(keyHandler);
		getGraphicalViewer().setKeyHandler(
				new GraphicalViewerKeyHandler(getGraphicalViewer())
						.setParent(keyHandler));

		ContextMenuProvider provider = new CustomContextMenuProvider(viewer,
				getActionRegistry());
		viewer.setContextMenu(provider);
		getSite().registerContextMenu(provider, viewer);
	}

	@Override
	protected void initializeGraphicalViewer() {
		//如果是新建的文件 
		if (isNewFile){
			contents = new ContentsModel();
			StateModel child = new StateModel();

			child.setConstraint(new Rectangle(80, 80, 50, 50));
			child.isInitial=1;
			contents.setInitialState(child);
			contents.addChild(child);
			child.setParent(contents);
		}
		//如果是打开一个文件
		else {
			String path = ((DiagramEditorInput)this.getEditorInput()).getName();
			try {
				Automaton automaton = AutomatonFactory.getAutomatonFromXml(new File(path));
				contents = ModelTransformer.getModelFromAutomaton(automaton);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		filepath = ((DiagramEditorInput)this.getEditorInput()).getPath().toString();
		System.out.println("System file path:"+filepath);
		viewer.setContents(contents);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		getCommandStack().markSaveLocation();
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public boolean isSaveAsAllowed() {
		return getCommandStack().isDirty();
	}

	public void CommandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		PaletteRoot root = new PaletteRoot();
		PaletteGroup toolGroup = new PaletteGroup("Tools");
		ToolEntry tool = new SelectionToolEntry();
		toolGroup.add(tool);
		root.setDefaultEntry(tool);

		tool = new MarqueeToolEntry();
		toolGroup.add(tool);

		PaletteDrawer drawer = new PaletteDrawer("State");
		ImageDescriptor descriptor = AbstractUIPlugin
				.imageDescriptorFromPlugin(Application.PLUGIN_ID, "/gar.ico");

		CreationToolEntry creationToolEntry = new CreationToolEntry(
				"Create a state", "Create a state", new SimpleFactory(
						StateModel.class), descriptor, descriptor);
		drawer.add(creationToolEntry);

		PaletteDrawer transitionDrawer = new PaletteDrawer("Transition");
		ImageDescriptor arrowConnectionDescriptor = AbstractUIPlugin
				.imageDescriptorFromPlugin(Application.PLUGIN_ID, "/gar.ico");
		ConnectionCreationToolEntry arrowConnxCreationToolEntry = new ConnectionCreationToolEntry(
				"Create a transition", "Create a transition",
				new SimpleFactory(TransitionModel.class),
				arrowConnectionDescriptor, arrowConnectionDescriptor);
		transitionDrawer.add(arrowConnxCreationToolEntry);

		root.add(toolGroup);
		root.add(drawer);
		root.add(transitionDrawer);
		return root;
	}

	@SuppressWarnings("unchecked")
	public void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();

		IAction action = new DirectEditAction((IWorkbenchPart) this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.MIDDLE);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

	}

	public Object getAdaptor(Class type) {
		if (type == ZoomManager.class) {
			return ((ScalableRootEditPart) getGraphicalViewer()
					.getRootEditPart()).getZoomManager();
		}
		if (type == IContentOutlinePage.class) {
			return new CustomContentOutlinePage();
		}
		return super.getAdapter(type);
	}

	class CustomContentOutlinePage extends ContentOutlinePage {

		private SashForm sash;
		private ScrollableThumbnail thumbnail;
		private DisposeListener disposeListener;

		public CustomContentOutlinePage() {
			super(new TreeViewer());
		}

		@SuppressWarnings("deprecation")
		public void init(IPageSite pageSite) {
			super.init(pageSite);
			ActionRegistry registry = getActionRegistry();
			IActionBars bars = pageSite.getActionBars();

			String id = IWorkbenchActionConstants.UNDO;
			bars.setGlobalActionHandler(id, registry.getAction(id));

			id = IWorkbenchActionConstants.REDO;
			bars.setGlobalActionHandler(id, registry.getAction(id));

			id = IWorkbenchActionConstants.DELETE;
			bars.setGlobalActionHandler(id, registry.getAction(id));

			bars.updateActionBars();
		}

		public void createControl(Composite parent) {
			sash = new SashForm(parent, SWT.VERTICAL);
			getViewer().createControl(sash);
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new TreeEditPartFactory());
			getViewer().setContents(viewer.getContents());
			getSelectionSynchronizer().addViewer(getViewer());

			Canvas canvas = new Canvas(sash, SWT.BORDER);
			LightweightSystem lws = new LightweightSystem(canvas);
			thumbnail = new ScrollableThumbnail(
					(Viewport) (((ScalableRootEditPart) getGraphicalViewer()
							.getRootEditPart())
							.getLayer(LayerConstants.PRINTABLE_LAYERS)));
			lws.setContents(thumbnail);

			disposeListener = new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}

			};
			getGraphicalViewer().getControl().addDisposeListener(
					disposeListener);
		}

		public Control getControl() {
			return sash;
		}

		public void dispose() {
			getSelectionSynchronizer().removeViewer(getViewer());
			if (getGraphicalViewer().getControl() != null
					&& !getGraphicalViewer().getControl().isDisposed()) {
				getGraphicalViewer().getControl().removeDisposeListener(
						disposeListener);
			}
			super.dispose();
		}
	}

	public ContentsModel getContents() {
		return contents;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setContentsType(String type) {
		if (type.equals("DFA"))
			this.contents.setType(AutomatonType.DFA);
		else if (type.equals("NFA"))
			this.contents.setType(AutomatonType.NFA);
		else this.contents.setType(AutomatonType.PDA);
	}
}
