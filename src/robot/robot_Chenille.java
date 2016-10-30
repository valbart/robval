package robot;
import enumeration.Incendie;
import terrain.Carte;
import terrain.Case;
import java.io.*;
import enumeration.NatureTerrain;
import enumeration.Direction;

public class robot_Chenille extends robot_Reservoir{
    public robot_Chenille(Case position){
	super(position,(float)12.5,2000,2000,(float)6.66);
    }
    
    public void remplissage(Carte carte) {
			// manque verif aux bords
	super.remplissage(carte);
    }
    
    public int get_Vitesse(NatureTerrain terrain) {
	if (terrain ==NatureTerrain.EAU | terrain ==NatureTerrain.ROCHE) {
	    return (0);
	}
	else if (terrain ==NatureTerrain.FORET) {
	    return 30;
	}
	else{
	    return (60);
	}
    }

    public void avancer(Direction x,Carte carte){
	NatureTerrain terrain1;
	NatureTerrain terrain2;
	terrain1=carte.getCase(this.position.getLigne(),this.position.getColonne()).getNature();
	if (x==Direction.EST){
	    if(this.position.getColonne()==carte.nbColonne){
		System.out.print("sort de la carte");
	    }
	    else{
		terrain2=carte.getCase(this.position.getLigne(),this.position.getColonne()+1).getNature();
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte.getCase(this.position.getLigne(),this.position.getColonne()+1));
		    Thread.sleep(10.0/((this.get_Vitesse(terrain1)+this.get_Vitesse(terrain2))/2)); 
		}
	    }
	}
	else if(x==Direction.OUEST){
	    if(this.position.getColonne()==1){
		System.out.print("sort de la carte");
	    }
	    else{
		terrain2=carte.getCase(this.position.getLigne(),this.position.getColonne()-1).getNature();
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte.getCase(this.position.getLigne(),this.position.getColonne()-1));
		    Thread.sleep(10/((this.get_Vitesse(terrain1)+this.get_Vitesse(terrain2))/2));
		}
	    }
	}
	else if(x==Direction.NORD){
	    if(this.position.getLigne()==1){
		System.out.print("sort de la carte");
	    }
	    else{
		terrain2=carte.getCase(this.position.getLigne()-1,this.position.getColonne()).getNature();
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte.getCase(this.position.getLigne()-1,this.position.getColonne()));
		    Thread.sleep(10/((this.get_Vitesse(terrain1)+this.get_Vitesse(terrain2))/2));
		}
	    }
	}
	else if (x==Direction.SUD){
	    if(this.position.getLigne()==carte.nbLigne){
		System.out.print("sort de la carte");
	    }
	    else{
		terrain2=carte.getCase(this.position.getLigne()+1,this.position.getColonne()).getNature();
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte.getCase(this.position.getLigne()+1,this.position.getColonne()));
		    Thread.sleep(10/((this.get_Vitesse(terrain1)+this.get_Vitesse(terrain2))/2));
		}
	    }
	}
    }
}
