package com.sternhalma.common.games.Sternhalma;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Board {
    private int numberOfPlayers;
    private static final HashSet<Point> validPoints;

    static {
        validPoints = new HashSet<>();
        int mxMidPoint = 12;
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j <= i; j++) {
                validPoints.add(new Point(i, mxMidPoint + j * 2));
            }
            mxMidPoint -= 1;
        }
        mxMidPoint = 12;
        for (int i = 16; i >= 4; i--) {
            for (int j = 0; j <= 16 - i; j++) {
                validPoints.add(new Point(i, mxMidPoint + j * 2));
            }
            mxMidPoint--;
        }

    }

    private HashMap<Point, Piece> piecesWithPosition;


    public Board() {
        numberOfPlayers = 0;
        piecesWithPosition = new HashMap<>();
    }

    public Piece getPieceAt(Point position) {
        return piecesWithPosition.get(position);
    }

    public void movePiece(Point oldPosition, Point newPosition) {
        piecesWithPosition.put(newPosition, piecesWithPosition.get(oldPosition));
    }

    public boolean isValidPoint(Point point){
        return validPoints.contains(point);
    }

    private void setPiecesAtPositionOne(int playerID) {
        int xMidPoint = 12;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= i; j++) {
                Point point = new Point(i, xMidPoint + j * 2);
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
                Point point = new Point(i, xMidPoint + j * 2);
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
                Point point = new Point(i, xMidPoint + j * 2);
                Piece piece = new Piece(playerID);
                piecesWithPosition.put(point, piece);
            }
            xMidPoint -= 1;
        }
    }

    private void setPiecesAtPositionFour(int playerID) {
        int xMidPoint = 12;
        for (int i = 16; i >= 12; i--) {
            for (int j = 0; j <= 16 - i; j++) {
                Point point = new Point(i, xMidPoint + j * 2);
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
                Point point = new Point(i, xMidPoint + j * 2);
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
                Point point = new Point(i, xMidPoint + j * 2);
                Piece piece = new Piece(playerID);
                piecesWithPosition.put(point, piece);
            }
            xMidPoint -= 1;
        }
    }

    public static void main(String[] args) {
        Point p = new Point(2,3);
        System.out.println(p.toString());
    }

    public int addPlayer() {
        numberOfPlayers++;
        switch (numberOfPlayers) {
            case 1 -> {
                setPiecesAtPositionOne(1);
            }
            case 2 -> {
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
            }
            case 3 -> {
                setPiecesAtPositionOne(1);
                setPiecesAtPositionThree(2);
                setPiecesAtPositionFive(3);

            }
            case 4 -> {
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
                setPiecesAtPositionTwo(3);
                setPiecesAtPositionFive(4);
            }
            case 5 -> {
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
                setPiecesAtPositionTwo(3);
                setPiecesAtPositionFive(4);
                setPiecesAtPositionThree(5);
            }
            case 6 -> {
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
