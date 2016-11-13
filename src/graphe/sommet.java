package graphe;

public class sommet {
	private int i;
	private int j;
	
	public sommet(int i, int j) {
		this.i = i;
		this.j = j; 
	}
	
	public int getI() {
		return this.i;
	}
	
	public int getJ() {
		return this.j;
	}
	
	public void setI(int I){
		this.i = I;
	}
	
	public void setJ(int J){
		this.j = J;
	}
	
	public String toString() {
		return "(" + this.i + "," + this.j +")";
	}
	
}
