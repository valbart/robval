package robot;
import terrain.incendie;
import terrain.Case;
import terrain.Carte;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import enumeration.NatureTerrain;
import enumeration.Direction;
import graphe.*;
import gui.*;
import evenements.*;
import exceptions.*;

public abstract class robot {
    private boolean Busy;
	public Case position;
    protected int debit_Vidage;
    protected int litre_Actuel;
    protected graphe graphe;
    protected int temps_vidage;
    
    public robot(Case position) {
    	this.position=position;
    	this.Busy = false;
    }
    
    public int getLitre() {
    	return this.litre_Actuel;
    }
    
    public abstract int get_Vitesse(NatureTerrain terrain);
    
    public abstract void deverser_Eau(incendie feu);
    
    public abstract void remplissage(Carte carte);
     
    public void set_Position(Case x) {
	this.position = x;
    }
    
    public Case get_Position() {
	return this.position;
    }
    
    
    public boolean isBusy() {
    	return this.Busy;
    }
    
    public void setBusy(boolean b) {
    	this.Busy = b ;
    }
    
    
	public void avancer(Direction x, Carte carte) {
		Case caseActuelle = this.get_Position();
		int i = caseActuelle.getLigne();
		int j = caseActuelle.getColonne();
		if (carte.checkDir(i, j, x)) {
			Case newCase = carte.getVoisin(i, j, x);
			NatureTerrain newNature = newCase.getNature();
			if (this.get_Vitesse(newNature) != 0) {
				this.set_Position(newCase);
				try {
					TimeUnit.MILLISECONDS.sleep(50);
				} catch (InterruptedException e) {
					
				}
			} else {
				System.out.println("Impossible pour ce robot d'avancer sur ce terrain");
			}
		}
	}
    
	
	public void trouveChemin(incendie incendie, LinkedList<incendie> listeIncendies, Carte carte, Simulateur simu) {
		this.setBusy(true);
		int iAct = this.position.getLigne();
		int jAct = this.position.getColonne();
		try {
			long dateActuelle = simu.getDateActuelle();
			int i = 1;
			if (incendie.getPosition().getLigne() != this.position.getLigne() || incendie.getPosition().getColonne() != this.position.getColonne()) {
				LinkedList<Direction> chemin = this.graphe.dijkstra(iAct, jAct, incendie.getPosition().getLigne(), incendie.getPosition().getColonne());
				for (Direction dir : chemin){
					simu.ajouteEvenement(new EvenementDeplacement(dateActuelle+i, carte, this, dir));
					i++;
				}
			}
			simu.ajouteEvenement(new EvenementDeverserEau(dateActuelle+i, this, listeIncendies, incendie, carte, simu));
		} catch (PasDeCheminException e) {
			
		}
	}
	
	
	
	public void trouveCheminEau(Carte carte, Simulateur simu) {
		this.setBusy(true);
		int iAct = this.position.getLigne();
		int jAct = this.position.getColonne();
		try {
			int i = 1;
			long dateActuelle = simu.getDateActuelle();
			if (! carte.estPretEau(this.position.getLigne(), this.position.getColonne())) {
				LinkedList<Direction> chemin = this.graphe.dijkstraEau(iAct, jAct, carte);
				for (Direction dir : chemin){
					simu.ajouteEvenement(new EvenementDeplacement(dateActuelle+i, carte, this, dir));			
					i++;
				}
			}
			simu.ajouteEvenement(new EvenementRemplirReservoir(dateActuelle+i, carte, this));
		} catch (PasDeCheminException e) {
			
		}
	}
	
    public void checkReservoir(Carte carte, Simulateur simu) {
    	if (this.litre_Actuel == 0) {
    		System.out.println("Le robot est vide il va se remplir");
    		this.trouveCheminEau(carte, simu);
    	}
    	this.setBusy(false);
    }
	
    
}

 
