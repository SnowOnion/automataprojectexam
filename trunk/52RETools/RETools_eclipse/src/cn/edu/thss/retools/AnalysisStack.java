/**
 * 
 */
package cn.edu.thss.retools;

import java.util.Stack;

/**
 * PDAµÄ·ÖÎöÕ»
 * @author Huhao
 *
 */
public class AnalysisStack {
	Stack<RENode> stack;
	public RENode root;
	
	public AnalysisStack() {
		stack = new Stack<RENode>();
//		stack.push(new AlphaNode("$"));
		stack.push(new MetaNode(String.valueOf(InputSequence.stackBottom)));
	}
	
	public void Push(RENode node) {
		stack.push(node);
	}
	
	public void PushRoot(RENode node) {
		root = node;
		Push(node);
	}
	
	public RENode Top() {
		return stack.peek();
	}
	
	public RENode Pop() {
		return stack.pop();
	}
	
	public boolean IsEmpty() {
		return stack.empty();
	}
}