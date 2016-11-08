package gui;

import robot.*;
import terrain.*;
import enumeration.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

import io.DonneesSimulation;
import io.LecteurDonnees;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;

class Simulateur implements Simulable {

	private DonneesSimulation data;

	private GUISimulator gui;

	private int tailleCarre;
 	private long dateActuelle;
	private PriorityQueue<Evenement> events;

	public Simulateur(GUISimulator gui, DonneesSimulation data, int tailleCarre) {
		this.gui = gui;
		this.dateActuelle = 0;
		gui.setSimulable(this);
		this.data = data;
		this.tailleCarre = tailleCarre;
		draw();
	}

	private Color getColorCase(Case c) {
		NatureTerrain n = c.getNature();
		Color couleur = Color.WHITE;
		switch(n) {
		case EAU:
			couleur =  Color.BLUE;
			break;
		case FORET:
			couleur =  Color.GREEN;
			break;
		case ROCHE:
			couleur =  Color.GRAY;
			break;
		case HABITAT:
			couleur =  Color.orange;
		default:
		}
		return couleur;
	}


	// POUR LES FONCTIONS DESSINS : ON A TOUT DECALER SELON UN VEC (tailleCarre,tailleCarr) SINON LA MAP SORT DE LA FENETRE
	private void drawCase(Case c) {
		int i = c.getLigne();
		int j = c.getColonne();
		Color couleur = getColorCase(c);
		gui.addGraphicalElement(new Rectangle(j*tailleCarre+tailleCarre, i*tailleCarre+tailleCarre, Color.BLACK, couleur, tailleCarre));
	}

	private void drawIncendie(incendie i) {
		Case c = i.getPosition();
		int lig = c.getLigne();
		int col = c.getColonne();
		gui.addGraphicalElement(new Text(col*tailleCarre+tailleCarre, lig*tailleCarre+tailleCarre, Color.RED, "FEU"));
	}


	private void drawRobot(robot r) {
		Case c  = r.get_Position();
		int lig = c.getLigne();
		int col = c.getColonne();
		gui.addGraphicalElement(new Text(col*tailleCarre+tailleCarre, lig*tailleCarre+tailleCarre, Color.BLACK, "ROB"));
	}

	private void draw() {
        gui.reset();
        for(int i = 0; i < this.data.carte.getNbLigne(); i++) {
        	for (int j = 0; j < this.data.carte.getNbColonne(); j++) {
        		this.drawCase(this.data.carte.getCase(i, j));
        	}
        }
        for (int i = 0; i < this.data.incendies.length; i++) {
        	this.drawIncendie(this.data.incendies[i]);
        }
        for (int j = 0; j < this.data.robots.length; j++) {
        	this.drawRobot(this.data.robots[j]);
        }
	}


	/**
	 * Ajoute des événements au simulateur.
	 * @param e événement à ajouter au simulateur
	 */
public void ajouteEvenement(Evenement e){
		this.events.add(e);
}

/**
     * Augmente la date du simulateur.
     */
	private void incrementeDate(){
		this.dateActuelle++;
		while (this.events.peek() != null && (this.events.peek().getDate() == this.dateActuelle)) {
			this.events.poll().execute();
		}
		draw();
	}


    @Override
    public void next() {
				incrementeDate();
				//manager.manage();
				System.out.println("Date de la simulation : " + dateSimulation);
    }

    @Override
    public void restart() {
						//simData = LecteurDonnees.initData(nomDuFichier);
						this.events = new PriorityQueue<Evenement>();
						dateActuelle = 0;
						draw();

    }

}
