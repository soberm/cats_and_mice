package at.ac.tuwien.catsandmice.dto.world;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class World extends Boundaries {

    @Expose
    private List<Cat> cats;
    @Expose
    private List<Mouse> mice;
    @Expose
    private List<Subway> subways;

    public World() {
        super();
        this.cats = new ArrayList<>();
        this.mice = new ArrayList<>();
        this.subways = new ArrayList<>();
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
