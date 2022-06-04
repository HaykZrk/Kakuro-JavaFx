package jeu;

import java.util.ArrayList;

public class PileDeCoups extends ArrayList<Coup> {
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> checkpoints;

    public PileDeCoups() {
        super();
        checkpoints = new ArrayList<Integer>();
    }

    /**
     * Ajoute un coup en tête de liste
     * 
     * @param c
     */
    public void adjt(Coup c) { add(c); }
    public Coup tete() {
        int nbcoups = size();
        if (nbcoups == 0) return null;
        return get(nbcoups-1);
    }
    /**
     * Supprime le coup en tête de liste
     */
    public void supt() {
        int nbcoups = size();
        if (nbcoups == 0) return;
        remove(nbcoups-1);
    }
    /**
     * Ajoute un checkpoint en tête
     */
    public void newCheckpoint() {
        checkpoints.add(size());
    }
    
    /**
     * Supprime le checkpoint en tête
     */
    public void delCheckpoint() {
        int taille =  checkpoints.size();
        if (taille != 0) checkpoints.remove(taille-1);
    }

    public ArrayList<Integer> getCheckpoints() { return checkpoints; }
}
