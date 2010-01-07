package gui.figure;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolylineConnection;

public class PDATransitionFigure extends PolylineConnection {
	final LabelEx label = new LabelEx("?");

	public PDATransitionFigure() {
		setConnectionRouter(new BendpointConnectionRouter());
		label.setOpaque(true);
		add(label, new MidpointLocator(this, 0));
		label.setVisible(true);
	}

	public void setText(String transitionText) {
		label.setText(transitionText);
		label.repaint();
		this.repaint();
	}

	public String getText() {
		return label.getText();
	}
}
