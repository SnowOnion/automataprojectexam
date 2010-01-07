package gui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class AbstractModel implements IPropertySource {
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		//System.out.println("add");
		listeners.addPropertyChangeListener(listener);
	}
	public void firePropertyChange(String propName,Object oldValue,Object newValue){
		//System.out.println("fire");
		listeners.firePropertyChange(propName,oldValue,newValue);
	}
	public void removePropertyChange(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
		//System.out.println("remove");
	}
	public Object getEditableValue() {
		return this;
	}
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[0];
	}
	public Object getPropertyValue(Object id) {
		return null;
	}
	public boolean isPropertySet(Object id) {
		return false;
	}
	public void resetPropertyValue(Object id) {
	}
	public void setPropertyValue(Object id, Object value) {
	}
}
