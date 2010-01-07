package gui.commands;

import gui.model.AbstractConnectionModel;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class MoveBendpointCommand extends Command{
	private AbstractConnectionModel connection;
	private Point oldLocation,newLocation;
	private int index;
	
	public void execute(){
		oldLocation = (Point)connection.getBendpoints().get(index);
		connection.replaceBendpoint(index, newLocation);
	}
	public void undo(){
		connection.replaceBendpoint(index, oldLocation);
	}
	public void setConnection(Object model) {
		this.connection = (AbstractConnectionModel)model;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void setNewLocation(Point location) {
		this.newLocation = location;
	}
}
