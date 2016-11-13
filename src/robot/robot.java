package robot;
import terrain.incendie;
import terrain.Case;
import terrain.Carte;
import java.util.*;
import java.util.concurrent.TimeUnit;
import enumeration.NatureTerrain;
import enumeration.Direction;
import graphe.*;
import gui.*;
import evenements.*;
import exceptions.*;

/**
 * Classe abstraite d�finissant les m�thodes et attributs communs � tous les robots :
 * <ul>
 * <li>Un booleen donant l'�tat du robot : occup� ou non</li>
 * <li>Une position </li>
 * <li>Une quantit� d'eau d�vers�e � chaque intervention</li>
 * <li>Une quantit� d'eau actuelle dans le r�servoir du robot</li>
 * <li>Un graphe d�pendant du type du robot : les sommets du graphes sont les cases sur lesquelles le robot peut se d�placer. Un arc existe entre deux sommets si le robot peut aller de l'un � l'autre, et est valu� par le temps mit par le robot pour effectuer ce d�placement</li>
 * <li>Un entier donnant le temps (en secondes) mis par le robot pour verser de l'eau (une intervention) <li>
 * </ul>
 *
 */
public abstract class robot {
    private boolean Busy;
	public Case position;
    protected int debit_Vidage;
    protected int litre_Actuel;
    protected graphe graphe;
    protected int temps_vidage;
    
    public robot(Case position) {
    	this.position=position;
    	this.Busy = false;
    }
    
    public int getLitre() {
    	return this.litre_Actuel;
    }
    
    public abstract int get_Vitesse(NatureTerrain terrain);
    
    /**
     * D�finit la mani�re dont le robot intervient
     * @param feu l'incendie sur lequelle intervenir
     */
    public abstract void deverser_Eau(incendie feu);
    
    /**
     * D�finit la mani�re dont le robot remplit son �ventuel r�servoir
     * @param carte la carte sur laquelle se trouve le robot. 
     */
    public abstract void remplissage(Carte carte);
     
    public void set_Position(Case x) {
	this.position = x;
    }
    
    public Case get_Position() {
	return this.position;
    }
    
    public boolean isBusy() {
    	return this.Busy;
    }
    
    public void setBusy(boolean b) {
    	this.Busy = b ;
    }
    
    /**
     * Modifie la position d'un robot sur la carte.
     * @param x la direction selon laquelle avancer
     * @param carte
     */
	public void avancer(Direction x, Carte carte) {
		Case caseActuelle = this.get_Position();
		int i = caseActuelle.getLigne();
		int j = caseActuelle.getColonne();
		if (carte.checkDir(i, j, x)) {
			Case newCase = carte.getVoisin(i, j, x);
			NatureTerrain newNature = newCase.getNature();
			if (this.get_Vitesse(newNature) != 0) {
				this.set_Position(newCase);
				try {
					TimeUnit.MILLISECONDS.sleep(50);
				} catch (InterruptedException e) {
					
				}
			} else {
				System.out.println("Impossible pour ce robot d'avancer sur ce terrain");
			}
		}
	}
    
	
	
	/**
	 * Trouve le plus court chemin vers l'incendie sp�cifier, programme la suite d'�v�nements correspondant et l'�v�nement de d�versement d'eau.
	 * @param incendie l'incendie � �teindre
	 * @param listeIncendies la liste de tous les incendies 
	 * @param carte la carte associ�e
	 * @param simu le simulateur associ�
	 */
	public void trouveChemin(incendie incendie, LinkedList<incendie> listeIncendies, Carte carte, Simulateur simu) {
		this.setBusy(true);
		int iAct = this.position.getLigne();
		int jAct = this.position.getColonne();
		try {
			long dateActuelle = simu.getDateActuelle();
			int i = 1;
			if (incendie.getPosition().getLigne() != this.position.getLigne() || incendie.getPosition().getColonne() != this.position.getColonne()) {
				LinkedList<Direction> chemin = this.graphe.dijkstra(iAct, jAct, incendie.getPosition().getLigne(), incendie.getPosition().getColonne());
				for (Direction dir : chemin){
					simu.ajouteEvenement(new EvenementDeplacement(dateActuelle+i, carte, this, dir));
					i++;
				}
			}
			simu.ajouteEvenement(new EvenementDeverserEau(dateActuelle+i, this, listeIncendies, incendie, carte, simu));
		} catch (PasDeCheminException e) {
			
		}
	}
	
	
	/**
	 * Trouve la case contenant de l'eau la plus proche et s'y rend en programmant la suite d'�v�nements associ�, puis programme l'�v�nement de remplissage. 
	 * @param carte la carte associ�e
	 * @param simu le simulateur associ�
	 */
	public void trouveCheminEau(Carte carte, Simulateur simu) {
		this.setBusy(true);
		int iAct = this.position.getLigne();
		int jAct = this.position.getColonne();
		try {
			int i = 1;
			long dateActuelle = simu.getDateActuelle();
			if (! carte.estPretEau(this.position.getLigne(), this.position.getColonne())) {
				LinkedList<Direction> chemin = this.graphe.dijkstraEau(iAct, jAct, carte);
				for (Direction dir : chemin){
					simu.ajouteEvenement(new EvenementDeplacement(dateActuelle+i, carte, this, dir));			
					i++;
				}
			}
			simu.ajouteEvenement(new EvenementRemplirReservoir(dateActuelle+i, carte, this));
		} catch (PasDeCheminException e) {
			
		}
	}
	
	
    
}

 
