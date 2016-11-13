package evenements;
import java.util.LinkedList;
import robot.*;
import terrain.*;

import gui.*;

/**
 * Evenement lié à l'exctinction d'un feu par un robot.
 * 
 */
public class EvenementDeverserEau extends Evenement {
	private robot robot;
	private LinkedList<incendie> listeIncendies;
	private incendie incendie;
	private Carte carte;
	private Simulateur simu;

	public EvenementDeverserEau(long date, robot robotAVider, LinkedList<incendie> listeDIncendies, incendie incendieAEteindre, Carte carte,
			Simulateur simu){
		super(date);
		this.robot = robotAVider;
		this.listeIncendies = listeDIncendies;
		this.incendie = incendieAEteindre;
		this.carte = carte;
		this.simu = simu;
	}
	
/**
 * Ordonne au robot lié à l'évènement de deverser de l'eau sur un incendie.
 * Si le robot n'a plus d'eau, cet évènement l'envoie se remplir.
 */
	public void execute(){
		robot.setBusy(true);
		if (robot.getLitre() > 0 && this.incendie.getIntensite() > 0) {
			this.robot.deverser_Eau(this.incendie);
		}
		if (this.incendie.getIntensite() <= 0) {
			System.out.println("Le feu (" + this.incendie.getPosition().getLigne() + "," + this.incendie.getPosition().getColonne() + ") est éteind");
			listeIncendies.remove(this.incendie);
			this.robot.setBusy(false);
		} else if (this.robot.getLitre() > 0) {
			this.simu.ajouteEvenement(new EvenementDeverserEau(this.date+1, this.robot, this.listeIncendies, this.incendie, this.carte, this.simu));
		} else {
			this.robot.trouveCheminEau(this.carte, this.simu);
			this.incendie.enCourExctinction = false;
		}

	}
}
