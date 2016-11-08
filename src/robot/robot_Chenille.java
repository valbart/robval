package robot;

import terrain.incendie;
import terrain.Carte;
import terrain.Case;
import java.io.*;
import enumeration.NatureTerrain;
import enumeration.Direction;
import graphe.*;

public class robot_Chenille extends robot_Reservoir {
	
	public robot_Chenille(Case position, graphe Graphe) {
		super(position, (float) 12.5, 2000, 2000, (float) 6.66, Graphe);
	}

	public void remplissage(Carte carte) {
		// manque verif aux bords
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
