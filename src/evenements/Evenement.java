package evenements;
import java.util.LinkedList;
import robot.*;
import terrain.*;
import java.util.*;


abstract public class Evenement implements Comparable<Evenement> {
	private long date;
	/**
	 * Constructeur.
	 * @param newEventDate date à laquelle l'événement devra s'éxécuter
	 */
	public Evenement(long newEventDate){
		this.date = newEventDate;
	}

	/**
	 * Accesseur sur la date d'éxécution de l'événement.
	 * @return (long) date d'éxécution de l'événement
	 */
	public long getDate(){
		return this.date;
	}
	/**
	 * Actions à effectuer quand la date d'éxécution de l'événement et celle du simulateur coïncident.
	 */
	abstract public void execute();

		/**
	 * Réalise l'interface Comparable et permet l'ordonnancement des événements dans une file de priorités
	 * @param e événement à comparer avec celui appelant la méthode
	 * @return 1 si l'événement appelant la méthode s'éxécute après le paramètre, 0 s'ils s'éxécutent simultanément, -1 sinon
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
