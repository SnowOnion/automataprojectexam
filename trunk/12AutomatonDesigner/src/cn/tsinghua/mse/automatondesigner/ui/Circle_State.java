/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.swt.graphics.Point;

import cn.tsinghua.mse.automatondesigner.dataobject.State;

/**
 * @author David
 *
 */
public class Circle_State {
	public static final int DEFAULTRADIUS = 20;
	
	private State m_State;
	private Point centre;
	private int radius;

	public Circle_State()
	{
		m_State = null;
		centre = null;
		radius = DEFAULTRADIUS;
	}
	
	public Circle_State(State s, Point p)
	{
		m_State = s;
		centre = p;
		radius = DEFAULTRADIUS;
	}
	
	public State getM_State() {
		return m_State;
	}

	public void setM_State(State mState) {
		m_State = mState;
	}

	public Point getCentre() {
		return centre;
	}

	public void setCentre(Point centre) {
		this.centre = centre;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

}
