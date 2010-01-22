/**
 * 
 */
package cn.edu.thss.retools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * 对模式的匹配，所有匹配的抽象基类
 * @author Huhao lifengxiang dengshuang
 *
 */
public abstract class Matcher {
	protected Pattern pattern;
	protected InputSequence inputs;
	protected AbstractState start;
	protected AbstractState curState;
	protected AbstractState finish;

	protected Matcher(){
	}
	
	
	public Matcher(Pattern pattern, InputSequence inputs){
		this.pattern = pattern;
		this.inputs = inputs;
		this.start = pattern.getStart();
		this.curState = start;
	}
	
	public abstract boolean Matches();	


	public String GetInputs() {
		// TODO Auto-generated method stub
		return inputs.GetAllInputs();
	}

	abstract public Vector<String> GetMatchs() throws REFormatException, TransitionNotMatchException, ParameterNotMatchException;
	
}

/**
 * 完全匹配
 * @author Huhao
 *
 */
class FullMatcher extends Matcher {
	
	protected FullMatcher(){}
	
	public FullMatcher(Pattern pattern, InputSequence inputs){
		super(pattern, inputs);
	}
	
	public boolean Matches(){
		return MatchesToEnd();
	}	
	
	/*
	 * 一直匹配到inputs 结束
	 */
	private boolean MatchesToEnd(){
		
		ArrayList<AbstractState> tos = new ArrayList<AbstractState>();
		curState = start;
//		inputs.Reset();
		do{
			char ch = inputs.Getc();
			tos = curState.MoveOn(ch);
			if(tos.isEmpty()){
				inputs.Ungetc();
				curState = null;
				break;
			} else {
				curState = tos.get(0);
			}
			tos.clear();
		}while(!inputs.IsEmpty());
		
		if(curState != null && curState.IsFinish()){
			finish = curState;
			return true;
		}
		return false;
	}
	
	/**
	 * 找出匹配的字符串，默认实现，只适用于完全匹配的情况（即从字符串开始到结束一直匹配）
	 */
	public Vector<String> GetMatchs() throws REFormatException, TransitionNotMatchException, ParameterNotMatchException{
		// TODO Auto-generated method stub
		if(finish == null)
			return new Vector<String>();
		Vector<String> matches = new Vector<String>();
		ArrayList<AbstractState> froms = new ArrayList<AbstractState>();	
		List<InstDes> ids = new ArrayList<InstDes>();
		InputSequence accinputs = inputs.reverseByIndex();		
		ids.add(new InstDes(finish, accinputs));
		
		while(!ids.isEmpty()){
			froms.clear();
			InstDes id = ids.remove(0);//ids.pop();
			curState = id.state;
			InputSequence in = id.inputs;
			
			if(curState.IsStart() && !in.GetAllConsumed().equals("")){
				String consumed = in.reverseConsumed();
				matches.add(consumed);
			}
			
			if(in.IsEmpty())
				continue;
			
			char ch = in.Getc();
			froms = curState.MoveBackOn(ch);
			
			for(int i = 0; i < froms.size(); i ++){
				ids.add(new InstDes(froms.get(i), (InputSequence)in.clone()));
			}			
		}		
		return matches;
	}
	
}

/**
 * 最短部分匹配
 * @author Huhao
 *
 */
class PartShorestMatcher extends Matcher {
	
	protected PartShorestMatcher() {}
	
	public PartShorestMatcher(Pattern pattern, InputSequence inputs){
		super(pattern,inputs);
	} 
	
	public boolean Matches(){
		return MatchesAnyShortest();
	}
	
	/**
	 * 匹配到出现输入模式，无论输入是否结束，且为最短匹配
	 * @return
	 */
	public boolean MatchesAnyShortest(){
		if(inputs.IsEmpty())
			return false;
		
		ArrayList<AbstractState> tos = new ArrayList<AbstractState>();		
		
		curState = start;
//		inputs.Reset();
		do{
			char ch = inputs.Getc();
			tos = curState.MoveOn(ch);
			if(tos.isEmpty()){
				inputs.Ungetc();
				curState = null;
				break;
			} else {
				curState = tos.get(0);
			}
			tos.clear();
		}while(!inputs.IsEmpty() && !curState.IsFinish());
		
		if(curState != null && curState.IsFinish()){
			finish = curState;
			return true;
		}
		return false;
	}
	
	/**
	 * 创建了一个专门匹配用的自动机
	 */
	public Vector<String> GetMatchs() throws REFormatException, TransitionNotMatchException, ParameterNotMatchException {
		// TODO Auto-generated method stub
		if(finish == null)
			return new Vector<String>();
		
		Automata fullDFA = this.pattern.Compile(pattern.getPatternString(), 
				new int[]{pattern.getbCaseSensitive(), InputSequence.FULL_MATCH});
		
		Vector<String> matches = new Vector<String>();		
		Vector<AbstractState> fies = fullDFA.getFinishes();
		
		Iterator<AbstractState> fit = fies.iterator();
		while(fit.hasNext()){
			matches.addAll(this.GetMatch(fit.next()));
		}
		
		return matches;
	}
	
	private Vector<String> GetMatch(AbstractState finish){
		
		Stack<InstDes> ids = new Stack<InstDes>();		
		ArrayList<AbstractState> froms = new ArrayList<AbstractState>();
		Vector<String> matches = new Vector<String>();
		AbstractState topState = null;
		
		InputSequence accinputs = inputs.reverseByIndex();			
		ids.push(new InstDes(finish, accinputs));
		
		while(!ids.empty()){
			froms.clear();
			InstDes id = ids.pop();
			topState = id.state;
			InputSequence in = id.inputs;
			
			if(topState.IsStart() && in.GetAllConsumed() != ""){
				String consumed = in.reverseConsumed();
				matches.add(consumed);
			}
			
			if(in.IsEmpty())
				continue;
			
			char ch = in.Getc();
			froms = topState.MoveBackOn(ch);
			if(!froms.isEmpty()){
				for(int i = 0; i < froms.size(); i ++){
					ids.push(new InstDes(froms.get(i), (InputSequence)in.clone()));
				}
			}
		}		
		return matches;
	}
}

/**
 * 最长部分匹配
 * @author Huhao
 *
 */
class PartLongestMatcher extends Matcher {
	
	protected PartLongestMatcher() {}
	
	public PartLongestMatcher(Pattern pattern, InputSequence inputs){
		super(pattern,inputs);
	} 
	
	public boolean Matches(){
		return MatchesAnyLongest();
	}
	
	/**
	 * 匹配到出现输入模式，无论输入是否结束，且为最长匹配
	 * @return
	 */
	public boolean MatchesAnyLongest(){
		if(inputs.IsEmpty())
			return false;
		
		ArrayList<AbstractState> tos = new ArrayList<AbstractState>();		
		
		curState = start;
//		inputs.Reset();
		do{
			char ch = inputs.Getc();
			tos = curState.MoveOn(ch);
			if(tos.isEmpty()){
				curState = null;
				break;
			} else {
				curState = tos.get(0);
			}
			tos.clear();
		}while(!inputs.IsEmpty() && !curState.IsFinish());
		
		//匹配完成后继续匹配更长的
		while(!inputs.IsEmpty() && curState.IsFinish()){
			char ch = inputs.Getc();
			tos = curState.MoveOn(ch);
			if(tos.isEmpty()){
				inputs.Ungetc();
				break;
			} else{
				AbstractState next = tos.get(0);
				if(!next.IsFinish()){
					inputs.Ungetc();
					break;
				}					
				curState = next;					
			}			
		}
		
		if(curState != null && curState.IsFinish()){
			finish = curState;
			return true;
		}
		return false;
	}
	
	/**
	 * 创建了一个专门匹配用的自动机，来获取已经匹配的字符串
	 */
	public Vector<String> GetMatchs() throws REFormatException, TransitionNotMatchException, ParameterNotMatchException {
		// TODO Auto-generated method stub
		if(finish == null)
			return new Vector<String>();
		
		Automata fullDFA = this.pattern.Compile(pattern.getPatternString(), 
				new int[]{pattern.getbCaseSensitive(), InputSequence.FULL_MATCH});
		
		Vector<String> matches = new Vector<String>();		
		Vector<AbstractState> fies = fullDFA.getFinishes();
		
		Iterator<AbstractState> fit = fies.iterator();
		while(fit.hasNext()){
			matches.addAll(this.GetMatch(fit.next()));
		}
		
		return matches;
	}
	
	private Vector<String> GetMatch(AbstractState finish){
		
		Stack<InstDes> ids = new Stack<InstDes>();		
		ArrayList<AbstractState> froms = new ArrayList<AbstractState>();
		Vector<String> matches = new Vector<String>();
		AbstractState topState = null;
		
		InputSequence accinputs = inputs.reverseByIndex();			
		ids.push(new InstDes(finish, accinputs));
		
		while(!ids.empty()){
			froms.clear();
			InstDes id = ids.pop();
			topState = id.state;
			InputSequence in = id.inputs;
			
			if(topState.IsStart() && in.GetAllConsumed() != ""){
				String consumed = in.reverseConsumed();
				matches.add(consumed);
			}
			
			if(in.IsEmpty())
				continue;
			
			char ch = in.Getc();
			froms = topState.MoveBackOn(ch);
			if(!froms.isEmpty()){
				for(int i = 0; i < froms.size(); i ++){
					ids.push(new InstDes(froms.get(i), (InputSequence)in.clone()));
				}
			}
		}		
		return matches;
	}
}