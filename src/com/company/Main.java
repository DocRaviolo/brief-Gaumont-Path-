package com.company;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //Déclaration des données utilisées
        int nbRangs = 12;    //nombre de rangées dans le cinéma
        int nbSièges = 15;  //nombre de sièges par rangées
        int nbPlaces = 0;   //nombre de places demandées par le client
        int rang = 0;
        int siège = 0;
        int numTestR=0;     //numéro "fictif" utilisé uniquement pour interdire la saisie d'un rang hors périmètre
        int numTestS = 0;   //numéro "fictif" utilisé uniquement pour interdire la saisie d'un siège hors périmètre
        Boolean résa = false;
        Boolean confirm = false;
        String[][] salle = new String[nbRangs][nbSièges];
        ArrayList<Integer> placesDisponibles = new ArrayList<Integer>();
        ArrayList<Integer> placesRéservées = new ArrayList<Integer>();

        //Boucle pour créer le plan de la salle vide
        for (int i = salle.length - 1; i >= 0; i--) {
            //System.out.printf("R " + i + "| ");
            for (int j = 0; j < salle[i].length; j++) {
                salle[i][j] = "[ - ]";
                //    System.out.print(salle[i][j]);
            }
            //System.out.println();
        }

        //Petit add-on pour tester des cas de réservations déjà effectuées
        salle[2][3] = "[ X ]";
        salle[2][4] = "[ X ]";
        salle[2][5] = "[ X ]";
        salle[4][11] = "[ X ]";
        salle[6][6] = "[ X ]";
        salle[3][7] = "[ X ]";
        salle[3][8] = "[ X ]";
        salle[3][9] = "[ X ]";
        salle[3][2] = "[ X ]";
        salle[3][4] = "[ X ]";
        salle[3][12] = "[ X ]";
        salle[0][1] = "[ X ]";

        //Accueil
        System.out.println();
        System.out.println("Bonjour et bienvenue sur notre site de réservation en ligne.\nDésirez-vous effectuer une réservation pour Retour vers le Futur 4 ? (true or false)");
        résa = sc.nextBoolean();

        //si le client confirme vouloir effectuer une réservation, on démarre la boucle Do While...
        if (résa) {
            //celle-ci sera rejouée tant que le client ne confirmera pas sa réservation ou tant que le siège choisi sera déjà occupé
            do {
                System.out.println("Combien de places désirez-vous réserver ?");
                nbPlaces = sc.nextInt();
                System.out.println("Voici la disposition de la salle et les places disponibles :");
                System.out.println();
                printPlan(salle);
                System.out.println();

                //boucle Do While qui se joue tant que le rang choisi ne dispose pas d'assez de sièges libres
                // par rapport à la demande
                do {
                    do {
                        System.out.println("Veuillez indiquer la rangée choisie ?");
                        numTestR = sc.nextInt();
                        if (numTestR >= 0 && numTestR < nbRangs) {
                            rang = numTestR;
                        } else {
                            System.out.println("Numéro de rangée non valide. Veuillez indiquer une rangée comprise entre 0 et "+(nbRangs-1)+".");
                        }
                    } while ( numTestR < 0 || numTestR >= nbRangs);

                    //boucle permettant d'indiquer le nombre de places disponibles sur la rangée choisie par le client
                    for (int i = 0; i < nbSièges; i++) {
                        if (salle[rang][i] == "[ - ]") {
                            placesDisponibles.add(i);
                        }
                    }
                    if (placesDisponibles.size() < nbPlaces) {
                        System.out.println("Il n'y a pas assez de sièges disponibles sur cette rangée.");
                    } else {
                        System.out.println("Les sièges n° " + placesDisponibles + " sont disponibles sur la rangée " + rang + ".");
                    }
                } while (placesDisponibles.size() < nbPlaces);


                //début de la boucle pour le choix des sièges
                for (int i = 1; i <= nbPlaces; i++) {
                    do {
                        System.out.println("Siège choisi pour le spectateur n°" + i + " ?");
                        numTestS = sc.nextInt();
                        if (numTestS >= 0 && numTestS < nbSièges) {
                            siège = numTestS;
                            if (salle[rang][siège] == "[ X ]") {
                                System.out.println("Attention, ce siège n'est pas disponible");
                            } else {
                                placesRéservées.add(siège);
                            }
                        } else {
                            System.out.println("Numéro de siège non valide. Veuillez saisir un siège compris entre 0 et "+(nbSièges-1)+".");
                        }
                    } while (salle[rang][siège] == "[ X ]" || numTestS < 0 || numTestS >= nbSièges);
                }
                System.out.println("Vous avez choisi les sièges " + placesRéservées + " sur la rangée " + rang + ".\nConfirmez-vous la réservation ? (true ou false)");
                confirm = sc.nextBoolean();
            }
            while (confirm == false);
            System.out.println("Votre réservation est validée. Les cinémas Gaumont-Pathé vous souhaitent une bonne séance !");

            //Mise à jour du plan de salle après confirmation du client
            for (int i = 0; i < placesRéservées.size(); i++) {
                salle[rang][placesRéservées.get(i)] = "[ X ]";
            }

        } else {
            System.out.println("Merci de votre visite et à bientôt.");
        }

        //Print de la salle après confirmation et MAJ pour vérification
        //printPlan(salle);
        sc.close();
    }

    //fonction permettant de printer le plan de salle
    private static void printPlan(String[][] salle) {
        for (int i = salle.length - 1; i >= 0; i--) {
            System.out.printf("R " + i + "| ");
            for (int j = 0; j < salle[i].length; j++) {
                System.out.print(salle[i][j]);
            }
            System.out.println();
        }
        System.out.println("                         __________________________________________     ");
        System.out.println("                        |                 ECRAN                    |    ");
        System.out.println("                         ------------------------------------------     ");
    }
}