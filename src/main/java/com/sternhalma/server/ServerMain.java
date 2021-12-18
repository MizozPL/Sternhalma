package com.sternhalma.server;

public class ServerMain {

    public static final int DEFAULT_PORT = 25000;
    public static final String DEFAULT_ADDRESS = "localhost";

    public static void main(String[] args) {
        //TODO: Dodać parsowanie argumentów konsoli i odpowiednio zastąpić wartości domyślne.
        Server server = new Server(DEFAULT_ADDRESS, DEFAULT_PORT);
        server.start();
    }
}
