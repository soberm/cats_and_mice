package at.ac.tuwien.catsandmice.dto.world;

import com.google.gson.annotations.Expose;

import java.util.Objects;
import java.util.UUID;

public abstract class Boundaries implements IBoundaries{
    @Expose
    private String uuid;

    public Boundaries() {
        this.uuid = UUID.randomUUID().toString();
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
        if (o == null || getClass() != o.getClass()) return false;
        Boundaries that = (Boundaries) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid);
    }
}
