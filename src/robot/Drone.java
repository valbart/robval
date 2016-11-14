package robot;
import terrain.incendie;
import terrain.Carte;
import terrain.Case;
import enumeration.Direction;
import enumeration.NatureTerrain;
import evenements.EvenementDeplacement;
import graphe.*;
import gui.Simulateur;

public class Drone extends robot_Reservoir {
	
	public Drone(Case position, graphe Graphe) {
		super(position);
		this.debit_Vidage = 10000;
		this.capacite_Reservoir = 10000;
		this.litre_Actuel = this.capacite_Reservoir;
		this.temps_vidage = 30;
		this.temps_Remplissage = 30*60;
		this.graphe = Graphe;
		this.vitesse = 100000/3600;
	}
	
	
	public int get_Vitesse(NatureTerrain terrain) {
		return this.vitesse;
	}
	
	public void remplissage(Carte carte) {
		
		System.out.println("Le drone se remplie..");
		super.remplissage(carte);		
	}
	

	public void deverser_Eau(incendie feu) {
		System.out.println("Le drone intervient...");
		super.deverser_Eau(feu);
	}

	public double deplacer_Eau(Carte carte, Simulateur simu, double dateSuiv, Case C) {
		Direction dir = Direction.NORD;
		int i = C.getLigne();
		int j = C.getColonne();
		double tempsDeplacement = carte.getTailleCase()/this.vitesse;
		for (Direction d : Direction.values()) {
			if (carte.checkDir(i,j,d) && carte.getVoisin(i, j, d).getNature() == NatureTerrain.EAU) {
				dir = d;
				break;
			}
		}
		simu.ajouteEvenement(new EvenementDeplacement((int)Math.ceil(dateSuiv + tempsDeplacement), carte, this, dir));
		System.out.println("I " + (int)Math.ceil(dateSuiv + tempsDeplacement));
		return tempsDeplacement;
	}
	
}
