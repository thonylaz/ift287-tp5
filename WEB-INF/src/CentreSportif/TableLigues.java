package CentreSportif;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TableLigues {
    private Connexion cx;

    private PreparedStatement stmtExiste;
    private PreparedStatement stmtLigues;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;

    public TableLigues(Connexion cx) throws SQLException {
        this.cx = cx;

        stmtExiste = cx.getConnection().prepareStatement(
                "select nomLigue, nbJoueurMaxParEquipe from ligues where nomLigue = ?");
        stmtInsert = cx.getConnection()
                .prepareStatement("insert into ligues (nomLigue, nbJoueurMaxParEquipe) "
                        + "values (?,?)");
        stmtDelete = cx.getConnection().prepareStatement("delete from ligues where nomLigue = ?");
        stmtLigues = cx.getConnection().prepareStatement("select nomLigue from ligues");
    }

    public boolean existe(String nomLigue) throws SQLException {
        stmtExiste.setString(1, nomLigue);
        ResultSet rs = stmtExiste.executeQuery();
        boolean equipeExiste = rs.next();
        rs.close();
        return equipeExiste;
    }

    public int supprimer(String nomLigue) throws SQLException {
        stmtDelete.setString(1, nomLigue);
        return stmtDelete.executeUpdate();
    }

    public void ajouter(String nomLigue,int nbJoueurMaxParEquipe) throws SQLException
    {
        stmtInsert.setString(1, nomLigue);
        stmtInsert.setInt(2, nbJoueurMaxParEquipe);
        stmtInsert.executeUpdate();
    }

    //Lecture d'une ligue

    public TupleLigue getLigue(String nomLigue) throws SQLException
    {
        stmtExiste.setString(1, nomLigue);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleLigue tupleLigue = new TupleLigue();
            tupleLigue.setNomLigue(nomLigue);
            tupleLigue.setNbJoueurMaxParEquipe(rset.getInt(2));

            rset.close();
            return tupleLigue;
        }
        else
        {
            return null;
        }
    }

    public Connexion getConnexion() {
        return cx;
    }

	public ArrayList<String> getLigues() throws SQLException {
		ArrayList<String> ligues = new ArrayList<String>();
		ResultSet rset = stmtLigues.executeQuery();
		while(rset.next()) {
			ligues.add(rset.getString(1));
		}
		rset.close();
		return ligues;
	}
}
