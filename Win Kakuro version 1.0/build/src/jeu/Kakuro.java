package jeu;

import java.io.IOException;
import java.util.ArrayList;

import exception.MonException;

public class Kakuro {
	private Grille g;
    private PileDeCoups history;

    public Kakuro(String fichier) throws IOException {
        g = new Grille(fichier);
        history = new PileDeCoups();
    }

    public int getSommeLigne(int x, int y) {
        int somme = 0;
        int tab[] = getLigne(x,y);
        for (int v : tab) somme += v;
        return somme;
    }
    public int getSommeColonne(int x, int y) {
        int somme = 0;
        int tab[] = getColonne(x,y);
        for (int v : tab) somme += v;
        return somme;
    }

    /**
     * Vérifie si la ligne est complète et juste
     * @param x
     * @param y
     * @return 
     */
    private boolean ligneTerminee(int x,int y) {
        try {
            return getSommeLigne(x,y) == getIndicLigne(x,y);
        } catch (MonException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Vérifie si la partie s'est terminée
     * 
     * @return
     */
    public boolean partieFinie() {
        for (int i = 0; i < g.getDimY(); i++) {
            for (int j = 0; j < g.getDimX(); j++) {
                if (g.getCase(i,j).estChiffre()) {
                    if (!ligneTerminee(i, j)) return false;
                } 
            }
        }
        return true;
    }

    /**
     * Vérifie si le chiffre a déjà été utilisé sur la ligne
     * 
     * @param chiffre
     * @param x
     * @param y
     * @return
     */
    private boolean chiffreUtiliseLigne(int chiffre, int x, int y) {
        int ligne[] = getLigne(x,y);
        for (int i = 0; i < ligne.length; i++) {
            if (ligne[i] == chiffre) return true;
        }
        return false;
    }
    /**
     * Vérifie si le chiffre a déjà été utilisé sur la colonne
     * 
     * @param chiffre
     * @param x
     * @param y
     * @return
     */
    private boolean chiffreUtiliseColonne(int chiffre, int x, int y) {
        int colonne[] = getColonne(x,y);
        for (int i = 0; i < colonne.length; i++) {
            if (colonne[i] == chiffre) return true;
        }
        return false;
    }

    /**
     * Vérifie si le coup est valide
     * 
     * @param c
     * @return
     */
    private boolean coupValide(Coup c) {
        try {
            boolean dimValide = c.getX() >= 0 && c.getX() < g.getDimY() && 
                c.getY() >= 0 && c.getY() < g.getDimX();
            boolean valValide = c.getVal() > 0 && c.getVal() < 10 &&
                !(chiffreUtiliseLigne(c.getVal(), c.getX(), c.getY()) || 
                chiffreUtiliseColonne(c.getVal(), c.getX(), c.getY())) &&
                c.getVal() <= getIndicLigne(c.getX(),c.getY()) - getSommeLigne(c.getX(),c.getY()) && 
                c.getVal() <= getIndicColonne(c.getX(),c.getY()) - getSommeColonne(c.getX(),c.getY());
            return dimValide && valValide;
        } catch (MonException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Effectue un coup
     * 
     * @param c le coup à effectuer
     * @return true si le coup a été effectué, false sinon
     */
    public boolean jouerCoup(Coup c) {
        if (coupValide(c)) {
            g.setCase(c);
            history.adjt(c);
            return true;
        }
        return false;
    }

    /** Annule le dernier coup  */
    public void annulerDernierCoup() {
        Coup c = history.tete();
        c.setVal(0);
        history.supt();
        g.setCase(c);
    }

    /**
     * Reviens au dernier checkpoint
     * 
     * @return Le coup effectué au dernier checkpoint
     * @throws MonException
     */
    public Coup dernierCheckpoint() throws MonException {
        ArrayList<Integer> checkpt = history.getCheckpoints();
        if (checkpt.isEmpty()) throw(new MonException("Pas de checkpoint")); 
        int lastCheckpoint = checkpt.get(checkpt.size()-1);
        while (history.size() != lastCheckpoint) {
            annulerDernierCoup();
        }
        Coup c = history.tete().copie();
        annulerDernierCoup();
        return c;
    }
    
    /**
     * Compte le nombre de case à remplir à la base
     * 
     * @return le nombre de case à remplir
     */
    public int nbCasesACompleter() {
        int compte = 0;
        for (int i = 0; i < getGrille().getDimY(); i++) {
            for (int j = 0; j < getGrille().getDimX(); j++) {
                if (getGrille().getCase(i, j).estChiffre() && ((CaseChiffre)getGrille().getCase(i, j)).isModifiable()) {
                    compte++;
                }
            }
        }
        return compte;
    }

    public Grille getGrille() { return g; }
    public PileDeCoups getHistory() { return history; }

    /**
     * Cherche l'indication de la ligne
     * 
     * @param x
     * @param y
     * @return
     * @throws MonException
     */
    public int getIndicLigne(int x, int y) throws MonException {
        while (!getGrille().getCase(x,y).estIndication()) {
            y--;
            if (y < 0) throw(new MonException("Pas d'indication sur la colonne"));
        }
        return ((CaseIndication)getGrille().getCase(x, y)).getIndicLigne();
    }
    /**
     * Cherche l'indication de la colonne
     * 
     * @param x
     * @param y
     * @return
     * @throws MonException
     */
    public int getIndicColonne(int x, int y) throws MonException {
        while (!getGrille().getCase(x,y).estIndication()) {
            x--;
            if (x < 0) throw(new MonException("Pas d'indication sur la colonne"));
        }
        return ((CaseIndication)getGrille().getCase(x,y)).getIndicColonne();
    }

    /**
     * Renvoie les éléments de la ligne
     * 
     * @param x
     * @param y
     * @return liste des chiffre de la ligne
     */
    public int[] getLigne(int x, int y) {
        int i, k, lgr, debut, fin;
        i = y;
        while (!getGrille().getCase(x, i).estIndication()) i--;
        debut = i;
        i = y;
        while (i<getGrille().getDimX() && !(getGrille().getCase(x,i).estIndication() ||
            getGrille().getCase(x,i).estNoire())) i++;
        fin = i;
        lgr = fin - debut -1;
        
        int tab[] = new int[lgr];
        for (i = debut+1, k = 0; i < fin; i++, k++)
            tab[k] = ((CaseChiffre)getGrille().getCase(x,i)).getVal();
        return tab;
    }
    /**
     * Renvoie les éléments de la ligne
     * 
     * @param x
     * @param y
     * @return liste des chiffre de la colonne
     */
    public int[] getColonne(int x, int y) {
        int i, k, lgr, debut, fin;
        i = x;
        while (!getGrille().getCase(i,y).estIndication()) i--;
        debut = i;
        i = x;
        while (i<getGrille().getDimY() && !(getGrille().getCase(i,y).estIndication() ||
            getGrille().getCase(i,y).estNoire())) i++;
        fin = i;
        lgr = fin - debut -1;
        
        int tab[] = new int[lgr];
        for (i = debut+1, k = 0; i < fin; i++, k++)
            tab[k] = ((CaseChiffre)getGrille().getCase(i,y)).getVal();
        return tab;
    }
}
