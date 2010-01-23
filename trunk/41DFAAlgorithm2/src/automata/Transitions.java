package automata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 一个状态所关联的迁移集合。
 * @author Leaf
 *
 */
public class Transitions {
//------------------------------------------------------------------------------
	/**
	 * 添加新的迁移。
	 * @param s 迁移对象
	 */
	public void addTransition(Transition s) {
		trans.add(s);
		conditions.addAll(s.conditions());
	}
	
	// Remove an exist transition, and mark it as invalid.
	/**
	 * 删除迁移
	 * @param s 要删除的迁移对象
	 */
	public void removeTransition(Transition s) {
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			if (t == s) {
				t.dispose();
				it.remove();
			}
		}
		updateConditions();
	}
	
	// Return whether there exist a transition accepting 'c'.
	/**
	 * 返回是否存在条件为 c 的迁移。
	 * @param c 迁移条件（字符）
	 * @return true：存在；false：不存在。
	 */
	public boolean containsCond(String c) {
		return conditions.contains(c);
	}
	
	/**
	 * 返回所有的迁移条件。
	 * @return 迁移条件集合
	 */
	public HashSet<String> conditions() {
		return conditions;
	}
	
	// Return whether this set of transitions is empty.
	/**
	 * 判断本迁移集合是否为空。
	 * @return true：集合为空；false：集合非空。
	 */
	public boolean isEmpty() {
		return trans.isEmpty();
	}
	
	
	// Remove all invalid transitions.
	/**
	 * 删除所有无效迁移。
	 */
	public void cleanUp() {
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			if (!t.isValid()) {
				it.remove();
			}
		}
		updateConditions();
	}
	
	// Dispose all transitions within, probably for that the state owning these
	// transitions has been deleted.
	/**
	 * 销毁此迁移集合。<br>
	 * 当迁移所关联的状态被删除时执行此函数。
	 */
	public void dispose() {
		Transition t;
		Iterator<Transition> it = trans.iterator();
		while (it.hasNext()) {
			t = it.next();
			t.dispose();
		}
	}
	
	// Update the conditions when some transition is removed
	/**
	 * 更新本集合所包含的迁移条件。
	 */
	protected void updateConditions() {
		Iterator<Transition> t = trans.iterator();
		while (t.hasNext()) {
			conditions.addAll(t.next().conditions());
		}
	}
//------------------------------------------------------------------------------	
	protected LinkedList<Transition> trans = new LinkedList<Transition>();
	protected HashSet<String> conditions = new HashSet<String>();
}
