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
    private PreparedStatement stmtUpdateNomEquipeNull;
    private PreparedStatement stmtUpdateNomEquipe;
    private PreparedStatement stmtUpdateAccepte;
    private PreparedStatement stmtUpdateRefuser;
    private PreparedStatement stmtgetJoueurEquipe;


    public TableParticipants(Connexion cx) throws SQLException {
        this.cx = cx;

        stmtExiste = cx.getConnection().prepareStatement(
                "select matricule, nom, prenom, motDePasse, nomEquipe, estAccepte from participants where matricule = ?");
        stmtInsert = cx.getConnection()
                .prepareStatement("insert into participants (matricule, nom, prenom, motDePasse, estAccepte, nomEquipe) "
                        + "values (?,?,?,?,0, null)");
        stmtDelete = cx.getConnection().prepareStatement("delete from participants where matricule = ?");

        stmtUpdateNomEquipe = cx.getConnection()
                .prepareStatement("update participants set nomEquipe = ? where matricule = ?");

        stmtUpdateNomEquipeNull = cx.getConnection()
                .prepareStatement("update participants set nomEquipe = ? where matricule = ?");

        stmtUpdateAccepte = cx.getConnection()
                .prepareStatement("update participants set estAccepte = 1 where matricule = ?");

        stmtUpdateRefuser = cx.getConnection()
                .prepareStatement("update participants set estAccepte = 0 where matricule = ?");

        stmtgetJoueurEquipe = cx.getConnection().prepareStatement(
                "select matricule, nom, prenom, motDePasse, nomEquipe from participants where nomEquipe = ? and estAccepte = 1");
        
        stmtParticpantMatricule = cx.getConnection().prepareStatement("select matricule from participants");
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
        stmtUpdateNomEquipe.setString(1, nomEquipe);
        stmtUpdateNomEquipe.setInt(2, matricule);

        stmtUpdateNomEquipe.executeUpdate();
    }

    public void supprimerEquipe(String nomEquipe,int matricule) throws SQLException {
        stmtUpdateNomEquipeNull.setNull(1, matricule, nomEquipe);
        stmtUpdateNomEquipeNull.setInt(2, matricule);

        stmtUpdateNomEquipeNull.executeUpdate();
    }

    public void accepterJoueur(String nomEquipe,int matricule) throws SQLException{
        //stmtUpdateAccepte.setString(1,nomEquipe);
        stmtUpdateAccepte.setInt(1, matricule);

        stmtUpdateAccepte.executeUpdate();
    }

    public void refuserJoueur(String nomEquipe,int matricule) throws SQLException{
        //stmtUpdateRefuser.setString(1,nomEquipe);
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

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion() {
        return cx;
    }
}
