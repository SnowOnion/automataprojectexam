/**
 * 
 */
package cn.edu.thss.retools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

/**
 * 自动机的模型
 * @author Huhao lifengxiang dengshuang
 *
 */
public class Automata {
	
	private Map<String, AbstractState> states;	//保存当前自动机的所有状态
	private Map<String, Transition> transitions;	//保存当前自动机的所有迁移
	private AbstractState start;		//自动机的起始状态，唯一
	private Vector<String> finishes;	//自动机的结束状态在Map中的索引key
	private NodeEdge root;		//构造自动机的初始节点边
//	private Stack<NodeEdge> splitStack;
	private List<NodeEdge> splitQueue;
	private int caseSensitive = InputSequence.NO_IGNORE_CASE; 
	
	public AbstractState getStart(){
		return start;
	}
	
	public Vector<AbstractState> getFinishes(){
		Vector<AbstractState> fies = new Vector<AbstractState>();
		Iterator<String> fit = finishes.iterator();
		while(fit.hasNext()){
			fies.add(states.get(fit.next()));
		}
		return fies;
	}
	
	private void init() {
		states = new HashMap<String, AbstractState>();
		transitions = new HashMap<String, Transition>();
		finishes = new Vector<String>();
		start = AbstractState.CreateStart();
		AbstractState finish = AbstractState.CreateFinish();
		states.put(start.ID, start);
		states.put(finish.ID, finish);
		finishes.add(finish.ID);
		root = null;
//		splitStack = new Stack<NodeEdge>();
		splitQueue = new ArrayList<NodeEdge>();
		
	}
	
	protected Automata() {
		init();	
	}
	
	public Automata(RENode ne, int caseSensitive){		
		init();
		this.caseSensitive = caseSensitive;
		root = new NodeEdge(ne, start, states.get(finishes.get(0)));
//		splitStack.push(root);
		splitQueue.add(root);
		this.Convert2NFA();
	}
	
	public Automata(Map<String, AbstractState> states, Map<String, Transition> transitions) {
		this.states = states;
		this.transitions = transitions;
		finishes = new Vector<String>();
		Iterator<AbstractState> iter = states.values().iterator();
		while(iter.hasNext()){
			AbstractState st = iter.next();
			if(st.IsStart()){
				start = st;
			} else if(st.IsFinish()){
				finishes.add(st.ID);
			}
		}
		root = null;
//		splitStack = new Stack<NodeEdge>();
		splitQueue = new ArrayList<NodeEdge>();
	}
	
	/**
	 *控制NodeEdge的分裂，从而构造NFA
	 */
	public void Convert2NFA(){
//		while(!splitStack.isEmpty()) {
		while(!splitQueue.isEmpty()){
//			NodeEdge top = splitStack.pop();
			NodeEdge top = splitQueue.remove(0);
			if(!top.CanSplit()) {
				this.AppendTransiton(top.generateTransition());
			} else {
				try {
					top.Split(this);
				} catch (REFormatException e) {
					// TODO Auto-generated catch block
					top.node.PrintNodeWithAttr(0);
					e.printStackTrace();
				}
			}
		}
		this.IgnoreCase();	//根据设置忽略大小写
	}
	
	/**
	 * 将NFA转化成一个新的DFA
	 * @return
	 * @throws TransitionNotMatchException
	 */
	public Automata Convert2DFA() throws TransitionNotMatchException {
		Map<Set<AbstractState>, AbstractState> newStates = new HashMap<Set<AbstractState>, AbstractState>();
		Queue<Set<AbstractState> > stateForDeal = new LinkedList<Set<AbstractState> >();
		Queue<Set<AbstractState> > stateDealed = new LinkedList<Set<AbstractState> >();
		Map<String, Transition> newTransitions = new HashMap<String, Transition>();
		
		Set<AbstractState> startClosure = this.EpsilonClosureOfState(start);
		Set<AbstractState> epsilonStart = this.EpsilonClosureOfStates(startClosure);
		if(this.ContainsFinish(epsilonStart)){	//be careful, some state maybe both start and finish
			newStates.put(epsilonStart, AbstractState.CreateStartFinish());
		} else {
			newStates.put(epsilonStart, AbstractState.CreateStart());
		}
		stateForDeal.offer(epsilonStart);
//		stateDealed.offer(epsilonStart);
		Set<AbstractState> tos = new LinkedHashSet<AbstractState>();
		while(!stateForDeal.isEmpty()) {
			
			Set<AbstractState> first = stateForDeal.poll();
			Set<Character> inChars = this.AllInputsOfStates(first);			
			Iterator<Character> inIter = inChars.iterator();
			while(inIter.hasNext()){
				tos.clear();
				char inc = inIter.next().charValue();
				Iterator<AbstractState> stIter = first.iterator();
				while(stIter.hasNext()) {
					AbstractState item = stIter.next();
					tos.addAll(item.StatesToOnCondition(inc));
				}
				//now all the states from first states on input "inc" have gotten
				Set<AbstractState> tosClosure = this.EpsilonClosureOfStates(tos);
				if(!(stateForDeal.contains(tosClosure) || stateDealed.contains(tosClosure))){
					stateForDeal.add(tosClosure);
					if(!newStates.containsKey(tosClosure))	{//be careful!
						if(this.ContainsFinish(tosClosure)){
								newStates.put(tosClosure, AbstractState.CreateFinish());
						} else {
							newStates.put(tosClosure, AbstractState.CreateCommon());
						}
					}
				}
				AbstractState from = newStates.get(first);
				AbstractState to = newStates.get(tosClosure);
				Transition newTrans = new Transition(from, to, inc);
				if(newTransitions.containsKey(newTrans.toString())){
					newTransitions.get(newTrans.toString()).Combine(newTrans);
				} else {
					newTransitions.put(newTrans.toString(), newTrans);
					from.AddToTransition(newTrans);		//don't forget
					to.AddFromTransition(newTrans);
				}
			}
			stateDealed.add(first);
		}
		
		Map<String, AbstractState> newStatesMap = new HashMap<String, AbstractState>();
		Iterator<AbstractState> newStateIter = newStates.values().iterator();
		while(newStateIter.hasNext()){
			AbstractState st = newStateIter.next();
			newStatesMap.put(st.ID, st);
		}
		
		Automata newAuto =  new Automata(newStatesMap, newTransitions);
		newAuto.RepaireParentRelation();
		newAuto.setCaseSentive(this.caseSensitive);
		
		return newAuto;		
	}
	
	private void setCaseSentive(int caseSensitive) {
		// TODO Auto-generated method stub
		this.caseSensitive = caseSensitive;
	}

	private void IgnoreCase() {
		// TODO Auto-generated method stub
		if(this.caseSensitive == InputSequence.IGNORE_CASE){
			Iterator<Transition> tranIt = transitions.values().iterator();
			while(tranIt.hasNext()){
				tranIt.next().IgnoreCase();
			}
		}
	}

	private Set<Character> AllInputsOfStates(Set<AbstractState> stats) {
		Set<Character> inChars = new LinkedHashSet<Character>();
		Iterator<AbstractState> iter = stats.iterator();
		while(iter.hasNext()) {
			inChars.addAll(iter.next().AllInputs());
		}
		//epsilon is not input char, but exist in transition
		inChars.remove(Character.valueOf(InputSequence.epsilon)); 
		return inChars;
	}
	
//	private boolean Appears(Set<AbstractState> stats, Queue<Set<AbstractState> > qu1, Queue<Set<AbstractState> > qu2) {
//		Iterator<Set<AbstractState> > iter = qu1.iterator();
//		while(iter.hasNext()) {
//			if(stats.equals(iter.next()))
//				return true;
//		}
//		iter = qu2.iterator();
//		while(iter.hasNext()) {
//			if(stats.equals(iter.next()))
//				return true;
//		}
//		return false;
//	}
	
	private boolean ContainsFinish(Set<AbstractState> sts) {
		Iterator<AbstractState> iter = sts.iterator();
		while(iter.hasNext()) {
			if(iter.next().IsFinish()) 
				return true;
		}
		return false;
	}

	private Set<AbstractState> EpsilonClosureOfStates(Set<AbstractState> stats){
		return this.ClosureOfStates(stats, InputSequence.epsilon);
	}
	
	private Set<AbstractState> ClosureOfStates(Set<AbstractState> stats, char ch) {
		Set<AbstractState> sets = new LinkedHashSet<AbstractState>();
		Set<AbstractState> set;
		Iterator<AbstractState> iter = stats.iterator();
		while(iter.hasNext()) {
			set = this.ClosureOfState(iter.next(), ch);
			sets.addAll(set);
		}
		return sets;
	}
	
	private Set<AbstractState> EpsilonClosureOfState(AbstractState st){
		return this.ClosureOfState(st, InputSequence.epsilon);
	}
	
	private Set<AbstractState> ClosureOfState(AbstractState st, char ch) {
		Set<AbstractState> sets = new LinkedHashSet<AbstractState>();
		Queue<AbstractState> queue = new LinkedList<AbstractState>();
		queue.offer(st);
		sets.add(st);
		while(!queue.isEmpty()) {
			AbstractState first = queue.poll();						
			Iterator<Transition> iter = first.tosTrans.iterator();
			while(iter.hasNext()) {
				Transition tr = iter.next();
				if(tr.IsConditionExist(ch) && !sets.contains(tr.getToState())){
					sets.add(tr.getToState());
					queue.offer(tr.getToState());
				}
			}			
		}
		return sets;		
	}
	
//	public void PushNodeEdge(NodeEdge ne) {
//		splitStack.push(ne);
//	}
	
	public void addToQueue(NodeEdge ne){
		splitQueue.add(ne);
	}
	public void AddState(AbstractState st) {
		states.put(st.ID, st);
		Iterator<Transition> iter = st.tosTrans.iterator();
		while(iter.hasNext()) {
			Transition ts = iter.next();
			if(!transitions.containsKey(ts.toString()))
				transitions.put(ts.toString(), ts);
		}
	}
	
	public void AppendTransiton(Transition ts) {
		if(!transitions.containsKey(ts.toString())){
			transitions.put(ts.toString(), ts);
		} else {
			Transition ob = transitions.get(ts.toString());
			Iterator<Character> iter = ts.getInputs().iterator();//ts.inputs.iterator();
			while(iter.hasNext()) {
				ob.AddCondition(iter.next().charValue());
			}
		}
		ts.getFromState().AddToTransition(ts);	//Don't forget
		ts.getToState().AddFromTransition(ts);
	}
	
	public void AddTransition(Transition ts) {
		if(!transitions.containsKey(ts.toString()))
			transitions.put(ts.toString(), ts);
		ts.getFromState().AddToTransition(ts);
		ts.getToState().AddFromTransition(ts);
		if(!states.containsValue(ts.getFromState()))
			states.put(ts.getFromState().ID, ts.getFromState());
		if(!states.containsValue(ts.getToState()))
			states.put(ts.getToState().ID, ts.getToState());
	}
	
	public void PrintNode() {
		Stack<AbstractState> stack = new Stack<AbstractState>();
		Set<AbstractState> sets = new LinkedHashSet<AbstractState>();
		sets.add(start);
		stack.push(start);
		while(!stack.isEmpty()) {
			AbstractState top = stack.pop();
			Iterator<Transition> iter = top.tosTrans.iterator();
			while(iter.hasNext()) {
				Transition ts = iter.next();
				if(!sets.contains(ts.getToState())){
					stack.push(ts.getToState());
					sets.add(ts.getToState());
				}	
				ts.PrintTransition();
			}
		}
//		Iterator<Transition> iter = transitions.values().iterator();
//		while(iter.hasNext()) {
//			iter.next().PrintTransition();
//		}
	}	
	
	public void PrintNodeReverse() {
		Stack<AbstractState> stack = new Stack<AbstractState>();
		Set<AbstractState> sets = new LinkedHashSet<AbstractState>();
		Vector<AbstractState> fis = this.getFinishes();
		Iterator<AbstractState> fit = fis.iterator();
		while(fit.hasNext()){
			AbstractState fi = fit.next();
			sets.add(fi);
			stack.push(fi);
		}
		while(!stack.isEmpty()) {
			AbstractState top = stack.pop();
			Iterator<Transition> iter = top.fromsTrans.iterator();
			while(iter.hasNext()) {
				Transition ts = iter.next();
				if(!sets.contains(ts.getFromState())){
					stack.push(ts.getFromState());
					sets.add(ts.getFromState());
				}	
				ts.PrintTransitionReverse();
			}
		}
	}	
	
	public void RepaireParentRelation(){
		Queue<AbstractState> queue = new LinkedList<AbstractState>();
		Set<AbstractState> sets = new LinkedHashSet<AbstractState>();
		sets.add(start);
		queue.add(start);
		while(!queue.isEmpty()) {
			AbstractState top = queue.poll();
			Iterator<Transition> iter = top.tosTrans.iterator();
			while(iter.hasNext()) {
				Transition ts = iter.next();
				if(!sets.contains(ts.getToState())){
					queue.add(ts.getToState());
					sets.add(ts.getToState());
				}	
				ts.getFromState().AddToTransition(ts);
				ts.getToState().AddFromTransition(ts);
//				ts.PrintTransition();
			}
		}
	}

	/**
	 * 获得一个对等的结束状态
	 * 已经废弃
	 * @param finish
	 * @return
	 */
	public AbstractState getSepcialFinish(AbstractState finish) {
		// TODO Auto-generated method stub
		Vector<AbstractState> fies = this.getFinishes();
		Iterator<AbstractState> fit = fies.iterator();
		while(fit.hasNext()){
			AbstractState fi = fit.next();
			if(finish.bigThan(fi))
				return fi;
		}
		return null;
	}
}