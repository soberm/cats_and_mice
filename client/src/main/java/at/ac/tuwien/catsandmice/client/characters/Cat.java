package at.ac.tuwien.catsandmice.client.characters;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.client.world.Boundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Cat extends Character {

    public Cat(Boundaries boundaries) {
        setBoundaries(boundaries);
        UP = KeyEvent.VK_W;
        DOWN = KeyEvent.VK_S;
        LEFT = KeyEvent.VK_A;
        RIGHT = KeyEvent.VK_D;

        setSpeed(4);

        loadImage();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(Board.class.getResource("/sprites/cat.png"));
        Image original = ii.getImage();
        int height=original.getHeight(null);
        setHeight(height/5);
        int width = original.getWidth(null);
        setWidth(width/5);
        setImage(original.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
    }


}
