package gui;

import com.mxgraph.model.mxCell;

public class Test {
	private mxCell cell;
	private String str = "";

	public Test(mxCell cell) {
		this.cell = cell;
	}

	public void setString(String str) {
		this.str += str;
	}
}
