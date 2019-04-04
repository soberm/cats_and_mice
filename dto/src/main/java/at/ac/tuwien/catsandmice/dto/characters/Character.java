package at.ac.tuwien.catsandmice.dto.characters;

import at.ac.tuwien.catsandmice.dto.world.Boundaries;
import at.ac.tuwien.catsandmice.dto.world.IBoundaries;
import com.google.gson.annotations.Expose;

import java.util.Objects;
import java.util.UUID;

public abstract class Character {
    @Expose
    private String uuid;

    @Expose
    private boolean alive = true;

    @Expose
    private int x = 0;
    @Expose
    private int y = 0;

    @Expose
    private int width = 0;
    @Expose
    private int height = 0;

    @Expose
    private int rotation = 0;

    private Boundaries boundaries;

    public Character() {
        this.uuid = UUID.randomUUID().toString();
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public Boundaries getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(Boundaries boundaries) {
        this.boundaries = boundaries;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
        return Objects.equals(uuid, character.uuid);
    }



    @Override
    public int hashCode() {

        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Character{" +
                "uuid='" + uuid + '\'' +
                ", alive=" + alive +
                ", x=" + x +
                ", y=" + y +
                ", rotation=" + rotation +
                '}';
    }
}
