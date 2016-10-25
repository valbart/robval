package terrain;
import enumeration.NatureTerrain;


public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;
	
	
	// Constructeur par défaut.
	public Case() {
		this.ligne = 0;
		this.colonne = 0;
		this.nature = NatureTerrain.TERRAIN_LIBRE;
	}
	
	// Constructeur avec paramêtres.
	public Case(int ligne, int colonne, NatureTerrain nature) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.nature = nature;
	}
	
	
	// Get uniquement pour position (une case reste à sa place), get et set pour type de terrain
	// qui peut varier au cours de la simulation
	
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
