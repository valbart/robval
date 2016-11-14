package robot;
import terrain.incendie;
import gui.Simulateur;
import terrain.Carte;
import terrain.Case;
import enumeration.NatureTerrain;
import graphe.*;

public class robot_Chenille extends robot_Reservoir {
	
	public robot_Chenille(Case position, graphe Graphe) {
		super(position);
		this.debit_Vidage = 100;
		this.capacite_Reservoir = 2000;
		this.litre_Actuel = this.capacite_Reservoir;
		this.temps_vidage = 8;
		this.temps_Remplissage = 5*60;
		this.graphe = Graphe;
		this.vitesse = 60000/3600;
	}

	public void remplissage(Carte carte) {
		super.remplissage(carte);
	}

	public int get_Vitesse(NatureTerrain terrain) {
		if (terrain == NatureTerrain.EAU | terrain == NatureTerrain.ROCHE) {
			return (0);
		} else if (terrain == NatureTerrain.FORET) {
			return (int) this.vitesse/2;
		} else {
			return this.vitesse;
		}
	}
		

	public void deverser_Eau(incendie feu) {
		System.out.println("Le robot chenille intervient...");
		super.deverser_Eau(feu);
	}

	public double deplacer_Eau(Carte carte,Simulateur simu, double dateSuiv, Case C) {
		return 0;
	}

	
}
