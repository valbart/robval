package gui;

import java.util.*;
import terrain.*;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import io.DonneesSimulation;
import io.LecteurDonnees;

import graphe.*;
import enumeration.Direction;
import enumeration.TypeRobot;
import evenements.*;
import robot.*;

public class TestSimulateur {
	
	public robot trouveRobotNonOccupe(DonneesSimulation data) {
		robot r = null;
		for (robot rob : data.robots) {
			if (!rob.isBusy()) {
				r = rob; 
				break;
			}
		}
		return r;
	}
	
	public incendie trouveIncendieNonGerer(DonneesSimulation data) {
		incendie feu = null;
		for (incendie i : data.incendies) {
			if (!i.enCourExctinction) {
				feu = i; 
				break;
			}
		}
		return feu;
	}
	
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
        
        
        
        
        int tailleCarre = 15;
        int longueurGui = tailleCarre*data.carte.getNbColonne();
        int largeurGui = tailleCarre*data.carte.getNbLigne();
        
        //TEST AUTO DEPLACEMENT ROBOTS
        

        //
        
        
        GUISimulator gui = new GUISimulator(longueurGui+100, largeurGui+100, Color.WHITE);
        
        Simulateur simu = new Simulateur(gui, data, tailleCarre);
        
       
        //robot RobotTest = data.robots[0];
        //RobotTest.trouveCheminEau(data.carte, simu);
        
        
        
        
        /*STRATEGIE DE MERDE
        while (!data.incendies.isEmpty()) {
        	for (int i = 0; i < data.robots.length; i++) {
        		if (!(data.robots[i].isBusy())) {
        			if (!(data.robots[i].getLitre() <= 0)) {
        				for (incendie feu : data.incendies) {
        					if (!feu.enCourExctinction) {
        						data.robots[i].trouveChemin(feu, data.incendies, data.carte, simu);
        						feu.enCourExctinction = true;
        					}
        				}
        			} else {
        				data.robots[i].trouveCheminEau(data.carte, simu);
        			}
        		}
        	}
        }
        */
        
        //STRATEGIE UN PEU MIEUX MAIS POURRIE QUAND MEME
        
        
    }
}
