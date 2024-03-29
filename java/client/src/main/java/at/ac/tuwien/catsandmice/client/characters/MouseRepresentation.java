package at.ac.tuwien.catsandmice.client.characters;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class MouseRepresentation implements Representation {

    private static int height;
    private static int width;
    private static Image image;

    private Mouse mouse;

    public MouseRepresentation(Mouse mouse) {
        this.mouse = mouse;
        loadImage();
    }

    @Override
    public void loadImage() {
        if(image == null) {
            //all mouse representations use same image for performance reasosns
            ImageIcon ii = new ImageIcon(Board.class.getResource("/sprites/mouse.png"));
            Image original = ii.getImage();
            int height=original.getHeight(null);
            setHeight(height/5);
            int width = original.getWidth(null);
            setWidth(width/5);

            setImage(original.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
        }
        this.mouse.setHeight(height);
        this.mouse.setWidth(width);
    }

    @Override
    public void draw(Graphics2D g2d, ImageObserver observer){
        AffineTransform transformation = getTransformation();
        g2d.drawImage(getImage(), transformation, observer);
    }

    private AffineTransform getTransformation() {
        AffineTransform transformation = getRotate();
        transformation.translate(this.mouse.getX(), this.mouse.getY());
        return transformation;
    }

    private AffineTransform getRotate() {
        return AffineTransform.getRotateInstance(Math.toRadians(this.mouse.getRotation()), this.mouse.getX() + getWidth() / 2, this.mouse.getY() + getHeight() / 2);
    }

    /**
     * returns the bounds of the representation object for collision detection
     * @return an awt rectangle specifying where the represenation is currently located and what sizes it has
     */
    public Rectangle getBounds() {
        Rectangle rectangle = new Rectangle(this.mouse.getX(), this.mouse.getY(), getWidth(), getHeight());
        AffineTransform transform = getRotate();
        Shape shape = transform.createTransformedShape(rectangle);
        return shape.getBounds();
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

    public void setWidth(int w) {
        width = w;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image i) {
        image = i;
    }

    public Mouse getMouse() {
        return this.mouse;
    }
}
