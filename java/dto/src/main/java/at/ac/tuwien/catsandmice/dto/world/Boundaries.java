package at.ac.tuwien.catsandmice.dto.world;

import at.ac.tuwien.catsandmice.dto.util.Constants;
import com.google.gson.annotations.Expose;

import java.util.Objects;
import java.util.UUID;

public class Boundaries implements IBoundaries{
    @Expose
    private String uuid;

    public Boundaries() {

    }

    public void initUuid() {
        if(this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Boundaries)) return false;
        Boundaries that = (Boundaries) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid);
    }

    @Override
    public int getMaxWidth() {
        return Constants.SCREEN_WIDTH;
    }

    @Override
    public int getMaxHeight() {
        return Constants.SCREEN_HEIGHT;
    }

    @Override
    public int getMinHeight() {
        return 0;
    }

    @Override
    public int getMinWidth() {
        return 0;
    }
}
