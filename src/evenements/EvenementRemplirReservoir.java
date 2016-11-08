package evenements;
import java.util.LinkedList;
import robot.*;
import terrain.*;
import java.util.*;

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
