package terrain;

/**
 * Classe des incendies définie par une position, une intensité (en litre d'eau nécessaire pour l'exctinction) et un statut : en cours d'extinction par un robot ou non.
 *
 */
public class incendie {
	
	private Case position;
	private int intensite;
	public boolean enCourExctinction;
	
	public incendie() {
		this.intensite = 0; 
	}
	
	public incendie(Case position, int intensite) {
		this.position = position;
		this.intensite = intensite;
		this.enCourExctinction = false;
	}
	
	public Case getPosition() {
		return this.position;
	}
	
	public int getIntensite() {
		return this.intensite;
	}
	
	public void setPosition(Case c) {
		this.position = c;
	}
	
	public void setIntensite(int intensite) {
		this.intensite = intensite;
	}
	
}
