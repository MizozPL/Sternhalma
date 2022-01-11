package com.sternhalma.common.games.basicsternhalma;

import java.awt.*;
import java.io.Serializable;

/**
 * Klasa pionka, przechowująca ID swojego właściciela.
 */
public class Piece implements Serializable {
    /**
     * ID gracza
     */
    private int playerNumber;

    /**
     * Konstruktor, inicjalizuje ID właściciela.
     * @param playerNumber
     */
    public Piece(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * Zwraca ID gracza.
     * @return
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Zwraca kolor pionków dla gracza o danym id.
     * @param playerID id gracza (grupy pionków)
     * @return kolor pionka
     */
    public static Color getColorForPlayerWithID(int playerID) {
        return switch (playerID) {
            case 1 -> Color.RED;
            case 2 -> Color.GREEN;
            case 3 -> Color.ORANGE;
            case 4 -> Color.YELLOW;
            case 5 -> Color.PINK;
            case 6 -> Color.CYAN;
            default -> Color.WHITE;
        };
    }
}
