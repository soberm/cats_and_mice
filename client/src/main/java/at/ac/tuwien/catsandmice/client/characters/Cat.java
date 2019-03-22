package at.ac.tuwien.catsandmice.client.characters;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.client.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Cat {

    private Image cat;
    private int height;
    private int width;

    private int x = 0;
    private int y = 0;

    private int speed = 2;

    private int rotation = 0;

    private int dx;
    private int dy;

    public Cat() {
        loadImage();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(Board.class.getResource("/sprites/cat.png"));
        Image original = ii.getImage();
        int height=original.getHeight(null);
        this.height = height/5;
        int width = original.getWidth(null);
        this.width = width/5;
        this.cat = original.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
    }

    public void move() {
        if(dx > 0 && (x+dx < getWidthBoundary()) || (dx < 0 && x+dx > 0)) {
            x += dx;
        }
        if(dy > 0 && (y+dy < getHeightBoundary()) || (dy < 0 && y+dy > 0)) {
            y += dy;
        }
    }

    private int getWidthBoundary() {
        int width;
        if(rotation % 180 == 0) {
            width = this.width;
        } else {
            width = this.height;
        }
        return Constants.SCREEN_WIDTH-width;
    }

    private int getHeightBoundary() {
        int height ;
        if(rotation % 180 == 0) {
            height = this.height;
        } else {
            height = this.width;
        }
        return Constants.SCREEN_HEIGHT-height;
    }

    public Image getImage() {
        return this.cat;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getRotation() {
        return rotation;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            rotation = 0;
            dx = -speed;
        }

        if (key == KeyEvent.VK_D) {
            rotation = 180;
            dx = speed;
        }

        if (key == KeyEvent.VK_W) {
            rotation = 90;
            dy = -speed;
        }

        if (key == KeyEvent.VK_S) {
            rotation = 270;
            dy = speed;

        }
    }

    private synchronized void releaseRotation() {
        if(dy > 0) {
            rotation = 270;
        } else if(dy < 0) {
            rotation = 90;
        }
        if(dx > 0) {
            rotation = 180;
        } else if (dx < 0) {
            rotation = 0;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A && dx == -speed) {
            dx = 0;
            releaseRotation();
        }

        if (key == KeyEvent.VK_D && dx == speed) {
            dx = 0;
            releaseRotation();
        }

        if (key == KeyEvent.VK_W && dy == -speed) {
            dy = 0;
            releaseRotation();
        }

        if (key == KeyEvent.VK_S && dy == speed) {
            dy = 0;
            releaseRotation();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
