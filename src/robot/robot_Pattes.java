package robot;

import terrain.Carte;
import terrain.incendie;
import terrain.Case;
import java.io.*;
import enumeration.NatureTerrain;
import graphe.*;

public class robot_Pattes extends robot {

	public robot_Pattes(Case position, graphe Graphe) {
		super(position, (float) 10, 10000000, Graphe, false);
	}

	public void deverser_Eau(int volume, incendie feu) {
		feu.setIntensite(feu.getIntensite()-volume);
	}

	public void remplissage(Carte carte) {
	}

	public int get_Vitesse(NatureTerrain terrain) {

		if (terrain == NatureTerrain.EAU) {
			return (0);
		} else if (terrain == NatureTerrain.ROCHE) {
			return (10);
		} else {
			return (30);
		}
	}

}
