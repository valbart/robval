package gui;

import java.util.*;
import terrain.*;
import io.DonneesSimulation;
import robot.*;
import gui.Simulateur;

/**
 * 
 * g�re la strat�gie � adopter pour �teindre les incendies. 
 *
 */
public class ChefPompier {
	/**
	 * @param data les donn�es du probl�me
	 * @return un robot non occup� 
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
	 * @param data les donn�es du probl�me
	 * @return un incendie non encore g�r� par un robot.
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
	 * Organise la gestion des robots selon une strat�gie simple :
	 * Tant qu'un feu non g�r� existe, on cherche un robot non affect� pour lui associer. Si tous les feux sont g�r�s, les robots non affect�s sont envoy�s sur le premier feu trouv�.
	 * @param simu le simulateur.
	 * @param data les donn�es du probl�me. 
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
			System.out.println("Tous les incendies ont �t� �teinds!");
		}
	}

}
