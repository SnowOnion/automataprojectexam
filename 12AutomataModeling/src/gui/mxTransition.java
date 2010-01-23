package gui;

import java.io.Serializable;

import com.mxgraph.model.*;

import automata.Automata;
import automata.Transition;

public class mxTransition extends Transition implements Serializable{
	private mxCell cell;
	
/*	public mxTransition(){
		super(null,' ',null);
	}
*/	
	public mxTransition(mxCell cell){
		super(null,' ',null);
		this.cell=cell;
		setInputChar(objectToChar(cell.getValue()));
	}
	
	public static char objectToChar(Object obj){
		if(obj==null){
			return Automata.EPSILON;
		}else{
			return stringToChar(obj.toString());
		}
	}
	
	public static char stringToChar(String input){
		if(input==null||input.length()==0){
			return Automata.EPSILON;
		}else{
			return input.charAt(0);
		}
	}
	
	@Override
	public String toString(){
		return this.getInputChar()+"";
	}

	public mxCell getCell() {
		return cell;
	}

	public void setCell(mxCell cell) {
		this.cell = cell;
	}
	
}
