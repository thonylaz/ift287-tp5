package CentreSportif;

public class TupleParticipant {
    private int matricule;
    private String nom;
    private String prenom;
    private String motDePasse;
    private String nomEquipe;
    private int estAccepter;


    public TupleParticipant() {
    }

    public TupleParticipant(int matricule, String nom, String prenom, String motDePasse, String nomEquipe, int estAccepter) {
        this.setMatricule(matricule);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setMotDePasse(motDePasse);
        this.setNomEquipe(nomEquipe);
        this.setEstAccepter(estAccepter);
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getEstAccepter() { return estAccepter; }

    public void setEstAccepter(int estAccepter) { this.estAccepter = estAccepter; }

    @Override
    public String toString() {
        return "  Matricule: " + matricule +
                "\n  Nom: " + nom +
                "\n  Prenom: " + prenom;
    }
}
