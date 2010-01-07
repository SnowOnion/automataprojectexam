package gui.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class AbstractConnectionModel extends AbstractModel {
	private StateModel source, target;
	private List bendpoints = new ArrayList();
	public static final String P_BEND_POINT = "_bend_point";
	private String condition = "?";

	public static final String P_SOURCE = "_source";
	public static final String P_TARGET = "_target";
	public static final String P_CONDITION = "_condition";

	public void setCondition(String condition) {
		this.condition = condition;
		firePropertyChange(P_CONDITION, null, condition);
	}

	public String getCondition() {
		return this.condition;
	}

	public void addBendpoint(int index, Point point) {
		bendpoints.add(index, point);
		firePropertyChange(P_BEND_POINT, null, null);
	}

	public List getBendpoints() {
		return this.bendpoints;
	}

	public void removeBendpoint(int index) {
		bendpoints.remove(index);
		firePropertyChange(P_BEND_POINT, null, null);
	}

	public void replaceBendpoint(int index, Point point) {
		bendpoints.set(index, point);
		firePropertyChange(P_BEND_POINT, null, null);
	}

	public void attachSource() {
		if (!source.getModelSourceConnections().contains(this)) {
			source.addSourceConnection(this);
		}
	}

	public void attachTarget() {
		if (!target.getModelTargetConnections().contains(this)) {
			target.addTargetConnection(this);
		}
	}

	public void detachSource() {
		source.removeSourceConnection(this);
	}

	public void detachTarget() {
		target.removeTargetConnection(this);
	}

	public StateModel getSource() {
		return source;
	}

	public void setSource(StateModel source) {
		this.source = source;
		firePropertyChange(P_SOURCE, null, source);
	}

	public StateModel getTarget() {
		return target;
	}

	public void setTarget(StateModel target) {
		this.target = target;
		firePropertyChange(P_TARGET, null, target);
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(P_CONDITION, "condition"),
				new PropertyDescriptor(P_SOURCE, "source"),
				new PropertyDescriptor(P_TARGET, "target"),
				new TextPropertyDescriptor(P_BEND_POINT, "bendPoint") };
		return descriptors;
	}

	public Object getPropertyValue(Object id) {
		if (id.equals(P_CONDITION)) {
			return condition;
		} else if (id.equals(P_SOURCE)) {
			return source.getText();
		} else if (id.equals(P_TARGET)) {
			return target.getText();
		} else if (id.equals(P_BEND_POINT)) {
			return bendpoints.toString();
		}
		return null;
	}

	public boolean isPropertySet(Object id) {
		if (id.equals(P_CONDITION) || id.equals(P_TARGET)
				|| id.equals(P_TARGET) || id.equals(P_BEND_POINT))
			return true;
		else
			return false;
	}

	public void setPropertyValue(Object id, Object value) {
		if (id.equals(P_CONDITION)) {
			setCondition((String) value);
		} else if (id.equals(P_SOURCE)) {
			setSource((StateModel) value);
		} else if (id.equals(P_TARGET)) {
			setTarget((StateModel) value);
		} else if (id.equals(P_BEND_POINT)) {
			// ==========================================
		}
	}

}
