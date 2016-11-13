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
 * Le simulateur g�re l'affichage graphique, l'ordonancement et l'execution des �v�nements.
 * Le simulateur impl�mente l'interface Simulable d�finie dans l'archive gui.jar. Il est d�finit par : 
 * <ul>
 * <li>L'ensembles des donn�es de la simulation</li>
 * <li>Un objet GuiSimulator le liant � l'interface graphique</li>
 * <li>Un entier d�finissant la taille du quadrillage de la carte</li>
 * <li>Un long donnant la date actuelle de la simulation</li>
 * <li>Un ChefPompier d�finissant la strat�gie � adopter</li>
 * <li>Une chaine de caract�re donnant le nom du fichier contenant les donn�es du probl�mes </li>
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
	 * Lit les donn�es du probl�mes et les enregistres dans le champ data de l'objet.
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
	 * Ajoute un �v�nement au simulateur.
	 * @param e �v�nement � ajouter au simulateur.
	 */
	public void ajouteEvenement(Evenement e){
		this.events.add(e);
	}

	/**
     * Incr�mente la date de la simulation et �xecute tout les �v�nements de la nouvelle date. 
     */
	private void incrementeDate(){
		this.dateActuelle++;
		while (this.events.peek() != null && (this.events.peek().getDate() == this.dateActuelle)) {
			this.events.poll().execute();
		}
		draw();
	}

	/**
	 * Incr�mente la date et organise la strat�gie.
	 */
    @Override
    public void next() {
		incrementeDate();
		this.Chef.organise(this, this.data);
		System.out.println("Date de la simulation : " + this.dateActuelle);
    }
    
    /**
     * Red�marre la simulation depuis le d�but. 
     */
    @Override
    public void restart() {
		this.dateActuelle = 0;
		this.events = new PriorityQueue<Evenement>();
		this.data = new DonneesSimulation(); 
		this.initData();
    }

}
