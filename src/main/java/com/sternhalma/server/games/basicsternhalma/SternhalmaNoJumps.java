package com.sternhalma.server.games.basicsternhalma;

import com.sternhalma.common.games.Games;

import java.awt.*;

public class SternhalmaNoJumps extends BasicSternhalma{
    @Override
    public boolean isValidJump(Point oldPoint, Point newPoint) {
        return false;
    }

    @Override
    public String getGameName() {
        return Games.STERNHALMA_NO_JUMPS;
    }
}
