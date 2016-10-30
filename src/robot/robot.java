package robot;
import terrain.incendie;
import terrain.Case;
import terrain.Carte;
import java.io.*;
import enumeration.NatureTerrain;
import enumeration.Direction;


public abstract class robot {
    public Case position;
    protected float debit_Vidage;
    protected int litre_Actuel;
     
    public void set_Position(Case x) {
	this.position = x;
    }
    
    public Case get_Position() {
	return this.position;
    }
    
    public abstract int get_Vitesse(NatureTerrain terrain);
    
    public abstract void deverser_Eau(int volume, incendie feu);
    
    public abstract void remplissage(Carte carte);
    
    public abstract void avancer(Direction x,Carte carte);
    
    public robot(Case position, float debit_Vidage, int litre_Actuel) {
	this.position=position;
	this.debit_Vidage = debit_Vidage;
	this.litre_Actuel = litre_Actuel;
    }
}

 
