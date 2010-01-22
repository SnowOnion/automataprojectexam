/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.graphicsobj;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import automatondesigner.SystemConstant;

import cn.tsinghua.mse.automatondesigner.dataobject.TransFunction;
import cn.tsinghua.mse.automatondesigner.interfaces.IPaint;
import cn.tsinghua.mse.automatondesigner.tools.CommonTool;

/**
 * 表示转换函数的折线
 * 
 * @author David
 * 
 */
public class Polyline_Trans implements IPaint {

	private TransFunction transFunc = null;
	private ArrayList<Point> polyLine = null;
	private Circle_State beginCircle = null;
	private Circle_State endCircle = null;
	
	private ArrayList<Point> Oranal_PolyLine = null;
	private ArrayList<Integer> selectedPointIdx = null;
	
	private Point lableAnchor;
	
	private Text_ItemLable lable;

	public Text_ItemLable getLable() {
		return lable;
	}

	public void setLable(Text_ItemLable lable) {
		this.lable = lable;
	}

	public Polyline_Trans() {
		transFunc = null;
		lableAnchor = null;
		polyLine = new ArrayList<Point>();
		Oranal_PolyLine = new ArrayList<Point>();
		selectedPointIdx = new ArrayList<Integer>();
	}

	public Polyline_Trans(TransFunction func, ArrayList<Point> pl,
			Circle_State bCircle, Circle_State eCircle) {
		transFunc = func;
		polyLine = pl;
		beginCircle = bCircle;
		endCircle = eCircle;
		Oranal_PolyLine = CommonTool.PointsClone(pl);
		selectedPointIdx = new ArrayList<Integer>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.tsinghua.mse.automatondesigner.interfaces.IPaint#paint(org.eclipse
	 * .swt.graphics.GC, byte, java.lang.String)
	 */
	@Override
	public void paint(GC gc, byte statue) {
		if (statue == SystemConstant.IMAGE_TYPE_SELECTED){
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
		}
		else{
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
		}
		if (polyLine == null || polyLine.size() == 0) {
			if (beginCircle.equals(endCircle)){
				gc.drawOval(beginCircle.getCentre().x - 15, beginCircle
						.getCentre().y - 36, 30, 30);
				paintk(gc, beginCircle.getCentre().x-20, beginCircle.getCentre().y-20, endCircle.getCentre().x, endCircle.getCentre().y, false);
			}
			else{
				gc.drawLine(beginCircle.getCentre().x, beginCircle.getCentre().y,
					endCircle.getCentre().x, endCircle.getCentre().y);
				paintk(gc, beginCircle.getCentre().x, beginCircle.getCentre().y, endCircle.getCentre().x, endCircle.getCentre().y, true);
			}
		} else {
			gc.drawLine(beginCircle.getCentre().x, beginCircle.getCentre().y,
					polyLine.get(0).x, polyLine.get(0).y);
			gc.drawLine(polyLine.get(polyLine.size() - 1).x, polyLine
					.get(polyLine.size() - 1).y, endCircle.getCentre().x,
					endCircle.getCentre().y);
			gc.drawPolyline(CommonTool.pointArrayToIntArray(polyLine));
			paintk(gc, polyLine.get(polyLine.size() - 1).x, polyLine
					.get(polyLine.size() - 1).y, endCircle.getCentre().x,
					endCircle.getCentre().y, true);
			if (statue == SystemConstant.IMAGE_TYPE_SELECTED){
				for(Point p : polyLine){
					gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
					gc.fillOval(p.x-4, p.y-4, 8, 8);
					gc.drawOval(p.x-4, p.y-4, 8, 8);
				}
			}
			else if (selectedPointIdx != null && selectedPointIdx.size() > 0){
				for(int i : selectedPointIdx){
					Point p = polyLine.get(i);
					gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
					gc.fillOval(p.x-4, p.y-4, 8, 8);   //RoundRectangle(p.x-4, p.y-4, 8, 8, 2, 2);
					gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
					gc.drawOval(p.x-4, p.y-4, 8, 8);
				}
			}
		}
		if (lable == null){
			lable = new Text_ItemLable(this);
		}
		lable.paint(gc, statue);
		//drawLabe(gc, statue);
	}
	
//	private void drawLabe(GC gc, byte statue){
//		ArrayList<Point> ps = new ArrayList<Point>();
//		ps.add(beginCircle.getCentre());
//		ps.addAll(polyLine);
//		ps.add(endCircle.getCentre());
//		int num = ps.size()/2;
//		gc.drawText(transFunc.getStrCondition(), (ps.get(num).x+ps.get(num-1).x)/2, (ps.get(num).y+ps.get(num-1).y)/2, true);
//	}
	
	public void updateLableAnchor(){
		if (beginCircle.equals(endCircle) && polyLine.size() == 0){
			if (lableAnchor == null){
				lableAnchor = new Point(beginCircle.getCentre().x, beginCircle.getCentre().y-36);
			}else{
				lableAnchor.x = beginCircle.getCentre().x;
				lableAnchor.y = beginCircle.getCentre().y-36;
			}
			return;
		}
		ArrayList<Point> ps = new ArrayList<Point>();
		ps.add(beginCircle.getCentre());
		ps.addAll(polyLine);
		ps.add(endCircle.getCentre());
		int num = ps.size()/2;
		if (lableAnchor == null){
			lableAnchor = new Point((ps.get(num).x+ps.get(num-1).x)/2, (ps.get(num).y+ps.get(num-1).y)/2);
		}else{
			lableAnchor.x = (ps.get(num).x+ps.get(num-1).x)/2;
			lableAnchor.y = (ps.get(num).y+ps.get(num-1).y)/2;
		}
	}
	
	/**
	 * 向折线中添加点
	 * @param p 添加的点坐标
	 */
	public void addPntToPolyline(Point p){
		this.getPolyLine().add(p);
		this.Oranal_PolyLine.add(new Point(p.x, p.y));
	}

	/**
	 * 绘制箭头
	 * @param g GC对象
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param drawline 是否绘制直线
	 */
	private void paintk(GC g, int x1, int y1, int x2, int y2, boolean drawline) {
		double H = 8; // 箭头高度
		double L = 4; // 底边的一半
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		double D = Math.abs(Point2D.distance(x1, y1, x2, y2));
		if (D <= SystemConstant.DEFAULTRADIUS/2 ){
			return;
		}
		x2 = (int) (x1+(x2-x1)*(D-10)/D);
		y2 = (int) (y1+(y2-y1)*(D-10)/D);
		double awrad = Math.atan(L / H); // 箭头角度
		double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
		double[] arrXY_1 = rotateVec(x2 - x1, y2 - y1, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(x2 - x1, y2 - y1, -awrad, true, arraow_len);
		double x_3 = x2 - arrXY_1[0]; // (x3,y3)是第一端点
		double y_3 = y2 - arrXY_1[1];
		double x_4 = x2 - arrXY_2[0]; // (x4,y4)是第二端点
		double y_4 = y2 - arrXY_2[1];
		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();
		// g.setColor(SWT.COLOR_WHITE);
		// 画线
		if (drawline)
			g.drawLine(x1, y1, x2, y2);
		// 画箭头的一半
		g.drawLine(x2, y2, x3, y3);
		// 画箭头的另一半
		g.drawLine(x2, y2, x4, y4);
	}

	public double[] rotateVec(int px, int py, double ang, boolean isChLen,
			double newLen) {
		double mathstr[] = new double[2];
		// 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
		double vx = px * Math.cos(ang) - py * Math.sin(ang);
		double vy = px * Math.sin(ang) + py * Math.cos(ang);
		if (isChLen) {
			double d = Math.sqrt(vx * vx + vy * vy);
			vx = vx / d * newLen;
			vy = vy / d * newLen;
			mathstr[0] = vx;
			mathstr[1] = vy;
		}
		return mathstr;
	}
	
	/**
	 * 检查某点是否在多边折线上。
	 * @param p 要检查的点
	 * @return 是否在折线上
	 */
	public boolean isOnThePolyline(Point p){
		ArrayList<Point> ps = new ArrayList<Point>();
		if (beginCircle.equals(endCircle) && polyLine.size() == 0){
			double dist = Point2D.distance(beginCircle.getCentre().x, beginCircle.getCentre().y-6-15, p.x, p.y);
			if (Math.abs(dist-SystemConstant.DEFAULTRADIUS+5) <= 2){
				return true;
			}
			return false;
		}
		ps.add(beginCircle.getCentre());
		ps.addAll(polyLine);
		ps.add(endCircle.getCentre());
		for (int i = 0; i < ps.size()-1; i++){
			Point p1 = ps.get(i);
			Point p2 = ps.get(1+i);
			double totalDistance = Point2D.distance(p1.x, p1.y, p2.x, p2.y);
			double d1 = Point2D.distance(p.x, p.y, p2.x, p2.y);
			double d2 = Point2D.distance(p.x, p.y, p1.x, p1.y);
			if (d1 + d2 <= totalDistance + 2){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void updateOriginalLocation(){
		if (polyLine == null || polyLine.size() == 0){
			return;
		}
		for (int i = 0; i < polyLine.size(); i++){
			Oranal_PolyLine.get(i).x = polyLine.get(i).x;
			Oranal_PolyLine.get(i).y = polyLine.get(i).y;
		}
	}
	
	/**
	 * 检查某点是否在折线的某个折点上
	 * @param p 要检查的点坐标
	 * @return 所在折点，为null表示不在此折线的折点上
	 */
	public Point isOnKneePoint(Point p){
		if (polyLine == null || polyLine.size() == 0){
			return null;
		}
		else{
			for(Point pnt : polyLine){
				if (Point2D.distance(p.x, p.y, pnt.x, pnt.y) <= 4)
					return pnt;
			}
			return null;
		}
	}
	
	/**
	 * 为折线增加折点
	 * @param p 要增加折点的位置坐标点。
	 */
	public void addKneePoint(Point p){
		if (beginCircle.equals(endCircle) && polyLine.size() == 0){
			polyLine.add(0, p);
			Oranal_PolyLine.add(0, new Point(p.x, p.y));
			//updateLableAnchor();
			return;
		}
		ArrayList<Point> ps = new ArrayList<Point>();
		ps.add(beginCircle.getCentre());
		ps.addAll(polyLine);
		ps.add(endCircle.getCentre());
		for (int i = 0; i < ps.size()-1; i++){
			Point p1 = ps.get(i);
			Point p2 = ps.get(1+i);
			double totalDistance = Point2D.distance(p1.x, p1.y, p2.x, p2.y);
			double d1 = Point2D.distance(p.x, p.y, p2.x, p2.y);
			double d2 = Point2D.distance(p.x, p.y, p1.x, p1.y);
			if (d1 + d2 <= totalDistance + 2){
				polyLine.add(i, p);
				Oranal_PolyLine.add(i, new Point(p.x, p.y));
				updateLableAnchor();
				return;
			}
		}
	}
	
	/**
	 * 移动整条折线
	 * @param XDist x方向上移动的距离
	 * @param YDist y方向上移动的距离
	 */
	public void movePolyline(int XDist, int YDist){
		if (polyLine == null || polyLine.size() == 0){
			return;
		}
		for (int i = 0; i < polyLine.size(); i++){
			polyLine.get(i).x = Oranal_PolyLine.get(i).x + XDist;
			polyLine.get(i).y = Oranal_PolyLine.get(i).y + YDist;
		}
		//updateLableAnchor();
	}
	
	/**
	 * 移动折线上选中的某些折点
	 * @param XDist x方向上移动的距离
	 * @param YDist y方向上移动的距离
	 */
	public void moveSelectedPnts(int XDist, int YDist){
		if (selectedPointIdx == null || selectedPointIdx.size() == 0){
			return;
		}
		for(int i : selectedPointIdx){
			polyLine.get(i).x = Oranal_PolyLine.get(i).x + XDist;
			polyLine.get(i).y = Oranal_PolyLine.get(i).y + YDist;
		}
		//updateLableAnchor();
	}
	
	/**
	 * 某个点是否已经被选中
	 * @param p 要检查的点坐标
	 * @return 是否已经选中
	 */
	public boolean isSelected(Point p){
		int idx = polyLine.indexOf(p);
		return selectedPointIdx.contains(idx);
	}
	
	/**
	 * 在矩形框内的所有点标记为选中
	 * @param rect 矩形框
	 */
	public void recordPntsInRect(Rectangle rect){
		if (polyLine == null || polyLine.size() == 0){
			return;
		}
		clearSelectedPointIdx();
		for (int i = 0; i < polyLine.size(); i++){
			if (rect.contains(polyLine.get(i))){
				selectedPointIdx.add(i);
			}
		}
	}
	
	/**
	 * 移除某个折点
	 * @param p 待移除的折点
	 */
	public void removePnt(Point p){
		int idx = polyLine.indexOf(p);
		polyLine.remove(idx);
		Oranal_PolyLine.remove(idx);
		//updateLableAnchor();
	}

	public void addSelectedPntIdx(Point p, boolean removeifexist){
		if (selectedPointIdx == null){
			selectedPointIdx = new ArrayList<Integer>();
		}
		int idx = polyLine.indexOf(p);
		if (!selectedPointIdx.contains(idx))
			selectedPointIdx.add(idx);
		else if (removeifexist){
			selectedPointIdx.remove(new Integer(idx));
		}
	}
	
	public void clearSelectedPointIdx(){
		selectedPointIdx.clear();
	}

	public Circle_State getBeginCircle() {
		return beginCircle;
	}

	public void setBeginCircle(Circle_State beginCircle) {
		this.beginCircle = beginCircle;
	}

	public Circle_State getEndCircle() {
		return endCircle;
	}

	public void setEndCircle(Circle_State endCircle) {
		this.endCircle = endCircle;
	}

	/**
	 * 获取与本组件相关的转移函数对象
	 * @return 转移函数对象
	 */
	public TransFunction getTransFunc() {
		return transFunc;
	}
	
	/**
	 * 设置与本组件相关的转移函数对象
	 * @param transFunc 转移函数对象
	 */
	public void setTransFunc(TransFunction transFunc) {
		this.transFunc = transFunc;
	}

	public ArrayList<Point> getPolyLine() {
		return polyLine;
	}

	public void setPolyLine(ArrayList<Point> polyLine) {
		this.polyLine = polyLine;
		this.Oranal_PolyLine = CommonTool.PointsClone(polyLine);
	}

	/**
	 * 删除所有的选中点
	 * @return 是否成功
	 */
	public boolean removeSelectedPtn() {
		if (selectedPointIdx == null || selectedPointIdx.size() == 0)
			return false;
		Collections.sort(selectedPointIdx);
		for (int i = selectedPointIdx.size()-1; i >= 0; i--){
			removePnt(polyLine.get(selectedPointIdx.get(i)));
		}
		selectedPointIdx.clear();
		return true;
	}

	@Override
	public Point getLableAnchor() {
		if (lableAnchor == null){
			updateLableAnchor();
		}
		return lableAnchor;
	}

}
