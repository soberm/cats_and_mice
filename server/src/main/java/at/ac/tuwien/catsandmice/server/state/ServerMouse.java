package at.ac.tuwien.catsandmice.server.state;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.MouseUpdateMessage;
import at.ac.tuwien.catsandmice.dto.world.Subway;
import at.ac.tuwien.catsandmice.dto.world.World;
import at.ac.tuwien.catsandmice.server.util.Constants;
import com.google.gson.annotations.Expose;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServerMouse extends ServerCharacter {

    @Expose
    private Model contained;

    @Expose
    private boolean alive = true;

    private Mouse mouse;

    public ServerMouse(Mouse mouse) {
        super(mouse);
        this.mouse = mouse;
    }


    public Model getContained() {
        return contained;
    }

    public void setContained(Model contained) {
        this.contained = contained;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void run() {
        String line = null;
        try {
            while (null != (line = inputStream.readLine())) {
                MouseUpdateMessage mouseUpdateMessage =  Constants.getGson().fromJson(line, MouseUpdateMessage.class);
                Mouse mouse = mouseUpdateMessage.getMouse();
                super.update(mouse);
                if(!mouseUpdateMessage.getContainedInUUID().equals(this.mouse.getBoundaries().getUuid())) {
                    if(this.mouse.getBoundaries() instanceof World) {
                        World world = (World) this.mouse.getBoundaries();
                        List<Subway> subwayList = world.getSubways().stream().filter(subway -> subway.getUuid().equals(mouseUpdateMessage.getContainedInUUID())).collect(Collectors.toList());
                        if(!subwayList.isEmpty()) {
                            world.removeMouse(this.mouse);
                            this.mouse.setBoundaries(subwayList.get(0));
                            subwayList.get(0).addMouse(this.mouse);
                        }
                    } else {
                        Subway subway = (Subway) this.mouse.getBoundaries();
                        subway.removeMouse(this.mouse);
                        World world = (World) subway.getContainedIn();
                        this.mouse.setBoundaries(world);
                        world.addMouse(this.mouse);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private AffineTransform getRotate() {
        return AffineTransform.getRotateInstance(Math.toRadians(getRotation()), getX() + mouse.getWidth() / 2, getY() + mouse.getHeight() / 2);
    }

    public Rectangle getBounds() {
        Rectangle rectangle = new Rectangle(getX(), getY(), mouse.getWidth(), mouse.getHeight());
        AffineTransform transform = getRotate();
        Shape shape = transform.createTransformedShape(rectangle);
        return shape.getBounds();
    }

    @Override
    public String toString() {
        return "ServerMouse{" +
                super.toString() + ", " +
                "contained=" + contained +
                ", alive=" + alive +
                '}';
    }

    @Override
    public void notifyClient(World world) {
        super.printWriter.println(Constants.getGson().toJson(world));
        super.printWriter.flush();
    }
}
