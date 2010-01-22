/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.graphicsobj;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import automatondesigner.SystemConstant;

import cn.tsinghua.mse.automatondesigner.interfaces.IPaint;

/**
 * 用于显示文字的标签
 * 
 */
public class Text_ItemLable implements IPaint {
	private static final Point DEFAULTDIFFER = new Point(0, 10);

	private Point anchorPtn = null;
	public Point getAnchorPtn() {
		return anchorPtn;
	}

	public void setAnchorPtn(Point anchorPtn) {
		this.anchorPtn = anchorPtn;
	}

	private IPaint contentContainer = null;

	public IPaint getContentStr() {
		return contentContainer;
	}

	public void setContentStr(IPaint contentContainer) {
		this.contentContainer = contentContainer;
	}

	private Point differ = null;
	private Point size = null;
	private Point orgianlDiffer = null;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.tsinghua.mse.automatondesigner.interfaces.IPaint#paint(org.eclipse
	 * .swt.graphics.GC, byte, java.lang.String)
	 */
	@Override
	public void paint(GC gc, byte statue) {
		if (contentContainer == null
				|| !(contentContainer instanceof Circle_State)
				&& !(contentContainer instanceof Polyline_Trans))
			return;
		if (statue == SystemConstant.IMAGE_TYPE_SELECTED)
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
		else if (statue == SystemConstant.IMAGE_TYPE_COMMON)
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLUE));
		String contentStr = "";
		if (contentContainer instanceof Circle_State) {
			contentStr = ((Circle_State) contentContainer).getM_State()
					.getM_Name();
		} else if (contentContainer instanceof Polyline_Trans){
			((Polyline_Trans)contentContainer).updateLableAnchor();
			contentStr = ((Polyline_Trans)contentContainer).getTransFunc().getStrCondition();
		}
		Point pnt = new Point(anchorPtn.x + differ.x, anchorPtn.y + differ.y);
		gc.drawText(contentStr, pnt.x, pnt.y, SWT.DRAW_DELIMITER
					| SWT.DRAW_TRANSPARENT);
		size = gc.textExtent(contentStr);
	}

	public Text_ItemLable() {
		anchorPtn = null;
		contentContainer = null;
	}

	public Text_ItemLable(IPaint content) {
		anchorPtn = content.getLableAnchor();
		differ = new Point(DEFAULTDIFFER.x, DEFAULTDIFFER.y);
		orgianlDiffer = new Point(differ.x, differ.y);
		contentContainer = content;
	}

	@Override
	public void updateOriginalLocation() {
		orgianlDiffer.x = differ.x;
		orgianlDiffer.y = differ.y;
	}

	@Override
	public Point getLableAnchor() {
		Point pnt = new Point(anchorPtn.x + differ.x, anchorPtn.y + differ.y);
		return pnt;
	}

	/**
	 * 判断是否被选中
	 * @param x 鼠标的x坐标
	 * @param y 鼠标的y坐标
	 * @return 是否选中
	 */
	public boolean isSelected(int x, int y) {
		Point pnt = new Point(anchorPtn.x + differ.x, anchorPtn.y + differ.y);
		if ((pnt.x + size.x) >= x && pnt.x <= x && (pnt.y + size.y) >= y
				&& pnt.y <= y) {
			return true;
		}
		return false;
	}

	/**
	 * 移动操作
	 * @param XDist x距离
	 * @param YDist y距离
	 */
	public void move(int XDist, int YDist) {
		differ.x = orgianlDiffer.x + XDist;
		differ.y = orgianlDiffer.y + YDist;
	}

}
