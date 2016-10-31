package io;
import terrain.*;
import robot.*;


public class DonneesSimulation {
	
	public Carte carte;
	
	public incendie[] incendies;
	
	public robot robots[];
	
	
	public DonneesSimulation() {
		this.carte = new Carte();
	}
	
	public void setIncendies(int nbIncendie) {
		this.incendies = new incendie[nbIncendie];
	}
	
	public void setRobots(int nbRobot) {
		this.robots = new robot[nbRobot];
	}
	
}
