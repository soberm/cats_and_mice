package at.ac.tuwien.catsandmice.client.world;

import at.ac.tuwien.catsandmice.client.characters.CatPlayer;
import at.ac.tuwien.catsandmice.client.characters.MousePlayer;

import java.util.List;

public class StateLevel {

    private List<MousePlayer> mice;
    private List<CatPlayer> cats;
    private List<SubwayRepresantation> subways;

    public List<MousePlayer> getMice() {
        return mice;
    }

    public void setMice(List<MousePlayer> mice) {
        this.mice = mice;
    }

    public List<CatPlayer> getCats() {
        return cats;
    }

    public void setCats(List<CatPlayer> cats) {
        this.cats = cats;
    }

    public List<SubwayRepresantation> getSubways() {
        return subways;
    }

    public void setSubways(List<SubwayRepresantation> subways) {
        this.subways = subways;
    }
}
