package cn.tsinghua.mse.automatondesigner.interfaces;

import org.eclipse.ui.IWorkbenchWindow;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Canvas_Automaton;

public interface ICanvasContainer {

	public abstract String getAutomatonPrefix();

	public abstract void setAutomatonPrefix(String automatonPrefix);

	public abstract IWorkbenchWindow getMainWindow();

	public abstract void setMainWindow(IWorkbenchWindow mainWindow);

	public abstract void setFocus();

	public abstract Automaton getM_Automaton();

	public abstract void setM_Automaton(Automaton mAutomaton);

	public abstract boolean isDirty();

	public abstract void setDirty(boolean isDirty);

	public abstract boolean doDelete();

	public abstract boolean doAlign(byte direction);

	public abstract boolean isContainsCiricle();
	
	public abstract void saveAsImage();
	
	public abstract Canvas_Automaton getCanvas();

}