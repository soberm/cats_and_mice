package at.ac.tuwien.catsandmice.client.characters;

import java.awt.*;
import java.awt.image.ImageObserver;

public interface Representation {

    void loadImage();

    void draw(Graphics2D g2d, ImageObserver observer);

}
