/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.interfaces;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

/**
 * 可绘制接口，所有用于界面绘制的组件都请实现此接口。
 * @author David
 *
 */
public interface IPaint {
	/**
	 * 组件的绘制函数
	 * @param gc GC对象
	 * @param statue 组件的状态，是否选中等
	 */
	public void paint(GC gc, byte statue);
	
	/**
	 * 更新原位置，用于move操作。
	 */
	public void updateOriginalLocation();
	
	/**
	 * 获取组件的anchor点，即附着点
	 * @return 附着点
	 */
	public Point getLableAnchor();
}
