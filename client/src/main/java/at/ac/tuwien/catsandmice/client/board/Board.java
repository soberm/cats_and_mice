package at.ac.tuwien.catsandmice.client.board;

import at.ac.tuwien.catsandmice.client.characters.Cat;
import at.ac.tuwien.catsandmice.client.characters.Mouse;
import at.ac.tuwien.catsandmice.client.util.Constants;
import at.ac.tuwien.catsandmice.client.world.Boundaries;
import at.ac.tuwien.catsandmice.client.world.Subway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener, Boundaries {
    private Cat cat;
    private Mouse mouse;
    private Subway subway;

    private final int DELAY = 20;
    private Timer timer;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.gray);
        setFocusable(true);

        this.cat = new Cat(this);
        this.mouse = new Mouse(this);
        this.subway = new Subway(300, 300, 300, 800, this);


        timer = new Timer(DELAY, this);
        timer.start();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);

        Toolkit.getDefaultToolkit().sync();


    }

    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        subway.draw(g2d, this);
        if(mouse.isAlive()) {
            if(mouse.isTryToEnter()) {
                System.out.println("enterOrExit: " + subway.enterOrExit(mouse));
                mouse.setTryToEnter(false);
            }
            mouse.draw(g2d, this);
        }
        cat.draw(g2d, this);
    }

    public void actionPerformed(ActionEvent e) {
        step();
    }
    private void step() {

        cat.move();
        if(mouse.isAlive()) {
            mouse.move();
        }

        repaint();
        if(cat.getBounds().intersects(mouse.getBounds())) {
            mouse.setAlive(false);
        }

    }

    public int getMaxWidth() {
        return Constants.SCREEN_WIDTH;
    }

    public int getMaxHeight() {
        return Constants.SCREEN_HEIGHT;
    }

    public int getMinHeight() {
        return 0;
    }

    public int getMinWidth() {
        return 0;
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            cat.keyReleased(e);
            mouse.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            cat.keyPressed(e);
            mouse.keyPressed(e);
        }
    }


}
