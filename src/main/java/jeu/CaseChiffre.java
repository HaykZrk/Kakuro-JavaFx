package jeu;

public class CaseChiffre extends Case {
    private int val;
    private boolean modifiable;

    public CaseChiffre() {
        val = 0;
        modifiable = true;
    }
    public CaseChiffre(int val) {
        this.val = val;
        modifiable = false;
    }

    public boolean estNoire() { return false; }
    public boolean estIndication() { return false; }
    public boolean estChiffre() { return true; }

    /**
     * Test si la case a déjà été remplie
     * @return boolean
     */
    public boolean estVide() { return val == 0; }

    public int getVal() { return val; }
    public void setVal(int val) { 
        if (isModifiable()) this.val = val; 
    }
	public boolean isModifiable() {
		return modifiable;
	}
}

