package com.sternhalma.common.games.basicsternhalma;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;

public class Board implements Serializable {

    public static final int BOARD_X = 25;
    public static final int BOARD_Y = 17;

    private int numberOfPlayers;
    private final HashMap<Point, Piece> piecesWithPosition;
    private static final HashSet<Point> validPoints;

    private static final HashSet<Point> topBasePoints;
    private static final HashSet<Point> topRightBasePoints;
    private static final HashSet<Point> bottomRightBasePoints;
    private static final HashSet<Point> bottomBasePoints;
    private static final HashSet<Point> bottomLeftBasePoints;
    private static final HashSet<Point> topLeftBasePoints;

    private final Set<Integer> winners;
    private int[] opponents;

    private Point lastMoveDestination;
    private boolean isLastMoveJump;

    public Board() {
        numberOfPlayers = 0;
        opponents = null;
        lastMoveDestination = null;
        piecesWithPosition = new HashMap<>();
        winners = new HashSet<>();
    }

    static {
        validPoints = generateAllValidPoints();
        topBasePoints = generateTopBasePoints();
        topRightBasePoints = generateTopRightBasePoints();
        bottomRightBasePoints = generateBottomRightBasePoints();
        bottomBasePoints = generateBottomBasePoints();
        bottomLeftBasePoints = generateBottomLeftBasePoints();
        topLeftBasePoints = generateTopLeftBasePoints();
    }

    public void movePiece(Point oldPosition, Point newPosition) {
        piecesWithPosition.put(newPosition, piecesWithPosition.get(oldPosition));
        piecesWithPosition.remove(oldPosition);
    }

    private boolean canJump(Point oldP, Point newP, int offsetX, int offsetY) {
        int newX = newP.x;
        int newY = newP.y;
        int oldX = oldP.x;
        int oldY = oldP.y;
        return ((newX - oldX) == 2 * offsetX && (newY - oldY) == 2 * offsetY)
                && getAllPiecesPositions().contains(new Point(oldX + offsetX, oldY + offsetY));
    }

    public boolean isValidMove(Point oldPoint, Point newPoint) {
        return (abs(oldPoint.x - newPoint.x) == 1 && abs(oldPoint.y - newPoint.y) == 1)
                || (abs(oldPoint.x - newPoint.x) == 2 && abs(oldPoint.y - newPoint.y) == 0);
    }

    public boolean isLeavingOpponentBase(Point oldPoint, Point newPoint) {
        int playerID = this.getPlayerIDAt(oldPoint);
        return this.getOpponentBaseCoordinates(playerID).contains(oldPoint) &&
                !(this.getOpponentBaseCoordinates(playerID).contains(newPoint));

    }

    public boolean isValidJump(Point oldPoint, Point newPoint) {
        return this.canJump(oldPoint, newPoint, 2, 0)
                || this.canJump(oldPoint, newPoint, -2, 0)
                || this.canJump(oldPoint, newPoint, 1, 1)
                || this.canJump(oldPoint, newPoint, 1, -1)
                || this.canJump(oldPoint, newPoint, -1, 1)
                || this.canJump(oldPoint, newPoint, -1, -1);
    }

    public boolean isValidPoint(Point point) {
        return validPoints.contains(point);
    }

    public Set<Integer> getWinners() {
        return winners;
    }

    public void addWinner(int playerID) {
        this.winners.add(playerID);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Piece getPieceAt(Point point) {
        return piecesWithPosition.get(point);
    }

    public int getPlayerIDAt(Point point) {
        return getPieceAt(point).getPlayerNumber();
    }

    public Set<Point> getValidPositions() {
        return validPoints;
    }

    public Set<Point> getAllPiecesPositions() {
        return piecesWithPosition.keySet();
    }

    public Set<Point> getPlayerPiecesCoordinates(int playerID) {
        HashSet<Point> playerPieces = new HashSet<>();
        piecesWithPosition.forEach((key, value) -> {
            if (value.getPlayerNumber() == playerID) {
                playerPieces.add(key);
            }
        });
        return playerPieces;
    }

    public int getOpponentBaseNumber(int playerID) {
        return opponents[playerID - 1];
    }

    public Set<Point> getOpponentBaseCoordinates(int playerID) {
        int baseNumber = getOpponentBaseNumber(playerID);
        switch (baseNumber) {
            case 1 -> {
                return topBasePoints;
            }
            case 2 -> {
                return topRightBasePoints;
            }
            case 3 -> {
                return bottomRightBasePoints;
            }
            case 4 -> {
                return bottomBasePoints;
            }
            case 5 -> {
                return bottomLeftBasePoints;
            }
            case 6 -> {
                return topLeftBasePoints;
            }
            default -> throw new IllegalStateException("Unexpected value: " + baseNumber);
        }
    }

    private void setOpponents() {
        this.opponents = switch (numberOfPlayers) {
            case 2 -> new int[]{4, 1};
            case 3 -> new int[]{3, 5, 1};
            case 4 -> new int[]{4, 1, 5, 2};
            case 6 -> new int[]{4, 1, 5, 2, 6, 3};
            default -> throw new IllegalStateException("Unexpected value: " + numberOfPlayers);
        };
    }

    private void clearBoard() {
        piecesWithPosition.clear();
    }

    private static HashSet<Point> generateAllValidPoints() {
        HashSet<Point> points = new HashSet<>();
        int mxMidPoint = 12;
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j <= i; j++) {
                points.add(new Point(mxMidPoint + j * 2, i));
            }
            mxMidPoint -= 1;
        }
        mxMidPoint = 12;
        for (int i = 16; i >= 4; i--) {
            for (int j = 0; j <= 16 - i; j++) {
                points.add(new Point(mxMidPoint + j * 2, i));
            }
            mxMidPoint--;
        }
        return points;
    }

    private static HashSet<Point> generateTopBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 12;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }
        return points;
    }

    private static HashSet<Point> generateTopRightBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 21;
        for (int i = 7; i >= 4; i--) {
            for (int j = 0; j <= 7 - i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }

        return points;
    }

    private static HashSet<Point> generateBottomRightBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 21;
        for (int i = 9; i < 13; i++) {
            for (int j = 0; j <= i - 9; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }
        return points;
    }

    private static HashSet<Point> generateBottomBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 12;
        for (int i = 16; i > 12; i--) {
            for (int j = 0; j <= 16 - i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }
        return points;
    }

    private static HashSet<Point> generateBottomLeftBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 3;
        for (int i = 9; i < 13; i++) {
            for (int j = 0; j <= i - 9; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }
        return points;
    }

    private static HashSet<Point> generateTopLeftBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 3;
        for (int i = 7; i >= 4; i--) {
            for (int j = 0; j <= 7 - i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }
        return points;
    }

    private void setPiecesAtPositionOne(int playerID) {
        topBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    private void setPiecesAtPositionTwo(int playerID) {
        topRightBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    private void setPiecesAtPositionThree(int playerID) {
        bottomRightBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    private void setPiecesAtPositionFour(int playerID) {
        bottomBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    private void setPiecesAtPositionFive(int playerID) {
        bottomLeftBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    private void setPiecesAtPositionSix(int playerID) {
        topLeftBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
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
                setOpponents();
            }
            case 3 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionThree(2);
                setPiecesAtPositionFive(3);
                setOpponents();
            }
            case 4 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
                setPiecesAtPositionTwo(3);
                setPiecesAtPositionFive(4);
                setOpponents();
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
                setOpponents();
            }
            default -> {
                throw new IllegalStateException("Invalid number of players.");
            }
        }
        return numberOfPlayers;
    }

    public boolean checkForWin(int playerID) {
        return getPlayerPiecesCoordinates(playerID)
                .equals(getOpponentBaseCoordinates(playerID));
    }

}
