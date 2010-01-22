package cn.tsinghua.mse.automatondesigner.dataobject;

/**
 * �Զ�����ʹ�õ�һЩ��̬������
 * @author David
 *
 */
public class AutomatonConst {
	/**
	 * ȷ���������Զ���
	 */
	public static final byte AUTOMATON_TYPE_DFA = 0;
	/**
	 * ��ȷ���������Զ���
	 */
	public static final byte AUTOMATON_TYPE_NFA = 1;
	/**
	 * �����Զ���
	 */
	public static final byte AUTOMATON_TYPE_PDA = 2;
	
	/**
	 * ��ͨ״̬
	 */
	public static final byte STATE_COMMON_TYPE = 0;
	
	/**
	 * ��ʼ״̬
	 */
	public static final byte STATE_INITIAL_TYPE = 1;
	
	/**
	 * ����״̬
	 */
	public static final byte STATE_FINAL_TYPE = 2;
	
	/**
	 * ״̬����Ӧ���ַ�����ʾ
	 */
	public static String [] STATE_TYPES={"normal", "initial", "accepted"};
}
