package jeu;

import java.util.Comparator;

public class Score {
    private String nom;
    private int valeur;

    public Score(String nom, int valeur) {
        if (nom.equals(" ")) this.nom = "Anon";
        else this.nom = nom.split(" ")[0];
        this.valeur = valeur;
    }

    /**
     * Modifie la valeur si le nom est identique
     * 
     * @param s
     * @return Vrai si les noms sont identiques
     */
    public boolean miseAJour(Score s) {
        if (s.getNom().equals(nom)) {
            if (s.getValeur() > valeur) {
                setValeur(s.getValeur());
            }
            return true;
        }
        return false;
    }

    public String getNom() { return nom; }
    public int getValeur() { return valeur; }
    public void setValeur(int valeur) { this.valeur = valeur; }
    
    /**
     * Comparateur de score
     */
    public static Comparator<Score> COMPARE = new Comparator<Score>() {
        public int compare(Score one, Score other) {
            return one.valeur > other.valeur ? 1 : one.valeur < other.valeur ? -1 : 0;
        }
    };

    public String toString() {
        return nom+" "+valeur;
    }
}