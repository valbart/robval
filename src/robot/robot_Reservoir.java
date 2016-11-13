package robot;

import terrain.incendie;
import graphe.*;
import gui.Simulateur;
import terrain.Case;
import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import terrain.Carte;
import enumeration.NatureTerrain;
import evenements.EvenementDeplacement;
import exceptions.PasDeCheminException;
import enumeration.Direction;


public abstract class robot_Reservoir extends robot {
	
	protected int capacite_Reservoir;
	protected float temps_Remplissage;
	
	public robot_Reservoir(Case position) {
		super(position);
	}
	
	public void remplissage(Carte carte) {
		this.litre_Actuel = this.capacite_Reservoir;
		try {
			TimeUnit.MILLISECONDS.sleep((long)(this.temps_Remplissage));
		} catch (InterruptedException e) {
			
		}
	}

	public void deverser_Eau(incendie feu) {
		feu.setIntensite(feu.getIntensite()-this.debit_Vidage);
		this.litre_Actuel -= debit_Vidage;
		try {
			TimeUnit.MILLISECONDS.sleep((long)(this.temps_vidage));
		} catch (InterruptedException e){
			
		}
	}

		
}
