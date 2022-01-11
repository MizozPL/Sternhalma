package com.sternhalma.client.games.basicsternhalma;

import com.sternhalma.client.connection.Client;
import com.sternhalma.client.games.Game;
import com.sternhalma.client.games.basicsternhalma.gui.BasicSternhalmaPanel;
import com.sternhalma.common.connection.NetworkMessages;
import com.sternhalma.common.games.basicsternhalma.Board;

import javax.swing.*;
import java.awt.*;

/**
 * Implementacja podstawowego wariantu gry po stronie serwera.
 * Deleguje wysyłanie i pobieranie informacji z serwera do klasy Client oraz odpowiednio je przetwarza/parsuje.
 */
public class BasicSternhalma implements Game {
    /**
     * Panel interfejsu gry.
     */
    private BasicSternhalmaPanel panel;
    /**
     * Handler połączenia z serwerem.
     */
    private Client client;

    /**
     * Inicjalizuje interfejs gry oraz odpowiednio przetwarza komunikaty z i do serwera. Wyświetla również komunikaty błędów (np. rozłączenie się gracza).
     * @param client handler do komunikacji z serwerem.
     */
    @Override
    public void init(Client client) {
        this.client = client;
        panel = new BasicSternhalmaPanel(this);
        this.client.getClientFrame().setGamePanel(panel);
        Object object;
        while (true) {
            try {
                object = client.readObject();
            } catch(Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(client.getClientFrame(), "Internal error!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (object instanceof Board) {
                Board board = (Board) object;
                panel.setBoard(board);
                continue;
            }

            if (object instanceof String) {
                String message = (String) object;
                String tokens[] = message.split(":");

                switch (tokens[0]) {
                    case NetworkMessages.BOARD_UPDATE -> {
                        if (tokens.length > 3) {
                            try {
                                int playerID = Integer.parseInt(tokens[1]);
                                int turn = Integer.parseInt(tokens[2]);
                                int playerNum = Integer.parseInt(tokens[3]);

                                int controlNumber = turn % playerNum;
                                int realTurn = (controlNumber == 0 ? playerNum : controlNumber);
                                panel.setPlayerID(playerID);
                                panel.setTurn(realTurn);
                                panel.repaint();
                            } catch (NumberFormatException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    case NetworkMessages.WINNER -> {
                        if (tokens.length > 1) {
                            try {
                                int playerID = Integer.parseInt(tokens[1]);
                                JOptionPane.showMessageDialog(client.getClientFrame(), "Player " + playerID + " ended the game!", "Information", JOptionPane.INFORMATION_MESSAGE);
                            } catch (NumberFormatException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    case NetworkMessages.GAME_END -> {
                        JOptionPane.showMessageDialog(client.getClientFrame(), "Game ended!", "Information", JOptionPane.INFORMATION_MESSAGE);
                        return; //disconnect
                    }
                    case NetworkMessages.GAME_END_PLAYER_DISCONNECTED -> {
                        JOptionPane.showMessageDialog(client.getClientFrame(), "Player got disconnected, game ended!", "Information", JOptionPane.INFORMATION_MESSAGE);
                        return; //disconnect
                    }
                }
            }
        }
    }

    /**
     * Deleguje wysłanie żądania wykonania ruchu przez pionek.
     * @param from pozycja początkowa pionka
     * @param to pozycja końcowa pionka
     */
    public void movePiece(Point from, Point to) {
        client.sendMessage(NetworkMessages.PERFORM_ACTION + ":" + client.getGameID() + ":" + NetworkMessages.MOVE + ":" + from.x + "," + from.y + ":" + to.x + "," + to.y);
    }

    /**
     * Deleguje wysłanie żądania końca tury do serwera.
     */
    public void endTurn() {
        client.sendMessage(NetworkMessages.PERFORM_ACTION + ":" + client.getGameID() + ":" + NetworkMessages.END_TURN);
    }
}
