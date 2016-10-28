package robot;

public abstract class robot {
    public Case position;
    protected float debit_Vidage;
    protected int litre_Actuel;
    public void set_Position(Case x){
	this.position=x;
    }
    public Case get_Position(){
	return this.position;
    }
    public abstract int get_Vitesse(NatureTerrain terrain);
    public abstract void derverser_Eau(int volume,incendie feu);
    public abstract void remplissage();
    public abstract void avancer(Direction x);
    public robot(Case position,float debit_Vidage,int litre_Actuel){
	set_Position(position);
	this.debit_Vidage=debit_Vidage;
	this.litre_Actuel=litre_Actuel;
    }
}
public class robot_Reservoir extends robot{
    int capacite_Reservoir;
    float debit_Remplissage;
    void remplissage(){
	this.litre_Actuel=this.capacite_Reservoir;
	sleep this.capacite_Reservoir/this.debit_Remplissage;
    }
    void deverser_Eau(int volume,incendie feu){
	feu.etat-=volume;
	this.litre_Actuel-=volume;
	sleep this.litre_Actuel/this.debit_Vidage;
    }
    public robot_Reservoir(Case position,float debit_Vidage,int litre_Actuel,int capacite_Reservoir,float debit_Remplissage){
	super(position,debit_Vidage,litre_actuel);
	this.capacite_Reservoir=capacite_Reservoir;
	this.debit_Remplissage=debit_Remplissage;
    }
}
public class Drone extends robot_Reservoir{
    public Drone(Case position){
	super(position,333.333,10000,10000,5.555);
    }
    void remplissage(){
	//manque verif aux bords
	if(carte[this.position.ligne+1][this.position.colonne].nature==EAU){
	    this.position.ligne+=1;
	}
	else if(carte[this.position.ligne-1][this.position.colonne].nature==EAU){
	    this.position.ligne-=1;
	}
	else if(carte[this.position.ligne][this.position.colonne+1].nature==EAU){
	    this.position.colonne+=1;
	}
	if(carte[this.position.ligne][this.position.colonne-1].nature==EAU){
	    this.position.colonne-=1;
	}
	
	super.remplissage();
    }

    public int get_Vitesse(NatureTerrain terrain){
	return (100);
    }
    public void avancer(Direction x){
	if (x==EST){
	    if(this.position.colonne==m){
		printf("sort de la carte");
	    }
	    else{
		this.position.colonne+=1;
	    }
	}
	else if(x==OUEST){
	    if(this.position.colonne==1){
		printf("sort de la carte");
	    }
	    else{
		this.position.colonne-=1;
	    }
	}
	else if(x==NORD){
	    if(this.position.ligne==1){
		printf("sort de la carte");
	    }
	    else{
		this.position.ligne-=1;
	    }
	}
	else if (x==SUD){
	    if(this.position.ligne==n){
		printf("sort de la carte");
	    }
	    else{
		this.position.ligne+=1;
	    }
	}
	//calcul de temps 1 case 10m vitesse 27,7m/s?
	wait 0.361;
    }
public class Robot_roues extends robot_Reservoir{
    public Robot_roues(Case position){
	super(position,20,5000,5000,8.3);
    }
    void remplissage(){
	//manque verif aux bords
	super.remplissage();
    }

    public int get_Vitesse(NatureTerrain terrain){
	if(terrain==TERRAIN_LIBRE | terrain==HABITAT){
	return (80);
	}
	else{
	    return 0;
	}
    }
    public void avancer(Direction x){
	NatureTerrain terrain1;
	NatureTerrain tarrain2;
	terrain1=carte[this.position.ligne][this.position.colonne].nature;
	if (x==EST){
	    if(this.position.colonne==m){
		printf("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne][this.position.colonne+1];
		if (get_Vitesse(terrain2!=0)){
		    this.position.colonne+=1;
		    wait 0.450;
		}
	    }
	}
	else if(x==OUEST){
	    if(this.position.colonne==1){
		printf("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne][this.position.colonne-1];
		if (get_Vitesse(terrain2!=0)){
		    this.position.colonne-=1;
		    wait 0.450;
		}
	    }
	}
	else if(x==NORD){
	    if(this.position.ligne==1){
		printf("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne-1][this.position.colonne];
		if (get_Vitesse(terrain2!=0)){
		    this.position.ligne-=1;
		    wait 0.450;
		}
	    }
	}
	else if (x==SUD){
	    if(this.position.ligne==n){
		printf("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne+1][this.position.colonne];
		if (get_Vitesse(terrain2!=0)){
		    this.position.ligne+=1;
		    wait 0.450;
		}
	    }
	}
    }
    
public Robot_chenille(Case position){
	super(position,12.5,2000,2000,6.66);
    }
    void remplissage(){
	//manque verif aux bords
	super.remplissage();
    }

    public int get_Vitesse(NatureTerrain terrain){
	if(terrain==EAU | terrain==ROCHER){
	return (0);
	}
	else if(terrain=FORET){
	    return 30;
	}
	else{
	    return(60);
	}
    }
    public void avancer(Direction x){
	NatureTerrain terrain1;
	NatureTerrain tarrain2;
	terrain1=carte[this.position.ligne][this.position.colonne].nature;
	if (x==EST){
	    if(this.position.colonne==m){
		printf("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne][this.position.colonne+1];
		if (get_Vitesse(terrain2!=0)){
		    this.position.colonne+=1;
		    wait 10/((this.getVitesse(terrain1)+this.getVitesse(terrain2))/2);
		}
	    }
	}
	else if(x==OUEST){
	    if(this.position.colonne==1){
		printf("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne][this.position.colonne-1];
		if (get_Vitesse(terrain2!=0)){
		    this.position.colonne-=1;
		    wait 10/((this.getVitesse(terrain1)+this.getVitesse(terrain2))/2);
		}
	    }
	}
	else if(x==NORD){
	    if(this.position.ligne==1){
		printf("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne-1][this.position.colonne];
		if (get_Vitesse(terrain2!=0)){
		    this.position.ligne-=1;
		    wait 10/((this.getVitesse(terrain1)+this.getVitesse(terrain2))/2);
		}
	    }
	}
	else if (x==SUD){
	    if(this.position.ligne==n){
		printf("sort de la carte");
	    }
	    else{
		terrain2=carte[this.position.ligne+1][this.position.colonne];
		if (get_Vitesse(terrain2!=0)){
		    this.position.ligne+=1;
		    wait 10/((this.getVitesse(terrain1)+this.getVitesse(terrain2))/2);
		}
	    }
	}
    }

    
    public class Robot_pattes extends robot{
	public Robot_pattes(Case position){
	    super(position,10.0,10^6);
	}
	public void DeverserEau(int volume,incendie feu){
	    feu.etat-=volume;
	}
	public void Remplissage(){
	    NULL;
	}
	public int get_Vitesse(NatureTerrain terrain){
	    
	    if(terrain==EAU){
		return (0);
	    }
	    else if(terrain=ROCHER){
		return (10);
	    }
	    else{
		return(30);
		
	    }   
	}

	



    
}
