package evenements;
import robot.*;
import terrain.*;

/**
 * Evénement de remplissage d'un robot.
 * 	
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
