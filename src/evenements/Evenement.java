package evenements;
import java.util.LinkedList;
import robot.*;
import terrain.*;
import java.util.*;


abstract public class Evenement{
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
}
