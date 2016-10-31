package graphe;

public class arc {
	
	private int ligneVoisin;
	private int colonneVoisin;
	private int cout;
	
	public arc(int i, int j, int c) {
		this.ligneVoisin = i;
		this.colonneVoisin = j;
		this.cout = c;
	}
	
	
	public int getLigneVoisin() {
		return this.ligneVoisin;
	}
	
	public void setLigneVoisin(int i) {
		this.ligneVoisin = i;
	}
	
	public int getColonneVoisin() {
		return this.colonneVoisin;
	}
	
	public void setColonneVoisin(int j) {
		this.colonneVoisin = j;
	}
	
	public int getCout() {
		return this.cout;
	}
	
	public void setCout(int c) {
		this.cout = c;
	}
	
}
