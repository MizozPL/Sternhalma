package com.sternhalma.client.connection;

import com.sternhalma.client.games.Game;
import com.sternhalma.client.games.GameFactory;
import com.sternhalma.client.gui.ClientFrame;
import com.sternhalma.common.connection.NetworkMessages;
import com.sternhalma.common.games.Games;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private final String address;
    private final int port;
    private final String gameID;
    private final ClientFrame clientFrame;
    private ObjectInputStream objectInputStream;
    private PrintWriter printWriter;
    private static final String GAME_NAME = Games.BASIC_STERNHALMA;

    public Client(String address, int port, String gameID) {
        this.address = address;
        this.port = port;
        this.gameID = gameID;
        clientFrame = new ClientFrame(address + ":" + port);
    }

    public String getGameID() {
        return gameID;
    }

    public void connect() {
        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            createGameInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        objectInputStream = null;
        printWriter = null;
    }

    private void createGameInstance() {
        sendMessage(NetworkMessages.CREATE_GAME + ":" + gameID + ":" + GAME_NAME);
        Object response = readObject();
        if (!(response instanceof String) || NetworkMessages.BAD_GAME_TYPE.equals(response)) {
            JOptionPane.showMessageDialog(clientFrame, "Error joining game!\nRestart client...", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        sendMessage(NetworkMessages.JOIN_GAME + ":" + gameID);
        response = readObject();
        if(NetworkMessages.GAME_END_PLAYER_DISCONNECTED.equals(response)){
            JOptionPane.showMessageDialog(clientFrame, "Player got disconnected, game ended!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!GAME_NAME.equals(response)) {
            JOptionPane.showMessageDialog(clientFrame, "Error joining game!\nRestart client...", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Game instance = GameFactory.createGameInstance(GAME_NAME);
        if (instance == null) {
            JOptionPane.showMessageDialog(clientFrame, "Error creating local game instance!\nRestart client...", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        instance.init(this);
    }

    public ClientFrame getClientFrame() {
        return clientFrame;
    }

    public void sendMessage(String message) {
        if (printWriter == null) {
            throw new IllegalStateException();
        }
        synchronized (printWriter) {
            printWriter.println(message);
        }
    }

    public Object readObject() {
        if (objectInputStream == null) {
            throw new IllegalStateException();
        }
        synchronized (objectInputStream) {
            try {
                return objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
