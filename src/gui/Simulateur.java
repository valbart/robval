package gui;
import robot.*;
import terrain.*;
import enumeration.*;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import io.DonneesSimulation;
import io.LecteurDonnees;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import evenements.*;
import java.util.*;
import gui.ChefPompier;

/**
 * Le simulateur gère l'affichage graphique, l'ordonancement et l'execution des évènements.
 * Le simulateur implémente l'interface Simulable définie dans l'archive gui.jar. Il est définit par : 
 * <ul>
 * <li>L'ensembles des données de la simulation</li>
 * <li>Un objet GuiSimulator le liant à l'interface graphique</li>
 * <li>Un entier définissant la taille du quadrillage de la carte</li>
 * <li>Un long donnant la date actuelle de la simulation</li>
 * <li>Un ChefPompier définissant la stratégie à adopter</li>
 * <li>Une chaine de caractère donnant le nom du fichier contenant les données du problèmes </li>
 * </ul>
 */

public class Simulateur implements Simulable {

	private DonneesSimulation data;
	private GUISimulator gui;
	private int tailleCarre;
 	private long dateActuelle;
	private PriorityQueue<Evenement> events;
	private ChefPompier Chef;
	private String fichier;

	public Simulateur(GUISimulator gui, DonneesSimulation data, int tailleCarre, String fichier) {
		this.gui = gui;
		this.dateActuelle = 0;
		gui.setSimulable(this);
		this.tailleCarre = tailleCarre;
		this.events = new PriorityQueue<Evenement>();
		this.Chef = new ChefPompier();
		this.data = new DonneesSimulation(); 
		this.fichier = fichier;
		this.initData();
	}

	/**
	 * Lit les données du problèmes et les enregistres dans le champ data de l'objet.
	 */
	public void initData() {
        try {
            LecteurDonnees.lire(this.fichier, this.data);
    		draw();
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + fichier + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + fichier + " invalide: " + e.getMessage());
        }
	}
	
	public long getDateActuelle() {
		return this.dateActuelle;
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
        for (incendie i: this.data.incendies) {
        	this.drawIncendie(i);
        }
        for (int j = 0; j < this.data.robots.length; j++) {
        	this.drawRobot(this.data.robots[j]);
        }
	}


	/**
	 * Ajoute un événement au simulateur.
	 * @param e événement à ajouter au simulateur.
	 */
	public void ajouteEvenement(Evenement e){
		this.events.add(e);
	}

	/**
     * Incrémente la date de la simulation et éxecute tout les évènements de la nouvelle date. 
     */
	private void incrementeDate(){
		this.dateActuelle++;
		while (this.events.peek() != null && (this.events.peek().getDate() == this.dateActuelle)) {
			this.events.poll().execute();
		}
		draw();
	}

	/**
	 * Incrémente la date et organise la stratégie.
	 */
    @Override
    public void next() {
		incrementeDate();
		this.Chef.organise(this, this.data);
		System.out.println("Date de la simulation : " + this.dateActuelle);
    }
    
    /**
     * Redémarre la simulation depuis le début. 
     */
    @Override
    public void restart() {
		this.dateActuelle = 0;
		this.events = new PriorityQueue<Evenement>();
		this.data = new DonneesSimulation(); 
		this.initData();
    }

}
