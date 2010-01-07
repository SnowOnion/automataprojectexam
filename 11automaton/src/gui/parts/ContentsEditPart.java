package gui.parts;


import gui.model.ContentsModel;
import gui.policies.CustomXYLayoutEditPolicy;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.EditPolicy;

public class ContentsEditPart extends EditPartWithListener {

	protected IFigure createFigure() {
		Layer figure = new Layer();
		XYLayout layout = new XYLayout();
		figure.setLayoutManager(layout);
		return figure;
	}

	protected void createEditPolicies() {
		//System.out.println("install");
		installEditPolicy(EditPolicy.LAYOUT_ROLE,new CustomXYLayoutEditPolicy());
	}
	
	protected List getModelChildren(){
		return ((ContentsModel)getModel()).getChildren();
	}
	public void propertyChange(PropertyChangeEvent event){
		//System.out.println("propertyChage");
		if(event.getPropertyName().equals(ContentsModel.P_CHILDREN)){
			refreshChildren();
		}
		
	}
}
