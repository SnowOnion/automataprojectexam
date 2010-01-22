/**
 * 
 */
package cn.edu.thss.retools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 用来构造自动机的节点边
 * @author Huhao lifengxiang dengshuang
 *
 */
public class NodeEdge {
	public AbstractState from;	
	public AbstractState to;
	public RENode node;
	public boolean canSplit; //标记某个节点是否去掉属性，用于节点复制，比如(a+)+
	
	protected NodeEdge() { }
	
	public NodeEdge(RENode node, AbstractState start, AbstractState finish) {
		this.init(node, start, finish);		
	}
	
	public void init(RENode node, AbstractState start, AbstractState finish) {
		from = start;
		to = finish;
		this.node = node;
		canSplit = true;
	}
	
	/**
	 * 判断当前的字表达式是否可以分裂
	 * @return
	 */
	public boolean CanSplit() {
		return !node.IsTerminal() || (canSplit && (node.type == RENode.SELECT_STAR_CLOSURE_TYPE 
			|| node.type == RENode.SELECT_Question_CLOSURE_TYPE || node.type == RENode.SELECT_POSITIVE_CLOSURE_TYPE 
			|| node.type == RENode.STAR_CLOSURE_TYPE || node.type == RENode.Question_CLOSURE_TYPE
			|| node.type == RENode.POSITIVE_CLOSURE_TYPE));
	}
	
	public void RemoveAttribute(){
		canSplit = false;
	}
	
	/**
	 * 对当前的节点边进行分裂
	 * @param mach
	 * @throws REFormatException
	 */
	public void Split(Automata mach) throws REFormatException {
		int type = node.getType();
		int childrenCount = node.CountOfChildren();
		Iterator<RENode> iter = node.children.iterator();
		AbstractState start, end, mid;
		Transition fe, se;
		switch(type) {
			case RENode.REVERSE_SELECT_TYPE:				
					Set<Character> inv = new LinkedHashSet<Character>();
					Iterator<RENode> ri = node.children.iterator();
					while(ri.hasNext()){
						RENode no = ri.next();
						if(no.IsTerminal()){
							inv.add(Character.valueOf(no.getLabel().charAt(0)));
						}else{
							throw new REFormatException("D : non Terminal");
						}
					}
					ArrayList<RENode> newChild = new ArrayList<RENode>();
					Iterator<Character> inputIt = InputSequence.acceptChars.iterator();
					while(inputIt.hasNext()){
						Character ch = inputIt.next();
						if(!inv.contains(ch)){
							newChild.add(new AlphaNode(String.valueOf(ch)));
						}
					}
					node.children = newChild; 
					node.RepaireParentRelation();
					node.setType(RENode.SELECT_TYPE);
					
					iter = node.children.iterator();
					//no break;
			case RENode.SELECT_TYPE :
			case RENode.OR_TYPE :
				while(iter.hasNext()) {
					RENode rn = iter.next();
					NodeEdge ne = new NodeEdge(rn, from, to);
//					mach.PushNodeEdge(ne);
					mach.addToQueue(ne);
				}				
				break;
			case RENode.LINK_TYPE :
				start = from;
				for(int i = 0; i < childrenCount - 1; i ++) {
					end = AbstractState.CreateCommon();
					mach.AddState(end);
//					mach.PushNodeEdge(new NodeEdge(node.ChildAt(i), start, end ));
					mach.addToQueue(new NodeEdge(node.ChildAt(i), start, end ));
					start = end;
				}
//				mach.PushNodeEdge(new NodeEdge(node.ChildAt(childrenCount - 1), start, to ));
				mach.addToQueue(new NodeEdge(node.ChildAt(childrenCount - 1), start, to ));
				break;	
			case RENode.SELECT_STAR_CLOSURE_TYPE :
			case RENode.STAR_CLOSURE_TYPE :
				mid = AbstractState.CreateCommon();
				fe = Transition.CreateEpsilon(from, mid);
				se = Transition.CreateEpsilon(mid, to);
				from.AddToTransition(fe);
				mid.AddFromTransition(fe);				
				mid.AddToTransition(se);	
				to.AddFromTransition(se);
				
				mach.AddTransition(fe);
				mach.AddTransition(se);
				if(!node.IsTerminal()) {
					while(iter.hasNext()) {
						RENode rn = iter.next();
//						mach.PushNodeEdge(new NodeEdge(rn, mid, mid));
						mach.addToQueue(new NodeEdge(rn, mid, mid));
					}			
				} else {
//					node.ClearType();
					NodeEdge ne = new NodeEdge(node, mid, mid);
					ne.RemoveAttribute();
//					mach.PushNodeEdge(ne);
					mach.addToQueue(ne);
				}
				break;
			case RENode.SELECT_Question_CLOSURE_TYPE :			
				if(!node.IsTerminal()){
					while(iter.hasNext()) {
						RENode rn = iter.next();
						NodeEdge ne = new NodeEdge(rn, from, to);
//						mach.PushNodeEdge(ne);
						mach.addToQueue(ne);
					}		
					mach.AddTransition(Transition.CreateEpsilon(from, to));
				} else {
//					node.ClearType();
					NodeEdge ne = new NodeEdge(node, from, to);
					ne.RemoveAttribute();
//					mach.PushNodeEdge(ne);
					mach.addToQueue(ne);
					mach.AddTransition(Transition.CreateEpsilon(from, to));
				}
				break;
			case RENode.Question_CLOSURE_TYPE :	//()?
				if(!node.IsTerminal()){
//					if(node.CountOfChildren() == 1){//be careful
//						RENode rn = iter.next();
//						NodeEdge ne = new NodeEdge(rn, from, from);
//						mach.PushNodeEdge(ne);
//					} else {
						while(iter.hasNext()) {
							RENode rn = iter.next();
							NodeEdge ne = new NodeEdge(rn, from, to);
//							mach.PushNodeEdge(ne);
							mach.addToQueue(ne);
						}
//					}
					mach.AddTransition(Transition.CreateEpsilon(from, to));
				} else {
//					node.ClearType();
					NodeEdge ne = new NodeEdge(node, from, to);
					ne.RemoveAttribute();
//					mach.PushNodeEdge(ne);
					mach.addToQueue(ne);
					mach.AddTransition(Transition.CreateEpsilon(from, to));
				}
				break;
			case RENode.POSITIVE_CLOSURE_TYPE :
			case RENode.SELECT_POSITIVE_CLOSURE_TYPE :			
				mid = AbstractState.CreateCommon();
				fe = Transition.CreateEpsilon(mid, to);
				mid.AddToTransition(fe);				
				mach.AddState(mid);
				mach.AddTransition(fe);
				if(!node.IsTerminal()){
					while(iter.hasNext()) {
						RENode rn = iter.next();
//						mach.PushNodeEdge(new NodeEdge(rn, from, mid));
//						mach.PushNodeEdge(new NodeEdge(rn, mid, mid));
						
						mach.addToQueue(new NodeEdge(rn, from, mid));
			 			mach.addToQueue(new NodeEdge(rn, mid, mid));
					}		
				} else {
//					node.ClearType();
					NodeEdge ne1 = new NodeEdge(node, from, mid);
					NodeEdge ne2 = new NodeEdge(node, mid, mid);
					ne1.RemoveAttribute();
					ne2.RemoveAttribute();
//					mach.PushNodeEdge(ne1);
//					mach.PushNodeEdge(ne2);
					mach.addToQueue(ne1);
					mach.addToQueue(ne2);
				}					
				break;
			default :
				break;
		}
	}
	
	/**
	 * 生成 迁移规则
	 * @return
	 */
	public Transition generateTransition(){
		Transition ts = new Transition(from, to);
		if(node.IsMeta())	//区分元数据
			ts.AddAllCondition(node.getLabel().charAt(0));
		else
			ts.AddCondition(node.getLabel().charAt(0));
		return ts;
	}
	
}
