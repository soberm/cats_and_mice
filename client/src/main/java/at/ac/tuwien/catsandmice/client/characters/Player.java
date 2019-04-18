package at.ac.tuwien.catsandmice.client.characters;

import at.ac.tuwien.catsandmice.client.util.ClientConstants;
import at.ac.tuwien.catsandmice.client.world.SubwayRepresantation;
import at.ac.tuwien.catsandmice.client.world.WorldRepresentation;
import at.ac.tuwien.catsandmice.dto.characters.Character;
import at.ac.tuwien.catsandmice.dto.world.IBoundaries;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Player {

    private boolean alive = true;
    private Character character;

    private boolean initialised = false;

    private Image image;

    private int speed = 2;

    private int dx;
    private int dy;


    protected int UP = KeyEvent.VK_W;
    protected int DOWN = KeyEvent.VK_S;
    protected int LEFT = KeyEvent.VK_A;
    protected int RIGHT = KeyEvent.VK_D;

    private Socket socket;

    protected PrintWriter writer;

    public Player() {

    }

    public void resetClicks() {

    }


    public void move() {
        if(isInitialised()) {
            if (dx > 0) {
                if (character.getX() + dx <= getWidthBoundary()) {
                    character.setX(character.getX() + dx);
                }
            } else if (dx < 0) {
                if (character.getX() + dx >= getCorrectMinWidthBoundary()) {
                    character.setX(character.getX() + dx);
                }
            }
            if (dy > 0) {
                if (character.getY() + dy <= getHeightBoundary()) {
                    character.setY(character.getY() + dy);
                }
            } else if (dy < 0) {
                if (character.getY() + dy >= getCorrectMinHeightBoundary()) {
                    character.setY(character.getY() + dy);
                }
            }

            updateOnServer();
        }
    }

    protected void updateOnServer() {
        String json = ClientConstants.getGson().toJson(character);
        writer.println(json);
        writer.flush();
    }

    private int getWidthBoundary() {
        return character.getBoundaries().getMaxWidth()-getWidthOfCurrentRotatedSprite();
    }

    private int getHeightBoundary() {
        return character.getBoundaries().getMaxHeight()-getHeightOfCurrentRotatedSprite();
    }

    private int getCorrectMinHeightBoundary() {
        int height;
        if(character.getRotation() % 180 == 0) {
            height = character.getBoundaries().getMinHeight();
        } else {
            height = character.getBoundaries().getMinHeight() + (character.getWidth()-character.getHeight())/2;
        }
        return height;
    }

    private int getCorrectMinWidthBoundary() {
        int width;
        if(character.getRotation() % 180 == 0) {
            width = character.getBoundaries().getMinWidth();
        } else {
            width = character.getBoundaries().getMinWidth() - (character.getWidth()-character.getHeight())/2;
        }
        return width;
    }

    private int getHeightOfCurrentRotatedSprite() {
        int height ;
        if(character.getRotation() % 180 == 0) {
            height = character.getHeight();
        } else {
            height = character.getHeight() + (character.getWidth()-character.getHeight())/2;
        }
        return height;
    }

    private int getWidthOfCurrentRotatedSprite() {
        int width;
        if(character.getRotation() % 180 == 0) {
            width = character.getWidth();
        } else {
            width = character.getWidth() - (character.getWidth()-character.getHeight())/2;
        }
        return  width;
    }

    public synchronized void updateCharacter(Character character) {

        this.character.setHeight(character.getHeight());
        this.character.setWidth(character.getWidth());
        if(this.character.getX() == null) {
            this.character.setX(character.getX());
        }
        if(this.character.getY() == null) {
            this.character.setY(character.getY());
        }
        if(this.character.getRotation() == null) {
            this.character.setRotation(character.getRotation());
        }
        if(this.character.getBoundaries() == null) {
            this.character.setBoundaries(character.getBoundaries());
        }

            this.character.setAlive(character.isAlive());

        setInitialised(true);
    }

    public void draw(Graphics2D g2d, ImageObserver observer, WorldRepresentation worldRepresentation) {
        for(SubwayRepresantation subway : worldRepresentation.getSubwayRepresantations()) {
            subway.draw(g2d, observer, true);
        }
        for(MouseRepresentation mouse : worldRepresentation.getMouseRepresentations()) {
            if(mouse.isAlive()) {
                mouse.draw(g2d, observer);
            }
        }
        for(CatRepresentation cat : worldRepresentation.getCatRepresentations()) {
            cat.draw(g2d, observer);
        }
    }

    public boolean isInitialised() {
        return initialised;
    }

    public void setInitialised(boolean initialised) {
        this.initialised = initialised;
    }

    public void enterSubway(SubwayRepresantation subway) {
        return;
    }


    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == LEFT) {
            character.setRotation(0);
            dx = -speed;
        }

        if (key == RIGHT) {
            character.setRotation(180);
            dx = speed;
        }

        if (key == UP) {
            character.setRotation(90);
            dy = -speed;
        }

        if (key == DOWN) {
            character.setRotation(270);
            dy = speed;

        }
    }

    private synchronized void releaseRotation() {
        if(dy > 0) {
            character.setRotation(270);
        } else if(dy < 0) {
            character.setRotation(90);
        }
        if(dx > 0) {
            character.setRotation(180);
        } else if (dx < 0) {
            character.setRotation(0);
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




    public Image getImage() {
        return image;
    }

    protected void setImage(Image image) {
        this.image = image;
    }


    public int getSpeed() {
        return speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
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
        return character.isAlive();
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            this.writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
