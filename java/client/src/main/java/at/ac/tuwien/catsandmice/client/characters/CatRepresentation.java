package at.ac.tuwien.catsandmice.client.characters;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.dto.characters.Cat;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class CatRepresentation implements Representation {

    private Cat cat;

    private static int height;
    private static int width;
    private static Image image;

    public CatRepresentation(Cat cat) {
        this.cat = cat;
        loadImage();
    }

    @Override
    public void loadImage() {
        if(image == null) {
            //cat image is only loaded once and used for every cat representation for performance reasons
            ImageIcon ii = new ImageIcon(Board.class.getResource("/sprites/cat.png"));
            Image original = ii.getImage();
            int height = original.getHeight(null);
            setHeight(height / 5);
            int width = original.getWidth(null);
            setWidth(width / 5);
            setImage(original.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
        }
        this.cat.setHeight(height);
        this.cat.setWidth(width);
    }

    @Override
    public void draw(Graphics2D g2d, ImageObserver observer){
        AffineTransform transformation = getTransformation();
        g2d.drawImage(getImage(), transformation, observer);
    }

    private AffineTransform getTransformation() {
        AffineTransform transformation = getRotate();
        transformation.translate(this.cat.getX(), this.cat.getY());
        return transformation;
    }

    private AffineTransform getRotate() {
        return AffineTransform.getRotateInstance(Math.toRadians(this.cat.getRotation()), this.cat.getX() + getWidth() / 2, this.cat.getY() + getHeight() / 2);
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int h) {
        height = h;
    }

    public int getWidth() {
        return width;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image i) {
        image = i;
    }

    public void setWidth(int w) {
        width = w;
    }

    public Cat getCat() {
        return this.cat;
    }
}
