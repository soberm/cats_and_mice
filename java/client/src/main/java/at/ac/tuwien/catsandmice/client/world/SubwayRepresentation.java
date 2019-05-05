package at.ac.tuwien.catsandmice.client.world;

import at.ac.tuwien.catsandmice.client.characters.CatRepresentation;
import at.ac.tuwien.catsandmice.client.characters.MousePlayer;
import at.ac.tuwien.catsandmice.client.characters.MouseRepresentation;
import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import at.ac.tuwien.catsandmice.dto.util.PingedExit;
import at.ac.tuwien.catsandmice.dto.world.Subway;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class SubwayRepresentation {

    private Subway subway;

    private Shape entry1;
    private Shape entry2;

    private Rectangle subwayTube;

    private List<MouseRepresentation> miceRepresentations;
    private List<CatRepresentation> knownCatRepresentationLocations;

    private boolean isVertical;

    public SubwayRepresentation(Subway subway) {
        this.miceRepresentations = new ArrayList<>();
        this.knownCatRepresentationLocations = new ArrayList<>();
        this.subway = subway;
        for(Mouse mouse : subway.getContainedMice()) {
            miceRepresentations.add(new MouseRepresentation(mouse));
        }
        for(Cat cat : subway.getKnownCatLocations()) {
            knownCatRepresentationLocations.add(new CatRepresentation(cat));
        }
        entry1 = new Ellipse2D.Double(
                subway.getX1()-Constants.SUBWAY_ENTRY_WIDTH/2,
                subway.getY1()-Constants.SUBWAY_ENTRY_WIDTH/2,
                Constants.SUBWAY_ENTRY_WIDTH,
                Constants.SUBWAY_ENTRY_WIDTH);

        entry2 = new Ellipse2D.Double(
                subway.getX2()-Constants.SUBWAY_ENTRY_WIDTH/2,
                subway.getY2()-Constants.SUBWAY_ENTRY_WIDTH/2,
                Constants.SUBWAY_ENTRY_WIDTH,
                Constants.SUBWAY_ENTRY_WIDTH);

        if(subway.getX1() == subway.getX2()) {
            subwayTube = new Rectangle(
                    this.subway.getX1()-Constants.SUBWAY_ENTRY_WIDTH/2,
                    this.subway.getY1(),
                    Constants.SUBWAY_ENTRY_WIDTH,
                    this.subway.getY2()-this.subway.getY1()
            );
            isVertical = true;
        } else {
            subwayTube = new Rectangle(
                    this.subway.getX1(),
                    this.subway.getY1()-Constants.SUBWAY_ENTRY_WIDTH/2,
                    subway.getX2()-subway.getX1(),
                    Constants.SUBWAY_ENTRY_WIDTH
            );
            isVertical = false;
        }
    }

    public void draw(Graphics2D g2d, ImageObserver observer, boolean opaque){
        Paint old = g2d.getPaint();
        Color baseColor = subway.isEnd() ? Color.green : Color.blue;
        g2d.setPaint(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), (opaque ? 30 : 100)));
        g2d.fill(subwayTube);
        g2d.setPaint(Color.black);
        List<String> entry1NamesList = new ArrayList<>();
        List<String> entry2NamesList = new ArrayList<>();
        if(!opaque) {
            for (Mouse mouse : subway.getContainedMice()) {
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
        if(!subway.isEnd() && !entry1NamesList.isEmpty()) {
            int x = subway.getX1() + 5 + (isVertical ? Constants.SUBWAY_ENTRY_WIDTH / 2 : 0);
            int y = subway.getY1() + 5 + (isVertical ? 0 : Constants.SUBWAY_ENTRY_WIDTH / 2);
            for(String name : entry1NamesList) {
                y+= g2d.getFontMetrics().getHeight();
                g2d.drawString(name, x, y);
            }
        }
        g2d.fill(entry2);
        if(!subway.isEnd() && !entry2NamesList.isEmpty()) {
            int x = subway.getX2() + 5 + (isVertical ? Constants.SUBWAY_ENTRY_WIDTH / 2 : 0);
            int y = subway.getY2() + 5 + (isVertical ? 0 : Constants.SUBWAY_ENTRY_WIDTH / 2);
            for(String name : entry2NamesList) {
                y+= g2d.getFontMetrics().getHeight();
                g2d.drawString(name, x, y);
            }
        }
        g2d.setPaint(old);
    }

    public boolean enterOrExit(MousePlayer mouse) {
        Rectangle rectangle = mouse.getMouseRepresentation().getBounds();

        if(entry1.intersects(rectangle)) {
            if (subway.getContainedMice().contains(mouse.getMouseRepresentation().getMouse())) {
                exit(mouse);
            } else {
                enter(mouse, subway.getX1(), subway.getY1());
            }
            return true;
        } else if(entry2.intersects(rectangle)) {
            if (subway.getContainedMice().contains(mouse.getMouseRepresentation().getMouse())) {
                exit(mouse);
            } else {
                enter(mouse, subway.getX2(), subway.getY2());
            }
            return true;
        }

        return false;
    }

    private void enter(MousePlayer mouse, int x, int y) {
        subway.addMouse(mouse.getMouseRepresentation().getMouse());
        mouse.getMouseRepresentation().getMouse().setBoundaries(subway);
        mouse.moveTo(x, y);
    }

    private void exit(MousePlayer mouse) {
        subway.removeMouse(mouse.getMouseRepresentation().getMouse());
        mouse.getMouseRepresentation().getMouse().setBoundaries(subway.getContainedIn());
        mouse.getMouseRepresentation().getMouse().setPingedExit(PingedExit.NONE);
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
        return "SubwayRepresentation{} " + super.toString();
    }
}
