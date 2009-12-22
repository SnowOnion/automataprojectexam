package automaton;

public class Nail {
	private int nailX;
	private int nailY;
	
	public Nail(int nailX,int nailY){
		this.nailX = nailX;
		this.nailY = nailY;
	}
	public Nail(String nailX,String nailY){
		this.nailX = Integer.parseInt(nailX);
		this.nailY = Integer.parseInt(nailY);
	}

	public int getNailX() {
		return nailX;
	}

	public void setNailX(int nailX) {
		this.nailX = nailX;
	}

	public int getNailY() {
		return nailY;
	}

	public void setNailY(int nailY) {
		this.nailY = nailY;
	}
}
