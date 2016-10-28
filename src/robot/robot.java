package robot;
import terrain.Case;
import enumeration.NatureTerrain;
import enumeration.Direction;
// T'avais oublié les import, je les ai rajouté

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

	public void remplissage(){
	this.litre_Actuel=this.capacite_Reservoir;
	sleep (this.capacite_Reservoir/this.debit_Remplissage);
    }

	void deverser_Eau(int volume,incendie feu){
	feu.etat-=volume;
	this.litre_Actuel-=volume;
	sleep this.litre_Actuel/this.debit_Vidage; // la fonction sleep est pas reconnu, faut régler ce pb
	//ERREUR ICI : c'est sleep this.volume/this.debit_Vidage
    }

	public robot_Reservoir(Case position, float debit_Vidage, int litre_Actuel, int capacite_Reservoir,
			float debit_Remplissage) {
		super(position, debit_Vidage, litre_Actuel);
		this.capacite_Reservoir = capacite_Reservoir;
		this.debit_Remplissage = debit_Remplissage;
	}
}

public class Drone extends robot_Reservoir {
	public Drone(Case position) {
		super(position, 333.333, 10000, 10000, 5.555);
	}

	void remplissage() {
		// manque verif aux bords
		//Fait une fonction a part pour la verif aux bords
		if (carte[this.position.ligne + 1][this.position.colonne].nature == EAU) {
			this.position.ligne += 1;
			// ERREUR : a chaque fois que t'utilises le type NatureTerrain faut préciser avant du genre :
			// this.position.colonne == NatureTerrain.EAU
		} else if (carte[this.position.ligne - 1][this.position.colonne].nature == EAU) {
			this.position.ligne -= 1;
			// ERREUR : this.position.ligne et this.position.colonne sont en private 
			// creer deux variable locale dans lesquelles tu récupères position ligne et colonne 
			// avec les methodes getLigne et getColonne de la classe case
		} else if (carte[this.position.ligne][this.position.colonne + 1].nature == EAU) {
			this.position.colonne += 1;
		} else if (carte[this.position.ligne][this.position.colonne - 1].nature == EAU) {
			this.position.colonne -= 1;
		}

		super.remplissage();
	}

	public int get_Vitesse(NatureTerrain terrain) {
		return (100);
	}

	public void avancer(Direction x){
	if (x==EST){ //même problème c'est x == Direction.EST
	    if(this.position.colonne==m){ //ca j'imagine t'as mis m au pif parcequ'on a pas encore définit la carte
		printf("sort de la carte");
	    }
	    else{
		this.position.colonne+=1; //Grosse erreur là : t'essaye de modifier this.position.colonne mais ça c'est une Case
	    }						  // et la position de la case est toujours fixe, c'est l'attribut Case du robot qui doit 
	}							 // changer avec this.setPosition(x)
	else if(x==OUEST){
	    if(this.position.colonne==1){
		printf("sort de la carte");
	    }
	    else{
		this.position.colonne-=1; //idem
	    }
	}
	else if(x==NORD){
	    if(this.position.ligne==1){
		printf("sort de la carte");
	    }
	    else{
		this.position.ligne-=1; //idem
	    }
	}
	else if (x==SUD){
	    if(this.position.ligne==n){
		printf("sort de la carte");
	    }
	    else{
		this.position.ligne+=1; //idem
	    }
	}
	//calcul de temps 1 case 10m vitesse 27,7m/s?
	wait 0.361;
    }

// GROS PB t'as du mal fermer une { après ta classe.. c'est pour ce genre de truc faut absolument tout faire
	// en fichiers séparés
	public class Robot_roues extends robot_Reservoir {
		public Robot_roues(Case position) {
			super(position, 20, 5000, 5000, 8.3); // prolbème dans le type des variables que tu file au constructeur
		}

		void remplissage() {
			// manque verif aux bords
			super.remplissage();
		}

		public int get_Vitesse(NatureTerrain terrain) {
			if (terrain == TERRAIN_LIBRE | terrain == HABITAT) { //NatureTerrain.TERRAIN_LIBRE même problème qu'avant
				return (80);
			} else {
				return 0;
			}
		}

	public void avancer(Direction x){ // Pourquoi y'a deux fonctions avancer différentes?
	NatureTerrain terrain1;
	NatureTerrain tarrain2;
	terrain1=carte[this.position.ligne][this.position.colonne].nature;
	if (x==EST){
	    if(this.position.colonne==m){ //même problème qu'avant this.position.colonne est private
		printf("sort de la carte"); // printf ça existe pas en java
	    }
	    else{
		terrain2=carte[this.position.ligne][this.position.colonne+1];
		if (get_Vitesse(terrain2!=0)){
		    this.position.colonne+=1;
		    wait 0.450;
		}
	    }
	}
	else if(x==OUEST){ //même problème qu'avant faut tout corriger et au lieu de faire des else if copier coller
						// doit y'avoir moyen de factoriser
	}
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

		void remplissage() {
			// manque verif aux bords
			super.remplissage();
		}

		public int get_Vitesse(NatureTerrain terrain) {
			if (terrain == EAU | terrain == ROCHER) {
				return (0);
			} else if (terrain = FORET) {
				return 30;
			} else {
				return (60);
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
		    wait 10/((this.getVitesse(terrain1)+this.getVitesse(terrain2))/2); //wait existe pas
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

				if (terrain == EAU) { //même pb
					return (0);
				} else if (terrain = ROCHER) {
					return (10);
				} else {
					return (30);

				}
			}

}
