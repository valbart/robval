package exceptions;
/**
 * Exception levée lorsque qu'aucun chemin n'est trouvé pour aller sur une case précise ou aller chercher de l'eau.
 */
public class PasDeCheminException extends Exception{
		  public PasDeCheminException(){
			  System.out.println("Pas de chemin pour aller a cette case");
		  }

}
