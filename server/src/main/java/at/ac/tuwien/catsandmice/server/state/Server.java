package at.ac.tuwien.catsandmice.server.state;

import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import at.ac.tuwien.catsandmice.dto.world.Subway;
import at.ac.tuwien.catsandmice.dto.world.World;
import at.ac.tuwien.catsandmice.server.computer.ComputerCat;
import at.ac.tuwien.catsandmice.server.computer.ComputerMouse;
import at.ac.tuwien.catsandmice.server.computer.IComputerPlayer;
import at.ac.tuwien.catsandmice.server.util.ServerConstants;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class Server implements Runnable {

    private World world;
    private List<ServerCharacter> allCharacters = new CopyOnWriteArrayList<>();
    private List<IComputerPlayer> computerPlayers = new CopyOnWriteArrayList<>();

    private ServerSocket socket;

    private int mousebots, catbots;

    public Server(int catbots, int mousebots) {
        super();
        this.catbots = catbots;
        this.mousebots = mousebots;
        initWorld();
    }

    private void initWorld() {
        this.world = new World();
        world.initUuid();
        for(int i = 0; i < 7; i++) {
            Subway subway = createRandomSubway();
            if(subway != null) {
                world.addSubway(createRandomSubway());
            }
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int goal = random.nextInt(0, world.getSubways().size());
        world.getSubways().get(goal).setEnd(true);
        System.out.println(world);


    }

    private Subway createRandomSubway() {
        boolean overlaps = false;
        Subway subway = null;
        int count = 10;
        while((overlaps || subway == null) && count > 0) {
            subway = new Subway();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int x1 = random.nextInt(Constants.SUBWAY_ENTRY_WIDTH, world.getMaxWidth()-Constants.SUBWAY_ENTRY_WIDTH*2);
            int y1 = random.nextInt(Constants.SUBWAY_ENTRY_WIDTH, world.getMaxHeight()-Constants.SUBWAY_ENTRY_WIDTH*2);
            int x2, y2;
            if (Math.random() > 0.5) {
                x2 = random.nextInt(x1+Constants.SUBWAY_ENTRY_WIDTH, world.getMaxWidth()-Constants.SUBWAY_ENTRY_WIDTH);
                if(x1 > x2) {
                    int tmp = x1;
                    x1 = x2;
                    x2 = tmp;
                }
                y2 = y1;
            } else {
                y2 = random.nextInt(y1 + Constants.SUBWAY_ENTRY_WIDTH, world.getMaxHeight()-Constants.SUBWAY_ENTRY_WIDTH);
                if(y1 > y2) {
                    int tmp = y1;
                    y1 = y2;
                    y2 = tmp;
                }
                x2 = x1;
            }

            subway.setX1(x1);
            subway.setX2(x2);
            subway.setY1(y1);
            subway.setY2(y2);

            Shape entry1 = new Ellipse2D.Double(
                    subway.getX1()- Constants.SUBWAY_ENTRY_WIDTH/2,
                    subway.getY1()- Constants.SUBWAY_ENTRY_WIDTH/2,
                    Constants.SUBWAY_ENTRY_WIDTH,
                    Constants.SUBWAY_ENTRY_WIDTH);

            Shape entry2 = new Ellipse2D.Double(
                    subway.getX2()- Constants.SUBWAY_ENTRY_WIDTH/2,
                    subway.getY2()- at.ac.tuwien.catsandmice.dto.util.Constants.SUBWAY_ENTRY_WIDTH/2,
                    Constants.SUBWAY_ENTRY_WIDTH,
                    Constants.SUBWAY_ENTRY_WIDTH);
            if(entry1.intersects(entry2.getBounds())) {
                System.out.println(subway.toString() + " entries overlap");
                subway = null;
                overlaps = true;
            } else {
                for (Subway other : world.getSubways()) {
                    Shape otherEntry = new Ellipse2D.Double(
                            other.getX1() - Constants.SUBWAY_ENTRY_WIDTH / 2,
                            other.getY1() - Constants.SUBWAY_ENTRY_WIDTH / 2,
                            Constants.SUBWAY_ENTRY_WIDTH,
                            Constants.SUBWAY_ENTRY_WIDTH);
                    if (otherEntry.intersects(entry1.getBounds()) || otherEntry.intersects(entry2.getBounds())) {
                        System.out.println(subway.toString() + " overlaps with entry1 of " + subway.toString());
                        overlaps = true;
                        subway = null;
                        break;
                    } else {
                        otherEntry = new Ellipse2D.Double(
                                other.getX2() - Constants.SUBWAY_ENTRY_WIDTH / 2,
                                other.getY2() - Constants.SUBWAY_ENTRY_WIDTH / 2,
                                Constants.SUBWAY_ENTRY_WIDTH,
                                Constants.SUBWAY_ENTRY_WIDTH);
                        if (otherEntry.intersects(entry1.getBounds()) || otherEntry.intersects(entry2.getBounds())) {
                            System.out.println(subway.toString() + " overlaps with entry2 of " + subway.toString());
                            overlaps = true;
                            subway = null;
                            break;
                        }
                    }
                }
            }
            count --;
        }
        if(subway != null) {
            subway.initUuid();
        }
        return subway;
    }

    private void initComputerPlayers() {
        for (int i=0; i<catbots; ++i) {
            Cat cat = new Cat();
            //TODO set width & height according to sprites
            cat.setWidth(100);
            cat.setHeight(50);
            cat.setX((int) (Math.random() * world.getMaxWidth()));
            cat.setY((int) (Math.random() * world.getMaxHeight()));
            cat.setBoundaries(world);
            ComputerCat cc = new ComputerCat(cat);

            world.addCat(cat);
            computerPlayers.add(cc);
        }

        for (int i=0; i<mousebots; ++i) {
            Mouse mouse = new Mouse();
            //TODO set width & height according to sprites
            mouse.setWidth(50);
            mouse.setHeight(25);
            Subway currentSub = world.getSubways().get(ThreadLocalRandom.current().nextInt(world.getSubways().size()));
            mouse.setBoundaries(currentSub);
            currentSub.addMouse(mouse);
            mouse.setX((currentSub.getX1() + currentSub.getX2()) / 2);
            mouse.setY((currentSub.getY1() + currentSub.getY2()) / 2);
            ComputerMouse cm = new ComputerMouse(mouse, currentSub);

            computerPlayers.add(cm);
        }
    }

    public void login(Cat cat, Socket socket) {
        if(cat != null) {
            cat.setBoundaries(world);
            ServerCat serverCat = new ServerCat(cat);
            cat.setX((int) (Math.random() * world.getMaxWidth()));
            cat.setY((int) (Math.random() * world.getMaxHeight()));
            world.addCat(cat);
            allCharacters.add(serverCat);
            startReading(serverCat, socket);
        }
    }

    public void login(Mouse mouse, Socket socket) {
        if(mouse != null) {
            Subway currentSub = world.getSubways().get(ThreadLocalRandom.current().nextInt(world.getSubways().size()));
            mouse.setBoundaries(currentSub);
            mouse.setX((currentSub.getX1() + currentSub.getX2()) / 2);
            mouse.setY((currentSub.getY1() + currentSub.getY2()) / 2);
            currentSub.addMouse(mouse);
            ServerMouse serverMouse = new ServerMouse(mouse);
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
                        writer.println(ServerConstants.getGson().toJson(world));
                        writer.flush();
                        if (!(loginMessage == null || loginMessage.isEmpty())) {
                            Message message = ServerConstants.getGson().fromJson(loginMessage, Message.class);
                            login(message.getCat(), socket);
                            login(message.getMouse(), socket);
                            initComputerPlayers();
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
                for (IComputerPlayer pc : computerPlayers) {
                    pc.move(world);
                }

                for(Cat cat : world.getCats()) {
                    ServerCat serverCat = new ServerCat(cat);
                    for(Mouse mouse : world.getMice()) {
                        serverCat.kill(mouse);
                    }
                }
                for(ServerCharacter serverCharacter : allCharacters) {
                    serverCharacter.notifyClient(world);
                }
                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
