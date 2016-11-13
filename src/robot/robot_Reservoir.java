package robot;
import terrain.incendie;
import terrain.Case;
import java.util.concurrent.TimeUnit;
import terrain.Carte;

/**
 * Classe des robots avec réservoir : on ajoute une capacité de reservoir et un temps de remplissage.
 */
public abstract class robot_Reservoir extends robot {
	
	protected int capacite_Reservoir;
	protected float temps_Remplissage;
	
	public robot_Reservoir(Case position) {
		super(position);
	}

	public void remplissage(Carte carte) {
		this.litre_Actuel = this.capacite_Reservoir;
		try {
			TimeUnit.MILLISECONDS.sleep((long)(this.temps_Remplissage));
		} catch (InterruptedException e) {
			
		}
	}

	public void deverser_Eau(incendie feu) {
		feu.setIntensite(feu.getIntensite()-this.debit_Vidage);
		this.litre_Actuel -= debit_Vidage;
		try {
			TimeUnit.MILLISECONDS.sleep((long)(this.temps_vidage));
		} catch (InterruptedException e){
			
		}
	}

		
}
