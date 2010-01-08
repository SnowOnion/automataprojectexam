package gui.help;

public class AutomatonException extends Exception {
	private String excepmsg;

	public AutomatonException() {
		super();
		excepmsg = "An error ocurred!";
	}

	public AutomatonException(String msg) {
		super();
		excepmsg = msg;
	}

	public String getExcepmsg() {
		return excepmsg;
	}

	public void setExcepmsg(String excepmsg) {
		this.excepmsg = excepmsg;
	}

}
