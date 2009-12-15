package cn.tsinghua.mse.automatondesigner.dataobject;

/**
 * 自动机中使用的一些静态变量。
 * @author David
 *
 */
public class AutomatonConst {
	public static final byte AUTOMATON_TYPE_DFA = 1;
	public static final byte AUTOMATON_TYPE_NFA = 2;
	public static final byte AUTOMATON_TYPE_PDA = 3;
	
	public static final byte STATE_COMMON_TYPE = 0;
	public static final byte STATE_INITIAL_TYPE = 1;
	public static final byte STATE_FINAL_TYPE = 2;
}
