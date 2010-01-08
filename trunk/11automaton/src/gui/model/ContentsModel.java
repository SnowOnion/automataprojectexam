package gui.model;

import gui.help.AutomatonType;

import java.util.ArrayList;
import java.util.List;

public class ContentsModel extends AbstractModel {
	private int newest_id = 0;
	@SuppressWarnings("unchecked")
	private List children = new ArrayList();
	private List<AbstractConnectionModel> connections = new ArrayList<AbstractConnectionModel>();
	private StateModel initialState = null;
	private AutomatonType type;

	public static final String P_CHILDREN = "_children";

	@SuppressWarnings("unchecked")
	public void addChild(Object child) {
		children.add(child);
		firePropertyChange(P_CHILDREN, null, null);
		((StateModel) child).setText("p" + newest_id);
		newest_id++;
	}

	@SuppressWarnings("unchecked")
	public List getChildren() {
		return children;
	}

	public void removeChild(Object child) {
		StateModel state = (StateModel) child;
		children.remove(child);
		for (int i = 0; i < connections.size();) {
			AbstractConnectionModel conn = connections.get(i);
			if (conn.getSource() == state || conn.getTarget() == state) {
				System.out.println("Remove " + conn.getSource().getText()
						+ " to " + conn.getTarget().getText());
				connections.remove(conn);
			} else
				i++;
		}
		firePropertyChange(P_CHILDREN, null, null);
	}

	public void addConnection(Object conn) {
		connections.add((AbstractConnectionModel) conn);
	}

	public void removeConnection(Object conn) {
		connections.remove(conn);
	}

	public String toString() {
		String text = "";
		text += "States:";
		for (Object state : children)
			text += ((StateModel) state).getText() + " ";
		text += "\n";
		text += "Transitions:";
		for (AbstractConnectionModel conn : connections)
			text += ((AbstractConnectionModel) conn).getSource().getText()
					+ " to "
					+ ((AbstractConnectionModel) conn).getTarget().getText()
					+ " ";
		text += "\nInitial State:";
		text += initialState == null ? null : initialState.getText();
		return text;
	}

	public StateModel getInitialState() {
		return initialState;
	}

	public void setInitialState(StateModel initialState) {
		this.initialState = initialState;
	}

	public int getNewest_id() {
		return newest_id;
	}

	public AutomatonType getType() {
		return type;
	}

	public void setType(AutomatonType type) {
		this.type = type;
	}

	public List<AbstractConnectionModel> getConnections() {
		return connections;
	}
}
