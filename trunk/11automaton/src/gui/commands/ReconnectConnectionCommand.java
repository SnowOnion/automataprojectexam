package gui.commands;

import gui.model.AbstractConnectionModel;
import gui.model.StateModel;

import org.eclipse.gef.commands.Command;

public class ReconnectConnectionCommand extends Command{
	private AbstractConnectionModel connectionModel;
	private StateModel newSource;
	private StateModel newTarget;
	private StateModel oldSource;
	private StateModel oldTarget;
	
	public void execute(){
		if(newSource != null && newTarget != null){
			throw new RuntimeException("both chage in reconnectConnection");
		}
		
		if (newSource != null){
			oldSource = connectionModel.getSource();
			connectionModel.detachSource();
			connectionModel.setSource(newSource);
			connectionModel.attachSource();
			
		}
		if(newTarget != null){
			oldTarget = connectionModel.getTarget();
			connectionModel.detachTarget();
			connectionModel.setTarget(newTarget);
			connectionModel.attachTarget();
		}
	}
	public void undo(){
		if(oldSource != null && oldTarget != null){
			throw new RuntimeException("both chage in reconnectConnection");
		}
		if (oldSource != null){
			connectionModel.detachSource();
			connectionModel.setSource(oldSource);
			connectionModel.attachSource();
			oldSource = null;
		}
		if(oldTarget != null){
			connectionModel.detachTarget();
			connectionModel.setTarget(oldTarget);
			connectionModel.attachTarget();
			oldTarget = null;
		}
	}
	public void setConnectionModel(Object model) {
		this.connectionModel = (AbstractConnectionModel)model;
	}
	public void setNewSource(Object model) {
		this.newSource = (StateModel)model;
	}
	public void setNewTarget(Object model) {
		this.newTarget = (StateModel)model;
	}

}
