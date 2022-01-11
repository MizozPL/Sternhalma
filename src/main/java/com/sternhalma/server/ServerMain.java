package com.sternhalma.server;

import com.sternhalma.server.connection.Server;

/**
 * Klasa parsująca adres i port dla serwera oraz go uruchamiająca.
 */
public class ServerMain {

    /**
     * Domyślny port serwera.
     */
    public static int PORT = 25000;
    /**
     * Domyślny adres serwera.
     */
    public static String ADDRESS = "localhost";

    /**
     * Punkt wejściowy serwera.
     * @param args - opcjonalne port(args[0]) i adres(args[1])
     */
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