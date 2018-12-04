package CentreSportif;

public class TupleLigue {
    private String nomLigue;
    private int nbJoueurMaxParEquipe;

    public TupleLigue() {
    }

    public TupleLigue(String nomLigue, int nbJoueurMaxParEquipe) {
        this.setNomLigue(nomLigue);
        this.setNbJoueurMaxParEquipe(nbJoueurMaxParEquipe);
    }

    public String getNomLigue() {
        return nomLigue;
    }

    public void setNomLigue(String nomLigue) {
        this.nomLigue = nomLigue;
    }

    public int getNbJoueurMaxParEquipe() {
        return nbJoueurMaxParEquipe;
    }

    public void setNbJoueurMaxParEquipe(int nbJoueurMaxParEquipe) {
        this.nbJoueurMaxParEquipe = nbJoueurMaxParEquipe;
    }
}
