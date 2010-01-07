package gui.policies;

import gui.commands.DirectEditCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class CustomDirectEditPolicy extends DirectEditPolicy{
	protected Command getDirectEditCommand(DirectEditRequest request){
		DirectEditCommand command = new DirectEditCommand();
		command.setModel(getHost().getModel());
		command.setText((String)request.getCellEditor().getValue());
		return command;
	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
		
	}
}
