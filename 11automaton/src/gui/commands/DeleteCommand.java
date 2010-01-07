package gui.commands;

import gui.model.AbstractConnectionModel;
import gui.model.ContentsModel;
import gui.model.StateModel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

public class DeleteCommand extends Command{
	private ContentsModel contentsModel;
	private StateModel helloModel;
	
	@SuppressWarnings("unchecked")
	private List sourceConnections = new ArrayList();
	@SuppressWarnings("unchecked")
	private List targetConnections = new ArrayList();
	
	
	@SuppressWarnings("unchecked")
	public void execute(){
		sourceConnections.addAll(helloModel.getModelSourceConnections());
		targetConnections.addAll(helloModel.getModelTargetConnections());
		for(int i=0;i<sourceConnections.size();i++){
			AbstractConnectionModel model = (AbstractConnectionModel)sourceConnections.get(i);
			model.detachSource();
			model.detachTarget();
		}
		
		for(int i=0;i<targetConnections.size();i++){
			AbstractConnectionModel model = (AbstractConnectionModel)targetConnections.get(i);
			model.detachSource();
			model.detachTarget();
		}
		
		contentsModel.removeChild(helloModel);
	}
	public void setContentsModel(Object model){
		this.contentsModel = (ContentsModel)model;
	}
	public void setHelloModel(Object model){
		helloModel = (StateModel)model;
	}
	public void undo(){
		contentsModel.addChild(helloModel);
		
		for(int i=0;i<sourceConnections.size();i++){
			AbstractConnectionModel model = (AbstractConnectionModel)sourceConnections.get(i);
			model.attachSource();
			model.attachTarget();
		}
		
		for(int i=0;i<targetConnections.size();i++){
			AbstractConnectionModel model = (AbstractConnectionModel)targetConnections.get(i);
			model.attachSource();
			model.attachTarget();
		}
		
		sourceConnections.clear();
		targetConnections.clear();
	}
}
