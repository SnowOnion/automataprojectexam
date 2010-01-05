/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.graphicsobj;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import automatondesigner.SystemConstant;

import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.State;
import cn.tsinghua.mse.automatondesigner.interfaces.IPaint;

/**
 * @author David
 * 
 */
public class Circle_State implements IPaint {
	//public static final byte IMAGE_TYPE_PARTSELECTED = 4;

	private static Long INDEXID = 0l;

	private State m_State;
	private Point centre;
	private Point originalCentre;
	private Text_ItemLable lable;

	public Text_ItemLable getLable() {
		return lable;
	}

	public void setLable(Text_ItemLable lable) {
		this.lable = lable;
	}

	public Point getOriginalCentre() {
		return originalCentre;
	}

	public void setOriginalCentre(Point originalCentre) {
		this.originalCentre = originalCentre;
	}

	private int radius;

	public Long getIndexID() {
		return INDEXID;
	}

	public Circle_State() {
		INDEXID++;
		m_State = null;
		centre = null;
		originalCentre = null;
		radius = SystemConstant.DEFAULTRADIUS;
	}

	public Circle_State(State s, Point p) {
		m_State = s;
		INDEXID++;
		centre = new Point(p.x, p.y);
		originalCentre = new Point(p.x, p.y);
		radius = SystemConstant.DEFAULTRADIUS;
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
		return dist <= Math.pow(radius / 2, 2);
	}

	@Override
	public void paint(GC gc, byte statue) {
		// if (type == IMAGE_TYPE_SELECTED || type == IMAGE_TYPE_COMMON ) {
		if (statue == SystemConstant.IMAGE_TYPE_SELECTED)
			gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
		else if (statue == SystemConstant.IMAGE_TYPE_COMMON)
			gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_BLUE));
		gc.fillRoundRectangle(centre.x - radius / 2, centre.y - radius / 2,
				radius, radius, radius - 4, radius - 4);
		gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
		gc.fillRoundRectangle(centre.x - radius / 2 + 2, centre.y - radius / 2
				+ 2, radius - 4, radius - 4, radius - 8, radius - 8);
		if ((m_State.getM_type()&AutomatonConst.STATE_INITIAL_TYPE)!= 0){
			paintISArrow(gc, statue==SystemConstant.IMAGE_TYPE_SELECTED);
		}
		if ((m_State.getM_type()&AutomatonConst.STATE_FINAL_TYPE)!= 0){
			paintFSAddition(gc, statue==SystemConstant.IMAGE_TYPE_SELECTED);
		}
		if (lable == null){
			lable = new Text_ItemLable(this);
		}
		lable.paint(gc, statue);
		//gc.drawString(m_State.getM_Name(), centre.x-6, centre.y-6, true);
		// if (type == IMAGE_TYPE_SELECTED)
		// gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
		// else if (type == IMAGE_TYPE_COMMON)
		// gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_CYAN));
		// gc.fillOval(centre.x - radius / 2, centre.y - radius / 2,
		// radius, radius);
		// gc.drawOval(centre.x - radius / 2, centre.y - radius / 2,
		// radius, radius);
		// }
		// else if (type == IMAGE_TYPE_MOVING){
		// gc.drawRoundRectangle(centre.x - radius / 2, centre.y - radius / 2,
		// radius, radius, radius - 4, radius - 4);
		// }
	}

	/**
	 * 绘制最终状态的额外中心圈
	 * 
	 * @param gc
	 */
	public void paintFSAddition(GC gc, boolean selected) {
		if (selected)
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
		else
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLUE));
		gc.drawRoundRectangle(centre.x - radius / 4, centre.y - radius / 4,
				radius / 2, radius / 2, radius / 2 - 2, radius / 2 - 2);
	}

	/**
	 * 绘制开始状态的额外箭头
	 * 
	 * @param gc
	 */
	public void paintISArrow(GC gc, boolean selected) {
		if (selected)
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
		else
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLUE));
		for (int i = 0; i < 2; i++) {
			gc.drawLine(centre.x - 2 * radius, centre.y + i, centre.x - radius
					/ 2, centre.y + i);
		}
		gc.drawLine(centre.x - radius / 2 - 10, centre.y + 7, centre.x - radius
				/ 2, centre.y + 2);
		gc.drawLine(centre.x - radius / 2 - 10, centre.y + 6, centre.x - radius
				/ 2, centre.y + 1);
		gc.drawLine(centre.x - radius / 2 - 10, centre.y - 5, centre.x - radius
				/ 2, centre.y);
		gc.drawLine(centre.x - radius / 2 - 10, centre.y - 4, centre.x - radius
				/ 2, centre.y + 1);
	}

	@Override
	public void updateOriginalLocation() {
		originalCentre.x = centre.x;
		originalCentre.y = centre.y;
	}

	@Override
	public Point getLableAnchor() {
		return centre;
	}

}
