package at.ac.tuwien.catsandmice.client.world;

import at.ac.tuwien.catsandmice.client.characters.CatRepresentation;
import at.ac.tuwien.catsandmice.client.characters.MouseRepresentation;
import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.world.Subway;
import at.ac.tuwien.catsandmice.dto.world.World;

import java.util.ArrayList;
import java.util.List;

public class WorldRepresentation extends World {
    private List<CatRepresentation> cats;
    private List<MouseRepresentation> mice;
    private List<SubwayRepresantation> subwayRepresantations;

    public WorldRepresentation() {
        super();
        this.cats = new ArrayList<>();
        this.mice = new ArrayList<>();
        this.subwayRepresantations = new ArrayList<>();
    }

    public void addCat(CatRepresentation cat) {
        this.cats.add(cat);
    }

    public void removeCat(CatRepresentation cat) {
        this.cats.remove(cat);
    }

    public void addMouse(MouseRepresentation mouse) {
        this.mice.add(mouse);
    }

    public void removeMouse(MouseRepresentation mouse) {
        this.mice.remove(mouse);
    }

    public List<CatRepresentation> getCatRepresentations() {
        return cats;
    }

    public void setCatRepresentations(List<CatRepresentation> cats) {
        this.cats = cats;
    }

    public List<MouseRepresentation> getMouseRepresentations() {
        return mice;
    }

    public void setMouseRepresentations(List<MouseRepresentation> mice) {
        this.mice = mice;
        for(Mouse mouse : mice) {
            mouse.setBoundaries(this);
        }
    }

    public List<SubwayRepresantation> getSubwayRepresantations() {
        return subwayRepresantations;
    }

    public void setSubwayRepresantations(List<SubwayRepresantation> subwayRepresantations) {
        this.subwayRepresantations = subwayRepresantations;
        for(Subway subway : subwayRepresantations) {
            subway.setContainedIn(this);
        }
    }

    @Override
    public String toString() {
        return "WorldRepresentation{" +
                "cats=" + cats +
                ", mice=" + mice +
                ", subwayRepresantations=" + subwayRepresantations +
                '}' + super.toString();
    }
}
