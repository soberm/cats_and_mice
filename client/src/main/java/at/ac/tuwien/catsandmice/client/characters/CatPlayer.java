package at.ac.tuwien.catsandmice.client.characters;


import java.awt.event.KeyEvent;

public class CatPlayer extends Player {

    private CatRepresentation cat;

    public CatPlayer(CatRepresentation cat) {
        this.cat = cat;
        setCharacter(this.cat);

        UP = KeyEvent.VK_W;
        DOWN = KeyEvent.VK_S;
        LEFT = KeyEvent.VK_A;
        RIGHT = KeyEvent.VK_D;

        setSpeed(4);
    }




}
