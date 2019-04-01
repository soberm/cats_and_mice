package at.ac.tuwien.catsandmice.server.state;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.world.World;
import at.ac.tuwien.catsandmice.server.util.Constants;

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
                System.out.println("cat: " + line);
                Cat cat = Constants.getGson().fromJson(line, Cat.class);
                super.update(cat);
                System.out.println(toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
