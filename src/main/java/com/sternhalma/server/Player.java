package com.sternhalma.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player implements Runnable {
    private final Socket playerSocket;
    //private PrintWriter output;
    private Scanner input;
    private ObjectOutputStream objectOutputStream;

    public Player(Socket socket) {
        this.playerSocket = socket;
    }


    @Override
    public void run() {
        try {
            //output = new PrintWriter(playerSocket.getOutputStream(), true);
            input = new Scanner(playerSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(playerSocket.getOutputStream());
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] tokens = line.split(":", 3);
                String request = tokens[0];


                switch (request) {
                    case "createGame" -> {
                        String gameID = tokens[1];
                        String gameType = tokens[2];
                        sendMessage(GameManager.getInstance().createGame(gameID, gameType));
                    }
                    case "joinGame" -> {
                        String gameID = tokens[1];
                        sendMessage(GameManager.getInstance().joinGame(this, gameID));
                    }
                    case "performAction" -> {
                        String gameID = tokens[1];
                        String action = tokens[2];
                        sendMessage(GameManager.getInstance().performAction(this, gameID, action));
                    }
                    default -> {
                        sendMessage("Bad request!");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                playerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            input = null;
            objectOutputStream = null;
        }

    }

    public synchronized void sendObject(Object data) {
        if (objectOutputStream == null) {
            throw new IllegalStateException();
        }
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(data);
        } catch (IOException e){
            //TODO
        }
    }

    //Nie musi być synchronized bo korzysta z sendObject
    public void sendMessage(String data) {
        sendObject(data);
    }
}
