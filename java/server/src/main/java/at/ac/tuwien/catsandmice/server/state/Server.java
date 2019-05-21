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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Server implements Runnable {

    private World world;
    private List<ServerCharacter> allCharacters = new CopyOnWriteArrayList<>();
    private List<IComputerPlayer> computerPlayers = new CopyOnWriteArrayList<>();

    private Subway goalSubway;

    private ServerSocket socket;
    private int players;
    private AtomicInteger currentPlayerCount;

    private int mousebots, catbots;
    private int port;
    private String name;

    public Server(int catbots, int mousebots, int port, int players) {
        super();
        this.catbots = catbots;
        this.mousebots = mousebots;
        this.port = port;
        this.players = players;
        this.currentPlayerCount = new AtomicInteger(0);

        initWorld();
    }

    private void initWorld() {
        this.world = new World();
        world.initUuid();
        for(int i = 0; i < 10; i++) {
            Subway subway = createRandomSubway();
            if(subway != null) {
                world.addSubway(subway);
            }
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int goal = random.nextInt(0, world.getSubways().size());
        goalSubway = world.getSubways().get(goal);
        goalSubway.setEnd(true);

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
            Mouse mouse = new Mouse("pc"+i);
            //TODO set width & height according to sprites
            mouse.setWidth(50);
            mouse.setHeight(25);
            Subway currentSub = null;
            while(currentSub == null || currentSub.isEnd()) {
                currentSub = world.getSubways().get(ThreadLocalRandom.current().nextInt(world.getSubways().size()));
            }
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
            ServerCat serverCat = new ServerCat(cat, this);
            cat.setX((int) (Math.random() * world.getMaxWidth()));
            cat.setY((int) (Math.random() * world.getMaxHeight()));
            world.addCat(cat);
            allCharacters.add(serverCat);
            serverCat.setClient(socket);
        }
    }

    public void login(Mouse mouse, Socket socket) {
        if(mouse != null) {
            Subway currentSub = null;
            while(currentSub == null || currentSub.isEnd()) {
                currentSub = world.getSubways().get(ThreadLocalRandom.current().nextInt(world.getSubways().size()));
            }
            mouse.setBoundaries(currentSub);
            mouse.setX((currentSub.getX1() + currentSub.getX2()) / 2);
            mouse.setY((currentSub.getY1() + currentSub.getY2()) / 2);
            currentSub.addMouse(mouse);
            ServerMouse serverMouse = new ServerMouse(mouse, this);
            allCharacters.add(serverMouse);
            serverMouse.setClient(socket);
        }
    }



    @Override
    public void run() {
        new Thread(new LoginService()).start();
    }

    public void removePlayer(ServerCat serverCat) {
        int index = allCharacters.indexOf(serverCat);
        if(index >= 0) {
            allCharacters.remove(serverCat);
            world.removeCat(serverCat.getCat());
            //decrease player count if disconnect happens before start
            currentPlayerCount.decrementAndGet();
        }
    }

    public void removePlayer(ServerMouse serverMouse) {
        int index = allCharacters.indexOf(serverMouse);
        if(index >= 0) {
            allCharacters.remove(serverMouse);
            if(serverMouse.getCharacter().getBoundaries() instanceof World) {
                world.removeMouse(serverMouse.getMouse());
            } else if(serverMouse.getCharacter().getBoundaries() instanceof Subway) {
                Subway subway = (Subway) serverMouse.getCharacter().getBoundaries();
                subway.removeMouse(serverMouse.getMouse());
            }
            //decrease player count if disconnect happens before start
            currentPlayerCount.decrementAndGet();
        }
    }

    private class LoginService implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                initComputerPlayers();
                while(!(currentPlayerCount.get() == players)) {
                    try {
                        Socket socket = serverSocket.accept();
                        currentPlayerCount.incrementAndGet();
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
                        }
                        if(currentPlayerCount.get() == players) {
                            world.setStarted(true);
                            for(ServerCharacter serverCharacter : allCharacters) {
                                new Thread(serverCharacter).start();
                            }
                            new Thread(new UpdateService()).start();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        if(currentPlayerCount.get() > 0) {
                            currentPlayerCount.decrementAndGet();
                        }
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
            while(!world.isEnded()) {
                world.setEnded(checkEnded());
                if(!world.isEnded()) {
                    for (IComputerPlayer pc : computerPlayers) {
                        pc.move(world);
                    }
                }

                for(Cat cat : world.getCats()) {
                    ServerCat serverCat = new ServerCat(cat, null);
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
            System.exit(0);
        }

        private boolean checkEnded() {
            boolean ended = true;
            for(ServerCharacter serverCharacter : allCharacters) {
                if(serverCharacter.getCharacter() instanceof  Mouse) {
                    Mouse mouse = (Mouse) serverCharacter.getCharacter();
                    if(mouse.isAlive()) {
                        if (mouse.getBoundaries() instanceof Subway && !((Subway) mouse.getBoundaries()).isEnd()) {
                            ended = false;

                            break;
                        } else if(mouse.getBoundaries() instanceof World) {
                            ended = false;
                            break;
                        }
                    }
                }
            }
            if(ended) {
                for(IComputerPlayer pc : computerPlayers) {
                    if(pc instanceof ComputerMouse) {
                        ComputerMouse mouse = (ComputerMouse) pc;
                        if(mouse.getMouse().isAlive() && (mouse.getCurrentSub() == null || !mouse.getCurrentSub().isEnd())) {
                            ended = false;
                            break;
                        }
                    }
                }
            }
            if(ended) {
                System.out.println("world end: " + world);
            }
            return ended;
        }
    }
}
