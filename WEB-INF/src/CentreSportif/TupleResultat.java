package CentreSportif;

import java.sql.*;

public class TupleResultat {

    private int idResultat;
    private String nomEquipeA;
    private String nomEquipeB;
    private int scoreEquipeA;
    private int scoreEquipeB;

    public TupleResultat() {
    }

    public TupleResultat(int idResultat, String nomEquipeA, String nomEquipeB, int scoreEquipeA, int scoreEquipeB) {
        this.setIdResultat(idResultat);
        this.setNomEquipeA(nomEquipeA);
        this.setNomEquipeB(nomEquipeB);
        this.setScoreEquipeA(scoreEquipeA);
        this.setScoreEquipeB(scoreEquipeB);
    }

    public int getIdResultat() {
        return idResultat;
    }

    public void setIdResultat(int idResultat) {
        this.idResultat = idResultat;
    }

    public String getNomEquipeA() {
        return nomEquipeA;
    }

    public void setNomEquipeA(String nomEquipeA) {
        this.nomEquipeA = nomEquipeA;
    }

    public String getNomEquipeB() {
        return nomEquipeB;
    }

    public void setNomEquipeB(String nomEquipeB) {
        this.nomEquipeB = nomEquipeB;
    }

    public int getScoreEquipeA() {
        return scoreEquipeA;
    }

    public void setScoreEquipeA(int scoreEquipeA) {
        this.scoreEquipeA = scoreEquipeA;
    }

    public int getScoreEquipeB() {
        return scoreEquipeB;
    }

    public void setScoreEquipeB(int scoreEquipeB) {
        this.scoreEquipeB = scoreEquipeB;
    }

    @Override
    public String toString() {
        return "Partie " + idResultat +
                "\n  Equipe " + nomEquipeA + ": " + scoreEquipeA + " points\n" +
                "  Equipe " + nomEquipeB + ": " + scoreEquipeB + " points ";
    }
}
