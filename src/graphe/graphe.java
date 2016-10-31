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
		for (int i = 0; i < this.nbLigne; i ++) {
			for (int j = 0; j < this.nbColonne; j++) {
				this.graphe[i][j] = new LinkedList();
			}
		}
	}
	
	public int getNbLigne() {
		return this.nbLigne;
	}
	
	public int getNbColonne() {
		return this.nbColonne;
	}
	
	public void addArc(int iInit, int jInit, int iFin, int jFin, int cout) {
		arc newArc = new arc(iFin, jFin, cout);
		this.graphe[iInit][jInit].add(newArc);
	}
	
	
	private static boolean peutAvancer(NatureTerrain nature, TypeRobot type) {
		boolean b = true;
		switch(type) {
			case DRONE:
				break;
			case CHENILLES:
				b = (nature != NatureTerrain.EAU && nature != NatureTerrain.ROCHE);
				break;
			case PATTES:
				b =  (nature != NatureTerrain.EAU);
				break;
			case ROUES:
				b  = (nature == NatureTerrain.TERRAIN_LIBRE || nature == NatureTerrain.HABITAT);
				break;
		}
		return b;
	}
	
	
	/* On ne se préoccupe pas de savoir si
	 * la vitesse est spécifiée par l'utilisateur:
	 * quelque soit la vitesse du robot, des robots du même type sont soumis
	 * aux même contraintes : les chemins les plus court resteront les même proportionellement
	 */
	private static int vitesseBase(NatureTerrain nature, TypeRobot type) {
		int v = 0;
		switch(type) {
			case DRONE:
				v = 100;
				break;
			case ROUES:
				v = 80;
				break;
			case CHENILLES:
				v = (nature == NatureTerrain.FORET) ? 30 : 60;
			case PATTES:
				v = (nature == NatureTerrain.ROCHE) ? 10 : 30;
		}
		return v;
	}
	
	/*
	 * Pas besoin de connaitre la taille d'une case, on ne gère pas l'attente du robot ici
	 * on prend donc tailleCase = 1
	 */
	private static float calculCout(NatureTerrain natureDepart, NatureTerrain natureArrive, TypeRobot type) {
		int v1 = vitesseBase(natureDepart, type);
		int v2 = vitesseBase(natureArrive, type);
		return ((2*10000/(v1+v2))); // A REGLER PROBABLEMENT COUT ARRNONDIT A 0 MULT PAR PLUS
	}
	
	/*
	 * NEC : this.nbLigne == carte.nbLigne && this.nbColonne == carte.nbColonne
	 */
	private void majGraphe(int i, int j, Carte carte, TypeRobot type){
		for(Direction dir : Direction.values()) {
			if (carte.checkDir(i, j, dir)) {
				Case voisin = carte.getVoisin(i, j, dir);
				NatureTerrain natureDepart = carte.getCase(i, j).getNature();
				NatureTerrain natureArrive = voisin.getNature();
				if (peutAvancer(voisin.getNature(), type)) {
					float cout = calculCout(natureDepart, natureArrive, type);
					this.graphe[i][j].add(new arc(voisin.getLigne(), voisin.getColonne(),cout));
				}
			}
		}
	}
	
	public void creerGraphe(Carte carte, TypeRobot type) {
		for (int i = 0; i < this.nbLigne; i++) {
			for (int j = 0; j < this.nbColonne; j++) {
				this.majGraphe(i, j, carte, type);
			}
		}
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < this.nbLigne; i++) {
			for (int j = 0; j < this.nbColonne; j++) {
				s += "Voisin de (" + i + "," + j +")\n";
				for (arc a : this.graphe[i][j]) {
					s += "(" + a.getLigneVoisin() + "," + a.getColonneVoisin() + "," + a.getCout() + ")" + "  ";
				}
				s += "\n";
			}
		}
		return s;
	}
	
}
