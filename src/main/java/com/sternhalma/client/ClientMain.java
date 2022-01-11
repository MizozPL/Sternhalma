package com.sternhalma.client;

import com.sternhalma.client.connection.Client;

import javax.swing.*;

/**
 * Klasa pobierająca adres i port do serwera oraz nawiązująca z nim połączenie. Pobiera również id gry.
 */
public class ClientMain {

    /**
     * Pobiera adres, port i id gry a następnie tworzy instancję klienta.
     * @param args ignorowane
     */
    public static void main(String[] args) {
        String address = JOptionPane.showInputDialog(null, "IP Address", "localhost");
        String port = JOptionPane.showInputDialog(null, "Port", "25000");
        String gameID = JOptionPane.showInputDialog(null, "GameID", "1");

        int iPort;
        try {
            iPort = Integer.parseInt(port);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Bad input", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Client client = new Client(address, iPort, gameID);
        client.connect();

    }
}
