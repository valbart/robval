package graphe;
import java.util.*;

import javax.print.attribute.IntegerSyntax;

import enumeration.*;
import robot.*;
import terrain.*;
import graphe.sommet;

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
	
	
	/* On ne se pr�occupe pas de savoir si
	 * la vitesse est sp�cifi�e par l'utilisateur:
	 * quelque soit la vitesse du robot, des robots du m�me type sont soumis
	 * aux m�me contraintes : les chemins les plus court resteront les m�me proportionellement
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
				break;
			case PATTES:
				v = (nature == NatureTerrain.ROCHE) ? 10 : 30;
				break;
		}
		return v;
	}
	
	/*
	 * Pas besoin de connaitre la taille d'une case, on ne g�re pas l'attente du robot ici
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
			if (peutAvancer(carte.getCase(i, j).getNature(),type) && carte.checkDir(i, j, dir)) {
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
	
	public sommet trouveMin(float[][] Q){
		sommet s = new sommet(0,0);
		float min = Q[0][0];
		for (int i = 0; i < this.nbLigne; i++) {
			for (int j = 0; j < this.nbLigne; j++) {
				if ( Q[i][j] != -1 && Q[i][j] < min) {
					s.setI(i);
					s.setJ(j);
					min = Q[i][j];
				}
			}
		}
		return s;
	}
	
	public void majDistance(sommet s, float[][] Q, sommet[][] pred) {
		int i = s.getI();
		int j = s.getJ();
		for (arc a : this.graphe[i][j]) {
			int iVoisin = a.getLigneVoisin();
			int jVoisin = a.getColonneVoisin();
			if (Q[iVoisin][jVoisin] > Q[i][j] + a.getCout()) {
				Q[iVoisin][jVoisin] = Q[i][j] + a.getCout();
				pred[iVoisin][jVoisin].setI(i);
				pred[iVoisin][jVoisin].setJ(j);
			}
		}
		Q[i][j] = -1;
	}
	
	public LinkedList<sommet> dijkstra(sommet sDeb, sommet sFin) {
		LinkedList<sommet> l  = new LinkedList();
		float[][] Q;
		Q = new float[this.nbLigne][this.nbColonne];
		sommet[][] pred;
		pred = new sommet[this.nbLigne][this.nbColonne];
		for (int i = 0; i < this.nbLigne; i ++){
			for (int j = 0; j < this.nbColonne; j++) {
				Q[i][j] = (float) Integer.MAX_VALUE;
				pred[i][j] = new sommet(i,j);
			}
		}
		
		Q[sDeb.getI()][sDeb.getJ()] = 0;
		
    	while (Q[sFin.getI()][sFin.getJ()] != -1) {
			sommet s = trouveMin(Q);
			majDistance(s, Q, pred);
		}

		sommet s2 = sFin;
		while ((s2.getJ() != sDeb.getJ()) || (s2.getI() != sDeb.getI())) {
			l.add(s2);
			s2 = pred[s2.getI()][s2.getJ()];
	    	System.out.println("(" + s2.getI() + "," + s2.getJ() + ")\n");
		}
		System.out.println(s2.getI()+ " " + s2.getJ());
		return l;
	}
	
}
