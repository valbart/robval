package gui;

import java.util.*;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import io.DonneesSimulation;
import io.LecteurDonnees;

import graphe.*;
import enumeration.TypeRobot;

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
        
        //TEST GRAPHE
        graphe grapheRoues = new graphe(data.carte.nbLigne, data.carte.nbColonne);
        grapheRoues.creerGraphe(data.carte, TypeRobot.ROUES);
        //System.out.println(grapheRoues.toString());
        sommet sDeb = new sommet(6,5);
        sommet sFin = new sommet(1,3);
        System.out.println(grapheRoues.toString());
        LinkedList<sommet> chemin = grapheRoues.dijkstra(sDeb,sFin);
        /*TEST TROUVER CHEMIN*/
        int nb = 0;
        System.out.println("CHEMIN:\n");
        for (sommet s : chemin) {
        	System.out.println("(" + s.getI() + "," + s.getJ() + ")\n");
        	nb+=1;
        }
        System.out.println(nb);
        //*/
        
        
        GUISimulator gui = new GUISimulator(longueurGui+100, largeurGui+100, Color.WHITE);
        
        Simulateur simu = new Simulateur(gui, data, tailleCarre);
    }
}
