package jeu;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Grille {
    private int dim_x, dim_y;
    private Case table[][];

    public Grille(String fichier) throws IOException {
        File doc = new File(fichier);
        Scanner scan = new Scanner(doc);
        String dim = scan.nextLine();

        dim_x = Integer.parseInt(dim.split(" ")[0]);
        dim_y = Integer.parseInt(dim.split(" ")[1]);

        this.table = new Case[dim_y][dim_x];
        String lgn, curCaseStr;
        Case curCase;
        int indicLigne;
        int indicColonne;
        for (int i = 0; i < dim_y; i++) {
            lgn = scan.nextLine();
            for (int j = 0; j < dim_x; j++) {
                curCaseStr = lgn.split(" ")[j];

                if (curCaseStr.charAt(0) == '{') {
                    curCaseStr = curCaseStr.substring(1,curCaseStr.length()-1);
                    indicColonne = Integer.parseInt(curCaseStr.split(",")[0]);
                    indicLigne = Integer.parseInt(curCaseStr.split(",")[1]);
                    curCase = new CaseIndication(indicLigne, indicColonne);
                } else if (curCaseStr.charAt(0) == '0') {
                    curCase = new CaseNoire();
                } else if (curCaseStr.charAt(0) == '_') {
                    curCase = new CaseChiffre();
                } else {
                    curCase = new CaseChiffre(Integer.parseInt(curCaseStr));
                }

                table[i][j] = curCase;
            }
        }

        scan.close();
    }

    public int getDimX() { return dim_x; }
    public int getDimY() { return dim_y; }
    public Case[][] getTable() { return table; }
    public Case getCase(int x, int y) { return table[x][y]; }

    /**
     * Modifie la case sans aucune verification sur le coup.
     * 
     * @param c Le coup a effectuer 
     */
    public void setCase(Coup c) {
        if (table[c.getX()][c.getY()].estChiffre()) {
            ((CaseChiffre)table[c.getX()][c.getY()]).setVal(c.getVal());
        }
    }
}
