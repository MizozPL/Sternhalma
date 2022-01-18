package com.sternhalma.server.games.basicsternhalma;

import com.sternhalma.common.games.Games;

import java.awt.*;

import static java.lang.Math.abs;

public class SternhalmaExtendedMoveLength extends BasicSternhalma{

    @Override
    public boolean isValidMove(Point oldPoint, Point newPoint) {
        return super.isValidMove(oldPoint, newPoint) || (((abs(oldPoint.x - newPoint.x) == 2 && abs(oldPoint.y - newPoint.y) == 2)
                || (abs(oldPoint.x - newPoint.x) == 4 && abs(oldPoint.y - newPoint.y) == 0) ||
                (abs(oldPoint.x - newPoint.x) == 0 && abs(oldPoint.y - newPoint.y) == 2) ||
                abs(oldPoint.x - newPoint.x) == 3 && abs(oldPoint.y - newPoint.y) == 1)
                && board.getPieceAt(newPoint) == null);
    }

    @Override
    public String getGameName() {
        return Games.STERNHALMA_EXTENDED_MOVE_LENGTH;
    }
}
