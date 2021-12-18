package com.sternhalma.common.games.Sternhalma;

public class Position {

    public int x;
    public int y;

    public Position (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof Position p2))
            return false;
        return this.x == p2.x && this.y == p2.y;
    }
}
