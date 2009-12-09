/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.State;
import cn.tsinghua.mse.automatondesigner.interfaces.IPaint;

/**
 * @author David
 * 
 */
public class Circle_State implements IPaint {
	public static final int DEFAULTRADIUS = 20;
	public static final byte IMAGE_TYPE_COMMON = 1;
	public static final byte IMAGE_TYPE_SELECTED = 2;
	public static final byte IMAGE_TYPE_MOVING = 3;

	private static Long INDEXID = 0l;

	private State m_State;
	private Point centre;
	private Point originalCentre;
	public Point getOriginalCentre() {
		return originalCentre;
	}

	public void setOriginalCentre(Point originalCentre) {
		this.originalCentre = originalCentre;
	}

	private int radius;
	private String stateType;

	public String getStateType() {
		return stateType;
	}

	public Long getIndexID() {
		return INDEXID;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
	}

	public Circle_State() {
		INDEXID++;
		m_State = null;
		centre = null;
		originalCentre = null;
		radius = DEFAULTRADIUS;
		stateType = AutomatonConst.STATE_TYPE_COMMON;
	}

	public Circle_State(State s, Point p, String statetype) {
		m_State = s;
		INDEXID++;
		centre = new Point(p.x, p.y);
		originalCentre = new Point(p.x, p.y);
		stateType = statetype;
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

	public boolean checkPointIn(int x, int y) {
		double dist = Math.pow((x - centre.x), 2) + Math.pow((y - centre.y), 2);
		return dist <= Math.pow(radius/2, 2);
	}

	@Override
	public void paint(GC gc, byte type) {
		if (type == IMAGE_TYPE_SELECTED || type == IMAGE_TYPE_COMMON) {
			if (type == IMAGE_TYPE_SELECTED)
				gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
			else if (type == IMAGE_TYPE_COMMON)
				gc
						.setBackground(gc.getDevice().getSystemColor(
								SWT.COLOR_BLACK));
			gc.fillRoundRectangle(centre.x - radius / 2, centre.y - radius / 2,
					radius, radius, radius - 4, radius - 4);
			gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
			gc.fillRoundRectangle(centre.x - radius / 2 + 2, centre.y - radius
					/ 2 + 2, radius - 4, radius - 4, radius - 8, radius - 8);
		}
		else if (type == IMAGE_TYPE_MOVING){
			gc.drawRoundRectangle(centre.x - radius / 2, centre.y - radius / 2,
					radius, radius, radius - 4, radius - 4);
		}
	}

}
