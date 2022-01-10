package com.sternhalma.common.games.basicsternhalma;

import java.awt.*;
import java.io.Serializable;

public class Piece implements Serializable {
    private int playerNumber;
//    private boolean inOpponentBase;

    public Piece(int playerNumber) {
        this.playerNumber = playerNumber;
//        inOpponentBase = false;
    }

//    public boolean isInOpponentBase() {0
//        return inOpponentBase;
//    }
//
//    public void setInOpponentBase() {
//        this.inOpponentBase = true;
//    }

    public int getPlayerNumber() {
        return playerNumber;
    }

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
