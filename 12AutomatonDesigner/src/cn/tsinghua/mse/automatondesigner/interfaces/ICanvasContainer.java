
package cn.tsinghua.mse.automatondesigner.interfaces;

import org.eclipse.ui.IWorkbenchWindow;
import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Canvas_Automaton;

/**
 * 画布容器接口，所有的包含画布对象的类实现此接口就可以完成工具栏和菜单栏的各项操作。
 */
public interface ICanvasContainer {

	/**
	 * 获得自动机的状态编号前缀
	 * @return 自动机状态编号前缀
	 */
	public abstract String getAutomatonPrefix();

	/**
	 * 设置自动机状态编号前缀
	 * @param automatonPrefix 自动机状态编号前缀，可以为空
	 */
	public abstract void setAutomatonPrefix(String automatonPrefix);

	/**
	 * 获得系统IWorkbenchWindow类型的主界面
	 * @return 主界面窗体
	 */
	public abstract IWorkbenchWindow getMainWindow();

	/**
	 * 设置系统IWorkbenchWindow类型的主界面
	 * @param mainWindow 主界面窗体
	 */
	public abstract void setMainWindow(IWorkbenchWindow mainWindow);

	/**
	 * 获取画布所对应的自动机
	 * @return 画布所对应的自动机
	 */
	public abstract Automaton getM_Automaton();

	/**
	 * 设置画布所对应的自动机
	 * @param mAutomaton 画布所对应的自动机
	 */
	public abstract void setM_Automaton(Automaton mAutomaton);

	/**
	 * 是否修改过并未保存
	 * @return 修改过则为true，否则为false
	 */
	public abstract boolean isDirty();

	/**
	 * 设置dirty属性
	 * @param isDirty 设置为的值
	 */
	public abstract void setDirty(boolean isDirty);

	/**
	 * 执行删除操作，将删除画布中选中的组件
	 * @return 是否删除成功，为false表示选中的可删除组件为空
	 */
	public abstract boolean doDelete();

	/**
	 * 执行对齐操作
	 * @param direction 对齐方向，参照SystemConst中对于方向的定义
	 * @return 是否执行成功，为false表示选中的可对齐组件不足
	 */
	public abstract boolean doAlign(byte direction);

	/**
	 * 获取是否存在选中的状态圆
	 * @return 存在返回true，不存在返回false
	 */
	public abstract boolean isContainsCiricle();
	
	/**
	 * 以图像的形式执行存储操作
	 */
	public abstract void saveAsImage();
	
	/**
	 * 获得容器内的画布对象
	 * @return 画布对象
	 */
	public abstract Canvas_Automaton getCanvas();
	
	/**
	 * 执行撤销操作
	 */
	public abstract void doUndo();
	
	/**
	 * 执行重做操作
	 */
	public abstract void doRedo();

}