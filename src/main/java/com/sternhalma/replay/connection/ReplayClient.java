package com.sternhalma.replay.connection;

import com.sternhalma.common.connection.NetworkMessages;
import com.sternhalma.common.replay.ReplayPacket;
import com.sternhalma.replay.gui.ReplayBasicSternhalmaPanel;
import com.sternhalma.replay.gui.ReplayFrame;
import com.sternhalma.replay.replays.BasicSternhalmaReplay;
import com.sternhalma.server.GameHistory;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class ReplayClient {

    private final String address;
    private final int port;
    private ObjectInputStream objectInputStream;
    private PrintWriter printWriter;

    public ReplayClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void connect() {
        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            createReplayInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        objectInputStream = null;
        printWriter = null;
    }

    private void createReplayInstance() {
        sendMessage(NetworkMessages.LIST_REPLAYS);
        Object response = readObject();

        if(!(response instanceof List)) {
            JOptionPane.showMessageDialog(null, "Error getting replay IDs!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Integer> list = (List) response;
        String str = "";
        for(int i : list) {
            str += i + ", ";
        }

        String replayID = JOptionPane.showInputDialog(null, "Select replay ID: " + str, "0");
        int replayIDint;
        try {
            replayIDint = Integer.parseInt(replayID);
            if(!list.contains(replayIDint)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Bad input", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sendMessage(NetworkMessages.GET_REPLAY + ":" + replayIDint);
        Object response2 = readObject();
        if(!(response2 instanceof ReplayPacket)) {
            JOptionPane.showMessageDialog(null, "Error getting the replay!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ReplayFrame frame = new ReplayFrame();
        BasicSternhalmaReplay basicSternhalmaReplay = new BasicSternhalmaReplay((ReplayPacket) response2, frame);

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
