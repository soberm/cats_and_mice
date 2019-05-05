package at.ac.tuwien.catsandmice.dto.util;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import com.google.gson.annotations.Expose;

public class LoginMessage {
    @Expose
    private Cat cat;
    @Expose
    private Mouse mouse;

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
}
