/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.ui;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.event.MouseInputListener;

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
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;

import com.swtdesigner.ResourceManager;

import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.State;

/**
 * @author David
 * 
 */
public class Canvas_Automaton extends Canvas implements MouseListener,
		MouseMoveListener, MouseTrackListener {

	private ArrayList<Circle_State> m_Circles;
	private ArrayList<Circle_State> m_SelectedCircles;
	private View_Main m_mainView = null;

	private Point startMovingPoint = null;
	private boolean isMoving = false;

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
	}

	public void paint(GC gc) {
		for (Circle_State circle : m_Circles) {
			circle.paint(gc, Circle_State.IMAGE_TYPE_COMMON);
		}

		for (Circle_State circle : m_SelectedCircles) {
			circle.paint(gc, isMoving ? Circle_State.IMAGE_TYPE_MOVING
					: Circle_State.IMAGE_TYPE_SELECTED);
		}
		
		paintAddition(gc);
	}

	/**
	 * 用于绘制自动机的其他额外组件，包括开始状态的箭头和结束状态的多余中心圈。
	 * @param gc
	 */
	private void paintAddition(GC gc) {
		ArrayList<State> finalStates = m_mainView.getM_Automaton().getM_FinalStates();
		for (State state : finalStates){
			Circle_State circle = findCircle(state);
			if (circle != null){
				circle.paintFSAddition(gc, m_SelectedCircles.contains(circle)&&(!isMoving));
			}
		}
		State s = m_mainView.getM_Automaton().getM_StartState();
		if (s != null){
			Circle_State circle = findCircle(s);
			if (circle != null){
				circle.paintISArrow(gc, m_SelectedCircles.contains(circle)&&(!isMoving));
			}
		}
	}
	
	private boolean isSelected(State s){
		for (Circle_State circle : m_SelectedCircles){
			if (circle.getM_State().equals(s))
				return true;
		}
		return false;
	}
	
	private Circle_State findCircle(State s){
		for (Circle_State circle : m_Circles){
			if (circle.getM_State().equals(s))
				return circle;
		}
		for (Circle_State circle : m_SelectedCircles){
			if (circle.getM_State().equals(s))
				return circle;
		}
		return null;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("click");
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
		byte toolType = getToolType();
		switch (toolType) {
		case View_ToolBox.TOOLTYPE_SELECT:
			// 按下鼠标左键
			if ((e.button == 1)) {
				Circle_State circle = getCurrSelected(e.x, e.y);
				if (circle != null) {
					// 没有按住Ctrl键的单选情况处理
					if (e.stateMask != SWT.CTRL) {
						m_SelectedCircles.clear();
						m_SelectedCircles.add(circle);
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
			} else if (e.button == 3) { // 鼠标右键菜单弹出
				Circle_State circle = getCurrSelected(e.x, e.y);
				if (circle != null) {
					m_SelectedCircles.clear();
					m_SelectedCircles.add(circle);
					Menu_StateRightClick rightmenu = new Menu_StateRightClick(
							this, SWT.POP_UP);
					Point p = getScreemLocation();
					rightmenu.showMenu(p.x + e.x + 6,  p.y + e.y + 52);
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

	@Override
	public void mouseMove(MouseEvent e) {
		byte toolType = getToolType();
		switch (toolType) {
		case View_ToolBox.TOOLTYPE_SELECT:
			// 如果不是左键或者选中的状态为空则不进行移动操作。
			if ((e.stateMask & SWT.BUTTON1) == 0 || m_SelectedCircles == null
					|| m_SelectedCircles.size() == 0)
				break;
			isMoving = true;
			int differX = e.x - startMovingPoint.x;
			int differY = e.y - startMovingPoint.y;
			for (Circle_State circle : m_SelectedCircles) {
				circle.getCentre().x = circle.getOriginalCentre().x + differX;
				circle.getCentre().y = circle.getOriginalCentre().y + differY;
			}
			redraw();
			break;
		case View_ToolBox.TOOLTYPE_STATE:
			// System.out.print("Wrong!");
			break;
		case View_ToolBox.TOOLTYPE_TRANSFORM:
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
			setCursor(new Cursor(null, SWT.CURSOR_ARROW));
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

	public void doDelete() {
		m_Circles.removeAll(m_SelectedCircles);
		for (Circle_State circle : m_SelectedCircles) {
			m_mainView.getM_Automaton().getM_States().remove(
					circle.getM_State());

		}
		m_SelectedCircles.clear();
		redraw();
	}

	public String getSelectedStateType(){
		return m_mainView.getM_Automaton().getStateType(m_SelectedCircles.get(0).getM_State());
	}
	
	public void setSelectedStateType(String type){
		String oldType = getSelectedStateType();
		State s = m_SelectedCircles.get(0).getM_State();
		if (!oldType.equals(type)){
			m_mainView.getM_Automaton().removeState(s);
			if (type.equals(AutomatonConst.STATE_TYPE_FINAL)){
				m_mainView.getM_Automaton().getM_FinalStates().add(s);
			}
			else if (type.equals(AutomatonConst.STATE_TYPE_INITIAL)){
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

}
