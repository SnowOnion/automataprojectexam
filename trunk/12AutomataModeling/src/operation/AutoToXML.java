package operation;

import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import automata.*;

public class AutoToXML {
	public static Document FAToXML(Automata fa) {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Element Automatas = document.createElement("Automatas");
		document.appendChild(Automatas);
		Element FA;
		if (fa instanceof DFA) {
			FA= document.createElement("DFA");
		}
		else {
			FA = document.createElement("NFA");
		}
		Automatas.appendChild(FA);			

		Attr name = document.createAttribute("name");
		name.setValue(fa.getName());
		FA.setAttributeNode(name);

		Element States = document.createElement("States");
		FA.appendChild(States);
		Element InputSymbols = document.createElement("InputSymbols");
		FA.appendChild(InputSymbols);
		Element TranFuncs = document.createElement("TranFuncs");
		FA.appendChild(TranFuncs);
		Element StartQ = document.createElement("StartQ");
		FA.appendChild(StartQ);
		Element FinalStates = document.createElement("FinalStates");
		FA.appendChild(FinalStates);

		Set<String> states = fa.getStates().keySet();
		for (String stateId : states) {
			Element state = document.createElement("state");
			state.setTextContent(stateId);
			States.appendChild(state);
		}

		for (char symbolId : fa.getSymbols()) {
			Element symbol = document.createElement("symbol");
			symbol.setTextContent(symbolId + "");
			InputSymbols.appendChild(symbol);
		}

		for (Transition tran : fa.getTransfers()) {
			Element Tranfunc = document.createElement("Tranfunc");

			String beginStateId = tran.getBeginState().getStateId();
			char symbolId = tran.getInputChar();
			String endStateId = tran.getEndState().getStateId();

			Element beginState = document.createElement("state");
			beginState.setTextContent(beginStateId);
			Tranfunc.appendChild(beginState);

			Element symbol = document.createElement("symbol");
			symbol.setTextContent(symbolId + "");
			Tranfunc.appendChild(symbol);

			Element endState = document.createElement("state");
			endState.setTextContent(endStateId);
			Tranfunc.appendChild(endState);

			TranFuncs.appendChild(Tranfunc);
		}

		Element state = document.createElement("state");
		state.setTextContent(fa.getStartQ().getStateId());
		StartQ.appendChild(state);

		for (State finalState : fa.getFinalStates()) {
			Element finalStateE = document.createElement("state");
			finalStateE.setTextContent(finalState.getStateId());
			FinalStates.appendChild(finalStateE);
		}

		return document;
	}
}
