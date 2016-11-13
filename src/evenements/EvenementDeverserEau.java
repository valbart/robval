package evenements;
import java.util.LinkedList;
import robot.*;
import terrain.*;
import java.util.*;
import gui.*;
/**
 * Evenement gérant le fait qu'un robot éteigne un feu
 * 	(en versant un débit d'eau qui lui est propre).
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

	public void execute(){
		robot.setBusy(true);
		if (robot.getLitre() != 0 && this.incendie.getIntensite() > 0) {
			this.robot.deverser_Eau(this.incendie);
		}


		if (this.incendie.getIntensite() <= 0) {
			listeIncendies.remove(this.incendie);
		}
		this.robot.setBusy(false);
	}
}
