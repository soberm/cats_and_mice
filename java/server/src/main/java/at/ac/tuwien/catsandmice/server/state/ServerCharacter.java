package at.ac.tuwien.catsandmice.server.state;

import at.ac.tuwien.catsandmice.dto.characters.Character;
import at.ac.tuwien.catsandmice.dto.world.World;

import java.io.*;
import java.net.Socket;

public abstract class ServerCharacter extends Model implements Runnable{

    protected Socket client;

    protected BufferedReader inputStream;

    protected PrintWriter printWriter;

    private Character character;

    protected Server server;

    public ServerCharacter(Character character, Server server) {
        super();
        this.server = server;
        this.character = character;
    }

    public int getX() {
        return character.getX();
    }

    public void setX(int x) {
        this.character.setX(x);
    }

    public int getY() {
        return this.character.getY();
    }

    public void setY(int y) {
        this.character.setY(y);
    }

    public int getRotation() {
        return character.getRotation();
    }

    public void setRotation(int rotation) {
        this.character.setRotation(rotation);
    }

    /**
     * update the character controlled by a client
     * @param character not null character object having x, y, width, height not null
     */
    public void update(Character character) {
        setRotation(character.getRotation());
        setX(character.getX());
        setY(character.getY());
        this.character.setWidth(character.getWidth());
        this.character.setHeight(character.getHeight());
    }

    /**
     * sets the client socket and opens a printer and a writer
     * @param client not null socket object of the client controlling the character
     */
    public void setClient(Socket client) {
        this.client = client;
        try {
            this.inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.printWriter = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ServerCharacter{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", rotation=" + getRotation()+
                '}';
    }

    public abstract void notifyClient(World world);

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
