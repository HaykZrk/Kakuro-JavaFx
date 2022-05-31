package jeu;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Classement {
    private ArrayList<Score> tableau;
    private File doc;

    public Classement(String fichier) throws IOException {
        doc = new File(fichier);
        Scanner scan = new Scanner(doc);
        String strScore, nom;
        int valeur;
        Score nvLigne;

        tableau = new ArrayList<Score>();
        while (scan.hasNext()) {
            strScore = scan.nextLine();
            nom = strScore.split(" ")[0];
            valeur = Integer.parseInt(strScore.split(" ")[1]);
            nvLigne = new Score(nom, valeur);
            getTableau().add(nvLigne);
        }
        scan.close();
    }

    /**
     * Ajoute un score au tableau en veillant a éviter les doublons
     * 
     * @param nom le nom à ajouter
     * @param valeur la valeur à ajouter
     */
    public void nouveauScore(String nom, int valeur) {
        Score nvScore = new Score(nom, valeur);
        boolean miseAJourEffectuee = false;
        for (Score s : getTableau()) {
            miseAJourEffectuee = miseAJourEffectuee || s.miseAJour(new Score(nom, valeur));
        }
        if (!miseAJourEffectuee) {
            getTableau().add(nvScore);
        }
    }

    /**
     * Refait le classement à partir du tableau trié et à jour
     * 
     * @throws IOException
     */
    public void miseAJourClassement() throws IOException {
        PrintWriter pw = new PrintWriter(doc);
        
        Collections.sort(tableau, Score.COMPARE);
        Collections.reverse(tableau);

        for (Score s : getTableau()) {
            pw.println(s);
        }
        
        pw.close();
    }

	public ArrayList<Score> getTableau() {
		return tableau;
	}
}