package at.ac.tuwien.catsandmice.server;

import at.ac.tuwien.catsandmice.server.state.*;
import org.apache.commons.cli.*;

public class Main {

    private static Server server;

    public static void main(String[] args) {
        Options options = new Options();

        Option catOption = new Option("cc", "computer-cat", true, "choose number of cat bots");
        catOption.setRequired(true);
        Option mouseOption = new Option("cm", "computer-mouse", true, "choose number of mouse bots");
        mouseOption.setRequired(true);

        Option playerOption = new Option("pc", "player-count", true, "number of players expected to connect");
        playerOption.setRequired(true);



        Option portOption = new Option("p", "port", true, "port to open server on");


        options.addOption(catOption);
        options.addOption(mouseOption);
        options.addOption(playerOption);
        options.addOption(portOption);



        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("utility-name", options);
            return;
        }

        try {
            int catbots = Integer.parseInt(cmd.getOptionValue("cc"));
            int mousebots = Integer.parseInt(cmd.getOptionValue("cm"));
            int port = Integer.valueOf(cmd.getOptionValue("p", "2222"));
            int players = Integer.valueOf(cmd.getOptionValue("pc"));
            server = new Server(catbots, mousebots, port, players);
            new Thread(server).start();
        } catch (NumberFormatException ne) {
            formatter.printHelp("utility-name", options);
        }
    }
}
