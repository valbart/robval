package robot;
import terrain.incendie;
import terrain.Case;
import terrain.Carte;
import java.io.*;
import enumeration.NatureTerrain;
import enumeration.Direction;
public class robot_Roues extends robot_Reservoir {
    public robot_Roues(Case position) {
	super(position,(float)20.0, 5000,5000,(float)8.3); 
    }
    
    public void remplissage(Carte carte){
        
	super.remplissage(carte);
    }
    
    public int get_Vitesse(NatureTerrain terrain) {
	if (terrain ==NatureTerrain.TERRAIN_LIBRE | terrain ==NatureTerrain.HABITAT) {
	    return (80);
	}
	else {
	    return(0);
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
		    this.set_Position(carte.getCase(this.position.getLigne(),this.position.getColonne()+1));
		    Thread.sleep(450);
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
		    Thread.sleep(450);
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
		    Thread.sleep(450);
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
                    
		    Thread.sleep(450);
                                     
                }          
            }          
    	}   
 }
}
