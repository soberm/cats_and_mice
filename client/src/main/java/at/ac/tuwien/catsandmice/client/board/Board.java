package at.ac.tuwien.catsandmice.client.board;

import at.ac.tuwien.catsandmice.client.characters.*;
import at.ac.tuwien.catsandmice.client.util.ClientConstants;
import at.ac.tuwien.catsandmice.client.world.SubwayRepresantation;
import at.ac.tuwien.catsandmice.client.world.WorldRepresentation;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import at.ac.tuwien.catsandmice.dto.world.Boundaries;
import at.ac.tuwien.catsandmice.dto.world.World;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Board extends JPanel implements ActionListener {

    private Player player;

    private WorldRepresentation world;

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
        stateUpdateReader.readLine();

    }

    public void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.gray);

        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setFocusable(true);

        timer = new Timer(DELAY, this);
        timer.start();
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
        for(SubwayRepresantation subway : world.getSubwayRepresantations()) {
            subway.draw(g2d, this);
            for(MouseRepresentation mouse : subway.getMiceRepresentations()) {
                mouse.draw(g2d, this);
            }
            if (player.isAlive()) {
                player.enterSubway(subway);
            }
        }
        player.resetClicks();
        for(MouseRepresentation mouse : world.getMouseRepresentations()) {
            if(mouse.isAlive()) {
                mouse.draw(g2d, this);
            }
        }
        for(CatRepresentation cat : world.getCatRepresentations()) {
            cat.draw(g2d, this);
        }

    }

    public void actionPerformed(ActionEvent e) {
        step();
    }
    private void step() {
        if(player.isAlive()) {
            player.move();
        }
//        for(CatRepresentation cat : world.getCatRepresentations()) {
//            for(MouseRepresentation mouse : world.getMouseRepresentations()) {
//                cat.kill(mouse);
//            }
//        }
//        repaint();
    }

    public void addMouse(MouseRepresentation mouse) {
        world.addMouse(mouse);
    }

    public WorldRepresentation getWorld() {
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

                world = ClientConstants.getGson().fromJson(worldJson, WorldRepresentation.class);
                if(player != null && !player.isInitialised()) {
                    for(CatRepresentation cat : world.getCatRepresentations()) {
                        if(cat.getUuid().equals(player.getCharacter().getUuid())) {
                            player.updateCharacter(cat);
                        }
                    }
                    for(MouseRepresentation mouse : world.getMouseRepresentations()) {
                        if (mouse.getUuid().equals(player.getCharacter().getUuid())) {
                            player.updateCharacter(mouse);
                        }
                    }
                    for(SubwayRepresantation subway : world.getSubwayRepresantations()) {
                        for(MouseRepresentation mouse : subway.getMiceRepresentations()) {
                            if (mouse.getUuid().equals(player.getCharacter().getUuid())) {
                                player.updateCharacter(mouse);
                            }
                        }
                    }
                }

                repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }
}
