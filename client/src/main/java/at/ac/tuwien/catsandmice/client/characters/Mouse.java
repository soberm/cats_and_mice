package at.ac.tuwien.catsandmice.client.characters;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.client.util.Constants;
import at.ac.tuwien.catsandmice.client.world.Boundaries;
import at.ac.tuwien.catsandmice.client.world.Subway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Mouse extends Character {

    private boolean tryToEnter = false;

    public Mouse(Boundaries boundaries) {
        setBoundaries(boundaries);
        UP = KeyEvent.VK_UP;
        DOWN = KeyEvent.VK_DOWN;
        LEFT = KeyEvent.VK_LEFT;
        RIGHT = KeyEvent.VK_RIGHT;

        setSpeed(3);

        loadImage();
    }

    private void enter(Subway subway) {
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon(Board.class.getResource("/sprites/mouse.png"));
        Image original = ii.getImage();
        int height=original.getHeight(null);
        setHeight(height/5);
        int width = original.getWidth(null);
        setWidth(width/5);

        setX(Constants.SCREEN_WIDTH - 200);
        setY(Constants.SCREEN_HEIGHT - 200);
        setImage(original.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
    }

    public boolean isTryToEnter() {
        return tryToEnter;
    }

    public void setTryToEnter(boolean tryToEnter) {
        this.tryToEnter = tryToEnter;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(e.getKeyCode() == KeyEvent.VK_E) {
            setTryToEnter(true);
        }
    }

    public void moveTo(int x, int y) {
        setX(x-getWidth()/2);
        setY(y-getHeight()/2);
    }
}
