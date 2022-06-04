package jeu;

import exception.MonException;

public class Solveur {
    Kakuro k;

    public Solveur(Kakuro k) { this.k = k; }

    /**
     * Résout le kakuro
     * @return la grille du kakuro complétée
     */
    public Grille resoudre() {
        int lgnSolvable[], colSolvable[];
        boolean solveSuccess = true;

        while(! k.partieFinie()) {
            if (!solveSuccess) {
                int val = 1;
                while (val > 0 && !solveSuccess) {
                    try {
                        Coup c = k.dernierCheckpoint();
                        val = c.getVal()-1;
                        if (val != 0) {
                            c.setVal(val);
                            solveSuccess = hypothese(c);
                        }
                    } catch(MonException e) {
                        System.out.println(e);
                        System.exit(1);
                    }
                }
                if (val ==0) k.getHistory().delCheckpoint();
            } else if ((lgnSolvable = solutionUniqueLigne())[0] != -1) {
                solveSuccess = resoudreLigne(lgnSolvable[0],lgnSolvable[1]);
            } else if ((colSolvable = solutionUniqueColonne())[0] != -1) {
                solveSuccess = resoudreColonne(colSolvable[0],colSolvable[1]);
            } else {
                int minLigneAResoudre[] = plusPetiteLigne();
                int minColonneAResoudre[] = plusPetiteColonne();
                if (nbCasesLibresLigne(minLigneAResoudre[0],minLigneAResoudre[1]) <= 
                    nbCasesLibresColonne(minColonneAResoudre[0],minColonneAResoudre[1])) {
                    solveSuccess = hypotheseLigne(minLigneAResoudre[0],minLigneAResoudre[1]);
                } 
                else {
                    solveSuccess = hypotheseColonne(minColonneAResoudre[0],minColonneAResoudre[1]);
                }
            }
        }
        return k.getGrille();
    }

    /**
     * Le nombres de case pas encore complétée sur la ligne
     * 
     * @param x
     * @param y
     * @return
     */
    private int nbCasesLibresLigne(int x, int y) {
        int compte = 0;
        int ligne[] = k.getLigne(x,y);
        for (int i = 0; i < ligne.length; i++) {
            if (ligne[i] == 0) compte++;
        }
        return compte;
    }
    /**
     * Le nombres de case pas encore complétée sur la colonne
     * 
     * @param x
     * @param y
     * @return
     */
    private int nbCasesLibresColonne(int x, int y) {
        int compte = 0;
        int colonne[] = k.getColonne(x,y);
        for (int i = 0; i < colonne.length; i++) {
            if (colonne[i] == 0) compte++;
        }
        return compte;
    }

    /**
     * Cherche une ligne à solution unique
     * 
     * @param x
     * @param y
     * @return une ligne à solution unique ou {-1,-1} si il n'y en a pas
     */
    private int[] solutionUniqueLigne() {
        int tab[] = new int[2];
        for (int i = 0; i < k.getGrille().getDimY(); i++) {
            for (int j = 0; j < k.getGrille().getDimX(); j++) {
                if (k.getGrille().getCase(i,j).estChiffre() && nbCasesLibresLigne(i,j) == 1) {
                    while (((CaseChiffre)k.getGrille().getCase(i,j)).getVal() != 0) j++;
                    tab[0] = i; tab[1] = j;
                    return tab;
                }
            }
        }
        tab[0] = -1;
        tab[1] = -1;
        return tab;
    }
    /**
     * Cherche une colonne à solution unique
     * 
     * @param x
     * @param y
     * @return une colonne à solution unique ou {-1,-1} si il n'y en a pas
     */
    private int[] solutionUniqueColonne() {
        int tab[] = new int[2];
        for (int i = 0; i < k.getGrille().getDimY(); i++) {
            for (int j = 0; j < k.getGrille().getDimX(); j++) {
                if (k.getGrille().getCase(i,j).estChiffre() && nbCasesLibresColonne(i,j) == 1) {
                    while (((CaseChiffre)k.getGrille().getCase(i,j)).getVal() != 0) i++;
                    tab[0] = i; tab[1] = j;
                    return tab;
                }
            }
        }
        tab[0] = -1;
        tab[1] = -1;
        return tab;
    }

    /**
     * résout la ligne en paramètre
     * @param x
     * @param y
     * @return true si la résolution a fonctionné
     */
    private boolean resoudreLigne(int x, int y) {
        try {
            int sol = k.getIndicLigne(x,y) - k.getSommeLigne(x,y);
            return k.jouerCoup(new Coup(x,y,sol));
        } catch (MonException e) {
            System.out.println(e);
            return false;
        }
    }
    /**
     * résout la colonne en paramètre
     * @param x
     * @param y
     * @return true si la résolution a fonctionné
     */
    private boolean resoudreColonne(int x, int y) {
        try {
            int sol = k.getIndicColonne(x,y) - k.getSommeColonne(x,y);
            return k.jouerCoup(new Coup(x,y,sol));
        } catch (MonException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Cherche la ligne avec le moins d'inconnu
     * 
     * @return
     */
    private int[] plusPetiteLigne() {
        int caseMin[] = new int[2];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < k.getGrille().getDimY(); i++) {
            for (int j = 0; j < k.getGrille().getDimX(); j++) {
                if (k.getGrille().getCase(i,j).estChiffre()) {
                    int taille = nbCasesLibresLigne(i,j);
                    if (taille!= 0 && taille < min){
                        min = taille;
                        caseMin[0] = i;
                        caseMin[1] = j;
                    }
                }
            }
        }
        return caseMin;
    }
    /**
     * Cherche la colonne avec le moins d'inconnu
     * 
     * @return
     */
    private int[] plusPetiteColonne() {
        int caseMin[] = new int[2];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < k.getGrille().getDimY(); i++) {
            for (int j = 0; j < k.getGrille().getDimX(); j++) {
                if (k.getGrille().getCase(i,j).estChiffre()) {
                    int taille = nbCasesLibresColonne(i,j);
                    if (taille!= 0 && taille < min){
                        min = taille;
                        caseMin[0] = i;
                        caseMin[1] = j;
                    }
                }
            }
        }
        return caseMin;
    }

    /**
     * Tente un coup sur la ligne
     * 
     * @param x
     * @param y
     * @return true si l'hypothèse a fonctionné
     */
    private boolean hypotheseLigne(int x, int y) {
        try {
            int maxIndic = 0;
            int caseMaxIndic = -1;
            while(!k.getGrille().getCase(x,y).estIndication()) y--;
            y++;
            while (y<k.getGrille().getDimX() && !(k.getGrille().getCase(x,y).estIndication() ||
                k.getGrille().getCase(x,y).estNoire())) {
                if (k.getGrille().getCase(x, y).estChiffre() && 
                    ((CaseChiffre)k.getGrille().getCase(x,y)).getVal() == 0) {
                    if (k.getIndicColonne(x,y) > maxIndic) {
                        maxIndic = k.getIndicColonne(x,y);
                        caseMaxIndic = y;
                    }
                }
                y++;
            }

            return hypothese(new Coup(x, caseMaxIndic, 9));
        } catch (MonException e) {
            System.out.println(e);
            return false;
        }
    }/**
     * Tente un coup sur la colonne
     * 
     * @param x
     * @param y
     * @return true si l'hypothèse a fonctionné
     */
    private boolean hypotheseColonne(int x, int y) {
        try {
            int maxIndic = 0;
            int caseMaxIndic = -1;
            while(!k.getGrille().getCase(x,y).estIndication()) x--;
            x++;
            while (x<k.getGrille().getDimX() && !(k.getGrille().getCase(x,y).estIndication() ||
                k.getGrille().getCase(x,y).estNoire())) {
                if (k.getGrille().getCase(x,y).estChiffre()) {
                    if (k.getIndicLigne(x,y) > maxIndic) {
                        maxIndic = k.getIndicLigne(x,y);
                        caseMaxIndic = x;
                    }
                }
                x++;
            }

            return hypothese(new Coup(caseMaxIndic, y, 9));
        } catch (MonException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Tente un coup et l'ajoute en checkpoint
     * 
     * @param x
     * @param y
     * @return true si le coup a fonctionné
     */
    private boolean hypothese(Coup c) {
        boolean coupJoue = false;
        while(c.getVal() > 0 && !coupJoue) {
            coupJoue = k.jouerCoup(c);
            if (coupJoue) k.getHistory().newCheckpoint();
            else c.setVal(c.getVal()-1);
        }
        return coupJoue;
    }
}