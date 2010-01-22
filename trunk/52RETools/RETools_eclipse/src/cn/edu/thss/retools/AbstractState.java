/**
 * 
 */
package cn.edu.thss.retools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

/**
 * 代表自动机的一个状态
 * @author Huhao lifengxiang dengshuang
 */
public abstract class AbstractState {
	
	private static int idcount = 0;
	private static String idPrefix = "Q";
	
	public static AbstractState CreateStart() {
		return new StartState(idcount ++);
	}
	
	public static AbstractState CreateFinish() {
		return new FinishState(idcount ++);
	}
	
	public static AbstractState CreateCommon() {
		return new CommonState(idcount ++);
	}
	
	public static AbstractState CreateStartFinish(){
		return new StartFinishState(idcount ++);
	}
	
	protected AbstractState() {}
	
	public AbstractState(int id) {
		this.ID = idPrefix + Integer.toString(id);
		tosTrans = new Vector<Transition>();
		fromsTrans = new Vector<Transition>();
	}
	
	public boolean IsFinish() {
		return false;
	}

	public boolean IsStart(){
		return false;
	}
	
	public void AddToTransition(Transition ts) {
		Iterator<Transition> iter = tosTrans.iterator();
		while(iter.hasNext()){
			Transition tran = iter.next();
			if(tran.toString().equals(ts.toString()))
					return;
		}
		if(! tosTrans.contains(ts)){
			tosTrans.add(ts);
		}		
	}
	
	public void AddFromTransition(Transition ts){
		Iterator<Transition> iter = fromsTrans.iterator();
		while(iter.hasNext()){
			Transition tran = iter.next();
			if(tran.toString().equals(ts.toString()))
					return;
		}
		if(! fromsTrans.contains(ts)){
			fromsTrans.add(ts);
		}
	}

	public Set<AbstractState> StatesToOnCondition(char ch){
		Set<AbstractState> tos = new LinkedHashSet<AbstractState>();
		Iterator<Transition> tranIter = tosTrans.iterator();
		while(tranIter.hasNext()){
			Transition tran = tranIter.next();
			if(tran.IsConditionExist(ch))
				tos.add(tran.getToState());
//				tos.add(tran.to);
		}
		return tos;
	}
	
	public Set<Character> AllInputs(){
		Set<Character> inputs = new LinkedHashSet<Character>();
		Iterator<Transition> iter = tosTrans.iterator();
		while(iter.hasNext()){
			Transition tran = iter.next();
//			inputs.addAll(tran.inputs);
			inputs.addAll(tran.getInputs());
		}
		return inputs;
	}
	
	public Vector<Transition> tosTrans;
	public Vector<Transition> fromsTrans;
	public String ID;

	public ArrayList<AbstractState> MoveOn(char ch) {
		// TODO Auto-generated method stub
		ArrayList<AbstractState> tos = new ArrayList<AbstractState>();
		Iterator<Transition> iter = this.tosTrans.iterator();
		while(iter.hasNext()){
			Transition tr = iter.next();
			if(tr.IsConditionExist(ch) && !tos.contains(tr.getToState()))
				tos.add(tr.getToState());
		}
		return tos;
	}

	public ArrayList<AbstractState> MoveBackOn(char ch) {
		// TODO Auto-generated method stub
		ArrayList<AbstractState> froms = new ArrayList<AbstractState>();
		Iterator<Transition> iter = this.fromsTrans.iterator();
		while(iter.hasNext()){
			Transition tr = iter.next();
			if(tr.IsConditionExist(ch) && !froms.contains(tr.getFromState())){
				froms.add(tr.getFromState());
			}
		}
		return froms;
	}
	
	/*
	 * 比较两个状态是否对应相同，即拥有相同的输入，相同的起始状态，相同的输入状态
	 */
	public boolean bigThan(AbstractState sta) {
		// TODO Auto-generated method stub
		if(this.getTypeString() != sta.getTypeString())
			return false;
		for(int i = 0; i < sta.fromsTrans.size(); i ++){
			if(!this.hasTheSameFromTransition(sta.fromsTrans.elementAt(i)))
				return false;
		}
		for(int i = 0; i < sta.tosTrans.size(); i ++){
			if(!this.hasTheSameToTransition(sta.tosTrans.elementAt(i)))
				return false;
		}
		return true;
	}

	abstract public String getTypeString(); 
	
	public boolean hasTheSameFromTransition(Transition tr){
		Iterator<Transition> tit = this.fromsTrans.iterator();
		while(tit.hasNext()){
			Transition tran = tit.next();
			if(tran.theSameInputsAs(tr))
				return true;
		}
		return false;
	}
	
	public boolean hasTheSameToTransition(Transition tr){
		Iterator<Transition> tit = this.tosTrans.iterator();
		while(tit.hasNext()){
			Transition tran = tit.next();
			if(tran.theSameInputsAs(tr))
				return true;
		}
		return false;
	}
}

class StartState extends AbstractState {
	protected StartState(int id) {
		super(id);
	}
	
	protected StartState() {}
	
	public boolean IsStart(){
		return true;
	}

	@Override
	public String getTypeString() {
		// TODO Auto-generated method stub
		return "Start";
	}
}

class FinishState extends AbstractState {
	protected FinishState(int id) {
		super(id);
	}
	
	protected FinishState() {}
	
	public boolean IsFinish() {
		return true;
	}

	@Override
	public String getTypeString() {
		// TODO Auto-generated method stub
		return "Finish";
	}
}

class CommonState extends AbstractState {
	protected CommonState(int id) {
		super(id);
	}
	protected CommonState() {}
	@Override
	public String getTypeString() {
		// TODO Auto-generated method stub
		return "Common";
	}
}

class StartFinishState extends AbstractState {
	protected StartFinishState(int id) {
		super(id);
	}
	
	protected StartFinishState() {}
	
	public boolean IsStart(){
		return true;
	}
	
	public boolean IsFinish() {
		return true;
	}

	@Override
	public String getTypeString() {
		// TODO Auto-generated method stub
		return "StartFinish";
	}
}