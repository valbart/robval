package gui;
import java.awt.Color;
import io.DonneesSimulation;


/**
 * Programme principale. Instancie le simulateur.  
 *
 */

public class TestSimulateur {
		
    public static void main(String[] args) {    	
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }               
        DonneesSimulation data = new DonneesSimulation();      
        int tailleCarre = 25;
        int longueurGui = tailleCarre*data.carte.getNbColonne();
        int largeurGui = tailleCarre*data.carte.getNbLigne();     
        GUISimulator gui = new GUISimulator(longueurGui+100, largeurGui+100, Color.WHITE);        
        Simulateur simu = new Simulateur(gui, data, tailleCarre, args[0]);
    }
    
}
