package gui.parts;

import gui.model.ContentsModel;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class ContentsTreeEditPart extends CustomTreeEditPart {
	
	protected List getModelChildren(){
		return ((ContentsModel)getModel()).getChildren();
	}
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(ContentsModel.P_CHILDREN)){
			refreshChildren();
		}
	}

}
