package evenements;
import java.util.LinkedList;
import robot;
import terrain;
import java.util.*;


abstract public class Evenement implements Comparable<Evenement> {
	private long date;

	/**
	 * Constructeur.
	 * @param newEventDate date à laquelle l'événement devra s'éxécuter
	 */
	public Evenement(long newEventDate){
		this.date = newEventDate;
	}

	/**
	 * Accesseur sur la date d'éxécution de l'événement.
	 * @return (long) date d'éxécution de l'événement
	 */
	public long getDate(){
		return this.date;
	}

	/**
	 * Réalise l'interface Comparable et permet l'ordonnancement des événements dans une file de priorités
	 * @param e événement à comparer avec celui appelant la méthode
	 * @return 1 si l'événement appelant la méthode s'éxécute après le paramètre, 0 s'ils s'éxécutent simultanément, -1 sinon
	 */
	public int compareTo(Evenement e){
 		if (this.date > e.getDate()) {
 			return 1;
 		} else if (this.date == e.getDate()) {
 			return 0;
 		} else {
 			return -1;
 		}
	}

	/**
	 * Actions à effectuer quand la date d'éxécution de l'événement et celle du simulateur coïncident.
	 */
	abstract public void execute();
}


/**
 * Evenement gérant le déplacement d'un robot selon son type.
 */
public class EvenementDeplacement extends Evenement {
	private Carte carte;
	private Robot robot;
	private Direction direction;
    private boolean isBusy;

	public EvenementDeplacement(long date, Carte map, Robot robotADeplacer, Direction dir, boolean busy){
		super(date);
		this.carte = map;
		this.robot = robotADeplacer;
		this.direction = dir;
        isBusy = busy;//vaut 1 si après l'évènement, le robot est occupé.
	}


	public void execute(){
		Case voisin =  carte.getVoisin(robot.get_Position().ligne,robot.get_Position().colonne, direction);
		if (voisin != robot.get_Position()) {
			robot.setBusy(isBusy);
            if ( robot.getClass() == Drone.class ){
                deplaceDrone(voisin);
            } else if ( robot.getClass() == robot_Chenille.class ){
                deplaceChenilles(voisin);
            } else if ( robot.getClass() == robot_Pattes.class ){
                deplacePattes(voisin);
            } else if ( robot.getClass() == robot_Roues.class ){
                deplaceRoues(voisin);
            }


		}
		else {
			System.out.println("Error : " + robot.getClass() + " ne peut continuer à aller " + direction.toString() + "!");
			return;
		}
	}

	private void deplaceDrone(Case voisin){
		this.robot.set_Position(voisin);
	}

	private void deplaceChenilles(Case voisin){

		if (voisin.getNature() != NatureTerrain.EAU && voisin.getNature() != NatureTerrain.ROCHE) {
			this.robot.set_Position(voisin);
		} else {
			System.out.println("Error : " + robot.getClass() + " ne peut aller dans " + voisin.getNature() + "!");
		}
		return;
	}

	private void deplacePattes(Case voisin){
		if (voisin.getNature() != NatureTerrain.EAU) {
			this.robot.set_Position(voisin);
		} else {
			System.out.println("Error : " + robot.getClass() + " ne peut aller dans " + voisin.getNature() + "!");
		}
	}

	private void deplaceRoues(Case voisin){
		if (voisin.getNature() == NatureTerrain.TERRAIN_LIBRE || voisin.getNature() == NatureTerrain.HABITAT) {
			this.robot.set_Position(voisin);
		} else {
			System.out.println("Error : " + robot.getClass() + " ne peut aller dans " + voisin.getNature() + "!");
		}
	}
}




/**
 * Evenement gérant le fait qu'un robot éteigne un feu
 * 	(en versant un débit d'eau qui lui est propre).
 */
public class EvenementDeverserEau extends Evenement {
	private Robot robot;
	private LinkedList<Incendie> listeIncendies;
	private Incendie incendie;
	private boolean isBusy;

	public EvenementDeverserEau(long date, Robot robotAVider, LinkedList<Incendie> listeDIncendies, Incendie incendieAEteindre){
		super(date);
		this.robot = robotAVider;
		this.listeIncendies = listeDIncendies;
		this.incendie = incendieAEteindre;
	}

	public void execute(){
		int eau = this.robot.litre_Actuel();
		robot.setBusy(true);
		if (incendie.getIntensite() > 0) {
            this.robot.deverser_Eau(eau);
			incendie.extinction(eau);
		} else {
			System.out.println(robot.getClass() + " est vide !");
			//robot.setBusy(false);
		}

		if (incendie.getIntensite() <= 0) {
			listeIncendies.remove(incendie);
			//robot.setBusy(false);

      // robot.setBusy / robot.getBusy / incendie.extinction / robot.getcapacite / robot.capacitemax
		}
	}
}


/**
 * Evenement affichant un messaage.
 */
public class EvenementMessage extends Evenement {
	private String message;

	public EvenementMessage(long date, String message){
		super(date);
		this.message = message;
	}

	public void execute(){
		System.out.println(this.getDate() + this.message);
	}
}


/**
 * Evenement de remplissage d'un robot
 * 	(par un débit qui lui est propre).
 */
public class EvenementRemplirReservoir extends Evenement {
	private Carte carte;
	private Robot robot;

	public EvenementRemplirReservoir(long date, Carte map, Robot robotARemplir){
		super(date);
		this.carte = map;
		this.robot = robotARemplir;
	}

	public void execute(){
		if (robot.getCapacite() < robot.getCapaciteMax()) {
			this.robot.remplissage(carte);
        }

		else {
			System.out.println(robot.getClass() + "est plein !");
			return;
		}
	}
}
