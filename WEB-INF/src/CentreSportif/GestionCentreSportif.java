package CentreSportif;

import java.sql.SQLException;

public class GestionCentreSportif {
    private Connexion cx;
    private TableParticipants participants;
    private TableEquipes equipes;
    private TableLigues ligues;
    private TableResultats resultats;
    private GestionParticipant gestionParticipant;
    private GestionEquipe gestionEquipe;
    private GestionLigue gestionLigue;
    private GestionResultat gestionResultat;

    /**
     * Ouvre une connexion avec la BD relationnelle et alloue les gestionnaires
     * de transactions et de tables.
     *
     * @param serveur SQL
     * @param bd nom de la bade de données
     * @param user user id pour établir une connexion avec le serveur SQL
     * @param password mot de passe pour le user id
     */
    public GestionCentreSportif(String serveur, String bd, String user, String password)
            throws IFT287Exception, SQLException {
        cx = new Connexion(serveur, bd, user, password);
        participants = new TableParticipants(cx);
        equipes = new TableEquipes(cx);
        ligues = new TableLigues(cx);
        resultats = new TableResultats(cx);

        setGestionParticipant(new GestionParticipant(participants, equipes, ligues));
        setGestionEquipe(new GestionEquipe(ligues, equipes, participants, resultats));
        setGestionLigue(new GestionLigue(ligues, equipes, resultats));
        setGestionResultat(new GestionResultat(resultats, equipes));

    }

    public void fermer() throws SQLException
    {
        // Fermeture de la connexion
        cx.fermer();
    }

    public GestionParticipant getGestionParticipant() {
        return gestionParticipant;
    }

    public void setGestionParticipant(GestionParticipant gestionParticipant) {
        this.gestionParticipant = gestionParticipant;
    }

    public GestionEquipe getGestionEquipe() {
        return gestionEquipe;
    }

    public void setGestionEquipe(GestionEquipe gestionEquipe) {
        this.gestionEquipe = gestionEquipe;
    }

    public GestionLigue getGestionLigue() {
        return gestionLigue;
    }

    public void setGestionLigue(GestionLigue gestionLigue) {
        this.gestionLigue = gestionLigue;
    }

    public GestionResultat getGestionResultat() {
        return gestionResultat;
    }

    public void setGestionResultat(GestionResultat gestionResultat) {
        this.gestionResultat = gestionResultat;
    }
}
