package com.sternhalma.replay;

import com.sternhalma.replay.connection.ReplayClient;

import javax.swing.*;

public class ReplayMain {
    public static void main(String[] args) {
        String address = JOptionPane.showInputDialog(null, "IP Address", "localhost");
        String port = JOptionPane.showInputDialog(null, "Port", "25000");

        int iPort;
        try {
            iPort = Integer.parseInt(port);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Bad input", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ReplayClient client = new ReplayClient(address, iPort);
        client.connect();
    }
}
