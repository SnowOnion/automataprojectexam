package automatondesigner;

public class SystemConstant {

	public static final byte ALIGN_LEFT = 1;
	public static final byte ALIGN_RIGHT = 2;
	public static final byte ALIGN_TOP = 3;
	public static final byte ALIGN_BOTTOM = 4;
	
	public static final String AUTOMATON_TYPE_DFA = "DFA";
	public static final String AUTOMATON_TYPE_NFA = "NFA";
	public static final String AUTOMATON_TYPE_PDA = "PDA";
	
	public static final String [] AUTOMATONTYPES = {AUTOMATON_TYPE_DFA, AUTOMATON_TYPE_NFA, AUTOMATON_TYPE_PDA};
	
	public static final String STATE_TYPE_INITIAL = "开始状态";
	public static final String STATE_TYPE_COMMON = "普通状态";
	public static final String STATE_TYPE_FINAL = "结束状态";
	public static final String STATE_TYPE_INI_FINAL = "开始结束状态";
	public static final String [] STATETYPES={STATE_TYPE_COMMON, STATE_TYPE_INITIAL, STATE_TYPE_FINAL, STATE_TYPE_INI_FINAL};
	
	public static final int DEFAULTRADIUS = 20;
	public static final byte IMAGE_TYPE_COMMON = 1;
	public static final byte IMAGE_TYPE_SELECTED = 2;
	public static final byte IMAGE_TYPE_MOVING = 3;
}
