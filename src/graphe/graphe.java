package graphe;
import java.util.*;
import enumeration.*;
import robot.*;
import terrain.*;

public class graphe {
	
	private int nbLigne;
	private int nbColonne;
	protected LinkedList<arc>[][] graphe;
	
	public graphe(int nbLigne, int nbColonne) {
		this.nbLigne = nbLigne;
		this.nbColonne = nbColonne;
		this.graphe = new LinkedList[nbLigne][nbColonne];
	}
	
	public int getNbLigne() {
		return this.nbLigne;
	}
	
	public int getNbColonne() {
		return this.nbColonne;
	}
	
	
	
}
