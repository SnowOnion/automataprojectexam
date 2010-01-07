package gui.parts;

import gui.model.StateModel;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.swt.widgets.Text;

public class CustomDirectEditManager extends DirectEditManager {
	private StateModel helloModel ;
	public CustomDirectEditManager(GraphicalEditPart source, Class editorType, CellEditorLocator locator) {
		super(source, editorType, locator);
		helloModel = (StateModel)source.getModel();
	}

	@Override
	protected void initCellEditor() {
		getCellEditor().setValue(helloModel.getText());
		Text text = (Text)getCellEditor().getControl();
		text.selectAll();
	}

}
