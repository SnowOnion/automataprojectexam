package cn.tsinghua.mse.automatondesigner.dataobject;

public class AutomatonConst {
	public static final String AUTOMATON_TYPE_DFA = "DFA";
	public static final String AUTOMATON_TYPE_NFA = "NFA";
	public static final String AUTOMATON_TYPE_PDA = "PDA";
	
	public static final String [] AUTOMATONTYPES = {AUTOMATON_TYPE_DFA, AUTOMATON_TYPE_NFA, AUTOMATON_TYPE_PDA};
	
	public static final String STATE_TYPE_INITIAL = "³õÊ¼×´Ì¬";
	public static final String STATE_TYPE_COMMON = "ÆÕÍ¨×´Ì¬";
	public static final String STATE_TYPE_FINAL = "½áÊø×´Ì¬";
	public static final String [] STATETYPES={STATE_TYPE_INITIAL, STATE_TYPE_COMMON, STATE_TYPE_FINAL};
}
