package automata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * һ��״̬��������Ǩ�Ƽ��ϡ�
 * @author Leaf
 *
 */
public class Transitions {
//------------------------------------------------------------------------------
	/**
	 * ����µ�Ǩ�ơ�
	 * @param s Ǩ�ƶ���
	 */
	public void addTransition(Transition s) {
		trans.add(s);
		conditions.addAll(s.conditions());
	}
	
	// Remove an exist transition, and mark it as invalid.
	/**
	 * ɾ��Ǩ��
	 * @param s Ҫɾ����Ǩ�ƶ���
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
	 * �����Ƿ��������Ϊ c ��Ǩ�ơ�
	 * @param c Ǩ���������ַ���
	 * @return true�����ڣ�false�������ڡ�
	 */
	public boolean containsCond(String c) {
		return conditions.contains(c);
	}
	
	/**
	 * �������е�Ǩ��������
	 * @return Ǩ����������
	 */
	public HashSet<String> conditions() {
		return conditions;
	}
	
	// Return whether this set of transitions is empty.
	/**
	 * �жϱ�Ǩ�Ƽ����Ƿ�Ϊ�ա�
	 * @return true������Ϊ�գ�false�����Ϸǿա�
	 */
	public boolean isEmpty() {
		return trans.isEmpty();
	}
	
	
	// Remove all invalid transitions.
	/**
	 * ɾ��������ЧǨ�ơ�
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
	 * ���ٴ�Ǩ�Ƽ��ϡ�<br>
	 * ��Ǩ����������״̬��ɾ��ʱִ�д˺�����
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
	 * ���±�������������Ǩ��������
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
