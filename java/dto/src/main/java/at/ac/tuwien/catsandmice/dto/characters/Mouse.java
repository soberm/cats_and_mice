package at.ac.tuwien.catsandmice.dto.characters;

import at.ac.tuwien.catsandmice.dto.util.PingedExit;
import at.ac.tuwien.catsandmice.dto.world.Boundaries;
import com.google.gson.annotations.Expose;


public class Mouse extends Character {

    @Expose
    private String name;

    @Expose
    private PingedExit pingedExit = PingedExit.NONE;

/*    public Mouse() {
        super();
    }*/

    public Mouse(String name) {
        super();
        this.name = name;
    }

    public Mouse(String name, Boundaries boundaries) {
        super();
        this.name = name;
        setBoundaries(boundaries);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PingedExit getPingedExit() {
        return pingedExit;
    }

    public void setPingedExit(PingedExit pingedExit) {
        this.pingedExit = pingedExit;
    }

    @Override
    public String toString() {
        return "Mouse{" +
                "name='" + name + '\'' +
                super.toString() +
                "} " + super.toString();
    }
}
