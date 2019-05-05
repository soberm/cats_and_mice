package at.ac.tuwien.catsandmice.server.computer;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.world.World;
import org.apache.commons.collections4.CollectionUtils;

import java.awt.geom.Point2D;
import java.util.List;

import static java.util.stream.Collectors.*;

public class ComputerCat implements IComputerPlayer {
    private Cat cat;
    private int speed, xMin = 0, yMin = 0;
    private double distance = Double.MAX_VALUE;

    public ComputerCat(Cat cat) {
        this.cat = cat;
        this.speed = 4;
    }

    @Override
    public void move(World world) {
        List<Mouse> activeMice = world.getMice().stream()
                .filter(Mouse::isAlive)
                .collect(toList());

        if (CollectionUtils.isEmpty(activeMice)) {
            if (Math.abs(xMin - cat.getX()) < 10 && Math.abs(yMin - cat.getY()) < 10) {
                xMin = ((int) (Math.random() * cat.getBoundaries().getMaxWidth())) + cat.getBoundaries().getMinWidth();
                yMin = ((int) (Math.random() * cat.getBoundaries().getMaxHeight())) + cat.getBoundaries().getMinHeight();
            }
        } else {
            distance = Double.MAX_VALUE;
            //find closest mouse
            for (Mouse m : activeMice) {
                if (distance > Point2D.distance(cat.getX(), cat.getY(), m.getX(), m.getY())) {
                    xMin = m.getX();
                    yMin = m.getY();
                }
            }
        }

        if (Math.abs(xMin - cat.getX()) > 4) {
            cat.setX(cat.getX() + speed * Integer.signum(xMin - cat.getX()));
            cat.setRotation(Integer.compare(xMin, cat.getX()) > 0 ? 180 : 0 );
        }

        if (Math.abs(yMin - cat.getY()) > 4) {
            cat.setY(cat.getY() + speed * Integer.signum(yMin - cat.getY()));
            cat.setRotation(Integer.compare(yMin, cat.getY()) > 0 ? 270 : 90);
        }
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getSpeed() {
        return speed;
    }
}
