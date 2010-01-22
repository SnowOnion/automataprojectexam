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
	/**
	 * ����Ļ��ƺ���
	 * @param gc GC����
	 * @param statue �����״̬���Ƿ�ѡ�е�
	 */
	public void paint(GC gc, byte statue);
	
	/**
	 * ����ԭλ�ã�����move������
	 */
	public void updateOriginalLocation();
	
	/**
	 * ��ȡ�����anchor�㣬�����ŵ�
	 * @return ���ŵ�
	 */
	public Point getLableAnchor();
}
