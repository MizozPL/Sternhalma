package com.sternhalma.common.games.basicsternhalma;

import java.awt.*;
import java.io.Serializable;

public class Piece implements Serializable {
    private int playerNumber;
    private boolean inOpponentBase;

    public Piece(int playerNumber) {
        this.playerNumber = playerNumber;
        inOpponentBase = false;
    }

    public boolean isInOpponentBase() {
        return inOpponentBase;
    }

    public void setInOpponentBase() {
        this.inOpponentBase = true;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public static Color getColorForPlayerWithID(int playerID) {
        Color color = Color.WHITE;
        switch (playerID) {
            case 1 -> color = Color.RED;
            case 2 -> color = Color.GREEN;
            case 3 -> color = Color.ORANGE;
            case 4 -> color = Color.YELLOW;
            case 5 -> color = Color.PINK;
            case 6 -> color = Color.CYAN;
        }
        return color;
    }
}
