/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.interfaces;

import org.eclipse.swt.graphics.GC;

/**
 * 可绘制接口，所有用于界面绘制的组件都请实现此接口。
 * @author David
 *
 */
public interface IPaint {
	public void paint(GC gc, byte statue, String type);
}
