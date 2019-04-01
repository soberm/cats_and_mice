package at.ac.tuwien.catsandmice.client.characters;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import at.ac.tuwien.catsandmice.dto.world.IBoundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class MouseRepresentation extends Mouse implements Representation {

    private int height;
    private int width;
    private Image image;

    public MouseRepresentation() {
        loadImage();
    }

    public MouseRepresentation(IBoundaries boundaries) {
        super(boundaries);
        loadImage();
    }

    @Override
    public void loadImage() {
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
        AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(getRotation()), getX() + getWidth() / 2, getY() + getHeight() / 2);
        return transform;
    }

    public Rectangle getBounds() {
        Rectangle rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        AffineTransform transform = getRotate();
        Shape shape = transform.createTransformedShape(rectangle);
        return shape.getBounds();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
