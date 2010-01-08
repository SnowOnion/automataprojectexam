package gui.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class StateModel extends AbstractModel {
	private String text = "p0";
	private int isInitial = 0;

	private int isAccept = 0;

	private Rectangle constraint;
	private Color color = ColorConstants.orange;
	private Point position = new Point(10, 80);

	public static final String P_CONSTRAINT = "_constraint";
	public static final String P_TEXT = "_text";
	public static final String P_COLOR = "_color";
	public static final String P_POSITION = "_position";
	public static final String P_SOURCE_CONNECTION = "_source_connection";
	public static final String P_TARGET_CONNECTION = "_target_connection";
	public static final String P_INITIAL = "_initial";
	public static final String P_ACCEPT = "_accept";
	public static final String P_ERROR = "_error";

	private List sourceConnectionList = new ArrayList();
	private List targetConnectionList = new ArrayList();
	private ContentsModel parent;

	public int getIsInitial() {
		return isInitial;
	}

	public void setIsInitial(int isInitial) {
		if (this.isInitial == 0 && isInitial == 1
				&& getParent().getInitialState() != null) {
			this.firePropertyChange(P_ERROR, null,
					"An initial state already exists!");
			return;
		}
		if (this.isInitial != isInitial) {
			this.isInitial = isInitial;
			if (isInitial == 1)
				getParent().setInitialState(this);
			else
				getParent().setInitialState(null);
			this.firePropertyChange(P_INITIAL, null, isInitial);
		}
	}

	public int getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(int isAccept) {
		this.isAccept = isAccept;
		this.firePropertyChange(P_ACCEPT, null, isAccept);
	}

	public ContentsModel getParent() {
		return parent;
	}

	public void setParent(Object parent) {
		this.parent = (ContentsModel) parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (this.text.equals(text))
			return;
		this.text = text;
		firePropertyChange(P_TEXT, null, text);
	}

	public Rectangle getConstraint() {
		return constraint;
	}

	public void setConstraint(Rectangle constraint) {
		this.constraint = constraint;
		firePropertyChange(P_CONSTRAINT, null, constraint);
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptor = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(P_COLOR, "color"),
				new TextPropertyDescriptor(P_TEXT, "content"),
				new TextPropertyDescriptor(P_POSITION, "position"),
				new ComboBoxPropertyDescriptor(P_INITIAL, "Initial",
						new String[] { "False", "True" }),
				new ComboBoxPropertyDescriptor(P_ACCEPT, "Accept",
						new String[] { "False", "True" }) };
		// java.util.Arrays.sort(descriptor,new MyComparator());
		return descriptor;
	}

	public Object getPropertyValue(Object id) {
		if (id.equals(P_TEXT)) {
			return text;
		} else if (id.equals(P_COLOR)) {
			return color;
		} else if (id.equals(P_POSITION)) {
			return "(" + position.x + "," + position.y + ")";
		} else if (id.equals(P_INITIAL)) {
			return new Integer(isInitial);
		} else if (id.equals(P_ACCEPT)) {
			return new Integer(isAccept);
		}
		return null;
	}

	public boolean isPropertySet(Object id) {
		if (id.equals(P_TEXT) || id.equals(P_COLOR) || id.equals(P_POSITION)
				|| id.equals(P_INITIAL) || id.equals(P_ACCEPT)) {
			return true;
		} else {
			return false;
		}
	}

	public void setPropertyValue(Object id, Object value) {
		if (id.equals(P_TEXT)) {
			setText((String) value);
		} else if (id.equals(P_COLOR)) {
			setColor((Color) value);
		} else if (id.equals(P_POSITION)) {
			String text = (String) value;
			int x = Integer.parseInt(text.substring(1, text.indexOf(',')));
			int y = Integer.parseInt(text.substring(text.indexOf(',') + 1, text
					.length() - 1));
			setPosition(new Point(x, y));
		} else if (id.equals(P_INITIAL)) {
			setIsInitial((Integer) value);
		} else if (id.equals(P_ACCEPT)) {
			setIsAccept((Integer) value);
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setPosition(Point position) {
		this.position = position;
		firePropertyChange(P_POSITION, null, position);
	}

	public Point getPosition() {
		return position;
	}

	public void addTargetConnection(Object connx) {
		targetConnectionList.add(connx);
		firePropertyChange(P_TARGET_CONNECTION, null, null);
	}

	public void addSourceConnection(Object connx) {
		sourceConnectionList.add(connx);
		firePropertyChange(P_SOURCE_CONNECTION, null, null);
	}

	public void removeSourceConnection(Object connx) {
		sourceConnectionList.remove(connx);
		firePropertyChange(P_SOURCE_CONNECTION, null, null);
	}

	public void removeTargetConnection(Object connx) {
		targetConnectionList.remove(connx);
		firePropertyChange(P_TARGET_CONNECTION, null, null);
	}

	public List getModelSourceConnections() {
		return sourceConnectionList;
	}

	public List getModelTargetConnections() {
		return targetConnectionList;
	}

	public boolean isInitial() {
		return isInitial == 1;
	}

	public boolean isAccept() {
		return isAccept == 1;
	}

}

class MyComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		if (o1 instanceof TextPropertyDescriptor
				&& o2 instanceof TextPropertyDescriptor) {
			TextPropertyDescriptor tpd1 = (TextPropertyDescriptor) o1;
			TextPropertyDescriptor tpd2 = (TextPropertyDescriptor) o2;
			String id1 = tpd1.getId().toString();
			String id2 = tpd2.getId().toString();
			// return id1.compareTo(id2);
			return id2.compareTo(id1);
		}
		return 0;
	}

}
