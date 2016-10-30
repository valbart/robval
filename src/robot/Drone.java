package robot;
import enumeration.Incendie;
import terrain.Carte;
import terrain.Case;
import java.util.concurrent.TimeUnit;
import java.io.*;
import enumeration.NatureTerrain;
import enumeration.Direction;
public class Drone extends robot_Reservoir {
    public Drone(Case position) {
	super(position,(float)333.333, 10000, 10000, (float)5.555);
    }

    public void remplissage(Carte carte) {
        boolean recup=false;
        if (carte.checkDir(this.position.getLigne(),this.position.getColonne(),Direction.SUD)){
            if (carte.getCase(this.position.getLigne() + 1,this.position.getColonne()).getNature()==NatureTerrain.EAU) {
	    set_Position(carte.getCase(this.position.getLigne()+1,this.position.getColonne()));
            recup=true;
            }
	}
        if (carte.checkDir(this.position.getLigne(),this.position.getColonne(),Direction.NORD)&& recup==false){
            if (carte.getCase(this.position.getLigne() - 1,this.position.getColonne()).getNature() ==NatureTerrain.EAU) {
                set_Position(carte.getCase(this.position.getLigne()-1,this.position.getColonne()));
                recup=true;
            }
	}
        if (carte.checkDir(this.position.getLigne(),this.position.getColonne(),Direction.EST) && recup==false){
            if (carte.getCase(this.position.getLigne(),this.position.getColonne() + 1).getNature() ==NatureTerrain.EAU) {
                set_Position(carte.getCase(this.position.getLigne(),this.position.getColonne()+1));
                recup=true;
            }
	}
        if (carte.checkDir(this.position.getLigne(),this.position.getColonne(),Direction.SUD) && recup==false){
            if (carte.getCase(this.position.getLigne(),this.position.getColonne() - 1).getNature() ==NatureTerrain.EAU) {
	    set_Position(carte.getCase(this.position.getLigne()+1,this.position.getColonne()-1));
            recup=true;
            }
	}
	
	super.remplissage(carte);
    }

    public int get_Vitesse(NatureTerrain terrain) {
		return (100);
    }

    public void avancer(Direction x,Carte carte){
	if (x==Direction.EST){ 
	    if(this.position.getColonne()==carte.nbColonne){ 
		System.out.println("sort de la carte");
	    }
	    else{
		set_Position(carte.getCase(this.position.getLigne(),this.position.getColonne()+1)); 
	    }						 
	}						       
	else if(x==Direction.OUEST){
	    if(this.position.getColonne()==1){
		System.out.print("sort de la carte");
	    }
	    else{
		set_Position(carte.getCase(this.position.getLigne(),this.position.getColonne()-1));
	    }
	}
	else if(x==Direction.NORD){
	    if(this.position.getLigne()==1){
		System.out.print("sort de la carte");
	    }
	    else{
		set_Position(carte.getCase(this.position.getLigne()-1,this.position.getColonne()));
	    }
	}
	else if (x==Direction.SUD){
	    if(this.position.getLigne()==carte.nbLigne){
		System.out.print("sort de la carte");
	    }
	    else{
		set_Position(carte.getCase(this.position.getLigne()+1,this.position.getColonne()));
	    }
	}
	//calcul de temps 1 case 10m vitesse 27,7m/s?
        try{
            TimeUnit.MILLISECONDS.sleep(361);
            }   
        catch (InterruptedException e){
            }
        }
    }
}
