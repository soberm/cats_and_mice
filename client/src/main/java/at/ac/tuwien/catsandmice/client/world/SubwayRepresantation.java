package at.ac.tuwien.catsandmice.client.world;

import at.ac.tuwien.catsandmice.client.characters.CatRepresentation;
import at.ac.tuwien.catsandmice.client.characters.MousePlayer;
import at.ac.tuwien.catsandmice.client.characters.MouseRepresentation;
import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import at.ac.tuwien.catsandmice.dto.util.PingedExit;
import at.ac.tuwien.catsandmice.dto.world.Boundaries;
import at.ac.tuwien.catsandmice.dto.world.IBoundaries;
import at.ac.tuwien.catsandmice.dto.world.Subway;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class SubwayRepresantation extends Subway {

    private Shape entry1;
    private Shape entry2;

    private Rectangle subwayTube;

    private List<MouseRepresentation> miceRepresentations;
    private List<CatRepresentation> knownCatRepresentationLocations;

    private boolean isVertical;

    public SubwayRepresantation() {
        super();
        this.miceRepresentations = new ArrayList<>();
    }

    public SubwayRepresantation(Subway subway) {
        this(subway.getX1(), subway.getY1(), subway.getX2(), subway.getY2(), subway.getContainedIn());
        this.miceRepresentations = new ArrayList<>();
        this.knownCatRepresentationLocations = new ArrayList<>();
        this.setEnd(subway.isEnd());
        this.setUuid(subway.getUuid());
        this.setKnownCatLocations(subway.getKnownCatLocations());
        this.setContainedMice(subway.getContainedMice());
        for(Mouse mouse : getContainedMice()) {
            miceRepresentations.add(new MouseRepresentation(mouse));
        }
        for(Cat cat : subway.getKnownCatLocations()) {
            knownCatRepresentationLocations.add(new CatRepresentation(cat));
        }
    }

    public SubwayRepresantation(int x1, int y1, int x2, int y2, Boundaries containedIn) {
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
            isVertical = true;
        } else {
            subwayTube = new Rectangle(
                    this.getX1(),
                    this.getY1()-Constants.SUBWAY_ENTRY_WIDTH/2,
                    getX2()-getX1(),
                    Constants.SUBWAY_ENTRY_WIDTH
            );
            isVertical = false;
        }
    }

    public void draw(Graphics2D g2d, ImageObserver observer, boolean opaque){
        Paint old = g2d.getPaint();
        Color baseColor = isEnd() ? Color.green : Color.blue;
        g2d.setPaint(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), (opaque ? 30 : 100)));
        g2d.fill(subwayTube);
        g2d.setPaint(Color.black);
        List<String> entry1NamesList = new ArrayList<>();
        List<String> entry2NamesList = new ArrayList<>();
        if(!opaque) {
            for (Mouse mouse : getMiceRepresentations()) {
                switch (mouse.getPingedExit()) {
                    case ENTRY1:
                        entry1NamesList.add(mouse.getName());
                        break;
                    case ENTRY2:
                        entry2NamesList.add(mouse.getName());
                        break;
                }
            }
        }
        g2d.fill(entry1);
        if(!isEnd() && !entry1NamesList.isEmpty()) {
            int x = getX1() + 5 + (isVertical ? Constants.SUBWAY_ENTRY_WIDTH / 2 : 0);
            int y = getY1() + 5 + (isVertical ? 0 : Constants.SUBWAY_ENTRY_WIDTH / 2);
            for(String name : entry1NamesList) {
                y+= g2d.getFontMetrics().getHeight();
                g2d.drawString(name, x, y);
            }
        }
        g2d.fill(entry2);
        if(!isEnd() && !entry2NamesList.isEmpty()) {
            int x = getX2() + 5 + (isVertical ? Constants.SUBWAY_ENTRY_WIDTH / 2 : 0);
            int y = getY2() + 5 + (isVertical ? 0 : Constants.SUBWAY_ENTRY_WIDTH / 2);
            for(String name : entry2NamesList) {
                y+= g2d.getFontMetrics().getHeight();
                g2d.drawString(name, x, y);
            }
        }
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
        mouse.getMouse().setPingedExit(PingedExit.NONE);
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

    public List<MouseRepresentation> getMiceRepresentations() {
        return miceRepresentations;
    }

    public void setMiceRepresentations(List<MouseRepresentation> miceRepresentations) {
        this.miceRepresentations = miceRepresentations;
    }

    public List<CatRepresentation> getKnownCatRepresentationLocations() {
        return knownCatRepresentationLocations;
    }

    public void setKnownCatRepresentationLocations(List<CatRepresentation> knownCatRepresentationLocations) {
        this.knownCatRepresentationLocations = knownCatRepresentationLocations;
    }

    public PingedExit getPingedExit(Mouse mouse) {
        if(isVertical) {
            //vertical subway
            if(mouse.getRotation() % 180 != 0) {
                if(mouse.getRotation() == 90) {
                    return PingedExit.ENTRY1;
                } else {
                    return PingedExit.ENTRY2;
                }
            }
        } else {
            //horizontal subway
            if(mouse.getRotation() % 180 == 0) {
                if(mouse.getRotation() == 180) {
                    return PingedExit.ENTRY2;
                } else {
                    return PingedExit.ENTRY1;
                }
            }
        }
        return mouse.getPingedExit();
    }

    @Override
    public String toString() {
        return "SubwayRepresantation{} " + super.toString();
    }
}
