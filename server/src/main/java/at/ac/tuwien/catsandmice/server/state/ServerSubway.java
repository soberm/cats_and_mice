package at.ac.tuwien.catsandmice.server.state;

import at.ac.tuwien.catsandmice.dto.characters.Cat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerSubway extends Model{

    private Server contained;

    private int x1;
    private int y1;

    private int x2;
    private int y2;

    private List<ServerMouse> containedMice;

    private List<ServerCat> knownCats;

    private boolean goal;

    public ServerSubway(int x1, int y1, int x2, int y2) {
        super();

        containedMice = new CopyOnWriteArrayList<>();
        knownCats = new CopyOnWriteArrayList<>();

        goal = false;

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public List<ServerMouse> getContainedMice() {
        return containedMice;
    }

    public void setContainedMice(List<ServerMouse> containedMice) {
        this.containedMice = containedMice;
    }

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public void addMouse(ServerMouse mouse) {
        containedMice.add(mouse);
    }

    public void removeMouse(ServerMouse mouse) {
        containedMice.remove(mouse);
    }

    public Server getContained() {
        return contained;
    }

    public void setContained(Server contained) {
        this.contained = contained;
    }

    public void setKnownCats(List<ServerCat> knownCats) {
        knownCats.clear();
        for(ServerCat cat : knownCats) {
            ServerCat copy = new ServerCat(new Cat(), null);
            copy.setUuid(cat.getUuid());
            copy.setX(cat.getX());
            copy.setY(cat.getY());
            copy.setRotation(cat.getRotation());

            knownCats.add(cat);
        }

    }

    @Override
    public String toString() {
        return "ServerSubway{" +
                "contained=" + contained +
                ", x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }
}
