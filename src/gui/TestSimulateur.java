package gui;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import io.DonneesSimulation;
import io.LecteurDonnees;

public class TestSimulateur {
    public static void main(String[] args) {
    	
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
                
        DonneesSimulation data = new DonneesSimulation();

        try {
            LecteurDonnees.lire(args[0], data);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
        
        int tailleCarre = 50;
        int longueurGui = tailleCarre*data.carte.getNbColonne();
        int largeurGui = tailleCarre*data.carte.getNbLigne();
        
        GUISimulator gui = new GUISimulator(longueurGui+100, largeurGui+100, Color.WHITE);
        
        Simulateur simu = new Simulateur(gui, data, tailleCarre);
    }
}
