package at.ac.tuwien.catsandmice.client.application;

import at.ac.tuwien.catsandmice.client.board.Board;
import at.ac.tuwien.catsandmice.client.characters.*;
import at.ac.tuwien.catsandmice.client.util.ClientConstants;
import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import at.ac.tuwien.catsandmice.dto.util.LoginMessage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Application extends JFrame {

    private Board board;
    private int port;
    private String url;
    private String name;

    /**
     * starts the application
     * @param ch the type of character to start, either "cat" or "mouse", not null
     * @param url the url to connect to the server, not null
     * @param port the port to connect to the server, not null
     * @param name the name of the user, not null
     */
    public Application(String ch, String url, int port, String name) {
        this.port = port;
        this.url = url;
        this.name = name;
        initUI(ch);

    }

    //String ch either "mouse" or "cat"
    private void initUI(String ch) {

        board = new Board();
        board.setDoubleBuffered(true);
        Player player = null;
        try {
            Socket socket = new Socket(url, port);


            switch (ch) {
                case "mouse":
                    Mouse mouse = new Mouse(this.name);
                    MouseRepresentation mouseRepresentation = new MouseRepresentation(mouse);
                    player = new MousePlayer(mouseRepresentation);
                    register(mouse, socket);
                    break;
                case "cat":
                    Cat cat = new Cat();
                    CatRepresentation catRep = new CatRepresentation(cat);
                    player = new CatPlayer(catRep);
                    register(cat, socket);
                    break;
                default:
                    return;
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
