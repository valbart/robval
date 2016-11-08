package evenements;
import java.util.LinkedList;
import robot;
import terrain;
import java.util.*;


abstract public class Evenement{
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
	 * Actions à effectuer quand la date d'éxécution de l'événement et celle du simulateur coïncident.
	 */
	abstract public void execute();
}


/**
 * Evenement gérant le déplacement d'un robot selon son type.
 */
public class EvenementDeplacement extends Evenement {
	private Carte carte;
	private robot robot;
	private Direction direction;

	public EvenementDeplacement(long date, Carte map, Robot robotADeplacer, Direction dir){
		super(date);
		this.carte = map;
		this.robot = robotADeplacer;
		this.direction = dir;
	}


	public void execute(){
		if(robot.isBusy() == false){
			robot.setBusy(true);
      robot.avancer(this.direction, this.carte);
		}else{
			System.out.println("Le robot ne peux pas avancer car il est occupé");
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

	public EvenementDeverserEau(long date, Robot robotAVider, LinkedList<Incendie> listeDIncendies, Incendie incendieAEteindre){
		super(date);
		this.robot = robotAVider;
		this.listeIncendies = listeDIncendies;
		this.incendie = incendieAEteindre;
	}

	public void execute(){
		int eau = (this.robot.litre_Actuel() <= this.incendie.intensite)?this.litre_Actuel:this.incendie.intensite;
		//int eau = this.robot.litre_Actuel();
		robot.setBusy(true);
    this.robot.deverser_Eau(eau, this.incendie);
		if (this.incendie.getIntensite() <= 0) {
			listeIncendies.remove(this.incendie);
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
		if (robot.litre_Actuel() < robot.capacite_Reservoir()) {
			this.robot.remplissage(carte);
        }
		else {
			System.out.println(robot.getClass() + "est plein !");
			return;
		}
	}
}
