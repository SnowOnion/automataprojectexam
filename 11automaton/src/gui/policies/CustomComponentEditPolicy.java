package gui.policies;

import gui.commands.DeleteCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class CustomComponentEditPolicy extends ComponentEditPolicy{
	protected Command createDeleteCommand(GroupRequest deleteRequest){
		DeleteCommand deleteCommand = new DeleteCommand();
		deleteCommand.setContentsModel(getHost().getParent().getModel());
		deleteCommand.setHelloModel(getHost().getModel());
		return deleteCommand;
	}

}
