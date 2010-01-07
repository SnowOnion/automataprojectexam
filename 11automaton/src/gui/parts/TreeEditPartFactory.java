package gui.parts;

import gui.model.ContentsModel;
import gui.model.StateModel;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class TreeEditPartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		if(model instanceof ContentsModel){
			part = new ContentsTreeEditPart();
		}else if(model instanceof StateModel){
			part = new StateTreeEditPart();
		}
		if(part != null){
			part.setModel(model);
		}
		return part;
	}

}
