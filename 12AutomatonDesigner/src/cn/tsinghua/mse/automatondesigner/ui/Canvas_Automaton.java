/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

import javax.swing.event.MouseInputListener;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.internal.handlers.WizardHandler.New;

import automatondesigner.SystemConstant;

import com.swtdesigner.ResourceManager;

import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.State;
import cn.tsinghua.mse.automatondesigner.dataobject.TransCondition;
import cn.tsinghua.mse.automatondesigner.dataobject.TransFunction;
import cn.tsinghua.mse.automatondesigner.tools.CommonTool;

/**
 * @author David
 * 
 */
public class Canvas_Automaton extends Canvas implements MouseListener,
		MouseMoveListener, MouseTrackListener {

	private ArrayList<Circle_State> m_Circles;
	private ArrayList<Circle_State> m_SelectedCircles;
	private View_Main m_mainView = null;

	private Rectangle selectRectangle = null;

	private Point startMovingPoint = null;
	private boolean isMoving = false;

	private Circle_State drawingPLStart = null;
	private ArrayList<Point> currectPolyline = null;
	private ArrayList<Polyline_Trans> m_Poylines;
	private Point currectPnt = null;

	/**
	 * @param parent
	 * @param style
	 */
	public Canvas_Automaton(Composite parent, int style, View_Main mainView) {
		super(parent, style);
		m_Circles = new ArrayList<Circle_State>();
		m_SelectedCircles = new ArrayList<Circle_State>();
		this.addMouseListener(this);
		this.addMouseMoveListener(this);
		this.addMouseTrackListener(this);
		m_mainView = mainView;
		drawingPLStart = null;
		currectPolyline = new ArrayList<Point>();
		m_Poylines = new ArrayList<Polyline_Trans>();
		currectPnt = new Point(-100, -100);
	}

	public void paint(GC gc) {
		for (Polyline_Trans trans : m_Poylines) {
			trans.paint(gc, Circle_State.IMAGE_TYPE_COMMON, "");
		}
		if (currectPolyline != null && drawingPLStart != null) {
			if (currectPolyline.size() == 0){
				if (currectPnt.x != -100)
					gc.drawLine(currectPnt.x, currectPnt.y, drawingPLStart.getCentre().x, drawingPLStart.getCentre().y);
			}
			else{
				gc.drawLine(currectPolyline.get(0).x, currectPolyline.get(0).y, drawingPLStart.getCentre().x, drawingPLStart.getCentre().y);
				gc.drawPolyline(CommonTool.pointArrayToIntArray(currectPolyline));
				Point p = currectPolyline.get(currectPolyline.size() - 1);
				if (currectPnt.x != -100)
					gc.drawLine(currectPnt.x, currectPnt.y, p.x, p.y);
			}
			
			
		}

		for (Circle_State circle : m_Circles) {
			circle.paint(gc, Circle_State.IMAGE_TYPE_COMMON, m_mainView
					.getM_Automaton().getStateType(circle.getM_State()));
		}

		for (Circle_State circle : m_SelectedCircles) {
			circle.paint(gc, Circle_State.IMAGE_TYPE_SELECTED, m_mainView
					.getM_Automaton().getStateType(circle.getM_State()));
		}

		// paintAddition(gc);

		if (selectRectangle != null && selectRectangle.width != 0) {
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
			gc.drawRectangle(selectRectangle.x, selectRectangle.y,
					selectRectangle.width, selectRectangle.height);
		}
	}

	/**
	 * 用于绘制自动机的其他额外组件，包括开始状态的箭头和结束状态的多余中心圈。
	 * 
	 * @param gc
	 */
	private void paintAddition(GC gc) {
		ArrayList<State> finalStates = m_mainView.getM_Automaton()
				.getM_FinalStates();
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
			break;
		case View_ToolBox.TOOLTYPE_STATE:
			break;
		case View_ToolBox.TOOLTYPE_TRANSFORM:
			if (drawingPLStart == null)
				break;
			Circle_State circle = getCurrSelected(e.x, e.y);
			if (circle != null) {
				if (currectPolyline.size() == 0 && circle.equals(drawingPLStart)){
//					boolean re = MessageDialog.openQuestion(m_mainView.getMainWindow().getShell(), "询问", "您是要创建自环吗？");
//					if(!re)
						return;
				}
				TransFunction func = new TransFunction(drawingPLStart
						.getM_State(), circle.getM_State(),
						new ArrayList<TransCondition>());
				m_mainView.getM_Automaton().addTransFunction(func);
				m_Poylines.add(new Polyline_Trans(func, CommonTool
						.PointsClone(currectPolyline), drawingPLStart, circle));
				drawingPLStart = null;
				currectPolyline.clear();
				currectPnt.x = -100;
			}else{
				MessageDialog.openWarning(m_mainView.getMainWindow().getShell(), "提示", "终结点必须为结束状态！");
			}
			break;
		default:
			break;
		}
	}

	private Circle_State getCurrSelected(int x, int y) {
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
				Circle_State circle = getCurrSelected(e.x, e.y);
				if (circle != null) {
					// 没有按住Ctrl键的单选情况处理
					if (e.stateMask != SWT.CTRL) {
						if (!m_SelectedCircles.contains(circle)) {
							m_SelectedCircles.clear();
							m_SelectedCircles.add(circle);
						}
					} else {
						if (!m_SelectedCircles.contains(circle)) {
							m_SelectedCircles.add(circle);
						} else {
							m_SelectedCircles.remove(circle);
						}
					}
					m_Circles.removeAll(m_SelectedCircles);
					startMovingPoint = new Point(e.x, e.y);
				} else {
					m_SelectedCircles.clear();
					selectRectangle = new Rectangle(e.x, e.y, 0, 0);
				}
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
				m_Circles.addAll(m_SelectedCircles);
				if (isMoving == true) {
					isMoving = false;
					for (Circle_State circle : m_SelectedCircles) {
						circle.getOriginalCentre().x = circle.getCentre().x;
						circle.getOriginalCentre().y = circle.getCentre().y;
					}
				}
				selectRectangle = null;
			} else if (e.button == 3) { // 鼠标右键菜单弹出
				Circle_State circle = getCurrSelected(e.x, e.y);
				if (circle != null) {
					m_SelectedCircles.clear();
					m_SelectedCircles.add(circle);
					Menu_StateRightClick rightmenu = new Menu_StateRightClick(
							this, SWT.POP_UP);
					Point p = getScreemLocation();
					rightmenu.showMenu(p.x + e.x + 6, p.y + e.y + 52);
				}

			}
			redraw();
			break;
		case View_ToolBox.TOOLTYPE_STATE:
			// 新加状态
			if (e.button == 1) {
				Circle_State newCircle = new Circle_State(new State(""),
						new Point(e.x, e.y), AutomatonConst.STATE_TYPE_COMMON);
				m_Circles.add(newCircle);
				m_SelectedCircles.clear();
				m_SelectedCircles.add(newCircle);
				// 将新加的状态放入自动机中
				m_mainView.getM_Automaton().getM_States().add(
						newCircle.getM_State());
				this.redraw();
				startMovingPoint = null;
			} else if (e.button == 3) {
				setToolBoxSelected();
				setCursor(new Cursor(null, SWT.CURSOR_ARROW));
			}
			break;
		case View_ToolBox.TOOLTYPE_TRANSFORM:
			//如果是鼠标左键
			if (e.button == 1) {
				Circle_State circle = getCurrSelected(e.x, e.y);
				if (drawingPLStart == null) {	//如果是刚刚开始绘制的话
					if (circle == null)
						break;
					drawingPLStart = circle;
				} else {
					if (circle == null)	//绘制拐点
						currectPolyline.add(new Point(e.x, e.y));
					else {
						if (currectPolyline.size() == 0 && circle.equals(drawingPLStart)){
							boolean re = MessageDialog.openQuestion(m_mainView.getMainWindow().getShell(), "询问", "您是要创建自环吗？");
							if(!re)
								return;
						}
						TransFunction func = new TransFunction(drawingPLStart
								.getM_State(), circle.getM_State(),
								new ArrayList<TransCondition>());
						m_mainView.getM_Automaton().addTransFunction(func);
						m_Poylines.add(new Polyline_Trans(func, CommonTool
								.PointsClone(currectPolyline), drawingPLStart, circle));
						currectPolyline.clear();
						drawingPLStart = circle;
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
		Rectangle temp = new Rectangle(selectRectangle.x, selectRectangle.y,
				Math.abs(selectRectangle.width), Math
						.abs(selectRectangle.height));
		if (selectRectangle.width < 0) {
			temp.x = selectRectangle.x + selectRectangle.width;
		}
		if (selectRectangle.height < 0) {
			temp.y = selectRectangle.y + selectRectangle.height;
		}
		for (Circle_State c : m_Circles) {
			if (temp.contains(c.getCentre()))
				m_SelectedCircles.add(c);
		}
	}

	@Override
	public void mouseMove(MouseEvent e) {
		byte toolType = getToolType();
		switch (toolType) {
		case View_ToolBox.TOOLTYPE_SELECT:
			// 如果不是左键或者选中的状态为空则不进行移动操作。
			if ((e.stateMask & SWT.BUTTON1) == 0)
				break;
			if (selectRectangle != null) {
				selectRectangle.width = e.x - selectRectangle.x;
				selectRectangle.height = e.y - selectRectangle.y;
				setCircleInRect();
			} else if (!(m_SelectedCircles == null || m_SelectedCircles.size() == 0)) {
				isMoving = true;
				int differX = e.x - startMovingPoint.x;
				int differY = e.y - startMovingPoint.y;
				for (Circle_State circle : m_SelectedCircles) {
					circle.getCentre().x = circle.getOriginalCentre().x
							+ differX;
					circle.getCentre().y = circle.getOriginalCentre().y
							+ differY;
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
	 * @return
	 */
	public boolean doDelete() {
		if (m_SelectedCircles == null || m_SelectedCircles.size() == 0)
			return false;
		m_Circles.removeAll(m_SelectedCircles);
		for (Circle_State circle : m_SelectedCircles) {
			m_mainView.getM_Automaton().getM_States().remove(
					circle.getM_State());
		}
		m_SelectedCircles.clear();
		redraw();
		return true;
	}

	public String getSelectedStateType() {
		return m_mainView.getM_Automaton().getStateType(
				m_SelectedCircles.get(0).getM_State());
	}

	public void setSelectedStateType(String type) {
		String oldType = getSelectedStateType();
		State s = m_SelectedCircles.get(0).getM_State();
		if (!oldType.equals(type)) {
			m_mainView.getM_Automaton().removeState(s);
			if (type.equals(AutomatonConst.STATE_TYPE_FINAL)) {
				m_mainView.getM_Automaton().getM_FinalStates().add(s);
			} else if (type.equals(AutomatonConst.STATE_TYPE_INITIAL)) {
				m_mainView.getM_Automaton().setM_StartState(s);
			} else if (type.equals(AutomatonConst.STATE_TYPE_INI_FINAL)) {
				m_mainView.getM_Automaton().getM_FinalStates().add(s);
				m_mainView.getM_Automaton().setM_StartState(s);
			}
			m_mainView.getM_Automaton().getM_States().add(s);
		}
		redraw();
	}

	public static void main(String[] args) {
		ArrayList<State> a = new ArrayList<State>();
		a.add(new State(""));
		State b = new State("");
		System.out.println(a.remove(b));
		System.out.println(a.size());
	}

	public boolean doAlign(byte direction) {
		if (m_SelectedCircles == null || m_SelectedCircles.size() < 2)
			return false;
		//m_Circles.removeAll(m_SelectedCircles);
		if (direction == SystemConstant.ALIGN_LEFT
				|| direction == SystemConstant.ALIGN_RIGHT) {
			Collections.sort(m_SelectedCircles, new CircleXComparator());
			int xIdx = direction == SystemConstant.ALIGN_LEFT ? 0 : m_SelectedCircles.size()-1;
			int x = m_SelectedCircles.get(xIdx).getCentre().x;
			for (Circle_State s : m_SelectedCircles){
				s.getCentre().x = x;
				s.getOriginalCentre().x = x;
			}
		} 
		else if (direction == SystemConstant.ALIGN_TOP
				|| direction == SystemConstant.ALIGN_BOTTOM){
			Collections.sort(m_SelectedCircles, new CircleYComparator());
			int yIdx = direction == SystemConstant.ALIGN_TOP ? 0 : m_SelectedCircles.size()-1;
			int y = m_SelectedCircles.get(yIdx).getCentre().y;
			for (Circle_State s : m_SelectedCircles){
				s.getCentre().y = y;
				s.getOriginalCentre().y = y;
			}
		}
		//m_Circles.addAll(m_SelectedCircles);
		redraw();
		return true;
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
