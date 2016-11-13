package robot;

import terrain.incendie;
import terrain.Carte;
import terrain.Case;
import java.io.*;
import enumeration.NatureTerrain;
import evenements.EvenementRemplirReservoir;
import enumeration.Direction;
import graphe.*;
import gui.Simulateur;
import evenements.*;
import terrain.*;
import gui.*;

public class robot_Chenille extends robot_Reservoir {
	
	public robot_Chenille(Case position, graphe Graphe) {
		super(position);
		this.debit_Vidage = 100;
		this.capacite_Reservoir = 2000;
		this.litre_Actuel = this.capacite_Reservoir;
		this.temps_vidage = 8;
		this.temps_Remplissage = 5*60;
		this.graphe = Graphe;
	}

	public void remplissage(Carte carte) {
		super.remplissage(carte);
	}

	public int get_Vitesse(NatureTerrain terrain) {
		if (terrain == NatureTerrain.EAU | terrain == NatureTerrain.ROCHE) {
			return (0);
		} else if (terrain == NatureTerrain.FORET) {
			return 30;
		} else {
			return (60);
		}
	}
		
}
