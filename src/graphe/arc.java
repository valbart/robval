package graphe;
/**
 * Classe qui repr�sente les arcs de notre graphe (orient�).
 * Un arc est d�finit par un couple d'entier repr�sentant les coordonn�es du sommet auquel m�ne l'arc et par son co�t.
 */
public class arc {
	
	private int ligneVoisin;
	private int colonneVoisin;
	private float cout;

	
	public arc(int i, int j, float c) {
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
	
	public float getCout() {
		return this.cout;
	}
	
	public void setCout(float c) {
		this.cout = c;
	}
	
}
