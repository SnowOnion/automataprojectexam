package automatonmodeling;

import gui.actions.CreateNewAction;
import gui.actions.DiagramAction;
import gui.actions.SaveModelAction;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private DiagramAction diagramAction;
	private SaveModelAction saveAction;
	private CreateNewAction createNewAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);
		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);
		diagramAction = new DiagramAction(window);
		register(diagramAction);
		saveAction = new SaveModelAction(window);
		register(saveAction);
		createNewAction = new CreateNewAction(window);
		register(createNewAction);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", "File");
		fileMenu.add(createNewAction);
		fileMenu.add(diagramAction);
		fileMenu.add(new Separator());
		fileMenu.add(saveAction);
		fileMenu.add(new Separator());
		fileMenu.add(exitAction);

		MenuManager helpMenu = new MenuManager("&Help", "Help");
		helpMenu.add(aboutAction);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
	}

}