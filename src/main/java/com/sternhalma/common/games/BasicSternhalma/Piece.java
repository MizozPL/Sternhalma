package com.sternhalma.common.games.BasicSternhalma;

import java.io.Serializable;

public class Piece implements Serializable {
    private int playerNumber;

    public Piece(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
