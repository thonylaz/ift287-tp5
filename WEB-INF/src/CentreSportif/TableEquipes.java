package CentreSportif;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableEquipes {
    private Connexion cx;

    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtEquipes;
    private PreparedStatement stmtListeEquipesTriesLigue;    //  Liste des équipes trié par lignes
    private PreparedStatement stmtListeEquipesLigue;        // Liste des équipes d'une ligue

    public TableEquipes(Connexion cx) throws SQLException {
        this.cx = cx;

        stmtExiste = cx.getConnection().prepareStatement(
                "select nomLigue, nomEquipe, matriculeCapitaine from equipes where nomEquipe = ?");
        stmtInsert = cx.getConnection()
                .prepareStatement("insert into equipes (nomLigue, nomEquipe, matriculeCapitaine) "
                        + "values (?,?,?)");
        stmtEquipes = cx.getConnection().prepareStatement("select nomEquipe from equipes");

        stmtListeEquipesTriesLigue = cx.getConnection().prepareStatement(
                "select nomLigue, nomEquipe, matriculeCapitaine from equipes order by nomLigue");

        stmtListeEquipesLigue = cx.getConnection().prepareStatement(
                "select nomLigue, nomEquipe, matriculeCapitaine from equipes where nomLigue = ?");

    }

    public boolean existe(String nomEquipe) throws SQLException {
        stmtExiste.setString(1, nomEquipe);
        ResultSet rs = stmtExiste.executeQuery();
        boolean equipeExiste = rs.next();
        rs.close();
        return equipeExiste;
    }

    public void ajouter(String nomLigue, String nomEquipe, int matriculeCapitaine) throws SQLException {
        stmtInsert.setString(1, nomLigue);
        stmtInsert.setString(2, nomEquipe);
        stmtInsert.setInt(3, matriculeCapitaine);
        stmtInsert.executeUpdate();
    }

    //Lecture d'une equipe

    public TupleEquipe getEquipe(String nomEquipe) throws SQLException {
        stmtExiste.setString(1, nomEquipe);
        ResultSet rset = stmtExiste.executeQuery();

        if (rset.next()) {
            TupleEquipe tupleEquipe = new TupleEquipe();
            tupleEquipe.setNomLigue(rset.getString(1));
            tupleEquipe.setNomEquipe(nomEquipe);
            tupleEquipe.setMatriculeCapitaine(rset.getInt(3));

            rset.close();
            return tupleEquipe;
        } else {
            return null;
        }
    }

    public ArrayList<TupleEquipe> getEquipes(String nomLigue) throws SQLException {
        ArrayList<TupleEquipe> equipes = new ArrayList<>();
        stmtListeEquipesLigue.setString(1, nomLigue);
        ResultSet rset = stmtListeEquipesLigue.executeQuery();

        while(rset.next()) {
            TupleEquipe tupleEquipe = new TupleEquipe();
            tupleEquipe.setNomLigue(rset.getString(1));
            tupleEquipe.setNomEquipe(rset.getString(2));
            tupleEquipe.setMatriculeCapitaine(rset.getInt(3));
            equipes.add(tupleEquipe);
        }
        rset.close();
        return equipes;
    }

    public ArrayList<TupleEquipe> getEquipes() throws SQLException {
        ArrayList<TupleEquipe> equipes = new ArrayList<>();
        ResultSet rset = stmtListeEquipesTriesLigue.executeQuery();

        while(rset.next()) {
            TupleEquipe tupleEquipe = new TupleEquipe();
            tupleEquipe.setNomLigue(rset.getString(1));
            tupleEquipe.setNomEquipe(rset.getString(2));
            tupleEquipe.setMatriculeCapitaine(rset.getInt(3));
            equipes.add(tupleEquipe);
        }
        rset.close();
        return equipes;
    }
    
    public ArrayList<String> getNomEquipes() throws SQLException {
        ArrayList<String> equipes = new ArrayList<>();
        ResultSet rset = stmtEquipes.executeQuery();

        while(rset.next()) {
            equipes.add(rset.getString(1));
        }
        rset.close();
        return equipes;
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion() {
        return cx;
    }
}
