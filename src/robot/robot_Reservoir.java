package robot;
import enumeration.Incendie;
import terrain.Case;
import java.io.*;
import terrain.Carte;
import enumeration.NatureTerrain;
import enumeration.Direction;
public abstract class robot_Reservoir extends robot {
    int capacite_Reservoir;
    float debit_Remplissage;

    public void remplissage(Carte carte){
	this.litre_Actuel=this.capacite_Reservoir;
	Thread.sleep(1000.0*this.capacite_Reservoir/this.debit_Remplissage);
    }

    public void deverser_Eau(int volume,Incendie feu){
	feu.etat-=volume;
	this.litre_Actuel-=volume;
	Thread.sleep(1000*volume/this.debit_Vidage);
    }

    public robot_Reservoir(Case position, float debit_Vidage, int litre_Actuel, int capacite_Reservoir,	float debit_Remplissage) {
	super(position, debit_Vidage, litre_Actuel);
	this.capacite_Reservoir = capacite_Reservoir;
	this.debit_Remplissage = debit_Remplissage;
    }
    public abstract void  avancer(Direction x,Carte carte);
}
