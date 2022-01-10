package com.sternhalma.server.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int THREADS = 20;
    private static final int BACKLOG = 10;
    private final String address;
    private final int port;

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port, BACKLOG, InetAddress.getByName(address))){
            ExecutorService executors = Executors.newFixedThreadPool(THREADS);
            while(true) {
                executors.execute(new Player(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
