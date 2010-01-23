package gui.parts;

import gui.figure.StateFigure;
import gui.model.StateModel;
import gui.policies.CustomComponentEditPolicy;
import gui.policies.CustomDirectEditPolicy;
import gui.policies.CustomGraphicalNodeEditPolicy;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TextCellEditor;

public class StateEditPart extends EditPartWithListener implements NodeEditPart {
	private CustomDirectEditManager directManager = null;

	@Override
	protected IFigure createFigure() {
		StateModel hm = (StateModel) this.getModel();
		StateFigure state = new StateFigure();
		state.setText(hm.getText());
		state.setPosition(hm.getPosition());
		if (hm.isInitial()) {
			state.changeType2Initial();
		}
		if (hm.isAccept()) {
			state.changeType2Accept();
		}
		return state;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new CustomComponentEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new CustomDirectEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new CustomGraphicalNodeEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		// System.out.println("refreshv");
		Rectangle constraint = ((StateModel) getModel()).getConstraint();
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), constraint);
		((StateModel) getModel()).setPosition(constraint.getLocation());

	}

	public void propertyChange(PropertyChangeEvent event) {
		// System.out.println("propertyChage");
		if (event.getPropertyName().equals(StateModel.P_CONSTRAINT)) {
			refreshVisuals();
		} else if (event.getPropertyName().equals(StateModel.P_TEXT)) {
			StateFigure figure = (StateFigure) getFigure();
			figure.setText((String) event.getNewValue());
		} else if (event.getPropertyName().equals(StateModel.P_POSITION)) {
			StateFigure figure = (StateFigure) getFigure();
			figure.setPosition((Point) event.getNewValue());
			((StateModel) getModel()).getConstraint().setLocation(
					(Point) event.getNewValue());
			System.out.println("position:"
					+ ((StateModel) getModel()).getPosition().x);
		} else if (event.getPropertyName().equals(
				StateModel.P_SOURCE_CONNECTION)) {
			refreshSourceConnections();
		} else if (event.getPropertyName().equals(
				StateModel.P_TARGET_CONNECTION)) {
			refreshTargetConnections();
		} else if (event.getPropertyName().equals(StateModel.P_INITIAL)) {
			StateFigure figure = (StateFigure) getFigure();
			System.out.println("color yellow");
			if (((Integer) event.getNewValue()).intValue() == 1)
				figure.changeType2Initial();
			else
				figure.changeType2NotInitial();
		} else if (event.getPropertyName().equals(StateModel.P_ACCEPT)) {
			StateFigure figure = (StateFigure) getFigure();
			if (((Integer) event.getNewValue()).intValue() == 1)
				figure.changeType2Accept();
			else
				figure.changeType2NotAccept();
		} else if (event.getPropertyName().equals(StateModel.P_ERROR)) {
			MessageDialog.openInformation(getViewer().getControl().getShell(),
					"Error", (String) (event.getNewValue()));
		}
	}

	// @Override
	// protected void refreshSourceConnections(){
	//		
	// }
	// @Override
	// protected void refreshTargetConnections(){
	//		
	// }
	public void performRequest(Request request) {
		if (request.getType().equals(RequestConstants.REQ_DIRECT_EDIT)) {
			performDirectEdit();
			return;
		}
		super.performRequest(request);
	}

	public void performDirectEdit() {
		if (directManager == null) {
			directManager = new CustomDirectEditManager(this,
					TextCellEditor.class, new CustomCellEditorLocator(
							getFigure()));
		}
		directManager.show();
	}

	protected List getModelSourceConnections() {
		return ((StateModel) getModel()).getModelSourceConnections();
	}

	protected List getModelTargetConnections() {
		return ((StateModel) getModel()).getModelTargetConnections();
	}

	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}
}
