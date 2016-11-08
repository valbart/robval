package robot;
import terrain.incendie;
import terrain.Case;
import terrain.Carte;
import java.io.*;
import java.util.concurrent.TimeUnit;

import enumeration.NatureTerrain;
import enumeration.Direction;
import graphe.*
;

public abstract class robot {
    private boolean Busy;
	public Case position;
    protected float debit_Vidage;
    protected int litre_Actuel;
    private graphe graphe;
    
    public abstract int get_Vitesse(NatureTerrain terrain);
    
    public abstract void deverser_Eau(int volume, incendie feu);
    
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
					TimeUnit.MILLISECONDS.sleep(450);
				} catch (InterruptedException e) {
					
				}
			} else {
				System.out.println("Impossible pour ce robot d'avancer sur ce terrain");
			}
		}
	}
    
    public robot(Case position, float debit_Vidage, int litre_Actuel,graphe graphe, boolean Busy) {
	this.graphe = graphe;
    this.position=position;
	this.debit_Vidage = debit_Vidage;
	this.litre_Actuel = litre_Actuel;
	this.Busy = Busy;
    }
    
}

 
