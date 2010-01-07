package gui.commands;

import gui.model.AbstractConnectionModel;
import gui.model.ContentsModel;

import org.eclipse.gef.commands.Command;

public class DeleteConnectionCommand extends Command {
	AbstractConnectionModel connection;
	private ContentsModel contentsModel;

	public ContentsModel getContentsModel() {
		return contentsModel;
	}

	public void setContentsModel(Object contentsModel) {
		if (contentsModel instanceof ContentsModel)
			this.contentsModel = (ContentsModel) contentsModel;
	}

	public void execute() {
		contentsModel.removeConnection(connection);
		connection.detachSource();
		connection.detachTarget();
	}

	public void setConnectionModel(Object model) {
		connection = (AbstractConnectionModel) model;
	}

	public void undo() {
		connection.attachSource();
		connection.attachTarget();
		contentsModel.addConnection(connection);
	}
}
