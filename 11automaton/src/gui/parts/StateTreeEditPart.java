package gui.parts;

import gui.model.StateModel;
import gui.policies.CustomComponentEditPolicy;

import java.beans.PropertyChangeEvent;

import org.eclipse.gef.EditPolicy;

public class StateTreeEditPart extends CustomTreeEditPart {

	protected void refreshVisuals(){
		StateModel model = (StateModel)getModel();
		setWidgetText(model.getText());
	}
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(StateModel.P_TEXT)){
			refreshVisuals();
		}
	}
	public void createEditPolicy(){
		installEditPolicy(EditPolicy.COMPONENT_ROLE,new CustomComponentEditPolicy());
	}
	

}
