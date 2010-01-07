package gui.parts;

import gui.model.AbstractConnectionModel;
import gui.model.ContentsModel;
import gui.model.StateModel;
import gui.model.TransitionModel;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class PartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		// TODO Auto-generated method stub
		EditPart part = getPartForElement(model);
		part.setModel(model);
		return part;
	}

	private EditPart getPartForElement(Object modelElement) {
		if (modelElement instanceof StateModel) {
			return new StateEditPart();

		} else if (modelElement instanceof ContentsModel) {
			return new ContentsEditPart();

		} else if (modelElement instanceof AbstractConnectionModel) {
			return new CustomAbstractConnectionEditPart();
		}

		throw new RuntimeException("Error");

	}

}
