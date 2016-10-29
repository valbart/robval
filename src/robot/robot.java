package robot;
import enumeration.Incendie;
import terrain.Case;
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
    
    public abstract void derverser_Eau(int volume, incendie feu);
    
    public abstract void remplissage();
    
    public abstract void avancer(Direction x);
    
    public robot(Case position, float debit_Vidage, int litre_Actuel) {
	set_Position(position);
	this.debit_Vidage = debit_Vidage;
	this.litre_Actuel = litre_Actuel;
    }
}

public abstract class robot_Reservoir extends robot {
    int capacite_Reservoir;
    float debit_Remplissage;

    public void remplissage(Carte carte){
	this.litre_Actuel=this.capacite_Reservoir;
	Thread.sleep(1000*this.capacite_Reservoir/this.debit_Remplissage);
    }

    void deverser_Eau(int volume,incendie feu){
	feu.etat-=volume;
	this.litre_Actuel-=volume;
	Thread.sleep(1000*this.volume/this.debit_Vidage);
    }

    public robot_Reservoir(Case position, float debit_Vidage, int litre_Actuel, int capacite_Reservoir,	float debit_Remplissage) {
	super(position, debit_Vidage, litre_Actuel);
	this.capacite_Reservoir = capacite_Reservoir;
	this.debit_Remplissage = debit_Remplissage;
    }
}

public class Drone extends robot_Reservoir {
    public Drone(Case position) {
	super(position, 333.333, 10000, 10000, 5.555);
    }

    void remplissage(Carte carte) {
	// manque verif aux bords
	if (carte[this.position.ligne + 1][this.position.colonne].nature ==NatureTerrain.EAU) {
	    set_Position(carte[this.position.ligne+1][this.position.colonne]);
	}
	else if (carte[this.position.ligne - 1][this.position.colonne].nature ==NatureTerrain.EAU) {
	    set_Position(carte[this.position.ligne-1][this.position.colonne]);
	}
	else if (carte[this.position.ligne][this.position.colonne + 1].nature ==NatureTerrain.EAU) {
	    set_Position(carte[this.position.ligne][this.position.colonne+1]);
	}
	else if (carte[this.position.ligne][this.position.colonne - 1].nature ==NatureTerrain.EAU) {
	    set_Position(carte[this.position.ligne+1][this.position.colonne-1]);
	}
	
	super.remplissage();
    }

    public int get_Vitesse(NatureTerrain terrain) {
		return (100);
    }

    public void avancer(Direction x,Carte carte){
	if (x==Direction.EST){ 
	    if(this.position.colonne==carte.largeur){ 
		printf("sort de la carte");
	    }
	    else{
		set_Position(carte[this.position.ligne][this.position.colonne+1]); 
	    }						 
	}						       
	else if(x==Direction.OUEST){
	    if(this.position.colonne==1){
		System.out.print("sort de la carte");
	    }
	    else{
		set_Position(carte[this.position.ligne][this.position.colonne-1]);
	    }
	}
	else if(x==Direction.NORD){
	    if(this.position.ligne==1){
		System.out.print("sort de la carte");
	    }
	    else{
		set_Position(carte[this.position.ligne-1][this.position.colonne]);
	    }
	}
	else if (x==Direction.SUD){
	    if(this.position.ligne==carte.longueur){
		System.out.print("sort de la carte");
	    }
	    else{
		set_Position(carte[this.position.ligne+1][this.position.colonne]);
	    }
	}
	//calcul de temps 1 case 10m vitesse 27,7m/s?
	Thread.sleep(361);
    }
}

public class Robot_roues extends robot_Reservoir {
    public Robot_roues(Case position) {
	super(position, 20.0, 5000,5000, 8.3); 
    }
    
    void remplissage() {
	// manque verif aux bords
	super.remplissage();
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
	terrain1=carte[this.position.ligne][this.position.colonne].nature;
	if (x==EST){
	    if(this.position.colonne==carte.largeur){
		System.out.print("sort de la carte"); 
	    }
	    else{
		terrain2=carte[this.position.ligne][this.position.colonne+1];
		if (get_Vitesse(terrain2)!=0){
		    this.position.colonne+=1;
		    Thread.sleep(450);
		}
	    }
	}
	else if(x==Direction.OUEST){ 
	
	    if(this.position.colonne==1){
		System.out.print("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne][this.position.colonne-1];
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte[this.position.ligne][this.position.colonne-1]);
		    Thread.sleep(450);
		}
	    }
	}
	else if(x==Direction.NORD){
	    if(this.position.ligne==1){
		System.out.print("sort de la carte"); 
	    }
	    else{
		terrain2=carte[this.position.ligne-1][this.position.colonne];
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte[this.position.ligne-1][this.position.colonne]);
		    Thread.sleep(450);
		}
	    }
	}
	else if (x==Direction.SUD){
	    if(this.position.ligne==carte.longeur){
		System.out.print("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne+1][this.position.colonne];
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte[this.position.ligne+1][this.position.colonne]);
		    Thread.sleep(450);
		}
	    }
	}
    }
}
public class Robot_chenille extends Robot_Reservoir{
    public Robot_chenille(Case position){
	super(position,12.5,2000,2000,6.66);
    }
    
    void remplissage() {
			// manque verif aux bords
	super.remplissage();
    }
    
    public int get_Vitesse(NatureTerrain terrain) {
	if (terrain ==NatureTerrain.EAU | terrain ==NatureTerrain.ROCHER) {
	    return (0);
	}
	else if (terrain =NatureTerrain.FORET) {
	    return 30;
	}
	else{
	    return (60);
	}
    }

    public void avancer(Direction x,Carte carte){
	NatureTerrain terrain1;
	NatureTerrain terrain2;
	terrain1=carte[this.position.ligne][this.position.colonne].nature;
	if (x==EST){
	    if(this.position.colonne==carte.largeur){
		System.out.print("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne][this.position.colonne+1];
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte[this.position.ligne][this.position.colonne+1]);
		    Thread.sleep(10.0/((this.getVitesse(terrain1)+this.getVitesse(terrain2))/2)); 
		}
	    }
	}
	else if(x==Direction.OUEST){
	    if(this.position.colonne==1){
		System.out.print("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne][this.position.colonne-1];
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte[this.position.ligne][this.position.colonne-1]);
		    Thread.sleep(10/((this.getVitesse(terrain1)+this.getVitesse(terrain2))/2));
		}
	    }
	}
	else if(x==Direction.NORD){
	    if(this.position.ligne==1){
		System.out.print("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne-1][this.position.colonne];
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte[this.position.ligne-1][this.position.colonne]);
		    Thread.sleep(10/((this.getVitesse(terrain1)+this.getVitesse(terrain2))/2));
		}
	    }
	}
	else if (x==Direction.SUD){
	    if(this.position.ligne==carte.longueur){
		System.out.print("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne+1][this.position.colonne];
		if (get_Vitesse(terrain2)!=0){
		    set_Position(carte[this.position.ligne+1][this.position.colonne]);
		    Thread.sleep(10/((this.getVitesse(terrain1)+this.getVitesse(terrain2))/2));
		}
	    }
	}
    }
}
public class Robot_pattes extends robot {
    public Robot_pattes(Case position) {
	super(position, 10.0, 10 ^ 6);
    }
    
    public void DeverserEau(int volume, incendie feu) {
	feu.etat -= volume;
    }
	
    public void Remplissage(){
	    
    }
    
    public int get_Vitesse(NatureTerrain terrain) {
	    
	if (terrain ==NatureTerrain.EAU) { 
	    return (0);
	}
	else if (terrain==NatureTerrain.ROCHER) {
	    return (10);
	}
	else {
	    return (30);
		
	}
    }
    
}
    
