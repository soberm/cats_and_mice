package at.ac.tuwien.catsandmice.client.characters;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.world.Boundaries;
import at.ac.tuwien.catsandmice.dto.world.IBoundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class CatRepresentation extends Cat implements Representation {

    private static int height;
    private static int width;
    private static Image image;

    public CatRepresentation() {
        loadImage();
    }

    public CatRepresentation(Boundaries boundaries) {
        super(boundaries);
        loadImage();
    }

    @Override
    public void loadImage() {
        if(image == null) {
            ImageIcon ii = new ImageIcon(Board.class.getResource("/sprites/cat.png"));
            Image original = ii.getImage();
            int height = original.getHeight(null);
            setHeight(height / 5);
            int width = original.getWidth(null);
            setWidth(width / 5);
            setImage(original.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
        }
    }

    @Override
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
        return AffineTransform.getRotateInstance(Math.toRadians(getRotation()), getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public Rectangle getBounds() {
        Rectangle rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        AffineTransform transform = getRotate();
        Shape shape = transform.createTransformedShape(rectangle);
        return shape.getBounds();
    }

    public void kill(MouseRepresentation mouse) {
//        if (mouse.getBoundaries().equals(getBoundaries()) ) {
//            System.out.println(mouse.getBounds());
//            System.out.println(getBounds());
//            System.out.println(getBounds().intersects(mouse.getBounds()));
//            if(getBounds().intersects(mouse.getBounds()))
//                mouse.setAlive(false);
//        }
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
}
