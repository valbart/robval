package io;
import terrain.*;
import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
import enumeration.NatureTerrain;
import enumeration.TypeRobot;
import robot.*;
import graphe.*;


/**
 * Lecteur de cartes.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * et enregistrées dans un objet de type DonneesSimulation
 */

public class LecteurDonnees {
    /**
     * Lit et et enregistre le contenu d'un fichier de donnees
     * @param fichierDonnees nom du fichier à lire
     */
    public static void lire(String fichierDonnees, DonneesSimulation data)
        throws FileNotFoundException, DataFormatException {
        System.out.println("\n == Lecture du fichier" + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        data.initGraphes();
        lecteur.lireCarte(data);
        lecteur.lireIncendies(data);
        lecteur.lireRobots(data);
        scanner.close();
        System.out.println("\n == Lecture terminee");
    }
    
    /**
     * 
     * @param S chaine contenant le type du robot à instancier
     * @param pos la position initiale du robot
     * @param data l'objet ou sont enregistrées les données
     * @return un robot du type spécifié dans la chaine S
     * @throws DataFormatException
     */
    private robot parseType(String S, Case pos, DonneesSimulation data) throws DataFormatException {
    	robot newRobot;
    	switch(S) {
    	case "DRONE":
    		newRobot = new Drone(pos, data.graphes[0]);
    		break;
    	case "CHENILLES": 
    		newRobot = new robot_Chenille(pos, data.graphes[1]);
    		break;
    	case "PATTES":
    		newRobot = new robot_Pattes(pos, data.graphes[3]);
    		break;
    	case "ROUES":
    		newRobot = new robot_Roues(pos, data.graphes[2]);
    		break;
    	default:
    			throw new DataFormatException("Robot inconnu : carte invalide");
    	}
    	return newRobot;
    }
    
    /** Associe un entier à chaque type de robot
     * @param type type de robot
     * @return l'entier associé
     */
	private int typeToInt(TypeRobot type) {
		int i = 0;
		switch(type) {
		case DRONE :
			i = 0;
			break;
		case CHENILLES :
			i = 1;
			break;
		case PATTES : 
			i = 3;
			break;
		case ROUES : 
			i = 2;
			break;		
		}
		return i;
	}

    private static Scanner scanner;

    /**
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit, initialise la carte et enregistre les données de la carte.
     * @param data objet où sont enregistrés les donéees.
     * @throws ExceptionFormatDonnees
     */
    private void lireCarte(DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            data.carte.setNbLigne(nbLignes);
            
            int nbColonnes = scanner.nextInt();
            data.carte.setNbColonne(nbColonnes);
            
            int tailleCases = scanner.nextInt();
            
            data.carte.setTailleCase(tailleCases);
            
            data.carte.setMap();

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    lireCase(lig, col, data);
                }
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
    }




    /**
     * Lit et enregistre les données d'une case de la carte.
     */
    private void lireCase(int lig, int col, DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        try {
            chaineNature = scanner.next();
            verifieLigneTerminee();
            data.carte.setCaseMap(lig, col, NatureTerrain.valueOf(chaineNature));
            
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }
    }


    /**
     * Lit et enregistre les donnees des incendies.
     */
    private void lireIncendies(DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            System.out.println("Nb d'incendies = " + nbIncendies);
            data.setIncendies();
            
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i,data);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et enregistre les données du i-ème incendie.
     * @param i
     */
    private void lireIncendie(int i, DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            data.incendies.add(new incendie(data.carte.getCase(lig, col), intensite));
            verifieLigneTerminee();
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et enregistre les donnees des robots.
     */
    private void lireRobots(DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            data.setRobots(nbRobots);
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i,data);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et enregistre les donnees du i-eme robot.
     * Si un robot d'un nouveau type (non encore instancié sur la carte) est détecté, alors génère et enregistre le graphe associé à ce type de robot.
     * @param i
     */
    private void lireRobot(int i, DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            Case posRobot = data.carte.getCase(lig, col);        
            String type = scanner.next();
            int typeInt = typeToInt(TypeRobot.valueOf(type));
            
            if (data.graphes[typeInt] == null) {
            	graphe g  = new graphe(data.carte.nbLigne, data.carte.nbColonne);
            	g.creerGraphe(data.carte, TypeRobot.valueOf(type));
            	data.setGraphe(typeInt,g);
            }
                      
            robot robot = parseType(type,posRobot,data);           

            data.robots[i] = robot;
            String s = scanner.findInLine("(\\d+)");

            if (s == null) {
            } else {
                int vitesse = Integer.parseInt(s);
                data.robots[i].setVitesse(vitesse);
            }
            verifieLigneTerminee();
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }


    /** 
     * Ignore toute (fin de) ligne commencant par '#' 
     */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
