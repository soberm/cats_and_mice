package at.ac.tuwien.catsandmice.client.characters;


import java.awt.event.KeyEvent;

public class CatPlayer extends Player {

    private CatRepresentation cat;

    public CatPlayer(CatRepresentation cat) {
        this.cat = cat;
        setCharacter(this.cat);

        UP = KeyEvent.VK_UP;
        DOWN = KeyEvent.VK_DOWN;
        LEFT = KeyEvent.VK_LEFT;
        RIGHT = KeyEvent.VK_RIGHT;

        setSpeed(4);
    }




}
