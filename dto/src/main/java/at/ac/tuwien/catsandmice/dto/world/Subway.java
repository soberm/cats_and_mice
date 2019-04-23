package at.ac.tuwien.catsandmice.dto.world;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private boolean isEnd;

    @Expose
    private List<Mouse> containedMice;

    @Expose
    private final List<Cat> knownCatLocations;

    private Boundaries containedIn;

    public Subway(int x1, int y1, int x2, int y2, Boundaries containedIn) {
        super();
        containedMice = new CopyOnWriteArrayList<>();
        knownCatLocations = new CopyOnWriteArrayList<>();
        this.containedIn = containedIn;

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Subway() {
        containedMice = new CopyOnWriteArrayList<>();
        knownCatLocations = new CopyOnWriteArrayList<>();
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

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public List<Mouse> getContainedMice() {
        return containedMice;
    }

    public void setContainedMice(List<Mouse> containedMice) {
        this.containedMice = containedMice;
        for (Mouse mouse : containedMice) {
            mouse.setBoundaries(this);
        }

    }

    public int getMaxWidth() {
        return x2 + Constants.SUBWAY_ENTRY_WIDTH / 2;
    }

    public int getMaxHeight() {
        return y2 + Constants.SUBWAY_ENTRY_WIDTH / 2;
    }

    public int getMinHeight() {
        return y1 - Constants.SUBWAY_ENTRY_WIDTH / 2;
    }

    public int getMinWidth() {
        return x1 - Constants.SUBWAY_ENTRY_WIDTH / 2;
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
        this.knownCatLocations.clear();
        synchronized (this.knownCatLocations) {
            for (Cat cat : knownCatLocations) {
                Cat copy = new Cat();
                copy.setUuid(cat.getUuid());
                copy.setX(cat.getX());
                copy.setY(cat.getY());
                copy.setRotation(cat.getRotation());

                this.knownCatLocations.add(copy);
            }
        }
    }

    public void addMouse(Mouse mouse) {
        this.containedMice.add(mouse);
        mouse.setBoundaries(this);
    }

    public void removeMouse(Mouse mouse) {
        this.containedMice.remove(mouse);
        mouse.setBoundaries(this.containedIn);
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
