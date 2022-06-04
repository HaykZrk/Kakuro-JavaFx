package jeu;

public class CaseIndication extends Case {
    private int indicLigne, indicColonne;

    public CaseIndication(int indicLigne, int indicColonne) {
        this.indicLigne = indicLigne;
        this.indicColonne = indicColonne;
    }

    public boolean estNoire() { return false; }
    public boolean estIndication() { return true; }
    public boolean estChiffre() { return false; }

    public int getIndicLigne() { return indicLigne; }
    public int getIndicColonne() { return indicColonne; }
}

