package cn.tsinghua.mse.automatondesigner.dataobject;

public class AutomatonConst {
	public static String AUTOMATON_TYPE_DFA = "DFA";
	public static String AUTOMATON_TYPE_NFA = "NFA";
	public static String AUTOMATON_TYPE_PDA = "PDA";
	
	public static String [] AUTOMATONTYPES = {AUTOMATON_TYPE_DFA, AUTOMATON_TYPE_NFA, AUTOMATON_TYPE_PDA};
	
	public static String STATE_TYPE_INITIAL = "³õÊ¼×´Ì¬";
	public static String STATE_TYPE_COMMON = "ÆÕÍ¨×´Ì¬";
	public static String STATE_TYPE_FINAL = "½áÊø×´Ì¬";
	public static String [] STATETYPES={STATE_TYPE_INITIAL, STATE_TYPE_COMMON, STATE_TYPE_FINAL};
}
