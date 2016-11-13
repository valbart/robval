package evenements;

import enumeration.Direction;
import robot.*;
import terrain.*;


/**
 * Evénement de déplacement d'un robot.
 */
public class EvenementDeplacement extends Evenement {
	private Carte carte;
	private robot robot;
	private Direction direction;

	public EvenementDeplacement(long date, Carte map, robot robotADeplacer, Direction dir){
		super(date);
		this.carte = map;
		this.robot = robotADeplacer;
		this.direction = dir;
	}


	public void execute(){
      robot.avancer(this.direction, this.carte);
	}
}
