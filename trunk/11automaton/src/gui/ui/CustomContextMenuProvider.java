package gui.ui;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

public class CustomContextMenuProvider extends ContextMenuProvider {

	private ActionRegistry registry;

	public void setActionRegistry(ActionRegistry registry) {
		this.registry = registry;
	}

	public ActionRegistry getActionRegistry() {
		return registry;
	}

	public CustomContextMenuProvider(EditPartViewer viewer,
			ActionRegistry registry) {
		super(viewer);
		setActionRegistry(registry);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		GEFActionConstants.addStandardActionGroups(menu);
		menu.add(registry.getAction(ActionFactory.REDO.getId()));
		menu.add(registry.getAction(ActionFactory.UNDO.getId()));
		menu.add(registry.getAction(ActionFactory.DELETE.getId()));
	}
}
