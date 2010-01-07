package gui.commands;

import gui.model.ContentsModel;
import gui.model.StateModel;

import org.eclipse.gef.commands.Command;

public class CreateStateCommand extends Command{
	private StateModel helloModel;
	private ContentsModel contentsModel;
	
	public void execute(){
		contentsModel.addChild(helloModel);
	}
	public void undo(){
		contentsModel.removeChild(helloModel);
	}
	public void setHelloModel(Object model){
		helloModel = (StateModel)model;
	}
	public void setContentsModel(Object model){
		contentsModel = (ContentsModel)model;
	}
}
