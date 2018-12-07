package CentreSportif;

import java.sql.*;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class GestionEquipe {
    private TableLigues ligues;
    private TableEquipes equipes;
    private TableParticipants participants;
    private TableResultats resultats;
    private Connexion cx;

    public GestionEquipe(TableLigues ligues, TableEquipes equipes, TableParticipants participants, TableResultats resultats) throws IFT287Exception {
        this.cx = equipes.getConnexion();
        if (equipes.getConnexion() != participants.getConnexion())
            throw new IFT287Exception("Les instances de TableEquipes et de TableParticipants n'utilisent pas la même connexion au serveur");
        if (equipes.getConnexion() != resultats.getConnexion())
            throw new IFT287Exception("Les instances de TableEquipes et de TableResultats n'utilisent pas la même connexion au serveur");
        this.resultats = resultats;
        this.participants = participants;
        this.equipes = equipes;
        this.ligues = ligues;
    }

    public void afficherEquipe(String nomEquipe, HttpServletRequest request) throws SQLException, IFT287Exception {
        try {
            TupleEquipe tupleEquipe = equipes.getEquipe(nomEquipe);

            if (tupleEquipe == null)
                throw new IFT287Exception("Nom d'équipe inexistant: " + nomEquipe);

            if(!participants.existe(tupleEquipe.getMatriculeCapitaine()))
                throw new IFT287Exception("Matricule inexistante: " + tupleEquipe.getMatriculeCapitaine());
            
            TupleParticipant capitaine = participants.getParticipant(tupleEquipe.getMatriculeCapitaine());
            request.setAttribute("resultatAfficherEquipe_Equipe", tupleEquipe);
            request.setAttribute("resultatAfficherEquipe_Capitaine", capitaine);
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void afficherEquipes(HttpServletRequest request) throws SQLException {
		ArrayList<TupleEquipe> listEquipes = equipes.getEquipes();
        request.setAttribute("resultatAfficherEquipes", listEquipes);
    }

    public void ajouterEquipe(String nomLigue, String nomEquipe, int matriculeCapitaine) throws IFT287Exception, SQLException {
        try {
            // VÃ©rifie si l'equipe existe dÃ©ja
            if (equipes.existe(nomEquipe))
                throw new IFT287Exception("Equipe existe déjà : " + nomEquipe);

            // VÃ©rifie si la ligue existe
            if (!ligues.existe(nomLigue))
                throw new IFT287Exception("Ligue inexistante: " + nomLigue);

            // Verifie si le capitaine existe
            if (!participants.existe(matriculeCapitaine))
                throw new IFT287Exception("Participant inexistant: " + matriculeCapitaine);
            TupleParticipant participant = participants.getParticipant(matriculeCapitaine);
            if(participant.getNomEquipe() != null)
            	throw new IFT287Exception("Participant déjà dans une équipe: " + matriculeCapitaine);
            // Ajout d'une equipe.
            equipes.ajouter(nomLigue, nomEquipe, matriculeCapitaine);
            participants.ajouterEquipe(nomEquipe,matriculeCapitaine);
            participants.accepterJoueur(nomEquipe,matriculeCapitaine);

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
    
    public TableEquipes getEquipe() {
    	return equipes;
    }
}