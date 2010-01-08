package gui.editor;

import gui.editor.EditorActions.HistoryAction;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;

import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;

public class EditorPopupMenu extends JPopupMenu
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132749140550242191L;

	public EditorPopupMenu(BasicGraphEditor editor)
	{
		boolean selected = !editor.getGraphComponent().getGraph()
				.isSelectionEmpty();

		add(editor.bind(mxResources.get("undo"), new HistoryAction(true),
				"/gui/images/undo.gif"));

		addSeparator();

		add(
				editor.bind(mxResources.get("cut"), TransferHandler
						.getCutAction(),
						"/gui/images/cut.gif"))
				.setEnabled(selected);
		add(
				editor.bind(mxResources.get("copy"), TransferHandler
						.getCopyAction(),
						"/gui/images/copy.gif"))
				.setEnabled(selected);
		add(editor.bind(mxResources.get("paste"), TransferHandler
				.getPasteAction(),
				"/gui/images/paste.gif"));

		addSeparator();

		add(
				editor.bind(mxResources.get("delete"), mxGraphActions
						.getDeleteAction(),
						"/gui/images/delete.gif"))
				.setEnabled(selected);

		addSeparator();

		// Creates the format menu
		JMenu menu = (JMenu) add(new JMenu(mxResources.get("format")));

		EditorMenuBar.populateFormatMenu(menu, editor);

		// Creates the shape menu
		menu = (JMenu) add(new JMenu(mxResources.get("shape")));

		EditorMenuBar.populateShapeMenu(menu, editor);

		addSeparator();

		add(
				editor.bind(mxResources.get("edit"), mxGraphActions
						.getEditAction())).setEnabled(selected);

		addSeparator();

		add(editor.bind(mxResources.get("selectVertices"), mxGraphActions
				.getSelectVerticesAction()));
		add(editor.bind(mxResources.get("selectEdges"), mxGraphActions
				.getSelectEdgesAction()));

		addSeparator();

		add(editor.bind(mxResources.get("selectAll"), mxGraphActions
				.getSelectAllAction()));
	}

}
