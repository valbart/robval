package gui;

import java.util.*;
import terrain.*;
import io.DonneesSimulation;
import robot.*;
import gui.Simulateur;

/**
 * 
 * gère la stratégie à adopter pour éteindre les incendies. 
 *
 */
public class ChefPompier {
	/**
	 * @param data les données du problème
	 * @return un robot non occupé 
	 */
	public static robot trouveRobotNonOccupe(DonneesSimulation data) {
		robot r = null;
		for (robot rob : data.robots) {
			if (!rob.isBusy()) {
				r = rob; 
				break;
			}
		}
		return r;
	}
	
	/**
	 * @param data les données du problème
	 * @return un incendie non encore géré par un robot.
	 */
	public static incendie trouveIncendieNonGerer(DonneesSimulation data) {
		incendie feu = null;
		Iterator<incendie> iterator = data.incendies.iterator();
	    while (iterator.hasNext()) {
	    	feu = iterator.next();
			if (!feu.enCourExctinction) { 
				break;
			}
	    }
		return feu;
	}
	
	/**
	 * Organise la gestion des robots selon une stratégie simple :
	 * Tant qu'un feu non géré existe, on cherche un robot non affecté pour lui associer. Si tous les feux sont gérés, les robots non affectés sont envoyés sur le premier feu trouvé.
	 * @param simu le simulateur.
	 * @param data les données du problème. 
	 */
	public void organise(Simulateur simu, DonneesSimulation data) {
		if (!data.incendies.isEmpty()) {
			incendie feu = trouveIncendieNonGerer(data);
			if (feu == null && !data.incendies.isEmpty()) {
				feu = data.incendies.getFirst();
			}

			robot rob = trouveRobotNonOccupe(data);
			if (rob != null) {
				rob.trouveChemin(feu, data.incendies, data.carte, simu);
				feu.enCourExctinction = true;
			} 
		} else {
			System.out.println("Tous les incendies ont été éteinds!");
		}
	}

}
