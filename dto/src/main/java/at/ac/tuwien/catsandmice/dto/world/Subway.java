package at.ac.tuwien.catsandmice.dto.world;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Subway extends Boundaries {
    @Expose
    private int x1;
    @Expose
    private int y1;

    @Expose
    private int x2;
    @Expose
    private int y2;


    @Expose
    private List<Mouse> containedMice;

    @Expose
    private List<Cat> knownCatLocations;

    private Boundaries containedIn;

    public Subway(int x1, int y1, int x2, int y2, Boundaries containedIn) {
        super();
        containedMice = new ArrayList<Mouse>();
        knownCatLocations = new ArrayList<Cat>();
        this.containedIn = containedIn;

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Subway() {
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
        for(Mouse mouse : containedMice) {
            mouse.setBoundaries(this);
        }
    }

    public int getMaxWidth() {
        return x1 + (x1 == x2 ? 0 : (Constants.SUBWAY_ENTRY_WIDTH/2 + x2-x1));
    }

    public int getMaxHeight() {
        return y1 + (y1 == y2 ? 0 : (Constants.SUBWAY_ENTRY_WIDTH/2 + y2-y1));
    }

    public int getMinHeight() {
        return y1  - (y1 == y2 ? 0 : Constants.SUBWAY_ENTRY_WIDTH/2);
    }

    public int getMinWidth() {
        return x1 - (x1 == x2 ? 0 : Constants.SUBWAY_ENTRY_WIDTH/2);
    }

    public Boundaries getContainedIn() {
        return containedIn;
    }

    public void setContainedIn(Boundaries containedIn) {
        this.containedIn = containedIn;
    }

    public List<Cat> getKnownCatLocations() {
        return knownCatLocations;
    }

    public void setKnownCatLocations(List<Cat> knownCatLocations) {
        this.knownCatLocations = knownCatLocations;
    }

    public void addMouse(Mouse mouse) {
        this.containedMice.add(mouse);

    }

    public void removeMouse(Mouse mouse) {
        this.containedMice.remove(mouse);
    }

    @Override
    public String toString() {
        return "Subway{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", uuid=" + getUuid() +
                '}';
    }
}
