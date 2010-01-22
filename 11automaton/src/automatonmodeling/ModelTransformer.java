package automatonmodeling;

import gui.help.AutomatonException;
import gui.help.AutomatonType;
import gui.model.AbstractConnectionModel;
import gui.model.AbstractModel;
import gui.model.ContentsModel;
import gui.model.StateModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import automaton.Automaton;
import automaton.AutomatonConstant;
import automaton.AutomatonDFA;
import automaton.AutomatonNFA;
import automaton.AutomatonPDA;
import automaton.Nail;
import automaton.State;
import automaton.Transition;
import automaton.TransitionNFA;
import automaton.TransitionPDA;
import automaton.TransitionPDACondition;

public class ModelTransformer {
	/****************************************************************
	 * 
	 * @return
	 */
	public static ContentsModel getModelFromAutomaton(Automaton automaton) {
		System.out.println("Automaton:" + automaton);
		ContentsModel contents = new ContentsModel();
		contents.setType(AutomatonType.valueOf(automaton.getAutomatonType()));
		for (State stateobj : automaton.getStates().values()) {
			StateModel child = new StateModel();
			child.setText(stateobj.getStateId());
			child.setPosition(new Point(stateobj.getStateNail().getNailX(),
					stateobj.getStateNail().getNailY()));
			child.setIsAccept(stateobj.getStateType());
			child.setConstraint(new Rectangle(stateobj.getStateNail()
					.getNailX(), stateobj.getStateNail().getNailY(), 50, 50));
			contents.addChild(child);
			child.setParent(contents);
		}

		for (Transition trans : automaton.getTransitions()) {
			AbstractConnectionModel connection = new AbstractConnectionModel();
			// System.out.println(contents.getChild(trans.getFromState().getStateId()));
			connection.setSource(contents.getChild(trans.getFromState()
					.getStateId()));
			connection.setTarget(contents.getChild(trans.getToState()
					.getStateId()));
			connection.attachSource();
			connection.attachTarget();
			// if (contents.getType()==
			// AutomatonType.NFA||contents.getType()==AutomatonType.DFA)
			// connection.setCondition((TransitionNFA)trans.)
			if (contents.getType() == AutomatonType.NFA
					|| contents.getType() == AutomatonType.DFA) {
				TransitionNFA transnfa = (TransitionNFA) trans;
				String condtext = "";
				for (String cond : transnfa.getTransitionConditions())
					condtext += cond + ",";
				connection.setCondition(condtext.substring(0,
						condtext.length() - 1));
			} else {
				TransitionPDA transnfa = (TransitionPDA) trans;
				String condtext = "";
				for (TransitionPDACondition cond : transnfa
						.getTransitionConditions())
					condtext += cond.toString() + ";";
				System.out.println(condtext);
				connection.setCondition(condtext.substring(0,
						condtext.length() - 1));
			}
			Iterator <Nail>iterator = trans.getNails().iterator();
			int index = 0;
			while(iterator.hasNext()){
				Nail nailTemp = iterator.next();
				connection.addBendpoint(index, new Point(nailTemp.getNailX(),
						nailTemp.getNailY()));
				index++;
			}
			/*
			Nail[] nails = (Nail[]) trans.getNails().toArray();
			for (int i = 0; i < nails.length; i++) {
				connection.addBendpoint(i, new Point(nails[i].getNailX(),
						nails[i].getNailY()));
			}
			*/
			contents.addConnection(connection);
		}

		contents.setInitialState(contents.getChild(automaton.getInitialState()
				.getStateId()));
		contents.getInitialState().isInitial = 1;
		return contents;
	}

	/*********************************************************************
	 * To be implmented.......... This method will create an Automaton Class
	 * according to the parameter Model
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Automaton getAutomatonFromModel(AbstractModel model)
			throws AutomatonException {
		Automaton automaton = null;
		ContentsModel contents = null;
		if (model instanceof ContentsModel)
			contents = (ContentsModel) model;
		else
			throw new AutomatonException("Model type not match");

		AutomatonType type = contents.getType();
		automaton = getAutomaton(type);

		for (Object stateobj : contents.getChildren()) {
			StateModel stateModel = (StateModel) stateobj;
			State state = new State(stateModel.getText());
			Point statePosition = stateModel.getPosition();
			state.setStateNail(new Nail(statePosition.x, statePosition.y));
			System.out.println(state + "position" + state.getStateNail());
			automaton.addState(state);
			if (stateModel.isInitial())
				automaton.setInitialState(state);
			if (stateModel.isAccept())
				state.setStateType(AutomatonConstant.STATETYPE_ACCEPTED);
		}
		System.out.println("States added:" + automaton);

		for (AbstractConnectionModel conn : contents.getConnections()) {
			Transition transition = Transition.getTransitionOfType(type);
			System.out.println("Converting transition :" + conn.getCondition());
			transition.setConditionsFromRawString(automaton, conn
					.getCondition());

			List<Point> bendpoints = conn.getBendpoints();
			for (Point bendpoint : bendpoints) {
				transition.addNail(new Nail(bendpoint.x, bendpoint.y));
			}
			transition.setFromState(automaton.getState(conn.getSource()
					.getText()));
			transition.setToState(automaton
					.getState(conn.getTarget().getText()));
			automaton.addTransition(transition);
		}

		return automaton;
	}

	public static Automaton getAutomaton(AutomatonType type) {
		Automaton automaton = null;
		if (type == AutomatonType.NFA) {
			automaton = new AutomatonNFA();
		} else if (type == AutomatonType.DFA)
			automaton = new AutomatonDFA();
		else
			automaton = new AutomatonPDA();
		return automaton;
	}

}
