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
