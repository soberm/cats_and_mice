package at.ac.tuwien.catsandmice.client.world;

import at.ac.tuwien.catsandmice.client.characters.MousePlayer;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import at.ac.tuwien.catsandmice.dto.world.IBoundaries;
import at.ac.tuwien.catsandmice.dto.world.Subway;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;

public class SubwayRepresantation extends Subway {

    private Shape entry1;
    private Shape entry2;

    private Rectangle subwayTube;

    public SubwayRepresantation() {
        super();
    }

    public SubwayRepresantation(Subway subway) {
        this(subway.getX1(), subway.getY1(), subway.getX2(), subway.getY2(), subway.getContainedIn());
        this.setKnownCatLocations(subway.getKnownCatLocations());
        this.setContainedMice(subway.getContainedMice());
    }

    public SubwayRepresantation(int x1, int y1, int x2, int y2, IBoundaries containedIn) {
        super(x1, y1, x2, y2, containedIn);

        entry1 = new Ellipse2D.Double(
                getX1()-Constants.SUBWAY_ENTRY_WIDTH/2,
                getY1()-Constants.SUBWAY_ENTRY_WIDTH/2,
                Constants.SUBWAY_ENTRY_WIDTH,
                Constants.SUBWAY_ENTRY_WIDTH);

        entry2 = new Ellipse2D.Double(
                getX2()-Constants.SUBWAY_ENTRY_WIDTH/2,
                getY2()-Constants.SUBWAY_ENTRY_WIDTH/2,
                Constants.SUBWAY_ENTRY_WIDTH,
                Constants.SUBWAY_ENTRY_WIDTH);

        if(getX1() == getX2()) {
            subwayTube = new Rectangle(
                    this.getX1()-Constants.SUBWAY_ENTRY_WIDTH/2,
                    this.getY1(),
                    Constants.SUBWAY_ENTRY_WIDTH,
                    this.getY2()-this.getY1()
            );
        } else {
            subwayTube = new Rectangle(
                    this.getX1(),
                    this.getY1()-Constants.SUBWAY_ENTRY_WIDTH/2,
                    getX2()-getX1(),
                    Constants.SUBWAY_ENTRY_WIDTH
            );
        }
    }

    public void draw(Graphics2D g2d, ImageObserver observer){
        Paint old = g2d.getPaint();
        g2d.setPaint(new Color(Color.blue.getRed(), Color.blue.getGreen(), Color.blue.getBlue(), (getContainedMice().isEmpty() ? 30 : 100)));
        g2d.fill(subwayTube);
        g2d.setPaint(Color.black);
        g2d.fill(entry1);
        g2d.fill(entry2);
        g2d.setPaint(old);
    }

    public boolean enterOrExit(MousePlayer mouse) {
        Rectangle rectangle = mouse.getMouse().getBounds();

        if(entry1.intersects(rectangle)) {
            if (getContainedMice().contains(mouse.getMouse())) {
                exit(mouse);
            } else {
                enter(mouse, getX1(), getY1());
            }
            return true;
        } else if(entry2.intersects(rectangle)) {
            if (getContainedMice().contains(mouse.getMouse())) {
                exit(mouse);
            } else {
                enter(mouse, getX2(), getY2());
            }
            return true;
        }

        return false;
    }

    private void enter(MousePlayer mouse, int x, int y) {
        addMouse(mouse.getMouse());
        mouse.getMouse().setBoundaries(this);
        mouse.moveTo(x, y);
    }

    private void exit(MousePlayer mouse) {
        removeMouse(mouse.getMouse());
        mouse.getMouse().setBoundaries(getContainedIn());
    }

    public int getMaxWidth() {
        return subwayTube.x+ subwayTube.width + (getX1() == getX2() ? 0 : Constants.SUBWAY_ENTRY_WIDTH/2);
    }

    public int getMaxHeight() {
        return subwayTube.y+ subwayTube.height + (getY1() == getY2() ? 0 : Constants.SUBWAY_ENTRY_WIDTH/2);
    }

    public int getMinHeight() {
        return subwayTube.y  - (getY1() == getY2() ? 0 : Constants.SUBWAY_ENTRY_WIDTH/2);
    }

    public int getMinWidth() {
        return subwayTube.x - (getX1() == getX2() ? 0 : Constants.SUBWAY_ENTRY_WIDTH/2);
    }

    @Override
    public String toString() {
        return "SubwayRepresantation{} " + super.toString();
    }
}
