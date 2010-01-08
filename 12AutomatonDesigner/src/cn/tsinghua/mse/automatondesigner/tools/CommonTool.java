package cn.tsinghua.mse.automatondesigner.tools;

import java.util.ArrayList;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.dataobject.State;

public class CommonTool {
	private static final int WM_PRINT = 0x0317;
	private static final int PRF_NONCLIENT = 0x00000002;
	private static final int PRF_CLIENT = 0x00000004;
	private static final int PRF_ERASEBKGND = 0x00000008;

	public static Image makeShotImage(Control control) {
		int width = control.getBounds().width;
		int height = control.getBounds().height;
		if (width > 0 && height > 0) {
			Image image = new Image(control.getDisplay(), width, height);
			GC gc = new GC(image);
			OS.SendMessage(control.handle, WM_PRINT, gc.handle, PRF_CLIENT
					| PRF_ERASEBKGND | PRF_NONCLIENT);
			gc.dispose();
			return image;
		}
		return null;
	}

	public static int[] pointArrayToIntArray(ArrayList<Point> ps) {
		int[] result = new int[ps.size() * 2];
		for (int i = 0; i < ps.size(); i++) {
			result[2 * i] = ps.get(i).x;
			result[2 * i + 1] = ps.get(i).y;
		}
		return result;
	}

	public static ArrayList<Point> PointsClone(ArrayList<Point> ps) {
		ArrayList<Point> result = new ArrayList<Point>();
		for (Point p : ps) {
			result.add(new Point(p.x, p.y));
		}
		return result;
	}

	public static String[][] getTrancTable(Automaton automaton) {
		if (automaton == null) {
			return null;
		}
		automaton.setInitStateFirst();
		int rowNum = automaton.getM_States().size() + 1;
		int columnNum = automaton.getM_InputSymbols().size() + 1;
		String[][] table = new String[rowNum][columnNum];
		for (int i = 0; i < rowNum; i++) {
			table[i] = new String[columnNum];
		}
		table[0][0] = "";
		for (int i = 1; i < columnNum; i++) {
			table[0][i] = automaton.getM_InputSymbols().get(i - 1);
		}
		for (int i = 1; i < rowNum; i++) {
			State currState = automaton.getM_States().get(i - 1);
			table[i][0] = currState.getM_Name();
			if ((currState.getM_type() & AutomatonConst.STATE_FINAL_TYPE) != 0){
				table[i][0] = "*" + table[i][0];
			}
			if ((currState.getM_type() & AutomatonConst.STATE_INITIAL_TYPE) != 0){
				table[i][0] = "-> " + table[i][0];
			}
			for (int j = 1; j < columnNum; j++) {
				ArrayList<State> states = automaton.getToStates(currState,
						automaton.getM_InputSymbols().get(j - 1));
				String temp = "{";
				for (State s : states) {
					temp += s.getM_Name() + ",";
				}
				if (!temp.equals("{")) {
					temp = temp.substring(0, temp.lastIndexOf(','));
				} 
				temp += "}";
				table[i][j] = temp;
			}
		}
		return table;
	}
}
