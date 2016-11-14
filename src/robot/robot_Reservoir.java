package robot;
import terrain.incendie;
import terrain.Case;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import enumeration.Direction;
import evenements.EvenementDeplacement;
import evenements.EvenementRemplirReservoir;
import exceptions.PasDeCheminException;
import gui.Simulateur;
import terrain.Carte;

/**
 * Classe des robots avec réservoir : on ajoute une capacité de reservoir et un temps de remplissage.
 */
public abstract class robot_Reservoir extends robot {
	
	protected int capacite_Reservoir;
	protected int temps_Remplissage;
	
	public robot_Reservoir(Case position) {
		super(position);
	}

	public void remplissage(Carte carte) {
		this.litre_Actuel = this.capacite_Reservoir;
	}

	public void deverser_Eau(incendie feu) {
		feu.setIntensite(feu.getIntensite()-this.debit_Vidage);
		this.litre_Actuel -= debit_Vidage;
	}
	
	
	
	public void trouveCheminEau(Carte carte, Simulateur simu) {
		this.setBusy(true);
		int iAct = this.position.getLigne();
		int jAct = this.position.getColonne();
		try {
			long dateDepart = simu.getDateActuelle();
			double dateSuiv = (double) dateDepart;
			if (! carte.estPretEau(this.position.getLigne(), this.position.getColonne()))
			{	
				LinkedList<Direction> chemin = this.graphe.dijkstraEau(iAct, jAct, carte);
				Case C1 = this.position;	
				Case C2 = C1;
				for (Direction dir : chemin){
					C2 = carte.getVoisin(C1.getLigne(), C1.getColonne(),dir);
					dateSuiv += carte.getTailleCase()/this.getVitesseMoyenne(C1,C2);
					simu.ajouteEvenement(new EvenementDeplacement((int)Math.ceil(dateSuiv), carte, this, dir));
					C1 = C2;
				}
			double deplacement_remplissage = this.deplacer_Eau(carte,simu,dateSuiv,C2);
			simu.ajouteEvenement(new EvenementRemplirReservoir((int)Math.ceil(dateSuiv + deplacement_remplissage) + this.temps_Remplissage, carte, this));
			}

		} catch (PasDeCheminException e) {
			
		}
	}

	public void remplirReservoir(Carte carte, Simulateur simu) {
		this.trouveCheminEau(carte, simu);
	}
		
	public abstract double deplacer_Eau(Carte carte, Simulateur simu, double dateSuiv, Case C);
	
}
