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
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel implements ActionListener, Boundaries {
    private Cat cat;
    private Mouse mouse;
    private List<Subway> subways = new ArrayList<Subway>();

    private final int DELAY = 20;
    private Timer timer;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.gray);
        System.out.println(getMaximumSize());
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setFocusable(true);

        this.cat = new Cat(this);
        this.mouse = new Mouse(this);
        Subway subway = new Subway(300, 300, 300, 800, this);
        subways.add(subway);

        subway = new Subway(500, 900, 900, 900, this);
        subways.add(subway);

        subway = new Subway(700, 450, 1000, 450, this);
        subways.add(subway);


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
        for(Subway subway : subways) {
            subway.draw(g2d, this);
            if (mouse.isAlive()) {
                if (mouse.isTryToEnter()) {
                    if(subway.enterOrExit(mouse)) {
                        mouse.setTryToEnter(false);
                    }
                }
            }
        }
        if(mouse.isAlive()) {
            mouse.draw(g2d, this);
        }
        mouse.setTryToEnter(false);
        cat.draw(g2d, this);

        //System.out.println("cat: "+ cat.getX() + " , " + cat.getY());
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
        if(cat.getBoundaries() == mouse.getBoundaries() && cat.getBounds().intersects(mouse.getBounds())) {
            mouse.setAlive(false);
        }

    }

    public int getMaxWidth() {
        return getSize().width;
    }

    public int getMaxHeight() {
        return getSize().height;
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
