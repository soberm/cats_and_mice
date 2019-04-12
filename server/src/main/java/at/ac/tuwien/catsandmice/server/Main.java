package at.ac.tuwien.catsandmice.server;

import at.ac.tuwien.catsandmice.server.state.*;
import org.apache.commons.cli.*;

public class Main {

    private static Server server;
    private static ServerSubway subway;

    public static void main(String[] args) {
        Options options = new Options();

        Option catOption = new Option("cc", "computer-cat", true, "choose number of cat bots");
        catOption.setRequired(true);
        Option mouseOption = new Option("cm", "computer-mouse", true, "choose number of mouse bots");
        mouseOption.setRequired(true);

        options.addOption(catOption);
        options.addOption(mouseOption);

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

            server = new Server(catbots, mousebots);
            new Thread(server).start();
        } catch (NumberFormatException ne) {
            formatter.printHelp("utility-name", options);
        }
    }
}
