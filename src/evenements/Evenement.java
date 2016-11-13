package evenements;
/**
 * Classe abstraite qui d�finie les �v�nements g�n�rique.
 * Ils sont caract�ris�s par une date d'execution et une mani�re particuli�re de s'�xecuter.
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
	 * Action � effectuer � la date this.date
	 */
	abstract public void execute();

		/**
	 * Permet l'ordonnancement des �v�nements dans la file de priorit� du simulateur
	 * @param e ordonnancement � comparer avec l'instance de la classe Evenement.
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
