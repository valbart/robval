package evenements;
import java.util.LinkedList;
import robot.*;
import terrain.*;
import java.util.*;

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
