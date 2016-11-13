package evenements;
/**
 * Classe abstraite qui définie les évènements générique.
 * Ils sont caractérisés par une date d'execution et une manière particulière de s'éxecuter.
 * 
 */

abstract public class Evenement implements Comparable<Evenement> {
	protected long date;

	public Evenement(long newEventDate){
		this.date = newEventDate;
	}


	public long getDate(){
		return this.date;
	}
	
	/**
	 * Action à effectuer à la date this.date
	 */
	abstract public void execute();

		/**
	 * Permet l'ordonnancement des événements dans la file de priorité du simulateur
	 * @param e ordonnancement à comparer avec l'instance de la classe Evenement.
	 */
	public int compareTo(Evenement e){
		if (this.date > e.getDate()) {
			return 1;
		} else if (this.date == e.getDate()) {
			return 0;
		} else {
			return -1;
		}
	}

}
