package com.sternhalma.server;

import com.sternhalma.server.connection.Server;

public class ServerMain {

    public static int PORT = 25000;
    public static String ADDRESS = "localhost";

    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                PORT = Integer.parseInt(args[1]);
                ADDRESS = args[0];
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        Server server = new Server(ADDRESS, PORT);
        server.start();
    }
}