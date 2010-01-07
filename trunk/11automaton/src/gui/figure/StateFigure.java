package gui.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class StateFigure extends Ellipse {

	private Label label;
	private Ellipse circle;

	public StateFigure() {
		circle = null;
		this.label = new Label();
		this.add(label);
		this.setSize(50, 50);
	}

	public String getText() {
		return this.label.getText();
	}

	public Rectangle getTextBounds() {
		return this.label.getTextBounds();
	}

	public void setText(String text) {
		this.label.setText(text);
		this.repaint();
	}

	// ------------------------------------------------------------------------
	// Overridden methods from Figure

	public void setBounds(Rectangle rect) {
		super.setBounds(rect);
		this.label.setBounds(rect);
	}

	public void setPosition(Point position) {
		this.setLocation(position);
		this.repaint();
	}

	public Point getPosition() {
		return this.getLocation();
	}

	public void changeType2Initial() {
		this.setBackgroundColor(ColorConstants.yellow);
		if (circle != null)
			circle.setBackgroundColor(ColorConstants.yellow);
		this.repaint();
	}

	public void changeType2Accept() {
		circle = new Ellipse();
		int x = this.getPosition().x + 5;
		int y = this.getPosition().y + 5;
		circle.setBounds(new Rectangle(x, y, 40, 40));
		this.setOpaque(false);
		this.add(circle);
		circle.add(label);

		if (this.getBackgroundColor().equals(ColorConstants.yellow))
			circle.setBackgroundColor(ColorConstants.yellow);
		this.repaint();
	}

	public void changeType2NotInitial() {
		this.setBackgroundColor(ColorConstants.white);
		if (circle != null)
			circle.setBackgroundColor(ColorConstants.white);
		this.repaint();
	}

	public void changeType2NotAccept() {
		this.remove(circle);
		circle = null;
		this.add(label);
		this.repaint();
	}

}
