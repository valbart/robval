package robot;
import terrain.Carte;
import terrain.incendie;
import terrain.Case;
import java.util.concurrent.TimeUnit;
import enumeration.NatureTerrain;
import graphe.*;
import gui.Simulateur;

public class robot_Pattes extends robot {

	public robot_Pattes(Case position, graphe Graphe) {
		super(position);
		this.debit_Vidage = 10;
		this.litre_Actuel = Integer.MAX_VALUE;
		this.temps_vidage = 1;
		this.graphe = Graphe;
		this.vitesse = 30000/3600;
	}

	public void deverser_Eau(incendie feu) {
		//System.out.println("Le robot a patte intervient...");
		feu.setIntensite(feu.getIntensite()-this.debit_Vidage);
	}

	public int get_Vitesse(NatureTerrain terrain) {

		if (terrain == NatureTerrain.EAU) {
			return (0);
		} else if (terrain == NatureTerrain.ROCHE) {
			return (10000/3600);
		} else {
			return this.vitesse;
		}
	}
	
	public void remplissage(Carte carte) {
	}
	
	public void remplirReservoir(Carte carte, Simulateur simu) {
	}
}
