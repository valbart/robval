package terrain;
import enumeration.NatureTerrain;

/**
 * Classe définissant les cases de la carte : une abscisse, une ordonnée et la nature du terrain de la case.
 */
public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;
	
	public Case() {
		this.ligne = 0;
		this.colonne = 0;
		this.nature = NatureTerrain.TERRAIN_LIBRE;
	}
	
	public Case(int ligne, int colonne, NatureTerrain nature) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.nature = nature;
	}
	
	
	public int getLigne() {
		return this.ligne;
	}
	
	public int getColonne() {
		return this.colonne;
	}
	
	public NatureTerrain getNature() {
		return this.nature;
	}
	
	public void setNature(NatureTerrain nature) {
		this.nature = nature;
	}
}
