/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.interfaces;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

/**
 * �ɻ��ƽӿڣ��������ڽ�����Ƶ��������ʵ�ִ˽ӿڡ�
 * @author David
 *
 */
public interface IPaint {
	public void paint(GC gc, byte statue);
	public void updateOriginalLocation();
	public Point getLableAnchor();
}
