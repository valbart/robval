package io;
import terrain.*;


public class DonneesSimulation {
	
	public Carte carte;
	
	public incendie[] incendies;
	
	
	public DonneesSimulation() {
		this.carte = new Carte();
	}
	
	public void setIncendies(int nbIncendie) {
		this.incendies = new incendie[nbIncendie];
	}
	
}
