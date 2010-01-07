package gui.policies;

import gui.commands.CreateTransitionCommand;
import gui.commands.ReconnectConnectionCommand;
import gui.model.StateModel;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class CustomGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		CreateTransitionCommand command = (CreateTransitionCommand) request
				.getStartCommand();
		command.setTarget(getHost().getModel());
		command.setContentsModel(((StateModel) getHost().getModel())
				.getParent());
		return command;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		CreateTransitionCommand command = new CreateTransitionCommand();
		command.setConnection(request.getNewObject());
		command.setSource(getHost().getModel());
		request.setStartCommand(command);
		return command;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ReconnectConnectionCommand command = new ReconnectConnectionCommand();
		command.setConnectionModel(request.getConnectionEditPart().getModel());
		command.setNewSource(getHost().getModel());
		return command;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		ReconnectConnectionCommand command = new ReconnectConnectionCommand();
		command.setConnectionModel(request.getConnectionEditPart().getModel());
		command.setNewTarget(getHost().getModel());
		return command;
	}

}
