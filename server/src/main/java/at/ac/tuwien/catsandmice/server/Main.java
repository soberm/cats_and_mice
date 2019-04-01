package at.ac.tuwien.catsandmice.server;

import at.ac.tuwien.catsandmice.server.state.*;
import at.ac.tuwien.catsandmice.server.util.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {

    private static Server server;
    private static ServerSubway subway;

    public static void main(String[] args) {
        server = new Server();
        new Thread(server).start();
    }
}
