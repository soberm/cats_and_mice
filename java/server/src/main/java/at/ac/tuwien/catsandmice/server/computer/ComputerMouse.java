package at.ac.tuwien.catsandmice.server.computer;

import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.PingedExit;
import at.ac.tuwien.catsandmice.dto.world.Subway;
import at.ac.tuwien.catsandmice.dto.world.World;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ComputerMouse implements IComputerPlayer{
    private Mouse mouse;
    private Subway currentSub, nextSub;
    private int speed, xDest, yDest;
    private Queue<Point> steps;
    private List<Subway> visitedSubways = new ArrayList<>();

    public ComputerMouse(Mouse mouse, Subway currentSub) {
        this.mouse = mouse;
        this.xDest = mouse.getX();
        this.yDest = mouse.getY();
        this.currentSub = currentSub;
        this.nextSub = currentSub;
        visitedSubways = new ArrayList<>();
        steps = new LinkedList<>();
        this.speed = 3;
    }

    @Override
    public void move(World world) {
        if (!mouse.isAlive() || (currentSub != null && currentSub.isEnd())) {
            return;
        }

        if (Math.abs(xDest - mouse.getX()) < 5 && Math.abs(yDest - mouse.getY()) < 5) {
            // enter or exit subway
            if (currentSub == null) {
                // enter subway
                world.removeMouse(mouse);
                nextSub.getContainedMice().add(mouse);
                nextSub.setKnownCatLocations(world.getCats());
                mouse.setBoundaries(nextSub);
            } else {
                if (nextSub == null) {
                    // exit subway
                    currentSub.removeMouse(mouse);
                    world.addMouse(mouse);
                    mouse.setBoundaries(world);
                }
            }

            currentSub = nextSub;

            if (steps.isEmpty()) {
                // figure out where to go- this is always the case when a mouse is in a subway
                // add three points to the queue: next exit, next entrance, middle of the subway

                // leave current subway at random exit
                if (Math.random() < 0.5) {
                    steps.add(new Point(currentSub.getX1(), currentSub.getY1(), null));
                } else {
                    steps.add(new Point(currentSub.getX2(), currentSub.getY2(), null));
                }

                // calculate closest subway entrance
                Point nextPoint = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE, null);

                for (Subway s : world.getSubways()) {
                    if (s.getUuid().equals(currentSub.getUuid()) ||visitedSubways.contains(s)) {
                        continue;
                    }

                    nextPoint = pointSelectionHelper(s.getX1(), s.getY1(), s, nextPoint);
                    nextPoint = pointSelectionHelper(s.getX2(), s.getY2(), s, nextPoint);
                }

                steps.add(nextPoint);

                // let mouse walk to the middle of the next subway
                steps.add(calculateSubwayCenter(nextPoint));
                visitedSubways.add(nextPoint.getSubway());
            }


            // get next dest from queue
            Point tmp = steps.remove();
            xDest = tmp.getX();
            yDest = tmp.getY();
            nextSub = tmp.getSubway();
            if(currentSub != null) {
                if (currentSub.getX1() == currentSub.getX2()) {
                    //vertical subway
                    if (mouse.getRotation() % 180 != 0) {
                        if (mouse.getRotation() == 90) {
                            mouse.setPingedExit(PingedExit.ENTRY1);
                        } else {
                            mouse.setPingedExit(PingedExit.ENTRY2);
                        }
                    }
                } else {
                    //horizontal subway
                    if (mouse.getRotation() % 180 == 0) {
                        if (mouse.getRotation() == 180) {
                            mouse.setPingedExit(PingedExit.ENTRY2);
                        } else {
                            mouse.setPingedExit(PingedExit.ENTRY1);
                        }
                    }
                }
            }
        } else {
            // move to xDest yDest
            if (Math.abs(xDest - mouse.getX()) > 4) {
                mouse.setX(mouse.getX() + speed * Integer.signum(xDest - mouse.getX()));
                mouse.setRotation(Integer.compare(xDest, mouse.getX()) > 0 ? 180 : 0 );
            }

            if (Math.abs(yDest - mouse.getY()) > 4) {
                mouse.setY(mouse.getY() + speed * Integer.signum(yDest - mouse.getY()));
                mouse.setRotation(Integer.compare(yDest, mouse.getY()) > 0 ? 270 : 90);
            }
        }
    }

    public Mouse getMouse() {
        return mouse;
    }


    public Subway getCurrentSub() {
        return currentSub;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    private Point pointSelectionHelper(int x, int y, Subway subway, Point curr) {
        return Point2D.distance(xDest, yDest, curr.getX(), curr.getY()) > Point2D.distance(xDest, yDest, x, y)
                ? new Point(x, y, subway) : curr;
    }

    private Point calculateSubwayCenter(Point p) {
        Subway s = p.getSubway();
        int x = s.getX1() == s.getX2() ? s.getX1() : (s.getX1() + s.getX2()) / 2;
        int y = s.getY1() == s.getY1() ? s.getY1() : (s.getY1() + s.getY2()) / 2;

        return new Point(x, y, s);
    }
}
