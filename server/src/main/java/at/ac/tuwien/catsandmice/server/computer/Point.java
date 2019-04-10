package at.ac.tuwien.catsandmice.server.computer;

import at.ac.tuwien.catsandmice.dto.world.Subway;

public class Point {
    private int x;
    private int y;

    private Subway subway;

    public Point(int x, int y, Subway subway) {
        this.x = x;
        this.y = y;
        this.subway = subway;
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

    public Subway getSubway() {
        return subway;
    }

    public void setSubway(Subway subway) {
        this.subway = subway;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", subway=" + subway +
                '}';
    }
}
