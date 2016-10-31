package robot;

import terrain.incendie;
import terrain.Case;
import java.io.*;
import java.util.concurrent.TimeUnit;

import terrain.Carte;
import enumeration.NatureTerrain;
import enumeration.Direction;


public abstract class robot_Reservoir extends robot {
	
	int capacite_Reservoir;
	float debit_Remplissage;

	public void remplissage(Carte carte) {
		this.litre_Actuel = this.capacite_Reservoir;
		try {
			TimeUnit.MILLISECONDS.sleep((long)(1000.0 * this.capacite_Reservoir / this.debit_Remplissage));
		} catch (InterruptedException e) {
			
		}
	}

	public void deverser_Eau(int volume, incendie feu) {
		feu.setIntensite(feu.getIntensite()-volume);
		this.litre_Actuel -= volume;
		try {
			TimeUnit.MILLISECONDS.sleep((long)(1000 * volume / this.debit_Vidage));
		} catch (InterruptedException e){
			
		}
	}

	public robot_Reservoir(Case position, float debit_Vidage, int litre_Actuel, int capacite_Reservoir,
			float debit_Remplissage) {
		super(position, debit_Vidage, litre_Actuel);
		this.capacite_Reservoir = capacite_Reservoir;
		this.debit_Remplissage = debit_Remplissage;
	}

}
