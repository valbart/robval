package robot;
import terrain.incendie;
import terrain.*;
import enumeration.*;
import graphe.*;

public class robot_Roues extends robot_Reservoir {

	public robot_Roues(Case position, graphe Graphe) {
		super(position);
		this.debit_Vidage = 100;
		this.capacite_Reservoir = 5000;
		this.litre_Actuel = this.capacite_Reservoir;
		this.temps_vidage = 5;
		this.temps_Remplissage = 10*600;
		this.graphe = Graphe;
	}

	public void remplissage(Carte carte) {
		System.out.println("Le robot à roues se rempli..");
		super.remplissage(carte);
	}

	public int get_Vitesse(NatureTerrain terrain) {
		if (terrain == NatureTerrain.TERRAIN_LIBRE | terrain == NatureTerrain.HABITAT) {
			return (80);
		} else {
			return (0);
		}
	}
	

	public void deverser_Eau(incendie feu) {
		System.out.println("Le robot a roue intervient...");
		super.deverser_Eau(feu);
	}

	
}
