package at.ac.tuwien.catsandmice.client.world;

import at.ac.tuwien.catsandmice.client.characters.Cat;
import at.ac.tuwien.catsandmice.client.characters.Mouse;

import java.util.List;

public class StateLevel {

    private List<Mouse> mice;
    private List<Cat> cats;
    private List<Subway> subways;

    public List<Mouse> getMice() {
        return mice;
    }

    public void setMice(List<Mouse> mice) {
        this.mice = mice;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    public List<Subway> getSubways() {
        return subways;
    }

    public void setSubways(List<Subway> subways) {
        this.subways = subways;
    }
}
