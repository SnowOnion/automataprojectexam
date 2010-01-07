package gui.policies;

import gui.commands.CreateBendpointCommand;
import gui.commands.DeleteBendpointCommand;
import gui.commands.MoveBendpointCommand;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

public class CustomBendpointEditPolicy extends BendpointEditPolicy{

	@Override
	protected Command getCreateBendpointCommand(BendpointRequest request) {
		Point point = request.getLocation();
		getConnection().translateToRelative(point);
		
		CreateBendpointCommand command = new CreateBendpointCommand();
		command.setLocation(point);
		command.setConnection(getHost().getModel());
		command.setIndex(request.getIndex());
		return command;
	}

	@Override
	protected Command getDeleteBendpointCommand(BendpointRequest request) {
		DeleteBendpointCommand command = new DeleteBendpointCommand();
		command.setConnection(getHost().getModel());
		command.setIndex(request.getIndex());
		return command;
	}

	@Override
	protected Command getMoveBendpointCommand(BendpointRequest request) {
		Point point = request.getLocation();
		getConnection().translateToRelative(point);
		
		MoveBendpointCommand command = new MoveBendpointCommand();
		command.setNewLocation(point);
		command.setConnection(getHost().getModel());
		command.setIndex(request.getIndex());
		return command;
	}

}
