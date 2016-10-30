package robot;
import terrain.incendie;
import terrain.Case;
import terrain.Carte;
import java.io.*;
import java.util.concurrent.TimeUnit;

import enumeration.NatureTerrain;
import enumeration.Direction;


public class robot_Roues extends robot_Reservoir {

	public robot_Roues(Case position) {
		super(position, (float) 20.0, 5000, 5000, (float) 8.3);
	}

	public void remplissage(Carte carte) {
		super.remplissage(carte);
	}

	public int get_Vitesse(NatureTerrain terrain) {
		if (terrain == NatureTerrain.TERRAIN_LIBRE | terrain == NatureTerrain.HABITAT) {
			return (80);
		} else {
			return (0);
		}
	}
}
