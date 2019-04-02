package at.ac.tuwien.catsandmice.server.state;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.world.Subway;
import at.ac.tuwien.catsandmice.dto.world.World;
import at.ac.tuwien.catsandmice.server.util.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Server implements Runnable {

    private World world;
    private List<ServerCharacter> allCharacters = new ArrayList<>();

    private ServerSocket socket;

    public Server() {
        super();
        initWorld();
    }

    private void initWorld() {
        this.world = new World();
        Subway sub = new Subway(300, 300, 300, 800, world);
        world.addSubway(sub);

        sub = new Subway(500, 900, 900, 900, world);

        world.addSubway(sub);

        sub = new Subway(700, 450, 1000, 450, world);

        world.addSubway(sub);
    }

    public void login(Cat cat, Socket socket) {
        if(cat != null) {
            ServerCat serverCat = new ServerCat(cat);
            world.addCat(cat);
            allCharacters.add(serverCat);
            startReading(serverCat, socket);

        }
    }

    public void login(Mouse mouse, Socket socket) {
        if(mouse != null) {
            /*if(mouse.getContained().getUuid().equals(this.getUuid())) {
                mouse.setContained(world);
            } else {
                mouse.setContained(getSubwayByUUID(mouse.getContained()));
            }*/
            ServerMouse serverMouse = new ServerMouse(mouse);
            world.addMouse(mouse);
            allCharacters.add(serverMouse);
            startReading(serverMouse, socket);
        }
    }

    public void startReading(ServerCharacter character, Socket socket) {
        character.setClient(socket);
        new Thread(character).start();
    }



    @Override
    public void run() {
        new Thread(new LoginService()).start();
        new Thread(new UpdateService()).start();

    }

    private class LoginService implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(2222);
                while(true) {
                    try {
                        Socket socket = serverSocket.accept();
                        System.out.println("got connection");
                        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter writer = new PrintWriter(socket.getOutputStream());

                        String loginMessage = inputStream.readLine();
                        System.out.println("login: " + loginMessage);
                        writer.println(Constants.getGson().toJson(world));
                        writer.flush();
                        if (!(loginMessage == null || loginMessage.isEmpty())) {
                            Message message = Constants.getGson().fromJson(loginMessage, Message.class);
                            login(message.getCat(), socket);
                            login(message.getMouse(), socket);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class UpdateService implements Runnable {

        @Override
        public void run() {
            while(true) {
                for(ServerCharacter serverCharacter : allCharacters) {
                    serverCharacter.notifyClient(world);
                }
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
