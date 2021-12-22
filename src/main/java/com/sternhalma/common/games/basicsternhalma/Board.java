package com.sternhalma.common.games.basicsternhalma;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Board implements Serializable {
    private int numberOfPlayers;
    private static final HashSet<Point> validPoints;
    private final HashMap<Point, Piece> piecesWithPosition;

    public Set<Point> getValidPositions() {
        return validPoints;
    }

    public boolean canJump(Point oldP, Point newP, int offsetX, int offsetY) {
        int newX = newP.x;
        int newY = newP.y;
        int oldX = oldP.x;
        int oldY = oldP.y;
        return ((newX - oldX) == 2 * offsetX && (newY - oldY) == 2 * offsetY)
                && getAllPiecesPositions().contains(new Point(oldX + offsetX, oldY + offsetY));
    }

    public Set<Point> getAllPiecesPositions() {
        return piecesWithPosition.keySet();
    }

    static {
        validPoints = new HashSet<>();
        int mxMidPoint = 12;
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j <= i; j++) {
                validPoints.add(new Point(mxMidPoint + j * 2, i));
            }
            mxMidPoint -= 1;
        }
        mxMidPoint = 12;
        for (int i = 16; i >= 4; i--) {
            for (int j = 0; j <= 16 - i; j++) {
                validPoints.add(new Point(mxMidPoint + j * 2, i));
            }
            mxMidPoint--;
        }

    }

    public Set<Point> getPlayerPiecesCoordinates(int playerID) {
        HashSet<Point> playerPieces = new HashSet<>();
        for (HashMap.Entry<Point, Piece> entry : piecesWithPosition.entrySet()) {
            if (entry.getValue().getPlayerNumber() == playerID) {
                playerPieces.add(entry.getKey());
            }
        }
        return playerPieces;
    }

    private void clearBoard() {
        piecesWithPosition.clear();
    }

    public Board() {
        numberOfPlayers = 0;
        piecesWithPosition = new HashMap<>();
    }

    public Piece getPieceAt(Point position) {
        return piecesWithPosition.get(position);
    }

    public void movePiece(Point oldPosition, Point newPosition) {
        piecesWithPosition.put(newPosition, piecesWithPosition.get(oldPosition));
        piecesWithPosition.remove(oldPosition);
    }

    public boolean isValidPoint(Point point) {
        return validPoints.contains(point);
    }

    private void setPiecesAtPositionOne(int playerID) {
        int xMidPoint = 12;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                Piece piece = new Piece(playerID);
                piecesWithPosition.put(point, piece);
            }
            xMidPoint -= 1;
        }

    }

    private void setPiecesAtPositionThree(int playerID) {
        int xMidPoint = 21;
        for (int i = 9; i < 13; i++) {
            for (int j = 0; j <= i - 9; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                Piece piece = new Piece(playerID);
                piecesWithPosition.put(point, piece);
            }
            xMidPoint -= 1;
        }
    }

    private void setPiecesAtPositionFive(int playerID) {
        int xMidPoint = 3;
        for (int i = 9; i < 13; i++) {
            for (int j = 0; j <= i - 9; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                Piece piece = new Piece(playerID);
                piecesWithPosition.put(point, piece);
            }
            xMidPoint -= 1;
        }
    }

    private void setPiecesAtPositionFour(int playerID) {
        int xMidPoint = 12;
        for (int i = 16; i > 12; i--) {
            for (int j = 0; j <= 16 - i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                Piece piece = new Piece(playerID);
                piecesWithPosition.put(point, piece);
            }
            xMidPoint -= 1;
        }
    }

    private void setPiecesAtPositionTwo(int playerID) {
        int xMidPoint = 21;
        for (int i = 7; i >= 4; i--) {
            for (int j = 0; j <= 7 - i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                Piece piece = new Piece(playerID);
                piecesWithPosition.put(point, piece);
            }
            xMidPoint -= 1;
        }
    }

    private void setPiecesAtPositionSix(int playerID) {
        int xMidPoint = 3;
        for (int i = 7; i >= 4; i--) {
            for (int j = 0; j <= 7 - i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                Piece piece = new Piece(playerID);
                piecesWithPosition.put(point, piece);
            }
            xMidPoint -= 1;
        }
    }

    public int addPlayer() {
        numberOfPlayers++;
        switch (numberOfPlayers) {
            case 1 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
            }
            case 2 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
            }
            case 3 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionThree(2);
                setPiecesAtPositionFive(3);

            }
            case 4 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
                setPiecesAtPositionTwo(3);
                setPiecesAtPositionFive(4);
            }
            case 5 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
                setPiecesAtPositionTwo(3);
                setPiecesAtPositionFive(4);
                setPiecesAtPositionThree(5);
            }
            case 6 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
                setPiecesAtPositionTwo(3);
                setPiecesAtPositionFive(4);
                setPiecesAtPositionThree(5);
                setPiecesAtPositionSix(6);
            }
            default -> {
                throw new IllegalStateException("Invalid number of players.");
            }
        }
        return numberOfPlayers;
    }
}
