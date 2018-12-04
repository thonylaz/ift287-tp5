package CentreSportif;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableResultats {
    private Connexion cx;

    private PreparedStatement stmtExiste;
    private PreparedStatement stmtResultatEquipe;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;

    public TableResultats() {
    }

    public TableResultats(Connexion cx) throws SQLException {
        this.cx = cx;

        stmtExiste = cx.getConnection().prepareStatement(
                "select nomEquipeA, nomEquipeB, scoreEquipeA, scoreEquipeB "
                        + "from resultats where nomEquipeA = ? and nomEquipeB = ?");
        stmtResultatEquipe = cx.getConnection()
                .prepareStatement("select idResultat, nomEquipeA, scoreEquipeA, nomEquipeB, scoreEquipeB "
                        + "from resultats where nomEquipeA = ? OR nomEquipeB = ? ");
        stmtInsert = cx.getConnection()
                .prepareStatement("insert into resultats (idResultat, nomEquipeA, scoreEquipeA, nomEquipeB, scoreEquipeB) "
                        + "values (DEFAULT,?,?,?,?)");
        stmtDelete = cx.getConnection().prepareStatement("delete from resultats where dateResultat = ?");

    }

    public int supprimer(int idResultat) throws SQLException {
        stmtExiste.setInt(1, idResultat);
        return stmtDelete.executeUpdate();
    }

    public void ajouterResultat(String nomEquipeA, int scoreEquipeA, String nomEquipeB, int scoreEquipeB) throws SQLException {
        stmtInsert.setString(1, nomEquipeA);
        stmtInsert.setInt(2, scoreEquipeA);
        stmtInsert.setString(3, nomEquipeB);
        stmtInsert.setInt(4, scoreEquipeB);
        stmtInsert.executeUpdate();
    }

    //Lecture d'un resultat

    public TupleResultat getResultat(int idResultat) throws SQLException {
        stmtExiste.setInt(1, idResultat);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next()) {
            TupleResultat tupleResultat = new TupleResultat();
            tupleResultat.setIdResultat(idResultat);
            tupleResultat.setNomEquipeA(rset.getString(1));
            tupleResultat.setScoreEquipeA(rset.getInt(2));
            tupleResultat.setNomEquipeA(rset.getString(3));
            tupleResultat.setScoreEquipeB(rset.getInt(4));

            rset.close();
            return tupleResultat;
        } else {
            return null;
        }
    }

    public ArrayList<TupleResultat> getResultats(String nomEquipe) throws SQLException {
        ArrayList<TupleResultat> resultats = new ArrayList<>();
        stmtResultatEquipe.setString(1, nomEquipe);
        stmtResultatEquipe.setString(2, nomEquipe);
        ResultSet rset = stmtResultatEquipe.executeQuery();

        while(rset.next()) {
            TupleResultat tupleResultat = new TupleResultat();
            tupleResultat.setIdResultat(rset.getInt(1));
            tupleResultat.setNomEquipeA(rset.getString(2));
            tupleResultat.setScoreEquipeA(rset.getInt(3));
            tupleResultat.setNomEquipeB(rset.getString(4));
            tupleResultat.setScoreEquipeB(rset.getInt(5));

            resultats.add(tupleResultat);
        }
        rset.close();
        return resultats;
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    public int nbVictoires(String nomEquipe, ArrayList<TupleResultat> listResultats) {
        int cpt=0;

        for(TupleResultat resultat: listResultats) {
            if(resultat.getNomEquipeA().equals(nomEquipe) && resultat.getScoreEquipeA() > resultat.getScoreEquipeB())
                cpt++;

            if(resultat.getNomEquipeB().equals(nomEquipe) && resultat.getScoreEquipeB() > resultat.getScoreEquipeA())
                cpt++;
        }

        return cpt;
    }

    public int nbDefaites(String nomEquipe, ArrayList<TupleResultat> listResultats) {
        int cpt=0;

        for(TupleResultat resultat: listResultats) {
            if(resultat.getNomEquipeA().equals(nomEquipe) && resultat.getScoreEquipeA() < resultat.getScoreEquipeB())
                cpt++;

            if(resultat.getNomEquipeB().equals(nomEquipe) && resultat.getScoreEquipeB() < resultat.getScoreEquipeA())
                cpt++;
        }

        return cpt;
    }

    public int nbPartiesNulles(String nomEquipe, ArrayList<TupleResultat> listResultats) {
        int cpt=0;

        for(TupleResultat resultat: listResultats)
            if((resultat.getNomEquipeA().equals(nomEquipe) || resultat.getNomEquipeB().equals(nomEquipe)) &&
                    resultat.getScoreEquipeA() == resultat.getScoreEquipeB())
                cpt++;

        return cpt;
    }

}
