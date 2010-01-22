/**
 * 
 */
package cn.edu.thss.retools;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * 自动机中的迁移
 * @author Huhao lifengxiang dengshuang
 *
 */
public class Transition {
	
	private AbstractState from;
	private AbstractState to;
	private Set<Character> inputs; 
	
	/**
	 * @return the from
	 */
	public AbstractState getFromState() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFromState(AbstractState from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public AbstractState getToState() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setToState(AbstractState to) {
		this.to = to;
	}

	/**
	 * @return the inputs
	 */
	public Set<Character> getInputs() {
		return inputs;
	}

	/**
	 * @param inputs the inputs to set
	 */
	public void setInputs(Set<Character> inputs) {
		this.inputs = inputs;
	}

	public static Transition CreateEpsilonFrom(AbstractState from) {
		Transition ts = new Transition(from, AbstractState.CreateCommon());
		ts.AddEpsilonCondition();
		return ts;
	}
	
	public static Transition CreateEpsilonTo(AbstractState to) {
		Transition ts = new Transition(AbstractState.CreateCommon(), to);
		ts.AddEpsilonCondition();
		return ts;
	}
	
	public static Transition CreateEpsilon(AbstractState from, AbstractState to) {
		Transition ts = new Transition(from, to);
		ts.AddEpsilonCondition();
		return ts;
	}
	
	public Transition() {
		init(null, null, null);
	}
	
	public Transition(AbstractState from, AbstractState to) {
		init(from, to, null);
	}
	
	public Transition(AbstractState from, AbstractState to, Set<Character> inputs) {
		init(from, to, inputs);
	}
	
	public Transition(AbstractState from, AbstractState to, char ch){
		init(from, to, null);
		inputs.add(Character.valueOf(ch));
	}
	
	public void init(AbstractState from, AbstractState to, Set<Character> inputs) {
		this.from = from == null ? AbstractState.CreateCommon() : from;
		this.to = to == null ? AbstractState.CreateCommon() : to;
		this.inputs = inputs == null ? new HashSet<Character>() : inputs;
	}
	
	public void AddEpsilonCondition() {
		inputs.add(Character.valueOf(InputSequence.epsilon));
	}
	
	public void AddCondition(char ch) {
		inputs.add(Character.valueOf(ch)); 
	}
	
	/**
	 * 加入所有支持的字符
	 * @param ch
	 */
	public void AddAllCondition(char ch){
		if(ch == InputSequence.anything){
			inputs.addAll(InputSequence.acceptChars);
			inputs.addAll(InputSequence.metaChars);		//don't forget
		} else {
			inputs.add(Character.valueOf(ch));
		}
	}
	
	/**
	 * 判断当前字符是否为该迁移的某个条件
	 * @param ch
	 * @return
	 */
	public boolean IsConditionExist(char ch) {
		//return inputs.contains(Character.valueOf(InputSequence.anything)) || inputs.contains(Character.valueOf(ch));
		return inputs.contains(Character.valueOf(ch));
	}
	
	public boolean ConstainsEpsilon(){
		return inputs.contains(Character.valueOf(InputSequence.epsilon));
	}
	
	public String toString() {
		return from.ID + to.ID;
	}
	
	public void Combine(Transition tran) throws TransitionNotMatchException{
		if(this.from == tran.from && this.to == tran.to){
			this.inputs.addAll(tran.inputs);
		} else {
			throw new TransitionNotMatchException("不能合并起止节点不一致的跃迁！");
		}
	}
	
	public void PrintTransition(){
		Iterator<Character> iter = inputs.iterator();
		while(iter.hasNext()) {
			if(from.IsStart()){
				if(from.IsFinish())
					System.out.print("From StartFinish\t");
				else
					System.out.print("From Start\t");
			} else if(from.IsFinish())
				System.out.print("From Finish\t");
			else 
				System.out.print("From Common\t");
			System.out.print(from.ID + " " + iter.next() + " " + to.ID + " ");
			if(to.IsStart())
				System.out.println("To Start");
			else if(to.IsFinish())
				System.out.println("To Finish");
			else 
				System.out.println("To Common");
		}
	}
	
	public void PrintTransitionReverse(){
		Iterator<Character> iter = inputs.iterator();
		while(iter.hasNext()) {
			if(to.IsStart())
				System.out.print("From Start\t");
			else if(to.IsFinish())
				System.out.print("From Finish\t");
			else 
				System.out.print("From Common\t");
			
			System.out.print(to.ID + " " + iter.next() + " " + from.ID + " ");
			
			if(from.IsStart()){
				if(from.IsFinish())
					System.out.println("to StartFinish\t");
				else
					System.out.println("to Start\t");
			} else if(from.IsFinish())
				System.out.println("to Finish\t");
			else 
				System.out.println("to Common\t");			
		}
	}

	/**
	 * 忽略大小写
	 */
	public void IgnoreCase() {
		// TODO Auto-generated method stub
		Iterator<Character> inIt = inputs.iterator();
		Vector<Character> caseVec = new Vector<Character>();
		while(inIt.hasNext()){
			char sym = InputSequence.getSymCase(inIt.next().charValue());
			Character character = Character.valueOf(sym);
			if(!inputs.contains(character))
				caseVec.add(character);
		}
		inputs.addAll(caseVec);
	}

	/**
	 * 判断两个Transition是否有相同的输入
	 * 已经废弃
	 * @param tr
	 * @return
	 */
	public boolean theSameInputsAs(Transition tr) {
		// TODO Auto-generated method stub
		if(!this.inputs.containsAll(tr.inputs))
			return false;
		if(tr.from.getTypeString() != this.from.getTypeString() 
			|| tr.to.getTypeString() != this.to.getTypeString())
			return false;
		
		return true;
	}
}