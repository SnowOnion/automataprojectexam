/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.graphicsobj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;

import automatondesigner.SystemConstant;

import com.swtdesigner.ResourceManager;

import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.State;
import cn.tsinghua.mse.automatondesigner.dataobject.TransCondition;
import cn.tsinghua.mse.automatondesigner.dataobject.TransFunction;
import cn.tsinghua.mse.automatondesigner.interfaces.ICanvasContainer;
import cn.tsinghua.mse.automatondesigner.interfaces.IPaint;
import cn.tsinghua.mse.automatondesigner.tools.CommonTool;
import cn.tsinghua.mse.automatondesigner.ui.Dialog_Location;
import cn.tsinghua.mse.automatondesigner.ui.Dialog_State;
import cn.tsinghua.mse.automatondesigner.ui.Dialog_TransFunction;
import cn.tsinghua.mse.automatondesigner.ui.Menu_AutomatonRightClick;
import cn.tsinghua.mse.automatondesigner.ui.Menu_KneePointRightClick;
import cn.tsinghua.mse.automatondesigner.ui.Menu_StateRightClick;
import cn.tsinghua.mse.automatondesigner.ui.Menu_TransRightClick;
import cn.tsinghua.mse.automatondesigner.ui.View_ToolBox;

/**
 * @author David
 * 
 */
public class Canvas_Automaton extends Canvas implements MouseListener,
		MouseMoveListener, MouseTrackListener, KeyListener {

	private ArrayList<Circle_State> m_Circles;
	private ArrayList<Circle_State> m_SelectedCircles;
	private ICanvasContainer m_mainView = null;

	// 为自动机状态自动命名的前缀字符串。
	private String prefix_StateName = SystemConstant.PREFIX_STATE_NAME;

	private Rectangle selectRectangle = null;

	private Point startMovingPoint = null;
	private boolean isMoving = false;

	private Circle_State drawingPLStart = null;
	private ArrayList<Point> currectPolyline = null;
	private ArrayList<Polyline_Trans> m_Polylines;
	private Point currectPnt = null;

	private ArrayList<Polyline_Trans> m_SelectedPolylines;

	private Text_ItemLable m_SelectedLable = null;

	// 此变量仅供内部暂存折线，为右键delete操作和双击操作提供便利。
	private Polyline_Trans storePolyline = null;

	public Polyline_Trans getStorePolyline() {
		return storePolyline;
	}

	/**
	 * @param parent 父组件
	 * @param style 类型
	 */
	public Canvas_Automaton(Composite parent, int style,
			ICanvasContainer mainView) {
		super(parent, style);
		m_Circles = new ArrayList<Circle_State>();
		m_SelectedCircles = new ArrayList<Circle_State>();
		m_SelectedPolylines = new ArrayList<Polyline_Trans>();
		this.addMouseListener(this);
		this.addMouseMoveListener(this);
		this.addMouseTrackListener(this);
		m_mainView = mainView;
		drawingPLStart = null;
		currectPolyline = new ArrayList<Point>();
		m_Polylines = new ArrayList<Polyline_Trans>();
		currectPnt = new Point(-100, -100);
	}

	public ArrayList<Circle_State> getM_Circles() {
		return m_Circles;
	}

	public void setM_Circles(ArrayList<Circle_State> mCircles) {
		m_Circles = mCircles;
	}

	public ArrayList<Polyline_Trans> getM_Polylines() {
		return m_Polylines;
	}

	public void setM_Polylines(ArrayList<Polyline_Trans> mPolylines) {
		m_Polylines = mPolylines;
	}

	public String getPrefix_StateName() {
		return prefix_StateName;
	}

	public void setPrefix_StateName(String prefixStateName) {
		prefix_StateName = prefixStateName;
	}

	public ICanvasContainer getM_mainView() {
		return m_mainView;
	}

	public void paint(GC gc) {
		// gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);
		if (m_SelectedLable != null) {
			gc.setLineStyle(SWT.LINE_DOT);
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_GRAY));
			gc.drawLine(m_SelectedLable.getAnchorPtn().x, m_SelectedLable.getAnchorPtn().y, m_SelectedLable.getLableAnchor().x, m_SelectedLable.getLableAnchor().y);
			gc.setLineStyle(SWT.LINE_SOLID);
		}
		
		for (Polyline_Trans trans : m_Polylines) {
			if (!m_SelectedPolylines.contains(trans))
				trans.paint(gc, SystemConstant.IMAGE_TYPE_COMMON);
		}
		if (currectPolyline != null && drawingPLStart != null) {
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
			if (currectPolyline.size() == 0) {
				if (currectPnt.x != -100)
					gc.drawLine(currectPnt.x, currectPnt.y, drawingPLStart
							.getCentre().x, drawingPLStart.getCentre().y);
			} else {
				gc.drawLine(currectPolyline.get(0).x, currectPolyline.get(0).y,
						drawingPLStart.getCentre().x, drawingPLStart
								.getCentre().y);
				gc.drawPolyline(CommonTool
						.pointArrayToIntArray(currectPolyline));
				Point p = currectPolyline.get(currectPolyline.size() - 1);
				if (currectPnt.x != -100)
					gc.drawLine(currectPnt.x, currectPnt.y, p.x, p.y);
			}

		}

		for (Polyline_Trans polyline : m_SelectedPolylines) {
			polyline.paint(gc, SystemConstant.IMAGE_TYPE_SELECTED);
		}

		for (Circle_State circle : m_Circles) {
			if (!m_SelectedCircles.contains(circle))
				circle.paint(gc, SystemConstant.IMAGE_TYPE_COMMON);
		}

		for (Circle_State circle : m_SelectedCircles) {
			circle.paint(gc, SystemConstant.IMAGE_TYPE_SELECTED);
		}

		// paintAddition(gc);

		if (selectRectangle != null && selectRectangle.width != 0) {
			gc.setLineStyle(SWT.LINE_DOT);
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
			gc.drawRectangle(selectRectangle.x, selectRectangle.y,
					selectRectangle.width, selectRectangle.height);
			gc.setLineStyle(SWT.LINE_SOLID);
		}
		if (m_SelectedLable != null) {
			m_SelectedLable.paint(gc, SystemConstant.IMAGE_TYPE_SELECTED);
		}
	}

	/**
	 * 用于绘制自动机的其他额外组件，包括开始状态的箭头和结束状态的多余中心圈。
	 * 
	 * @param gc
	 */
	private void paintAddition(GC gc) {
		ArrayList<State> finalStates = m_mainView.getM_Automaton()
				.getFinalStates();
		for (State state : finalStates) {
			Circle_State circle = findCircle(state);
			if (circle != null) {
				circle.paintFSAddition(gc, m_SelectedCircles.contains(circle));
			}
		}
		State s = m_mainView.getM_Automaton().getM_StartState();
		if (s != null) {
			Circle_State circle = findCircle(s);
			if (circle != null) {
				circle.paintISArrow(gc, m_SelectedCircles.contains(circle));
			}
		}
	}

	private boolean isSelected(State s) {
		for (Circle_State circle : m_SelectedCircles) {
			if (circle.getM_State().equals(s))
				return true;
		}
		return false;
	}

	private Circle_State findCircle(State s) {
		for (Circle_State circle : m_Circles) {
			if (circle.getM_State().equals(s))
				return circle;
		}
		for (Circle_State circle : m_SelectedCircles) {
			if (circle.getM_State().equals(s))
				return circle;
		}
		return null;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		byte toolType = getToolType();
		switch (toolType) {
		case View_ToolBox.TOOLTYPE_SELECT:
			Object obj = getCurrSelected(e.x, e.y, false);
			if (obj == null) {

			} else if (obj instanceof Circle_State) {
				showStateProperty((Circle_State) obj);
			} else if (obj instanceof Polyline_Trans) {
				showTransFuncProperty((Polyline_Trans) obj);
			} else if (obj instanceof Point) {
				showLocationShell((Point) obj, storePolyline);
			}
			break;
		case View_ToolBox.TOOLTYPE_STATE:
			break;
		case View_ToolBox.TOOLTYPE_TRANSFORM:
			if (drawingPLStart == null)
				break;
			Object paint = getCurrSelected(e.x, e.y, false);
			if (paint != null && paint instanceof Circle_State) {
				Circle_State circle = (Circle_State) paint;
				if (currectPolyline.size() == 0
						&& circle.equals(drawingPLStart)) {
					// boolean re =
					// MessageDialog.openQuestion(m_mainView.getMainWindow().getShell(),
					// "询问", "您是要创建自环吗？");
					// if(!re)
					drawingPLStart = null;
					currectPolyline.clear();
					currectPnt.x = -100;
					return;
				}
				TransFunction func = new TransFunction(drawingPLStart
						.getM_State(), circle.getM_State(),
						new ArrayList<TransCondition>());
				m_mainView.getM_Automaton().addTransFunction(func);
				m_Polylines.add(new Polyline_Trans(func, CommonTool
						.PointsClone(currectPolyline), drawingPLStart, circle));
				drawingPLStart = null;
				currectPolyline.clear();
				currectPnt.x = -100;
			} else {
				MessageDialog.openWarning(
						m_mainView.getMainWindow().getShell(), "提示",
						"终结点必须为结束状态！");
			}
			break;
		default:
			break;
		}
	}

	public void showLocationShell(Point p, IPaint paint) {
		Dialog_Location shell = new Dialog_Location(this.getShell(), p);
		int result = shell.open();
		if (result == SystemConstant.DIALOG_RESULT_SAVE) {
			paint.updateOriginalLocation();
			m_mainView.setDirty(true);
			redraw();
		}
	}

	public void showTransFuncProperty(Polyline_Trans trans) {
		Dialog_TransFunction dialog = new Dialog_TransFunction(this.getShell(),
				this, trans);
		int result = dialog.open();
		if (result == SystemConstant.DIALOG_RESULT_SAVE) {
			// circle.updateOriginalLocation();
			m_mainView.setDirty(true);
			redraw();
		}
	}

	public void showStateProperty(Circle_State circle) {
		Dialog_State shell = new Dialog_State(this.getShell(), getM_mainView()
				.getM_Automaton(), circle.getM_State());
		int result = shell.open();
		if (result == SystemConstant.DIALOG_RESULT_SAVE) {
			circle.updateOriginalLocation();
			m_mainView.setDirty(true);
			redraw();
		}
	}

	private Object getCurrSelected(int x, int y, boolean CtrlPressed) {
		IPaint p = null;
		p = getCurrectLable(x, y);
		if (p != null) {
			return p;
		}
		p = getCurrSelectedCircle(x, y);
		if (p != null) {
			return p;
		}
		for (Polyline_Trans polyline : m_Polylines) {
			Point pnt = polyline.isOnKneePoint(new Point(x, y));
			if (pnt != null) {
				if (!CtrlPressed && !polyline.isSelected(pnt)) {
					clearAllSelectedItems();
				}
				polyline.addSelectedPntIdx(pnt, CtrlPressed);
				storePolyline = polyline;
				return pnt;
			}
			if (polyline.isOnThePolyline(new Point(x, y)))
				return polyline;
		}
		return null;
	}

	private IPaint getCurrectLable(int x, int y) {
		for (Circle_State circle : m_Circles) {
			if (circle.getLable().isSelected(x, y)) {
				return circle.getLable();
			}
		}
		for (Polyline_Trans polyline : m_Polylines) {
			if (polyline.getLable().isSelected(x, y)) {
				return polyline.getLable();
			}
		}
		return null;
	}

	private Circle_State getCurrSelectedCircle(int x, int y) {
		for (Circle_State circle : m_Circles) {
			if (circle.checkPointIn(x, y)) {
				return circle;
			}
		}
		return null;
	}

	@Override
	public void mouseDown(MouseEvent e) {
		if (e.count == 2)
			return;
		byte toolType = getToolType();
		switch (toolType) {
		case View_ToolBox.TOOLTYPE_SELECT:
			// 按下鼠标左键
			if ((e.button == 1)) {
				Object p = getCurrSelected(e.x, e.y, e.stateMask == SWT.CTRL);
				if (p == null) {
					if (e.stateMask != SWT.CTRL) {
						clearAllSelectedItems();
						selectRectangle = new Rectangle(e.x, e.y, 0, 0);
					}
				} else if (p instanceof Text_ItemLable) {
					clearAllSelectedItems();
					m_SelectedLable = (Text_ItemLable) p;
					startMovingPoint = new Point(e.x, e.y);
				} else if (p instanceof Circle_State) {
					Circle_State circle = (Circle_State) p;
					// 没有按住Ctrl键的单选情况处理
					if (e.stateMask != SWT.CTRL) {
						if (!m_SelectedCircles.contains(circle)) {
							clearAllSelectedItems();
							m_SelectedCircles.add(circle);
						}
					} else {
						if (!m_SelectedCircles.contains(circle)) {
							m_SelectedCircles.add(circle);
						} else {
							m_SelectedCircles.remove(circle);
						}
					}
					startMovingPoint = new Point(e.x, e.y);
				} else if (p instanceof Polyline_Trans) {
					Polyline_Trans polyline = (Polyline_Trans) p;
					if (e.stateMask != SWT.CTRL) {
						if (!m_SelectedPolylines.contains(polyline)) {
							clearAllSelectedItems();
							m_SelectedPolylines.add(polyline);
						}
					} else {
						if (!m_SelectedPolylines.contains(polyline)) {
							m_SelectedPolylines.add(polyline);
						} else {
							m_SelectedPolylines.remove(polyline);
						}
					}
					startMovingPoint = new Point(e.x, e.y);
				} else if (p instanceof Point) {
					startMovingPoint = new Point(e.x, e.y);
				}
				m_Circles.removeAll(m_SelectedCircles);
				m_Polylines.removeAll(m_SelectedPolylines);
			}
			redraw();
			break;
		case View_ToolBox.TOOLTYPE_STATE:
			break;
		case View_ToolBox.TOOLTYPE_TRANSFORM:
			break;
		default:
			break;
		}
	}

	/**
	 * 获取工具箱当前选中的组件类型
	 * 
	 * @return
	 */
	private byte getToolType() {
		IViewReference[] vfs = m_mainView.getMainWindow().getActivePage()
				.getViewReferences();
		IViewPart vw;
		byte toolType = View_ToolBox.TOOLTYPE_SELECT;
		for (int i = 0; i < vfs.length; i++) {
			vw = vfs[i].getView(false);
			if (vw.getTitle().equals("工具箱")) {
				toolType = ((View_ToolBox) vw).getToolTypeSelected();
			}
		}
		return toolType;
	}

	public void addKneePointForPoyline(int x, int y) {
		m_SelectedPolylines.get(0).addKneePoint(new Point(x, y));
		m_mainView.setDirty(true);
		redraw();
	}

	/**
	 * 将工具箱设置为选择状态
	 */
	private void setToolBoxSelected() {
		IViewReference[] vfs = m_mainView.getMainWindow().getActivePage()
				.getViewReferences();
		IViewPart vw;
		byte toolType = View_ToolBox.TOOLTYPE_SELECT;
		for (int i = 0; i < vfs.length; i++) {
			vw = vfs[i].getView(true);
			if (vw.getTitle().equals("工具箱")) {
				((View_ToolBox) vw).setToolSelected();
			}
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
		if (e.count == 2)
			return;
		byte toolType = getToolType();
		switch (toolType) {
		case View_ToolBox.TOOLTYPE_SELECT:
			if (e.button == 1) {
				if (selectRectangle == null) {
					m_Circles.addAll(m_SelectedCircles);
					m_Polylines.addAll(m_SelectedPolylines);
				}
				if (isMoving == true) {
					isMoving = false;
					for (Circle_State circle : m_SelectedCircles) {
						circle.getOriginalCentre().x = circle.getCentre().x;
						circle.getOriginalCentre().y = circle.getCentre().y;
					}
					for (Polyline_Trans trans : m_Polylines) {
						trans.updateOriginalLocation();
					}
					if (m_SelectedLable != null) {
						m_SelectedLable.updateOriginalLocation();
					}
					m_mainView.setDirty(true);
				}
				selectRectangle = null;
			} else if (e.button == 3) { // 鼠标右键菜单弹出
				Object obj = getCurrSelected(e.x, e.y, false);
				clearAllSelectedItems();
				if (obj instanceof Circle_State) {
					Circle_State circle = (Circle_State) obj;
					m_SelectedCircles.add(circle);
					Menu_StateRightClick rightmenu = new Menu_StateRightClick(
							this, SWT.POP_UP, (Circle_State) obj);
					Point pnt = getScreemLocation();
					rightmenu.showMenu(pnt.x + e.x + 6, pnt.y + e.y + 52);
				} else if (obj instanceof Polyline_Trans) {
					Polyline_Trans polyline = (Polyline_Trans) obj;
					m_SelectedPolylines.add(polyline);
					Menu_TransRightClick rightmenu = new Menu_TransRightClick(
							this, SWT.POP_UP, new Point(e.x, e.y), polyline);
					Point pnt = getScreemLocation();
					rightmenu.showMenu(pnt.x + e.x + 6, pnt.y + e.y + 52);
				} else if (obj instanceof Point) {
					storePolyline.addSelectedPntIdx((Point)obj, false);
					Menu_KneePointRightClick rightmenu = new Menu_KneePointRightClick(
							this, SWT.POP_UP, (Point) obj);
					Point pnt = getScreemLocation();
					rightmenu.showMenu(pnt.x + e.x + 6, pnt.y + e.y + 52);
				}
				else if (obj == null){
					Menu_AutomatonRightClick rightmenu = new Menu_AutomatonRightClick(
							this, SWT.POP_UP);
					Point pnt = getScreemLocation();
					rightmenu.showMenu(pnt.x + e.x + 6, pnt.y + e.y + 52);
				}
			}
			redraw();
			break;
		case View_ToolBox.TOOLTYPE_STATE:
			// 新加状态
			if (e.button == 1) {
				Circle_State newCircle = new Circle_State(new State(m_mainView
						.getM_Automaton().getNextNameIdx(prefix_StateName),
						AutomatonConst.STATE_COMMON_TYPE), new Point(e.x, e.y));
				m_Circles.add(newCircle);
				m_SelectedCircles.clear();
				m_SelectedCircles.add(newCircle);
				// 将新加的状态放入自动机中
				m_mainView.getM_Automaton().getM_States().add(
						newCircle.getM_State());
				this.redraw();
				startMovingPoint = null;
				m_mainView.setDirty(true);
			} else if (e.button == 3) {
				setToolBoxSelected();
				setCursor(new Cursor(null, SWT.CURSOR_ARROW));
			}
			break;
		case View_ToolBox.TOOLTYPE_TRANSFORM:
			// 如果是鼠标左键
			if (e.button == 1) {
				Circle_State circle = getCurrSelectedCircle(e.x, e.y);
				if (drawingPLStart == null) { // 如果是刚刚开始绘制的话
					if (circle == null)
						break;
					drawingPLStart = circle;
				} else {
					if (circle == null) // 绘制拐点
						currectPolyline.add(new Point(e.x, e.y));
					else {
						if (currectPolyline.size() == 0
								&& circle.equals(drawingPLStart)) {
							 boolean re =
							 MessageDialog.openQuestion(m_mainView
							 .getMainWindow().getShell(), "询问",
							 "您是要创建自环吗？");
							 if (!re)
								 return;
						}
						TransFunction func = new TransFunction(drawingPLStart
								.getM_State(), circle.getM_State(),
								new ArrayList<TransCondition>());
						m_mainView.getM_Automaton().addTransFunction(func);
						m_Polylines.add(new Polyline_Trans(func, CommonTool
								.PointsClone(currectPolyline), drawingPLStart,
								circle));
						currectPolyline.clear();
						drawingPLStart = circle;
						m_mainView.setDirty(true);
					}
				}
			} else if (e.button == 3) {
				if (drawingPLStart == null) {
					setToolBoxSelected();
					setCursor(new Cursor(null, SWT.CURSOR_ARROW));
				} else if (currectPolyline.size() == 0) {
					drawingPLStart = null;
					currectPnt.x = -100;
				} else {
					currectPolyline.remove(currectPolyline.size() - 1);
				}
			}
			redraw();
			break;
		default:
			break;
		}
	}

	/**
	 * 清理画布上所有选中的组件
	 */
	public void clearAllSelectedItems() {
		m_SelectedCircles.clear();
		m_SelectedPolylines.clear();
		for (Polyline_Trans trans : m_Polylines) {
			trans.clearSelectedPointIdx();
		}
		m_SelectedLable = null;
	}

	private Point getScreemLocation() {
		Control control = this;
		int width = control.getLocation().x;
		int height = control.getLocation().y;
		while (control.getParent() != null) {
			control = control.getParent();
			width += control.getLocation().x;
			height += control.getLocation().y;
		}
		return new Point(width, height);
	}

	public void setCircleInRect() {
		m_SelectedCircles.clear();
		if (selectRectangle == null)
			return;
		Rectangle temp = getRagularRect();
		for (Circle_State c : m_Circles) {
			if (temp.contains(c.getCentre()))
				m_SelectedCircles.add(c);
		}
	}

	public void setItemsInRect() {
		setCircleInRect();
		setPolylineInRect();
		setPntsinRect();
	}

	private void setPntsinRect() {
		if (m_Polylines == null || m_Polylines.size() == 0)
			return;
		Rectangle rect = getRagularRect();
		for (Polyline_Trans trans : m_Polylines) {
			trans.recordPntsInRect(rect);
		}
	}

	private Rectangle getRagularRect() {
		Rectangle temp = new Rectangle(selectRectangle.x, selectRectangle.y,
				Math.abs(selectRectangle.width), Math
						.abs(selectRectangle.height));
		if (selectRectangle.width < 0) {
			temp.x = selectRectangle.x + selectRectangle.width;
		}
		if (selectRectangle.height < 0) {
			temp.y = selectRectangle.y + selectRectangle.height;
		}
		return temp;
	}

	public void setPolylineInRect() {
		m_SelectedPolylines.clear();
		if (selectRectangle == null)
			return;
		Rectangle temp = getRagularRect();
		for (Polyline_Trans t : m_Polylines) {
			if (temp.contains(t.getBeginCircle().getCentre())
					&& temp.contains(t.getEndCircle().getCentre()))
				if (!m_SelectedPolylines.contains(t))
					m_SelectedPolylines.add(t);
		}
	}

	@Override
	public void mouseMove(MouseEvent e) {
		byte toolType = getToolType();
		switch (toolType) {
		case View_ToolBox.TOOLTYPE_SELECT:
			// 如果不是左键则不进行移动操作。
			if ((e.stateMask & SWT.BUTTON1) == 0)
				break;
			if (selectRectangle != null) {
				selectRectangle.width = e.x - selectRectangle.x;
				selectRectangle.height = e.y - selectRectangle.y;
				setItemsInRect();
			} else if (!(m_SelectedCircles == null)) {
				isMoving = true;
				int differX = e.x - startMovingPoint.x;
				int differY = e.y - startMovingPoint.y;
				if (m_SelectedLable != null) {
					m_SelectedLable.move(differX, differY);
				} else {
					for (Circle_State circle : m_SelectedCircles) {
						circle.getCentre().x = circle.getOriginalCentre().x
								+ differX;
						circle.getCentre().y = circle.getOriginalCentre().y
								+ differY;
					}
					for (Polyline_Trans trans : m_SelectedPolylines) {
						trans.movePolyline(differX, differY);
					}
					for (Polyline_Trans trans : m_Polylines) {
						trans.moveSelectedPnts(differX, differY);
					}
					//m_mainView.setDirty(true);
				}
			}
			redraw();
			break;
		case View_ToolBox.TOOLTYPE_STATE:
			// System.out.print("Wrong!");
			break;
		case View_ToolBox.TOOLTYPE_TRANSFORM:
			if (drawingPLStart != null) {
				currectPnt.x = e.x;
				currectPnt.y = e.y;
				redraw();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseEnter(MouseEvent e) {
		byte toolType = getToolType();
		switch (toolType) {
		case View_ToolBox.TOOLTYPE_SELECT:
			setCursor(new Cursor(null, SWT.CURSOR_ARROW));
			break;
		case View_ToolBox.TOOLTYPE_STATE:
			Cursor imgcursor = new Cursor(this.getParent().getDisplay(),
					ResourceManager.getPluginImage("AutomatonDesigner",
							"icons/adding.gif").getImageData(), 0, 0);
			setCursor(imgcursor);
			break;
		case View_ToolBox.TOOLTYPE_TRANSFORM:
			setCursor(new Cursor(null, SWT.CURSOR_CROSS));
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseExit(MouseEvent e) {
		setCursor(new Cursor(null, SWT.CURSOR_ARROW));
	}

	@Override
	public void mouseHover(MouseEvent e) {

	}

	/**
	 * 执行删除选中元素的操作，返回值表示是否成功删除。
	 * 
	 * @return 是否删除成功
	 */
	public boolean doDelete() {
		boolean result = false;
		if (m_SelectedCircles != null && m_SelectedCircles.size() > 0) {
			if (!MessageDialog.openQuestion(m_mainView.getMainWindow()
					.getShell(), "确认", "删除状态将删除所有与该状态关联的转换函数，是否继续？")) {
				return true;
			}
		}
		if ((m_SelectedCircles != null && m_SelectedCircles.size() > 0)) {
			int size = m_Polylines.size();
			for (int i = size - 1; i >= 0; i--) {
				Polyline_Trans trans = m_Polylines.get(i);
				if (m_SelectedCircles.contains(trans.getBeginCircle())
						|| m_SelectedCircles.contains(trans.getEndCircle())) {
					m_mainView.getM_Automaton().removeTransFunction(
							trans.getTransFunc());
					m_Polylines.remove(trans);
					m_SelectedPolylines.remove(trans);
				}
			}

			m_Circles.removeAll(m_SelectedCircles);
			for (Circle_State circle : m_SelectedCircles) {
				m_mainView.getM_Automaton().getM_States().remove(
						circle.getM_State());
			}
			m_SelectedCircles.clear();
			result = true;
		}
		if ((m_SelectedPolylines != null && m_SelectedPolylines.size() > 0)) {
			for (Polyline_Trans trans : m_SelectedPolylines) {
				m_mainView.getM_Automaton().removeTransFunction(
						trans.getTransFunc());
			}
			m_Polylines.removeAll(m_SelectedPolylines);
			m_SelectedPolylines.clear();
			result = true;
		}

		for (Polyline_Trans polyline : m_Polylines) {
			if (polyline.removeSelectedPtn())
				result = true;
		}
		if (result == true) {
			m_mainView.setDirty(true);
		}
		redraw();
		return result;
	}

	public byte getSelectedStateType() {
		return m_SelectedCircles.get(0).getM_State().getM_type();
	}

	public int getNumofSelectedCircle() {
		if (m_SelectedCircles == null) {
			return 0;
		}
		return m_SelectedCircles.size();
	}

	public Circle_State getCirclebyName(String stateName) {
		for (Circle_State circle : m_Circles) {
			if (stateName.equals(circle.getM_State().getM_Name()))
				return circle;
		}
		return null;
	}

	public void setSelectedStateType(byte type) {
		m_mainView.getM_Automaton().modifyStateType(
				m_SelectedCircles.get(0).getM_State(), type);
		m_mainView.setDirty(true);
		redraw();
	}

	public boolean doAlign(byte direction) {
		if (m_SelectedCircles == null || m_SelectedCircles.size() < 2)
			return false;
		// m_Circles.removeAll(m_SelectedCircles);
		if (direction == SystemConstant.ALIGN_LEFT
				|| direction == SystemConstant.ALIGN_RIGHT) {
			Collections.sort(m_SelectedCircles, new CircleXComparator());
			int xIdx = direction == SystemConstant.ALIGN_LEFT ? 0
					: m_SelectedCircles.size() - 1;
			int x = m_SelectedCircles.get(xIdx).getCentre().x;
			for (Circle_State s : m_SelectedCircles) {
				s.getCentre().x = x;
				s.getOriginalCentre().x = x;
			}
		} else if (direction == SystemConstant.ALIGN_TOP
				|| direction == SystemConstant.ALIGN_BOTTOM) {
			Collections.sort(m_SelectedCircles, new CircleYComparator());
			int yIdx = direction == SystemConstant.ALIGN_TOP ? 0
					: m_SelectedCircles.size() - 1;
			int y = m_SelectedCircles.get(yIdx).getCentre().y;
			for (Circle_State s : m_SelectedCircles) {
				s.getCentre().y = y;
				s.getOriginalCentre().y = y;
			}
		}
		// m_Circles.addAll(m_SelectedCircles);
		m_mainView.setDirty(true);
		redraw();
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}

class CircleXComparator implements Comparator<Circle_State> {
	public int compare(Circle_State o1, Circle_State o2) {
		if (o1.getCentre().x > o2.getCentre().x)
			return 1;
		else if (o1.getCentre().x < o2.getCentre().x)
			return -1;
		else
			return 0;
	}
}

class CircleYComparator implements Comparator<Circle_State> {
	public int compare(Circle_State o1, Circle_State o2) {
		if (o1.getCentre().y > o2.getCentre().y)
			return 1;
		else if (o1.getCentre().y < o2.getCentre().y)
			return -1;
		else
			return 0;
	}
}
