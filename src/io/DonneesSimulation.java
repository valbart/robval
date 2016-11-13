package io;
import terrain.*;
import robot.*;
import graphe.*;
import java.util.*;

public class DonneesSimulation {
	
	public Carte carte;
	
	public LinkedList<incendie> incendies;
	
	public robot robots[];
	
	public graphe graphes[];
	
	public DonneesSimulation() {
		this.carte = new Carte();
	}
	
	public void setIncendies() {
		this.incendies = new LinkedList<incendie>();
	}
	
	public void setRobots(int nbRobot) {
		this.robots = new robot[nbRobot];
	}
	
	public void initGraphes() {
		this.graphes = new graphe[4];
	}
	
	public void setGraphe(int i, graphe g) {
		this.graphes[i] = g;
	} 
}
