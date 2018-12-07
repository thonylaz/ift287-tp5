package CentreSportif;

import java.sql.*;

public class GestionParticipant {
    private Connexion cx;
    private TableParticipants participants;
    private TableEquipes equipes;
    private TableLigues ligues;

    /**
     * Création d'une instance
     */
    public GestionParticipant(TableParticipants participants, TableEquipes equipes, TableLigues ligues) throws IFT287Exception {
        this.cx = participants.getConnexion();
        if (participants.getConnexion() != equipes.getConnexion())
            throw new IFT287Exception("Les instances de TableParticipants et de TableEquipes n'utilisent pas la même connexion au serveur");
        this.participants = participants;
        this.equipes = equipes;
        this.ligues = ligues;
    }

    /**
     * Ajout d'un nouveau participant dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void inscrireParticipant(String prenom, String nom, String motDePasse, int matricule)
            throws SQLException, IFT287Exception, Exception {
        try {
            // Vérifie si le particpant existe déja
            if (participants.existe(matricule))
                throw new IFT287Exception("Participant existe déjà: " + matricule);

            // Ajout d'un particpant.
            participants.inscrire(prenom, nom, motDePasse, matricule);

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Suppression d'un partcipant de la base de données.
     */
    public void supprimerParticipant(int matricule) throws SQLException, IFT287Exception, Exception {
        try {
            // Vérifie si le participant existe et qu'il fait partie d'une equipe
            TupleParticipant tupleParticipant = participants.getParticipant(matricule);
            if (tupleParticipant == null)
                throw new IFT287Exception("Participant inexistant: " + matricule);
            if (tupleParticipant.getNomEquipe() != null)
                throw new IFT287Exception("Le participant avec la matricule: " + matricule + " fait partie de l'équipe " + tupleParticipant.getNomEquipe());

            // Suppression d'un participant
            int nb = participants.supprimer(matricule);
            if (nb == 0)
                throw new IFT287Exception("Participant: " + matricule + " inexistant");

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
    /**
     * Permettre à un particpant de s'inscrire dans une équipe.
     */
    public void ajouterJoueur(String nomEquipe, int matricule ) throws SQLException, IFT287Exception, Exception {
        try {
            TupleEquipe tupleEquipe = equipes.getEquipe(nomEquipe);
            if (tupleEquipe == null)
                throw new IFT287Exception("Nom d'équipe inexistant: " + nomEquipe);
            TupleParticipant tupleParticipant = participants.getParticipant(matricule);
            if (tupleParticipant == null)
                throw new IFT287Exception("Participant inexistant: " + matricule);

            if (tupleParticipant.getNomEquipe() != null)
                throw new IFT287Exception("Le participant avec la matricule: " + matricule + " fait partie de l'équipe: " + tupleParticipant.getNomEquipe());

            // ajout d'un joueur
            participants.ajouterEquipe(nomEquipe, matricule);

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
    /**
     * Permettre à un particpant de s'inscrire dans une équipe.
     */
    public void supprimerJoueur(String nomEquipe, int matricule ) throws SQLException, IFT287Exception, Exception {
        try {
            TupleEquipe tupleEquipe = equipes.getEquipe(nomEquipe);
            if (tupleEquipe == null)
                throw new IFT287Exception("Nom d'équipe inexistant: " + nomEquipe);
            TupleParticipant tupleParticipant = participants.getParticipant(matricule);
            if (tupleParticipant == null)
                throw new IFT287Exception("Participant inexistant: " + matricule);

            if (tupleParticipant.getNomEquipe()== null )
                throw new IFT287Exception("Le participant avec la matricule: " + matricule + " ne fait partie d'une équipe");
            
            if (!tupleParticipant.getNomEquipe().equals(nomEquipe))
                throw new IFT287Exception("Le participant avec la matricule: " + matricule + " ne fait partie de l'équipe: " + nomEquipe);
            
            if (tupleParticipant.getMatricule() == tupleEquipe.getMatriculeCapitaine())
                throw new IFT287Exception("le matricule est celui du capitaine: " + matricule);

            participants.supprimerEquipe(nomEquipe, matricule);

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void accepterJoueur(String nomEquipe, int matricule ) throws SQLException, IFT287Exception, Exception {
        try {
            TupleEquipe tupleEquipe = equipes.getEquipe(nomEquipe);
            if (tupleEquipe == null)
                throw new IFT287Exception("Nom d'équipe inexistant: " + nomEquipe);
            TupleParticipant tupleParticipant = participants.getParticipant(matricule);
            if (tupleParticipant == null)
                throw new IFT287Exception("Participant inexistant: " + matricule);
            
            if (!nomEquipe.equals(tupleParticipant.getNomEquipe()))
                throw new IFT287Exception("Il faut ajouter le participant dans l'equipe avant de l'accepter");
            
            if (tupleParticipant.getNomEquipe()== null )
                throw new IFT287Exception("Le participant avec la matricule: " + matricule + " ne fait partie d'une équipe");

            if(tupleParticipant.getEstAccepter() != 0)
                throw new IFT287Exception("Participant: " + matricule + " deja accepte" );
            
            TupleLigue tupleLigue = ligues.getLigue(tupleEquipe.getNomLigue());
            if(tupleLigue == null)
                throw new IFT287Exception("Ligue: " + tupleLigue.getNomLigue() + " non existante");

            if (tupleLigue.getNbJoueurMaxParEquipe() <= participants.getJoueursEquipe(nomEquipe).size())
                throw new IFT287Exception("Equipe complete");
            
            participants.accepterJoueur(nomEquipe, matricule);

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void refuserJoueur(String nomEquipe, int matricule ) throws SQLException, IFT287Exception, Exception {
        try {
            TupleEquipe tupleEquipe = equipes.getEquipe(nomEquipe);
            if (tupleEquipe == null)
                throw new IFT287Exception("Nom d'équipe inexistant: " + nomEquipe);
            TupleParticipant tupleParticipant = participants.getParticipant(matricule);
            if (tupleParticipant == null)
                throw new IFT287Exception("Participant inexistant: " + matricule);
              
            if (tupleParticipant.getNomEquipe()== null )
                throw new IFT287Exception("Le participant avec la matricule: " + matricule + " ne fait partie d'une équipe");
            
            if (!tupleParticipant.getNomEquipe().equals(nomEquipe))
                throw new IFT287Exception("Le participant avec la matricule: " + matricule + " n'est pas inscrit à l'équipe: " + nomEquipe);
            
            if (tupleParticipant.getMatricule() == tupleEquipe.getMatriculeCapitaine())
                throw new IFT287Exception("le matricule est celui du capitaine: " + matricule);

            if(tupleParticipant.getEstAccepter() == 0)
                throw new IFT287Exception("Participant: " + matricule + " deja refuse" );

            participants.refuserJoueur(nomEquipe, matricule);

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
    
    public TableParticipants getParticipant()  throws IFT287Exception {
    	return participants;
    }
}