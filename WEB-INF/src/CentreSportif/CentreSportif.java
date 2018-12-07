// Travail fait par :
//   Antoine Lazure - 14 098 870
//   NomEquipier2 - Matricule

package CentreSportif;

import java.io.*;
import java.util.StringTokenizer;
import java.sql.*;

/**
 * Fichier de base pour le TP2 du cours IFT287
 * <p>
 * <pre>
 *
 * Vincent Ducharme
 * Universite de Sherbrooke
 * Version 1.0 - 16 août 2018
 * IFT287 - Exploitation de BD relationnelles et OO
 *
 * Ce programme permet d'appeler des transactions d'un systeme
 * de gestion utilisant une base de donnees.
 *
 * Paramètres du programme
 * 0- site du serveur SQL ("local" ou "dinf")
 * 1- nom de la BD
 * 2- user id pour etablir une connexion avec le serveur SQL
 * 3- mot de passe pour le user id
 * 4- fichier de transaction [optionnel]
 *           si non spécifié, les transactions sont lues au
 *           clavier (System.in)
 *
 * Pré-condition
 *   - La base de donnees doit exister
 *
 * Post-condition
 *   - Le programme effectue les mises à jour associees à chaque
 *     transaction
 * </pre>
 */
public class CentreSportif {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            System.out.println("Usage: java CentreSportif.CentreSportif <serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }
        GestionCentreSportif gestionCentreSportif = null;


        try {
            // Il est possible que vous ayez à déplacer la connexion ailleurs.
            // N'hésitez pas à le faire!

            BufferedReader reader = ouvrirFichier(args);

            gestionCentreSportif = new GestionCentreSportif(args[0], args[1], args[2], args[3]);

            String transaction = lireTransaction(reader);
            while (!finTransaction(transaction)) {
                executerTransaction(transaction, gestionCentreSportif);
                transaction = lireTransaction(reader);
            }
        } finally {
            if(gestionCentreSportif != null)
                gestionCentreSportif.fermer();
        }
    }

    /**
     * Decodage et traitement d'une transaction
     */
    static void executerTransaction(String transaction, GestionCentreSportif gestionCentreSportif) throws Exception, IFT287Exception {
        try {
            System.out.print(transaction);
            // Decoupage de la transaction en mots
            StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
            if (tokenizer.hasMoreTokens()) {
                String command = tokenizer.nextToken();
                // Vous devez remplacer la chaine "commande1" et "commande2" par
                // les commandes de votre programme. Vous pouvez ajouter autant
                // de else if que necessaire. Vous n'avez pas a traiter la
                // commande "quitter".
                if (command.equals("inscrireParticipant")) {
                    String prenom = readString(tokenizer);
                    String nom = readString(tokenizer);
                    String motDePasse = readString(tokenizer);
                    int matricule = readInt(tokenizer);

                    gestionCentreSportif.getGestionParticipant().inscrireParticipant(prenom, nom, motDePasse, matricule);

                    // Appel de la methode des gestionnaires qui traite la transaction specifique
                }
                else if (command.equals("supprimerParticipant")) {
                    int matricule = readInt(tokenizer);
                    gestionCentreSportif.getGestionParticipant().supprimerParticipant(matricule);
                }
                else if (command.equals("ajouterLigue")) {
                    String nomLigue = readString(tokenizer);
                    int nbJoueurMaxParEquipe = readInt(tokenizer);
                    gestionCentreSportif.getGestionLigue().ajouterLigue(nomLigue, nbJoueurMaxParEquipe);
                }
                else if (command.equals("supprimerLigue")) {
                    String nomLigue = readString(tokenizer);
                    gestionCentreSportif.getGestionLigue().supprimerLigue(nomLigue);
                }
                else if (command.equals("ajouterEquipe")) {
                    String nomLigue = readString(tokenizer);
                    String nomEquipe = readString(tokenizer);
                    int matricule = readInt(tokenizer);
                    gestionCentreSportif.getGestionEquipe().ajouterEquipe(nomLigue, nomEquipe, matricule);
                }
                else if (command.equals("ajouterJoueur")) {
                    String nomEquipe = readString(tokenizer);
                    int matricule = readInt(tokenizer);
                    gestionCentreSportif.getGestionParticipant().ajouterJoueur(nomEquipe, matricule);
                }
                else if (command.equals("supprimerJoueur")) {
                    String nomEquipe = readString(tokenizer);
                    int matricule = readInt(tokenizer);
                    gestionCentreSportif.getGestionParticipant().supprimerJoueur(nomEquipe, matricule);
                }
                else if (command.equals("accepterJoueur")) {
                    String nomEquipe = readString(tokenizer);
                    int matricule = readInt(tokenizer);
                    gestionCentreSportif.getGestionParticipant().accepterJoueur(nomEquipe, matricule);
                }
                else if (command.equals("refuserJoueur")) {
                    String nomEquipe = readString(tokenizer);
                    int matricule = readInt(tokenizer);
                    gestionCentreSportif.getGestionParticipant().refuserJoueur(nomEquipe, matricule);
                }
                else if (command.equals("afficherEquipes")) {
                    //gestionCentreSportif.getGestionEquipe().afficherEquipes();
                }
                else if (command.equals("afficherEquipe")) {
                    String nomEquipe = readString(tokenizer);
                    //gestionCentreSportif.getGestionEquipe().afficherEquipe(nomEquipe);
                }
                else if (command.equals("afficherLigue")) {
                    String nomLigue = readString(tokenizer);
                    //gestionCentreSportif.getGestionLigue().afficherLigue(nomLigue);
                }
                else if (command.equals("ajouterResultat")) {
                    String nomEquipeA = readString(tokenizer);
                    int scoreEquipeA = readInt(tokenizer);
                    String nomEquipeB = readString(tokenizer);
                    int scoreEquipeB = readInt(tokenizer);
                    gestionCentreSportif.getGestionResultat().ajouterResultat(nomEquipeA, scoreEquipeA, nomEquipeB, scoreEquipeB);
                }
                else if (command.equals("quitter")) {
                    return;
                }
                else {
                    System.out.println(" : Transaction non reconnue");
                }
            }
        } catch (Exception e) {
            System.out.println(" " + e.toString());
            // Ce rollback est ici seulement pour vous aider et éviter des problèmes lors de la correction
            // automatique. En théorie, il ne sert à rien et ne devrait pas apparaître ici dans un programme
            // fini et fonctionnel sans bogues.

        }
    }


    // ****************************************************************
    // *   Les methodes suivantes n'ont pas besoin d'etre modifiees   *
    // ****************************************************************

    /**
     * Ouvre le fichier de transaction, ou lit à partir de System.in
     */
    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException {
        if (args.length < 5)
            // Lecture au clavier
            return new BufferedReader(new InputStreamReader(System.in));
        else
            // Lecture dans le fichier passe en parametre
            return new BufferedReader(new InputStreamReader(new FileInputStream(args[4])));
    }

    /**
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    /**
     * Verifie si la fin du traitement des transactions est atteinte.
     */
    static boolean finTransaction(String transaction) {
        // fin de fichier atteinte
        return (transaction == null || transaction.equals("quitter"));
    }

    /**
     * Lecture d'une chaine de caracteres de la transaction entree a l'ecran
     */
    static String readString(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements())
            return tokenizer.nextToken();
        else
            throw new Exception("Autre parametre attendu");
    }

    /**
     * Lecture d'un int java de la transaction entree a l'ecran
     */
    static int readInt(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                return Integer.valueOf(token).intValue();
            } catch (NumberFormatException e) {
                throw new Exception("Nombre attendu a la place de \"" + token + "\"");
            }
        } else
            throw new Exception("Autre parametre attendu");
    }

    static Date readDate(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                return Date.valueOf(token);
            } catch (IllegalArgumentException e) {
                throw new Exception("Date dans un format invalide - \"" + token + "\"");
            }
        } else
            throw new Exception("Autre parametre attendu");
    }

}

