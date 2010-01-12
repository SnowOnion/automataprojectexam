package automaton;

public class AutomatonConstant {
	public static String [] AUTOMATONTYPES = {"UNKNOWN","DFA","NFA","PDA"};
	public static String [] STATETYPES={"normal","accepted"};
	
	public static final byte AUTOMATA_UNKNOWN = 0;
	public static final byte AUTOMATA_DFA = 1;
	public static final byte AUTOMATA_NFA = 2;
	public static final byte AUTOMATA_PDA = 3;
	
	public static final byte STATETYPE_NORMAL = 0;
	public static final byte STATETYPE_ACCEPTED = 1;
	public static byte getStateTypeByteFromString(String stateType){
		if(stateType.equals("normal")){
			return STATETYPE_NORMAL;
		}
		return STATETYPE_ACCEPTED;
	}
}
