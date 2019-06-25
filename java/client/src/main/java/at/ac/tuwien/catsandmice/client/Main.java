package at.ac.tuwien.catsandmice.client;

import at.ac.tuwien.catsandmice.client.application.Application;
import org.apache.commons.cli.*;


public class Main {

    public static void main(String[] args) {
        Options options = new Options();

        Option option = new Option("c", "character", true, "choose between cat and mouse");
        option.setRequired(true);
        options.addOption(option);

        option = new Option("u", "url", true, "url of server");
        options.addOption(option);

        option = new Option("p", "port", true, "port of server");
        options.addOption(option);

        Option nameOption = new Option("n", "name", true, "name of player");
        nameOption.setRequired(true);
        options.addOption(nameOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("utility-name", options);
            return;
        }

        String ch = cmd.getOptionValue("c");

        if ((!ch.equals("mouse") && (!ch.equals("cat")))) {
            formatter.printHelp("utility-name", options);
            return;
        }

        String name = cmd.getOptionValue("n");

        String url = cmd.getOptionValue("u", "localhost");
        int port = Integer.valueOf(cmd.getOptionValue("p", "2222"));

        Application application = new Application(ch, url, port, name);
        application.setVisible(true);
    }
}
