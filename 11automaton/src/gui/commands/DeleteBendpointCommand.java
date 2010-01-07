package gui.commands;

import gui.model.AbstractConnectionModel;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class DeleteBendpointCommand extends Command{
	private AbstractConnectionModel connection;
	private Point oldLocation;
	private int index;
	
	public void execute(){
		oldLocation = (Point)connection.getBendpoints().get(index);
		connection.removeBendpoint(index);
	}
	public void undo(){
		connection.addBendpoint(index, oldLocation);
	}
	public void setConnection(Object model) {
		this.connection = (AbstractConnectionModel)model;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
