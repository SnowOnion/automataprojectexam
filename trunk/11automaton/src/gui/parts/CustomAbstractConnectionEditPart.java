package gui.parts;

import gui.figure.TransitionFigure;
import gui.model.AbstractConnectionModel;
import gui.policies.CustomBendpointEditPolicy;
import gui.policies.CustomConnectionEditPolicy;
import gui.policies.CustomConnectionEndpointEditPolicy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

public class CustomAbstractConnectionEditPart extends
		AbstractConnectionEditPart implements PropertyChangeListener {

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new CustomConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new CustomConnectionEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
				new CustomBendpointEditPolicy());

	}

	protected IFigure createFigure() {
		AbstractConnectionModel transition = (AbstractConnectionModel)this.getModel();
		TransitionFigure figure = new TransitionFigure();
		figure.setText(transition.getCondition());
		return figure;
	}

	public void activate() {
		super.activate();
		((AbstractConnectionModel) getModel()).addPropertyChangeListener(this);
	}

	public void deactivate() {
		((AbstractConnectionModel) getModel()).removePropertyChange(this);
		super.deactivate();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(
				AbstractConnectionModel.P_CONDITION)) {
			TransitionFigure figure = (TransitionFigure)this.getFigure();
			figure.setText((String) evt.getNewValue());
			//System.out.println(figure.getText());
		} else if (evt.getPropertyName().equals(
				AbstractConnectionModel.P_BEND_POINT)) {
            refreshBendpoints();
		} else if (evt.getPropertyName().equals(
				AbstractConnectionModel.P_SOURCE)) {
			//TransitionFigure figure = (TransitionFigure)this.getFigure();
		} else if (evt.getPropertyName().equals(
				AbstractConnectionModel.P_TARGET)) {
			// ============================================
		}
	}

	public void refreshBendpoints() {
		List bendpoints = ((AbstractConnectionModel) getModel())
				.getBendpoints();
		List constraint = new ArrayList();
		for (int i = 0; i < bendpoints.size(); i++) {
			constraint.add(new AbsoluteBendpoint((Point) bendpoints.get(i)));
		}
		getConnectionFigure().setRoutingConstraint(constraint);
	}

	public void refreshVisuals() {
		refreshBendpoints();
	}

}
