package gui.commands;

import gui.model.AbstractConnectionModel;
import gui.model.ContentsModel;
import gui.model.StateModel;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class CreateTransitionCommand extends Command {
	private StateModel source, target;
	private AbstractConnectionModel connection;
	private ContentsModel contentsModel;

	public ContentsModel getContentsModel() {
		return contentsModel;
	}

	public void setContentsModel(Object contentsModel) {
		this.contentsModel = (ContentsModel) contentsModel;
	}

	public boolean canExecute() {
		if (source == null || target == null) {
			// System.out.println("can not execute");
			return false;
		}
		return true;
	}

	public void execute() {
		connection.attachSource();
		connection.attachTarget();
		//如果source和target相等，则增加bendpoint，以便transition正常显示
		if (connection.getSource() == connection.getTarget()) {
			Point startLocation = connection.getSource().getPosition();
			int X = startLocation.x;
			int Y = startLocation.y;
			Point cbp=new Point();
			cbp.setLocation(X+25,Y-60);
			connection.addBendpoint(0, cbp);
			Point cbp2 = new Point();
			cbp2.setLocation(X+100, Y-60);
			connection.addBendpoint(1, cbp2);
			Point cbp3 = new Point();
			cbp3.setLocation(X+100, Y+25);
			connection.addBendpoint(2, cbp3);

		}
		contentsModel.addConnection(connection);
		System.out.println("size:"+contentsModel.getConnections().size());
	}

	public void setConnection(Object model) {
		this.connection = (AbstractConnectionModel) model;
	}

	public void setSource(Object model) {
		this.source = (StateModel) model;
		connection.setSource(source);
	}

	public void setTarget(Object model) {
		this.target = (StateModel) model;
		connection.setTarget(target);
	}

	public void undo() {
		connection.detachSource();
		connection.detachTarget();
		contentsModel.removeConnection(connection);
	}

}
