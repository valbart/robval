package exceptions;
/**
 * Exception lev�e lorsque qu'aucun chemin n'est trouv� pour aller sur une case pr�cise ou aller chercher de l'eau.
 */
public class PasDeCheminException extends Exception{
		  public PasDeCheminException(){
			  System.out.println("Pas de chemin pour aller a cette case");
		  }

}
