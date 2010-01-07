package gui.figure;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;

public class TransitionFigure extends PolylineConnection {
	private Label label;
	private int anchorX, anchorY, dx, dy;

	public TransitionFigure() {
		this.label = new Label();
		setConnectionRouter(new BendpointConnectionRouter());
		this.setTargetDecoration(new PolygonDecoration());
		//label.setText("0,1");
		//label.setOpaque(true);
		this.add(label, new MidpointOffsetLocator(this, 0));
		label.addMouseListener(new MouseListener() {

			public void mouseDoubleClicked(MouseEvent me) {

			}

			public void mousePressed(MouseEvent me) {
				anchorX = me.x;
				anchorY = me.y;
				me.consume();
			}

			public void mouseReleased(MouseEvent me) {
				me.consume();
			}
		});

		label.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent me) {
				dx += me.x - anchorX;
				dy += me.y - anchorY;
				anchorX = me.x;
				anchorY = me.y;
				Object constraint = getLayoutManager().getConstraint(label);
				if (constraint instanceof MidpointOffsetLocator) {
					((MidpointOffsetLocator) constraint).setOffset(new Point(
							dx, dy));
					label.revalidate();
				}
				me.consume();
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent me) {
				// TODO Auto-generated method stub

			}

			public void mouseHover(MouseEvent me) {
				// TODO Auto-generated method stub

			}

			public void mouseMoved(MouseEvent me) {
				// TODO Auto-generated method stub

			}
		});
		this.label.setVisible(true);
	}

	public void setText(String transitionText) {
		this.label.setText(transitionText);
		//label.repaint();
		this.repaint();
	}

	public String getText() {
		return label.getText();
	}

}
