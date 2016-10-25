package robot;

public abstract class robot {
    private case position;
    protected  int capacite_reservoir;
    public int litre_actuel;
    protected  int temps_remplissage;
    protected int temps_vidage;
    int vol;
    case getPosition(){
	return this.position;
    }
    void setPosition(case x){
	this.position=x;
    }
    abstract int getVitesse(NatureTerrain terrain);
    public void deverserEau(incendie feu){
	while(feu.etat>0 && this.litre_actuel>0){
	    this.litre_actuel-=1;
	    feu.etat-=1;
	    sleep this.temps_vidage/this.capacite_reservoir;
	}
    }
    public void remplir_Reservoir(void){
	while(this.litre_actuel<this.capacite_reservoir){
	    this.litre_actuel+=1;
	    sleep this.temps_remplissage/this.capacite_reservoir;
	    
	}
    abstract void avancer_remplir(case point_eau);
    abstract void avancer_verser(incendie feu);
    protected robot(case position,int temps_remplissage,int temps_vidage,int capacite_reservoir,int vol,int litre_actuel){
	setPosition(position);
	this.capacite_reservoir=capacite_reservoir;
	this.temps_remplissage=temps_remplissage;
	this.temps_vidage=temps_vidage;
	this.vol=vol;
	this.litre_actuel=litre_actuel;
    }
}
class drone extends robot{
    protected drone(case position){
	setPosition(position);
	this.capacite_reservoir=10000;
	this.temps_remplissage=1800;
	this.temps_vidage=30;
	this.vol=1;
	this.litre_actuel=this.capacite_reservoir;
    }
    int getVitesse(NatureTerrain terrain){
	return 100;
	//ou peut etre lu dans le fichier de données <150
    }
    
    

}
class robot_roue extends robot{
    protected robot_roue(case position){
	setPosition(position);
	this.capacite_reservoir=5000;
	this.temps_remplissage=600;
	this.temps_vidage=250;
	this.vol=0;
	this.litre_actuel=this.capacite_reservoir;
    }
    int getVitesse(NatureTerrain terrain){
	if(terrain==TERRAIN_LIBRE | HABITAT){
	    return (80);
	}
	else{
	    return(0);
	}
    }
 }
