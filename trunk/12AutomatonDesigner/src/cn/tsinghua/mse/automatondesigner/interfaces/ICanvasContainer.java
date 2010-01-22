
package cn.tsinghua.mse.automatondesigner.interfaces;

import org.eclipse.ui.IWorkbenchWindow;
import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Canvas_Automaton;

/**
 * ���������ӿڣ����еİ��������������ʵ�ִ˽ӿھͿ�����ɹ������Ͳ˵����ĸ��������
 */
public interface ICanvasContainer {

	/**
	 * ����Զ�����״̬���ǰ׺
	 * @return �Զ���״̬���ǰ׺
	 */
	public abstract String getAutomatonPrefix();

	/**
	 * �����Զ���״̬���ǰ׺
	 * @param automatonPrefix �Զ���״̬���ǰ׺������Ϊ��
	 */
	public abstract void setAutomatonPrefix(String automatonPrefix);

	/**
	 * ���ϵͳIWorkbenchWindow���͵�������
	 * @return �����洰��
	 */
	public abstract IWorkbenchWindow getMainWindow();

	/**
	 * ����ϵͳIWorkbenchWindow���͵�������
	 * @param mainWindow �����洰��
	 */
	public abstract void setMainWindow(IWorkbenchWindow mainWindow);

	/**
	 * ��ȡ��������Ӧ���Զ���
	 * @return ��������Ӧ���Զ���
	 */
	public abstract Automaton getM_Automaton();

	/**
	 * ���û�������Ӧ���Զ���
	 * @param mAutomaton ��������Ӧ���Զ���
	 */
	public abstract void setM_Automaton(Automaton mAutomaton);

	/**
	 * �Ƿ��޸Ĺ���δ����
	 * @return �޸Ĺ���Ϊtrue������Ϊfalse
	 */
	public abstract boolean isDirty();

	/**
	 * ����dirty����
	 * @param isDirty ����Ϊ��ֵ
	 */
	public abstract void setDirty(boolean isDirty);

	/**
	 * ִ��ɾ����������ɾ��������ѡ�е����
	 * @return �Ƿ�ɾ���ɹ���Ϊfalse��ʾѡ�еĿ�ɾ�����Ϊ��
	 */
	public abstract boolean doDelete();

	/**
	 * ִ�ж������
	 * @param direction ���뷽�򣬲���SystemConst�ж��ڷ���Ķ���
	 * @return �Ƿ�ִ�гɹ���Ϊfalse��ʾѡ�еĿɶ����������
	 */
	public abstract boolean doAlign(byte direction);

	/**
	 * ��ȡ�Ƿ����ѡ�е�״̬Բ
	 * @return ���ڷ���true�������ڷ���false
	 */
	public abstract boolean isContainsCiricle();
	
	/**
	 * ��ͼ�����ʽִ�д洢����
	 */
	public abstract void saveAsImage();
	
	/**
	 * ��������ڵĻ�������
	 * @return ��������
	 */
	public abstract Canvas_Automaton getCanvas();
	
	/**
	 * ִ�г�������
	 */
	public abstract void doUndo();
	
	/**
	 * ִ����������
	 */
	public abstract void doRedo();

}