package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player implements Runnable {
    private int playerId;
    private Socket socket;
    private DataInputStream fromPlayer;
    private DataOutputStream toPlayer;

    public Player(Socket socket, int id) {
        this.socket = socket;
        this.playerId = id;
    }


    @Override
    public void run() {
        try {
            fromPlayer = new DataInputStream(socket.getInputStream());
            toPlayer = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int sendData(int data) {
        try {
            this.toPlayer.writeInt(data);
            return 1;
        } catch (IOException e) {
            System.out.println("sending: Player not found");
        }
    }

    public int receiveData() {
        try {
            int data = this.fromPlayer.readInt();
            return data;
        } catch (IOException e) {
            System.out.println("Waiting: No respond from Player");
        }
    }

    public void closeConnection() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isOnline() {
        return this.socket.isConnected();
    }
}

