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
			//if ((e.stateMask & SWT.BUTTON1) != 0) {
				Circle_State circle = getCurrSelected(e.x, e.y);
				if (circle != null) {
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
			//}
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

	@Override
	public void mouseUp(MouseEvent e) {
		byte toolType = getToolType();
		switch (toolType) {
		case View_ToolBox.TOOLTYPE_SELECT:
			m_Circles.addAll(m_SelectedCircles);
			if (isMoving == true) {
				isMoving = false;
				for (Circle_State circle : m_SelectedCircles) {
					circle.getOriginalCentre().x = circle.getCentre().x;
					circle.getOriginalCentre().y = circle.getCentre().y;
				}
			}
			redraw();
			break;
		case View_ToolBox.TOOLTYPE_STATE:
			Circle_State newCircle = new Circle_State(new State(""), new Point(
					e.x, e.y), AutomatonConst.STATE_TYPE_COMMON);
			m_Circles.add(newCircle);
			m_SelectedCircles.clear();
			m_SelectedCircles.add(newCircle);
			this.redraw();
			startMovingPoint = null;
			break;
		case View_ToolBox.TOOLTYPE_TRANSFORM:
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseMove(MouseEvent e) {
		byte toolType = getToolType();
		switch (toolType) {
		case View_ToolBox.TOOLTYPE_SELECT:
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
		m_SelectedCircles.clear();
		redraw();
	}

}
