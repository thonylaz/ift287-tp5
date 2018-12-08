package CentreSportif;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableParticipants {
    private Connexion cx;

    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtParticpantMatricule;
    private PreparedStatement stmtUpdateSupprimerJoueur;
    private PreparedStatement stmtUpdateAjouterJoueur;
    private PreparedStatement stmtUpdateAccepterJoueur;
    private PreparedStatement stmtUpdateRefuser;
    private PreparedStatement stmtgetJoueurEquipe;
    private PreparedStatement stmtParticipantEquipe;
    private PreparedStatement stmtParticipantInscrit;


    public TableParticipants(Connexion cx) throws SQLException {
        this.cx = cx;

        stmtExiste = cx.getConnection().prepareStatement(
                "select matricule, nom, prenom, motDePasse, nomEquipe, estAccepte from participants where matricule = ?");
        stmtInsert = cx.getConnection()
                .prepareStatement("insert into participants (matricule, nom, prenom, motDePasse, estAccepte, nomEquipe) "
                        + "values (?,?,?,?,0, null)");
        stmtDelete = cx.getConnection().prepareStatement("delete from participants where matricule = ?");

        stmtUpdateAjouterJoueur = cx.getConnection()
                .prepareStatement("update participants set nomEquipe = ? where matricule = ?");

        stmtUpdateSupprimerJoueur = cx.getConnection()
                .prepareStatement("update participants set estAccepte = 0 where matricule = ?");

        stmtUpdateAccepterJoueur = cx.getConnection()
                .prepareStatement("update participants set estAccepte = 1 where matricule = ?");

        stmtUpdateRefuser = cx.getConnection()
                .prepareStatement("update participants set estAccepte = 0, nomEquipe = null  where matricule = ?");

        stmtgetJoueurEquipe = cx.getConnection().prepareStatement(
                "select matricule, nom, prenom, motDePasse, nomEquipe from participants where nomEquipe = ? and estAccepte = 1");
    
 stmtParticpantMatricule = cx.getConnection().prepareStatement("select matricule from participants");
        
        stmtParticipantEquipe = cx.getConnection().prepareStatement("select matricule from participants where nomEquipe is not null and estAccepte = 1");
        
        stmtParticipantInscrit = cx.getConnection().prepareStatement("select matricule from participants where nomEquipe is not null and estAccepte = 0");
    }

   public boolean existe(int matricule) throws SQLException {
        stmtExiste.setInt(1, matricule);
        ResultSet rs = stmtExiste.executeQuery();
        boolean participantExiste = rs.next();
        rs.close();

        return participantExiste;
    }

    public int supprimer(int matricule) throws SQLException {
        stmtDelete.setInt(1, matricule);
        return stmtDelete.executeUpdate();
    }


    public void inscrire(String prenom, String nom, String motDePasse, int matricule) throws SQLException {
        stmtInsert.setInt(1, matricule);
        stmtInsert.setString(2, prenom);
        stmtInsert.setString(3, nom);
        stmtInsert.setString(4, motDePasse);

        stmtInsert.executeUpdate();
    }

    public void ajouterEquipe(String nomEquipe, int matricule)throws SQLException{
        stmtUpdateAjouterJoueur.setString(1, nomEquipe);
        stmtUpdateAjouterJoueur.setInt(2, matricule);

        stmtUpdateAjouterJoueur.executeUpdate();
    }

    public void supprimerEquipe(String nomEquipe,int matricule) throws SQLException {
        stmtUpdateSupprimerJoueur.setInt(1, matricule);

        stmtUpdateSupprimerJoueur.executeUpdate();
    }

    public void accepterJoueur(String nomEquipe,int matricule) throws SQLException{
        stmtUpdateAccepterJoueur.setInt(1, matricule);

        stmtUpdateAccepterJoueur.executeUpdate();
    }

    public void refuserJoueur(String nomEquipe,int matricule) throws SQLException{
        stmtUpdateRefuser.setInt(1, matricule);

        stmtUpdateRefuser.executeUpdate();
    }

    //Lecture d'un participant

    public TupleParticipant getParticipant(int matricule) throws SQLException {
        stmtExiste.setInt(1, matricule);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next()) {
            TupleParticipant tupleParticipant = new TupleParticipant();
            tupleParticipant.setMatricule(matricule);
            tupleParticipant.setNom(rset.getString(2));
            tupleParticipant.setPrenom(rset.getString(3));
            tupleParticipant.setMotDePasse(rset.getString(4));
            tupleParticipant.setNomEquipe(rset.getString(5));

            rset.close();
            return tupleParticipant;
        } else {
            return null;
        }
    }

    public ArrayList<TupleParticipant> getJoueursEquipe(String nomEquipe) throws SQLException {
        ArrayList<TupleParticipant> participants = new ArrayList<>();
        stmtgetJoueurEquipe.setString(1, nomEquipe);

        ResultSet rset = stmtgetJoueurEquipe.executeQuery();
        while(rset.next()) {
            TupleParticipant tupleParticipant = new TupleParticipant();
            tupleParticipant.setMatricule(rset.getInt(1));
            tupleParticipant.setNom(rset.getString(2));
            tupleParticipant.setPrenom(rset.getString(3));
            tupleParticipant.setMotDePasse(rset.getString(4));
            tupleParticipant.setNomEquipe(rset.getString(5));

            participants.add(tupleParticipant);
        }
        rset.close();
        return participants;
    }
    
    public ArrayList<String> getParticipantsMatricule() throws SQLException {
        ArrayList<String> listes = new ArrayList<>();
        ResultSet rset = stmtParticpantMatricule.executeQuery();
        while(rset.next()) {
            listes.add(Integer.toString(rset.getInt(1)));
        }
        rset.close();
        return listes;
    } 
    
    public ArrayList<String> getParticipantsDansEquipe() throws SQLException {
    	 ArrayList<String> listes = new ArrayList<>();
         ResultSet rset = stmtParticipantEquipe.executeQuery();
         while(rset.next()) {
             listes.add(Integer.toString(rset.getInt(1)));
         }
         rset.close();
         return listes;
    }
    
    public ArrayList<String> getParticipantsInscrit() throws SQLException {
    	ArrayList<String> listes = new ArrayList<>();
        ResultSet rset = stmtParticipantInscrit.executeQuery();
        while(rset.next()) {
            listes.add(Integer.toString(rset.getInt(1)));
        }
        rset.close();
        return listes;
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion() {
        return cx;
    }
}
