package at.ac.tuwien.catsandmice.client.application;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.client.characters.*;
import at.ac.tuwien.catsandmice.client.util.ClientConstants;
import at.ac.tuwien.catsandmice.client.world.SubwayRepresantation;
import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import at.ac.tuwien.catsandmice.dto.util.LoginMessage;
import at.ac.tuwien.catsandmice.dto.world.Subway;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Application extends JFrame {

    private Board board;
    private int port;
    private String url;

    public Application(String ch, String url, int port) {
        this.port = port;
        this.url = url;
        initUI(ch);

    }

    private void initUI(String ch) {

        board = new Board();
        board.setDoubleBuffered(true);
        Player player = null;
        try {
            Socket socket = new Socket(url, port);


            switch (ch) {
                case "mouse":
                    MouseRepresentation mouse = new MouseRepresentation();
                    player = new MousePlayer(mouse);
                    register(mouse, socket);
                    break;
                case "cat":
                    CatRepresentation cat = new CatRepresentation();
                    player = new CatPlayer(cat);
                    register(cat, socket);
                    break;
            }

            board.initWorld(socket);

            player.setSocket(socket);
            board.setPlayer(player);

            board.initBoard();
            board.setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
            add(board);

            setBackground(Color.black);
            setResizable(false);
            pack();
            //setSize(new Dimension(ClientConstants.SCREEN_WIDTH, ClientConstants.SCREEN_HEIGHT));

            setTitle("Cats and Mice");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void register(Mouse mouse, Socket socket) {
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMouse(mouse);
        register(loginMessage, socket);
    }

    private void register(Cat cat, Socket socket) {
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setCat(cat);
        register(loginMessage, socket);
    }

    private void register(LoginMessage loginMessage, Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            String json = ClientConstants.getGson().toJson(loginMessage);
            printWriter.println(json);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
