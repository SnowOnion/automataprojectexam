package cn.tsinghua.mse.automatondesigner.dataobject;

/**
 * 自动机中使用的一些静态变量。
 * @author David
 *
 */
public class AutomatonConst {
	/**
	 * 确定型有限自动机
	 */
	public static final byte AUTOMATON_TYPE_DFA = 0;
	/**
	 * 非确定型有限自动机
	 */
	public static final byte AUTOMATON_TYPE_NFA = 1;
	/**
	 * 下推自动机
	 */
	public static final byte AUTOMATON_TYPE_PDA = 2;
	
	/**
	 * 普通状态
	 */
	public static final byte STATE_COMMON_TYPE = 0;
	
	/**
	 * 初始状态
	 */
	public static final byte STATE_INITIAL_TYPE = 1;
	
	/**
	 * 接受状态
	 */
	public static final byte STATE_FINAL_TYPE = 2;
	
	/**
	 * 状态所对应的字符串表示
	 */
	public static String [] STATE_TYPES={"normal", "initial", "accepted"};
}
