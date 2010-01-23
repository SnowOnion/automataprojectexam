package gui;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.mxgraph.model.*;
import automata.*;

public class mxState extends State implements Serializable{
	private mxCell cell;
	
/*	public mxState(){
		this(null);
	}
*/	
	public mxState(mxCell cell){
		super("");
		this.cell=cell;
		this.setStateId(cell.getValue().toString());
	}
	
	public Set<mxTransition> getTrans(){
		Collection<List<Transition>> lsts= trans.values();
		Set<mxTransition> re=new LinkedHashSet<mxTransition>();
		for(List<Transition> lst:lsts){
			for(Transition t:lst){
				re.add((mxTransition)t);
			}
		}
		return re;
	}
	
	public void removeTransfer(Transition tran){
		trans.get(tran.getInputChar()).remove(tran);
	}

	
	@Override
	public String toString(){
		return getStateId();
	}
	
	public mxCell getCell() {
		return cell;
	}

	public void setCell(mxCell cell) {
		this.cell = cell;
	}
}
