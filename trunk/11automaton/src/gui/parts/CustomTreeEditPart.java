package gui.parts;

import gui.model.AbstractModel;

import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

public abstract class CustomTreeEditPart extends AbstractTreeEditPart 
	implements PropertyChangeListener{
	
	public void activate(){
		super.activate();
		((AbstractModel)getModel()).addPropertyChangeListener(this);
	}
	
	public void deactivate(){
		((AbstractModel)getModel()).removePropertyChange(this);
		super.deactivate();
	}
	

}
