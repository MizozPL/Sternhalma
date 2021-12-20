package com.sternhalma.client;

import com.sternhalma.client.gui.ClientFrame;
import com.sternhalma.common.games.BasicSternhalma.Board;

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
    private static final String GAME_NAME = "BasicSternhalma";

    public Client(String address, int port, String gameID){
        this.address = address;
        this.port = port;
        this.gameID = gameID;
        clientFrame = new ClientFrame(address+":"+port);
    }

    public void connect(){
        try(Socket socket = new Socket(InetAddress.getByName(address), port)){
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            sendMessage("createGame:"+gameID+":"+GAME_NAME);
            readObject();
            sendMessage("joinGame:"+gameID);
            Object response = readObject();
            if(!(response instanceof String)) {
                throw new IllegalStateException();
            }
            String responseString = (String) response;
            if(responseString.equals("Not joined")) {
                throw new IllegalStateException();
            }

            Game instance = GameFactory.creteGameInstance(GAME_NAME);
            instance.init(this);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        objectInputStream = null;
        printWriter = null;
    }

    public ClientFrame getClientFrame(){
        return clientFrame;
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
