package com.sternhalma.server.connection;

import com.sternhalma.common.connection.NetworkMessages;
import com.sternhalma.server.games.GameManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Player implements Runnable {
    private final Socket playerSocket;
    private Scanner input;
    private ObjectOutputStream objectOutputStream;

    public Player(Socket socket) {
        this.playerSocket = socket;
    }


    @Override
    public void run() {
        try {
            input = new Scanner(playerSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(playerSocket.getOutputStream());

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] tokens = line.split(":", 3);
                String request = tokens[0];

                switch (request) {
                    case NetworkMessages.CREATE_GAME -> {
                        String gameID = tokens[1];
                        String gameType = tokens[2];
                        GameManager.getInstance().createGame(this, gameID, gameType);
                    }
                    case NetworkMessages.JOIN_GAME -> {
                        String gameID = tokens[1];
                        GameManager.getInstance().joinGame(this, gameID);
                    }
                    case NetworkMessages.PERFORM_ACTION -> {
                        String gameID = tokens[1];
                        String action = tokens[2];
                        GameManager.getInstance().performAction(this, gameID, action);
                    }
                    default -> {
                        sendMessage(NetworkMessages.BAD_REQUEST);
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

    public synchronized boolean isAlive() {
        return objectOutputStream != null;
    }

    public synchronized void sendObject(Object data) {
        if (objectOutputStream == null) {
            //throw new IllegalStateException();
            return;
        }
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String data) {
        sendObject(data);
    }
}
