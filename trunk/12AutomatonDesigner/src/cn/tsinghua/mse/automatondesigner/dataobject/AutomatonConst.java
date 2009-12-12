package cn.tsinghua.mse.automatondesigner.dataobject;

/**
 * �Զ�����ʹ�õ�һЩ��̬������
 * @author David
 *
 */
public class AutomatonConst {
	public static final String AUTOMATON_TYPE_DFA = "DFA";
	public static final String AUTOMATON_TYPE_NFA = "NFA";
	public static final String AUTOMATON_TYPE_PDA = "PDA";
	
	public static final String [] AUTOMATONTYPES = {AUTOMATON_TYPE_DFA, AUTOMATON_TYPE_NFA, AUTOMATON_TYPE_PDA};
	
	public static final String STATE_TYPE_INITIAL = "��ʼ״̬";
	public static final String STATE_TYPE_COMMON = "��ͨ״̬";
	public static final String STATE_TYPE_FINAL = "����״̬";
	public static final String STATE_TYPE_INI_FINAL = "��ʼ����״̬";
	public static final String [] STATETYPES={STATE_TYPE_INITIAL, STATE_TYPE_COMMON, STATE_TYPE_FINAL, STATE_TYPE_INI_FINAL};
}
