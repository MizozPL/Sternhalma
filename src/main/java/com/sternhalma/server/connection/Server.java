package com.sternhalma.server.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implementuje socket nasłuchujący na przychodzących klientów.
 */
public class Server {
    /**
     * Liczba wątków / maksymalna liczba jednocześnie podłączonych graczy.
     */
    private static final int THREADS = 20;
    /**
     * Parametr określający maksymalną liczbę graczy czekających w kolejce.
     */
    private static final int BACKLOG = 10;
    /**
     * Adres serwera.
     */
    private final String address;
    /**
     * Port serwera.
     */
    private final int port;

    /**
     * Konstruktor ustawiający adres i port serwera.
     * @param address adres serwera.
     * @param port port serwera.
     */
    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Uruchamia serwer (nasłuchiwanie klientów).
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port, BACKLOG, InetAddress.getByName(address))) {
            ExecutorService executors = Executors.newFixedThreadPool(THREADS);
            while (true) {
                executors.execute(new Player(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
