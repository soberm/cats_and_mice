package at.ac.tuwien.catsandmice.server.state;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Character;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.world.World;
import at.ac.tuwien.catsandmice.server.util.ServerConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class ServerCat extends ServerCharacter {

    private Cat cat;

    public ServerCat(Cat cat,Server server) {
        super(cat, server);
        this.cat = cat;
    }

    public void run() {
        String line = null;
        try {
            while ((line = inputStream.readLine()) != null) {
                Cat cat = ServerConstants.getGson().fromJson(line, Cat.class);
                super.update(cat);
            }
        } catch (IOException e) {
            e.printStackTrace();
            server.removePlayer(this);
        }
    }

    private AffineTransform getRotate(Character character) {
        return AffineTransform.getRotateInstance(Math.toRadians(character.getRotation()), character.getX() + character.getWidth() / 2, character.getY() + character.getHeight() / 2);
    }


    private Rectangle getBounds(Character character) {
        Rectangle rectangle = new Rectangle(character.getX(), character.getY(), character.getWidth(), character.getHeight());
        AffineTransform transform = getRotate(character);
        Shape shape = transform.createTransformedShape(rectangle);
        return shape.getBounds();
    }

    /**
     * checks wheter a cat and a mouse collide
     * @param mouse not null mouse object to check if collision exists and cat eats the given mouse
     */
    public void kill(Mouse mouse) {
        if (mouse.getBoundaries().equals(cat.getBoundaries()) ) {
            if(mouse.isAlive() && getBounds(cat).intersects(getBounds(mouse))) {
                mouse.setAlive(false);
                cat.increaseKilledMice();
            }
        }
    }

    @Override
    public synchronized void notifyClient(World world) {
        super.printWriter.println(ServerConstants.getGson().toJson(world));
        super.printWriter.flush();
    }

    public Cat getCat() {
        return this.cat;
    }
}
