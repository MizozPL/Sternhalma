package com.sternhalma.server;

import org.apache.commons.cli.*;

public class ServerMain {

    public static void main(String[] args) {
        final int PORT;
        final String ADDRESS;

        Options options = new Options();
        options.addOption(Option.builder("port")
                .hasArg(true)
                .desc("server PORT    [REQUIRED]")
                .type(Number.class)
                .required(true)
                .build());
        options.addOption(Option.builder("a")
                .longOpt("address")
                .hasArg(true)
                .desc("server address [REQUIRED]")
                .required(true)
                .build());

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);

            PORT = ((Number) cmd.getParsedOptionValue("port")).intValue();
            ADDRESS = cmd.getOptionValue("address");

            Server server = new Server(ADDRESS, PORT);
            System.out.println("Server running on PORT: " + PORT + " and address: " + ADDRESS);
            server.start();

        } catch (ParseException pe) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ServerMain -port <arg> -a <arg>", options);
            System.exit(1);
        }
    }
}
