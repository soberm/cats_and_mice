package at.ac.tuwien.catsandmice.client.characters;

import java.awt.*;
import java.awt.image.ImageObserver;

public interface Representation {

    /**
     * loads an image for the representation into the storage
     */
    void loadImage();

    /**
     * draws the player image at the location stored in the cat object
     * @param g2d not null graphics2d object to draw the cat on
     * @param observer observer used for drawing
     */
    void draw(Graphics2D g2d, ImageObserver observer);

}
