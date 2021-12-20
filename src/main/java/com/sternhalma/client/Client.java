package com.sternhalma.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    private final String address;
    private final int port;
    private final String gameID;
    private ObjectInputStream objectInputStream;
    private PrintWriter printWriter;

    public Client(String address, int port, String gameID){
        this.address = address;
        this.port = port;
        this.gameID = gameID;
    }

    public void connect(){
        try(Socket socket = new Socket(InetAddress.getByName(address), port)){
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            //TODO: Kontynuować implementację klienta

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        objectInputStream = null;
        printWriter = null;
    }

    public synchronized void sendMessage(String message){
        if (printWriter == null) {
            throw new IllegalStateException();
        }
        printWriter.println(message);
    }

    public synchronized Object readObject(){
        if (objectInputStream == null) {
            throw new IllegalStateException();
        }
        try {
            return objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
