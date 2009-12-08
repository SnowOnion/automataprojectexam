package automaton;

public class AutomatonConstant {
	public static String [] AUTOMATONTYPES = {"DFA","NFA","PDA"};
	public static String [] STATETYPES={"initial","normal","accepted"};
	
	public static final byte AUTOMATA_UNKNOWN = 0;
	public static final byte AUTOMATA_DFA = 1;
	public static final byte AUTOMATA_NFA = 2;
	public static final byte AUTOMATA_PDA = 3;
	
	public static final byte STATETYPE_INITIAL = 1;
	public static final byte STATETYPE_NORMAL = 2;
	public static final byte STATETYPE_ACCEPTED = 3;

}
