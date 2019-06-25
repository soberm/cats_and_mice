package at.ac.tuwien.catsandmice.dto.characters;

import at.ac.tuwien.catsandmice.dto.world.Boundaries;
import com.google.gson.annotations.Expose;

public class Cat extends Character {

    @Expose
    private int killedMice;

    public Cat() {
        super();
    }

    public Cat(Boundaries boundaries) {
        super();
        setBoundaries(boundaries);
    }

    public int getKilledMice() {
        return killedMice;
    }

    public void setKilledMice(int killedMice) {
        this.killedMice = killedMice;
    }

    public synchronized void increaseKilledMice() {
        this.killedMice++;
    }
}
