package at.ac.tuwien.catsandmice.client.application;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.client.util.Constants;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    private Board board;

    public Application() {
        initUI();
    }

    private void initUI() {
        board = new Board();
        board.setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        add(board);

        setBackground(Color.black);
        setResizable(false);
        pack();
        //setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));

        setTitle("Cats and Mice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
