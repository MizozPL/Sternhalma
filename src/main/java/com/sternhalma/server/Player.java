package com.sternhalma.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player implements Runnable {
    private final Socket playerSocket;
    private PrintWriter output;
    private Scanner input;
    public Player(Socket socket) {
        this.playerSocket = socket;
    }

    @Override
    public void run() {
        try {
            output = new PrintWriter(playerSocket.getOutputStream(), true);
            input = new Scanner(playerSocket.getInputStream());
            while(input.hasNextLine()) {
                String line = input.nextLine();
                //TODO: Tablicować splita co by nie splitować za każdym razem...
                String request = line.split(":")[0];
                switch (request) {
                    case "createGame" -> {
                        output.println(GameManager.getInstance().createGame(line.split(":")[1], line.split(":")[2]));
                    }
                    case "joinGame" -> {
                        output.println(GameManager.getInstance().joinGame(this, line.split(":")[1]));
                    }
                    case "performAction" -> {
                        output.println(GameManager.getInstance().performAction(this, line.split(":")[1], line.substring(14).substring(line.split(":")[1].length() + 1)));
                    }
                    default -> {
                        output.println("Bad request!");
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
        }

    }

    public synchronized void sendData(String data) {
        if(output == null)
            throw new IllegalStateException();
        output.println(data);
    }
}
