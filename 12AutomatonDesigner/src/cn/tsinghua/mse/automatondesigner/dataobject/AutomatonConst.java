package cn.tsinghua.mse.automatondesigner.dataobject;

/**
 * 自动机中使用的一些静态变量。
 * @author David
 *
 */
public class AutomatonConst {
	public static final String AUTOMATON_TYPE_DFA = "DFA";
	public static final String AUTOMATON_TYPE_NFA = "NFA";
	public static final String AUTOMATON_TYPE_PDA = "PDA";
	
	public static final String [] AUTOMATONTYPES = {AUTOMATON_TYPE_DFA, AUTOMATON_TYPE_NFA, AUTOMATON_TYPE_PDA};
	
	public static final String STATE_TYPE_INITIAL = "开始状态";
	public static final String STATE_TYPE_COMMON = "普通状态";
	public static final String STATE_TYPE_FINAL = "结束状态";
	public static final String STATE_TYPE_INI_FINAL = "开始结束状态";
	public static final String [] STATETYPES={STATE_TYPE_INITIAL, STATE_TYPE_COMMON, STATE_TYPE_FINAL, STATE_TYPE_INI_FINAL};
}
