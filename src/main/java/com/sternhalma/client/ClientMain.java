package com.sternhalma.client;

import com.sternhalma.client.connection.Client;

import javax.swing.*;

public class ClientMain {
    public static void main(String[] args) {
        //String address = JOptionPane.showInputDialog("IP Address");
        //String port = JOptionPane.showInputDialog("Port");
        //String gameID = JOptionPane.showInputDialog("GameID");
        String address ="localhost";
        String port = "25000";
        String gameID="1";

        int iPort;
        try{
            iPort = Integer.parseInt(port);
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null,"Bad input","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        Client client = new Client(address,iPort,gameID);
        client.connect();

    }
}
