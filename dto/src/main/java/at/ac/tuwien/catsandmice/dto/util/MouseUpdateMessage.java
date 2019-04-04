package at.ac.tuwien.catsandmice.dto.util;

import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import com.google.gson.annotations.Expose;

public class MouseUpdateMessage {
    @Expose
    private Mouse mouse;
    @Expose
    private String containedInUUID;

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public String getContainedInUUID() {
        return containedInUUID;
    }

    public void setContainedInUUID(String containedInUUID) {
        this.containedInUUID = containedInUUID;
    }

    @Override
    public String toString() {
        return "MouseUpdateMessage{" +
                "mouse=" + mouse +
                ", containedInUUID='" + containedInUUID + '\'' +
                '}';
    }
}
