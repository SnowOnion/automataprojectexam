package gui.commands;

import gui.model.StateModel;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

public class ChangeConstraintCommand extends Command{
	private StateModel helloModel;
	private Rectangle constraint;
	private Rectangle oldConstraint;
	
	public void execute(){
		//System.out.println("execute");
		helloModel.setConstraint(constraint);
		
	}
	public void setConstraint(Rectangle rect){
		constraint = rect;
	}
	public void  setModel(Object model){
		helloModel = (StateModel)model;
		oldConstraint = helloModel.getConstraint();
	}
	public void undo(){
		helloModel.setConstraint(oldConstraint);
	}
}
