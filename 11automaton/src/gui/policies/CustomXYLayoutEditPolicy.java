package gui.policies;

import gui.commands.ChangeConstraintCommand;
import gui.commands.CreateStateCommand;
import gui.model.StateModel;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class CustomXYLayoutEditPolicy extends XYLayoutEditPolicy {

	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		ChangeConstraintCommand command = new ChangeConstraintCommand();
		command.setModel(child.getModel());
		command.setConstraint((Rectangle)constraint);
		//System.out.println("create cmd");
		return command;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		CreateStateCommand command = new CreateStateCommand();
		Rectangle constraint = (Rectangle)getConstraintFor(request);
		StateModel stateModel = (StateModel)request.getNewObject();
		stateModel.setConstraint(constraint);
		stateModel.setColor(ColorConstants.orange);
		System.out.println(getHost().getModel());
		stateModel.setParent(getHost().getModel());
		command.setContentsModel(getHost().getModel());
		command.setHelloModel(stateModel);
		return command;
	}
	
	@Override
	public Command getCommand(Request request){
		//System.out.println("cmd "+request.getType());
		return super.getCommand(request);
	}

}
