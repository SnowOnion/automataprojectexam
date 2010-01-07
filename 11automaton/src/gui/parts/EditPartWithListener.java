package gui.parts;

import gui.model.AbstractModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class EditPartWithListener extends AbstractGraphicalEditPart 
	implements PropertyChangeListener{
	
	@Override
	public void activate(){
		super.activate();
		((AbstractModel)getModel()).addPropertyChangeListener(this);
		//System.out.println("activate");
	}
	@Override
	public void deactivate(){
		super.deactivate();
		((AbstractModel)getModel()).removePropertyChange(this);
		//System.out.println("deactivate");
	}
	
	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
