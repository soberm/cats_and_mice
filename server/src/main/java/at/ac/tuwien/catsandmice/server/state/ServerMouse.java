package at.ac.tuwien.catsandmice.server.state;

import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.world.World;
import at.ac.tuwien.catsandmice.server.util.Constants;
import com.google.gson.annotations.Expose;

import java.io.IOException;

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
                System.out.println("mouse: " + line);
                Mouse mouse = Constants.getGson().fromJson(line, Mouse.class);
                super.update(mouse);
                /*alive = mouse.alive;
                if(!mouse.getContained().getUuid().equals(contained.getUuid())) {
                    if(contained instanceof ServerSubway) {
                        ServerSubway subway = (ServerSubway) contained;
                        subway.removeMouse(this);
                        Server world = subway.getContained();
                        if(world.getUuid().equals(mouse.getUuid())) {
                            setContained(world);
                        } else {
                            subway = world.getSubwayByUUID(mouse.getContained());
                            subway.addMouse(this);
                            setContained(subway);
                        }
                    } else if(contained instanceof Server) {
                        Server world = (Server) contained;
                        ServerSubway subway = world.getSubwayByUUID(mouse.getContained());
                        subway.addMouse(this);
                        setContained(subway);
                    }
                }*/
                System.out.println(toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
