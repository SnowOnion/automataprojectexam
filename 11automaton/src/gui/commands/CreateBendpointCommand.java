package gui.commands;

import gui.model.AbstractConnectionModel;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class CreateBendpointCommand extends Command{
	private AbstractConnectionModel connection;
	private Point location;
	private int index;
	
	public void execute(){
		System.out.println("Add a bendpoint.....");
		connection.addBendpoint(index, location);
	}
	public void undo(){
		connection.removeBendpoint(index);
	}
	public void setConnection(Object model) {
		this.connection = (AbstractConnectionModel)model;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	
	
}
