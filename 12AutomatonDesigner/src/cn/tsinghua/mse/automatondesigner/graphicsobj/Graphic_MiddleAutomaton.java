package cn.tsinghua.mse.automatondesigner.graphicsobj;

import java.util.ArrayList;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;

/**
 * 本类用作XML导出的转存
 * @author David
 *
 */
public class Graphic_MiddleAutomaton {

	private ArrayList<Circle_State> cStates = null;
	public ArrayList<Circle_State> getcStates() {
		return cStates;
	}

	public void setcStates(ArrayList<Circle_State> cStates) {
		this.cStates = cStates;
	}

	public ArrayList<Polyline_Trans> getpTrans() {
		return pTrans;
	}

	public void setpTrans(ArrayList<Polyline_Trans> pTrans) {
		this.pTrans = pTrans;
	}

	public Automaton getAutomaton() {
		return automaton;
	}

	public void setAutomaton(Automaton automaton) {
		this.automaton = automaton;
	}

	private ArrayList<Polyline_Trans> pTrans = null;
	private Automaton automaton = null;
	
	/**
	 * 构造函数
	 * @param a 自动机
	 * @param pts	canvas上的转移函数集合
	 * @param css	canvas上的状态集合
	 */
	public Graphic_MiddleAutomaton(Automaton a, ArrayList<Polyline_Trans> pts, ArrayList<Circle_State> css){
		automaton = a;
		pTrans = pts;
		cStates = css;
	}
	
	public Circle_State getCStateByName(String sname){
		for (Circle_State c : cStates){
			if (c.getM_State().getM_Name().equals(sname))
				return c;
		}
		return null;
	}
}
