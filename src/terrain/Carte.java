package terrain;
import terrain.Case;
import enumeration.*;

/**
 * Classe des cartes des simulation : une carte est une matrice de case définie par sa longueur, sa largeur, et la taille réelles des cases
 *
 */
public class Carte {
	
	private Case[][] map;
	public int nbLigne;
	public int nbColonne;
	private int tailleCase;

	public Carte() {
		this.nbLigne = 0;
		this.nbColonne = 0;
		this.tailleCase = 0;
	}
	
	public int getNbLigne(){
		return this.nbLigne;
	}

	public int getNbColonne(){
		return this.nbColonne;
	}
	
	public void setNbLigne(int i) {
		this.nbLigne = i;
	}
	
	public void setNbColonne(int j) {
		this.nbColonne = j;
	}
	
	public void setMap() {
		this.map = new Case[this.nbLigne][this.nbColonne];
	}
	
	public void setCaseMap(int i, int j, NatureTerrain nature) {
		this.map[i][j] = new Case(i, j, nature);
	}
	
	public void setTailleCase(int tailleCase) {
		this.tailleCase = tailleCase;
	}
	
	public int getTailleCase() {
		return this.tailleCase;
	}
	
	public Case getCase(int i, int j) {
		return this.map[i][j];
	}
	
	
	/** Donne si oui on on on peut se déplacer selon la direction dir depuis la case (i,j)
	 * @param i
	 * @param j
	 * @param dir
	 * @return un booleen qui vaut true ssi on ne sort pas de la carte en effectuant ce déplacement.
	 */
	public Boolean checkDir(int i, int j, Direction dir) {
		return !((i == 0 && dir == Direction.NORD) || (j == 0 && dir == Direction.OUEST)
				|| (i == nbLigne-1 && dir == Direction.SUD) || (j == nbColonne-1 && dir == Direction.EST));
	}
	 
	
	
	/**
	 * Renvoie la case voisine de la case (i,j) dans la direction dir
	 * @param i
	 * @param j
	 * @param dir
	 * @return la case voisine de la case (i,j) dans la direction dir.
	 */
	public Case getVoisin(int i, int j, Direction dir) {
		int newI = i;
		int newJ = j;
		
		if (checkDir(i, j, dir)) {
			switch (dir) {
				case NORD:
					newI -= 1;
					break;
				case SUD:
					newI += 1;
					break;
				case EST:
					newJ += 1;
					break;
				case OUEST: 
					newJ -= 1;
					break;		
			} 
			
		} else {
				System.out.println("Vous tentez d'accéder à une case hors de la carte, la case renvoyer est la case actuelle");
		}
		
		return this.map[newI][newJ];
	}
	
	/**
	 * Indique si la case (i,j) a une des cases qui l'entoure qui est constituée d'eau.
	 * @param i
	 * @param j
	 * @return un booleen valant true ssi il existe un voisin de la case (i,j) qui a pour nature l'eau. 
	 */
	public boolean estPretEau(int i, int j){
		boolean b = false;
		for (Direction dir : Direction.values()) {
			if (checkDir(i,j,dir)) {
				if (this.getVoisin(i, j, dir).getNature() == NatureTerrain.EAU) {
					b = true;
				}
			}
		}
		return b;
	}
	
	
	
	public String toString() {
		String c = "";
		NatureTerrain n;
		for (int i = 0; i < this.nbColonne; i++) {
			for (int j = 0; j < this.nbLigne; j++) {
				n = this.map[i][j].getNature();
				c += n.toString() + "  ";
			}
			c += "\n";
		}
		return c;
	}
	
}
