package CentreSportif;

public class TupleEquipe {
    private String nomEquipe;
    private String nomLigue;
    private int matriculeCapitaine;

    public TupleEquipe() {
    }

    public TupleEquipe(String nomLigue, String nomEquipe, int matriculeCapitaine) {
        this.setNomLigue(nomLigue);
        this.setNomEquipe(nomEquipe);
        this.setMatriculeCapitaine(matriculeCapitaine);
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public int getMatriculeCapitaine() {
        return matriculeCapitaine;
    }

    public void setMatriculeCapitaine(int matriculeCapitaine) {
        this.matriculeCapitaine = matriculeCapitaine;
    }

    public String getNomLigue() {
        return nomLigue;
    }

    public void setNomLigue(String nomLigue) {
        this.nomLigue = nomLigue;
    }

    @Override
    public String toString() {
        return "Nom Ligue: '" + nomLigue +
                "' | Nom Equipe: '" + nomEquipe +
                "' | Matricule Capitaine: " + matriculeCapitaine;
    }
}