package at.ac.tuwien.catsandmice.client;

import at.ac.tuwien.catsandmice.client.application.Application;
import at.ac.tuwien.catsandmice.dto.util.Constants;
import org.apache.commons.cli.*;


public class Main {

    public static void main(String[] args) {
        Options options = new Options();

        Option option = new Option("c", "character", true, "choose between cat and mouse");
        option.setRequired(true);
        options.addOption(option);

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

        Application application = new Application(ch);
        application.setVisible(true);
    }
}
