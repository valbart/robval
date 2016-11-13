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
	private robot robot;

	public EvenementRemplirReservoir(long date, Carte map, robot robotARemplir){
		super(date);
		this.carte = map;
		this.robot = robotARemplir;
	}

	public void execute(){
			this.robot.remplissage(carte);
			this.robot.setBusy(false);
	}

}
