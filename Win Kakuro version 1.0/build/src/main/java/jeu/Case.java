package jeu;

public abstract class Case {
    /**
     * Test si la case est noire
     * @return boolean
     */
    public abstract boolean estNoire();

    /**
     * Test si la case est une indication
     * @return boolean
     */
    public abstract boolean estIndication();

    /**
     * Test si la case est un chiffre
     * @return boolean
     */
    public abstract boolean estChiffre();
}

