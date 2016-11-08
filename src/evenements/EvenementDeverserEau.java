package evenements;
import java.util.LinkedList;
import robot.*;
import terrain.*;
import java.util.*;

/**
 * Evenement gérant le fait qu'un robot éteigne un feu
 * 	(en versant un débit d'eau qui lui est propre).
 */
public class EvenementDeverserEau extends Evenement {
	private robot robot;
	private LinkedList<incendie> listeIncendies;
	private incendie incendie;

	public EvenementDeverserEau(long date, robot robotAVider, LinkedList<incendie> listeDIncendies, incendie incendieAEteindre){
		super(date);
		this.robot = robotAVider;
		this.listeIncendies = listeDIncendies;
		this.incendie = incendieAEteindre;
	}

	public void execute(){
		int eau = (this.robot.getLitre() <= this.incendie.getIntensite()) ? this.robot.getLitre() : this.incendie.getIntensite();
		//int eau = this.robot.litre_Actuel();
		robot.setBusy(true);
    this.robot.deverser_Eau(eau, this.incendie);
		if (this.incendie.getIntensite() <= 0) {
			listeIncendies.remove(this.incendie);
		}
	}
}
