package terrain;

public class incendie {
	
	private Case position;
	private int intensite;
	
	public incendie() {
		this.intensite = 0; 
	}
	
	public incendie(Case position, int intensite) {
		this.position = position;
		this.intensite = intensite;
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
