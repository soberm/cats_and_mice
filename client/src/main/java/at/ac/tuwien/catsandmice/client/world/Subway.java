package at.ac.tuwien.catsandmice.client.world;

import at.ac.tuwien.catsandmice.client.characters.Mouse;
import at.ac.tuwien.catsandmice.client.util.Constants;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Subway implements Boundaries {
    private int x1;
    private int y1;

    private int x2;
    private int y2;

    private Shape entry1;
    private Shape entry2;

    private Rectangle subway;

    private List<Mouse> containedMice;

    private Boundaries containedIn;

    public Subway(int x1, int y1, int x2, int y2, Boundaries containedIn) {
        containedMice = new ArrayList<Mouse>();
        this.containedIn = containedIn;

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;


        entry1 = new Ellipse2D.Double(
                x1-Constants.SUBWAY_ENTRY_WIDTH/2,
                y1-Constants.SUBWAY_ENTRY_WIDTH/2,
                Constants.SUBWAY_ENTRY_WIDTH,
                Constants.SUBWAY_ENTRY_WIDTH);

        entry2 = new Ellipse2D.Double(
                x2-Constants.SUBWAY_ENTRY_WIDTH/2,
                y2-Constants.SUBWAY_ENTRY_WIDTH/2,
                Constants.SUBWAY_ENTRY_WIDTH,
                Constants.SUBWAY_ENTRY_WIDTH);

        if(x1 == x2) {
            subway = new Rectangle(
                    this.x1-Constants.SUBWAY_ENTRY_WIDTH/2,
                    this.y1,
                    Constants.SUBWAY_ENTRY_WIDTH,
                    y2-y1
            );
        } else {
            subway = new Rectangle(
                    this.x1+Constants.SUBWAY_ENTRY_WIDTH/2,
                    this.y1+Constants.SUBWAY_ENTRY_WIDTH/2,
                    x2-x1,
                    Constants.SUBWAY_ENTRY_WIDTH
            );
        }
    }

    public void draw(Graphics2D g2d, ImageObserver observer){
        Paint old = g2d.getPaint();
        g2d.setPaint(new Color(Color.blue.getRed(), Color.blue.getGreen(), Color.blue.getBlue(), 30));
        g2d.fill(subway);
        g2d.setPaint(Color.black);
        g2d.fill(entry1);
        g2d.fill(entry2);
        g2d.setPaint(old);
    }

    public boolean enterOrExit(Mouse mouse) {
        Rectangle rectangle = mouse.getBounds();

        if(entry1.intersects(rectangle)) {
            if (containedMice.contains(mouse)) {
                exit(mouse);
            } else {
                enter(mouse, x1, y1);
            }
            return true;
        } else if(entry2.intersects(rectangle)) {
            if (containedMice.contains(mouse)) {
                exit(mouse);
            } else {
                enter(mouse, x2, y2);
            }
            return true;
        }

        return false;
    }

    private void enter(Mouse mouse, int x, int y) {
        containedMice.add(mouse);
        mouse.setBoundaries(this);
        mouse.moveTo(x, y);
    }

    private void exit(Mouse mouse) {
        containedMice.remove(mouse);
        mouse.setBoundaries(containedIn);
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public List<Mouse> getContainedMice() {
        return containedMice;
    }

    public void setContainedMice(List<Mouse> containedMice) {
        this.containedMice = containedMice;
    }

    public int getMaxWidth() {
        return subway.x+subway.width;
    }

    public int getMaxHeight() {
        return subway.y+subway.height;
    }

    public int getMinHeight() {
        return subway.y;
    }

    public int getMinWidth() {
        return subway.x;
    }
}
