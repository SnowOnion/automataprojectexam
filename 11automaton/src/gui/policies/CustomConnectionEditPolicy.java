package gui.policies;

import gui.commands.DeleteConnectionCommand;
import gui.model.AbstractConnectionModel;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class CustomConnectionEditPolicy extends ConnectionEditPolicy {

	@Override
	protected Command getDeleteCommand(GroupRequest request) {
		DeleteConnectionCommand command = new DeleteConnectionCommand();
		command.setConnectionModel(getHost().getModel());
		command.setContentsModel(((AbstractConnectionModel) getHost()
				.getModel()).getSource().getParent());
		return command;
	}

}
