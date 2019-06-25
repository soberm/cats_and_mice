package at.ac.tuwien.catsandmice.client.board;

import at.ac.tuwien.catsandmice.client.characters.*;
import at.ac.tuwien.catsandmice.client.util.ClientConstants;
import at.ac.tuwien.catsandmice.client.world.SubwayRepresentation;
import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import at.ac.tuwien.catsandmice.dto.world.Subway;
import at.ac.tuwien.catsandmice.dto.world.World;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Board extends JPanel implements ActionListener {

    private Player player;

    private World world;

    private StateUpdateReader stateUpdateReader;

    private Socket socket;


    private final int DELAY = 20;
    private Timer timer;

    public Board() {
        super();
    }

    public void initWorld(Socket socket) {
        this.socket = socket;
        stateUpdateReader = new StateUpdateReader();
        //first discover how the world looks like
        stateUpdateReader.readLine();

    }

    /**
     * inits the board for further usage
     */
    public void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.gray);

        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setFocusable(true);

        timer = new Timer(DELAY, this);
        //start the update reader
        new Thread(stateUpdateReader).start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);

        Toolkit.getDefaultToolkit().sync();


    }

    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //if player is alive and game hasn't stopped draw world to only show what player should be seeing
        if(player.isAlive() && !world.isEnded()) {
            player.draw(g2d, this, world);
        } else {
            //otherwise draw everything
            for(Subway subway : world.getSubways()) {
                SubwayRepresentation subwayRepresentation = new SubwayRepresentation(subway);
                subwayRepresentation.draw(g2d, subway.getContainedMice().isEmpty(), false);
                for(Mouse mouse : subway.getContainedMice()) {
                    if(mouse.isAlive()) {
                        MouseRepresentation mouseRepresentation = new MouseRepresentation(mouse);
                        mouseRepresentation.draw(g2d, this);
                    }
                }
            }

            for(Mouse mouse : world.getMice()) {
                if(mouse.isAlive()) {
                    MouseRepresentation mouseRepresentation = new MouseRepresentation(mouse);
                    mouseRepresentation.draw(g2d, this);
                }
            }

            for(Cat cat : world.getCats()) {
                CatRepresentation catRepresentation = new CatRepresentation(cat);
                catRepresentation.draw(g2d, this);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        step();
    }
    private void step() {
        if(player.isAlive() && !world.isEnded()) {
            player.move();
        }

    }

    public World getWorld() {
        return world;
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if(player.isAlive()) {
                player.keyReleased(e);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(player.isAlive()) {
                player.keyPressed(e);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * receives the updates from the server
     */
    private class StateUpdateReader implements Runnable {

        private BufferedReader bufferedReader;

        public StateUpdateReader() {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            while (true) {
                readLine();
            }
        }

        public void readLine() {
            String worldJson = null;
            try {
                worldJson = bufferedReader.readLine();

                World newWorld = ClientConstants.getGson().fromJson(worldJson, World.class);
                if(newWorld.isStarted() && !world.isStarted()) {
                    timer.start();
                }
                world = newWorld;

                //if a player already exists, update their status
                if(player != null) {
                    for(Cat cat : world.getCats()) {
                        if(cat.getUuid().equals(player.getCharacter().getUuid())) {
                            player.updateCharacter(cat, world);
                        }
                    }
                    for(Mouse mouse : world.getMice()) {
                        if (mouse.getUuid().equals(player.getCharacter().getUuid())) {
                            player.updateCharacter(mouse, world);
                        }
                    }
                    for(Subway subway : world.getSubways()) {
                        subway.setContainedIn(world);
                        for(Mouse mouse : subway.getContainedMice()) {
                            if (mouse.getUuid().equals(player.getCharacter().getUuid())) {
                                player.updateCharacter(mouse, subway);
                            }
                        }
                    }
                }

                repaint();

                //if the game ended
                if(newWorld.isEnded()) {
                    timer.stop();

                    int sumCatKills = 0;
                    for(Cat cat : newWorld.getCats()) {
                        sumCatKills += cat.getKilledMice();
                    }
                    int sumSurvivedMice = 0;
                    for(Subway subway : newWorld.getSubways()) {
                        if(subway.isEnd()) {
                            sumSurvivedMice = subway.getContainedMice().size();
                        }
                    }
                    String message = "Cats: " + sumCatKills +  "; Mice: " + sumSurvivedMice + "\n";
                    message += sumCatKills > sumSurvivedMice ? "Cats have won!" : (sumSurvivedMice > sumCatKills ? "Mice have won!" : "Tie!");
                    JOptionPane.showMessageDialog(null, message, "ENDED", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            } catch (IOException e) {
                //end game for client if exception occurs
                throw new RuntimeException(e);

            }
        }
    }
}
