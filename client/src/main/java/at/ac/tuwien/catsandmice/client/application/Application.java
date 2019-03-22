package at.ac.tuwien.catsandmice.client.application;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.client.util.Constants;

import javax.swing.*;

public class Application extends JFrame {

    public Application() {
        initUI();
    }

    private void initUI() {
        add(new Board());

        setResizable(false);
        pack();
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        setTitle("Cats and Mice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
