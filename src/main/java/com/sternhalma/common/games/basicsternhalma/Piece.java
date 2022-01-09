package com.sternhalma.common.games.basicsternhalma;

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
}
