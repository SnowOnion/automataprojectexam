package gui.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;

public class CustomCellEditorLocator implements CellEditorLocator {
	private IFigure figure;
	public CustomCellEditorLocator(IFigure figure){
		this.figure = figure;
	}
	public void relocate(CellEditor celleditor) {
		Text text = (Text)celleditor.getControl();
		Rectangle rect = figure.getBounds().getCopy();
		figure.translateToAbsolute(rect);
		text.setBounds(rect.x,rect.y,rect.width,rect.height);
	}
	

}
