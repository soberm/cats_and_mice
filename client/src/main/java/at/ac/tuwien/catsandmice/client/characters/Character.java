package at.ac.tuwien.catsandmice.client.characters;

import at.ac.tuwien.catsandmice.client.world.Boundaries;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public abstract class Character {

    private boolean alive = true;

    private Image image;
    private int height;
    private int width;

    private int x = 0;
    private int y = 0;

    private int speed = 2;

    private int rotation = 0;

    private int dx;
    private int dy;

    private Boundaries boundaries;

    protected int UP = KeyEvent.VK_W;
    protected int DOWN = KeyEvent.VK_S;
    protected int LEFT = KeyEvent.VK_A;
    protected int RIGHT = KeyEvent.VK_D;




    public void move() {
        if(dx > 0 && (x+dx < getWidthBoundary()) || (dx < 0 && x+dx > boundaries.getMinWidth())) {
            x += dx;
        }
        if(dy > 0 && (y+dy < getHeightBoundary()) || (dy < 0 && y+dy > boundaries.getMinHeight())) {
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
        return boundaries.getMaxWidth()-width;
    }

    private int getHeightBoundary() {
        int height ;
        if(rotation % 180 == 0) {
            height = this.height;
        } else {
            height = this.width;
        }
        return boundaries.getMaxHeight()-height;
    }



    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == LEFT) {
            rotation = 0;
            dx = -speed;
        }

        if (key == RIGHT) {
            rotation = 180;
            dx = speed;
        }

        if (key == UP) {
            rotation = 90;
            dy = -speed;
        }

        if (key == DOWN) {
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

        if (key == LEFT && dx == -speed) {
            dx = 0;
            releaseRotation();
        }

        if (key == RIGHT && dx == speed) {
            dx = 0;
            releaseRotation();
        }

        if (key == UP && dy == -speed) {
            dy = 0;
            releaseRotation();
        }

        if (key == DOWN && dy == speed) {
            dy = 0;
            releaseRotation();
        }
    }

    public void draw(Graphics2D g2d, ImageObserver observer){
        AffineTransform transformation = getTransformation();
        g2d.drawImage(getImage(), transformation, observer);
    }

    private AffineTransform getTransformation() {
        AffineTransform transformation = getRotate();
        transformation.translate(getX(), getY());
        return transformation;
    }

    private AffineTransform getRotate() {
        AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(getRotation()), getX() + getWidth() / 2, getY() + getHeight() / 2);
        return transform;
    }

    public Rectangle getBounds() {
        Rectangle rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        AffineTransform transform = getRotate();
        Shape shape = transform.createTransformedShape(rectangle);
        return shape.getBounds();
    }


    public Image getImage() {
        return image;
    }

    protected void setImage(Image image) {
        this.image = image;
    }

    public int getHeight() {
        return height;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    protected void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    protected void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRotation() {
        return rotation;
    }

    protected void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getDx() {
        return dx;
    }

    protected void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    protected void setDy(int dy) {
        this.dy = dy;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Boundaries getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(Boundaries boundaries) {
        this.boundaries = boundaries;
    }
}
