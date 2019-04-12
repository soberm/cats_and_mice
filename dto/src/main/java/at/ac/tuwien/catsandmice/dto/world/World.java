package at.ac.tuwien.catsandmice.dto.world;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class World extends Boundaries {

    @Expose
    private List<Cat> cats;
    @Expose
    private List<Mouse> mice;
    @Expose
    private List<Subway> subways;

    public World() {
        super();
        this.cats = new CopyOnWriteArrayList<>();
        this.mice = new CopyOnWriteArrayList<>();
        this.subways = new CopyOnWriteArrayList<>();
    }


    public void addCat(Cat cat) {
        cats.add(cat);
    }

    public void removeCat(Cat cat) {
        cats.remove(cat);
    }

    public void addMouse(Mouse mouse) {
        mice.add(mouse);
    }

    public void removeMouse(Mouse mouse) {
        mice.remove(mouse);
    }

    public void addSubway(Subway sub) {
        subways.add(sub);
        sub.setContainedIn(this);
    }

    public void removeSubway(Subway subway) {
        subways.remove(subway);
    }

    public int getMaxWidth() {
        return Constants.SCREEN_WIDTH;
    }

    public int getMaxHeight() {
        return Constants.SCREEN_HEIGHT;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
        for(Cat cat : cats) {
            cat.setBoundaries(this);
        }
    }

    public List<Mouse> getMice() {
        return mice;
    }

    public void setMice(List<Mouse> mice) {
        this.mice = mice;
    }

    public List<Subway> getSubways() {
        return subways;
    }

    public void setSubways(List<Subway> subways) {
        this.subways = subways;
    }

    public int getMinHeight() {
        return 0;
    }

    public int getMinWidth() {
        return 0;
    }

    @Override
    public String toString() {
        return "World{" +
                "cats=" + cats +
                ", mice=" + mice +
                ", subways=" + subways +
                '}';
    }
}
