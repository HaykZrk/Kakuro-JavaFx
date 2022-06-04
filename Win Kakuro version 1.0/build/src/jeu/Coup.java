package jeu;

public class Coup {
    private int x, y;
    private int val;

    public Coup(int x, int y, int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVal() { return val; }
    public void setVal(int x) { val = x; }
    
    /**
     * Créer une nouvelle instance de Coup identique
     * 
     * @return la copie
     */
    public Coup copie() {
    	return new Coup(x, y, val);
    }
}
