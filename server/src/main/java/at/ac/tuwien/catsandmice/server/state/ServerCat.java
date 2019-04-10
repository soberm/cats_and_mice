package at.ac.tuwien.catsandmice.server.state;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Character;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.world.World;
import at.ac.tuwien.catsandmice.server.util.Constants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerCat extends ServerCharacter {

    private Cat cat;

    public ServerCat(Cat cat) {
        super(cat);
        this.cat = cat;
    }

    public void run() {
        String line = null;
        try {
            while ((line = inputStream.readLine()) != null) {
                Cat cat = Constants.getGson().fromJson(line, Cat.class);
                super.update(cat);
/*                World world = (World) this.cat.getBoundaries();
                for(Mouse mouse : world.getMice()) {
                    kill(mouse);
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AffineTransform getRotate(Character character) {
        return AffineTransform.getRotateInstance(Math.toRadians(character.getRotation()), character.getX() + character.getWidth() / 2, character.getY() + character.getHeight() / 2);
    }

    public Rectangle getBounds(Character character) {
        Rectangle rectangle = new Rectangle(character.getX(), character.getY(), character.getWidth(), character.getHeight());
        AffineTransform transform = getRotate(character);
        Shape shape = transform.createTransformedShape(rectangle);
        return shape.getBounds();
    }

    public void kill(Mouse mouse) {
        if (mouse.getBoundaries().equals(cat.getBoundaries()) ) {
            if(mouse.isAlive() && getBounds(cat).intersects(getBounds(mouse))) {
                mouse.setAlive(false);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void notifyClient(World world) {
        super.printWriter.println(Constants.getGson().toJson(world));
        super.printWriter.flush();
    }
}
