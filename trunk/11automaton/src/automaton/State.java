package automaton;

public class State {
	protected String stateId;
	protected byte stateType;
	private Nail stateNail;
		
	/***************************************************
	 * Default State Type is normal
	 * @param stateId
	 */
	public State(String stateId) {
		this.stateId = stateId;
		stateType = AutomatonConstant.STATETYPE_NORMAL;
		stateNail = new Nail(0,0);
	}

	public State(String stateId, byte stateType) {
		this.stateId = stateId;
		this.stateType = stateType;
		stateNail = new Nail(0,0);
	}
	public State(String stateId,byte stateType,Nail stateNail){
		this.stateId =stateId;
		this.stateType = stateType;
		this.stateNail =stateNail;
	}

	public void setStateId(String id) {
		this.stateId = id;
	}

	public String getStateId() {
		return stateId;
	}

	public byte getStateType() {
		return stateType;
	}

	public void setStateType(byte stateType) {
		this.stateType = stateType;
	}

	public Nail getStateNail() {
		return stateNail;
	}

	public void setStateNail(Nail stateNail) {
		this.stateNail = stateNail;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("(State Id:"+stateId+"\t");
		builder.append("State Type:"+AutomatonConstant.STATETYPES[stateType]+")\n");
		return builder.toString();
	}
	
}
