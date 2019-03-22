package at.ac.tuwien.catsandmice.client.board;

import at.ac.tuwien.catsandmice.client.characters.Cat;
import at.ac.tuwien.catsandmice.client.characters.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Board extends JPanel implements ActionListener {
    private Cat cat;
    private Mouse mouse;

    private final int DELAY = 10;
    private Timer timer;
    private AffineTransform transform = new AffineTransform();
    private AffineTransform mouseTransform = new AffineTransform();

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.gray);
        setFocusable(true);

        this.cat = new Cat();
        this.mouse = new Mouse();


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
        if(mouse.isAlive()) {
            AffineTransform rotationMouse = new AffineTransform();
            rotationMouse.rotate(Math.toRadians(mouse.getRotation()), mouse.getX() + mouse.getWidth() / 2, mouse.getY() + mouse.getHeight() / 2);
            rotationMouse.translate(mouse.getX(), mouse.getY());
            g2d.drawImage(mouse.getImage(), rotationMouse, this);
        }


        AffineTransform rotation = new AffineTransform();
        rotation.setTransform(transform);
        rotation.rotate(Math.toRadians(cat.getRotation()), cat.getX() + cat.getWidth()/2, cat.getY() + cat.getHeight()/2);
        rotation.translate(cat.getX(), cat.getY());

        //rotation.translate(cat.getX()-(cat.getWidth()/2), cat.getY()-(cat.getHeight()/2));
        g2d.drawImage(cat.getImage(), rotation, this);


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
