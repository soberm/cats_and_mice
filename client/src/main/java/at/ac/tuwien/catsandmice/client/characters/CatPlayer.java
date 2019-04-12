package at.ac.tuwien.catsandmice.client.characters;


import at.ac.tuwien.catsandmice.client.world.SubwayRepresantation;
import at.ac.tuwien.catsandmice.client.world.WorldRepresentation;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

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
