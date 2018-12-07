package CentreSportif;

import java.sql.*;
import java.util.ArrayList;

public class GestionResultat {
    private TableResultats resultats;
    private TableEquipes equipes;
    private Connexion cx;

    //Creation d'une instance

    public GestionResultat(TableResultats resultats, TableEquipes equipes) throws IFT287Exception {
        this.cx = resultats.getConnexion();
        if (resultats.getConnexion() != equipes.getConnexion())
            throw new IFT287Exception("Les instances de resultat et de equipe n'utilisent pas la même connexion au serveur");
        this.resultats = resultats;
        this.equipes = equipes;
    }

    /**
     * Ajout d'un nouveau resultat dans la base de donnÃ©es. S'il existe dÃ©jÃ , une
     * exception est levÃ©e.
     */
    public void ajouterResultat(String nomEquipeA, int scoreEquipeA, String nomEquipeB, int scoreEquipeB)
            throws SQLException, IFT287Exception, Exception {
        try {
            // Verifie si les equipes existent
            TupleEquipe tupleEquipeA = equipes.getEquipe(nomEquipeA);
            if(tupleEquipeA == null)
                throw new IFT287Exception("Nom d'équipe A : " + nomEquipeA + "inexistant");
            TupleEquipe tupleEquipeB = equipes.getEquipe(nomEquipeB);
            if(tupleEquipeB == null)
                throw new IFT287Exception("Nom d'équipe B : " + nomEquipeB + "inexistant");

            if(!tupleEquipeA.getNomLigue().equals(tupleEquipeB.getNomLigue()))
                throw new IFT287Exception("Les deux equipes ne font pas partie de la même ligue.");
            
            if(scoreEquipeA < 0)
                throw new IFT287Exception("Le score de l'équipe A doit être positif.");

            if(scoreEquipeB < 0)
                throw new IFT287Exception("Le score de l'équipe B doit être positif.");
            
            if(nomEquipeA.equals(nomEquipeB))
            	throw new IFT287Exception("L'équipe " + nomEquipeA + " ne peux pas jouer contre elle-même");
            // Ajout d'un resultat dans la table des livres
            resultats.ajouterResultat(nomEquipeA, scoreEquipeA, nomEquipeB, scoreEquipeB);

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
    
    public TableResultats getResultat() {
    	return  resultats;
    }


}
