package gui.commands;

import gui.model.StateModel;

import org.eclipse.gef.commands.Command;

public class DirectEditCommand extends Command{
	private String oldText ,newText;
	private StateModel helloModel;
	public void execute(){
		oldText = helloModel.getText();
		helloModel.setText(newText);
	}
	public void setModel(Object model){
		helloModel = (StateModel)model;
	}
	public void setText(String text){
		newText = text;
	}
	public void undo(){
		helloModel.setText(oldText);
	}
	
}
