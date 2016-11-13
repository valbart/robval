package robot;

import terrain.incendie;
import terrain.Carte;
import terrain.Case;
import java.util.concurrent.TimeUnit;
import java.io.*;
import enumeration.NatureTerrain;
import enumeration.Direction;
import graphe.*;
import evenements.*;
import terrain.*;
import gui.*;

public class Drone extends robot_Reservoir {
	
	public Drone(Case position, graphe Graphe) {
		super(position);
		this.debit_Vidage = 10000;
		this.capacite_Reservoir = 10000;
		this.litre_Actuel = this.capacite_Reservoir;
		this.temps_vidage = 30;
		this.temps_Remplissage = 30*60;
		this.graphe = Graphe;
	}
	
	
	
	public int get_Vitesse(NatureTerrain terrain) {
		return (100);
	}
	
	public void remplissage(Carte carte) {
		System.out.println("Le drone se remplie..");
		super.remplissage(carte);		
	}
	

}
