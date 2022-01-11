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

/**
 * Handler gracza łączący się z serwerem.
 */
public class Client {
    /**
     * Adres serwera.
     */
    private final String address;
    /**
     * Port serwera.
     */
    private final int port;
    /**
     * Id gry.
     */
    private final String gameID;
    /**
     * Główne okno interfejsu użytkownika.
     */
    private final ClientFrame clientFrame;
    /**
     * Wejściowy strumień obiektów z serwera.
     */
    private ObjectInputStream objectInputStream;
    /**
     * PrintWriter do wysyłania danych do serwera.
     */
    private PrintWriter printWriter;
    /**
     * Nazwa typu gry, do którego dołączamy (i tworzymy).
     */
    private static final String GAME_NAME = Games.BASIC_STERNHALMA;

    /**
     * Konstruktor inicjalizujący wartości pobranew kklasie ClientMain.java.
     * @param address adres serwera
     * @param port port serwera
     * @param gameID id gry
     */
    public Client(String address, int port, String gameID) {
        this.address = address;
        this.port = port;
        this.gameID = gameID;
        clientFrame = new ClientFrame(address + ":" + port);
    }

    /**
     * Zwraca id gry, do której klient jest połączony.
     * @return id gry
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * Łączy się z serwerem i inicjalizuje komunikację. Tworzy instancję gry.
     */
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

    /**
     * Tworzy instancję gry i deleguje dalsze wykonywanie do kodu odpowiedniego dla danej gry. W przypadku błędu wyświetla kkomunikat w okienku.
     */
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

    /**
     * Zwraca główne okno interfejsu.
     * @return główne okno interfejsu
     */
    public ClientFrame getClientFrame() {
        return clientFrame;
    }

    /**
     * Wysyła wiadomość do serwera. W przypadku braku połączenia wyrzuca wyjątek.
     * @throws IllegalStateException
     * @param message wiadomość do serwera
     */
    public void sendMessage(String message) {
        if (printWriter == null) {
            throw new IllegalStateException();
        }
        synchronized (printWriter) {
            printWriter.println(message);
        }
    }

    /**
     * Pobiera obiekt z serwera. W przypadku braku połączenia wyrzuca wyjątek.
     * @throws IllegalStateException
     * @return obiekt z serwera
     */
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
